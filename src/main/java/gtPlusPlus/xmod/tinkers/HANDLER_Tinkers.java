package gtPlusPlus.xmod.tinkers;

import gtPlusPlus.api.objects.data.AutoMap;
import gtPlusPlus.xmod.tinkers.material.BaseTinkersMaterial;
import gtPlusPlus.xmod.tinkers.util.TinkersDryingRecipe;
import gtPlusPlus.xmod.tinkers.util.TinkersUtils;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import static gregtech.api.enums.Mods.TinkerConstruct;

public class HANDLER_Tinkers {

    public static AutoMap<BaseTinkersMaterial> mTinkerMaterials = new AutoMap<>();

    public static void postInit() {
        if (TinkerConstruct.isModLoaded()) {

            Fluid pyrotheumFluid = FluidRegistry.getFluid("pyrotheum");
            if (pyrotheumFluid != null) {
                // Enable Pyrotheum as Fuel for the Smeltery
                TinkersUtils.addSmelteryFuel(pyrotheumFluid, 5000, 70); // pyrotheum lasts 3.5 seconds per 15 mb
            }

            // Generate Drying Rack recipes
            TinkersDryingRecipe.generateAllDryingRecipes();
        }
    }
}
