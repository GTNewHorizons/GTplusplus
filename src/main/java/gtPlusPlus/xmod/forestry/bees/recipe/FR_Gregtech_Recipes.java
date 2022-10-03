package gtPlusPlus.xmod.forestry.bees.recipe;

import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.lib.LoadedMods;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.RecipeUtils;
import gtPlusPlus.xmod.forestry.bees.items.FR_ItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FR_Gregtech_Recipes {

    private static final String rod_Electrum = "stickElectrum";
    private static final String rod_LongElectrum = "stickLongElectrum";
    private static final String foil_Electrum = "foilElectrum";
    private static final String rod_Uranium = "stickUranium";
    private static final String rod_LongUranium = "stickLongUranium";
    private static final String foil_Uranium235 = "foilUranium235";
    private static final ItemStack hiveFrameAccelerated = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameAccelerated);
    private static final ItemStack hiveFrameMutagenic = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameMutagenic);
    private static final ItemStack hiveFrameVoid = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameVoid);
    private static final ItemStack hiveFrameBusy = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameBusy);

    private static final ItemStack hiveFrameCocoa = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameCocoa);
    private static final ItemStack hiveFrameCaged = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameCaged);
    private static final ItemStack hiveFrameSoul = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameSoul);
    private static final ItemStack hiveFrameClay = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameClay);
    private static final ItemStack hiveFrameNova = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameNova);

    private static final ItemStack hiveFrameImpregnated = ItemUtils.getItemStackFromFQRN("Forestry:frameImpregnated", 1);
    private static final ItemStack blockSoulSand = new ItemStack(Blocks.soul_sand, 1);
    private static final ItemStack blockIronBars = new ItemStack(Blocks.iron_bars, 1);
    private static final ItemStack itemClayDust = new ItemStack(Items.clay_ball, 1);
    private static final ItemStack itemCocoaBeans = new ItemStack(Items.dye, 1, 3);

    private static final ItemStack hiveFrameDecay = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameDecay);
    private static final ItemStack hiveFrameSlow = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameSlow);
    private static final ItemStack hiveFrameStalilize = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameStalilize);
    private static final ItemStack hiveFrameArborist = ItemUtils.getSimpleStack(FR_ItemRegistry.hiveFrameArborist);

    public static void registerItems() {
        // Magic Bee Like Frames
        RecipeUtils.addShapedGregtechRecipe(
                rod_LongElectrum,
                rod_Electrum,
                rod_LongElectrum,
                rod_LongElectrum,
                foil_Electrum,
                rod_LongElectrum,
                rod_Electrum,
                rod_Electrum,
                rod_Electrum,
                hiveFrameAccelerated);

        RecipeUtils.addShapedGregtechRecipe(
                rod_LongUranium,
                rod_Uranium,
                rod_LongUranium,
                rod_LongUranium,
                foil_Uranium235,
                rod_LongUranium,
                rod_Uranium,
                rod_Uranium,
                rod_Uranium,
                hiveFrameMutagenic);
        if (!CORE.GTNH) {
            RecipeUtils.addShapedGregtechRecipe(
                    "stickLongThaumium",
                    "stickThaumium",
                    "stickLongThaumium",
                    "stickLongThaumium",
                    ItemUtils.getSimpleStack(Items.ender_pearl),
                    "stickLongThaumium",
                    "stickThaumium",
                    "stickThaumium",
                    "stickThaumium",
                    hiveFrameVoid);
        } else {
            if (LoadedMods.MagicBees) {
                RecipeUtils.addShapelessGregtechRecipe(
                        new ItemStack[] {hiveFrameVoid}, ItemUtils.getCorrectStacktype("MagicBees:frameOblivion", 1));
            }
        }
        RecipeUtils.addShapedGregtechRecipe(
                "stickLongBlueSteel",
                "stickBlueSteel",
                "stickLongBlueSteel",
                "stickLongBlueSteel",
                ItemUtils.getSimpleStack(Items.nether_star),
                "stickLongBlueSteel",
                "stickBlueSteel",
                "stickBlueSteel",
                "stickBlueSteel",
                hiveFrameBusy);

        if (!LoadedMods.ExtraBees) {
            // Extra Bee Like Frames
            RecipeUtils.addShapedRecipe(
                    null,
                    itemCocoaBeans,
                    null,
                    itemCocoaBeans,
                    hiveFrameImpregnated,
                    itemCocoaBeans,
                    null,
                    itemCocoaBeans,
                    null,
                    hiveFrameCocoa);

            RecipeUtils.addShapedRecipe(
                    hiveFrameImpregnated, blockIronBars, null, null, null, null, null, null, null, hiveFrameCaged);

            RecipeUtils.addShapedRecipe(
                    hiveFrameImpregnated, blockSoulSand, null, null, null, null, null, null, null, hiveFrameSoul);

            RecipeUtils.addShapedRecipe(
                    null,
                    itemClayDust,
                    null,
                    itemClayDust,
                    hiveFrameImpregnated,
                    itemClayDust,
                    null,
                    itemClayDust,
                    null,
                    hiveFrameClay);
        }

        // Frame Items added by bartimaeusnek
        RecipeUtils.addShapedGregtechRecipe(
                ItemUtils.getItemStackOfAmountFromOreDict("stickLongTumbaga", 1),
                ItemUtils.getItemStackOfAmountFromOreDict("stickTumbaga", 1),
                ItemUtils.getItemStackOfAmountFromOreDict("stickLongTumbaga", 1),
                ItemUtils.getItemStackOfAmountFromOreDict("stickLongTumbaga", 1),
                foil_Electrum,
                ItemUtils.getItemStackOfAmountFromOreDict("stickLongTumbaga", 1),
                ItemUtils.getItemStackOfAmountFromOreDict("stickTumbaga", 1),
                ItemUtils.getItemStackOfAmountFromOreDict("stickTumbaga", 1),
                ItemUtils.getItemStackOfAmountFromOreDict("stickTumbaga", 1),
                hiveFrameSlow);

        RecipeUtils.addShapedGregtechRecipe(
                "stickLongWroughtIron",
                "stickWroughtIron",
                "stickLongWroughtIron",
                "stickLongWroughtIron",
                "foilZinc",
                "stickLongWroughtIron",
                "stickWroughtIron",
                "stickWroughtIron",
                "stickWroughtIron",
                hiveFrameDecay);

        RecipeUtils.addShapedGregtechRecipe(
                "stickLongOsmiridium",
                "stickOsmiridium",
                "stickLongOsmiridium",
                "stickLongOsmiridium",
                "foilOsmiridium",
                "stickLongOsmiridium",
                "stickOsmiridium",
                "stickOsmiridium",
                "stickOsmiridium",
                hiveFrameStalilize);

        RecipeUtils.addShapedGregtechRecipe(
                "stickLongWoodSealed",
                "stickWoodSealed",
                "stickLongWoodSealed",
                "stickLongWoodSealed",
                Items.paper,
                "stickLongWoodSealed",
                "stickWoodSealed",
                "stickWoodSealed",
                "stickWoodSealed",
                hiveFrameArborist);
    }
}
