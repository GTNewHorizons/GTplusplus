package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.turbines;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdder;

import java.util.ArrayList;

import com.gtnewhorizon.structurelib.structure.*;

import gregtech.api.enums.*;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.util.*;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.api.objects.data.AutoMap;
import gtPlusPlus.api.objects.minecraft.BlockPos;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.util.math.MathUtils;
import gtPlusPlus.core.util.minecraft.gregtech.PollutionUtils;
import gtPlusPlus.core.util.sys.KeyboardUtils;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Turbine;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import gtPlusPlus.xmod.gregtech.common.StaticFields59;
import net.minecraft.block.Block;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.fluids.FluidStack;

public abstract class GregtechMetaTileEntity_LargerTurbineBase extends GregtechMeta_MultiBlockBase<GregtechMetaTileEntity_LargerTurbineBase> {

	protected int baseEff = 0;
	protected int optFlow = 0;
	protected double realOptFlow = 0;
	protected int storedFluid = 0;
	protected int counter = 0;
	protected int mCasing;

	public ITexture frontFace;
	public ITexture frontFaceActive;


	public ArrayList<GT_MetaTileEntity_Hatch_Turbine> mTurbineRotorHatches = new ArrayList<GT_MetaTileEntity_Hatch_Turbine>();


	public GregtechMetaTileEntity_LargerTurbineBase(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional);
		this.frontFace = getTextureFrontFace();
		this.frontFaceActive = getTextureFrontFaceActive();

	}
	public GregtechMetaTileEntity_LargerTurbineBase(String aName) {
		super(aName);
		this.frontFace = getTextureFrontFace();
		this.frontFaceActive = getTextureFrontFaceActive();
	}


	protected abstract ITexture getTextureFrontFace();

	protected abstract ITexture getTextureFrontFaceActive();

	protected abstract String getTurbineType();

	protected abstract String getCasingName();

	protected abstract boolean requiresOutputHatch();

	@Override
	protected final GT_Multiblock_Tooltip_Builder createTooltip() {
		GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
		tt.addMachineType(getMachineType())
		.addInfo("Controller Block for the XL "+getTurbineType()+" Turbine")
		.addPollutionAmount(getPollutionPerSecond(null))
		.addSeparator()
		.beginStructureBlock(7, 9, 7, false)
		.addController("Top Middle")
		.addCasingInfo(getCasingName(), 360)
		.addOtherStructurePart("Rotor Assembly", "Any 1 dot hint", 1)
		.addInputBus("Any 4 dot hint (min 1)", 4)
		.addInputHatch("Any 4 dot hint(min 1)", 4);
		if (requiresOutputHatch()) {
			tt.addOutputHatch("Any 4 dot hint(min 1)", 4);
		}
		tt.addDynamoHatch("Any 4 dot hint(min 1)", 4)
		.addMaintenanceHatch("Any 4 dot hint(min 1)", 4);
		if (requiresMufflers()) {
			tt.addMufflerHatch("Any 7 dot hint (x4)", 7);
		}
		tt.toolTipFinisher(CORE.GT_Tooltip_Builder);
		return tt;
	}

	private static final String STRUCTURE_PIECE_MAIN = "main";
	private static final ClassValue<IStructureDefinition<GregtechMetaTileEntity_LargerTurbineBase>> STRUCTURE_DEFINITION = new ClassValue<IStructureDefinition<GregtechMetaTileEntity_LargerTurbineBase>>() {
		@Override
		protected IStructureDefinition<GregtechMetaTileEntity_LargerTurbineBase> computeValue(Class<?> type) {
			return StructureDefinition.<GregtechMetaTileEntity_LargerTurbineBase>builder()
					// c = turbine casing
					// s = turbine shaft
					// t = turbine housing
					// h = dynamo/maint
					// m = muffler
					.addShape(STRUCTURE_PIECE_MAIN, (new String[][]{
						{"ccchccc", "ccccccc", "ccmmmcc", "ccm~mcc", "ccmmmcc", "ccccccc", "ccchccc"},
						{"ctchctc", "cscccsc", "cscccsc", "cscccsc", "cscccsc", "cscccsc", "ctchctc"},
						{"ccchccc", "ccccccc", "ccccccc", "ccccccc", "ccccccc", "ccccccc", "ccchccc"},
						{"ccchccc", "ccccccc", "ccccccc", "ccccccc", "ccccccc", "ccccccc", "ccchccc"},
						{"ctchctc", "cscccsc", "cscccsc", "cscccsc", "cscccsc", "cscccsc", "ctchctc"},
						{"ccchccc", "ccccccc", "ccccccc", "ccccccc", "ccccccc", "ccccccc", "ccchccc"},
						{"ccchccc", "ccccccc", "ccccccc", "ccccccc", "ccccccc", "ccccccc", "ccchccc"},
						{"ctchctc", "cscccsc", "cscccsc", "cscccsc", "cscccsc", "cscccsc", "ctchctc"},
						{"ccchccc", "ccccccc", "ccccccc", "ccccccc", "ccccccc", "ccccccc", "ccchccc"},
					}))

					.addElement('c', lazy(t -> onElementPass(x -> ++x.mCasing, ofBlock(t.getCasingBlock(), t.getCasingMeta()))))
					.addElement('s', lazy(t -> ofBlock(t.getCasingBlock(), t.getTurbineShaftMeta())))
					.addElement('t', lazy(t -> ofHatchAdder(GregtechMetaTileEntity_LargerTurbineBase::addTurbineHatch, t.getCasingTextureIndex(), 1)))
					.addElement('h', lazy(t -> ofChain(
							ofHatchAdder(GregtechMetaTileEntity_LargerTurbineBase::addGenericHatch, t.getCasingTextureIndex(), 4),
							onElementPass(x -> ++x.mCasing, ofBlock(t.getCasingBlock(), t.getCasingMeta()))
							)))
					.addElement('m', lazy(t -> ofChain(
							ofHatchAdder(GregtechMetaTileEntity_LargerTurbineBase::addMuffler, t.getCasingTextureIndex(), 7),
							onElementPass(x -> ++x.mCasing, ofBlock(t.getCasingBlock(), t.getCasingMeta())))))
					.build();
		}
	};

	@Override
	public IStructureDefinition<GregtechMetaTileEntity_LargerTurbineBase> getStructureDefinition() {
		return STRUCTURE_DEFINITION.get(getClass());
	}

	private boolean requiresMufflers() {
		if (!PollutionUtils.isPollutionEnabled()) {
			return false;
		}
		return getPollutionPerSecond(null) > 0;
	}

	@Override
	public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
		this.mDynamoHatches.clear();
		this.mTurbineRotorHatches.clear();
		this.mMaintenanceHatches.clear();
		if (requiresMufflers()) {
			this.mMufflerHatches.clear();
		}
		this.mInputBusses.clear();
		this.mInputHatches.clear();
		if (requiresOutputHatch()) {
			this.mOutputHatches.clear();
		}
		this.mCasing = 0;

		boolean aStructure = checkPiece(STRUCTURE_PIECE_MAIN, 3, 3, 0);
		boolean aCasingCount = this.mCasing >= 360;
		log("Structure Check: "+aStructure);
		if (!aCasingCount ||
				this.mTurbineRotorHatches.size() != 12 ||
				this.mMaintenanceHatches.size() != 1 ||
				this.mDynamoHatches.size() < 1 ||
				(requiresMufflers() && this.mMufflerHatches.size() != 4) ||
				this.mInputBusses.size() < 1 ||
				this.mInputHatches.size() < 1 ||
				(requiresOutputHatch() && this.mOutputHatches.size() < 1)
				) {
			log("Bad Hatches - Turbine Housings: "+this.mTurbineRotorHatches.size()+
					", Maint: "+this.mMaintenanceHatches.size()+
					", Dynamo: "+this.mDynamoHatches.size()+
					", Muffler: "+this.mMufflerHatches.size()+
					", Input Buses: "+this.mInputBusses.size()+
					", Input Hatches: "+this.mInputHatches.size()+
					", Output Hatches: "+this.mOutputHatches.size()+
					", Casing Count: "+aCasingCount+" | Found: "+this.mCasing);
			return false;
		}
		log("Built "+this.getLocalName()+" with "+this.mCasing+"/360 casings.");
		return  aCasingCount && aStructure;
	}

	@Override
	public void construct(ItemStack stackSize, boolean hintsOnly) {
		buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, 3, 3, 0);
	}

	public boolean addTurbineHatch(final IGregTechTileEntity aTileEntity, final int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		}
		final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) {
			return false;
		}
		if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Turbine) {
			log("Found GT_MetaTileEntity_Hatch_Turbine");
			updateTexture(aTileEntity, aBaseCasingIndex);
			GT_MetaTileEntity_Hatch_Turbine aTurbineHatch = (GT_MetaTileEntity_Hatch_Turbine) aMetaTileEntity;
			IGregTechTileEntity g = this.getBaseMetaTileEntity();
			if (aTurbineHatch.setController(new BlockPos(g.getXCoord(), g.getYCoord(), g.getZCoord(), g.getWorld()))) {
				boolean aDidAdd = this.mTurbineRotorHatches.add(aTurbineHatch);
				Logger.INFO("Injected Controller into Turbine Assembly. Found: "+this.mTurbineRotorHatches.size());
				return aDidAdd;
			}
			else {
				Logger.INFO("Failed to inject controller into Turbine Assembly Hatch.");
			}
		}
		log("Bad Turbine Housing");
		return false;
	}

	public final boolean addMuffler(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		} else {
			IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
			if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Muffler) {
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			}
		}
		return false;
	}

	public final boolean addGenericHatch(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		}
		else {
			IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
			if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Maintenance){
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			}
			else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Input) {
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			}
			else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Output) {
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			}
			else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_InputBus) {
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			}
			else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Dynamo) {
				return addToMachineList(aTileEntity, aBaseCasingIndex);
			}
		}
		log("Bad Hatch");
		return false;
	}

	@Override
	public boolean isCorrectMachinePart(ItemStack aStack) {
		return getMaxEfficiency(aStack) > 0;
	}

	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "LargeTurbine.png");
	}

	public boolean isValidCasingBlock(Block aBlock, int aMeta) {
		if (Block.isEqualTo(aBlock, getCasingBlock()) && aMeta == getCasingMeta()) {
			return true;
		}return false;
	}

	public boolean isValidTurbineShaft(Block aBlock, int aMeta) {
		if (aBlock == getCasingBlock() && aMeta == getTurbineShaftMeta()) {
			return true;
		}
		return false;
	}

	public final Block getCasingBlock() {
		return ModBlocks.blockSpecialMultiCasings;
	}

	@Override
	public final boolean hasSlotInGUI() {
		return true;
	}

	public abstract int getCasingMeta();

	public byte getTurbineShaftMeta() {
		return 0;
	}

	public abstract byte getCasingTextureIndex();

	public abstract int getFuelValue(FluidStack aLiquid);

	public static boolean isValidTurbine(ItemStack aTurbine) {
		return (aTurbine !=null && aTurbine.getItem() instanceof GT_MetaGenerated_Tool  && aTurbine.getItemDamage() >= 170 && aTurbine.getItemDamage() <= 176);
	}

	protected ArrayList<ItemStack> getAllBufferedTurbines(){
		ArrayList<ItemStack> aTurbinesInStorage = new ArrayList<ItemStack>();
		for (GT_MetaTileEntity_Hatch_InputBus aBus: this.mInputBusses) {
			if (isValidMetaTileEntity(aBus)) {
				for (ItemStack aContent : aBus.mInventory) {
					if (isValidTurbine(aContent)) {
						aTurbinesInStorage.add(aContent);
					}
				}
			}
		}
		return aTurbinesInStorage;
	}

	public boolean areAllTurbinesTheSame() {
		ArrayList<GT_MetaTileEntity_Hatch_Turbine> aTurbineAssemblies = getFullTurbineAssemblies();
		if (aTurbineAssemblies.size() < 12) {
			log("Found "+aTurbineAssemblies.size()+", expected 12.");
			return false;
		}
		AutoMap<Materials> aTurbineMats = new AutoMap<Materials>();
		AutoMap<Integer> aTurbineSizes = new AutoMap<Integer>();
		for (GT_MetaTileEntity_Hatch_Turbine aHatch : aTurbineAssemblies) {
			aTurbineMats.add(GT_MetaGenerated_Tool.getPrimaryMaterial(aHatch.getTurbine()));
			aTurbineSizes.add(getTurbineSize(aHatch.getTurbine()));
		}
		Materials aBaseMat = aTurbineMats.get(0);
		int aBaseSize = aTurbineSizes.get(0);
		for (int aSize : aTurbineSizes) {
			if (aBaseSize != aSize) {
				return false;
			}
		}
		for (Materials aMat : aTurbineMats) {
			if (aBaseMat != aMat) {
				return false;
			}
		}
		return true;
	}

	public static int getTurbineSize(ItemStack aTurbine) {
		if (isValidTurbine(aTurbine)) {
			if (aTurbine.getItemDamage() >= 170 && aTurbine.getItemDamage() < 172) {
				return 1;
			}
			else if (aTurbine.getItemDamage() >= 172 && aTurbine.getItemDamage() < 174) {
				return 2;
			}
			else if (aTurbine.getItemDamage() >= 174 && aTurbine.getItemDamage() < 176) {
				return 3;
			}
			else if (aTurbine.getItemDamage() >= 176 && aTurbine.getItemDamage() < 178) {
				return 4;
			}
		}
		return 0;
	}

	public static String getTurbineSizeString(int aSize) {
		switch (aSize) {
		case 1:
			return "Small Turbine";
		case 2:
			return "Turbine";
		case 3:
			return "Large Turbine";
		case 4:
			return "Huge Turbine";
		default:
			return "";
		}
	}

	protected ArrayList<GT_MetaTileEntity_Hatch_Turbine> getEmptyTurbineAssemblies() {
		ArrayList<GT_MetaTileEntity_Hatch_Turbine> aEmptyTurbineRotorHatches = new ArrayList<GT_MetaTileEntity_Hatch_Turbine>();
		//log("Checking "+mTurbineRotorHatches.size()+" Assemblies for empties.");
		for (GT_MetaTileEntity_Hatch_Turbine aTurbineHatch : this.mTurbineRotorHatches) {
			if (!aTurbineHatch.hasTurbine()) {
				//log("Found Assembly without Turbine.");
				aEmptyTurbineRotorHatches.add(aTurbineHatch);
			}
		}
		return aEmptyTurbineRotorHatches;
	}

	protected ArrayList<GT_MetaTileEntity_Hatch_Turbine> getFullTurbineAssemblies() {
		ArrayList<GT_MetaTileEntity_Hatch_Turbine> aTurbineRotorHatches = new ArrayList<GT_MetaTileEntity_Hatch_Turbine>();
		//log("Checking "+mTurbineRotorHatches.size()+" Assemblies for Turbines.");
		for (GT_MetaTileEntity_Hatch_Turbine aTurbineHatch : this.mTurbineRotorHatches) {
			if (aTurbineHatch.hasTurbine()) {
				//log("Found Assembly with Turbine.");
				aTurbineRotorHatches.add(aTurbineHatch);
			}
		}
		return aTurbineRotorHatches;
	}

	protected boolean depleteTurbineFromStock(ItemStack aTurbine) {
		for (GT_MetaTileEntity_Hatch_InputBus aInputBus : this.mInputBusses) {
			for (int slot = 0; slot < aInputBus.mInventory.length; slot++) {
				ItemStack aStack = aInputBus.getStackInSlot(slot);
				if (aStack != null && GT_Utility.areStacksEqual(aStack, aTurbine)) {
					aInputBus.setInventorySlotContents(slot, null);
					updateSlots();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkRecipe(final ItemStack aStack) {
		return checkRecipeGeneric(new ItemStack[] {}, getStoredFluids().toArray(new FluidStack[] {}), 1, 100, 100, 10000);
	}

	@Override
	public boolean canHaveParallelUpgraded() {
		return false;
	}

	@Override
	public boolean checkRecipeGeneric(
			ItemStack[] aItemInputs, FluidStack[] aFluidInputs,
			int aMaxParallelRecipes, int aEUPercent,
			int aSpeedBonusPercent, int aOutputChanceRoll, GT_Recipe aRecipe) {

		try {
			ArrayList<GT_MetaTileEntity_Hatch_Turbine> aEmptyTurbineRotorHatches = getEmptyTurbineAssemblies();
			if (aEmptyTurbineRotorHatches.size() > 0) {
				log("Found "+aEmptyTurbineRotorHatches.size()+" Assemblies without Turbine.");
				ArrayList<ItemStack> aTurbines = getAllBufferedTurbines();
				hatch : for (GT_MetaTileEntity_Hatch_Turbine aHatch : aEmptyTurbineRotorHatches) {
					for (ItemStack aTurbineItem : aTurbines) {
						if (aTurbineItem == null) {
							continue;
						}
						if (aTurbineItem != null && aHatch.insertTurbine(aTurbineItem.copy())) {
							boolean aDidDeplete = depleteTurbineFromStock(aTurbineItem);
							log("Put Turbine into Assembly - "+aDidDeplete);
							continue hatch;
						}
					}
				}
			}
			//log("Found "+getFullTurbineAssemblies().size()+" Assemblies with a Turbine.");

			if (getEmptyTurbineAssemblies().size() > 0 || !areAllTurbinesTheSame()) {
				log("BAD RETURN - 1");
				stopMachine();
				return false;
			}

			//log("Running checkRecipeGeneric(0)");

			ArrayList<FluidStack> tFluids = getStoredFluids();

			if (tFluids.size() > 0) {
				if (this.baseEff == 0 || this.optFlow == 0 || this.counter >= 512 || this.getBaseMetaTileEntity().hasWorkJustBeenEnabled()
						|| this.getBaseMetaTileEntity().hasInventoryBeenModified()) {
					this.counter = 0;

					//log("Running checkRecipeGeneric(1)");
					float aTotalBaseEff = 0;
					float aTotalOptimalFlow = 0;

					ItemStack aStack = getFullTurbineAssemblies().get(0).getTurbine();
					for (int i=0;i<18;i++) {
						if (i == 0) {
							aTotalBaseEff += GT_Utility.safeInt((long) ((5F + ((GT_MetaGenerated_Tool) aStack.getItem()).getToolCombatDamage(aStack)) * 1000F));
							//log("Bumped base eff to "+aTotalBaseEff);
						}
						aTotalOptimalFlow += GT_Utility.safeInt((long) Math.max(Float.MIN_NORMAL,
								((GT_MetaGenerated_Tool) aStack.getItem()).getToolStats(aStack).getSpeedMultiplier()
								* GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mToolSpeed
								* 50));
						//log("Bumped base optimal flow to "+aTotalOptimalFlow);
					}

					//log("Running checkRecipeGeneric(2)");
					//log("Total base eff: "+aTotalBaseEff);
					//log("Total base optimal flow: "+aTotalOptimalFlow);
					this.baseEff = MathUtils.roundToClosestInt(aTotalBaseEff);
					this.optFlow = MathUtils.roundToClosestInt(aTotalOptimalFlow);
					//log("Total eff: "+baseEff);
					//log("Total optimal flow: "+optFlow);
					if(this.optFlow<=0 || this.baseEff<=0){
						log("Running checkRecipeGeneric(bad-1)");
						stopMachine();//in case the turbine got removed
						return false;
					}
				} else {
					this.counter++;
				}
			}

			//log("Total eff: "+baseEff);
			//log("Total optimal flow: "+optFlow);

			// How much the turbine should be producing with this flow
			int newPower = fluidIntoPower(tFluids, this.optFlow, this.baseEff);
			//log("Bumped newPower to "+newPower);
			//log("New Power: "+newPower);
			int difference = newPower - this.mEUt; // difference between current output and new output
			//log("diff: "+difference);

			// Magic numbers: can always change by at least 10 eu/t, but otherwise by at most 1 percent of the difference in power level (per tick)
			// This is how much the turbine can actually change during this tick
			int maxChangeAllowed = Math.max(10, GT_Utility.safeInt((long)Math.abs(difference)/100));
			//log("Max Change Allowed: "+maxChangeAllowed);

			if (Math.abs(difference) > maxChangeAllowed) { // If this difference is too big, use the maximum allowed change
				int change = maxChangeAllowed * (difference > 0 ? 1 : -1); // Make the change positive or negative.
				this.mEUt += change; // Apply the change
				//log("Applied power change.");
			}
			else {
				this.mEUt = newPower;
				//log("Using same value.");
			}
			if (this.mEUt <= 0) {
				this.mEUt=0;
				this.mEfficiency=0;
				log("Running checkRecipeGeneric(bad-2)");
				//stopMachine();
				return false;
			} else {
				this.mMaxProgresstime = 1;
				this.mEfficiencyIncrease = 10;
				// Overvoltage is handled inside the MultiBlockBase when pushing out to dynamos.  no need to do it here.
				// Play sounds (GT++ addition - GT multiblocks play no sounds)
				startProcess();
				//log("GOOD RETURN - Making: "+this.mEUt+" EU/t");
				return true;
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean doRandomMaintenanceDamage() {
		if (getMaxParallelRecipes() == 0 || getRepairStatus() == 0) {
			stopMachine();
			return false;
		}
		if (this.mRuntime++ > 1000) {
			this.mRuntime = 0;
			if (getBaseMetaTileEntity().getRandomNumber(6000) == 0) {
				switch (getBaseMetaTileEntity().getRandomNumber(6)) {
				case 0:
					this.mWrench = false;
					break;
				case 1:
					this.mScrewdriver = false;
					break;
				case 2:
					this.mSoftHammer = false;
					break;
				case 3:
					this.mHardHammer = false;
					break;
				case 4:
					this.mSolderingTool = false;
					break;
				case 5:
					this.mCrowbar = false;
					break;
				}
			}
			for (GT_MetaTileEntity_Hatch_Turbine aHatch : getFullTurbineAssemblies()) {
				aHatch.damageTurbine(this.mEUt, this.damageFactorLow, this.damageFactorHigh);
			}
		}
		return true;
	}

	@Override
	public int getMaxParallelRecipes() {
		return (getFullTurbineAssemblies().size());
	}

	public boolean runRecipe(GT_MetaTileEntity_Hatch_Turbine aHatch) {
		return false;
	}

	abstract int fluidIntoPower(ArrayList<FluidStack> aFluids, int aOptFlow, int aBaseEff);

	@Override
	public int getDamageToComponent(ItemStack aStack) {
		return 1;
	}

	@Override
	public int getMaxEfficiency(ItemStack aStack) {
		return this.getMaxParallelRecipes() == 12 ? 10000 : 0;
	}
	@Override
	public boolean explodesOnComponentBreak(ItemStack aStack) {
		return true;
	}

	public boolean isLooseMode() {
		return false;
	}

	@Override
	public String[] getExtraInfoData() {
		int mPollutionReduction=0;
		for (GT_MetaTileEntity_Hatch_Muffler tHatch : this.mMufflerHatches) {
			if (isValidMetaTileEntity(tHatch)) {
				mPollutionReduction=Math.max(StaticFields59.calculatePollutionReducation(tHatch, 100),mPollutionReduction);
			}
		}

		String tRunning = this.mMaxProgresstime > 0 ?
				EnumChatFormatting.GREEN+StatCollector.translateToLocal("GT5U.turbine.running.true")+EnumChatFormatting.RESET :
					EnumChatFormatting.RED+StatCollector.translateToLocal("GT5U.turbine.running.false")+EnumChatFormatting.RESET;

		String tMaintainance = getIdealStatus() == getRepairStatus() ?
				EnumChatFormatting.GREEN+StatCollector.translateToLocal("GT5U.turbine.maintenance.false")+EnumChatFormatting.RESET :
					EnumChatFormatting.RED+StatCollector.translateToLocal("GT5U.turbine.maintenance.true")+EnumChatFormatting.RESET ;
		int tDura = 0;

		StringBuilder aTurbineDamage = new StringBuilder();
		for (GT_MetaTileEntity_Hatch_Turbine aHatch : this.getFullTurbineAssemblies()) {
			ItemStack aTurbine = aHatch.getTurbine();
			tDura = MathUtils.safeInt((long)(100.0f / GT_MetaGenerated_Tool.getToolMaxDamage(aTurbine) * (GT_MetaGenerated_Tool.getToolDamage(aTurbine))+1));
			aTurbineDamage.append(EnumChatFormatting.RED).append(Integer.toString(tDura)).append(EnumChatFormatting.RESET).append("% | ");
		}

		long storedEnergy=0;
		long maxEnergy=0;
		for(GT_MetaTileEntity_Hatch_Dynamo tHatch : this.mDynamoHatches) {
			if (isValidMetaTileEntity(tHatch)) {
				storedEnergy+=tHatch.getBaseMetaTileEntity().getStoredEU();
				maxEnergy+=tHatch.getBaseMetaTileEntity().getEUCapacity();
			}
		}

		boolean aIsSteam = this.getClass().getName().toLowerCase().contains("steam");

		String[] ret = new String[]{
				// 8 Lines available for information panels
				tRunning + ": " + EnumChatFormatting.RED+this.mEUt+EnumChatFormatting.RESET+" EU/t",
				tMaintainance,
				StatCollector.translateToLocal("GT5U.turbine.efficiency")+": "+EnumChatFormatting.YELLOW+(this.mEfficiency/100F)+EnumChatFormatting.RESET+"%",
				StatCollector.translateToLocal("GT5U.multiblock.energy")+": " + EnumChatFormatting.GREEN + Long.toString(storedEnergy) + EnumChatFormatting.RESET +" EU / "+
						EnumChatFormatting.YELLOW + Long.toString(maxEnergy) + EnumChatFormatting.RESET +" EU",
						StatCollector.translateToLocal("GT5U.turbine.flow")+": "+EnumChatFormatting.YELLOW+MathUtils.safeInt((long)this.realOptFlow)+EnumChatFormatting.RESET+" L/t" +
								EnumChatFormatting.YELLOW+" ("+(isLooseMode()?StatCollector.translateToLocal("GT5U.turbine.loose"):StatCollector.translateToLocal("GT5U.turbine.tight"))+")",
								StatCollector.translateToLocal("GT5U.turbine.fuel")+": "+EnumChatFormatting.GOLD+this.storedFluid+EnumChatFormatting.RESET+"L",
								StatCollector.translateToLocal("GT5U.turbine.dmg") + ": " + aTurbineDamage.toString(),
								StatCollector.translateToLocal("GT5U.multiblock.pollution")+": "+ EnumChatFormatting.GREEN + mPollutionReduction+ EnumChatFormatting.RESET+" %"
		};
		if (!aIsSteam)
			ret[4]=StatCollector.translateToLocal("GT5U.turbine.flow")+": "+EnumChatFormatting.YELLOW+MathUtils.safeInt((long)this.realOptFlow)+EnumChatFormatting.RESET+" L/t";
		return ret;


	}

	@Override
	public boolean isGivingInformation() {
		return true;
	}


	@Override
	public boolean polluteEnvironment(int aPollutionLevel) {
		if (this.requiresMufflers()) {
			this.mPollution += aPollutionLevel;
			for (GT_MetaTileEntity_Hatch_Muffler tHatch : this.mMufflerHatches) {
				if (isValidMetaTileEntity(tHatch)) {
					if (this.mPollution >= 10000) {
						if (PollutionUtils.addPollution(this.getBaseMetaTileEntity(), 10000)) {
							this.mPollution -= 10000;
						}
					} else {
						break;
					}
				}
			}
			return this.mPollution < 10000;
		}
		return true;
	}
	@Override
	public long maxAmperesOut() {
		return 16;
	}


	@Override
	public void onModeChangeByScrewdriver(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		if (!KeyboardUtils.isShiftKeyDown()) {
			//super.onModeChangeByScrewdriver(aSide, aPlayer, aX, aY, aZ);
		}
		else {
			/*
			this.mIsAnimated = Utils.invertBoolean(mIsAnimated);
			if (this.mIsAnimated) {
			PlayerUtils.messagePlayer(aPlayer, "Using Animated Turbine Texture.");
			}
			else {
			PlayerUtils.messagePlayer(aPlayer, "Using Static Turbine Texture.");
			}
			if (mTurbineRotorHatches.size() > 0) {
			for (GT_MetaTileEntity_Hatch_Turbine h : mTurbineRotorHatches) {
				if (h != null) {
					h.mUsingAnimation = mIsAnimated;
				}
			}
			}
			 */}
	}

	@Override
	public final ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[1][aColorIndex + 1], aFacing == aSide ? getFrontFacingTurbineTexture(aActive) : Textures.BlockIcons.getCasingTextureForId(getCasingTextureIndex())};
	}

	protected ITexture getFrontFacingTurbineTexture(boolean isActive) {
		if (isActive) {
			return this.frontFaceActive;
		}
		return this.frontFace;
	}

	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		if (aBaseMetaTileEntity.isServerSide()) {
			if (this.mUpdate == 1 || this.mStartUpCheck == 1) {
				log("Cleared Rotor Assemblies.");
				this.mTurbineRotorHatches.clear();
			}
		}
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (this.maxProgresstime() > 0 || this.getBaseMetaTileEntity().hasWorkJustBeenEnabled()) {
			enableAllTurbineHatches();
		}
		if (this.maxProgresstime() <= 0) {
			stopMachine();
		}

	}
	@Override
	public void startProcess() {
		super.startProcess();
		enableAllTurbineHatches();
	}

	@Override
	public void stopMachine() {
		this.baseEff = 0;
		this.optFlow = 0;
		disableAllTurbineHatches();
		super.stopMachine();
	}
	@Override
	public void onRemoval() {
		super.onRemoval();
		for (GT_MetaTileEntity_Hatch_Turbine h : this.mTurbineRotorHatches) {
			h.clearController();
		}
		disableAllTurbineHatches();
		this.mTurbineRotorHatches.clear();
	}

	public boolean enableAllTurbineHatches() {
		return updateTurbineHatches(this.isMachineRunning()) > 0;
	}
	public boolean disableAllTurbineHatches() {
		return updateTurbineHatches(false) > 0;
	}

	private Long mLastHatchUpdate;
	public int updateTurbineHatches(boolean aState) {
		int aUpdated = 0;
		if (this.mLastHatchUpdate == null) {
			this.mLastHatchUpdate = System.currentTimeMillis()/1000;
		}
		if (this.mTurbineRotorHatches.isEmpty() || ((System.currentTimeMillis()/1000)-this.mLastHatchUpdate) <= 2) {
			return 0;
		}
		for (GT_MetaTileEntity_Hatch_Turbine h : this.mTurbineRotorHatches) {
			h.setActive(aState);
			aUpdated++;
		}

		this.mLastHatchUpdate = System.currentTimeMillis()/1000;
		return aUpdated;
	}

	@Override
	public int getEuDiscountForParallelism() {
		return 0;
	}

	@Override
	public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPreTick(aBaseMetaTileEntity, aTick);
		// Fix GT bug
		if (this.getBaseMetaTileEntity().getFrontFacing() != 1) {
			log("Fixing Bad Facing. (GT Bug)");
			this.getBaseMetaTileEntity().setFrontFacing((byte) 1);
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
		return false;
	}

	@Override
	public boolean addEnergyOutput(long aEU) {
		if (aEU <= 0) {
			return true;
		}
		if (this.mAllDynamoHatches.size() > 0) {
			return addEnergyOutputMultipleDynamos(aEU, true);
		}
		return false;
	}

	@Override
	public boolean addEnergyOutputMultipleDynamos(long aEU, boolean aAllowMixedVoltageDynamos) {
		int injected = 0;
		long totalOutput = 0;
		long aFirstVoltageFound = -1;
		boolean aFoundMixedDynamos = false;
		for (GT_MetaTileEntity_Hatch aDynamo : this.mAllDynamoHatches) {
			if(aDynamo == null) {
				return false;
			}
			if (isValidMetaTileEntity(aDynamo)) {
				long aVoltage = aDynamo.maxEUOutput();
				long aTotal = aDynamo.maxAmperesOut() * aVoltage;
				// Check against voltage to check when hatch mixing
				if (aFirstVoltageFound == -1) {
					aFirstVoltageFound = aVoltage;
				}
				else {
					if (aFirstVoltageFound != aVoltage) {
						aFoundMixedDynamos = true;
					}
				}
				totalOutput += aTotal;
			}
		}

		if (totalOutput < aEU || (aFoundMixedDynamos && !aAllowMixedVoltageDynamos)) {
			explodeMultiblock();
			return false;
		}

		long leftToInject;
		long aVoltage;
		int aAmpsToInject;
		int aRemainder;
		int ampsOnCurrentHatch;
		for (GT_MetaTileEntity_Hatch aDynamo : this.mAllDynamoHatches) {
			if (isValidMetaTileEntity(aDynamo)) {
				leftToInject = aEU - injected;
				aVoltage = aDynamo.maxEUOutput();
				aAmpsToInject = (int) (leftToInject / aVoltage);
				aRemainder = (int) (leftToInject - (aAmpsToInject * aVoltage));
				ampsOnCurrentHatch= (int) Math.min(aDynamo.maxAmperesOut(), aAmpsToInject);
				for (int i = 0; i < ampsOnCurrentHatch; i++) {
					aDynamo.getBaseMetaTileEntity().increaseStoredEnergyUnits(aVoltage, false);
				}
				injected+=aVoltage*ampsOnCurrentHatch;
				if(aRemainder>0 && ampsOnCurrentHatch<aDynamo.maxAmperesOut()){
					aDynamo.getBaseMetaTileEntity().increaseStoredEnergyUnits(aRemainder, false);
					injected+=aRemainder;
				}
			}
		}
		return injected > 0;
	}

}
