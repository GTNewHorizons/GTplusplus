package gtPlusPlus.xmod.gregtech.loaders.recipe;

import gregtech.api.enums.Materials;
import gtPlusPlus.core.item.chemistry.GenericChem;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.material.MISC_MATERIALS;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeLoader_ChemicalSkips {

    public static void generate() {
        createRecipes();
    }

    private static void createRecipes() {
        quantumTransformerRecipes();
    }

    private static void quantumTransformerRecipes() {
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
                50 * 20,
                8000,
                1);
        CORE.RA.addQuantumTransformerRecipe(
                new ItemStack[] {MISC_MATERIALS.SODIUM_CYANIDE.getDust(1)},
                new FluidStack[] {MISC_MATERIALS.ETHYL_CYANOACETATE.getFluidStack(1000)},
                new FluidStack[] {},
                new ItemStack[] {
                    Materials.Platinum.getDust(32),
                    Materials.Palladium.getDust(32),
                    Materials.Iridium.getDust(32),
                    Materials.Osmium.getDust(32)
                },
                new int[] {2500, 2500, 2500, 2500},
                50 * 20,
                8000,
                2);
    }
}
