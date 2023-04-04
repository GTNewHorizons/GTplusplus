package gtPlusPlus.xmod.sc2;

import vswe.stevescarts.ModuleData.GppModuleData;
import gtPlusPlus.core.lib.LoadedMods;

import static gregtech.api.enums.Mods.StevesCarts2;

public class HANDLER_SC2 {

    public static synchronized void preInit() {
        if (StevesCarts2.isModLoaded()) {
            GppModuleData.loadGpp();
        }
    }
}
