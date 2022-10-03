package gtPlusPlus.api.interfaces;

import gtPlusPlus.api.objects.minecraft.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IEntityCatcher {

    boolean hasEntity(ItemStack aStack);

    Entity getStoredEntity(World aWorld, ItemStack aStack);

    boolean setStoredEntity(World aWorld, ItemStack aStack, Entity aEntity);

    Class<? extends Entity> getStoredEntityClass(ItemStack aStack);

    boolean spawnStoredEntity(World aWorld, ItemStack aStack, BlockPos aPos);
}
