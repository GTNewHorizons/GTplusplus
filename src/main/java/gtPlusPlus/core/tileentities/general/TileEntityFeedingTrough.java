package gtPlusPlus.core.tileentities.general;

import java.lang.reflect.*;
import java.util.*;

import com.mojang.authlib.GameProfile;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.api.objects.minecraft.*;
import gtPlusPlus.core.entity.ai.EntityAITryMoveToTargetBlockpos;
import gtPlusPlus.core.util.Utils;
import gtPlusPlus.core.util.math.MathUtils;
import gtPlusPlus.core.util.minecraft.*;
import gtPlusPlus.core.util.reflect.ReflectionUtils;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.*;
import net.minecraftforge.common.util.FakePlayer;

public class TileEntityFeedingTrough extends TileEntity implements IInventory {

	private static final GameProfile DUMMY_PROFILE = new GameProfile(UUID.randomUUID(), "[FeedingTrough]");
	public static final int MAX_COOLDOWN = 100;
	public static final int MAX_ANIMALS = 32;
	public static final int MAX_RANGE = 16;
	public static final double MAX_LOVE_CHANCE = 0.333333333;
	private static Method aFindPlayer = ReflectionUtils.getMethod(EntityAnimal.class, "findPlayerToAttack");
	private static Field aPath = ReflectionUtils.getField(EntityAnimal.class, "pathToEntity");

	private ItemStack[] mInventory;

	private FakePlayer mFoodHolder = null;

	private int mCooldown = 0;
	private long mInternalRNG = 0;

	public TileEntityFeedingTrough() {
		this.mInventory = new ItemStack[getSizeInventory()];
	}

	@Override
	public void updateEntity() {
		updateMeta();
		tick(this);
	}

	public FakePlayer getFoodHolder(EntityAnimal aAnimal) {
		if (this.mFoodHolder == null && Utils.isServer()) {
			if(this.worldObj instanceof WorldServer) {
				this.mFoodHolder = new MovableFakePlayer((WorldServer) this.worldObj, DUMMY_PROFILE);
			}
		}
		if (this.mFoodHolder != null) {
			this.mFoodHolder.setPosition(this.xCoord, this.yCoord, this.zCoord);
			return this.mFoodHolder;
		}
		return null;
	}

	public World getWorld() {
		return this.worldObj;
	}

	public void updateMeta() {
		if (this.isEmpty()) {
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 2);
		}
		else {
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 1, 2);
		}
	}

	public static List<EntityAnimal> getAnimalsInRange(TileEntityFeedingTrough aTile){
		ArrayList<EntityAnimal> aReturn = new ArrayList<EntityAnimal>();
		if (aTile == null) {
			Logger.INFO("Bad TileEntity");
			return aReturn;
		}
		List<EntityAnimal> animals = aTile.worldObj.getEntitiesWithinAABB(EntityAnimal.class, new AABB(aTile, MAX_RANGE, MAX_RANGE, MAX_RANGE).get());
		for (EntityAnimal animal : animals) {
			if (!EntityPlayer.class.isInstance(animal)) {
				aReturn.add(animal);
				setFeedTask(animal);
			}
		}
		return animals;
	}

	public static List<EntityAnimal> getAnimalsInRange(Entity aEntity){
		ArrayList<EntityAnimal> aReturn = new ArrayList<EntityAnimal>();
		if (aEntity == null) {
			Logger.INFO("Bad Entity");
			return aReturn;
		}
		List<EntityAnimal> animals = aEntity.worldObj.getEntitiesWithinAABB(EntityAnimal.class, new AABB(aEntity, MAX_RANGE, MAX_RANGE, MAX_RANGE).get());
		for (EntityAnimal animal : animals) {
			if (!EntityPlayer.class.isInstance(animal)) {
				aReturn.add(animal);
				setFeedTask(animal);
			}
		}
		return animals;
	}

	public static void setFeedTask(EntityCreature aEntity) {
		if (!EntityAITryMoveToTargetBlockpos.hasTask(aEntity)) {
			aEntity.tasks.addTask(1, new EntityAITryMoveToTargetBlockpos(aEntity, 1.25f, 32));
		}
	}

	public static void tick(TileEntityFeedingTrough be) {
		if (be.getWorld() != null && !be.getWorld().isRemote) {
			if (be.mCooldown > 0) {
				be.mCooldown--;
			}
			else {
				be.mCooldown = MAX_COOLDOWN; // minimize aabb calls
				if (be.mFoodHolder == null) {
					be.mFoodHolder = be.getFoodHolder(null);
				}
				Logger.INFO("Bleep");
				List<EntityAnimal> animals = getAnimalsInRange(be);
				Logger.INFO("Bleep 1 | "+animals.size());

				for (EntityAnimal creature : animals) {
					Logger.INFO("Bleep 2");
					EntityAITryMoveToTargetBlockpos.setTaskLocation(creature, new BlockPos(be).getUp());
					EntityPlayer aPlayer = be.getFoodHolder(creature);
					if (aPlayer != null && canFallInLove(creature) && creature.getAge() == 0) {
						for (int i = 0; i < be.getSizeInventory(); i++) {
							ItemStack stack = be.getStackInSlot(i);
							if (stack != null && creature.isBreedingItem(stack) && creature.getGrowingAge() == 0 && !creature.isInLove()) {
								aPlayer.setCurrentItemOrArmor(0, stack);
								//creature.playSound("random.eat", 0.5F + 0.5F * be.getWorld().rand.nextInt(2), (be.getWorld().rand.nextFloat() - be.getWorld().rand.nextFloat()) * 0.2F + 1.0F);
								be.addItemParticles(creature, stack, MathUtils.randInt(32, 64));
								if(be.getSpecialRand().nextDouble() < MAX_LOVE_CHANCE) {
									List<EntityAnimal> animalsAround = getAnimalsInRange(be);
									if(animalsAround.size() <= MAX_ANIMALS){
										creature.func_146082_f(aPlayer);
										creature.playSound("random.eat", 0.5F + 0.5F * be.getWorld().rand.nextInt(2), (be.getWorld().rand.nextFloat() - be.getWorld().rand.nextFloat()) * 0.2F + 1.0F);
									}
								}
								--stack.stackSize;
								if (stack.stackSize <= 0) {
									be.setInventorySlotContents(i, null);
									stack = null;
								}
								aPlayer.setCurrentItemOrArmor(0, null);
								be.markDirty();
								//creature.playSound("random.eat", 0.5F + 0.5F * be.getWorld().rand.nextInt(2), (be.getWorld().rand.nextFloat() - be.getWorld().rand.nextFloat()) * 0.2F + 1.0F);
								break;
							}
						}
					}
				}
			}
		}
	}

	private void addItemParticles(EntityAnimal creature, ItemStack stack, int amount) {
		for (int j = 0; j < amount; ++j) {
			Vec3 vec3 = Vec3.createVectorHelper((getWorld().rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
			vec3.rotateAroundX(-creature.rotationPitch * (float) Math.PI / 180.0F);
			vec3.rotateAroundY(-creature.rotationYaw * (float) Math.PI / 180.0F);
			Vec3 vec31 = Vec3.createVectorHelper((getWorld().rand.nextFloat() - 0.5D) * 0.3D, (-getWorld().rand.nextFloat()) * 0.6D - 0.3D, 0.6D);
			vec31.rotateAroundX(-creature.rotationPitch * (float) Math.PI / 180.0F);
			vec31.rotateAroundY(-creature.rotationYaw * (float) Math.PI / 180.0F);
			vec31 = vec31.addVector(creature.posX, creature.posY + creature.getEyeHeight(), creature.posZ);
			StringBuilder s = new StringBuilder("iconcrack_").append(Item.getIdFromItem(stack.getItem()));
			if (stack.getHasSubtypes()) {
				s.append("_").append(stack.getItemDamage());
			}
			this.worldObj.spawnParticle(s.toString(), vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord);
		}
	}

	public static boolean canFallInLove(EntityAnimal aAnimal) {
		if (!aAnimal.isInLove() && !aAnimal.isDead && !aAnimal.isChild()) {
			return true;
		}
		return false;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer aPlayer, EntityAnimal aAnimal) {
		ItemStack itemstack = aPlayer.inventory.getCurrentItem();
		if (itemstack != null && aAnimal.isBreedingItem(itemstack) && aAnimal.getGrowingAge() == 0 && !aAnimal.isInLove()) {
			--itemstack.stackSize;
			if (itemstack.stackSize <= 0) {
				aPlayer.inventory.setInventorySlotContents(aPlayer.inventory.currentItem, (ItemStack) null);
			}
			aAnimal.func_146082_f(aPlayer);
			return true;
		}
		return false;
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound aNBT) {
		super.readFromNBT(aNBT);
		final NBTTagList list = aNBT.getTagList("Items", 10);
		this.mInventory = new ItemStack[getSizeInventory()];
		for(int i = 0;i<list.tagCount();i++){
			final NBTTagCompound data = list.getCompoundTagAt(i);
			final int slot = data.getInteger("Slot");
			if((slot >= 0) && (slot < getSizeInventory())){
				this.mInventory[slot] = ItemStack.loadItemStackFromNBT(data);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound aNBT) {
		super.writeToNBT(aNBT);
		final NBTTagList list = new NBTTagList();
		for(int i = 0;i<getSizeInventory();i++){
			final ItemStack stack = this.mInventory[i];
			if(stack != null){
				final NBTTagCompound data = new NBTTagCompound();
				stack.writeToNBT(data);
				data.setInteger("Slot", i);
				list.appendTag(data);
			}
		}
		aNBT.setTag("Items", list);
	}

	private Random getSpecialRand() {
		Random specialRand = new Random(this.mInternalRNG);
		this.mInternalRNG = specialRand.nextLong();
		return specialRand;
	}


	public boolean isEmpty() {
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack stack = getStackInSlot(i);
			if (ItemUtils.checkForInvalidItems(stack)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 15;
	}

	@Override
	public ItemStack getStackInSlot(int aSlot) {
		return this.mInventory[aSlot];
	}

	@Override
	public ItemStack decrStackSize(int aSlot, int aRemove) {
		ItemStack aStack = getStackInSlot(aSlot);
		if (aStack != null) {
			aStack.stackSize = aStack.stackSize - aRemove;
			if (aStack.stackSize <= 0) {
				setInventorySlotContents(aSlot, null);
				aStack = null;
			}
			return aStack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int aSlot) {
		return getStackInSlot(aSlot);
	}

	@Override
	public void setInventorySlotContents(int aSlot, ItemStack aStack) {
		this.mInventory[aSlot] = aStack;
	}

	@Override
	public String getInventoryName() {
		return "Feeding Trough";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int aSlot, ItemStack aStack) {
		return true;
	}
}
