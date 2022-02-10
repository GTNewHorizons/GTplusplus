package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing.advanced.fusion;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdderOptional;

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
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.common.gui.GT_GUIContainer_FusionReactor;
import gtPlusPlus.core.lib.*;
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

public abstract class GregtechMetaTileEntity_Adv_Fusion_Base
extends GregtechMeta_MultiBlockBase<GregtechMetaTileEntity_Adv_Fusion_Base> {

	public static final String STRUCTURE_PIECE_MAIN = "main";
	private int mCurrentParallels = 0;
	private static final ClassValue<IStructureDefinition<GregtechMetaTileEntity_Adv_Fusion_Base>> STRUCTURE_DEFINITION = new ClassValue<IStructureDefinition<GregtechMetaTileEntity_Adv_Fusion_Base>>() {
		@Override
		protected IStructureDefinition<GregtechMetaTileEntity_Adv_Fusion_Base> computeValue(Class<?> type) {
			return StructureDefinition.<GregtechMetaTileEntity_Adv_Fusion_Base>builder()
					.addShape(STRUCTURE_PIECE_MAIN,
							transpose(new String[][] {
								{ "               ", "      ihi      ", "    hh   hh    ", "   h       h   ",
									"  h         h  ", "  h         h  ", " i           i ", " h           h ",
									" i           i ", "  h         h  ", "  h         h  ", "   h       h   ",
									"    hh   hh    ", "      ihi      ", "               ", },
								{ "      xhx      ", "    hhccchh    ", "   eccxhxcce   ", "  eceh   hece  ",
										" hce       ech ", " hch       hch ", "xcx         xcx", "hch         hch",
										"xcx         xcx", " hch       hch ", " hce       ech ", "  eceh   hece  ",
										"   eccx~xcce   ", "    hhccchh    ", "      xhx      ", },
								{ "               ", "      ihi      ", "    hh   hh    ", "   h       h   ",
											"  h         h  ", "  h         h  ", " i           i ", " h           h ",
											" i           i ", "  h         h  ", "  h         h  ", "   h       h   ",
											"    hh   hh    ", "      ihi      ", "               ", } }))
					.addElement('c', lazy(t -> ofBlock(t.getFusionCoil(), t.getFusionCoilMeta())))
					.addElement('h', lazy(t -> ofBlock(t.getCasing(), t.getCasingMeta())))
					.addElement('i',
							lazy(t -> ofHatchAdderOptional(GregtechMetaTileEntity_Adv_Fusion_Base::addInjector, 53, 1,
									t.getCasing(), t.getCasingMeta())))
					.addElement('e',
							lazy(t -> ofHatchAdderOptional(GregtechMetaTileEntity_Adv_Fusion_Base::addEnergyInjector,
									53, 2, t.getCasing(), t.getCasingMeta())))
					.addElement('x',
							lazy(t -> ofHatchAdderOptional(GregtechMetaTileEntity_Adv_Fusion_Base::addExtractor, 53, 3,
									t.getCasing(), t.getCasingMeta())))
					.build();
		}
	};

	public long mEUStore;

	public GregtechMetaTileEntity_Adv_Fusion_Base(int aID, String aName, String aNameRegional, int tier) {
		super(aID, aName, aNameRegional);
	}

	public GregtechMetaTileEntity_Adv_Fusion_Base(String aName) {
		super(aName);
	}

	public final String getMinimumHatchTierString() {
		return GT_Values.VN[getMinimumHatchTier()];
	}

	public abstract String getCasingName();

	public abstract String getCoilName();

	public abstract String getTooltipText();

	public abstract int getFusionTier();

	public abstract long getEuPerTickInPerHatch();

	public abstract long getEuStoragePerHatch();

	@Override
	protected GT_Multiblock_Tooltip_Builder createTooltip() {
		GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
		tt.addMachineType("Fusion Reactor").addInfo(getTooltipText())
		.addInfo("Controller block for the Fusion Reactor Mk " + getFusionTier()).addInfo("Has 4/4 Overclocks")
		.addInfo(MathUtils.formatNumbers(getEuPerTickInPerHatch()) + " EU/t per Energy Hatch")
		.addInfo(MathUtils.formatNumbers(getEuStoragePerHatch()) + " EU capacity per Energy Hatch")
		.addInfo("If the recipe has a startup cost greater than the")
		.addInfo("number of energy hatches * cap, you can't do it").addSeparator().addInfo("Parallel:")
		.addInfo("Startup < 160,000,000 EU:   " + overclock(150000000))
		.addInfo("Startup < 320,000,000 EU:   " + overclock(300000000))
		.addInfo("Startup < 640,000,000 EU:   " + overclock(600000000))
		.addInfo("Startup < 1,200,000,000 EU: " + overclock(900000000))
		.addInfo("Startup < 2,000,000,000 EU: " + overclock(1500000000)).addSeparator()
		.beginStructureBlock(15, 3, 15, false).addController("See diagram when placed")
		.addCasingInfo(getCasingName(), 79).addStructureInfo("Cover the coils with casings")
		.addOtherStructurePart(getCoilName(), "Center part of the ring")
		.addEnergyHatch("1-16, Specified casings", 2).addInputHatch("2-16, Specified casings", 1)
		.addOutputHatch("1-16, Specified casings", 3)
		.addStructureInfo("ALL Hatches must be " + getMinimumHatchTierString() + " or better");
		if (LoadedMods.TecTech) {
			tt.addStructureInfo("Supports Laser Hatches");
		}
		tt.toolTipFinisher(CORE.GT_Tooltip_Builder);
		return tt;
	}

	public abstract int getMinimumHatchTier();

	@Override
	public final long maxEUStore() {
		return getEuStoragePerHatch() * this.mAllEnergyHatches.size();
	}

	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_MultiMachine(aPlayerInventory, aBaseMetaTileEntity);
	}

	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_FusionReactor(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
				"FusionComputer.png", GT_Recipe.GT_Recipe_Map.sFusionRecipes.mNEIName);
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
		boolean aStructCheck = checkPiece(STRUCTURE_PIECE_MAIN, 7, 1, 12);
		boolean aInjectors = this.mInputHatches.size() > 1;
		boolean aExtractors = !this.mOutputHatches.isEmpty();
		boolean aEnergy = !this.mAllEnergyHatches.isEmpty();
		if (aStructCheck && aInjectors && aExtractors && aEnergy) {
			this.mWrench = true;
			this.mScrewdriver = true;
			this.mSoftHammer = true;
			this.mHardHammer = true;
			this.mSolderingTool = true;
			this.mCrowbar = true;
			log("Structure? " + aStructCheck + " | Injectors? " + aInjectors + " | Extractors? " + aExtractors
					+ " | Energy? " + aEnergy);
			return true;
		}
		log("Structure? " + aStructCheck + " | Injectors? " + aInjectors + " | Extractors? " + aExtractors
				+ " | Energy? " + aEnergy);
		log("Injectors? " + this.mInputHatches.size() + " | Extractors? " + this.mOutputHatches.size() + " | Energy? "
				+ this.mAllEnergyHatches.size());
		return false;
	}

	private boolean addEnergyInjector(IGregTechTileEntity aBaseMetaTileEntity, int aBaseCasingIndex) {
		return this.addEnergyInputToMachineList(aBaseMetaTileEntity, aBaseCasingIndex);
	}

	private boolean addInjector(IGregTechTileEntity aBaseMetaTileEntity, int aBaseCasingIndex) {
		if (aBaseMetaTileEntity == null) {
			return false;
		}
		IMetaTileEntity aMetaTileEntity = aBaseMetaTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) {
			return false;
		}
		if (!(aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Input)) {
			return false;
		}
		return addHatch(aMetaTileEntity, aBaseCasingIndex);
	}

	private boolean addExtractor(IGregTechTileEntity aBaseMetaTileEntity, int aBaseCasingIndex) {
		if (aBaseMetaTileEntity == null) {
			return false;
		}
		IMetaTileEntity aMetaTileEntity = aBaseMetaTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) {
			return false;
		}
		if (!(aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Output)) {
			return false;
		}
		return addHatch(aMetaTileEntity, aBaseCasingIndex);
	}

	private boolean addHatch(IMetaTileEntity aMetaTileEntity, int aBaseCasingIndex) {
		GT_MetaTileEntity_Hatch tHatch = (GT_MetaTileEntity_Hatch) aMetaTileEntity;
		if (tHatch.mTier < getMinimumHatchTier()) {
			return false;
		}
		return this.addToMachineList(aMetaTileEntity, aBaseCasingIndex);

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
			sTexture = new ITexture[] { new GT_RenderedTexture(getFusionCasingTexture()), new GT_RenderedTexture(this.getIconOverlay()) };
		} else if (!aActive) {
			sTexture = new ITexture[] { (this.getFusionTier() == 4 ? new GT_RenderedTexture(Textures.BlockIcons.FUSIONI_8) : new GT_RenderedTexture(Textures.BlockIcons.FUSIONII_8)) };
		} else {
			sTexture = new ITexture[] {	new GT_RenderedTexture(getFusionCasingTexture()) };
		}
		return sTexture;
	}

	public abstract CustomIcon getFusionCasingTexture();

	@SuppressWarnings("deprecation")
	public ITexture getTextureOverlay() {
		return new GT_RenderedTexture(this.mMaxProgresstime > 0 ? TexturesGtBlock.Casing_Machine_Screen_3
				: TexturesGtBlock.Casing_Machine_Screen_1);
	}

	public IIconContainer getIconOverlay() {
		return this.mMaxProgresstime > 0 ? TexturesGtBlock.Casing_Machine_Screen_3
				: TexturesGtBlock.Casing_Machine_Screen_1;
	}

	@Override
	public boolean isCorrectMachinePart(ItemStack aStack) {
		return true;
	}

	public final int overclock(int mStartEnergy) {
		int aMulti = this.getFusionTier() == 4 ? 1 : 2;
		int aOverclock = 0;
		if (mStartEnergy < 160000000) {
			aOverclock = 32;
		} else if (mStartEnergy < 320000000) {
			aOverclock = 16;
		} else if (mStartEnergy < 640000000) {
			aOverclock = 8;
		} else if (mStartEnergy < 1200000000) {
			aOverclock = 4;
		} else if (mStartEnergy < 2000000000) {
			aOverclock = 2;
		} else if (mStartEnergy >= 2000000000 && mStartEnergy <= Integer.MAX_VALUE) {
			return 1;
		} else {
			return 1;
		}
		return aOverclock * aMulti;
	}

	@Override
	public boolean hasPerfectOverclock() {
		return true;
	}

	@Override
	public boolean drainEnergyInput(long aEU) {
		if (aEU <= 0) {
			//log("aEU <= 0 | " + aEU);
			return true;
		}
		if (this.getBaseMetaTileEntity().getStoredEU() - aEU >= 0
				&& this.getBaseMetaTileEntity().decreaseStoredEnergyUnits(aEU, false)) {
			//log("Removed " + aEU + " from EU Storage.");
			return true;
		}
		//log("aEU | " + aEU + " | false | " + this.mAllEnergyHatches.size());
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
	public final int getMaxParallelRecipes() {
		if (this.mLastRecipe != null) {
			return overclock(this.mLastRecipe.mSpecialValue);
		}
		return 0;
	}

	@Override
	public String[] getExtraInfoData() {
		String tier = "" + EnumChatFormatting.DARK_RED + this.getFusionTier() + EnumChatFormatting.RESET;
		float plasmaOut = 0;
		int powerRequired = 0;
		if (this.mLastRecipe != null) {
			powerRequired = this.mLastRecipe.mEUt * this.mCurrentParallels;
			if (this.mLastRecipe.getFluidOutput(0) != null) {
				plasmaOut = (float) this.mLastRecipe.getFluidOutput(0).amount / (float) this.mLastRecipe.mDuration
						* this.mCurrentParallels;
			}
		}
		return new String[] { EnumChatFormatting.RED + "Fusion Reactor MK " + EnumChatFormatting.RESET + tier,
				"Current Parallel: " + this.mCurrentParallels,
				StatCollector.translateToLocal("GT5U.fusion.req") + ": " + EnumChatFormatting.RED
				+ GT_Utility.formatNumbers(powerRequired) + EnumChatFormatting.RESET + "EU/t",
				StatCollector.translateToLocal("GT5U.multiblock.energy") + ": " + EnumChatFormatting.GREEN
				+ GT_Utility.formatNumbers(this.mEUStore) + EnumChatFormatting.RESET + " EU / "
				+ EnumChatFormatting.YELLOW + GT_Utility.formatNumbers(maxEUStore()) + EnumChatFormatting.RESET
				+ " EU",
				StatCollector.translateToLocal("GT5U.fusion.plasma") + ": " + EnumChatFormatting.YELLOW
				+ GT_Utility.formatNumbers(plasmaOut) + EnumChatFormatting.RESET + "L/t" };
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
		if (this.mAllEnergyHatches != null) {
			for (GT_MetaTileEntity_Hatch hatch : this.mAllEnergyHatches) {
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
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		if (aBaseMetaTileEntity.isServerSide()) {
			if (this.mEfficiency < 0) {
				this.mEfficiency = 0;
			}
			if (this.mUpdated) {
				this.mUpdate = 50;
				this.mUpdated = false;
			}
			// Time Counter
			this.mTotalRunTime++;
			this.mEUStore = aBaseMetaTileEntity.getStoredEU();
			if (this.mRunningOnLoad && checkMachine(aBaseMetaTileEntity, this.mInventory[1])) {
				checkRecipe(this.mInventory[1]);
			}
			if (--this.mUpdate == 0 || --this.mStartUpCheck == 0) {
				this.mTecTechEnergyHatches.clear();
				this.mTecTechDynamoHatches.clear();
				this.mAllEnergyHatches.clear();
				this.mAllDynamoHatches.clear();
				checkStructure(true, aBaseMetaTileEntity);
			}
			int aHatchCount = this.mAllEnergyHatches.size();
			if (aHatchCount > 0) {
				//log("Capacity: " + this.getBaseMetaTileEntity().getEUCapacity());
				for (GT_MetaTileEntity_Hatch tHatch : this.mAllEnergyHatches) {
					if (isValidMetaTileEntity(tHatch)) {
						long aDrain = Math.min(getEuPerTickInPerHatch(), GT_Values.V[tHatch.mTier]);
						if (this.getBaseMetaTileEntity()
								.getStoredEU() <= (this.getBaseMetaTileEntity().getEUCapacity() - aDrain)) {
							if (tHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(aDrain, false)) {
								this.getBaseMetaTileEntity().increaseStoredEnergyUnits(aDrain, false);
								//log("Added " + aDrain + " to EU Storage.");
							}
						}
					}
				}
			}
			if (this.mStartUpCheck < 0) {
				if (this.mMachine) {
					checkMaintenanceStatus();
					if (getRepairStatus() > 0) {
						runMachine(aBaseMetaTileEntity, aTick);
					} else {
						stopMachine();
					}
				} else {
					stopMachine();
				}
			}
			aBaseMetaTileEntity
			.setErrorDisplayID((aBaseMetaTileEntity.getErrorDisplayID() & ~127) | (this.mWrench ? 0 : 1)
					| (this.mScrewdriver ? 0 : 2) | (this.mSoftHammer ? 0 : 4) | (this.mHardHammer ? 0 : 8)
					| (this.mSolderingTool ? 0 : 16) | (this.mCrowbar ? 0 : 32) | (this.mMachine ? 0 : 64));
			aBaseMetaTileEntity.setActive(this.mMaxProgresstime > 0);
			turnCasingActive(this.mMaxProgresstime > 0);
		}
	}

	@Override
	protected void runMachine(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		if (this.mMaxProgresstime > 0) {
			boolean runningTick = onRunningTick(this.mInventory[1]);
			if (runningTick) {
				if (!polluteEnvironment(getPollutionPerTick(this.mInventory[1]))) {
					stopMachine();
				}
				if (this.mMaxProgresstime > 0 && ++this.mProgresstime >= this.mMaxProgresstime) {
					if (this.mOutputItems != null)
						for (ItemStack tStack : this.mOutputItems)
							if (tStack != null) {
								try {
									GT_Mod.achievements.issueAchivementHatch(aBaseMetaTileEntity.getWorld()
											.getPlayerEntityByName(aBaseMetaTileEntity.getOwnerName()), tStack);
								} catch (Exception ignored) {
								}
								addOutput(tStack);
							}
					if (this.mOutputFluids != null) {
						addFluidOutputs(this.mOutputFluids);
					}
					this.mEfficiency = Math.max(0, Math.min(this.mEfficiency + this.mEfficiencyIncrease,
							getMaxEfficiency(this.mInventory[1]) - ((getIdealStatus() - getRepairStatus()) * 1000)));
					this.mOutputItems = null;
					this.mProgresstime = 0;
					this.mMaxProgresstime = 0;
					this.mEfficiencyIncrease = 0;
					if (aBaseMetaTileEntity.isAllowedToWork()) {
						checkRecipe(this.mInventory[1]);
					}
					if (this.mOutputFluids != null && this.mOutputFluids.length > 0) {
						if (this.mOutputFluids.length > 1) {
							try {
								GT_Mod.achievements.issueAchievement(aBaseMetaTileEntity.getWorld()
										.getPlayerEntityByName(aBaseMetaTileEntity.getOwnerName()), "oilplant");
							} catch (Exception ignored) {
							}
						}
					}
				}
			}
		} else {
			if (aTick % 100 == 0 || aBaseMetaTileEntity.hasWorkJustBeenEnabled()
					|| aBaseMetaTileEntity.hasInventoryBeenModified()) {
				// log("C1 | "+aBaseMetaTileEntity.hasWorkJustBeenEnabled()+" |
				// "+aBaseMetaTileEntity.hasInventoryBeenModified()+" |
				// "+aBaseMetaTileEntity.isAllowedToWork());
				if (aBaseMetaTileEntity.isAllowedToWork()) {
					checkRecipe(this.mInventory[1]);
				}
				if (this.mMaxProgresstime <= 0) {
					this.mEfficiency = Math.max(0, this.mEfficiency - 1000);
				}
			}
		}
	}

	/**
	 * Called every tick the Machine runs
	 */
	@Override
	public boolean onRunningTick(ItemStack aStack) {
		if (this.mEUt > 0) {
			addEnergyOutput(((long) this.mEUt * this.mEfficiency) / 10000);
			return true;
		}
		if (this.mEUt < 0) {
			if (!drainEnergyInput(((long) -this.mEUt * 10000) / Math.max(1000, this.mEfficiency))) {
				criticalStopMachine();
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
	public void stopMachine() {
		this.mOutputItems = null;
		this.mEUt = 0;
		this.mEfficiency = 0;
		this.mProgresstime = 0;
		this.mMaxProgresstime = 0;
		this.mEfficiencyIncrease = 0;
		getBaseMetaTileEntity().disableWorking();
		turnCasingActive(false);
		this.mLastRecipe = null;
		this.mCurrentParallels = 0;
	}

	@Override
	public void criticalStopMachine() {
		stopMachine();
		getBaseMetaTileEntity().setShutdownStatus(true);
	}

	@Override
	public GT_Recipe_Map getRecipeMap() {
		return GT_Recipe_Map.sFusionRecipes;
	}

	@Override
	public boolean checkRecipe(ItemStack aStack) {
		return checkRecipeGeneric(1, 100, 0);
	}

	@Override
	public boolean checkRecipeGeneric() {
		return checkRecipeGeneric(1, 100, 0);
	}

	@Override
	public boolean checkRecipeGeneric(int aMaxParallelRecipes, int aEUPercent, int aSpeedBonusPercent) {
		return checkRecipeGeneric(aMaxParallelRecipes, aEUPercent, aSpeedBonusPercent, 10000);
	}

	@Override
	public boolean checkRecipeGeneric(int aMaxParallelRecipes, int aEUPercent, int aSpeedBonusPercent,
			int aOutputChanceRoll) {
		return checkRecipeGeneric(new ItemStack[] {}, this.getCompactedFluids(), aMaxParallelRecipes, aEUPercent,
				aSpeedBonusPercent, aOutputChanceRoll);
	}

	@Override
	public boolean checkRecipeGeneric(ItemStack[] aItemInputs, FluidStack[] aFluidInputs, int aMaxParallelRecipes,
			int aEUPercent, int aSpeedBonusPercent, int aOutputChanceRoll, GT_Recipe aRecipe) {
		// Based on the Processing Array. A bit overkill, but very flexible.

		aFluidInputs = this.getCompactedFluids();

		// Reset outputs and progress stats
		this.mEUt = 0;
		this.mMaxProgresstime = 0;
		this.mOutputItems = new ItemStack[] {};
		this.mOutputFluids = new FluidStack[] {};

		long tVoltage = this.mAllEnergyHatches.size() * getEuPerTickInPerHatch();
		byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
		long tEnergy = getMaxInputEnergy();
		log("Running checkRecipeGeneric(0) | "+tVoltage+" | "+tTier+" | "+tEnergy);
		GT_Recipe tRecipe;
		try {
			tRecipe = findRecipe(getBaseMetaTileEntity(), this.mLastRecipe, false, false,
					gregtech.api.enums.GT_Values.V[tTier], aFluidInputs, aItemInputs);
		} catch (Throwable t) {
			t.printStackTrace();
			stopMachine();
			return false;
		}
		// log("Running checkRecipeGeneric(1)");
		// Remember last recipe - an optimization for findRecipe()
		this.mLastRecipe = tRecipe;

		if ((tRecipe == null && !this.mRunningOnLoad) || (tRecipe != null && maxEUStore() < tRecipe.mSpecialValue)) {
			stopMachine();
			return false;
		} else if (tRecipe == null) {
			stopMachine();
			return false;
		} else {
			aMaxParallelRecipes = this.overclock(tRecipe.mSpecialValue);
			log("Setting Max parallel to " + aMaxParallelRecipes);
		}

		aMaxParallelRecipes = this.canBufferOutputs(tRecipe, aMaxParallelRecipes);
		if (aMaxParallelRecipes == 0) {
			// log("BAD RETURN - 2");
			stopMachine();
			return false;
		}

		// EU discount
		float tRecipeEUt = (tRecipe.mEUt * aEUPercent) / 100.0f;
		float tTotalEUt = 0.0f;

		int parallelRecipes = 0;

		// log("parallelRecipes: "+parallelRecipes);
		// log("aMaxParallelRecipes: "+aMaxParallelRecipes);
		// log("tTotalEUt: "+tTotalEUt);
		// log("tVoltage: "+tVoltage);
		// log("tRecipeEUt: "+tRecipeEUt);
		// Count recipes to do in parallel, consuming input items and fluids and
		// considering input voltage limits
		for (; parallelRecipes < aMaxParallelRecipes && tTotalEUt < (tEnergy - tRecipeEUt); parallelRecipes++) {
			if (!this.mRunningOnLoad) {
				if (!tRecipe.isRecipeInputEqual(true, aFluidInputs, aItemInputs)) {
					// log("Broke at "+parallelRecipes+".");
					break;
				}
			}
			// log("Bumped EU from "+tTotalEUt+" to "+(tTotalEUt+tRecipeEUt)+".");
			tTotalEUt += tRecipeEUt;
		}

		if (parallelRecipes == 0) {
			// log("BAD RETURN - 3");
			stopMachine();
			return false;
		}

		log("Parallel: " + parallelRecipes);
		this.mCurrentParallels = parallelRecipes;
		// -- Try not to fail after this point - inputs have already been consumed! --
		turnCasingActive(true);

		// Convert speed bonus to duration multiplier
		// e.g. 100% speed bonus = 200% speed = 100%/200% = 50% recipe duration.
		aSpeedBonusPercent = Math.max(-99, aSpeedBonusPercent);
		float tTimeFactor = 100.0f / (100.0f + aSpeedBonusPercent);
		this.mMaxProgresstime = (int) (tRecipe.mDuration * tTimeFactor);
		// log("mMaxProgresstime: "+this.mMaxProgresstime);
		this.mEUt = (int) Math.ceil(tTotalEUt);
		// log("mEUt: "+this.mEUt);

		this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
		this.mEfficiencyIncrease = 10000;
		// log("mEfficiency: "+this.mEfficiency);

		// Overclock
		if (this.mEUt <= 16) {
			this.mEUt = (this.mEUt * (1 << tTier - 1) * (1 << tTier - 1));
			this.mMaxProgresstime = (this.mMaxProgresstime / (1 << tTier - 1));
		} else {
			while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)]) {
				this.mEUt *= 4;
				if (hasPerfectOverclock()) {
					this.mMaxProgresstime /= 4;
				} else {
					this.mMaxProgresstime /= 2;
				}
			}
		}
		// log("mMaxProgresstime: "+this.mMaxProgresstime);
		// log("mEUt: "+this.mEUt);

		if (this.mEUt > 0) {
			this.mEUt = (-this.mEUt);
		}
		log("mEUt: " + this.mEUt);

		this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
		log("mMaxProgresstime: " + this.mMaxProgresstime);
		this.mRunningOnLoad = false;

		// Collect fluid outputs
		FluidStack[] tOutputFluids = new FluidStack[tRecipe.mFluidOutputs.length];
		for (int h = 0; h < tRecipe.mFluidOutputs.length; h++) {
			if (tRecipe.getFluidOutput(h) != null) {
				tOutputFluids[h] = tRecipe.getFluidOutput(h).copy();
				tOutputFluids[h].amount *= parallelRecipes;
			}
		}

		for (FluidStack output : tOutputFluids) {
			if (output != null) {
				log("Outputting " + output.amount + "L of " + output.getLocalizedName());
			}
		}

		// Commit outputs
		this.mOutputItems = new ItemStack[] {};
		this.mOutputFluids = tOutputFluids;
		updateSlots();

		// Play sounds (GT++ addition - GT multiblocks play no sounds)
		startProcess();

		log("GOOD RETURN - 1");
		return true;
	}

}
