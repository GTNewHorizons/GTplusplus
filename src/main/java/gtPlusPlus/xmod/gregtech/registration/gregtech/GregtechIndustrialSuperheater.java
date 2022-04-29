package gtPlusPlus.xmod.gregtech.registration.gregtech;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing.GregtechMetaTileEntity_IndustrialSuperheater;

public class GregtechIndustrialSuperheater {

    public static void run() {
        if (gtPlusPlus.core.lib.LoadedMods.Gregtech) {
            Logger.INFO("Gregtech5u Content | Registering Industrial Superheater Multiblock.");
            run1();
        }

    }

    private static void run1() {
        GregtechItemList.Controller_IndustrialSuperheater.set(new GregtechMetaTileEntity_IndustrialSuperheater(31080,
                "industrialsuperheater.controller.tier.single", "Industrial Superheater").getStackForm(1L));

    }
}