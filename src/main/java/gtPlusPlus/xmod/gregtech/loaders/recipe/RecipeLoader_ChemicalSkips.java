package gtPlusPlus.xmod.gregtech.loaders.recipe;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsKevlar;
import gtPlusPlus.core.item.chemistry.GenericChem;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.material.ALLOY;
import gtPlusPlus.core.material.ELEMENT;
import gtPlusPlus.core.material.MISC_MATERIALS;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.MaterialUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeLoader_ChemicalSkips {

    public static void generate() {
        createRecipes();
    }

    private static void createRecipes() {
        quantumTransformerRecipes();
        fusionReactorRecipes();
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
                    MISC_MATERIALS.CYANOACETIC_ACID.getDust(1),
                    ItemUtils.getSimpleStack(GenericChem.mSolidAcidCatalyst, 0)
                },
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {},
                new ItemStack[] {
                    Materials.Platinum.getDust(32),
                    Materials.Palladium.getDust(32),
                    Materials.Iridium.getDust(32),
                    Materials.Osmium.getDust(32)
                },
                new int[] {2500, 2500, 2500, 2500},
                5 * 20,
                8000000,
                1);

        // Early Plastics
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {MISC_MATERIALS.SODIUM_CYANIDE.getDust(1)},
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {},
                new ItemStack[] {
                    Materials.Plastic.getDust(32),
                    Materials.PolyvinylChloride.getDust(32),
                    Materials.Polystyrene.getDust(32),
                    Materials.Epoxid.getDust(32),
                    Materials.Polybenzimidazole.getDust(32)
                },
                new int[] {2000, 2000, 2000, 2000, 2000},
                5 * 20,
                8000000,
                1);

        // Glues and Solders
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {MISC_MATERIALS.SODIUM_CYANIDE.getDust(1)},
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {
                    MISC_MATERIALS.ETHYL_CYANOACRYLATE.getFluidStack(4608),
                    Materials.AdvancedGlue.getFluid(4608L),
                    ALLOY.INDALLOY_140.getFluidStack(4608),
                    Materials.SolderingAlloy.getFluid(4608L)
                },
                new ItemStack[] {Materials.HSSG.getDust(1)},
                new int[] {2000, 2000, 2000, 2000, 2000},
                5 * 20,
                8000000,
                1);

        // Monaline (Missing Hafnium and Zirconium - Europium is ZPM)
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {MISC_MATERIALS.SODIUM_CYANIDE.getDust(1)},
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {},
                new ItemStack[] {
                    Materials.Cerium.getDust(32),
                    Materials.Gadolinium.getDust(32),
                    Materials.Samarium.getDust(32),
                    Materials.Europium.getDust(32)
                },
                new int[] {2500, 2500, 2500, 2500},
                5 * 20,
                30000000,
                2);

        // Early Naqline (Naquadah should be Naquadahine)
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {MISC_MATERIALS.SODIUM_CYANIDE.getDust(1)},
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {},
                new ItemStack[] {
                    Materials.Naquadah.getDust(32),
                    Materials.Adamantium.getDust(32),
                    Materials.Gallium.getDust(32),
                    Materials.Titanium.getDust(32)
                },
                new int[] {2500, 2500, 2500, 2500},
                5 * 20,
                30000000,
                2);

        // Advanced Naqline
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {MISC_MATERIALS.SODIUM_CYANIDE.getDust(1)},
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {},
                new ItemStack[] {
                    Materials.Naquadria.getDust(32),
                    Materials.EnrichedNaquadria.getDust(32),
                    Materials.Trinium.getDust(32),
                    Materials.Indium.getDust(32),
                    Materials.Barium.getDust(32)
                },
                new int[] {2000, 2000, 2000, 2000, 2000},
                5 * 20,
                120000000,
                3);

        // Lategame Plastics (Missing Radox Polymer and Heavy Radox)
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {MISC_MATERIALS.SODIUM_CYANIDE.getDust(1)},
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {},
                new ItemStack[] {
                    MaterialsKevlar.Kevlar.getDust(32),
                },
                new int[] {2500, 2500, 2500, 2500},
                5 * 20,
                500000000,
                4);

        // Mutated Solder
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {MISC_MATERIALS.SODIUM_CYANIDE.getDust(1)},
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {MISC_MATERIALS.MUTATED_LIVING_SOLDER.getFluidStack(4608)},
                new ItemStack[] {},
                new int[] {2500, 2500, 2500, 2500},
                5 * 20,
                500000000,
                4);
    }

    private static void fusionReactorRecipes() {
        GT_Values.RA.addFusionReactorRecipe(
                new FluidStack[] {
                        Materials.Radon.getPlasma(100),
                        Materials.Nitrogen.getPlasma(100)
                },
                new FluidStack[] {
                        new FluidStack(ELEMENT.getInstance().NEPTUNIUM.getPlasma(), 100)
                },
                2048 * 4,
                MaterialUtils.getVoltageForTier(9),
                600000000 * 2);

        GT_Values.RA.addFusionReactorRecipe(
                new FluidStack[] {
                        Materials.Americium.getPlasma(100),
                        Materials.Boron.getPlasma(100)
                },
                new FluidStack[] {
                        new FluidStack(ELEMENT.getInstance().FERMIUM.getPlasma(), 100)
                },
                2048 * 4,
                MaterialUtils.getVoltageForTier(9),
                600000000 * 2);
    }
}
