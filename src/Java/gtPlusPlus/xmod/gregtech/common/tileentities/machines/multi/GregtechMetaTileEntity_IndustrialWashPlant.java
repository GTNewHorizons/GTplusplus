package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.util.Utils;
import gtPlusPlus.core.util.fluid.FluidUtils;
import gtPlusPlus.xmod.gregtech.api.gui.GUI_MultiMachine;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class GregtechMetaTileEntity_IndustrialWashPlant
extends GregtechMeta_MultiBlockBase {
	public GregtechMetaTileEntity_IndustrialWashPlant(final int aID, final String aName, final String aNameRegional) {
		super(aID, aName, aNameRegional);
	}

	public GregtechMetaTileEntity_IndustrialWashPlant(final String aName) {
		super(aName);
	}

	@Override
	public IMetaTileEntity newMetaEntity(final IGregTechTileEntity aTileEntity) {
		return new GregtechMetaTileEntity_IndustrialWashPlant(this.mName);
	}

	@Override
	public String[] getDescription() {
		return new String[]{
				"Controller Block for the Industrial Ore Washing Plant",
				"60% faster than using single block machines of the same voltage",
				"Size: 7x2x5 [WxHxL] (open)",
				"Controller (front centered)",
				"1x Input Bus (Any casing)",
				"1x Output Bus (Any casing)",
				"1x Maintenance Hatch (Any casing)",
				"1x Energy Hatch (Any casing)",
				CORE.GT_Tooltip

		};
	}

	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[getCasingTextureIndex()], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER)};
		}
		return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[getCasingTextureIndex()]};
	}

	@Override
	public Object getClientGUI(final int aID, final InventoryPlayer aPlayerInventory, final IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, this.getLocalName(), "WireFactory.png");
	}

	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_Recipe.GT_Recipe_Map.sOreWasherRecipes;
	}

	@Override
	public boolean isFacingValid(final byte aFacing) {
		return aFacing > 1;
	}

	@Override
	public boolean checkRecipe(final ItemStack aStack) {

		if (!checkForWater()){
			Utils.LOG_INFO("Did not find enough cleaning solution.");
			return false;
		}
		final ArrayList<ItemStack> tInputList = this.getStoredInputs();
		for (int i = 0; i < (tInputList.size() - 1); i++) {
			for (int j = i + 1; j < tInputList.size(); j++) {
				if (GT_Utility.areStacksEqual(tInputList.get(i), tInputList.get(j))) {
					if (tInputList.get(i).stackSize >= tInputList.get(j).stackSize) {
						tInputList.remove(j--);
					} else {
						tInputList.remove(i--);
						break;
					}
				}
			}
		}
		final ItemStack[] tInputs = Arrays.copyOfRange(tInputList.toArray(new ItemStack[tInputList.size()]), 0, 2);

		final ArrayList<FluidStack> tFluidList = this.getStoredFluids();
		for (int i = 0; i < (tFluidList.size() - 1); i++) {
			for (int j = i + 1; j < tFluidList.size(); j++) {
				if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
					if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
						tFluidList.remove(j--);
					} else {
						tFluidList.remove(i--);
						break;
					}
				}
			}
		}
		final FluidStack[] tFluids = Arrays.copyOfRange(tFluidList.toArray(new FluidStack[tInputList.size()]), 0, 1);

		Utils.LOG_INFO("0");
		final int tValidOutputSlots = this.getValidOutputSlots(this.getBaseMetaTileEntity(), GT_Recipe.GT_Recipe_Map.sOreWasherRecipes.findRecipe(this.getBaseMetaTileEntity(), false, gregtech.api.enums.GT_Values.V[(byte) Math.max(1, GT_Utility.getTier(this.getMaxInputVoltage()))], tFluids, tInputs), tInputs);
		Utils.LOG_INFO("Valid Output Slots: "+tValidOutputSlots);

		//More than or one input
		if ((tInputList.size() > 0) && (tValidOutputSlots >= 1)) {
			Utils.LOG_INFO("1");
			final long tVoltage = this.getMaxInputVoltage();
			final byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
			GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sOreWasherRecipes.findRecipe(this.getBaseMetaTileEntity(), false, gregtech.api.enums.GT_Values.V[tTier], tFluids, tInputs);
			tRecipe = this.reduceRecipeTimeByPercentage(tRecipe, 40F);
			if ((tRecipe != null) && (7500 >= tRecipe.mSpecialValue) && (tRecipe.isRecipeInputEqual(true, tFluids, tInputs))) {
				this.mEfficiency = (10000 - ((this.getIdealStatus() - this.getRepairStatus()) * 1000));
				this.mEfficiencyIncrease = 10000;
				if (tRecipe.mEUt <= 16) {
					this.mEUt = (tRecipe.mEUt * (1 << (tTier - 1)) * (1 << (tTier - 1)));
					this.mMaxProgresstime = (tRecipe.mDuration / (1 << (tTier - 1)));
				} else {
					this.mEUt = tRecipe.mEUt;
					this.mMaxProgresstime = tRecipe.mDuration;
					while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)]) {
						this.mEUt *= 4;
						this.mMaxProgresstime /= 2;
					}
				}
				if (this.mEUt > 0) {
					this.mEUt = (-this.mEUt);
				}
				this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);

				ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];
				for (int h = 0; h < tRecipe.mOutputs.length; h++) {
					tOut[h] = tRecipe.getOutput(h).copy();
					tOut[h].stackSize = 0;
				}
				FluidStack tFOut = null;
				if (tRecipe.getFluidOutput(0) != null) {
					tFOut = tRecipe.getFluidOutput(0).copy();
				}
				for (int f = 0; f < tOut.length; f++) {
					if ((tRecipe.mOutputs[f] != null) && (tOut[f] != null)) {
						for (int g = 0; g < 1; g++) {
							if (this.getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe.getOutputChance(f)) {
								tOut[f].stackSize += tRecipe.mOutputs[f].stackSize;
							}
						}
					}
				}
				if (tFOut != null) {
					final int tSize = tFOut.amount;
					tFOut.amount = tSize * 1;
				}

				final List<ItemStack> overStacks = new ArrayList<>();
				for (int f = 0; f < tOut.length; f++) {
					if (tOut[f].getMaxStackSize() < tOut[f].stackSize) {
						while (tOut[f].getMaxStackSize() < tOut[f].stackSize) {
							final ItemStack tmp = tOut[f].copy();
							tmp.stackSize = tmp.getMaxStackSize();
							tOut[f].stackSize = tOut[f].stackSize - tOut[f].getMaxStackSize();
							overStacks.add(tmp);
						}
					}
				}
				if (overStacks.size() > 0) {
					ItemStack[] tmp = new ItemStack[overStacks.size()];
					tmp = overStacks.toArray(tmp);
					tOut = ArrayUtils.addAll(tOut, tmp);
				}
				final List<ItemStack> tSList = new ArrayList<>();
				for (final ItemStack tS : tOut) {
					if (tS.stackSize > 0) {
						tSList.add(tS);
					}
				}
				tOut = tSList.toArray(new ItemStack[tSList.size()]);
				this.mOutputItems = tOut;
				this.mOutputFluids = new FluidStack[]{tFOut};
				this.updateSlots();

				/* this.mOutputItems = new ItemStack[]{tRecipe.getOutput(0), tRecipe.getOutput(1)};
                updateSlots();*/
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkMachine(final IGregTechTileEntity aBaseMetaTileEntity, final ItemStack aStack) {

		//Get Facing direction
		int mDirectionX  = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;

		int mCurrentDirectionX;
		int mCurrentDirectionZ;
		int mOffsetX_Lower = 0;
		int mOffsetX_Upper = 0;
		int mOffsetZ_Lower = 0;
		int mOffsetZ_Upper = 0;


		Utils.LOG_WARNING("mDirectionX "+(mDirectionX));
		if (mDirectionX == 0){
			mCurrentDirectionX = 2;
			mCurrentDirectionZ = 3;
			mOffsetX_Lower = -2;
			mOffsetX_Upper = 2;
			mOffsetZ_Lower = -3;
			mOffsetZ_Upper = 3;
			Utils.LOG_WARNING("Controler is facing Z direction.");
		}
		else {
			mCurrentDirectionX = 3;
			mCurrentDirectionZ = 2;	
			mOffsetX_Lower = -3;
			mOffsetX_Upper = 3;
			mOffsetZ_Lower = -2;
			mOffsetZ_Upper = 2;	
			Utils.LOG_WARNING("Controler is facing X direction.");	
		}

		//if (aBaseMetaTileEntity.fac)

		final int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX * mCurrentDirectionX;
		final int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ * mCurrentDirectionZ;

		Utils.LOG_WARNING("xDir"+(xDir));
		Utils.LOG_WARNING("zDir"+(zDir)); 
		/*if (!(aBaseMetaTileEntity.getAirOffset(xDir, 0, zDir))) {
			return false;
		}*/
		int tAmount = 0;
		for (int i = mOffsetX_Lower; i <=mOffsetX_Upper; ++i) {
			for (int j = mOffsetZ_Lower; j <= mOffsetZ_Upper; ++j) {
				for (int h = -1; h < 2; ++h) {
					if ((h != 0) || ((((xDir + i != 0) || (zDir + j != 0))) && (((i != 0) || (j != 0))))) {
						IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h,
								zDir + j);
						if (!addToMachineList(tTileEntity)) {
							Utils.LOG_WARNING("X: "+i+" | Z: "+j);
							Block tBlock = aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j);
							byte tMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j);
							if ((tBlock != getCasingBlock()) && (tMeta != getCasingMeta())) {
								if ((i != mOffsetX_Lower && j !=  mOffsetZ_Lower
										&& i != mOffsetX_Upper && j != mOffsetZ_Upper) && (h == 0 || h == 1)){
									if (tBlock == Blocks.air){
										Utils.LOG_WARNING("Found Air");
									}
									else if (tBlock == Blocks.water){
										Utils.LOG_WARNING("Found Water");
									}
								}
								else {
									Utils.LOG_WARNING("[x] Did not form - Found: "+tBlock.getLocalizedName() + " | "+tBlock.getDamageValue(aBaseMetaTileEntity.getWorld(), aBaseMetaTileEntity.getXCoord()+ i, aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord() + j));
									Utils.LOG_WARNING("[x] Did not form - Found: "+(aBaseMetaTileEntity.getXCoord()+xDir + i) +" | "+ aBaseMetaTileEntity.getYCoord()+" | "+ (aBaseMetaTileEntity.getZCoord()+zDir + j));
									return false;
								}

							}
							++tAmount;
						}
					}
				}
			}
		}
		if ((tAmount >= 8)){
			Utils.LOG_WARNING("Made structure.");
		}
		else {
			Utils.LOG_WARNING("Did not make structure.");
		}
		return (tAmount >= 8);
	}

	@Override
	public int getMaxEfficiency(final ItemStack aStack) {
		return 10000;
	}

	@Override
	public int getPollutionPerTick(final ItemStack aStack) {
		return 0;
	}

	@Override
	public int getAmountOfOutputs() {
		return 1;
	}

	@Override
	public boolean explodesOnComponentBreak(final ItemStack aStack) {
		return false;
	}

	public Block getCasingBlock() {
		return ModBlocks.blockCasings2Misc;
	}


	public byte getCasingMeta() {
		return 4;
	}


	public byte getCasingTextureIndex() {
		return (byte) TAE.GTPP_INDEX(20);
	}

	private boolean addToMachineList(final IGregTechTileEntity tTileEntity) {
		return ((this.addMaintenanceToMachineList(tTileEntity, this.getCasingTextureIndex())) 
				|| (this.addInputToMachineList(tTileEntity, this.getCasingTextureIndex())) 
				|| (this.addOutputToMachineList(tTileEntity, this.getCasingTextureIndex())) 
				|| (this.addMufflerToMachineList(tTileEntity, this.getCasingTextureIndex()))
				|| (this.addEnergyInputToMachineList(tTileEntity, this.getCasingTextureIndex()))
				|| (this.addDynamoToMachineList(tTileEntity, this.getCasingTextureIndex())));
	}

	public boolean checkForWater() {

		//Get Facing direction
		IGregTechTileEntity aBaseMetaTileEntity = this.getBaseMetaTileEntity();
		int mDirectionX  = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
		int mCurrentDirectionX;
		int mCurrentDirectionZ;
		int mOffsetX_Lower = 0;
		int mOffsetX_Upper = 0;
		int mOffsetZ_Lower = 0;
		int mOffsetZ_Upper = 0;

		if (mDirectionX == 0){
			mCurrentDirectionX = 2;
			mCurrentDirectionZ = 3;
			mOffsetX_Lower = -2;
			mOffsetX_Upper = 2;
			mOffsetZ_Lower = -3;
			mOffsetZ_Upper = 3;
		}
		else {
			mCurrentDirectionX = 3;
			mCurrentDirectionZ = 2;	
			mOffsetX_Lower = -3;
			mOffsetX_Upper = 3;
			mOffsetZ_Lower = -2;
			mOffsetZ_Upper = 2;	
		}

		//if (aBaseMetaTileEntity.fac)

		final int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX * mCurrentDirectionX;
		final int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ * mCurrentDirectionZ;

		int tAmount = 0;
		for (int i = mOffsetX_Lower; i <=mOffsetX_Upper; ++i) {
			for (int j = mOffsetZ_Lower; j <= mOffsetZ_Upper; ++j) {
				for (int h = -1; h < 2; ++h) {
					if ((h != 0) || ((((xDir + i != 0) || (zDir + j != 0))) && (((i != 0) || (j != 0))))) {
						IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h,
								zDir + j);
						if (!addToMachineList(tTileEntity)) {
							Block tBlock = aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j);
							byte tMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j);
							if ((tBlock != getCasingBlock()) && (tMeta != getCasingMeta())) {
								if ((i != mOffsetX_Lower && j !=  mOffsetZ_Lower
										&& i != mOffsetX_Upper && j != mOffsetZ_Upper) && (h == 0 || h == 1)){
									if (tBlock == Blocks.air || tBlock == Blocks.flowing_water || tBlock == Blocks.water){
										if (this.getStoredFluids() != null){
											for (FluidStack stored : this.getStoredFluids()){
												if (stored.isFluidEqual(FluidUtils.getFluidStack("water", 1))){
													if (stored.amount >= 1000){
														//Utils.LOG_INFO("Going to try swap an air block for water from inut bus.");
														stored.amount -= 1000;
														Block fluidUsed = null;
														if (tBlock == Blocks.air || tBlock == Blocks.flowing_water){
															fluidUsed = Blocks.water;
														}
														if (tBlock == Blocks.water){
															fluidUsed = BlocksItems.getFluidBlock(InternalName.fluidDistilledWater);
														}
														aBaseMetaTileEntity.getWorld().setBlock(aBaseMetaTileEntity.getXCoord()+xDir + i, aBaseMetaTileEntity.getYCoord()+h, aBaseMetaTileEntity.getZCoord()+zDir + j, fluidUsed);



													}
												}
											}
										}
									}
									if (tBlock == Blocks.water){
										++tAmount;
										//Utils.LOG_INFO("Found Water");
									}
									else if (tBlock == BlocksItems.getFluidBlock(InternalName.fluidDistilledWater)){
										++tAmount;
										++tAmount;
										//Utils.LOG_INFO("Found Distilled Water");										
									}
								}
								else {
									//Utils.LOG_WARNING("[x] Did not form - Found: "+tBlock.getLocalizedName() + " | "+tBlock.getDamageValue(aBaseMetaTileEntity.getWorld(), aBaseMetaTileEntity.getXCoord()+ i, aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord() + j));
									//Utils.LOG_WARNING("[x] Did not form - Found: "+(aBaseMetaTileEntity.getXCoord()+xDir + i) +" | "+ aBaseMetaTileEntity.getYCoord()+" | "+ (aBaseMetaTileEntity.getZCoord()+zDir + j));
									return false;
								}

							}
						}
					}
				}
			}
		}
		if ((tAmount >= 45)){
			Utils.LOG_WARNING("Filled structure.");
		}
		else {
			Utils.LOG_WARNING("Did not fill structure.");
		}
		return (tAmount >= 45);
	}

}