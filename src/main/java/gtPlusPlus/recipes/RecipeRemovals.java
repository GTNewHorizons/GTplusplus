package gtPlusPlus.recipes;

import static gtPlusPlus.core.recipe.common.CI.bits;
import static gtPlusPlus.core.util.minecraft.MaterialUtils.getMaterialName;

import java.util.Collection;

import gregtech.GT_Mod;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTPP_Recipe;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.api.objects.data.AutoMap;
import gtPlusPlus.api.objects.data.Pair;
import gtPlusPlus.core.material.ELEMENT;
import gtPlusPlus.core.util.minecraft.MaterialUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.ArrayUtils;

import advsolar.common.AdvancedSolarPanel;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_Recipe;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.recipe.common.CI;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.RecipeUtils;

public class RecipeRemovals {

    public static void postInit() {
        if (Mods.AdvancedSolarPanel.isModLoaded()) {
            RecipeUtils.removeRecipeByOutput(ItemUtils.getSimpleStack(AdvancedSolarPanel.blockMolecularTransformer));
        }
    }

    public static void onLoadComplete() {
        removeCrudeTurbineRotors();
        removeGTRareEarthRecipe();
        if (CORE.ConfigSwitches.enableHarderRecipesForHighTierCasings) {
            // Casings
            Logger.INFO("Removing shaped crafting for Casings.");
            RecipeUtils.removeRecipeByOutput(CI.machineCasing_LuV);
            RecipeUtils.removeRecipeByOutput(CI.machineCasing_ZPM);
            RecipeUtils.removeRecipeByOutput(CI.machineCasing_UV);

            // Hulls
            Logger.INFO("Removing shaped crafting for Hulls.");
            RecipeUtils.removeRecipeByOutput(CI.machineHull_LuV);
            RecipeUtils.removeRecipeByOutput(CI.machineHull_ZPM);
            RecipeUtils.removeRecipeByOutput(CI.machineHull_UV);

            removeOldHighTierCasingRecipes();
        }
    }

    // Doesn't actually remove recipes, just hide them
    private static void removeCrudeTurbineRotors() {
        int aRemoved = 0;
        int CUT = CORE.turbineCutoffBase;
        Item aU;
        Collection<GT_Recipe> aAssRecipes = GT_Recipe.GT_Recipe_Map.sAssemblerRecipes.mRecipeList;
        // 170, 172, 174, 176
        if (aAssRecipes.size() > 0) {
            for (GT_Recipe aG : aAssRecipes) {
                if (ArrayUtils.isNotEmpty(aG.mOutputs)) {
                    for (ItemStack aI : aG.mOutputs) {
                        if (aI == null) {
                            continue;
                        }
                        aU = aI.getItem();
                        if (aU == null) {
                            continue;
                        }
                        if (aU instanceof GT_MetaGenerated_Tool_01) {
                            int aMeta = aI.getItemDamage();
                            // Found a Turbine
                            if (aMeta >= 170 && aMeta <= 176) {
                                int aCutoff;
                                String aType;
                                switch (aMeta) {
                                    case 170:
                                        aCutoff = CUT;
                                        aType = "Small ";
                                        break;
                                    case 172:
                                        aCutoff = 2 * CUT;
                                        aType = "";
                                        break;
                                    case 174:
                                        aCutoff = 3 * CUT;
                                        aType = "Large ";
                                        break;
                                    default: // 176
                                        aCutoff = 4 * CUT;
                                        aType = "Huge ";
                                        break;
                                }
                                Materials aMainMaterial = GT_MetaGenerated_Tool.getPrimaryMaterial(aI);
                                Materials aSecondaryMaterial = GT_MetaGenerated_Tool.getSecondaryMaterial(aI);
                                long rotorDurabilityMax = GT_MetaGenerated_Tool.getToolMaxDamage(aI);
                                if (rotorDurabilityMax < aCutoff) {
                                    Logger.WARNING(
                                            "[Turbine Cleanup] " + getMaterialName(aMainMaterial)
                                                    + " "
                                                    + aType
                                                    + "Turbines have "
                                                    + rotorDurabilityMax
                                                    + ", which is below the cutoff durability of "
                                                    + aCutoff
                                                    + ", disabling.");
                                    aG.mEnabled = false;
                                    aG.mHidden = true;
                                    aG.mCanBeBuffered = false;
                                    aRemoved++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        Logger.INFO("Removed " + aRemoved + " useless Turbines.");
    }

    private static void removeGTRareEarthRecipe(){

        Logger.INFO("Processing Gregtech recipe maps, removing recipes to suit GT++.");
        // Remove Rare Earth Centrifuging
        // 1 Rare Earth Dust - 25% chance for small piles of: neodymium, yttrium, lanthanum, cerium, cadmium, and
        // caesium
        // Replaced by advanced sifting recipe.
        GT_Recipe aRareEarthCentrifuging = GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.findRecipe(
                null,
                false,
                20,
                new FluidStack[] {},
                new ItemStack[] { ItemUtils.getItemStackOfAmountFromOreDict("dustRareEarth", 1) });
        if (aRareEarthCentrifuging != null && aRareEarthCentrifuging.mEnabled) {
            aRareEarthCentrifuging.mEnabled = false;
            aRareEarthCentrifuging.mHidden = true;
            GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.mRecipeList.remove(aRareEarthCentrifuging);
            GTPP_Recipe.GTPP_Recipe_Map.sMultiblockCentrifugeRecipes_GT.mRecipeList.remove(aRareEarthCentrifuging);
            Logger.INFO("Removed vanilla GT Rare Earth processing.");
        }

    }

    private static void removeOldHighTierCasingRecipes() {
        // Static objects to save memory
        ItemStack aCasing_LUV = CI.machineCasing_LuV;
        ItemStack aCasing_ZPM = CI.machineCasing_ZPM;
        ItemStack aCasing_UV = CI.machineCasing_UV;
        ItemStack aCasing_MAX = CI.machineCasing_MAX;

        ItemStack aHull_LUV = CI.machineHull_LuV;
        ItemStack aHull_ZPM = CI.machineHull_ZPM;
        ItemStack aHull_UV = CI.machineHull_UV;
        ItemStack aHull_MAX = CI.machineHull_MAX;

        int aTier_LUV = 5;
        int aTier_ZPM = 6;
        int aTier_UV = 7;
        // int aTier_MAX = 8;

        ItemStack[] aCasings = new ItemStack[] { aCasing_LUV, aCasing_ZPM, aCasing_UV, aCasing_MAX };
        ItemStack[] aHulls = new ItemStack[] { aHull_LUV, aHull_ZPM, aHull_UV, aHull_MAX };

        // Modify Assembler Recipes
        Logger.INFO(
                "Attempting to modify existing Assembly recipes for Casings & Hulls, this should provide best compatibility.");
        int aUpdateCount = 0;

        AutoMap<Pair<GT_Recipe, GT_Recipe>> aDataToModify = new AutoMap<Pair<GT_Recipe, GT_Recipe>>();

        Outer: for (final GT_Recipe r : GT_Recipe.GT_Recipe_Map.sAssemblerRecipes.mRecipeList) {

            if (r != null && r.mOutputs != null && r.mOutputs.length > 0) {

                GT_Recipe aOldRecipeCopy = r;
                GT_Recipe aNewRecipe = r.copy();

                // Casings
                Inner: for (ItemStack aCasingObject : aCasings) {
                    if (GT_Utility.areStacksEqual(aOldRecipeCopy.mOutputs[0], aCasingObject)) {
                        String aOutputName = ItemUtils.getItemName(aOldRecipeCopy.mOutputs[0]);
                        Logger.INFO("Attempting to Modify Assembly Recipe for " + aOutputName);
                        // Replace Chrome
                        if (GT_Utility.areStacksEqual(aOldRecipeCopy.mOutputs[0], aCasing_LUV)) {
                            aNewRecipe = replaceItemInRecipeWithAnother(
                                    aOldRecipeCopy,
                                    ItemUtils.getItemStackOfAmountFromOreDict("plateChrome", 1),
                                    ELEMENT.getInstance().SELENIUM.getPlate(1));
                            aDataToModify.put(new Pair<>(r, aNewRecipe));
                            aUpdateCount++;
                            continue Outer;
                        }
                        // Replace Iridium
                        else if (GT_Utility.areStacksEqual(aOldRecipeCopy.mOutputs[0], aCasing_ZPM)) {
                            aNewRecipe = replaceItemInRecipeWithAnother(
                                    aOldRecipeCopy,
                                    ItemUtils.getItemStackOfAmountFromOreDict("plateIridium", 1),
                                    CI.getPlate(aTier_ZPM, 1));
                            aDataToModify.put(new Pair<>(r, aNewRecipe));
                            aUpdateCount++;
                            continue Outer;
                        }
                        // Replace Osmium
                        else if (GT_Utility.areStacksEqual(aOldRecipeCopy.mOutputs[0], aCasing_UV)) {
                            aNewRecipe = replaceItemInRecipeWithAnother(
                                    aOldRecipeCopy,
                                    ItemUtils.getItemStackOfAmountFromOreDict("plateOsmium", 1),
                                    CI.getPlate(aTier_UV, 1));
                            aDataToModify.put(new Pair<>(r, aNewRecipe));
                            aUpdateCount++;
                            continue Outer;
                        } else {
                            continue Inner;
                        }
                    }
                }

                // Hulls
                Inner: for (ItemStack aHullObject : aHulls) {
                    if (GT_Utility.areStacksEqual(aOldRecipeCopy.mOutputs[0], aHullObject)) {
                        String aOutputName = ItemUtils.getItemName(aOldRecipeCopy.mOutputs[0]);
                        Logger.INFO("Attempting to Modify Assembly Recipe for " + aOutputName);
                        // Replace Chrome
                        if (GT_Utility.areStacksEqual(aOldRecipeCopy.mOutputs[0], aHull_LUV)) {
                            aNewRecipe = replaceItemInRecipeWithAnother(
                                    aOldRecipeCopy,
                                    ItemUtils.getItemStackOfAmountFromOreDict("plateChrome", 1),
                                    ELEMENT.getInstance().SELENIUM.getPlate(1));
                            aDataToModify.put(new Pair<>(r, aNewRecipe));
                            aUpdateCount++;
                            continue Outer;
                        }
                        // Replace Iridium
                        else if (GT_Utility.areStacksEqual(aOldRecipeCopy.mOutputs[0], aHull_ZPM)) {
                            aNewRecipe = replaceItemInRecipeWithAnother(
                                    aOldRecipeCopy,
                                    ItemUtils.getItemStackOfAmountFromOreDict("plateIridium", 1),
                                    CI.getPlate(aTier_ZPM, 1));
                            aDataToModify.put(new Pair<>(r, aNewRecipe));
                            aUpdateCount++;
                            continue Outer;
                        }
                        // Replace Osmium
                        else if (GT_Utility.areStacksEqual(aOldRecipeCopy.mOutputs[0], aHull_UV)) {
                            aNewRecipe = replaceItemInRecipeWithAnother(
                                    aOldRecipeCopy,
                                    ItemUtils.getItemStackOfAmountFromOreDict("plateOsmium", 1),
                                    CI.getPlate(aTier_UV, 1));
                            aDataToModify.put(new Pair<>(r, aNewRecipe));
                            aUpdateCount++;
                            continue Outer;
                        } else {
                            continue Inner;
                        }
                    }
                }
            }
        }

        Logger.INFO("There is " + aUpdateCount + " recipes flagged for update.");

        if (aUpdateCount > 0) {
            for (Pair<GT_Recipe, GT_Recipe> g : aDataToModify) {
                updateRecipeMap(g.getKey(), g.getValue(), GT_Recipe.GT_Recipe_Map.sAssemblerRecipes);
            }
            Logger.INFO("Modified " + aUpdateCount + " recipes.");
        }

        Logger.INFO("Adding new Shaped recipes for Casings.");
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_LuV.get(1),
                bits,
                new Object[] { "PPP", "PwP", "PPP", 'P', ELEMENT.getInstance().SELENIUM.getPlate(1) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_ZPM.get(1),
                bits,
                new Object[] { "PPP", "PwP", "PPP", 'P', CI.getPlate(aTier_ZPM, 1) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_UV.get(1),
                bits,
                new Object[] { "PPP", "PwP", "PPP", 'P', CI.getPlate(aTier_UV, 1) });

        if (!GT_Mod.gregtechproxy.mHardMachineCasings) {
            Logger.INFO("Adding new easy Shaped recipes for Hulls.");
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Hull_LuV.get(1),
                    GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                    new Object[] { "CMC", 'M', ItemList.Casing_LuV, 'C',
                            OrePrefixes.cableGt01.get(Materials.VanadiumGallium) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Hull_ZPM.get(1),
                    GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                    new Object[] { "CMC", 'M', ItemList.Casing_ZPM, 'C',
                            OrePrefixes.cableGt01.get(Materials.Naquadah) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Hull_UV.get(1),
                    GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                    new Object[] { "CMC", 'M', ItemList.Casing_UV, 'C',
                            OrePrefixes.wireGt04.get(Materials.NaquadahAlloy) });
        } else {

            Materials aPolytetrafluoroethylene = MaterialUtils.getMaterial("Polytetrafluoroethylene", "Plastic");

            Logger.INFO("Adding new hard Shaped recipes for Hulls.");
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Hull_LuV.get(1),
                    GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                    new Object[] { "PHP", "CMC", 'M', ItemList.Casing_LuV, 'C',
                            OrePrefixes.cableGt01.get(Materials.VanadiumGallium), 'H',
                            ELEMENT.getInstance().SELENIUM.getPlate(1), 'P',
                            OrePrefixes.plate.get(Materials.Plastic) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Hull_ZPM.get(1),
                    GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                    new Object[] { "PHP", "CMC", 'M', ItemList.Casing_ZPM, 'C',
                            OrePrefixes.cableGt01.get(Materials.Naquadah), 'H', CI.getPlate(aTier_ZPM, 1), 'P',
                            OrePrefixes.plate.get(aPolytetrafluoroethylene) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Hull_UV.get(1),
                    GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                    new Object[] { "PHP", "CMC", 'M', ItemList.Casing_UV, 'C',
                            OrePrefixes.wireGt04.get(Materials.NaquadahAlloy), 'H', CI.getPlate(aTier_UV, 1), 'P',
                            OrePrefixes.plate.get(aPolytetrafluoroethylene) });
        }
    }

    private static void updateRecipeMap(GT_Recipe aOld, GT_Recipe aNew, GT_Recipe.GT_Recipe_Map aMap) {
        RecipeUtils.removeGtRecipe(aOld, aMap);
        RecipeUtils.addGtRecipe(aNew, aMap);
        Logger.INFO("Updating recipe map: " + aMap.mNEIName);
        Logger.INFO("Removed Recipe with hash: " + aOld.hashCode());
        Logger.INFO("Added Recipe with hash: " + aNew.hashCode());
    }

    private static GT_Recipe replaceItemInRecipeWithAnother(GT_Recipe aRecipe, ItemStack aExisting,
                                                            ItemStack aNewItem) {
        ItemStack[] aInputItemsCopy = aRecipe.mInputs;
        String aOutputName = ItemUtils.getItemName(aRecipe.mOutputs[0]);
        boolean aDidChange = false;
        Logger.INFO("Attempting to Modify Recipe for " + aOutputName);
        for (int i = 0; i < aRecipe.mInputs.length; i++) {
            ItemStack aCurrentInputSlot = aRecipe.mInputs[i];
            if (aCurrentInputSlot != null) {
                if (GT_Utility.areStacksEqual(aCurrentInputSlot, aExisting, true)) {
                    aInputItemsCopy[i] = ItemUtils.getSimpleStack(aNewItem, aCurrentInputSlot.stackSize);
                    aDidChange = true;
                }
            }
        }
        if (aDidChange) {
            aRecipe.mInputs = aInputItemsCopy;
            Logger.INFO("Modifed Recipe for " + aOutputName);
            return aRecipe;
        } else {
            Logger.INFO("Failed to Modify Recipe for " + aOutputName);
            return aRecipe;
        }
    }

}
