package miscutil.core.handler.registration.gregtech;

import miscutil.core.lib.LoadedMods;
import miscutil.core.util.Utils;
import miscutil.core.xmod.gregtech.api.enums.GregtechItemList;
import miscutil.core.xmod.gregtech.common.tileentities.generators.GregtechMetaTileEntityRocketFuelGenerator;

public class GregtechRocketFuelGenerator {
	
	public static void run()
	{
		if (LoadedMods.Gregtech){
			Utils.LOG_INFO("Gregtech5u Content | Registering Industrial Rocket Engines.");
			run1();
		}

	}

	private static void run1(){
		GregtechItemList.Rocket_Engine_EV.set(new GregtechMetaTileEntityRocketFuelGenerator(793, "advancedgenerator.rocketFuel.tier.01", "Basic Rocket Engine", 4).getStackForm(1L));
		GregtechItemList.Rocket_Engine_IV.set(new GregtechMetaTileEntityRocketFuelGenerator(794, "advancedgenerator.rocketFuel.tier.02", "Advanced Rocket Engine", 5).getStackForm(1L));
		GregtechItemList.Rocket_Engine_LuV.set(new GregtechMetaTileEntityRocketFuelGenerator(795, "advancedgenerator.rocketFuel.tier.03", "Turbo Rocket Engine", 6).getStackForm(1L));
	}
	
}
