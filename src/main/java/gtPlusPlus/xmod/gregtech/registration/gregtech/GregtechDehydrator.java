package gtPlusPlus.xmod.gregtech.registration.gregtech;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.lib.LoadedMods;
import gtPlusPlus.core.material.ALLOY;
import gtPlusPlus.core.recipe.common.CI;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Dehydrator;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing.GregtechMetaTileEntity_IndustrialDehydrator;
import net.minecraft.item.ItemStack;

public class GregtechDehydrator {
    public static void run() {
        if (LoadedMods.Gregtech) {
            Logger.INFO("Gregtech5u Content | Registering Chemical Dehydrators.");
            if (CORE.ConfigSwitches.enableMachine_Dehydrators) {
                run1();
            }
        }
    }

    private static void run1() {

        /*
         *
         * public GT_MetaTileEntity_BasicMachine_GT_Recipe( int aID, String
         * aName, String aNameRegional, int aTier, String aDescription,
         * GT_Recipe_Map aRecipes, int aInputSlots, int aOutputSlots, int
         * aTankCapacity, int aGUIParameterA, int aGUIParameterB, String
         * aGUIName, String aSound, boolean aSharedTank, boolean
         * aRequiresFluidForFiltering, int aSpecialEffect, String aOverlays,
         * Object[] aRecipe) {
         *
         */

        // Basic
        GregtechItemList.GT_Dehydrator_MV.set(new GT_MetaTileEntity_Dehydrator(
                        911,
                        "machine.dehydrator.tier.00",
                        "Basic Dehydrator I",
                        2,
                        "This dehydrates your Grapes into Raisins.",
                        16000)
                .getStackForm(1L));

        GregtechItemList.GT_Dehydrator_HV.set(new GT_MetaTileEntity_Dehydrator(
                        912,
                        "machine.dehydrator.tier.01",
                        "Basic Dehydrator II",
                        3,
                        "This dehydrates your Grapes into Raisins.",
                        32000)
                .getStackForm(1L));

        // Chemical

        GregtechItemList.GT_Dehydrator_EV.set(new GT_MetaTileEntity_Dehydrator(
                        813,
                        "advancedmachine.dehydrator.tier.01",
                        "Chemical Dehydrator I",
                        4,
                        "A hangover is the way your body reacts to dehydration.",
                        48000)
                .getStackForm(1L));

        GregtechItemList.GT_Dehydrator_IV.set(new GT_MetaTileEntity_Dehydrator(
                        814,
                        "advancedmachine.dehydrator.tier.02",
                        "Chemical Dehydrator II",
                        5,
                        "A hangover is the way your body reacts to dehydration.",
                        48000)
                .getStackForm(1L));

        GregtechItemList.GT_Dehydrator_LuV.set(new GT_MetaTileEntity_Dehydrator(
                        815,
                        "advancedmachine.dehydrator.tier.03",
                        "Chemical Dehydrator III",
                        6,
                        "You could probably make space icecream with this..",
                        64000)
                .getStackForm(1L));

        GregtechItemList.GT_Dehydrator_ZPM.set(new GT_MetaTileEntity_Dehydrator(
                        816,
                        "advancedmachine.dehydrator.tier.04",
                        "Chemical Dehydrator IV",
                        7,
                        "You can definitely make space icecream with this..",
                        64000)
                .getStackForm(1L));

        // Advanced
        GregtechItemList.Controller_Vacuum_Furnace.set(
                new GregtechMetaTileEntity_IndustrialDehydrator(995, "multimachine.adv.vacuumfurnace", "Utupu-Tanuri")
                        .getStackForm(1L));

        ItemStack coilWire1 =
                ItemUtils.getItemStackWithMeta(true, "miscutils:itemDehydratorCoilWire", "coilWire1", 0, 4);
        ItemStack coilWire2 =
                ItemUtils.getItemStackWithMeta(true, "miscutils:itemDehydratorCoilWire:1", "coilWire2", 1, 4);
        ItemStack coilWire3 =
                ItemUtils.getItemStackWithMeta(true, "miscutils:itemDehydratorCoilWire:2", "coilWire3", 2, 4);
        ItemStack coilWire4 =
                ItemUtils.getItemStackWithMeta(true, "miscutils:itemDehydratorCoilWire:3", "coilWire4", 3, 4);
        ItemStack coilT1 = ItemUtils.getItemStackWithMeta(true, "miscutils:itemDehydratorCoil", "coil1", 0, 1);
        ItemStack coilT2 = ItemUtils.getItemStackWithMeta(true, "miscutils:itemDehydratorCoil:1", "coil2", 1, 1);
        ItemStack coilT3 = ItemUtils.getItemStackWithMeta(true, "miscutils:itemDehydratorCoil:2", "coil3", 2, 1);
        ItemStack coilT4 = ItemUtils.getItemStackWithMeta(true, "miscutils:itemDehydratorCoil:3", "coil4", 3, 1);
        ItemStack spoolT1 = ItemUtils.getItemStackOfAmountFromOreDict("pipeMediumBronze", 1);
        ItemStack spoolT2 = ItemUtils.getItemStackOfAmountFromOreDict("pipeMediumSteel", 1);
        ItemStack spoolT3 = ItemUtils.getItemStackOfAmountFromOreDict("pipeMediumStainlessSteel", 1);
        ItemStack spoolT4 = ItemUtils.getItemStackOfAmountFromOreDict("pipeMediumTitanium", 1);

        // Make some coils by wrapping wire around a spool.
        GT_Values.RA.addAssemblerRecipe(coilWire1, spoolT1, coilT1, 8 * 20, 120);
        GT_Values.RA.addAssemblerRecipe(coilWire2, spoolT2, coilT2, 8 * 20, 240);
        GT_Values.RA.addAssemblerRecipe(coilWire3, spoolT3, coilT3, 8 * 20, 480);
        GT_Values.RA.addAssemblerRecipe(coilWire4, spoolT4, coilT4, 8 * 20, 960);

        // Add recipes for the Dehydrators.

        // Basic
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Dehydrator_MV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "ECE",
                    "WMW",
                    "GPG",
                        'M',
                    ItemList.Hull_MV,
                        'P',
                    ItemList.Robot_Arm_MV,
                        'E',
                    OrePrefixes.wireFine.get(Materials.RedAlloy),
                        'C',
                    OrePrefixes.circuit.get(Materials.Good),
                        'W',
                    OrePrefixes.cableGt04.get(Materials.Copper),
                        'G',
                    OrePrefixes.gearGt.get(Materials.Steel)
                });
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Dehydrator_HV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "ECE",
                    "WMW",
                    "GPG",
                        'M',
                    ItemList.Hull_HV,
                        'P',
                    ItemList.Robot_Arm_HV,
                        'E',
                    OrePrefixes.wireFine.get(Materials.Electrum),
                        'C',
                    OrePrefixes.circuit.get(Materials.Advanced),
                        'W',
                    OrePrefixes.cableGt04.get(Materials.Silver),
                        'G',
                    ALLOY.POTIN.getGear(1)
                });

        // Chemical
        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Dehydrator_EV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "ECE",
                    "WMW",
                    "GPG",
                        'M',
                    ItemList.Hull_EV,
                        'P',
                    ItemList.Robot_Arm_EV,
                        'E',
                    coilT1,
                        'C',
                    OrePrefixes.circuit.get(Materials.Data),
                        'W',
                    OrePrefixes.cableGt04.get(Materials.Aluminium),
                        'G',
                    ALLOY.TUMBAGA.getGear(1)
                });

        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Dehydrator_IV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "ECE",
                    "WMW",
                    "GPG",
                        'M',
                    ItemList.Hull_IV,
                        'P',
                    ItemList.Robot_Arm_IV,
                        'E',
                    coilT2,
                        'C',
                    OrePrefixes.circuit.get(Materials.Elite),
                        'W',
                    OrePrefixes.cableGt04.get(Materials.Tungsten),
                        'G',
                    ALLOY.INCONEL_690.getGear(1)
                });

        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Dehydrator_LuV.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "ECE",
                    "WMW",
                    "GPG",
                        'M',
                    ItemList.Hull_LuV,
                        'P',
                    CI.robotArm_LuV,
                        'E',
                    coilT3,
                        'C',
                    OrePrefixes.circuit.get(Materials.Master),
                        'W',
                    OrePrefixes.cableGt04.get(Materials.Naquadah),
                        'G',
                    ALLOY.HASTELLOY_N.getGear(1)
                });

        GT_ModHandler.addCraftingRecipe(
                GregtechItemList.GT_Dehydrator_ZPM.get(1L, new Object[0]),
                GT_ModHandler.RecipeBits.DISMANTLEABLE
                        | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE
                        | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] {
                    "ECE",
                    "WMW",
                    "GPG",
                        'M',
                    ItemList.Hull_ZPM,
                        'P',
                    CI.robotArm_ZPM,
                        'E',
                    coilT4,
                        'C',
                    OrePrefixes.circuit.get(Materials.Ultimate),
                        'W',
                    OrePrefixes.cableGt04.get(Materials.Osmium),
                        'G',
                    ALLOY.ZERON_100.getGear(1)
                });
    }
}
