
package gtPlusPlus.xmod.gregtech.registration.gregtech;

import gregtech.api.util.GT_Recipe;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.util.minecraft.RecipeUtils;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.GregtechMetaTileEntityTreeFarm;

public class GregtechIndustrialTreeFarm {

	public static void run() {
		if (gtPlusPlus.core.lib.LoadedMods.Gregtech) {
			if (CORE.ConfigSwitches.enableMultiblock_TreeFarmer) {
				Logger.INFO("Gregtech5u Content | Registering Tree Farm Multiblock.");
				run1();
			}
		}

	}

	private static void run1() {
		GregtechItemList.Industrial_TreeFarm
				.set(new GregtechMetaTileEntityTreeFarm(836, "treefarm.controller.tier.single", "Tree Growth Simulator")
						.getStackForm(1L));
		GregtechMetaTileEntityTreeFarm.loadMapWoodFromSapling();
		Logger.INFO("Dumping Tree Growth Simulations.");
		for (GT_Recipe aRecipe : gregtech.api.util.GTPP_Recipe.GTPP_Recipe_Map.sTreeSimFakeRecipes.mRecipeList) {
			String[] aInfo = RecipeUtils.getRecipeInfo(aRecipe);
			for (String s : aInfo) {
				Logger.INFO(s);
			}
		}
	}
}
