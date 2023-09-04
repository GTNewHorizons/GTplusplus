package gtPlusPlus.core.util.recipe;

import static gtPlusPlus.core.slots.SlotIntegratedCircuit.isRegularProgrammableCircuit;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.item.ItemStack;

import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.set.hash.TCustomHashSet;
import gregtech.api.util.GT_Recipe;
import gtPlusPlus.api.objects.Logger;

public class GT_RecipeUtils {

    public static GT_Recipe[] removeDuplicates(GT_Recipe[] inputRecipes, String recipeMapName) {
        TCustomHashSet<GT_Recipe> recipes = new TCustomHashSet<>(RecipeHashStrat.RecipeHashingStrategy);
        ArrayList<GT_Recipe> recipeOutput = new ArrayList<>();
        ArrayList<GT_Recipe> removedRecipes = new ArrayList<>();
        TCustomHashMap<GT_Recipe, ItemStack> circuitMap = new TCustomHashMap<>(RecipeHashStrat.RecipeHashingStrategy);
        // create a new input ItemStack array that does not contain programmable circuits if they were in the recipe
        ArrayList<ItemStack> ItemInputsWithoutProgrammableCircuit = new ArrayList<>();
        for (GT_Recipe recipeInput : inputRecipes) {
            ItemStack savedCircuit = null;
            ItemInputsWithoutProgrammableCircuit.clear();
            // iterate over the recipe input items and add them all to a new array without any programmable circuits
            for (ItemStack itemStack : recipeInput.mInputs) {
                if (itemStack == null) {
                    continue;
                }
                if (isRegularProgrammableCircuit(itemStack) == -1) {
                    ItemInputsWithoutProgrammableCircuit.add(itemStack);
                } else {
                    savedCircuit = itemStack;
                }
            }
            GT_Recipe newRecipe = new GT_Recipe(
                    false,
                    ItemInputsWithoutProgrammableCircuit.toArray(new ItemStack[0]),
                    recipeInput.mOutputs,
                    recipeInput.mSpecialItems,
                    recipeInput.mChances,
                    recipeInput.mFluidInputs,
                    recipeInput.mFluidOutputs,
                    recipeInput.mDuration,
                    recipeInput.mEUt,
                    recipeInput.mSpecialValue);
            if (!recipes.contains(newRecipe)) {
                // if the recipes customHashSet does not contain the new recipe then add it
                recipes.add(newRecipe);
            } else {
                // add the removed recipe to the removal array with its original item inputs.
                GT_Recipe recipeCopy = newRecipe.copy();
                recipeCopy.mInputs = recipeInput.mInputs;
                removedRecipes.add(recipeCopy);
            }
            if (savedCircuit != null) {
                // if the current recipe has a circuit and the recipe (without circuits) is already in the
                // circuit map then check make sure the circuit map saves the recipe with the smallest circuit
                // damage value. This is to prevent a case where recipe load order would affect which duplicate
                // recipes with multiple circuit values gets removed.
                if (circuitMap.containsKey(newRecipe)) {
                    if (circuitMap.get(newRecipe).getItemDamage() > savedCircuit.getItemDamage()) {
                        circuitMap.put(newRecipe, savedCircuit);
                    }
                } else {
                    // If the circuit map does not have the recipe in it yet then add it
                    circuitMap.put(newRecipe, savedCircuit);
                }
            }
        }
        // iterate over all recipes without duplicates and add them to the output. If the recipe had a programmable
        // circuit in it then add it back with its damage value coming from the circuit map.
        for (GT_Recipe filteredRecipe : recipes.toArray(new GT_Recipe[0])) {
            // check to see if the recipe is in the circuit map
            if (circuitMap.contains(filteredRecipe)) {
                ArrayList<ItemStack> revertedInputItemStackArray = new ArrayList<>(
                        Arrays.asList(filteredRecipe.mInputs));
                // add the circuit back
                revertedInputItemStackArray.add(circuitMap.get(filteredRecipe));
                // update the item input array with the new input from
                // ItemInputsWithoutProgrammableCircuit + circuit map circuit
                filteredRecipe.mInputs = revertedInputItemStackArray.toArray(new ItemStack[0]);
            }
            // if the recipe was not in the circuit map then just add it the output as no updates to the item input
            // needs to be made
            recipeOutput.add(filteredRecipe);
        }
        // print results to log
        Logger.INFO(
                "Recipe Array duplication removal process completed for '" + recipeMapName
                        + "': '"
                        + removedRecipes.size()
                        + "' removed.");
        return recipeOutput.toArray(new GT_Recipe[0]);
    }
}
