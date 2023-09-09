package gtPlusPlus.xmod.thermalfoundation.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

import cofh.core.item.ItemBase;
import cofh.core.item.ItemBucket;
import cofh.core.util.energy.FurnaceFuelHandler;
import cofh.core.util.fluid.BucketHandler;
import gtPlusPlus.core.creative.AddToCreativeTab;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.reflect.ReflectionUtils;
import gtPlusPlus.xmod.thermalfoundation.block.TF_Blocks;
import gtPlusPlus.xmod.thermalfoundation.fluid.TF_Fluids;

public class TF_Items {

    public static ItemBase itemMaterial;
    public static ItemStack rodBlizz;
    public static ItemBucket itemBucket;
    public static ItemStack bucketPyrotheum;
    public static ItemStack bucketCryotheum;
    public static ItemStack bucketEnder;

    public static ItemStack itemRodBlizz;

    public static void preInit() {

        itemBucket = (ItemBucket) new ItemBucket("MiscUtils").setUnlocalizedName("bucket")
                .setCreativeTab(AddToCreativeTab.tabMisc);
        itemMaterial = (ItemBase) new ItemBase("MiscUtils").setUnlocalizedName("material")
                .setCreativeTab(AddToCreativeTab.tabMisc);

        bucketPyrotheum = itemBucket.addOreDictItem(1, "bucketPyrotheum");
        bucketCryotheum = itemBucket.addOreDictItem(2, "bucketCryotheum");
        bucketEnder = itemBucket.addOreDictItem(3, "bucketEnder", 1);
        rodBlizz = itemMaterial.addOreDictItem(1, "rodBlizz");

        ItemUtils.addItemToOreDictionary(rodBlizz, "stickBlizz");

        itemRodBlizz = ItemUtils.simpleMetaStack(itemMaterial, 1, 1);
    }

    public static void init() {

        BucketHandler.registerBucket(TF_Blocks.blockFluidPyrotheum, 0, bucketPyrotheum);
        BucketHandler.registerBucket(TF_Blocks.blockFluidCryotheum, 0, bucketCryotheum);
        BucketHandler.registerBucket(TF_Blocks.blockFluidEnder, 0, bucketEnder);
        FluidContainerRegistry
                .registerFluidContainer(TF_Fluids.fluidPyrotheum, bucketPyrotheum, FluidContainerRegistry.EMPTY_BUCKET);
        FluidContainerRegistry
                .registerFluidContainer(TF_Fluids.fluidCryotheum, bucketCryotheum, FluidContainerRegistry.EMPTY_BUCKET);
        FluidContainerRegistry
                .registerFluidContainer(TF_Fluids.fluidEnder, bucketEnder, FluidContainerRegistry.EMPTY_BUCKET);
    }

    public static void postInit() {}
}
