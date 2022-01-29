package gtPlusPlus.core.tileentities;

import cpw.mods.fml.common.registry.GameRegistry;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.block.general.BlockSuperLight.TileEntitySuperLight;
import gtPlusPlus.core.block.machine.Machine_SuperJukebox.TileEntitySuperJukebox;
import gtPlusPlus.core.lib.LoadedMods;
import gtPlusPlus.core.tileentities.general.*;
import gtPlusPlus.core.tileentities.machines.*;
import gtPlusPlus.plugin.villagers.tile.TileEntityGenericSpawner;
import gtPlusPlus.xmod.gregtech.common.Meta_GT_Proxy;

public class ModTileEntities {

	public static void init() {
		Logger.INFO("Registering Tile Entities.");
		GameRegistry.registerTileEntity(TileEntityPooCollector.class, "TileEntityPooCollector");
		GameRegistry.registerTileEntity(TileEntityAdvPooCollector.class, "TileEntityAdvPooCollector");
		GameRegistry.registerTileEntity(TileEntityWorkbench.class, "TileWorkbench");
		GameRegistry.registerTileEntity(TileEntityWorkbenchAdvanced.class, "TileWorkbenchAdvanced");
		GameRegistry.registerTileEntity(TileEntityFishTrap.class, "TileFishTrap");
		GameRegistry.registerTileEntity(TileEntityFirepit.class, "TileFirePit");
		GameRegistry.registerTileEntity(TileEntityInfiniteFluid.class, "TileInfiniteFluid");
		GameRegistry.registerTileEntity(TileEntityProjectTable.class, "TileProjectTable");
		GameRegistry.registerTileEntity(TileEntityTradeTable.class, "TileTradeTable");
		GameRegistry.registerTileEntity(TileEntityModularityTable.class, "TileEntityModularityTable");
		GameRegistry.registerTileEntity(TileEntityXpConverter.class, "TileEntityXpConverter");
		GameRegistry.registerTileEntity(TileEntityGenericSpawner.class, "TileEntityGenericSpawner");
		GameRegistry.registerTileEntity(TileEntityCircuitProgrammer.class, "TileCircuitProgrammer");
		GameRegistry.registerTileEntity(TileEntityPlayerDoorBase.class, "TilePlayerDoorBase");
		GameRegistry.registerTileEntity(TileEntityDecayablesChest.class, "TileDecayablesChest");
		GameRegistry.registerTileEntity(TileEntitySuperJukebox.class, "TileEntitySuperJukebox");
		GameRegistry.registerTileEntity(TileEntitySuperLight.class, "TileEntitySuperLight");
		GameRegistry.registerTileEntity(TileEntityPestKiller.class, "TileEntityPestKiller");
		GameRegistry.registerTileEntity(TileEntityRoundRobinator.class, "TileEntityRoundRobinator");
		GameRegistry.registerTileEntity(TileEntityEggBox.class, "TileEggBox");
		GameRegistry.registerTileEntity(TileEntityFeedingTrough.class, "TileEntityFeedingTrough");

		if (Meta_GT_Proxy.sDoesVolumetricFlaskExist) {
			GameRegistry.registerTileEntity(TileEntityVolumetricFlaskSetter.class, "TileEntityVolumetricFlaskSetter");
		}

		//Mod TEs
		if (LoadedMods.Thaumcraft){

		}
		blacklistTilesFromAcceleration();
	}

	private static void blacklistTilesFromAcceleration() {
		Meta_GT_Proxy.setTileEntityClassAsBlacklistedInWorldAccelerator("gtPlusPlus.core.tileentities.general.TileEntityFishTrap");
		Meta_GT_Proxy.setTileEntityClassAsBlacklistedInWorldAccelerator("gtPlusPlus.core.tileentities.general.TileEntityDecayablesChest");
		Meta_GT_Proxy.setTileEntityClassAsBlacklistedInWorldAccelerator("gtPlusPlus.core.tileentities.general.TileEggBox");
	}

}
