package gtPlusPlus.nei;

import codechicken.nei.api.IStackStringifyHandler;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.nbt.NBTTagCompound;
import codechicken.nei.NEIClientConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import gregtech.common.items.GT_FluidDisplayItem;
import gtPlusPlus.core.util.minecraft.NBTUtils;

public class FluidStackStringifyHandler implements IStackStringifyHandler
{

    public NBTTagCompound convertItemStackToNBT(ItemStack[] stacks)
    {

        if (!(stacks[0].getItem() instanceof GT_FluidDisplayItem)) {
            return null;
        }

        ItemStack stack = stacks[0];

        String gtFluidId = FluidRegistry.getFluid(stack.getItemDamage()).getName();

        NBTTagCompound nbTag = new NBTTagCompound();
        nbTag.setString("gtFluidId", gtFluidId);

        return nbTag;
    }

    public ItemStack convertNBTToItemStack(NBTTagCompound nbtTag)
    {

        if (!nbtTag.hasKey("gtFluidId")) {
            return null;
        }

        String gtFluidId = nbtTag.getString("gtFluidId");

        return gregtech.api.util.GT_Utility.getFluidDisplayStack(FluidRegistry.getFluidStack(gtFluidId, 0), true);
    }

    public ItemStack normalize(ItemStack item)
    {

        if (!(item.getItem() instanceof GT_FluidDisplayItem)) {
            return null;
        }

        return convertNBTToItemStack(convertItemStackToNBT(new ItemStack[]{item}));
    }

}