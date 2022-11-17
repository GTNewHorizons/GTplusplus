package gtPlusPlus.xmod.gregtech.loaders.recipe;

import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gtPlusPlus.core.item.chemistry.GenericChem;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.material.ALLOY;
import gtPlusPlus.core.material.ELEMENT;
import gtPlusPlus.core.material.MISC_MATERIALS;
import gtPlusPlus.core.recipe.common.CI;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.MaterialUtils;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeLoader_ChemicalSkips {

    public static void generate() {
        createRecipes();
    }

    private static void createRecipes() {
        quantumTransformerRecipes();
        fusionReactorRecipes();
        catalystRecipes();
        tieredCasingRecipes();
    }

    // All the recipes that the QFT can do. Each recipe has a machine tier.
    // -> Tier 1 is UEV (UEV circuits and 1 Eternal Singularity);
    // -> Tier 2 needs new item from QFT, plus stacks of Infinity;
    // -> Tier 3 needs new item from QFT, plus stacks of Transcendent Metal;
    // -> Tier 4 needs new item from QFT, plus stacks of Spacetime;
    // (Until they are created, the new items are represented by
    // HSS-G for Tier 2, HSS-S for Tier 3 and HSS-E for Tier 4)

    private static void quantumTransformerRecipes() {
        // Platline (Missing Rhodium and Ruthenium)
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {
                        GT_ModHandler.getModItem("bartworks", "gt.bwMetaGenerateddust", 64L, 47),
                        ItemUtils.getSimpleStack(GenericChem.mPlatinumGroupCatalyst, 0)
                },
                new FluidStack[] {},
                new FluidStack[] {},
                new ItemStack[] {
                    Materials.Platinum.getDust(64),
                    Materials.Palladium.getDust(64),
                    Materials.Iridium.getDust(64),
                    Materials.Osmium.getDust(64)
                },
                new int[] {2500, 2500, 2500, 2500},
                20 * 20,
                8000000,
                1);

        // Early Plastics
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {
                        Materials.Carbon.getDust(64),
                        ItemUtils.getSimpleStack(GenericChem.mPlasticPolymerCatalyst, 0)
                },
                new FluidStack[] {
                        Materials.Oxygen.getFluid(10000),
                        Materials.Hydrogen.getFluid(10000),
                        Materials.Chlorine.getFluid(10000),
                },
                new FluidStack[] {},
                new ItemStack[] {
                    Materials.Plastic.getDust(64),
                    Materials.PolyvinylChloride.getDust(64),
                    Materials.Polystyrene.getDust(64),
                    Materials.Epoxid.getDust(64),
                    Materials.Polybenzimidazole.getDust(64)
                },
                new int[] {2000, 2000, 2000, 2000, 2000},
                20 * 20,
                8000000,
                1);

        // Glues and Solders
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {
                        Materials.Carbon.getDust(64),
                        Materials.Bismuth.getDust(64),
                        ItemUtils.getSimpleStack(GenericChem.mAdhesionPromoterCatalyst, 0)
                },
                new FluidStack[] {
                        Materials.Oxygen.getFluid(10000),
                        Materials.Hydrogen.getFluid(10000)
                },
                new FluidStack[] {
                    MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(9216),
                    Materials.AdvancedGlue.getFluid(9216L),

                },
                new ItemStack[] {
                    ALLOY.INDALLOY_140.getDust(64),
                    Materials.SolderingAlloy.getDust(64),
                    Materials.HSSG.getDust(1)
                },
                new int[] {2000, 2000, 2000, 2000, 2000},
                20 * 20,
                8000000,
                1);

        // Monaline (Missing Hafnium and Zirconium - Europium is ZPM)
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {
                        Materials.Monazite.getDust(64),
                        ItemUtils.getSimpleStack(GenericChem.mRareEarthGroupCatalyst, 0)
                },
                new FluidStack[] {},
                new FluidStack[] {},
                new ItemStack[] {
                    Materials.Cerium.getDust(64),
                    Materials.Gadolinium.getDust(64),
                    Materials.Samarium.getDust(64),
                    Materials.Europium.getDust(64),
                    Materials.HSSS.getDust(1)
                },
                new int[] {2000, 2000, 2000, 2000, 2000},
                20 * 20,
                30000000,
                2);

        // Early Naqline (Naquadah should be Naquadahine)
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {
                        MISC_MATERIALS.SODIUM_CYANIDE.getDust(1),
                        ItemUtils.getSimpleStack(GenericChem.mSimpleNaquadahCatalyst, 0)
                },
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {},
                new ItemStack[] {
                    Materials.Naquadah.getDust(64),
                    Materials.Adamantium.getDust(64),
                    Materials.Gallium.getDust(64),
                    Materials.Titanium.getDust(64)
                },
                new int[] {2500, 2500, 2500, 2500},
                20 * 20,
                30000000,
                2);

        // Advanced Naqline
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {
                        MISC_MATERIALS.SODIUM_CYANIDE.getDust(1),
                        ItemUtils.getSimpleStack(GenericChem.mAdvancedNaquadahCatalyst, 0)
                },
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {},
                new ItemStack[] {
                    Materials.Naquadria.getDust(64),
                    Materials.EnrichedNaquadria.getDust(64),
                    Materials.Trinium.getDust(64),
                    Materials.Barium.getDust(64),
                    Materials.HSSE.getDust(1)
                },
                new int[] {2000, 2000, 2000, 2000, 2000},
                20 * 20,
                120000000,
                3);

        // Lategame Plastics (Missing Radox Polymer and Heavy Radox)
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {
                        Materials.Carbon.getDust(64),
                        ItemUtils.getSimpleStack(GenericChem.mUltimatePlasticCatalyst, 0)
                },
                new FluidStack[] {
                        Materials.Hydrogen.getFluid(10000),
                        Materials.Nitrogen.getFluid(10000),
                },
                new FluidStack[] {},
                new ItemStack[] {
                    MaterialsKevlar.Kevlar.getDust(64),
                },
                new int[] {10000},
                20 * 20,
                500000000,
                4);

        // Mutated Solder
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {
                        ItemList.Circuit_Chip_Stemcell.get(64),
                        //ItemUtils.getSimpleStack(GenericChem.mSolidAcidCatalyst, 0)
                },
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {MISC_MATERIALS.MUTATED_LIVING_SOLDER.getFluidStack(9216)},
                new ItemStack[] {
                        ItemList.Circuit_Chip_Biocell.get(64)
                },
                new int[] {3333, 3333, 3333},
                20 * 20,
                500000000,
                4);
    }

    private static void fusionReactorRecipes() {
        GT_Values.RA.addFusionReactorRecipe(
                new FluidStack[] {Materials.Radon.getPlasma(100), Materials.Nitrogen.getPlasma(100)},
                new FluidStack[] {new FluidStack(ELEMENT.getInstance().NEPTUNIUM.getPlasma(), 100)},
                2048 * 4,
                MaterialUtils.getVoltageForTier(9),
                600000000 * 2);

        GT_Values.RA.addFusionReactorRecipe(
                new FluidStack[] {Materials.Americium.getPlasma(100), Materials.Boron.getPlasma(100)},
                new FluidStack[] {new FluidStack(ELEMENT.getInstance().FERMIUM.getPlasma(), 100)},
                2048 * 4,
                MaterialUtils.getVoltageForTier(9),
                600000000 * 2);
    }

    private static void catalystRecipes() {
        CORE.RA.addSixSlotAssemblingRecipe(
                new ItemStack[] {
                        CI.getNumberedCircuit(10),
                        CI.getEmptyCatalyst(1),
                        Materials.Infinity.getDust(1),
                        GT_ModHandler.getModItem("bartworks", "gt.bwMetaGenerateddust", 64L, 88),
                        Materials.Osmiridium.getDust(64)
                },
                GT_Values.NF,
                ItemUtils.getSimpleStack(GenericChem.mPlatinumGroupCatalyst, 1),
                60 * 20,
                1966080);

        CORE.RA.addSixSlotAssemblingRecipe(
                new ItemStack[] {
                        CI.getNumberedCircuit(10),
                        CI.getEmptyCatalyst(1),
                        Materials.Infinity.getDust(1),
                        Materials.Polybenzimidazole.getDust(64),
                        Materials.Tetrafluoroethylene.getDust(64)
                },
                GT_Values.NF,
                ItemUtils.getSimpleStack(GenericChem.mPlasticPolymerCatalyst, 1),
                60 * 20,
                1966080);

        CORE.RA.addSixSlotAssemblingRecipe(
                new ItemStack[] {
                        CI.getNumberedCircuit(10),
                        CI.getEmptyCatalyst(1),
                        Materials.Infinity.getDust(1),
                        //ALLOY.INDALLOY_140.getDust(64)
                },
                new FluidStack(MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluid(), 10000),
                ItemUtils.getSimpleStack(GenericChem.mAdhesionPromoterCatalyst, 1),
                60 * 20,
                1966080);

        CORE.RA.addSixSlotAssemblingRecipe(
                new ItemStack[] {
                        CI.getNumberedCircuit(10),
                        CI.getEmptyCatalyst(1),
                        Materials.Infinity.getDust(1),
                        Materials.Silicone.getDust(64),
                        Materials.StyreneButadieneRubber.getDust(64)
                },
                GT_Values.NF,
                ItemUtils.getSimpleStack(GenericChem.mRubberPolymerCatalyst, 1),
                60 * 20,
                1966080);

        CORE.RA.addSixSlotAssemblingRecipe(
                new ItemStack[] {
                        CI.getNumberedCircuit(10),
                        CI.getEmptyCatalyst(1),
                        Materials.Infinity.getDust(1),
                        Materials.Samarium.getDust(64),
                        Materials.Gadolinium.getDust(64),
                },
                GT_Values.NF,
                ItemUtils.getSimpleStack(GenericChem.mRareEarthGroupCatalyst, 1),
                60 * 20,
                1966080);

        CORE.RA.addSixSlotAssemblingRecipe(
                new ItemStack[] {
                        CI.getNumberedCircuit(10),
                        CI.getEmptyCatalyst(1),
                        Materials.Infinity.getDust(1),
                        Materials.Naquadah.getDust(64),
                        Materials.Adamantium.getDust(64)
                },
                GT_Values.NF,
                ItemUtils.getSimpleStack(GenericChem.mSimpleNaquadahCatalyst, 1),
                60 * 20,
                1966080);

        CORE.RA.addSixSlotAssemblingRecipe(
                new ItemStack[] {
                        CI.getNumberedCircuit(10),
                        CI.getEmptyCatalyst(1),
                        Materials.Infinity.getDust(1),
                        Materials.Naquadria.getDust(64),
                        Materials.Trinium.getDust(64)
                },
                GT_Values.NF,
                ItemUtils.getSimpleStack(GenericChem.mAdvancedNaquadahCatalyst, 1),
                60 * 20,
                1966080);

        CORE.RA.addSixSlotAssemblingRecipe(
                new ItemStack[] {
                        CI.getNumberedCircuit(10),
                        CI.getEmptyCatalyst(1),
                        Materials.Infinity.getDust(1),
                        MaterialsKevlar.Kevlar.getDust(64)
                },
                GT_Values.NF,
                ItemUtils.getSimpleStack(GenericChem.mUltimatePlasticCatalyst, 1),
                60 * 20,
                1966080);
    }

    private static void tieredCasingRecipes() {
        CORE.RA.addAssemblylineRecipe(
                GregtechItemList.Casing_Coil_QuantumForceTransformer.get(1),
                40 * 60 * 30,
                new Object[] {
                        GT_ModHandler.getModItem("eternalsingularity", "eternal_singularity", 1),
                        new Object[] {OrePrefixes.circuit.get(Materials.Bio), 1},
                        ItemList.Electric_Pump_UEV.get(4),
                        ItemList.Field_Generator_UEV.get(4),
                        GregtechItemList.Laser_Lens_Special.get(1)
                },
                new FluidStack[] {
                        MISC_MATERIALS.MUTATED_LIVING_SOLDER.getFluidStack(144 * 10),
                        ALLOY.PIKYONIUM.getFluidStack(144 * 32)
                },
                GregtechItemList.NeutronPulseManipulator.get(1),
                1200,
                8000000);

        CORE.RA.addAssemblylineRecipe(
                GregtechItemList.Casing_Coil_QuantumForceTransformer.get(1),
                40 * 60 * 30,
                new Object[] {
                        GT_ModHandler.getModItem("eternalsingularity", "eternal_singularity", 1),
                        new Object[] {OrePrefixes.circuit.get(Materials.Nano), 1},
                        ItemList.Electric_Pump_UEV.get(4),
                        ItemList.Field_Generator_UEV.get(4),
                        GregtechItemList.Laser_Lens_Special.get(1)
                },
                new FluidStack[] {
                        MISC_MATERIALS.MUTATED_LIVING_SOLDER.getFluidStack(144 * 10),
                        ALLOY.PIKYONIUM.getFluidStack(144 * 32)
                },
                GregtechItemList.CosmicFabricManipulator.get(1),
                1200,
                8000000);

        CORE.RA.addAssemblylineRecipe(
                GregtechItemList.Casing_Coil_QuantumForceTransformer.get(1),
                40 * 60 * 30,
                new Object[] {
                        GT_ModHandler.getModItem("eternalsingularity", "eternal_singularity", 1),
                        new Object[] {OrePrefixes.circuit.get(Materials.Piko), 1},
                        ItemList.Electric_Pump_UEV.get(4),
                        ItemList.Field_Generator_UEV.get(4),
                        GregtechItemList.Laser_Lens_Special.get(1)
                },
                new FluidStack[] {
                        MISC_MATERIALS.MUTATED_LIVING_SOLDER.getFluidStack(144 * 10),
                        ALLOY.PIKYONIUM.getFluidStack(144 * 32)
                },
                GregtechItemList.InfinityInfusedManipulator.get(1),
                1200,
                8000000);

        CORE.RA.addAssemblylineRecipe(
                GregtechItemList.Casing_Coil_QuantumForceTransformer.get(1),
                40 * 60 * 30,
                new Object[] {
                        GT_ModHandler.getModItem("eternalsingularity", "eternal_singularity", 1),
                        new Object[] {OrePrefixes.circuit.get(Materials.Quantum), 1},
                        ItemList.Electric_Pump_UEV.get(4),
                        ItemList.Field_Generator_UEV.get(4),
                        GregtechItemList.Laser_Lens_Special.get(1)
                },
                new FluidStack[] {
                        MISC_MATERIALS.MUTATED_LIVING_SOLDER.getFluidStack(144 * 10),
                        ALLOY.PIKYONIUM.getFluidStack(144 * 32)
                },
                GregtechItemList.SpaceTimeContinuumRipper.get(1),
                1200,
                8000000);

        CORE.RA.addAssemblylineRecipe(
                GregtechItemList.Casing_Coil_QuantumForceTransformer.get(1),
                40 * 60 * 30,
                new Object[] {
                        GT_ModHandler.getModItem("eternalsingularity", "eternal_singularity", 1),
                        new Object[] {OrePrefixes.circuit.get(Materials.Bio), 1},
                        ItemList.Electric_Pump_UEV.get(4),
                        ItemList.Field_Generator_UEV.get(4),
                        GregtechItemList.Laser_Lens_Special.get(1)
                },
                new FluidStack[] {
                        MISC_MATERIALS.MUTATED_LIVING_SOLDER.getFluidStack(144 * 10),
                        ALLOY.PIKYONIUM.getFluidStack(144 * 32)
                },
                GregtechItemList.NeutronShieldingCore.get(1),
                1200,
                8000000);

        CORE.RA.addAssemblylineRecipe(
                GregtechItemList.Casing_Coil_QuantumForceTransformer.get(1),
                40 * 60 * 30,
                new Object[] {
                        GT_ModHandler.getModItem("eternalsingularity", "eternal_singularity", 1),
                        new Object[] {OrePrefixes.circuit.get(Materials.Nano), 1},
                        ItemList.Electric_Pump_UEV.get(4),
                        ItemList.Field_Generator_UEV.get(4),
                        GregtechItemList.Laser_Lens_Special.get(1)
                },
                new FluidStack[] {
                        MISC_MATERIALS.MUTATED_LIVING_SOLDER.getFluidStack(144 * 10),
                        ALLOY.PIKYONIUM.getFluidStack(144 * 32)
                },
                GregtechItemList.CosmicFabricShieldingCore.get(1),
                1200,
                8000000);

        CORE.RA.addAssemblylineRecipe(
                GregtechItemList.Casing_Coil_QuantumForceTransformer.get(1),
                40 * 60 * 30,
                new Object[] {
                        GT_ModHandler.getModItem("eternalsingularity", "eternal_singularity", 1),
                        new Object[] {OrePrefixes.circuit.get(Materials.Piko), 1},
                        ItemList.Electric_Pump_UEV.get(4),
                        ItemList.Field_Generator_UEV.get(4),
                        GregtechItemList.Laser_Lens_Special.get(1)
                },
                new FluidStack[] {
                        MISC_MATERIALS.MUTATED_LIVING_SOLDER.getFluidStack(144 * 10),
                        ALLOY.PIKYONIUM.getFluidStack(144 * 32)
                },
                GregtechItemList.InfinityInfusedShieldingCore.get(1),
                1200,
                8000000);

        CORE.RA.addAssemblylineRecipe(
                GregtechItemList.Casing_Coil_QuantumForceTransformer.get(1),
                40 * 60 * 30,
                new Object[] {
                        GT_ModHandler.getModItem("eternalsingularity", "eternal_singularity", 1),
                        new Object[] {OrePrefixes.circuit.get(Materials.Quantum), 1},
                        ItemList.Electric_Pump_UEV.get(4),
                        ItemList.Field_Generator_UEV.get(4),
                        GregtechItemList.Laser_Lens_Special.get(1)
                },
                new FluidStack[] {
                        MISC_MATERIALS.MUTATED_LIVING_SOLDER.getFluidStack(144 * 10),
                        ALLOY.PIKYONIUM.getFluidStack(144 * 32)
                },
                GregtechItemList.SpaceTimeBendingCore.get(1),
                1200,
                8000000);

        CORE.RA.addAssemblylineRecipe(
                GregtechItemList.Casing_Coil_QuantumForceTransformer.get(1),
                40 * 60 * 30,
                new Object[] {
                        ItemList.Electric_Pump_UV.get(4),
                        ItemList.Field_Generator_UEV.get(4),
                        GregtechItemList.Laser_Lens_Special.get(1)
                },
                new FluidStack[] {
                        MISC_MATERIALS.MUTATED_LIVING_SOLDER.getFluidStack(144 * 10),
                        ALLOY.PIKYONIUM.getFluidStack(144 * 32)
                },
                GregtechItemList.ForceFieldGlass.get(1),
                1200,
                8000000);
    }
}
