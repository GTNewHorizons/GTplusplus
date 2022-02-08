package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing.advanced.fusion;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdderOptional;
import static gtPlusPlus.core.util.data.ArrayUtils.removeNulls;

import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

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
		int aOverclock = 0;
		if (mStartEnergy < 160000000) {
			aOverclock = 16;
		}
		else if (mStartEnergy < 320000000) {
			aOverclock = 8;
		}
		else if (mStartEnergy < 640000000) {
			aOverclock = 4;
		}
		else if (mStartEnergy < 1200000000) {
			aOverclock = 2;
		}
		else if (mStartEnergy < 2000000000) {
			aOverclock = 1;
		}
		else if (mStartEnergy <= Integer.MAX_VALUE) {
			return 1;
		}
		else {
			return 1;
		}
		return aOverclock * aMulti;
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
								if (aBaseMetaTileEntity.getStoredEU() + getEuPerTickInPerHatch() < maxEUStore()
										&& tHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(getEuPerTickInPerHatch(), false)) {
									aBaseMetaTileEntity.increaseStoredEnergyUnits(getEuPerTickInPerHatch(), true);
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




	@Override
	public boolean checkRecipeGeneric(
			ItemStack[] aItemInputs, FluidStack[] aFluidInputs,
			int aMaxParallelRecipes, int aEUPercent,
			int aSpeedBonusPercent, int aOutputChanceRoll, GT_Recipe aRecipe) {
		// Based on the Processing Array. A bit overkill, but very flexible.

		// Reset outputs and progress stats
		this.mEUt = 0;
		this.mMaxProgresstime = 0;
		this.mOutputItems = new ItemStack[]{};
		this.mOutputFluids = new FluidStack[]{};

		long tVoltage = getMaxInputVoltage();
		byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
		long tEnergy = getMaxInputEnergy();
		log("Running checkRecipeGeneric(0)");

		GT_Recipe tRecipe = findRecipe(
				getBaseMetaTileEntity(), this.mLastRecipe, false, false,
				gregtech.api.enums.GT_Values.V[tTier], aFluidInputs, aItemInputs);

		log("Running checkRecipeGeneric(1)");
		// Remember last recipe - an optimization for findRecipe()
		this.mLastRecipe = tRecipe;

		if (tRecipe == null) {
			log("BAD RETURN - 1");
			return false;
		}

		aMaxParallelRecipes = this.canBufferOutputs(tRecipe, aMaxParallelRecipes);
		if (aMaxParallelRecipes == 0) {
			log("BAD RETURN - 2");
			return false;
		}

		// EU discount
		float tRecipeEUt = (tRecipe.mEUt * aEUPercent) / 100.0f;
		float tTotalEUt = 0.0f;

		int parallelRecipes = 0;

		log("parallelRecipes: "+parallelRecipes);
		log("aMaxParallelRecipes: "+aMaxParallelRecipes);
		log("tTotalEUt: "+tTotalEUt);
		log("tVoltage: "+tVoltage);
		log("tRecipeEUt: "+tRecipeEUt);
		// Count recipes to do in parallel, consuming input items and fluids and considering input voltage limits
		for (; parallelRecipes < aMaxParallelRecipes && tTotalEUt < (tEnergy - tRecipeEUt); parallelRecipes++) {
			if (!tRecipe.isRecipeInputEqual(true, aFluidInputs, aItemInputs)) {
				log("Broke at "+parallelRecipes+".");
				break;
			}
			log("Bumped EU from "+tTotalEUt+" to "+(tTotalEUt+tRecipeEUt)+".");
			tTotalEUt += tRecipeEUt;
		}

		if (parallelRecipes == 0) {
			log("BAD RETURN - 3");
			return false;
		}

		// -- Try not to fail after this point - inputs have already been consumed! --



		// Convert speed bonus to duration multiplier
		// e.g. 100% speed bonus = 200% speed = 100%/200% = 50% recipe duration.
		aSpeedBonusPercent = Math.max(-99, aSpeedBonusPercent);
		float tTimeFactor = 100.0f / (100.0f + aSpeedBonusPercent);
		this.mMaxProgresstime = (int)(tRecipe.mDuration * tTimeFactor);

		this.mEUt = (int)Math.ceil(tTotalEUt);

		this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
		this.mEfficiencyIncrease = 10000;

		// Overclock
		if (this.mEUt <= 16) {
			this.mEUt = (this.mEUt * (1 << tTier - 1) * (1 << tTier - 1));
			this.mMaxProgresstime = (this.mMaxProgresstime / (1 << tTier - 1));
		} else {
			while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)]) {
				this.mEUt *= 4;
				if (hasPerfectOverclock()) {
					this.mMaxProgresstime /= 4;
				}
				else {
					this.mMaxProgresstime /= 2;
				}
			}
		}

		if (this.mEUt > 0) {
			this.mEUt = (-this.mEUt);
		}

		this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);

		// Collect fluid outputs
		FluidStack[] tOutputFluids = new FluidStack[tRecipe.mFluidOutputs.length];
		for (int h = 0; h < tRecipe.mFluidOutputs.length; h++) {
			if (tRecipe.getFluidOutput(h) != null) {
				tOutputFluids[h] = tRecipe.getFluidOutput(h).copy();
				tOutputFluids[h].amount *= parallelRecipes;
			}
		}

		// Collect output item types
		ItemStack[] tOutputItems = new ItemStack[tRecipe.mOutputs.length];
		for (int h = 0; h < tRecipe.mOutputs.length; h++) {
			if (tRecipe.getOutput(h) != null) {
				tOutputItems[h] = tRecipe.getOutput(h).copy();
				tOutputItems[h].stackSize = 0;
			}
		}

		// Set output item stack sizes (taking output chance into account)
		for (int f = 0; f < tOutputItems.length; f++) {
			if (tRecipe.mOutputs[f] != null && tOutputItems[f] != null) {
				for (int g = 0; g < parallelRecipes; g++) {
					if (getBaseMetaTileEntity().getRandomNumber(aOutputChanceRoll) < tRecipe.getOutputChance(f))
						tOutputItems[f].stackSize += tRecipe.mOutputs[f].stackSize;
				}
			}
		}

		tOutputItems = removeNulls(tOutputItems);

		// Sanitize item stack size, splitting any stacks greater than max stack size
		List<ItemStack> splitStacks = new ArrayList<ItemStack>();
		for (ItemStack tItem : tOutputItems) {
			while (tItem.getMaxStackSize() < tItem.stackSize) {
				ItemStack tmp = tItem.copy();
				tmp.stackSize = tmp.getMaxStackSize();
				tItem.stackSize = tItem.stackSize - tItem.getMaxStackSize();
				splitStacks.add(tmp);
			}
		}

		if (splitStacks.size() > 0) {
			ItemStack[] tmp = new ItemStack[splitStacks.size()];
			tmp = splitStacks.toArray(tmp);
			tOutputItems = ArrayUtils.addAll(tOutputItems, tmp);
		}

		// Strip empty stacks
		List<ItemStack> tSList = new ArrayList<ItemStack>();
		for (ItemStack tS : tOutputItems) {
			if (tS.stackSize > 0) tSList.add(tS);
		}
		tOutputItems = tSList.toArray(new ItemStack[tSList.size()]);

		// Commit outputs
		this.mOutputItems = tOutputItems;
		this.mOutputFluids = tOutputFluids;
		updateSlots();

		// Play sounds (GT++ addition - GT multiblocks play no sounds)
		startProcess();

		log("GOOD RETURN - 1");
		return true;
	}


}
