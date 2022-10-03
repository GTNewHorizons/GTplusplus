package gtPlusPlus.xmod.gregtech.registration.gregtech;

import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.common.tileentities.storage.GregtechMetaSafeBlock;

public class GregtechSafeBlock {
    public static void run() {
        if (gtPlusPlus.core.lib.LoadedMods.Gregtech) {
            Logger.INFO("Gregtech5u Content | Registering Lockable Safe Blocks.");
            if (CORE.ConfigSwitches.enableMachine_Safes) {
                run1();
            }
        }
    }

    private static void run1() {

        GregtechItemList.GT_Safe_ULV.set(
                new GregtechMetaSafeBlock(780, "protection.playersafe.tier.00", "Ultra Low Voltage Player Safe", 0)
                        .getStackForm(1L));
        GregtechItemList.GT_Safe_LV.set(
                new GregtechMetaSafeBlock(781, "protection.playersafe.tier.01", "Low Voltage Player Safe", 1)
                        .getStackForm(1L));
        GregtechItemList.GT_Safe_MV.set(
                new GregtechMetaSafeBlock(782, "protection.playersafe.tier.02", "Medium Voltage Player Safe", 2)
                        .getStackForm(1L));
        GregtechItemList.GT_Safe_HV.set(
                new GregtechMetaSafeBlock(783, "protection.playersafe.tier.03", "High Voltage Player Safe", 3)
                        .getStackForm(1L));
        GregtechItemList.GT_Safe_EV.set(
                new GregtechMetaSafeBlock(784, "protection.playersafe.tier.04", "Extreme Voltage Player Safe", 4)
                        .getStackForm(1L));
        GregtechItemList.GT_Safe_IV.set(
                new GregtechMetaSafeBlock(785, "protection.playersafe.tier.05", "Insane Voltage Player Safe", 5)
                        .getStackForm(1L));
        GregtechItemList.GT_Safe_LuV.set(
                new GregtechMetaSafeBlock(786, "protection.playersafe.tier.06", "Ludicrous Voltage Player Safe", 6)
                        .getStackForm(1L));
        GregtechItemList.GT_Safe_ZPM.set(
                new GregtechMetaSafeBlock(787, "protection.playersafe.tier.07", "ZPM Voltage Player Safe", 7)
                        .getStackForm(1L));
        GregtechItemList.GT_Safe_UV.set(
                new GregtechMetaSafeBlock(788, "protection.playersafe.tier.08", "Ultimate Voltage Player Safe", 8)
                        .getStackForm(1L));
        GregtechItemList.GT_Safe_MAX.set(
                new GregtechMetaSafeBlock(789, "protection.playersafe.tier.09", "MAX Voltage Player Safe", 9)
                        .getStackForm(1L));

        // To-Do Change Recipes
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Safe_ULV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "CMV",
                    " X ",
                        'M',
                    ItemList.Hull_ULV,
                        'V',
                    OrePrefixes.circuit.get(Materials.Basic),
                        'C',
                    OreDictNames.craftingChest,
                        'X',
                    OrePrefixes.circuit.get(Materials.Basic)
                });
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Safe_LV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "CMV",
                    " X ",
                        'M',
                    ItemList.Hull_LV,
                        'V',
                    OrePrefixes.circuit.get(Materials.Basic),
                        'C',
                    OreDictNames.craftingChest,
                        'X',
                    OrePrefixes.circuit.get(Materials.Good)
                });
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Safe_MV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "CMV",
                    " X ",
                        'M',
                    ItemList.Hull_MV,
                        'V',
                    OrePrefixes.circuit.get(Materials.Good),
                        'C',
                    OreDictNames.craftingChest,
                        'X',
                    OrePrefixes.circuit.get(Materials.Good)
                });
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Safe_HV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "CMV",
                    " X ",
                        'M',
                    ItemList.Hull_HV,
                        'V',
                    OrePrefixes.circuit.get(Materials.Good),
                        'C',
                    OreDictNames.craftingChest,
                        'X',
                    OrePrefixes.circuit.get(Materials.Advanced)
                });
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Safe_EV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "CMV",
                    " X ",
                        'M',
                    ItemList.Hull_EV,
                        'V',
                    OrePrefixes.circuit.get(Materials.Advanced),
                        'C',
                    OreDictNames.craftingChest,
                        'X',
                    OrePrefixes.circuit.get(Materials.Elite)
                });
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Safe_IV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "CMV",
                    " X ",
                        'M',
                    ItemList.Hull_IV,
                        'V',
                    OrePrefixes.circuit.get(Materials.Advanced),
                        'C',
                    OreDictNames.craftingChest,
                        'X',
                    OrePrefixes.circuit.get(Materials.Master)
                });
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Safe_LuV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "CMV",
                    " X ",
                        'M',
                    ItemList.Hull_LuV,
                        'V',
                    OrePrefixes.circuit.get(Materials.Elite),
                        'C',
                    OreDictNames.craftingChest,
                        'X',
                    OrePrefixes.circuit.get(Materials.Master)
                });
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Safe_ZPM.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "CMV",
                    " X ",
                        'M',
                    ItemList.Hull_ZPM,
                        'V',
                    OrePrefixes.circuit.get(Materials.Elite),
                        'C',
                    OreDictNames.craftingChest,
                        'X',
                    OrePrefixes.circuit.get(Materials.Master)
                });
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Safe_UV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "CMV",
                    " X ",
                        'M',
                    ItemList.Hull_UV,
                        'V',
                    OrePrefixes.circuit.get(Materials.Master),
                        'C',
                    OreDictNames.craftingChest,
                        'X',
                    OrePrefixes.circuit.get(Materials.Superconductor)
                });
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Safe_MAX.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "CMV",
                    " X ",
                        'M',
                    ItemList.Hull_MAX,
                        'V',
                    OrePrefixes.circuit.get(Materials.Master),
                        'C',
                    OreDictNames.craftingChest,
                        'X',
                    OrePrefixes.circuit.get(Materials.Infinite)
                });
    }
}
