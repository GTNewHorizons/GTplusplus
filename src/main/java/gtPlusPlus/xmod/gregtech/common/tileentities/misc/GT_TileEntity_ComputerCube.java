package gtPlusPlus.xmod.gregtech.common.tileentities.misc;

import java.util.ArrayList;
import java.util.Collections;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.util.math.MathUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.xmod.gregtech.api.gui.computer.GT_Container_ComputerCube;
import gtPlusPlus.xmod.gregtech.api.gui.computer.GT_GUIContainer_ComputerCube;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import gtPlusPlus.xmod.gregtech.common.computer.GT_ComputercubeDescription;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.Ic2Items;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class GT_TileEntity_ComputerCube extends GT_MetaTileEntity_BasicTank implements IReactor {

	public static boolean mSeedscanner = true;

	public static boolean mReactorplanner = true;

	public static ArrayList<GT_ItemStack> sReactorList;

	public boolean mStarted = false;

	public int mMode = 0;

	public int mHeat = 0;

	public int mEUOut = 0;

	public int mMaxHeat = 10000;

	public int mEU = 0;

	public int mProgress = 0;

	public int mEUTimer = 0;

	public int mEULast1 = 0;

	public int mEULast2 = 0;

	public int mEULast3 = 0;

	public int mEULast4 = 0;

	public float mHEM = 1.0F, mExplosionStrength = 0.0F;

	private boolean mNeedsUpdate;

	public GT_TileEntity_ComputerCube(final int aID, final String aDescription) {
		super(aID, "computer.cube", "Computer Cube", 5, 114, aDescription);
	}

	public GT_TileEntity_ComputerCube(final String aName, final String aDescription, final ITexture[][][] aTextures) {
		super(aName, 5, 114, aDescription, aTextures);
	}

	@Override
	public Object getServerGUI(final int aID, final InventoryPlayer aPlayerInventory, final IGregTechTileEntity aBaseMetaTileEntity) {
		try {
			return new GT_Container_ComputerCube(aPlayerInventory, aBaseMetaTileEntity, mMode);
		}
		catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	@Override
	public Object getClientGUI(final int aID, final InventoryPlayer aPlayerInventory, final IGregTechTileEntity aBaseMetaTileEntity) {
		try {
			return new GT_GUIContainer_ComputerCube(aPlayerInventory, aBaseMetaTileEntity, mMode);
		}
		catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean onRightclick(final IGregTechTileEntity aBaseMetaTileEntity, final EntityPlayer aPlayer) {
		if (aBaseMetaTileEntity.isClientSide()) {
			return true;
		}
		Logger.INFO("Did rmb.");
		boolean aDidOpen = aBaseMetaTileEntity.openGUI(aPlayer);
		Logger.INFO("Did open? "+aDidOpen);
		return true;
	}

	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_TileEntity_ComputerCube(this.mName, this.mDescription, this.mTextures);
	}

	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		ItemStack tStack = aPlayer.getCurrentEquippedItem();
		if (tStack != null && ItemList.Tool_DataOrb.isStackEqual(tStack)) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isSimpleMachine() {
		return true;
	}

	@Override
	public boolean isEnetInput() {
		return true;
	}

	@Override
	public boolean isInputFacing(byte aDirection) {
		return true;
	}	

	@Override
	public long maxAmperesIn() {
		return 4;
	}

	@Override
	public long maxEUInput() {
		return GT_Values.V[2];
	}
	
	@Override
	public long maxEUStore() {
		return GT_Values.V[5] * 1024;
	}

	@Override
	public boolean ownerControl() {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 114;
	}

	@Override
	public boolean isValidSlot(int aIndex) {
		return (aIndex > 53 && aIndex < 58);
	}
	
	@Override
	public boolean isFacingValid(byte aFacing) {
		return true;
	}

	public void saveNuclearReactor() {
		for (int i = 0; i < 54; i++) {
			if (this.mInventory[i] == null) {
				this.mInventory[i + 59] = null;
			}
			else {
				this.mInventory[i + 59] = this.mInventory[i].copy();
			}
		}
	}

	public void loadNuclearReactor() {
		for (int i = 0; i < 54; i++) {
			if (this.mInventory[i + 59] == null) {
				this.mInventory[i] = null;
			}
			else {
				this.mInventory[i] = this.mInventory[i + 59].copy();
			}
		}
	}

	public int getXCoord() {
		return this.getBaseMetaTileEntity().getXCoord();
	}

	public int getYCoord() {
		return this.getBaseMetaTileEntity().getYCoord();
	}

	public int getZCoord() {
		return this.getBaseMetaTileEntity().getZCoord();
	}

	public void reset() {
		this.mEU = 0;
		this.mHeat = 0;
		this.mEUOut = 0;
		this.mMaxHeat = 10000;
		this.mHEM = 1.0F;
		this.mExplosionStrength = 0.0F;
		this.mProgress = 0;
		this.mInventory[113] = null;
		int i;
		for (i = 0; i < 54; i++) {
			this.mInventory[i] = null;
			this.mInventory[i + 59] = null;
		}
		for (i = 54; i < 58; i++) {
			if (this.mInventory[i] != null) {
				if (!this.getWorld().isRemote)
					this.getWorld().spawnEntityInWorld((Entity) new EntityItem(this.getWorld(), this.getXCoord() + 0.5D, this.getYCoord() + 0.5D, this.getZCoord() + 0.5D, this.mInventory[i]));
				this.mInventory[i] = null;
			}
		}
	}

	public void switchModeForward() {
		this.mMode = (this.mMode + 1) % 7;
		switchMode();
	}

	public void switchModeBackward() {
		this.mMode--;
		if (this.mMode < 0)
			this.mMode = 6;
		switchMode();
	}

	private void switchMode() {
		reset();
		if (this.mMode == 1 && !mReactorplanner) {
			switchMode();
			return;
		}
		if (this.mMode == 2 && !mSeedscanner) {
			switchMode();
			return;
		}
		if (this.mMode == 3) {
			showCentrifugeRecipe(0);
		}
		if (this.mMode == 4) {
			showFusionRecipe(0);
		}
		if (this.mMode == 5) {
			showDescription(0);
		}
		if (this.mMode == 6) {
			showElectrolyzerRecipe(0);
		}
		//this.getWorld().addBlockEvent(this.getXCoord(), this.getYCoord(), this.getZCoord(), (GregTech_API.sBlockList[1]), 10, this.mMode);
		//this.getWorld().addBlockEvent(this.getXCoord(), this.getYCoord(), this.getZCoord(), (GregTech_API.sBlockList[1]), 11, this.mMaxHeat);
	}

	public void showDescription(int aIndex) {
		this.mExplosionStrength = 0.0F;
		if (GT_ComputercubeDescription.sDescriptions.isEmpty()) {
			return;
		}
		if (aIndex >= GT_ComputercubeDescription.sDescriptions.size() || aIndex < 0)
			aIndex = 0;
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[0] == null) {
			this.mInventory[59] = null;
		}
		else {
			this.mInventory[59] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[0].copy();
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[1] == null) {
			this.mInventory[60] = null;
		}
		else {
			this.mInventory[60] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[1].copy();
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[2] == null) {
			this.mInventory[61] = null;
		}
		else {
			this.mInventory[61] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[2].copy();
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[3] == null) {
			this.mInventory[62] = null;
		}
		else {
			this.mInventory[62] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[3].copy();
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[4] == null) {
			this.mInventory[63] = null;
		}
		else {
			this.mInventory[63] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[4].copy();
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[5] == null) {
			this.mInventory[64] = null;
		}
		else {
			this.mInventory[64] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[5].copy();
			this.mExplosionStrength = 100.0F;
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[6] == null) {
			this.mInventory[65] = null;
		}
		else {
			this.mInventory[65] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[6].copy();
			this.mExplosionStrength = 100.0F;
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[7] == null) {
			this.mInventory[66] = null;
		}
		else {
			this.mInventory[66] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[7].copy();
			this.mExplosionStrength = 100.0F;
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[8] == null) {
			this.mInventory[67] = null;
		}
		else {
			this.mInventory[67] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[8].copy();
			this.mExplosionStrength = 100.0F;
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[9] == null) {
			this.mInventory[68] = null;
		}
		else {
			this.mInventory[68] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[9].copy();
			this.mExplosionStrength = 100.0F;
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[10] == null) {
			this.mInventory[69] = null;
		}
		else {
			this.mInventory[69] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[10].copy();
			this.mExplosionStrength = 100.0F;
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[11] == null) {
			this.mInventory[70] = null;
		}
		else {
			this.mInventory[70] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[11].copy();
			this.mExplosionStrength = 100.0F;
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[12] == null) {
			this.mInventory[71] = null;
		}
		else {
			this.mInventory[71] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[12].copy();
			this.mExplosionStrength = 100.0F;
		}
		if (((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[13] == null) {
			this.mInventory[72] = null;
		}
		else {
			this.mInventory[72] = ((GT_ComputercubeDescription) GT_ComputercubeDescription.sDescriptions.get(aIndex)).mStacks[13].copy();
			this.mExplosionStrength = 100.0F;
		}
		this.mMaxHeat = aIndex;
		//this.getWorld().addBlockEvent(this.getXCoord(), this.getYCoord(), this.getZCoord(), GregTech_API.sBlockList[1], 11, this.mMaxHeat);
	}

	public void switchDescriptionPageForward() {
		if (++this.mMaxHeat >= GT_ComputercubeDescription.sDescriptions.size())
			this.mMaxHeat = 0;
		showDescription(this.mMaxHeat);
	}

	public void switchDescriptionPageBackward() {
		if (--this.mMaxHeat < 0)
			this.mMaxHeat = GT_ComputercubeDescription.sDescriptions.size() - 1;
		showDescription(this.mMaxHeat);
	}

	public void showCentrifugeRecipe(int aIndex) {
		/*
		if (aIndex >= GT_Recipe_Map.sCentrifugeRecipes.mRecipeList.size() || aIndex < 0)
		aIndex = 0;
		GT_Recipe tRecipe = GT_Recipe_Map.sCentrifugeRecipes.mRecipeList.get(aIndex);
		if (tRecipe != null) {
		if (tRecipe.mInput1 == null) {
		this.mInventory[59] = null;
		}
		else {
		this.mInventory[59] = tRecipe.mInput1.copy();
		}
		if (tRecipe.mInput2 == null) {
		this.mInventory[60] = null;
		}
		else {
		this.mInventory[60] = tRecipe.mInput2.copy();
		}
		if (tRecipe.mOutput1 == null) {
		this.mInventory[61] = null;
		}
		else {
		this.mInventory[61] = tRecipe.mOutput1.copy();
		}
		if (tRecipe.mOutput2 == null) {
		this.mInventory[62] = null;
		}
		else {
		this.mInventory[62] = tRecipe.mOutput2.copy();
		}
		if (tRecipe.mOutput3 == null) {
		this.mInventory[63] = null;
		}
		else {
		this.mInventory[63] = tRecipe.mOutput3.copy();
		}
		if (tRecipe.mOutput4 == null) {
		this.mInventory[64] = null;
		}
		else {
		this.mInventory[64] = tRecipe.mOutput4.copy();
		}
		this.mEU = tRecipe.mDuration * 5;
		this.mMaxHeat = aIndex;
		}
		this.getWorld().addBlockEvent(this.xCoord, this.yCoord, this.zCoord, (GregTech_API.sBlockList[1]).field_71990_ca, 11, this.mMaxHeat);
		 */}

	public void switchCentrifugePageForward() {
		if (++this.mMaxHeat >= GT_Recipe_Map.sCentrifugeRecipes.mRecipeList.size())
			this.mMaxHeat = 0;
		// showCentrifugeRecipe(this.mMaxHeat);
	}

	public void switchCentrifugePageBackward() {
		if (--this.mMaxHeat < 0)
			this.mMaxHeat = GT_Recipe_Map.sCentrifugeRecipes.mRecipeList.size() - 1;
		// showCentrifugeRecipe(this.mMaxHeat);
	}

	public void showElectrolyzerRecipe(int aIndex) {
		/*
		if (aIndex >= GT_Recipe_Map.sElectrolyzerRecipes.mRecipeList.size() || aIndex < 0)
		aIndex = 0;
		GT_Recipe tRecipe = GT_Recipe_Map.sElectrolyzerRecipes.get(aIndex);
		if (tRecipe != null) {
		if (tRecipe.mInput1 == null) {
		this.mInventory[59] = null;
		}
		else {
		this.mInventory[59] = tRecipe.mInput1.copy();
		}
		if (tRecipe.mInput2 == null) {
		this.mInventory[60] = null;
		}
		else {
		this.mInventory[60] = tRecipe.mInput2.copy();
		}
		if (tRecipe.mOutput1 == null) {
		this.mInventory[61] = null;
		}
		else {
		this.mInventory[61] = tRecipe.mOutput1.copy();
		}
		if (tRecipe.mOutput2 == null) {
		this.mInventory[62] = null;
		}
		else {
		this.mInventory[62] = tRecipe.mOutput2.copy();
		}
		if (tRecipe.mOutput3 == null) {
		this.mInventory[63] = null;
		}
		else {
		this.mInventory[63] = tRecipe.mOutput3.copy();
		}
		if (tRecipe.mOutput4 == null) {
		this.mInventory[64] = null;
		}
		else {
		this.mInventory[64] = tRecipe.mOutput4.copy();
		}
		this.mEU = tRecipe.mDuration * tRecipe.mEUt;
		this.mMaxHeat = aIndex;
		}
		this.getWorld().addBlockEvent(this.xCoord, this.yCoord, this.zCoord, (GregTech_API.sBlockList[1]).field_71990_ca, 11, this.mMaxHeat);
		 */}

	public void switchElectrolyzerPageForward() {
		if (++this.mMaxHeat >= GT_Recipe_Map.sElectrolyzerRecipes.mRecipeList.size())
			this.mMaxHeat = 0;
		showElectrolyzerRecipe(this.mMaxHeat);
	}

	public void switchElectrolyzerPageBackward() {
		if (--this.mMaxHeat < 0)
			this.mMaxHeat = GT_Recipe_Map.sElectrolyzerRecipes.mRecipeList.size() - 1;
		showElectrolyzerRecipe(this.mMaxHeat);
	}

	public static ArrayList<GT_Recipe> sFusionReactorRecipes = new ArrayList<GT_Recipe>();

	public void showFusionRecipe(int aIndex) {

		if (sFusionReactorRecipes.isEmpty()) {
			for (GT_Recipe aRecipe : GT_Recipe_Map.sFusionRecipes.mRecipeList) {
				sFusionReactorRecipes.add(aRecipe);
			}
			Collections.sort(sFusionReactorRecipes);
		}

		if (aIndex >= sFusionReactorRecipes.size() || aIndex < 0) {
			aIndex = 0;
		}
		GT_Recipe tRecipe = sFusionReactorRecipes.get(aIndex);
		if (tRecipe != null) {
			if (tRecipe.mFluidInputs[0] == null) {
				this.mInventory[59] = null;
			}
			else {
				this.mInventory[59] = GT_Utility.getFluidDisplayStack(tRecipe.mFluidInputs[0], true);
			}
			if (tRecipe.mFluidInputs[1] == null) {
				this.mInventory[60] = null;
			}
			else {
				this.mInventory[60] = GT_Utility.getFluidDisplayStack(tRecipe.mFluidInputs[1], true);
			}
			if (tRecipe.mFluidOutputs[0] == null) {
				this.mInventory[61] = null;
			}
			else {
				this.mInventory[61] = GT_Utility.getFluidDisplayStack(tRecipe.mFluidOutputs[0], true);
			}
			this.mEU = tRecipe.mSpecialValue;
			this.mEUOut = tRecipe.mEUt;
			this.mHeat = tRecipe.mDuration;
			this.mMaxHeat = aIndex;
		}
		//this.getWorld().addBlockEvent(this.getXCoord(), this.getYCoord(), this.getZCoord(), GregTech_API.sBlockList[1], 11, this.mMaxHeat);
	}

	public void switchFusionPageForward() {
		if (++this.mMaxHeat >= sFusionReactorRecipes.size())
			this.mMaxHeat = 0;
		showFusionRecipe(this.mMaxHeat);
	}

	public void switchFusionPageBackward() {
		if (--this.mMaxHeat < 0)
			this.mMaxHeat = sFusionReactorRecipes.size() - 1;
		showFusionRecipe(this.mMaxHeat);
	}

	public void switchNuclearReactor() {
		if (this.mStarted) {
			stopNuclearReactor();
		}
		else {
			startNuclearReactor();
		}
	}

	public void startNuclearReactor() {
		this.mStarted = true;
		this.mHeat = 0;
		this.mEU = 0;
	}

	public void stopNuclearReactor() {
		this.mStarted = false;
	}

	public void storeAdditionalData(NBTTagCompound aNBT) {
		aNBT.setInteger("mMode", this.mMode);
		aNBT.setInteger("mProgress", this.mProgress);
		aNBT.setBoolean("mStarted", this.mStarted);
		aNBT.setInteger("mEU", this.mEU);
		aNBT.setInteger("mHeat", this.mHeat);
		aNBT.setInteger("mEUOut", this.mEUOut);
		aNBT.setInteger("mMaxHeat", this.mMaxHeat);
		aNBT.setFloat("mHEM", this.mHEM);
		aNBT.setFloat("mExplosionStrength", this.mExplosionStrength);
	}

	public void getAdditionalData(NBTTagCompound aNBT) {
		this.mMode = aNBT.getInteger("mMode");
		this.mProgress = aNBT.getInteger("mProgress");
		this.mStarted = aNBT.getBoolean("mStarted");
		this.mEU = aNBT.getInteger("mEU");
		this.mHeat = aNBT.getInteger("mHeat");
		this.mEUOut = aNBT.getInteger("mEUOut");
		this.mMaxHeat = aNBT.getInteger("mMaxHeat");
		this.mHEM = aNBT.getFloat("mHEM");
		this.mExplosionStrength = aNBT.getFloat("mExplosionStrength");
	}

	public void onFirstTickUpdate() {
	}

	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
		if (sReactorList == null) {
			sReactorList = new ArrayList<GT_ItemStack>();

			String[] aIc2Items = new String[]{
					"reactorUraniumSimple", "reactorUraniumDual", "reactorUraniumQuad", /*"reactorIsotopeCell",*/ "reactorReflector", "reactorReflectorThick", "reactorCoolantSimple",
					"reactorCoolantTriple", "reactorCoolantSix", "reactorCondensator", "reactorCondensatorLap", "reactorPlating", "reactorPlatingHeat", "reactorPlatingExplosive", "reactorVent",
					"reactorVentCore", "reactorVentGold", "reactorVentSpread", "reactorVentDiamond", "reactorHeatSwitch", "reactorHeatSwitchCore", "reactorHeatSwitchSpread",
					"reactorHeatSwitchDiamond", /*"reactorHeatpack",*/
			};

			for (String aItem : aIc2Items) {
				ItemStack aStack = GT_ModHandler.getIC2Item(aItem, 1);
				if (!ItemUtils.checkForInvalidItems(aStack)) {
					Logger.INFO("Unable to find IC2 Item: " + aItem);
					CORE.crash("Unable to find IC2 Item: " + aItem);
				}
				else {
					sReactorList.add(new GT_ItemStack(aStack.copy()));
				}
			}

			ItemList[] aGtItems = new ItemList[]{
					ItemList.Neutron_Reflector, ItemList.Moxcell_1, ItemList.Moxcell_2, ItemList.Moxcell_4, ItemList.Uraniumcell_1, ItemList.Uraniumcell_2, ItemList.Uraniumcell_4,
					ItemList.NaquadahCell_1, ItemList.NaquadahCell_2, ItemList.NaquadahCell_4, ItemList.ThoriumCell_1, ItemList.ThoriumCell_2, ItemList.ThoriumCell_4, ItemList.Reactor_Coolant_He_1,
					ItemList.Reactor_Coolant_He_3, ItemList.Reactor_Coolant_He_6, ItemList.Reactor_Coolant_NaK_1, ItemList.Reactor_Coolant_NaK_3, ItemList.Reactor_Coolant_NaK_6,
			};

			for (ItemList aItem : aGtItems) {
				sReactorList.add(new GT_ItemStack(aItem.get(1)));
			}

		}

	}

	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if(this.getBaseMetaTileEntity().isClientSide()) {
			this.getWorld().markBlockForUpdate(this.getXCoord(), this.getYCoord(), this.getZCoord());
			this.mNeedsUpdate = false;
		}
		else {
			this.mNeedsUpdate = false;
		}
		if (this.getBaseMetaTileEntity().isServerSide()) {
			if (this.mMode == 2) {
				if (this.mInventory[55] == null) {
					this.mInventory[55] = this.mInventory[54];
					this.mInventory[54] = null;
				}
				if (this.mInventory[57] == null) {
					this.mInventory[57] = this.mInventory[56];
					this.mInventory[56] = null;
				}

				if (mSeedscanner && this.mInventory[55] != null && GT_Utility.areStacksEqual(this.mInventory[55], Ic2Items.cropSeed, true) && this.mInventory[55].getTagCompound() != null) {
					if (this.mInventory[55].getTagCompound().getByte("scan") < 4) {
						if (this.mProgress >= 100) {
							this.mInventory[55].getTagCompound().setByte("scan", (byte) 4);
							this.mProgress = 0;
						}
						else if (this.getBaseMetaTileEntity().decreaseStoredEnergyUnits(100, false)) {
							this.mProgress++;
						}
					}
					else {
						this.mProgress = 0;
						if (this.mInventory[56] == null) {
							this.mInventory[56] = this.mInventory[55];
							this.mInventory[55] = null;
						}
					}
				}
				else {
					this.mProgress = 0;
					if (this.mInventory[56] == null) {
						this.mInventory[56] = this.mInventory[55];
						this.mInventory[55] = null;
					}
				}
			}
			if (this.mMode == 1 && mReactorplanner && this.mStarted && this.getBaseMetaTileEntity().decreaseStoredEnergyUnits(32, false))
				for (int i = 0; i < 25 && this.mStarted; i++) {
					this.mEUOut = 0;
					this.mMaxHeat = 10000;
					this.mHEM = 1.0F;
					this.mExplosionStrength = 10.0F;
					float tMultiplier = 1.0F;
					for (int y = 0; y < 6; y++) {
						for (int x = 0; x < 9; x++) {
							ItemStack tStack = getStackInSlot(x + y * 9);
							if (tStack != null)
								if (tStack.getItem() instanceof IReactorComponent) {
									IReactorComponent tComponent = (IReactorComponent) tStack.getItem();
									tComponent.processChamber(this, tStack, x, y, false); //TODO
									float tInfluence = ((IReactorComponent) tStack.getItem()).influenceExplosion(this, tStack);
									if (tInfluence > 0.0F && tInfluence < 1.0F) {
										tMultiplier *= tInfluence;
									}
									else {
										this.mExplosionStrength += tInfluence;
									}
								}
								else if (tStack.isItemEqual(GT_ModHandler.getIC2Item("nearDepletedUraniumCell", 1)) || tStack.isItemEqual(GT_ModHandler.getIC2Item("reEnrichedUraniumCell", 1))) {
									stopNuclearReactor();
								}
								else {
									setInventorySlotContents(x + y * 9, (ItemStack) null);
								}
						}
					}
					this.mEUOut *= getReactorEUOutput();
					if ((this.mEUOut == 0 && this.mEUTimer++ > 20) || this.mHeat >= this.mMaxHeat)
						stopNuclearReactor();
					if (this.mEUOut != 0)
						this.mEUTimer = 0;
					this.mExplosionStrength *= this.mHEM * tMultiplier;
					this.mEU += this.mEUOut * 20;
					int tEU = this.mEULast1;
					this.mEULast1 = this.mEULast2;
					this.mEULast2 = this.mEULast3;
					this.mEULast3 = this.mEULast4;
					this.mEULast4 = this.mEUOut;
					this.mEUOut = (this.mEUOut + this.mEULast1 + this.mEULast2 + this.mEULast3 + tEU) / 5;
				}
			if (aTick % 20L == 0L) {
				//this.getWorld().addBlockEvent(this.xCoord, this.yCoord, this.zCoord, (GregTech_API.sBlockList[1]).field_71990_ca, 10, this.mMode);
				//this.getWorld().addBlockEvent(this.xCoord, this.yCoord, this.zCoord, (GregTech_API.sBlockList[1]).field_71990_ca, 11, this.mMaxHeat);
			}
		}
	}

	private int getReactorEUOutput() {
		return MathUtils.roundToClosestInt(getReactorEnergyOutput() * 5.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/nuclear"));
	}

	@Override
	public void receiveClientEvent(byte aEventID, byte aValue) {
		super.receiveClientEvent(aEventID, aValue);
		if (this.getWorld().isRemote)
			switch (aEventID) {
				case 10 :
					this.mNeedsUpdate = true;
					this.mMode = aValue;
					break;
				case 11 :
					this.mMaxHeat = aValue;
					break;
			}
		return;
	}

	@Override
	public void onValueUpdate(byte aValue) {
		super.onValueUpdate(aValue);
		this.mNeedsUpdate = true;
	}

	@Override
	public void onMachineBlockUpdate() {
		super.onMachineBlockUpdate();
		this.mNeedsUpdate = true;
	}

	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return (this.mMode == 2) ? ((i == 54 || i == 55)) : false;
	}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return (this.mMode == 2) ? ((i == 56 || i == 57)) : false;
	}

	public String getInvName() {
		return "GregTech_Computercube";
	}

	public int getTexture(int aSide, int aMeta) {
		switch (this.mMode) {
			case 0 :
				return 8;
			case 1 :
				return 46;
			case 2 :
				return 45;
		}
		return 48;
	}

	@Override
	public ChunkCoordinates getPosition() {
		return new ChunkCoordinates(this.getBaseMetaTileEntity().getXCoord(), this.getBaseMetaTileEntity().getYCoord(), this.getBaseMetaTileEntity().getZCoord());
	}

	@Override
	public int getHeat() {
		return this.mHeat;
	}

	@Override
	public void setHeat(int aHeat) {
		this.mHeat = aHeat;
	}

	@Override
	public int addHeat(int aAmount) {
		this.mHeat += aAmount;
		return this.mHeat;
	}

	@Override
	public int getMaxHeat() {
		return this.mMaxHeat;
	}

	@Override
	public void setMaxHeat(int aMaxHeat) {
		this.mMaxHeat = aMaxHeat;
	}

	@Override
	public float getHeatEffectModifier() {
		return this.mHEM;
	}

	@Override
	public void setHeatEffectModifier(float aHEM) {
		this.mHEM = aHEM;
	}


	public int addOutput(int aEnergy) {
		this.mEUOut += aEnergy;
		return this.mEUOut;
	}

	@Override
	public ItemStack getItemAt(int x, int y) {
		if (x < 0 || x > 8 || y < 0 || y > 5)
			return null;
		return getStackInSlot(x + y * 9);
	}

	@Override
	public void setItemAt(int x, int y, ItemStack aStack) {
		setInventorySlotContents(x + y * 9, aStack);
	}

	@Override
	public void explode() {
		stopNuclearReactor();
	}

	@Override
	public int getTickRate() {
		return 1;
	}

	@Override
	public boolean produceEnergy() {
		return true;
	}

	public int getProgress() {
		return this.mProgress;
	}

	public int getMaxProgress() {
		return (this.mProgress > 0) ? 100 : 0;
	}

	@Override
	public float addOutput(float aEnergy) {
		this.mEUOut = (int) (this.mEUOut + aEnergy);
		return this.mEUOut;
	}

	@Override
	public World getWorld() {
		return this.getBaseMetaTileEntity().getWorld();
	}

	@Override
	public void addEmitHeat(int heat) {

	}

	@Override
	public float getReactorEnergyOutput() {
		return this.mEUOut;
	}

	@Override
	public double getReactorEUEnergyOutput() {
		return 0;
	}

	@Override
	public void setRedstoneSignal(boolean redstone) {

	}

	@Override
	public boolean isFluidCooled() {
		return false;
	}



	@Override
	public boolean doesFillContainers() {
		return false;
	}

	@Override
	public boolean doesEmptyContainers() {
		return false;
	}

	@Override
	public boolean canTankBeFilled() {
		return false;
	}

	@Override
	public boolean canTankBeEmptied() {
		return false;
	}

	@Override
	public boolean displaysItemStack() {
		return false;
	}

	@Override
	public boolean displaysStackSize() {
		return false;
	}

	@Override
	public ITexture[][][] getTextureSet(final ITexture[] aTextures) {
		final ITexture[][][] rTextures = new ITexture[10][17][];
		for (byte i = -1; i < 16; i++) {
			rTextures[0][i + 1] = this.getFront(i);
			rTextures[1][i + 1] = this.getSides(i);
			rTextures[2][i + 1] = this.getSides(i);
			rTextures[3][i + 1] = this.getSides(i);
			rTextures[4][i + 1] = this.getSides(i);
			rTextures[5][i + 1] = this.getFront(i);
			rTextures[6][i + 1] = this.getSides(i);
			rTextures[7][i + 1] = this.getSides(i);
			rTextures[8][i + 1] = this.getSides(i);
			rTextures[9][i + 1] = this.getSides(i);
		}
		return rTextures;
	}

	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
			final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return this.mTextures[(aSide == aFacing ? 0	: aSide == GT_Utility.getOppositeSide(aFacing) ? 1 : aSide == 0 ? 2 : aSide == 1 ? 3 : 4)][aColorIndex + 1];
	}

	public ITexture[] getFront(final byte aColor) {
		return new ITexture[] { new GT_RenderedTexture(TexturesGtBlock.Casing_Machine_Screen_3)};
	}

	public ITexture[] getSides(final byte aColor) {
		return new ITexture[] { new GT_RenderedTexture(TexturesGtBlock.Casing_Computer_Cube)};
	}

}