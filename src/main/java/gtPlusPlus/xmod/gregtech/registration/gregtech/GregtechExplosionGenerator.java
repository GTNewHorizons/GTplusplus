package gtPlusPlus.xmod.gregtech.registration.gregtech;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.GregtechMetaTileEntity_ExplosionGenerator;

public class GregtechExplosionGenerator {

    public static void run() {
        Logger.INFO("Gregtech5u Content | Registering Explosion Generator.");
        run1();
    }

    private static void run1() {
        GregtechItemList.Controller_ExplosionGenerator.set(
                new GregtechMetaTileEntity_ExplosionGenerator(
                        31160,
                        "gtpp.multimachine.explosiongenerator",
                        "Explosion Generator").getStackForm(1L));
    }
}
