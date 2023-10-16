package gtPlusPlus.core.item.general;

import static gregtech.api.enums.Mods.GTPlusPlus;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Mods;
import gtPlusPlus.core.creative.AddToCreativeTab;
import gtPlusPlus.core.util.minecraft.ItemUtils;

@Optional.InterfaceList(
        value = { @Optional.Interface(iface = "baubles.api.IBauble", modid = Mods.Names.BAUBLES),
                @Optional.Interface(iface = "baubles.api.BaubleType", modid = Mods.Names.BAUBLES) })
public class ItemSlowBuildingRing extends Item implements IBauble {

    public ItemSlowBuildingRing() {
        this.setCreativeTab(AddToCreativeTab.tabMachines);
        String unlocalizedName = "SlowBuildingRing";
        this.setUnlocalizedName(unlocalizedName);
        this.setMaxStackSize(1);
        this.setTextureName(GTPlusPlus.ID + ":" + "itemSlowBuildersRing");
        ItemUtils.getSimpleStack(this);
        GameRegistry.registerItem(this, unlocalizedName);
    }

    @Override
    public void onUpdate(final ItemStack itemStack, final World worldObj, final Entity player, final int p_77663_4_,
            final boolean p_77663_5_) {
        if (worldObj.isRemote) {
            return;
        }
        super.onUpdate(itemStack, worldObj, player, p_77663_4_, p_77663_5_);
    }

    @Override
    public boolean showDurabilityBar(final ItemStack stack) {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer aPlayer, final List list, final boolean bool) {
        list.add("");
        list.add(StatCollector.translateToLocal("item.SlowBuildingRing.tooltip.0"));
        list.add(StatCollector.translateToLocal("item.SlowBuildingRing.tooltip.1"));
        list.add(StatCollector.translateToLocal("item.SlowBuildingRing.tooltip.2"));
        list.add("");
        super.addInformation(stack, aPlayer, list, bool);
    }

    @Override
    public boolean canEquip(final ItemStack arg0, final EntityLivingBase arg1) {
        return true;
    }

    @Override
    public boolean canUnequip(final ItemStack arg0, final EntityLivingBase arg1) {
        return true;
    }

    @Override
    public BaubleType getBaubleType(final ItemStack arg0) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
        if(entity instanceof EntityPlayer player) {

            // Continuously set the sprinting flag to false to ensure the player doesn't sprint
            player.setSprinting(false);
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

}
