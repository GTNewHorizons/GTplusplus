package gtPlusPlus.xmod.pamsharvest.fishtrap;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_ModHandler;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;

public class FishTrapHandler {

    // FishTrapHandler.pamsHarvestCraftCompat();
    static final String prefix = "food";
    static final String suffix = "raw";
    static final String seaweed = "cropSeaweed";
    static final String greenheartFish = "foodGreenheartfish";
    private static final String[] harvestcraftFish = { "Anchovy", "Bass", "Calamari", "Carp", "Catfish", "Charr",
            "Clam", "Crab", "Crayfish", "Eel", "Frog", "Grouper", "Herring", "Jellyfish", "Mudfish", "Octopus", "Perch",
            "Scallop", "Shrimp", "Snail", "Snapper", "Tilapia", "Trout", "Tuna", "Turtle", "Walley" };

    public static void pamsHarvestCraftCompat() {
        for (int i = 0; i < harvestcraftFish.length; i++) {
            final String itemName = prefix + harvestcraftFish[i] + suffix;
            final int lootChance = getLootChance(harvestcraftFish[i]);
            if (ItemUtils.getItemStackOfAmountFromOreDictNoBroken(itemName, 1) != null) {
                // addFish("fish", ItemUtils.getItemStackOfAmountFromOreDict(itemName, 1), lootChance);
                addGregtechMaceratorRecipe(ItemUtils.getItemStackOfAmountFromOreDict(itemName, 1));
                addGregtechCentrifugeRecipe(ItemUtils.getItemStackOfAmountFromOreDict(itemName, 1));
                addGregtechFluidRecipe(ItemUtils.getItemStackOfAmountFromOreDict(itemName, 1));
            }
        }
        if (ItemUtils.getItemStackOfAmountFromOreDictNoBroken(greenheartFish, 1) != null) {
            // addFish("fish", ItemUtils.getItemStackOfAmountFromOreDict(greenheartFish, 1),
            // getLootChance(greenheartFish));
            addGregtechMaceratorRecipe(ItemUtils.getItemStackOfAmountFromOreDict(greenheartFish, 1));
            addGregtechCentrifugeRecipe(ItemUtils.getItemStackOfAmountFromOreDict(greenheartFish, 1));
            addGregtechFluidRecipe(ItemUtils.getItemStackOfAmountFromOreDict(greenheartFish, 1));
        }
        if (ItemUtils.getItemStackOfAmountFromOreDictNoBroken(seaweed, 1) != null) {
            // addFish("junk", ItemUtils.getItemStackOfAmountFromOreDict(seaweed, 1), getLootChance(seaweed));
        }
    }

    private static int getLootChance(final String name) {
        if (name.equals(harvestcraftFish[0])) {
            return 20;
        } else if (name.equals(harvestcraftFish[1])) {
            return 10;
        } else if (name.equals(harvestcraftFish[2])) {
            return 15;
        } else if (name.equals(harvestcraftFish[3])) {
            return 55;
        } else if (name.equals(harvestcraftFish[4])) {
            return 5;
        } else if (name.equals(harvestcraftFish[5])) {
            return 8;
        } else if (name.equals(harvestcraftFish[6])) {
            return 11;
        } else if (name.equals(harvestcraftFish[7])) {
            return 11;
        } else if (name.equals(harvestcraftFish[8])) {
            return 15;
        } else if (name.equals(harvestcraftFish[9])) {
            return 1;
        } else if (name.equals(harvestcraftFish[10])) {
            return 5;
        } else if (name.equals(harvestcraftFish[11])) {
            return 10;
        } else if (name.equals(harvestcraftFish[12])) {
            return 25;
        } else if (name.equals(harvestcraftFish[13])) {
            return 15;
        } else if (name.equals(harvestcraftFish[14])) {
            return 20;
        } // "Perch", "Scallop", "Shrimp", "Snail", "Snapper", "Tilapia", "Trout", "Tuna", "Turtle", "Walleye"};
        else if (name.equals(harvestcraftFish[15])) {
            return 22;
        } else if (name.equals(harvestcraftFish[16])) {
            return 10;
        } else if (name.equals(harvestcraftFish[17])) {
            return 35;
        } else if (name.equals(harvestcraftFish[18])) {
            return 3;
        } else if (name.equals(harvestcraftFish[19])) {
            return 20;
        } else if (name.equals(harvestcraftFish[20])) {
            return 4;
        } else if (name.equals(harvestcraftFish[21])) {
            return 40;
        } else if (name.equals(harvestcraftFish[22])) {
            return 30;
        } else if (name.equals(harvestcraftFish[23])) {
            return 5;
        } else if (name.equals(harvestcraftFish[24])) {
            return 8;
        } else {
            return 25;
        }
    }

    private static void addGregtechCentrifugeRecipe(final ItemStack input) {
        GT_Values.RA.addCentrifugeRecipe(
                input,
                null,
                null,
                FluidUtils.getFluidStack("methane", 96),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                19 * 20,
                5);
    }

    private static void addGregtechMaceratorRecipe(final ItemStack input) {
        GT_ModHandler.addPulverisationRecipe(
                input,
                ItemUtils.getItemStackOfAmountFromOreDict("dustMeatRaw", 1),
                ItemUtils.getItemStackOfAmountFromOreDict("dustTinyBone", 1),
                0);
    }

    private static void addGregtechFluidRecipe(final ItemStack input) {
        CORE.RA.addFluidExtractionRecipe(input, FluidUtils.getFluidStack("fishoil", 50), 16, 4);
    }
}
