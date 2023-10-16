package gtPlusPlus.preloader.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import gtPlusPlus.preloader.CORE_Preloader;
import gtPlusPlus.preloader.Preloader_Logger;
import gtPlusPlus.preloader.asm.transformers.Preloader_Transformer_Handler;

@SortingIndex(10097)
@MCVersion(value = "1.7.10")
@IFMLLoadingPlugin.TransformerExclusions("gtPlusPlus.preloader")
@IFMLLoadingPlugin.Name(CORE_Preloader.NAME)
public class Preloader_FMLLoadingPlugin implements IFMLLoadingPlugin {

    // -Dfml.coreMods.load=gtPlusPlus.preloader.asm.Preloader_FMLLoadingPlugin

    static {
        Preloader_Logger.INFO("Initializing IFMLLoadingPlugin");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String[] getASMTransformerClass() {
        // This will return the name of the class
        return new String[] { Preloader_Transformer_Handler.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        // This is the name of our dummy container
        return Preloader_DummyContainer.class.getName();
    }

    @Override
    public String getSetupClass() {
        // return Preloader_SetupClass.class.getName();
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        CORE_Preloader.DEV_ENVIRONMENT = !(boolean) data.get("runtimeDeobfuscationEnabled");
        File mcDir = (File) data.get("mcLocation");
        if (mcDir != null && mcDir.exists()) {
            CORE_Preloader.setMinecraftDirectory(mcDir);
            Preloader_Logger.INFO("Set McDir via Preloader_SetupClass");
        }
        CORE_Preloader.DEBUG_MODE = AsmConfig.debugMode;
        Preloader_Logger.INFO(
                "Running on " + gtPlusPlus.preloader.CORE_Preloader.JAVA_VERSION
                        + " | Development Environment: "
                        + CORE_Preloader.DEV_ENVIRONMENT);
    }
}
