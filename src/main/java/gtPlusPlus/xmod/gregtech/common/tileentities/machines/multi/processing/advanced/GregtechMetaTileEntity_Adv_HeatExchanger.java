package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing.advanced;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdder;

import com.gtnewhorizon.structurelib.alignment.IAlignmentLimits;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTech_API;
import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.util.*;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.core.block.base.BasicBlock.BlockTypes;
import gtPlusPlus.core.block.base.BlockBaseModular;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.material.ALLOY;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import gtPlusPlus.xmod.gregtech.api.objects.GTPP_RenderedTexture;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GregtechMetaTileEntity_Adv_HeatExchanger extends GregtechMeta_MultiBlockBase<GregtechMetaTileEntity_Adv_HeatExchanger> {
    
	private static final int CASING_INDEX = TAE.getIndexFromPage(1, 12);
    private static final String STRUCTURE_PIECE_MAIN = "main";
    
    private static final IStructureDefinition<GregtechMetaTileEntity_Adv_HeatExchanger> STRUCTURE_DEFINITION = StructureDefinition.<GregtechMetaTileEntity_Adv_HeatExchanger>builder()
            .addShape(STRUCTURE_PIECE_MAIN, transpose(new String[][]{
                {" ccc ", "cCCCc", "cCCCc", "cCCCc", " ccc "},
                {" ccc ", "cPPPc", "cPPPc", "cPPPc", " ccc "},
                {" ccc ", "cPPPc", "cPPPc", "cPPPc", " ccc "},
                {" ccc ", "cPPPc", "cPPPc", "cPPPc", " ccc "},
                {" ccc ", "cPPPc", "cPPPc", "cPPPc", " ccc "},
                {" c~c ", "cPPPc", "cPPPc", "cPPPc", " ccc "},
                {" hhh ", "hHHHh", "hHHHh", "hHHHh", " hhh "},
                {" f f ", "f   f", "     ", "f   f", " f f "},
                {" f f ", "f   f", "     ", "f   f", " f f "},
            }))
            .addElement('P', ofBlock(GregTech_API.sBlockCasings2, 15))
            .addElement('f', ofBlock(getFrame(), 0))
            .addElement('C', ofChain(
                    ofHatchAdder(GregtechMetaTileEntity_Adv_HeatExchanger::addColdFluidOutputToMachineList, CASING_INDEX, 2),
                    onElementPass(GregtechMetaTileEntity_Adv_HeatExchanger::onCasingAdded, ofBlock(ModBlocks.blockSpecialMultiCasings, 14))
                    ))
            .addElement('H', ofChain(
                    ofHatchAdder(GregtechMetaTileEntity_Adv_HeatExchanger::addHotFluidInputToMachineList, CASING_INDEX, 3),
                    onElementPass(GregtechMetaTileEntity_Adv_HeatExchanger::onCasingAdded, ofBlock(ModBlocks.blockSpecialMultiCasings, 14))
                    ))
            .addElement('h', ofChain(
                    ofHatchAdder(GregtechMetaTileEntity_Adv_HeatExchanger::addInputToMachineList, CASING_INDEX, 1),
                    ofHatchAdder(GregtechMetaTileEntity_Adv_HeatExchanger::addOutputToMachineList, CASING_INDEX, 1),
                    ofHatchAdder(GregtechMetaTileEntity_Adv_HeatExchanger::addMaintenanceToMachineList, CASING_INDEX, 1),
                    onElementPass(GregtechMetaTileEntity_Adv_HeatExchanger::onCasingAdded, ofBlock(ModBlocks.blockSpecialMultiCasings, 14))
            ))
            .addElement('c', ofChain(
                    onElementPass(GregtechMetaTileEntity_Adv_HeatExchanger::onCasingAdded, ofBlock(ModBlocks.blockSpecialMultiCasings, 14))
            ))
            .build();
    public static float penalty_per_config = 0.015f;  // penalize 1.5% efficiency per circuitry level (1-25)

    private GT_MetaTileEntity_Hatch_Input mInputHotFluidHatch;
    private GT_MetaTileEntity_Hatch_Output mOutputColdFluidHatch;
    private boolean superheated = false;
    private int superheated_threshold=0;
    private float water;
    private int mCasingAmount;

    public GregtechMetaTileEntity_Adv_HeatExchanger(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    public GregtechMetaTileEntity_Adv_HeatExchanger(String aName) {
        super(aName);
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType(getMachineType())
                .addInfo("Controller Block for the XL Heat Exchanger")
                .addInfo("More complicated than a Fusion Reactor. Seriously")
                .addInfo("But you know this by now, right?")
                .addInfo("Works as fast as 16 Large Heat Exchangers")
                .addSeparator()
                .addInfo("Inputs are Hot Coolant or Lava")
                .addInfo("Outputs Coolant or Pahoehoe Lava and SH Steam/Steam")
                .addInfo("Read the wiki article to understand how it works")
                .addInfo("Then go to the Discord to understand the wiki")
                .addSeparator()
                .beginStructureBlock(5, 9, 5, false)
                .addController("Front bottom")
                .addCasingInfo("Reinforced Heat Exchanger Casing", 90)
                .addOtherStructurePart("Tungstensteel Pipe Casing", "Center 3x5x3 (45 blocks)")
                .addMaintenanceHatch("Any casing", 1)
                .addInputHatch("Hot fluid, bottom center", 2)
                .addInputHatch("Distilled water, any bottom layer casing", 1)
                .addOutputHatch("Cold fluid, top center", 3)
                .addOutputHatch("Steam/SH Steam, any bottom layer casing", 1)
                .toolTipFinisher(CORE.GT_Tooltip_Builder);
        return tt;
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        superheated = aNBT.getBoolean("superheated");
        super.loadNBTData(aNBT);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setBoolean("superheated", superheated);
        super.saveNBTData(aNBT);
    }

	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.getCasingTextureForId(CASING_INDEX), new GTPP_RenderedTexture(aActive ? TexturesGtBlock.Overlay_Machine_Controller_Advanced_Active : TexturesGtBlock.Overlay_Machine_Controller_Advanced)};
		}
		return new ITexture[]{Textures.BlockIcons.getCasingTextureForId(CASING_INDEX)};
	}

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "LargeHeatExchanger.png");
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (d, r, f) -> !r.isUpsideDown() && !f.isVerticallyFliped();
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        if (mInputHotFluidHatch.getFluid() == null)
            return true;

        int fluidAmountToConsume = mInputHotFluidHatch.getFluidAmount(); // how much fluid is in hatch

        // The XL LHE works as fast as 16 regular LHEs. These are the comments from the original LHE,
        // with changes where the values needed to change for the 16x speed multiplier
        superheated_threshold = 64000;   // default: must have 4000L -> 64000L per second to generate superheated steam
        float efficiency = 1f;              // default: operate at 100% efficiency with no integrated circuitry
        int shs_reduction_per_config = 2400; // reduce threshold 150L -> 2400L per second per circuitry level (1-25)
        float steam_output_multiplier = 20f; // default: multiply output by 4 * 10 (boosted x5)
        float penalty = 0.0f;               // penalty to apply to output based on circuitry level (1-25).
        boolean do_lava = false;

        // Do we have an integrated circuit with a valid configuration?
        if (mInventory[1] != null && mInventory[1].getUnlocalizedName().startsWith("gt.integrated_circuit")) {
            int circuit_config = mInventory[1].getItemDamage();
            if (circuit_config >= 1 && circuit_config <= 25) {
                // If so, apply the penalty and reduced threshold.
                penalty = (circuit_config - 1) * penalty_per_config;
                superheated_threshold -= (shs_reduction_per_config * (circuit_config - 1));
            }
        }
        efficiency -= penalty;

        // If we're working with lava, adjust the threshold and multipliers accordingly.
        if (GT_ModHandler.isLava(mInputHotFluidHatch.getFluid())) {
            steam_output_multiplier /= 5f; // lava is not boosted
            superheated_threshold /= 4f; // unchanged
            do_lava = true;
        } else if (mInputHotFluidHatch.getFluid().isFluidEqual(FluidRegistry.getFluidStack("ic2hotcoolant", 1))) {
            steam_output_multiplier /= 2f; // was boosted x2 on top of x5 -> total x10 -> nerf with this code back to 5x
            superheated_threshold /=5f; // 10x smaller since the Hot Things production in reactor is the same.
        } else {
            // If we're working with neither, fail out
            superheated_threshold=0;
            return false;
        }

        superheated = fluidAmountToConsume >= superheated_threshold; // set the internal superheated flag if we have enough hot fluid.  Used in the onRunningTick method.
        fluidAmountToConsume = Math.min(fluidAmountToConsume, superheated_threshold * 2); // Don't consume too much hot fluid per second, maximum is 2x SH threshold.
        mInputHotFluidHatch.drain(fluidAmountToConsume, true);
        this.mMaxProgresstime = 20;
        this.mEUt = (int) (fluidAmountToConsume * steam_output_multiplier * efficiency);
        if (do_lava) {
            mOutputColdFluidHatch.fill(FluidRegistry.getFluidStack("ic2pahoehoelava", fluidAmountToConsume), true);
        } else {
            mOutputColdFluidHatch.fill(FluidRegistry.getFluidStack("ic2coolant", fluidAmountToConsume), true);
        }
        this.mEfficiencyIncrease = 80;
        return true;
    }

    private int useWater(float input) {
        water = water + input;
        int usage = (int) water;
        water = water - usage;
        return usage;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (this.mEUt > 0) {
            int tGeneratedEU = (int) (this.mEUt * 2L * this.mEfficiency / 10000L); // APPROXIMATELY how much steam to generate.
            if (tGeneratedEU > 0) {

                if (superheated) tGeneratedEU /= 2; // We produce half as much superheated steam if necessary

                int distilledConsumed = useWater(tGeneratedEU / 160f); // how much distilled water to consume
                //tGeneratedEU = distilledConsumed * 160; // EXACTLY how much steam to generate, producing a perfect 1:160 ratio with distilled water consumption

                FluidStack distilledStack = GT_ModHandler.getDistilledWater(distilledConsumed);
                if (depleteInput(distilledStack)) // Consume the distilled water
                {
                    if (superheated) {
                        addOutput(FluidRegistry.getFluidStack("ic2superheatedsteam", tGeneratedEU)); // Generate superheated steam
                    } else {
                        addOutput(GT_ModHandler.getSteam(tGeneratedEU)); // Generate regular steam
                    }
                } else {
                    GT_Log.exp.println(this.mName+" had no more Distilled water!");
                    explodeMultiblock(); // Generate crater
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public IStructureDefinition<GregtechMetaTileEntity_Adv_HeatExchanger> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    private void onCasingAdded() {
        mCasingAmount++;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mOutputColdFluidHatch = null;
        mInputHotFluidHatch = null;
        mCasingAmount = 0;
        return checkPiece(STRUCTURE_PIECE_MAIN, 2, 5, 0) && mCasingAmount >= 90 && mMaintenanceHatches.size() == 1;
    }

    public boolean addColdFluidOutputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Output) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            mOutputColdFluidHatch = (GT_MetaTileEntity_Hatch_Output) aMetaTileEntity;
            return true;
        }
        return false;
    }

    public boolean addHotFluidInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Input) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            ((GT_MetaTileEntity_Hatch_Input) aMetaTileEntity).mRecipeMap = getRecipeMap();
            mInputHotFluidHatch = (GT_MetaTileEntity_Hatch_Input) aMetaTileEntity;
            return true;
        }
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
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GregtechMetaTileEntity_Adv_HeatExchanger(this.mName);
    }

    @Override
    public boolean isGivingInformation() {
        return super.isGivingInformation();
    }

    @Override
    public String[] getExtraInfoData() {
        return new String[]{
                StatCollector.translateToLocal("GT5U.multiblock.Progress") + ": " +
                        EnumChatFormatting.GREEN + GT_Utility.formatNumbers(mProgresstime / 20) + EnumChatFormatting.RESET + " s / " +
                        EnumChatFormatting.YELLOW + GT_Utility.formatNumbers(mMaxProgresstime / 20) + EnumChatFormatting.RESET + " s",
                StatCollector.translateToLocal("GT5U.multiblock.usage") + " " + StatCollector.translateToLocal("GT5U.LHE.steam") + ": " +
                        (superheated ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW) + GT_Utility.formatNumbers(superheated ? -2 * mEUt : -mEUt) + EnumChatFormatting.RESET + " EU/t",
                StatCollector.translateToLocal("GT5U.multiblock.problems") + ": " +
                        EnumChatFormatting.RED + (getIdealStatus() - getRepairStatus()) + EnumChatFormatting.RESET + " " +
                        StatCollector.translateToLocal("GT5U.multiblock.efficiency") + ": " +
                        EnumChatFormatting.YELLOW + mEfficiency / 100.0F + EnumChatFormatting.RESET + " %",
                StatCollector.translateToLocal("GT5U.LHE.superheated") + ": " +
                        (superheated ? EnumChatFormatting.RED : EnumChatFormatting.BLUE) + superheated + EnumChatFormatting.RESET,
                StatCollector.translateToLocal("GT5U.LHE.superheated") + " " + StatCollector.translateToLocal("GT5U.LHE.threshold") + ": " +
                        EnumChatFormatting.GREEN + GT_Utility.formatNumbers(superheated_threshold) + EnumChatFormatting.RESET
        };
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, 2, 5, 0);
    }
	@Override
	public boolean hasSlotInGUI() {
		return true;
	}
	@Override
	public String getCustomGUIResourceName() {
		return null;
	}
	@Override
	public String getMachineType() {
		return "Heat Exchanger";
	}
	@Override
	public int getMaxParallelRecipes() {
		return 0;
	}
	@Override
	public int getEuDiscountForParallelism() {
		return 0;
	}
	
	private static Block sFrame;
	
	public static Block getFrame() {
		if (sFrame == null) {
			sFrame = BlockBaseModular.getMaterialBlock(ALLOY.TALONITE, BlockTypes.FRAME);
		}
		return sFrame;
	}
}
