package gtPlusPlus.core.util.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gnu.trove.strategy.HashingStrategy;
import gregtech.api.util.GT_Recipe;

public class RecipeHashStrat {

    public static final HashingStrategy<GT_Recipe> RecipeHashingStrategy = new HashingStrategy<>() {

        // Not implemented.
        @Override
        public int computeHashCode(GT_Recipe stack) {
            return 0;
        }

        @Override
        public boolean equals(GT_Recipe item1, GT_Recipe item2) {
            return IsRecipeEqual(item1, item2);
        }
    };

    public static boolean IsRecipeEqual(GT_Recipe item1, GT_Recipe item2) {
        // sort all the arrays for item1
        RecipeHashStrat.sortItemStackArray(item1.mInputs);
        RecipeHashStrat.sortItemStackArray(item1.mOutputs);
        RecipeHashStrat.sortFluidStackArray(item1.mFluidInputs);
        RecipeHashStrat.sortFluidStackArray(item1.mFluidOutputs);
        // sort all the arrays for item2
        RecipeHashStrat.sortItemStackArray(item2.mInputs);
        RecipeHashStrat.sortItemStackArray(item2.mOutputs);
        RecipeHashStrat.sortFluidStackArray(item2.mFluidInputs);
        RecipeHashStrat.sortFluidStackArray(item2.mFluidOutputs);

        // checks if the recipe EUt, Duration, inputs and outputs for both items and fluids are equal
        if (item1.mEUt != item2.mEUt) {
            return false;
        }
        if (item1.mDuration != item2.mDuration) {
            return false;
        }
        if (!areItemsStackArraysEqual(item1.mInputs, item2.mInputs)) {
            return false;
        }
        if (!areItemsStackArraysEqual(item1.mOutputs, item2.mOutputs)) {
            return false;
        }
        if (!areFluidStackArraysEqual(item1.mFluidInputs, item2.mFluidInputs)) {
            return false;
        }
        if (!areFluidStackArraysEqual(item1.mFluidOutputs, item2.mFluidOutputs)) {
            return false;
        }
        return true;

    }

    public static void sortItemStackArray(ItemStack[] itemStackArray) {
        Arrays.sort(itemStackArray, Comparator.comparing(ItemStack::toString));
    }

    public static void sortFluidStackArray(FluidStack[] fluidStackArray) {
        Arrays.sort(fluidStackArray, Comparator.comparing(FluidStack::toString));
    }

    public static boolean areItemsStackArraysEqual(ItemStack[] array1, ItemStack[] array2) {
        if (array1.length != array2.length) {
            return false;
        }
        ArrayList<String> array1asStrings = new ArrayList<>();
        ArrayList<String> array2asStrings = new ArrayList<>();
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != null && array2[i] != null) {
                array1asStrings.add(array1[i].toString());
                array2asStrings.add(array2[i].toString());
            } else {
                return false;
            }
        }
        return array1asStrings.equals(array2asStrings);
    }

    public static boolean areFluidStackArraysEqual(FluidStack[] array1, FluidStack[] array2) {
        if (array1.length != array2.length) {
            return false;
        }
        ArrayList<String> array1asStrings = new ArrayList<>();
        ArrayList<String> array2asStrings = new ArrayList<>();
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != null && array2[i] != null) {
                array1asStrings.add(RecipeHashStrat.createUniqueFluidStackString(array1[i]));
                array2asStrings.add(RecipeHashStrat.createUniqueFluidStackString(array2[i]));
            } else {
                return false;
            }
        }
        return array1asStrings.equals(array2asStrings);
    }

    // FluidStack really doesn't have an overridden toString huh...
    public static String createUniqueFluidStackString(FluidStack fluidStack) {
        return fluidStack.getUnlocalizedName() + ":" + fluidStack.amount;
    }
}
