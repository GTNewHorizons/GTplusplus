package gtPlusPlus.core.fluids;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.util.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidBase extends BlockFluidClassic {

    @SideOnly(Side.CLIENT)
    protected static IIcon stillIcon;

    @SideOnly(Side.CLIENT)
    protected static IIcon flowingIcon;

    protected final short[] mRGB;

    public BlockFluidBase(Fluid fluid, short[] aRGB) {
        this(fluid, Material.water, aRGB);
    }

    public BlockFluidBase(Fluid fluid, Material material, short[] aRGB) {
        super(fluid, material);
        mRGB = aRGB;
        this.setHardness(100.0F);
        this.setLightOpacity(3);
        this.disableStats();
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
        if (!(ent instanceof EntityLivingBase)) {}
        /*EntityLivingBase player = (EntityLivingBase) ent;
        int pot = world.rand.nextInt(Potion.potionTypes.length);
        Potion.potionTypes[pot].performEffect(player, 40);*/
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? stillIcon : flowingIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        if (stillIcon == null) {
            stillIcon = register.registerIcon(CORE.MODID + ":" + "fluids/fluid.fluid.autogenerated.still");
        }
        if (flowingIcon == null) {
            flowingIcon = register.registerIcon(CORE.MODID + ":" + "fluids/fluid.fluid.autogenerated.flowing");
        }
    }

    @Override
    public int getBlockColor() {
        if (mRGB != null && mRGB.length >= 3) {
            return Utils.rgbtoHexValue(mRGB[0], mRGB[1], mRGB[2]);
        }
        return super.getBlockColor();
    }

    @Override
    public int getRenderColor(int aMeta) {
        if (mRGB != null && mRGB.length >= 3) {
            return Utils.rgbtoHexValue(mRGB[0], mRGB[1], mRGB[2]);
        }
        return super.getRenderColor(aMeta);
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        // TODO Auto-generated method stub
        return super.recolourBlock(world, x, y, z, side, colour);
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
            return false;
        }
        return super.canDisplace(world, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
            return false;
        }
        return super.displaceIfPossible(world, x, y, z);
    }
}
