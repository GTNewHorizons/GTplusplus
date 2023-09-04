package gtPlusPlus.xmod.bartcrops.crops;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import gtPlusPlus.core.item.ModItems;
import gtPlusPlus.core.util.math.MathUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.preloader.CORE_Preloader;
import gtPlusPlus.xmod.bartcrops.abstracts.BaseAestheticCrop;
import ic2.api.crops.ICropTile;

public class Crop_Hemp extends BaseAestheticCrop {

    @Override
    public int tier() {
        return 2;
    }

    @Override
    public String name() {
        return "Hemp";
    }

    @Override
    public int growthDuration(ICropTile crop) {
        return 550;
    }

    @Override
    public String[] attributes() {
        return new String[] { "Green", "Soil", "Orange" };
    }

    @Override
    public ItemStack getGain(ICropTile crop) {
        return new ItemStack(Items.string, MathUtils.randInt(1, 3), 0);
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack(Items.string, 1, 0);
    }
}
