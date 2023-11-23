package gtPlusPlus.api.recipe;

import java.util.Arrays;
import java.util.Collections;

import net.minecraft.util.StatCollector;

import com.gtnewhorizons.modularui.common.widget.ProgressBar;

import gregtech.api.gui.modularui.GT_UITextures;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMapBuilder;
import gregtech.api.recipe.maps.FluidOnlyFrontend;
import gregtech.api.recipe.maps.FuelBackend;
import gregtech.api.recipe.maps.LargeNEIFrontend;
import gregtech.api.util.GT_Utility;
import gregtech.nei.formatter.FuelSpecialValueFormatter;
import gregtech.nei.formatter.HeatingCoilSpecialValueFormatter;
import gregtech.nei.formatter.SimpleSpecialValueFormatter;
import gtPlusPlus.core.util.math.MathUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.xmod.gregtech.api.gui.GTPP_UITextures;

public class GTPPRecipeMaps {

    public static final RecipeMap<RecipeMapBackend> sCokeOvenRecipes = RecipeMapBuilder.of("gtpp.recipe.cokeoven")
            .maxIO(2, 9, 1, 1).minInputs(1, 0).progressBar(GT_UITextures.PROGRESSBAR_SIFT, ProgressBar.Direction.DOWN)
            .build();
    public static final RecipeMap<RecipeMapBackend> sMatterFab2Recipes = RecipeMapBuilder.of("gtpp.recipe.matterfab2")
            .maxIO(2, 0, 1, 1).build();
    public static final RecipeMap<FuelBackend> sRocketFuels = RecipeMapBuilder
            .of("gtpp.recipe.rocketenginefuel", FuelBackend::new).maxIO(0, 0, 1, 0)
            .neiSpecialInfoFormatter(
                    recipeInfo -> Collections.singletonList(
                            StatCollector.translateToLocalFormatted(
                                    "GT5U.nei.fuel",
                                    GT_Utility.formatNumbers(recipeInfo.recipe.mSpecialValue * 3000L))))
            .build();
    public static final RecipeMap<RecipeMapBackend> sQuantumForceTransformerRecipes = RecipeMapBuilder
            .of("gtpp.recipe.quantumforcesmelter").maxIO(6, 6, 6, 6).minInputs(1, 0)
            .progressBar(GT_UITextures.PROGRESSBAR_ARROW_MULTIPLE)
            .neiSpecialInfoFormatter(new SimpleSpecialValueFormatter("GT5U.nei.tier")).frontend(LargeNEIFrontend::new)
            .build();
    public static final RecipeMap<RecipeMapBackend> sChemicalDehydratorRecipes = RecipeMapBuilder
            .of("gtpp.recipe.chemicaldehydrator").maxIO(2, 9, 1, 1)
            .progressBar(GT_UITextures.PROGRESSBAR_SIFT, ProgressBar.Direction.DOWN).build();
    public static final RecipeMap<RecipeMapBackend> sVacuumFurnaceRecipes = RecipeMapBuilder
            .of("gtpp.recipe.vacfurnace").maxIO(9, 9, 3, 3).minInputs(1, 0)
            .neiSpecialInfoFormatter(HeatingCoilSpecialValueFormatter.INSTANCE).frontend(LargeNEIFrontend::new).build();
    public static final RecipeMap<RecipeMapBackend> sAlloyBlastSmelterRecipes = RecipeMapBuilder
            .of("gtpp.recipe.alloyblastsmelter").maxIO(9, 9, 3, 3).minInputs(1, 0).frontend(LargeNEIFrontend::new)
            .build();
    public static final RecipeMap<RecipeMapBackend> sLiquidFluorineThoriumReactorRecipes = RecipeMapBuilder
            .of("gtpp.recipe.lftr").maxIO(0, 0, 6, 6).minInputs(0, 2).frontend(FluidOnlyFrontend::new)
            .neiSpecialInfoFormatter(recipeInfo -> {
                final long eut = recipeInfo.recipe.mSpecialValue;
                final int duration = recipeInfo.recipe.mDuration;
                return Arrays.asList(
                        StatCollector.translateToLocalFormatted("gtpp.nei.lftr.power", GT_Utility.formatNumbers(eut)),
                        StatCollector.translateToLocalFormatted(
                                "gtpp.nei.lftr.dynamo",
                                MathUtils.formatNumbers(duration * eut)),
                        StatCollector.translateToLocalFormatted(
                                "gtpp.nei.lftr.total",
                                MathUtils.formatNumbers(duration * eut * 4)));
            }).build();
    public static final RecipeMap<RecipeMapBackend> sNuclearSaltProcessingPlantRecipes = RecipeMapBuilder
            .of("gtpp.recipe.nuclearsaltprocessingplant").maxIO(1, 6, 2, 3).frontend(LargeNEIFrontend::new).build();
    public static final RecipeMap<RecipeMapBackend> sOreMillRecipes = RecipeMapBuilder.of("gtpp.recipe.oremill")
            .maxIO(3, 1, 0, 0).minInputs(1, 0).frontend(MillingFrontend::new).build();
    public static final RecipeMap<RecipeMapBackend> sFissionFuelProcessing = RecipeMapBuilder
            .of("gtpp.recipe.fissionfuel").maxIO(0, 0, 6, 1).frontend(FluidOnlyFrontend::new).build();
    public static final RecipeMap<RecipeMapBackend> sColdTrapRecipes = RecipeMapBuilder.of("gtpp.recipe.coldtrap")
            .maxIO(2, 9, 1, 1).progressBar(GT_UITextures.PROGRESSBAR_SIFT, ProgressBar.Direction.DOWN).build();
    public static final RecipeMap<RecipeMapBackend> sReactorProcessingUnitRecipes = RecipeMapBuilder
            .of("gtpp.recipe.reactorprocessingunit").maxIO(2, 9, 1, 1)
            .progressBar(GT_UITextures.PROGRESSBAR_SIFT, ProgressBar.Direction.DOWN).build();
    public static final RecipeMap<RecipeMapBackend> sSimpleWasherRecipes = RecipeMapBuilder
            .of("gtpp.recipe.simplewasher").maxIO(1, 1, 1, 0)
            .slotOverlays(
                    (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GT_UITextures.OVERLAY_SLOT_CAULDRON
                            : null)
            .progressBar(GT_UITextures.PROGRESSBAR_ARROW_MULTIPLE).build();
    public static final RecipeMap<RecipeMapBackend> sMolecularTransformerRecipes = RecipeMapBuilder
            .of("gtpp.recipe.moleculartransformer").maxIO(1, 1, 0, 0)
            .slotOverlays(
                    (index, isFluid, isOutput,
                            isSpecial) -> !isFluid && !isOutput ? GT_UITextures.OVERLAY_SLOT_MICROSCOPE : null)
            .neiHandlerInfo(
                    builder -> builder.setDisplayStack(
                            ItemUtils.getItemStackFromFQRN("AdvancedSolarPanel:BlockMolecularTransformer", 1)))
            .build();
    public static final RecipeMap<RecipeMapBackend> sChemicalPlantRecipes = RecipeMapBuilder
            .of("gtpp.recipe.fluidchemicaleactor").maxIO(4, 4, 4, 2)
            .slotOverlays((index, isFluid, isOutput, isSpecial) -> {
                if (isFluid) {
                    if (isOutput) {
                        return GT_UITextures.OVERLAY_SLOT_VIAL_2;
                    }
                    return GT_UITextures.OVERLAY_SLOT_MOLECULAR_3;
                }
                if (isOutput) {
                    return GT_UITextures.OVERLAY_SLOT_VIAL_1;
                }
                return GT_UITextures.OVERLAY_SLOT_MOLECULAR_1;
            }).progressBar(GTPP_UITextures.PROGRESSBAR_FLUID_REACTOR, ProgressBar.Direction.CIRCULAR_CW)
            .progressBarPos(82, 24).neiSpecialInfoFormatter(recipeInfo -> {
                int tier = recipeInfo.recipe.mSpecialValue + 1;
                String materialName = StatCollector.translateToLocal("gtpp.nei.chemplant.tier." + tier);
                return Collections.singletonList(
                        StatCollector.translateToLocalFormatted("GT5U.nei.tier", tier + " - " + materialName));
            }).frontend(ChemicalPlantFrontend::new).build();
    public static final RecipeMap<FuelBackend> sRTGFuels = RecipeMapBuilder
            .of("gtpp.recipe.RTGgenerators", FuelBackend::new).maxIO(1, 0, 0, 0)
            .neiSpecialInfoFormatter(new SimpleSpecialValueFormatter("gtpp.nei.rtg.days", 365)).build();
    public static final RecipeMap<RecipeMapBackend> sThermalFuels = RecipeMapBuilder
            .of("gtpp.recipe.thermalgeneratorfuel").maxIO(9, 9, 3, 3).frontend(LargeNEIFrontend::new).build();
    public static final RecipeMap<RecipeMapBackend> sSolarTowerRecipes = RecipeMapBuilder.of("gtpp.recipe.solartower")
            .maxIO(0, 0, 1, 1)
            .neiSpecialInfoFormatter(
                    recipeInfo -> Arrays.asList(
                            StatCollector.translateToLocal("gtpp.nei.solar_tower.1"),
                            StatCollector.translateToLocal("gtpp.nei.solar_tower.2"),
                            StatCollector.translateToLocal("gtpp.nei.solar_tower.3")))
            .frontend(FluidOnlyFrontend::new).build();
    public static final RecipeMap<RecipeMapBackend> sCyclotronRecipes = RecipeMapBuilder.of("gtpp.recipe.cyclotron")
            .maxIO(9, 9, 1, 1).build();
    public static final RecipeMap<RecipeMapBackend> sFishPondRecipes = RecipeMapBuilder.of("gtpp.recipe.fishpond")
            .maxIO(1, 1, 0, 0)
            .slotOverlays(
                    (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GT_UITextures.OVERLAY_SLOT_CAULDRON
                            : null)
            .progressBar(GT_UITextures.PROGRESSBAR_ARROW_MULTIPLE).build();
    public static final RecipeMap<RecipeMapBackend> sSpargeTowerRecipes = RecipeMapBuilder.of("gtpp.recipe.spargetower")
            .maxIO(0, 0, 9, 9).disableRegisterNEI().build();
    public static final RecipeMap<RecipeMapBackend> sAdvFreezerRecipes_GT = RecipeMapBuilder
            .of("gtpp.recipe.cryogenicfreezer").maxIO(1, 1, 2, 1).build();
    public static final RecipeMap<RecipeMapBackend> sMultiblockCentrifugeRecipes_GT = RecipeMapBuilder
            .of("gtpp.recipe.multicentrifuge").maxIO(6, 6, 6, 6).progressBar(GT_UITextures.PROGRESSBAR_EXTRACT)
            .frontend(LargeNEIFrontend::new).build();
    public static final RecipeMap<RecipeMapBackend> sMultiblockElectrolyzerRecipes_GT = RecipeMapBuilder
            .of("gtpp.recipe.multielectro").maxIO(6, 6, 6, 6).progressBar(GT_UITextures.PROGRESSBAR_EXTRACT)
            .frontend(LargeNEIFrontend::new).build();
    public static final RecipeMap<RecipeMapBackend> sMultiblockMixerRecipes_GT = RecipeMapBuilder
            .of("gtpp.recipe.multimixer").maxIO(9, 9, 6, 6)
            .progressBar(GT_UITextures.PROGRESSBAR_MIXER, ProgressBar.Direction.CIRCULAR_CW)
            .frontend(LargeNEIFrontend::new).build();
    public static final RecipeMap<RecipeMapBackend> sMultiblockChemicalDehydratorRecipes = RecipeMapBuilder
            .of("gtpp.recipe.multidehydrator").maxIO(6, 9, 3, 3).frontend(LargeNEIFrontend::new).build();
    public static final RecipeMap<FuelBackend> sSemiFluidLiquidFuels = RecipeMapBuilder
            .of("gtpp.recipe.semifluidgeneratorfuels", FuelBackend::new).maxIO(0, 0, 1, 0)
            .neiSpecialInfoFormatter(FuelSpecialValueFormatter.INSTANCE).build();
    public static final RecipeMap<RecipeMapBackend> sFlotationCellRecipes = RecipeMapBuilder
            .of("gtpp.recipe.flotationcell").maxIO(6, 0, 1, 1).build();
    public static final RecipeMap<RecipeMapBackend> sTreeSimFakeRecipes = RecipeMapBuilder.of("gtpp.recipe.treefarm")
            .maxIO(1, 2, 1, 0).minInputs(1, 0).frontend(TGSFrontend::new).build();
}
