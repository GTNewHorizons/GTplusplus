package gtPlusPlus.core.lib;

import java.util.HashMap;

import cpw.mods.fml.common.Loader;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechTextures;
import gtPlusPlus.xmod.gregtech.recipes.GregtechRecipeAdder;

public class LoadedMods {

    // missing in GT5U

    public static boolean CoFHCore = false;

    public static boolean TecTech = false; // Technus' Mod
    public static boolean PlayerAPI = false;



    @SuppressWarnings("deprecation")
    public static void checkLoaded() {
        Logger.INFO("Looking for optional mod prereqs.");
        Logger.INFO("Components enabled for: Gregtech");
        try {
            CORE.RA = new GregtechRecipeAdder();
            Logger.INFO("Created Gregtech recipe handler.");
            GregtechTextures.BlockIcons.VOID.name();
            GregtechTextures.ItemIcons.VOID.name();
            Logger.INFO("Created Gregtech texture handler.");
        } catch (final NullPointerException e) {
            Logger.INFO("Could NOT create a Gregtech recipe handler.");
        }
    }

    public static String getModVersion(final String modName) {
        final String ver = cpw.mods.fml.common.FMLCommonHandler.instance().findContainerFor(modName).getVersion();
        return ver;
    }

    private static final HashMap<String, Boolean> mLoadedModCache = new HashMap<String, Boolean>();

    public static boolean isModLoaded(String aModName) {
        Boolean aResult = mLoadedModCache.get(aModName);
        if (aResult == null) {
            boolean aTemp = Loader.isModLoaded(aModName);
            mLoadedModCache.put(aModName, aTemp);
            aResult = aTemp;
        }
        return aResult;
    }
}
