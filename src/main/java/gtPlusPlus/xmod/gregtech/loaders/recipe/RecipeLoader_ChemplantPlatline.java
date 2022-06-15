package gtPlusPlus.xmod.gregtech.loaders.recipe;

import static gtPlusPlus.core.lib.CORE.GTNH;
import static gtPlusPlus.core.material.MISC_MATERIALS.CYANOACETIC_ACID;
import static gtPlusPlus.core.material.MISC_MATERIALS.SODIUM_CHLORIDE;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.item.ModItems;
import gtPlusPlus.core.item.chemistry.AgriculturalChem;
import gtPlusPlus.core.item.chemistry.GenericChem;
import gtPlusPlus.core.item.chemistry.NuclearChem;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.lib.LoadedMods;
import gtPlusPlus.core.material.ELEMENT;
import gtPlusPlus.core.material.MISC_MATERIALS;
import gtPlusPlus.core.material.nuclear.FLUORIDES;
import gtPlusPlus.core.material.nuclear.NUCLIDE;
import gtPlusPlus.core.recipe.common.CI;
import gtPlusPlus.core.util.Utils;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.MaterialUtils;
import gtPlusPlus.plugin.agrichem.BioRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeLoader_ChemplantPlatline {
    public static void generate() {
        createRecipes();
    }

    private static void createRecipes() {
        chemicalPlantRecipes();
    }

    private static void chemicalPlantRecipes() {
        CORE.RA.addChemicalPlantRecipe(
                new ItemStack[] {
                        CI.getNumberedCircuit(9),
                        ItemUtils.getItemStackFromFQRN("bartworks:gt.bwMetaGenerateddust:47", 9)
                },
                new FluidStack[] {
                        FluidUtils.getFluidStack("aqua regia", 9000)
                },
                new ItemStack[] {
                        ItemUtils.getItemStackFromFQRN("bartworks:gt.bwMetaGenerateddust:49", 1)
                },
                new FluidStack[] {
                        FluidUtils.getFluidStack("platinum concentrate", 9000)
                },
                8*20,
                400,
                3);

        CORE.RA.addChemicalPlantRecipe(
                new ItemStack[] {
                        CI.getNumberedCircuit(2),
                        Materials.Calcium.getDust(1)
                },
                new FluidStack[] {
                        FluidUtils.getFluidStack("platinum concentrate", 18000),
                        FluidUtils.getFluidStack("ammonium chloride", 1800),
                        FluidUtils.getFluidStack("oxygen", 4500)
                },
                new ItemStack[] {
                        ItemUtils.getItemStackFromFQRN("bartworks:gt.bwMetaGenerateddust:45", 16),
                        MISC_MATERIALS.CALCIUM_CHLORIDE.getDust(1),
                        Materials.Platinum.getDust(2)
                },
                new FluidStack[] {
                        FluidUtils.getFluidStack("palladium enriched ammonia", 1800),
                        FluidUtils.getFluidStack("aqua regia", 18000)
                },
                4*20,
                400,
                3);
    }
}
