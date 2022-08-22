package gtPlusPlus.xmod.gregtech.registration.gregtech;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.GregtechMetaTileEntity_AlloyBlastSmelter;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.advanced.GregtechMetaTileEntity_Adv_AlloyBlastSmelter;

public class GregtechIndustrialBlastSmelter {

	public static void run() {
		if (gtPlusPlus.core.lib.LoadedMods.Gregtech) {
			Logger.INFO("Gregtech5u Content | Registering Industrial Alloy Blast Smelter Multiblock.");
			if (CORE.ConfigSwitches.enableMultiblock_AlloyBlastSmelter) {
				run1();
			}
		}

	}

	private static void run1() {
		// Industrial Alloy Blast Smelter Multiblock
		GregtechItemList.Industrial_AlloyBlastSmelter.set(new GregtechMetaTileEntity_AlloyBlastSmelter(810,
				"industrialsalloyamelter.controller.tier.single", "Alloy Blast Smelter").getStackForm(1L));
		GregtechItemList.Industrial_Adv_AlloyBlastSmelter.set(new GregtechMetaTileEntity_Adv_AlloyBlastSmelter(31150,
				"industrialsalloyamelter.controller.tier.adv", "Refined Amalgam Foundry").getStackForm(1L));






	}
}