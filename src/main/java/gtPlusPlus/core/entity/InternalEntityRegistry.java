package gtPlusPlus.core.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import gtPlusPlus.GTplusplus;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.entity.monster.EntityStaballoyConstruct;
import gtPlusPlus.core.entity.projectile.EntityLightningAttack;
import gtPlusPlus.core.item.general.spawn.ItemCustomSpawnEgg;
import gtPlusPlus.core.util.Utils;

public class InternalEntityRegistry {

    static int mEntityID = 0;

    public static void registerEntities() {
        Logger.INFO("Registering GT++ Entities.");

        EntityRegistry.registerModEntity(
                EntityPrimedMiningExplosive.class,
                "MiningCharge",
                mEntityID++,
                GTplusplus.instance,
                64,
                20,
                true);

        EntityRegistry.registerModEntity(
                EntityStaballoyConstruct.class,
                "constructStaballoy",
                mEntityID++,
                GTplusplus.instance,
                64,
                20,
                true);
        ItemCustomSpawnEgg.registerEntityForSpawnEgg(
                0,
                "constructStaballoy",
                Utils.rgbtoHexValue(20, 200, 20),
                Utils.rgbtoHexValue(20, 20, 20));

        EntityRegistry.registerModEntity(
                EntityLightningAttack.class,
                "EntityLightningAttack",
                mEntityID++,
                GTplusplus.instance,
                64,
                20,
                true);

    }
}
