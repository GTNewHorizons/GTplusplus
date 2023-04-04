package gtPlusPlus.xmod.ob;

import gtPlusPlus.core.lib.LoadedMods;
import gtPlusPlus.core.util.Utils;

import static gregtech.api.enums.Mods.OpenBlocks;

public class HANDLER_OpenBlocks {
    public static void init() {
        if (OpenBlocks.isModLoaded()) {
            GliderHandler.populateBlacklist();
        }
    }

    public static void postInit() {
        if (OpenBlocks.isModLoaded()) {
            Utils.registerEvent(new GliderHandler());
        }
    }
}
