package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.enums.GT_HatchElement.*;
import static gregtech.api.util.GT_StructureUtility.buildHatchAdder;
import static gregtech.api.util.GT_StructureUtility.filterByMTETier;

import java.util.ArrayList;
import java.util.List;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.*;

import gregtech.api.GregTech_API;
import gregtech.api.enums.TAE;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Muffler;
import gregtech.api.util.GTPP_Recipe;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Recipe;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.GregtechMetaTileEntity_Refinery;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

public class GregtechMetaTileEntity_QuantumForceTransformer extends GregtechMeta_MultiBlockBase<GregtechMetaTileEntity_QuantumForceTransformer> implements ISurvivalConstructable {

    private int mCasing;
    protected int mChamberTier = 0;
    protected int mFabCoilTier = 0;
    protected int mGlassTier = 0;
    protected int mMinimumMufflerTier = 0;
    private IStructureDefinition<GregtechMetaTileEntity_QuantumForceTransformer> STRUCTURE_DEFINITION = null;


    public GregtechMetaTileEntity_QuantumForceTransformer(final int aID, final String aName, final String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GregtechMetaTileEntity_QuantumForceTransformer(final String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(final IGregTechTileEntity aTileEntity) {
        return new GregtechMetaTileEntity_QuantumForceTransformer(this.mName);
    }

    @Override
    public String getMachineType() {
        return "Fluid Alloy Cooker";
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType(getMachineType())
                .addInfo("Controller Block for the Quantum Force Smelter")
                .addInfo("Can perform a maximum of "+getMaxParallelRecipes()+" recipes in parallel")
                .addInfo("Allows Complex GT++ alloys to be created")
                .addInfo("Accepts TecTech Energy and Laser Hatches")
                .addInfo("Each input bus can support a unique Circuit")
                .addInfo("This multi gives bonuses when all casings of some types are upgraded")
                .addInfo("Switch these casings with these replacements to get bonuses:")
                .addInfo("Particle Containment Casing (pink) -> Containment Casing (blue)")
                .addInfo("Naquadah Containment Casing -> Matter Fabrication Casing ")
                .addInfo("Resonance Chamber III -> Resonance Chamber IV")
                .addPollutionAmount(getPollutionPerSecond(null))
                .addSeparator()
                .beginStructureBlock(15, 21, 15, true) // @Steelux TODO
                .addController("Bottom Center")
                .addCasingInfo("Bulk Production Frame", 96)
                .addCasingInfo("Quantum Force Conductor", 177)
                .addCasingInfo("Particle Containment Casing", 224)
                .addCasingInfo("Naquadah Containment Casing", 234)
                .addCasingInfo("Resonance Chamber III", 142)
                .addInputBus("Bottom Layer", 4)
                .addInputHatch("Bottom Layer", 4)
                .addOutputHatch("Bottom Layer", 4)
                .addEnergyHatch("Bottom Layer", 4)
                .addMaintenanceHatch("Bottom Layer", 4)
                .addMufflerHatch("Top Layer (except edges), x21", 5)
                .toolTipFinisher(CORE.GT_Tooltip_Builder);
        return tt;
    }

    @Override
    public IStructureDefinition<GregtechMetaTileEntity_QuantumForceTransformer> getStructureDefinition() {
        if (this.STRUCTURE_DEFINITION == null) {
            this.STRUCTURE_DEFINITION = StructureDefinition.<GregtechMetaTileEntity_QuantumForceTransformer>builder()
                    .addShape(this.mName, new String[][]{ // A - 142, B - 234, C - 177, D - 96, E - 224, H - 36, M - 21
                            {"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","   AAAABAAAA   ","   ABBBBBBBA   ","   ABAABAABA   ","   AB     BA   ","    B     B    ","    B     B    ","    B     B    "},
                            {"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","   BBBAAABBB   ","  ABBBBBBBBBA  ","  A         A  ","  B         B  ","  B         B  ","               ","               ","               "},
                            {"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","    BB   BB    ","  BB       BB  "," ABB       BBA "," A           A "," B           B "," B           B ","               ","               ","               "},
                            {"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","     ABBBA     ","   BB     BB   "," BB         BB ","ABB         BBA","A             A","B             B","B             B","B             B","B             B","B             B"},
                            {"      DDD      ","      EEE      ","      EEE      ","      EEE      ","      DDD      ","      EEE      ","      DDD      ","      EEE      ","      EEE      ","      EEE      ","      DDD      ","    ABEEEBA    ","  BB  EEE  BB  "," B    EEE    B ","AB    DDD    BA","A     EEE     A","A     DDD     A","      EEE      ","      EEE      ","      EEE      ","      H~H      "},
                            {"     DMMMD     ","     ECCCE     ","     ECCCE     ","     ECCCE     ","     D   D     ","     ECCCE     ","     D   D     ","     ECCCE     ","     ECCCE     ","     ECCCE     ","     D   D     ","   ABECCCEBA   ","  B  ECCCE  B  "," B   ECCCE   B ","AB   D   D   BA","A    ECCCE    A","A    D   D    A","A    ECCCE    A","     ECCCE     ","     ECCCE     ","     HHHHH     "},
                            {"    DMMMMMD    ","    ECCCCCE    ","    EC   CE    ","    EC   CE    ","    D     D    ","    EC   CE    ","    D     D    ","    EC   CE    ","    EC   CE    ","    EC   CE    ","    D     D    ","  ABEC   CEBA  "," A  EC   CE  A ","AA  EC   CE  AA","AB  D     D  BA","B   EC   CE   B","B   D     D   B","B   EC   CE   B","    EC   CE    ","    EC   CE    ","    HHHHHHH    "},
                            {"    DMMMMMD    ","    ECCCCCE    ","    EC   CE    ","    EC   CE    ","    D     D    ","    EC   CE    ","    D     D    ","    EC   CE    ","    EC   CE    ","    EC   CE    ","    D     D    ","  BBEC   CEBB  "," B  EC   CE  B ","BA  EC   CE  AB","BB  D     D  BB","B   EC   CE   B","B   D     D   B","    EC   CE    ","    EC   CE    ","    EC   CE    ","    HHHHHHH    "},
                            {"    DMMMMMD    ","    ECCCCCE    ","    EC   CE    ","    EC   CE    ","    D     D    ","    EC   CE    ","    D     D    ","    EC   CE    ","    EC   CE    ","    EC   CE    ","    D     D    ","  ABEC   CEBA  "," A  EC   CE  A ","AA  EC   CE  AA","AB  D     D  BA","B   EC   CE   B","B   D     D   B","B   EC   CE   B","    EC   CE    ","    EC   CE    ","    HHHHHHH    "},
                            {"     DMMMD     ","     ECCCE     ","     ECCCE     ","     ECCCE     ","     D   D     ","     ECCCE     ","     D   D     ","     ECCCE     ","     ECCCE     ","     ECCCE     ","     D   D     ","   ABECCCEBA   ","  B  ECCCE  B  "," B   ECCCE   B ","AB   D   D   BA","A    ECCCE    A","A    D   D    A","A    ECCCE    A","     ECCCE     ","     ECCCE     ","     HHHHH     "},
                            {"      DDD      ","      EEE      ","      EEE      ","      EEE      ","      DDD      ","      EEE      ","      DDD      ","      EEE      ","      EEE      ","      EEE      ","      DDD      ","    ABEEEBA    ","  BB  EEE  BB  "," B    EEE    B ","AB    DDD    BA","A     EEE     A","A     DDD     A","      EEE      ","      EEE      ","      EEE      ","      HHH      "},
                            {"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","     ABBBA     ","   BB     BB   "," BB         BB ","ABB          BA","A             A","B             B","B             B","B             B","B             B","B             B"},
                            {"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","    BB   BB    ","  BB       BB  "," ABB        BA "," A           A "," B           B "," B           B ","               ","               ","               "},
                            {"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","   BBBAAABBB   ","  ABBBBBBBBBA  ","  A         A  ","  B         B  ","  B         B  ","               ","               ","               "},
                            {"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","   AAAABAAAA   ","   AAABBBAAA   ","   BAABBBAAB   ","   B AB BA B   ","      B B      ","      B B      ","      B B      "},
                    })
                    .addElement(
                            'A',
                            StructureUtility.ofBlocksTiered(
                                    chamberTierConverter(),
                                    getAllChamberTiers(),
                                    0,
                                    GregtechMetaTileEntity_QuantumForceTransformer::setChamberTier,
                                    GregtechMetaTileEntity_QuantumForceTransformer::getChamberTier))
                    .addElement(
                            'B',
                            StructureUtility.ofBlocksTiered(
                                    fabCoilTierConverter(),
                                    getAllFabCoilTiers(),
                                    0,
                                    GregtechMetaTileEntity_QuantumForceTransformer::setFabCoilTier,
                                    GregtechMetaTileEntity_QuantumForceTransformer::getFabCoilTier))
                    .addElement(
                            'C',
                            ofBlock(
                                    ModBlocks.blockSpecialMultiCasings, 15
                            )
                    )
                    .addElement(
                            'D',
                            ofBlock(
                                    ModBlocks.blockCasings2Misc, 12
                            )
                    )
                    .addElement(
                            'E',
                            StructureUtility.ofBlocksTiered(
                                    glassTierConverter(),
                                    getAllGlassTiers(),
                                    0,
                                    GregtechMetaTileEntity_QuantumForceTransformer::setGlassTier,
                                    GregtechMetaTileEntity_QuantumForceTransformer::getGlassTier))
                    .addElement(
                            'H',
                            buildHatchAdder(GregtechMetaTileEntity_QuantumForceTransformer.class)
                                    .atLeast(InputBus, InputHatch, OutputHatch, Maintenance, Energy)
                                    .casingIndex(TAE.getIndexFromPage(0, 10))
                                    .dot(4)
                                    .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(ModBlocks.blockCasings2Misc, 12)))
                    )
                    .addElement(
                            'M',
                            buildHatchAdder(GregtechMetaTileEntity_QuantumForceTransformer.class)
                                    .atLeast(Muffler)
                                    .adder(GregtechMetaTileEntity_QuantumForceTransformer::addMufflerToMachineList)
                                    .casingIndex(TAE.getIndexFromPage(0, 10))
                                    .dot(5)
                                    .build()
                    )
                    .build();
        }
        return this.STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        this.mCasing = 0;
        this.mMinimumMufflerTier = 0;
        if (checkPiece(this.mName, 7, 20, 4) &&
                checkHatch() &&
                mMufflerHatches.size() == 21 &&
                (mTecTechEnergyHatches.size() >= 1 || mEnergyHatches.size() >= 1)) {
            return true;
        }
        return false;
    }

    @Override // The lowest tier muffler of the total 21 will decide the muffler tier in the multi
    public boolean addMufflerToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Muffler) {
                if (mMinimumMufflerTier == 0 || ((GT_MetaTileEntity_Hatch_Muffler) aMetaTileEntity).mTier < mMinimumMufflerTier) {
                    mMinimumMufflerTier = ((GT_MetaTileEntity_Hatch_Muffler) aMetaTileEntity).mTier;
                }
                return addToMachineList(aTileEntity, aBaseCasingIndex);
            }
        }
        return false;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(this.mName , stackSize, hintsOnly, 7, 20, 4);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, IItemSource source, EntityPlayerMP actor) {
        if (this.mMachine) return -1;
        return survivialBuildPiece(this.mName, stackSize, 7, 20, 4, elementBudget, source, actor, false, true);
    }

    public static List<Pair<Block, Integer>> getAllChamberTiers() {
        return new ArrayList<Pair<Block, Integer>>() {
            {
                add(Pair.of(ModBlocks.blockSpecialMultiCasings2, 2));
                add(Pair.of(ModBlocks.blockSpecialMultiCasings2, 3));
            }
        };
    }

    public static List<Pair<Block, Integer>> getAllFabCoilTiers() {
        return new ArrayList<Pair<Block, Integer>>() {
            {
                add(Pair.of(ModBlocks.blockCasings4Misc, 2));
                add(Pair.of(ModBlocks.blockCasingsMisc, 8));
            }
        };
    }

    public static List<Pair<Block, Integer>> getAllGlassTiers() {
        return new ArrayList<Pair<Block, Integer>>() {
            {
                add(Pair.of(ModBlocks.blockSpecialMultiCasings, 13));
                add(Pair.of(ModBlocks.blockCasings3Misc, 15));
            }
        };
    }

    public static ITierConverter<Integer> chamberTierConverter() {
        return (block, meta) -> {
            if (block == null) {
                return -1;
            } else if (block == ModBlocks.blockSpecialMultiCasings2) { // Resonance Chambers
                switch (meta) {
                    case 2:
                        return 1;
                    case 3:
                        return 2;
                }
            }
            return -1;
        };
    }

    public static ITierConverter<Integer> fabCoilTierConverter() {
        return (block, meta) -> {
            if (block == null) {
                return -1;
            } else if (block == ModBlocks.blockCasingsMisc || block == ModBlocks.blockCasings4Misc) { // Generation Coils
                switch (meta) {
                    case 2:
                        return 1;
                    case 8:
                        return 2;
                }
            }
            return -1;
        };
    }

    public static ITierConverter<Integer> glassTierConverter() {
        return (block, meta) -> {
            if (block == null) {
                return -1;
            } else if (block == ModBlocks.blockSpecialMultiCasings || block == ModBlocks.blockCasings3Misc) { // Glass
                switch (meta) {
                    case 13:
                        return 1;
                    case 15:
                        return 2;
                }
            }
            return -1;
        };
    }

    public void setChamberTier(int tier) { mChamberTier = tier; }

    public void setFabCoilTier(int tier) { mFabCoilTier = tier; }

    public void setGlassTier(int tier) { mGlassTier = tier; }

    public int getChamberTier() { return mChamberTier; }

    public int getFabCoilTier() { return mFabCoilTier; }

    public int getGlassTier() { return mGlassTier; }

    @Override
    public String getSound() {
        return GregTech_API.sSoundList.get(Integer.valueOf(208));
    }

    @Override
    protected IIconContainer getActiveOverlay() {
        return TexturesGtBlock.Overlay_Machine_Controller_Advanced_Active;
    }

    @Override
    protected IIconContainer getInactiveOverlay() {
        return TexturesGtBlock.Overlay_Machine_Controller_Advanced;
    }

    @Override
    protected int getCasingTextureId() {
        return 70;
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
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GTPP_Recipe.GTPP_Recipe_Map.sQuantumForceTransformerRecipes;
    }

    @Override
    public boolean isCorrectMachinePart(final ItemStack aStack) {
        return true;
    }

    @Override
    public long getMaxInputVoltage() {
        long rVoltage = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches)
            if (isValidMetaTileEntity(tHatch)) {
                rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage() * tHatch.getBaseMetaTileEntity().getInputAmperage();
            }
        return rVoltage;
    }

    @Override
    public boolean checkRecipe(final ItemStack aStack) {
        for (GT_MetaTileEntity_Hatch_InputBus tBus : this.mInputBusses) {
            ArrayList<ItemStack> tBusItems = new ArrayList<ItemStack>();
            tBus.mRecipeMap = getRecipeMap();
            if (isValidMetaTileEntity(tBus)) {
                for (int i = tBus.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                    if (tBus.getBaseMetaTileEntity().getStackInSlot(i) != null)
                        tBusItems.add(tBus.getBaseMetaTileEntity().getStackInSlot(i));
                }
            }
            ItemStack[] inputs = new ItemStack[tBusItems.size()];
            int slot = 0;
            for (ItemStack g : tBusItems) {
                inputs[slot++] = g;
            }
            if (inputs.length > 0) {
                if (mChamberTier == 2) { // 2x bonus to multi speed with upgraded Resonance Chambers
                    if (checkRecipeGeneric(inputs, new FluidStack[]{}, getMaxParallelRecipes(), 100, 200, 10000)) {
                        return true;
                    }
                }
                else {
                    if (checkRecipeGeneric(inputs, new FluidStack[]{}, getMaxParallelRecipes(), 100, 100, 10000)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    @Override
    public int getMaxParallelRecipes() {
        if (mGlassTier == 2) { // 4x bonus to parallel amount with upgraded Containment Chambers (glass)
            return 256;
        }
        return 64;
    }

    @Override
    public int getEuDiscountForParallelism() {
        return 0;
    }

    @Override
    public int getMaxEfficiency(final ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerSecond(final ItemStack aStack) {
        return CORE.ConfigSwitches.pollutionPerSecondMultiABS * 4;
    }

    @Override
    public int getDamageToComponent(final ItemStack aStack) {
        return 0;
    }

    @Override
    public int getAmountOfOutputs() {
        return 2;
    }

    @Override
    public boolean explodesOnComponentBreak(final ItemStack aStack) {
        return false;
    }
}
