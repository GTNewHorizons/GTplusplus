package gtPlusPlus.core.block.machine;

import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.*;
import gtPlusPlus.GTplusplus;
import gtPlusPlus.api.interfaces.ITileTooltip;
import gtPlusPlus.core.creative.AddToCreativeTab;
import gtPlusPlus.core.item.base.itemblock.ItemBlockBasicTile;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.tileentities.general.TileEntityFeedingTrough;
import gtPlusPlus.core.util.minecraft.InventoryUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

public class Machine_FeedingTrough extends BlockContainer implements ITileTooltip {

	@SideOnly(Side.CLIENT)
	private IIcon textureTop;
	@SideOnly(Side.CLIENT)
	private IIcon textureBottom;
	@SideOnly(Side.CLIENT)
	private IIcon textureFront;

	@SuppressWarnings("deprecation")
	public Machine_FeedingTrough() {
		super(Material.wood);
		this.setHardness(5f);
		this.setResistance(1f);
		this.setBlockName("blockAnimalFeeder");
		this.setCreativeTab(AddToCreativeTab.tabMachines);
		GameRegistry.registerBlock(this, ItemBlockBasicTile.class, "blockAnimalFeeder");
		LanguageRegistry.addName(this, "Feeding Trough");

	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
		return p_149691_1_ == 1 ? this.textureTop
				: (p_149691_1_ == 0 ? this.textureBottom
						: ((p_149691_1_ != 2) && (p_149691_1_ != 4) ? this.blockIcon : this.textureFront));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(CORE.MODID + ":" + "TileEntities/" + "bronze_side_cabinet");
		this.textureTop = p_149651_1_.registerIcon(CORE.MODID + ":" + "TileEntities/" + "bronze_top_crafting");
		this.textureBottom = p_149651_1_.registerIcon(CORE.MODID + ":" + "TileEntities/" + "bronze_side");
		this.textureFront = p_149651_1_.registerIcon(CORE.MODID + ":" + "TileEntities/" + "bronze_side_cabinet");
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player,
			final int side, final float lx, final float ly, final float lz) {

		if (world.isRemote) {
			return true;
		}

		final TileEntity te = world.getTileEntity(x, y, z);
		if ((te != null) && (te instanceof TileEntityFeedingTrough)) {
			player.openGui(GTplusplus.instance, 3, world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int p_149915_2_) {
		return new TileEntityFeedingTrough();
	}

	@Override
	public boolean canCreatureSpawn(final EnumCreatureType type, final IBlockAccess world, final int x, final int y,
			final int z) {
		return false;
	}

	@Override
	public void breakBlock(final World world, final int x, final int y, final int z, final Block block, final int number) {
		InventoryUtils.dropInventoryItems(world, x, y, z, block);
		super.breakBlock(world, x, y, z, block, number);
	}

	@Override
	public int getTooltipID() {
		return 9;
	}

}