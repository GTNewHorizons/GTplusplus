package gtPlusPlus.core.common.compat;

import gtPlusPlus.xmod.growthcraft.fishtrap.FishTrapHandler;

public class COMPAT_HarvestCraft {

    public static void OreDict() {
        run();
    }

    private static void run() {
        FishTrapHandler.pamsHarvestCraftCompat();
    }
}
