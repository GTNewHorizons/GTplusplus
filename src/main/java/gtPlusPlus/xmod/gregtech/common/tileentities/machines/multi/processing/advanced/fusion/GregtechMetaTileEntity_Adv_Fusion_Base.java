package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing.advanced.fusion;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdderOptional;

import java.util.ArrayList;

import com.gtnewhorizon.structurelib.structure.*;

import gregtech.GT_Mod;
import gregtech.api.enums.*;
import gregtech.api.gui.GT_Container_MultiMachine;
import gregtech.api.interfaces.*;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.objects.*;
import gregtech.api.util.*;
import gregtech.common.gui.GT_GUIContainer_FusionReactor;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.util.math.MathUtils;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock.CustomIcon;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraftforge.fluids.FluidStack;

public abstract class GregtechMetaTileEntity_Adv_Fusion_Base extends GregtechMeta_MultiBlockBase<GregtechMetaTileEntity_Adv_Fusion_Base> {

	public static final String STRUCTURE_PIECE_MAIN = "main";
	private static final ClassValue<IStructureDefinition<GregtechMetaTileEntity_Adv_Fusion_Base>> STRUCTURE_DEFINITION = new ClassValue<IStructureDefinition<GregtechMetaTileEntity_Adv_Fusion_Base>>() {
		@Override
		protected IStructureDefinition<GregtechMetaTileEntity_Adv_Fusion_Base> computeValue(Class<?> type) {
			return StructureDefinition.<GregtechMetaTileEntity_Adv_Fusion_Base>builder()
					.addShape(STRUCTURE_PIECE_MAIN, transpose(new String[][]{
						{
							"               ",
							"      ihi      ",
							"    hh   hh    ",
							"   h       h   ",
							"  h         h  ",
							"  h         h  ",
							" i           i ",
							" h           h ",
							" i           i ",
							"  h         h  ",
							"  h         h  ",
							"   h       h   ",
							"    hh   hh    ",
							"      ihi      ",
							"               ",
						},
						{
							"      xhx      ",
							"    hhccchh    ",
							"   eccxhxcce   ",
							"  eceh   hece  ",
							" hce       ech ",
							" hch       hch ",
							"xcx         xcx",
							"hch         hch",
							"xcx         xcx",
							" hch       hch ",
							" hce       ech ",
							"  eceh   hece  ",
							"   eccx~xcce   ",
							"    hhccchh    ",
							"      xhx      ",
						},
						{
							"               ",
							"      ihi      ",
							"    hh   hh    ",
							"   h       h   ",
							"  h         h  ",
							"  h         h  ",
							" i           i ",
							" h           h ",
							" i           i ",
							"  h         h  ",
							"  h         h  ",
							"   h       h   ",
							"    hh   hh    ",
							"      ihi      ",
							"               ",
						}
					}))
					.addElement('c', lazy(t -> ofBlock(t.getFusionCoil(), t.getFusionCoilMeta())))
					.addElement('h', lazy(t -> ofBlock(t.getCasing(), t.getCasingMeta())))
					.addElement('i', lazy(t -> ofHatchAdderOptional(GregtechMetaTileEntity_Adv_Fusion_Base::addInjector, 53, 1, t.getCasing(), t.getCasingMeta())))
					.addElement('e', lazy(t -> ofHatchAdderOptional(GregtechMetaTileEntity_Adv_Fusion_Base::addEnergyInjector, 53, 2, t.getCasing(), t.getCasingMeta())))
					.addElement('x', lazy(t -> ofHatchAdderOptional(GregtechMetaTileEntity_Adv_Fusion_Base::addExtractor, 53, 3, t.getCasing(), t.getCasingMeta())))
					.build();
		}
	};

	public int mEUStore;

	public GregtechMetaTileEntity_Adv_Fusion_Base(int aID, String aName, String aNameRegional, int tier) {
		super(aID, aName, aNameRegional);
	}

	public GregtechMetaTileEntity_Adv_Fusion_Base(String aName) {
		super(aName);
	}

	public abstract String getMinimumHatchTier();

	public abstract String getCasingName();

	public abstract String getCoilName();

	public abstract String getTooltipText();

	public abstract int getFusionTier();

	public abstract long getEuPerTickInPerHatch();

	public abstract long getEuStoragePerHatch();

	@Override
	protected GT_Multiblock_Tooltip_Builder createTooltip() {
		GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
		tt.addMachineType("Fusion Reactor")
		.addInfo(getTooltipText())
		.addInfo("Controller block for the Fusion Reactor Mk "+getFusionTier())
		.addInfo("Speed: 100% | Eu Usage: 100% | Parallel: "+getMaxParallelRecipes())
		.addInfo(MathUtils.formatNumbers(getEuPerTickInPerHatch())+" EU/t per Energy Hatch")
		.addInfo(MathUtils.formatNumbers(getEuStoragePerHatch())+" EU capacity per Energy Hatch")
		.addInfo("If the recipe has a startup cost greater than the")
		.addInfo("number of energy hatches * cap, you can't do it")
		.addSeparator()
		.beginStructureBlock(15, 3, 15, false)
		.addController("See diagram when placed")
		.addCasingInfo(getCasingName(), 79)
		.addStructureInfo("Cover the coils with casing")
		.addOtherStructurePart(getCoilName(), "Center part of the ring")
		.addEnergyHatch("1-16, Specified casings", 2)
		.addInputHatch("2-16, Specified casings", 1)
		.addOutputHatch("1-16, Specified casings", 3)
		.addStructureInfo("ALL Hatches must be "+getMinimumHatchTier()+" or better")
		.toolTipFinisher(CORE.GT_Tooltip_Builder);
		return tt;
	}

	public abstract int tier();

	@Override
	public final long maxEUStore() {
		return getEuStoragePerHatch() * this.mEnergyHatches.size();
	}

	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_MultiMachine(aPlayerInventory, aBaseMetaTileEntity);
	}

	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_FusionReactor(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "FusionComputer.png", GT_Recipe.GT_Recipe_Map.sFusionRecipes.mNEIName);
	}

	@Override
	public boolean hasSlotInGUI() {
		return true;
	}

	@Override
	public String getCustomGUIResourceName() {
		return "FusionComputer";
	}

	@Override
	public boolean requiresVanillaGtGUI() {
		return true;
	}

	@Override
	public abstract MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity);

	@Override
	public boolean allowCoverOnSide(byte aSide, GT_ItemStack aStack) {

		return aSide != getBaseMetaTileEntity().getFrontFacing();
	}

	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
	}

	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
	}

	@Override
	public IStructureDefinition<GregtechMetaTileEntity_Adv_Fusion_Base> getStructureDefinition() {
		return STRUCTURE_DEFINITION.get(getClass());
	}

	@Override
	public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
		if (checkPiece(STRUCTURE_PIECE_MAIN, 7, 1, 12) && this.mInputHatches.size() > 1 && !this.mOutputHatches.isEmpty() && !this.mEnergyHatches.isEmpty()) {
			this.mWrench = true;
			this.mScrewdriver = true;
			this.mSoftHammer = true;
			this.mHardHammer = true;
			this.mSolderingTool = true;
			this.mCrowbar = true;
			return true;
		}
		return false;
	}

	private boolean addEnergyInjector(IGregTechTileEntity aBaseMetaTileEntity, int aBaseCasingIndex) {
		IMetaTileEntity aMetaTileEntity = aBaseMetaTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) return false;
		if (!(aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Energy)) return false;
		GT_MetaTileEntity_Hatch_Energy tHatch = (GT_MetaTileEntity_Hatch_Energy) aMetaTileEntity;
		if (tHatch.mTier < tier()) return false;
		tHatch.updateTexture(aBaseCasingIndex);
		return this.mEnergyHatches.add(tHatch);
	}

	private boolean addInjector(IGregTechTileEntity aBaseMetaTileEntity, int aBaseCasingIndex) {
		IMetaTileEntity aMetaTileEntity = aBaseMetaTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) return false;
		if (!(aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Input)) return false;
		GT_MetaTileEntity_Hatch_Input tHatch = (GT_MetaTileEntity_Hatch_Input) aMetaTileEntity;
		if (tHatch.mTier < tier()) return false;
		tHatch.updateTexture(aBaseCasingIndex);
		tHatch.mRecipeMap = getRecipeMap();
		return this.mInputHatches.add(tHatch);
	}

	private boolean addExtractor(IGregTechTileEntity aBaseMetaTileEntity, int aBaseCasingIndex) {
		if (aBaseMetaTileEntity == null) return false;
		IMetaTileEntity aMetaTileEntity = aBaseMetaTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) return false;
		if (!(aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Output)) return false;
		GT_MetaTileEntity_Hatch_Output tHatch = (GT_MetaTileEntity_Hatch_Output) aMetaTileEntity;
		if (tHatch.mTier < tier()) return false;
		tHatch.updateTexture(aBaseCasingIndex);
		return this.mOutputHatches.add(tHatch);
	}

	public abstract Block getCasing();

	public abstract int getCasingMeta();

	public abstract Block getFusionCoil();

	public abstract int getFusionCoilMeta();

	@SuppressWarnings("deprecation")
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
			final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		ITexture[] sTexture;
		if (aSide == aFacing) {
			sTexture = new ITexture[]{
					new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS,
							Dyes.getModulation(-1, Dyes._NULL.mRGBa)),
					new GT_RenderedTexture(this.getIconOverlay())};
		} else if (!aActive) {
			sTexture = new ITexture[]{
					new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS,
							Dyes.getModulation(-1, Dyes._NULL.mRGBa))};
		} else {
			sTexture = new ITexture[]{
					new GT_RenderedTexture(getFusionCasingTexture(),
							Dyes.getModulation(-1, Dyes._NULL.mRGBa))};
		}
		return sTexture;
	}

	public abstract CustomIcon getFusionCasingTexture();

	@SuppressWarnings("deprecation")
	public ITexture getTextureOverlay() {
		return new GT_RenderedTexture(this.mMaxProgresstime > 0 ? TexturesGtBlock.Casing_Machine_Screen_3 : TexturesGtBlock.Casing_Machine_Screen_1);
	}

	public IIconContainer getIconOverlay() {
		return this.mMaxProgresstime > 0 ? TexturesGtBlock.Casing_Machine_Screen_3 : TexturesGtBlock.Casing_Machine_Screen_1;
	}

	@Override
	public boolean isCorrectMachinePart(ItemStack aStack) {
		return true;
	}

	public final int overclock(int mStartEnergy) {
		int aMulti = this.getFusionTier() == 4 ? 1 : 2;
		return (mStartEnergy < 160000000) ? 8 : ((mStartEnergy < 320000000) ? 4 : (mStartEnergy < 640000000) ? 2 : 1) * aMulti;
	}

	@Override
	public boolean checkRecipe(ItemStack aStack) {
		ArrayList<FluidStack> tFluidList = getStoredFluids();
		int tFluidList_sS=tFluidList.size();
		for (int i = 0; i < tFluidList_sS - 1; i++) {
			for (int j = i + 1; j < tFluidList_sS; j++) {
				if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
					if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
						tFluidList.remove(j--); tFluidList_sS=tFluidList.size();
					} else {
						tFluidList.remove(i--); tFluidList_sS=tFluidList.size();
						break;
					}
				}
			}
		}
		if (tFluidList.size() > 1) {
			FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
			GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sFusionRecipes.findRecipe(this.getBaseMetaTileEntity(), this.mLastRecipe, false, GT_Values.V[8], tFluids);
			if ((tRecipe == null && !this.mRunningOnLoad) || (tRecipe != null && maxEUStore() < tRecipe.mSpecialValue)) {
				turnCasingActive(false);
				this.mLastRecipe = null;
				return false;
			}
			if (this.mRunningOnLoad || (tRecipe != null && tRecipe.isRecipeInputEqual(true, tFluids))) {
				this.mLastRecipe = tRecipe;
				this.mEUt = (this.mLastRecipe.mEUt * overclock(this.mLastRecipe.mSpecialValue));
				this.mMaxProgresstime = this.mLastRecipe.mDuration / overclock(this.mLastRecipe.mSpecialValue);
				this.mEfficiencyIncrease = 10000;
				this.mOutputFluids = this.mLastRecipe.mFluidOutputs;
				turnCasingActive(true);
				this.mRunningOnLoad = false;
				return true;
			}
		}
		return false;
	}

	public abstract int tierOverclock();

	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		if (aBaseMetaTileEntity.isServerSide()) {
			if (this.mEfficiency < 0)
				this.mEfficiency = 0;
			if (this.mRunningOnLoad && checkMachine(aBaseMetaTileEntity, this.mInventory[1])) {
				this.mEUStore = (int) aBaseMetaTileEntity.getStoredEU();
				checkRecipe(this.mInventory[1]);
			}
			if (--this.mUpdate == 0 || --this.mStartUpCheck == 0) {
				checkStructure(true, aBaseMetaTileEntity);
			}
			if (this.mStartUpCheck < 0) {
				if (this.mMachine) {
					if (this.mEnergyHatches != null) {
						for (GT_MetaTileEntity_Hatch_Energy tHatch : this.mEnergyHatches)
							if (isValidMetaTileEntity(tHatch)) {
								if (aBaseMetaTileEntity.getStoredEU() + (2048 * tierOverclock()) < maxEUStore()
										&& tHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(2048 * tierOverclock(), false)) {
									aBaseMetaTileEntity.increaseStoredEnergyUnits(2048 * tierOverclock(), true);
								}
							}
					}
					if (this.mEUStore <= 0 && this.mMaxProgresstime > 0) {
						stopMachine();
					}
					if (this.mMaxProgresstime > 0) {
						this.getBaseMetaTileEntity().decreaseStoredEnergyUnits(this.mEUt, true);
						if (this.mMaxProgresstime > 0 && ++this.mProgresstime >= this.mMaxProgresstime) {
							if (this.mOutputItems != null)
								for (ItemStack tStack : this.mOutputItems) if (tStack != null) addOutput(tStack);
							if (this.mOutputFluids != null)
								for (FluidStack tStack : this.mOutputFluids) if (tStack != null) addOutput(tStack);
							this.mEfficiency = Math.max(0, Math.min(this.mEfficiency + this.mEfficiencyIncrease, getMaxEfficiency(this.mInventory[1])));
							this.mOutputItems = null;
							this.mProgresstime = 0;
							this.mMaxProgresstime = 0;
							this.mEfficiencyIncrease = 0;
							if (this.mOutputFluids != null && this.mOutputFluids.length > 0) {
								try {
									GT_Mod.achievements.issueAchivementHatchFluid(aBaseMetaTileEntity.getWorld().getPlayerEntityByName(aBaseMetaTileEntity.getOwnerName()), this.mOutputFluids[0]);
								} catch (Exception ignored) {
								}
							}
							this.mEUStore = (int) aBaseMetaTileEntity.getStoredEU();
							if (aBaseMetaTileEntity.isAllowedToWork())
								checkRecipe(this.mInventory[1]);
						}
					} else {
						if (aTick % 100 == 0 || aBaseMetaTileEntity.hasWorkJustBeenEnabled() || aBaseMetaTileEntity.hasInventoryBeenModified()) {
							turnCasingActive(this.mMaxProgresstime > 0);
							if (aBaseMetaTileEntity.isAllowedToWork()) {
								this.mEUStore = (int) aBaseMetaTileEntity.getStoredEU();
								if (checkRecipe(this.mInventory[1])) {
									if (this.mEUStore < this.mLastRecipe.mSpecialValue - this.mEUt) {
										this.mMaxProgresstime = 0;
										turnCasingActive(false);
									}
									aBaseMetaTileEntity.decreaseStoredEnergyUnits(this.mLastRecipe.mSpecialValue - this.mEUt, true);
								}
							}
							if (this.mMaxProgresstime <= 0)
								this.mEfficiency = Math.max(0, this.mEfficiency - 1000);
						}
					}
				} else {
					turnCasingActive(false);
					this.mLastRecipe = null;
					stopMachine();
				}
			}
			aBaseMetaTileEntity.setErrorDisplayID((aBaseMetaTileEntity.getErrorDisplayID() & ~127) | (this.mMachine ? 0 : 64));
			aBaseMetaTileEntity.setActive(this.mMaxProgresstime > 0);
		}
	}

	@Override
	public boolean onRunningTick(ItemStack aStack) {
		if (this.mEUt < 0) {
			if (!drainEnergyInput(((long) -this.mEUt * 10000) / Math.max(1000, this.mEfficiency))) {
				this.mLastRecipe = null;
				stopMachine();
				return false;
			}
		}
		if (this.mEUStore <= 0) {
			this.mLastRecipe = null;
			stopMachine();
			return false;
		}
		return true;
	}

	@Override
	public boolean drainEnergyInput(long aEU) {
		return false;
	}

	@Override
	public int getMaxEfficiency(ItemStack aStack) {
		return 10000;
	}

	@Override
	public int getDamageToComponent(ItemStack aStack) {
		return 0;
	}
	@Override
	public boolean explodesOnComponentBreak(ItemStack aStack) {
		return false;
	}

	@Override
	public String[] getExtraInfoData() {
		String tier = ""+EnumChatFormatting.DARK_RED+this.getFusionTier()+EnumChatFormatting.RESET;
		float plasmaOut = 0;
		int powerRequired = 0;
		if (this.mLastRecipe != null) {
			powerRequired = this.mLastRecipe.mEUt;
			if (this.mLastRecipe.getFluidOutput(0) != null) {
				plasmaOut = (float)this.mLastRecipe.getFluidOutput(0).amount / (float)this.mLastRecipe.mDuration;
			}
		}

		return new String[]{
				EnumChatFormatting.RED + "Fusion Reactor MK " + EnumChatFormatting.RESET + tier,
				StatCollector.translateToLocal("GT5U.fusion.req") + ": " +
						EnumChatFormatting.RED + GT_Utility.formatNumbers(powerRequired) + EnumChatFormatting.RESET + "EU/t",
						StatCollector.translateToLocal("GT5U.multiblock.energy") + ": " +
								EnumChatFormatting.GREEN + GT_Utility.formatNumbers(this.mEUStore) + EnumChatFormatting.RESET + " EU / " +
								EnumChatFormatting.YELLOW + GT_Utility.formatNumbers(maxEUStore()) + EnumChatFormatting.RESET + " EU",
								StatCollector.translateToLocal("GT5U.fusion.plasma") + ": " +
										EnumChatFormatting.YELLOW + GT_Utility.formatNumbers(plasmaOut) + EnumChatFormatting.RESET + "L/t"};
	}

	@Override
	public boolean isGivingInformation() {
		return true;
	}

	@Override
	public void construct(ItemStack stackSize, boolean hintsOnly) {
		buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, 7, 1, 12);
	}

	@Override
	public String getMachineType() {
		return "Fusion Reactor";
	}

	public boolean turnCasingActive(boolean status) {
		if (this.mEnergyHatches != null) {
			for (GT_MetaTileEntity_Hatch_Energy hatch : this.mEnergyHatches) {
				hatch.updateTexture(status ? getCasingOverlayActive() : getCasingOverlayInactive());
			}
		}
		if (this.mOutputHatches != null) {
			for (GT_MetaTileEntity_Hatch_Output hatch : this.mOutputHatches) {
				hatch.updateTexture(status ? getCasingOverlayActive() : getCasingOverlayInactive());
			}
		}
		if (this.mInputHatches != null) {
			for (GT_MetaTileEntity_Hatch_Input hatch : this.mInputHatches) {
				hatch.updateTexture(status ? getCasingOverlayActive() : getCasingOverlayInactive());
			}
		}
		return true;
	}

	public abstract int getCasingOverlayActive();

	public abstract int getCasingOverlayInactive();

}
