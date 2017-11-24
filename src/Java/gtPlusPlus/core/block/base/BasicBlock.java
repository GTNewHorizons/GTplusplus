package gtPlusPlus.core.block.base;

import gtPlusPlus.core.creative.AddToCreativeTab;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.util.Utils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BasicBlock extends BlockContainer {

	public BasicBlock(final String unlocalizedName, final Material material) {
		this(unlocalizedName, material, 2);
	}

	public BasicBlock(final String unlocalizedName, final Material material, final int harvestLevel) {
		super(material);
		this.setBlockName(Utils.sanitizeString(unlocalizedName));
		this.setBlockTextureName(CORE.MODID + ":" + unlocalizedName);
		this.setCreativeTab(AddToCreativeTab.tabBlock);
		this.setHardness(2.0F);
		this.setResistance(6.0F);
		this.setLightLevel(0.0F);
		this.setHarvestLevel("pickaxe", harvestLevel);
		this.setStepSound(soundTypeMetal);
	}


	public static enum BlockTypes {
		STANDARD("blockBlock", "pickaxe", soundTypeMetal),
		FRAME("blockFrameGt", "wrench", soundTypeMetal),
		ORE("blockStone", "pickaxe", soundTypeStone);

		private String TEXTURE_NAME;
		private String HARVEST_TOOL;
		private SoundType soundOfBlock;
		private BlockTypes (final String textureName, final String harvestTool, final SoundType blockSound)
		{
			this.TEXTURE_NAME = textureName;
			this.HARVEST_TOOL = harvestTool;
			this.soundOfBlock = blockSound;
		}

		public String getTexture() {
			return this.TEXTURE_NAME;
		}

		public String getHarvestTool(){
			return this.HARVEST_TOOL;
		}

		public SoundType getBlockSoundType(){
			return this.soundOfBlock;
		}

	}


	@Override
	public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
		return null;
	}

	@Override
	public boolean canCreatureSpawn(final EnumCreatureType type, final IBlockAccess world, final int x, final int y, final int z) {
		return false;
	}

}
