package gtPlusPlus.xmod.gregtech.common.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import gregtech.api.enums.TAE;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Material_Casings;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.api.objects.GTPP_CopiedBlockTexture;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class GregtechMetaCasingBlocks6
extends GregtechMetaCasingBlocksAbstract {

	public static boolean mConnectedMachineTextures = false;
	CasingTextureHandler6 TextureHandler = new CasingTextureHandler6();

	public static class GregtechMetaCasingItemBlocks6 extends GregtechMetaCasingItems {

		public GregtechMetaCasingItemBlocks6(Block par1) {
			super(par1);
		}

		@Override
		public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
			super.addInformation(aStack, aPlayer, aList, aF3_H);
		}
	}

	public GregtechMetaCasingBlocks6() {
		super(GregtechMetaCasingItemBlocks6.class, "gtplusplus.blockcasings.6", GT_Material_Casings.INSTANCE);
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".0.name", "Fusion Machine Casing MK III");
		TAE.registerTexture(3, 4, new GTPP_CopiedBlockTexture(this, 6, 0));
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".1.name", "Advanced Fusion Coil I");
		TAE.registerTexture(3, 5, new GTPP_CopiedBlockTexture(this, 6, 1));
		GT_LanguageManager.addStringLocalization(this.getUnlocalizedName() + ".2.name", "Unnamed"); // Can Use, don't change texture (Used for Fusion MK4)
		TAE.registerTexture(3, 6, new GTPP_CopiedBlockTexture(this, 6, 2));
		GregtechItemList.Casing_Fusion_External.set(new ItemStack(this, 1, 0));
		GregtechItemList.Casing_Fusion_Internal.set(new ItemStack(this, 1, 1));
	}

	@Override
	public IIcon getIcon(final int aSide, final int aMeta) {
		return CasingTextureHandler6.getIcon(aSide, aMeta);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(final IBlockAccess aWorld, final int xCoord, final int yCoord, final int zCoord,
			final int aSide) {
		final Block thisBlock = aWorld.getBlock(xCoord, yCoord, zCoord);
		final int tMeta = aWorld.getBlockMetadata(xCoord, yCoord, zCoord);
		if ((tMeta != 0)|| !GregtechMetaCasingBlocks6.mConnectedMachineTextures) {
			return getIcon(aSide, tMeta);
		}
		final int tStartIndex = 0;
		if (tMeta == 0) {
			final boolean[] tConnectedSides = {
					aWorld.getBlock(xCoord, yCoord - 1, zCoord) == thisBlock
							&& aWorld.getBlockMetadata(xCoord, yCoord - 1, zCoord) == tMeta,
							aWorld.getBlock(xCoord, yCoord + 1, zCoord) == thisBlock
							&& aWorld.getBlockMetadata(xCoord, yCoord + 1, zCoord) == tMeta,
							aWorld.getBlock(xCoord + 1, yCoord, zCoord) == thisBlock
							&& aWorld.getBlockMetadata(xCoord + 1, yCoord, zCoord) == tMeta,
							aWorld.getBlock(xCoord, yCoord, zCoord + 1) == thisBlock
							&& aWorld.getBlockMetadata(xCoord, yCoord, zCoord + 1) == tMeta,
							aWorld.getBlock(xCoord - 1, yCoord, zCoord) == thisBlock
							&& aWorld.getBlockMetadata(xCoord - 1, yCoord, zCoord) == tMeta,
							aWorld.getBlock(xCoord, yCoord, zCoord - 1) == thisBlock
							&& aWorld.getBlockMetadata(xCoord, yCoord, zCoord - 1) == tMeta};
			switch (aSide) {
			case 0 : {
				if (tConnectedSides[0]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (tConnectedSides[4] && tConnectedSides[5] && tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 6].getIcon();
				}
				if (!tConnectedSides[4] && tConnectedSides[5] && tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 2].getIcon();
				}
				if (tConnectedSides[4] && !tConnectedSides[5] && tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 3].getIcon();
				}
				if (tConnectedSides[4] && tConnectedSides[5] && !tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 4].getIcon();
				}
				if (tConnectedSides[4] && tConnectedSides[5] && tConnectedSides[2] && !tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 5].getIcon();
				}
				if (!tConnectedSides[4] && !tConnectedSides[5] && tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 8].getIcon();
				}
				if (tConnectedSides[4] && !tConnectedSides[5] && !tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 9].getIcon();
				}
				if (tConnectedSides[4] && tConnectedSides[5] && !tConnectedSides[2] && !tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 10].getIcon();
				}
				if (!tConnectedSides[4] && tConnectedSides[5] && tConnectedSides[2] && !tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 11].getIcon();
				}
				if (!tConnectedSides[4] && !tConnectedSides[5] && !tConnectedSides[2] && !tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (!tConnectedSides[4] && !tConnectedSides[2]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 1].getIcon();
				}
				if (!tConnectedSides[5] && !tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 0].getIcon();
				}
			}
			case 1 : {
				if (tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (tConnectedSides[4] && tConnectedSides[5] && tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 6].getIcon();
				}
				if (!tConnectedSides[4] && tConnectedSides[5] && tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 2].getIcon();
				}
				if (tConnectedSides[4] && !tConnectedSides[5] && tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 3].getIcon();
				}
				if (tConnectedSides[4] && tConnectedSides[5] && !tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 4].getIcon();
				}
				if (tConnectedSides[4] && tConnectedSides[5] && tConnectedSides[2] && !tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 5].getIcon();
				}
				if (!tConnectedSides[4] && !tConnectedSides[5] && tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 8].getIcon();
				}
				if (tConnectedSides[4] && !tConnectedSides[5] && !tConnectedSides[2] && tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 9].getIcon();
				}
				if (tConnectedSides[4] && tConnectedSides[5] && !tConnectedSides[2] && !tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 10].getIcon();
				}
				if (!tConnectedSides[4] && tConnectedSides[5] && tConnectedSides[2] && !tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 11].getIcon();
				}
				if (!tConnectedSides[4] && !tConnectedSides[5] && !tConnectedSides[2] && !tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (!tConnectedSides[2] && !tConnectedSides[4]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 1].getIcon();
				}
				if (!tConnectedSides[3] && !tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 0].getIcon();
				}
			}
			case 2 : {
				if (tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (tConnectedSides[2] && tConnectedSides[0] && tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 6].getIcon();
				}
				if (!tConnectedSides[2] && tConnectedSides[0] && tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 2].getIcon();
				}
				if (tConnectedSides[2] && !tConnectedSides[0] && tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 5].getIcon();
				}
				if (tConnectedSides[2] && tConnectedSides[0] && !tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 4].getIcon();
				}
				if (tConnectedSides[2] && tConnectedSides[0] && tConnectedSides[4] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 3].getIcon();
				}
				if (!tConnectedSides[2] && !tConnectedSides[0] && tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 11].getIcon();
				}
				if (tConnectedSides[2] && !tConnectedSides[0] && !tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 10].getIcon();
				}
				if (tConnectedSides[2] && tConnectedSides[0] && !tConnectedSides[4] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 9].getIcon();
				}
				if (!tConnectedSides[2] && tConnectedSides[0] && tConnectedSides[4] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 8].getIcon();
				}
				if (!tConnectedSides[2] && !tConnectedSides[0] && !tConnectedSides[4] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (!tConnectedSides[2] && !tConnectedSides[4]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 1].getIcon();
				}
				if (!tConnectedSides[0] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 0].getIcon();
				}
			}
			case 3 : {
				if (tConnectedSides[3]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (tConnectedSides[2] && tConnectedSides[0] && tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 6].getIcon();
				}
				if (!tConnectedSides[2] && tConnectedSides[0] && tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 4].getIcon();
				}
				if (tConnectedSides[2] && !tConnectedSides[0] && tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 5].getIcon();
				}
				if (tConnectedSides[2] && tConnectedSides[0] && !tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 2].getIcon();
				}
				if (tConnectedSides[2] && tConnectedSides[0] && tConnectedSides[4] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 3].getIcon();
				}
				if (!tConnectedSides[2] && !tConnectedSides[0] && tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 10].getIcon();
				}
				if (tConnectedSides[2] && !tConnectedSides[0] && !tConnectedSides[4] && tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 11].getIcon();
				}
				if (tConnectedSides[2] && tConnectedSides[0] && !tConnectedSides[4] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 8].getIcon();
				}
				if (!tConnectedSides[2] && tConnectedSides[0] && tConnectedSides[4] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 9].getIcon();
				}
				if (!tConnectedSides[2] && !tConnectedSides[0] && !tConnectedSides[4] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (!tConnectedSides[2] && !tConnectedSides[4]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 1].getIcon();
				}
				if (!tConnectedSides[0] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 0].getIcon();
				}
			}
			case 4 : {
				if (tConnectedSides[4]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (tConnectedSides[0] && tConnectedSides[3] && tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 6].getIcon();
				}
				if (!tConnectedSides[0] && tConnectedSides[3] && tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 5].getIcon();
				}
				if (tConnectedSides[0] && !tConnectedSides[3] && tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 4].getIcon();
				}
				if (tConnectedSides[0] && tConnectedSides[3] && !tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 3].getIcon();
				}
				if (tConnectedSides[0] && tConnectedSides[3] && tConnectedSides[1] && !tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 2].getIcon();
				}
				if (!tConnectedSides[0] && !tConnectedSides[3] && tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 10].getIcon();
				}
				if (tConnectedSides[0] && !tConnectedSides[3] && !tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 9].getIcon();
				}
				if (tConnectedSides[0] && tConnectedSides[3] && !tConnectedSides[1] && !tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 8].getIcon();
				}
				if (!tConnectedSides[0] && tConnectedSides[3] && tConnectedSides[1] && !tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 11].getIcon();
				}
				if (!tConnectedSides[0] && !tConnectedSides[3] && !tConnectedSides[1] && !tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (!tConnectedSides[0] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 0].getIcon();
				}
				if (!tConnectedSides[3] && !tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 1].getIcon();
				}
			}
			case 5 : {
				if (tConnectedSides[2]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (tConnectedSides[0] && tConnectedSides[3] && tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 6].getIcon();
				}
				if (!tConnectedSides[0] && tConnectedSides[3] && tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 5].getIcon();
				}
				if (tConnectedSides[0] && !tConnectedSides[3] && tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 2].getIcon();
				}
				if (tConnectedSides[0] && tConnectedSides[3] && !tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 3].getIcon();
				}
				if (tConnectedSides[0] && tConnectedSides[3] && tConnectedSides[1] && !tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 4].getIcon();
				}
				if (!tConnectedSides[0] && !tConnectedSides[3] && tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 11].getIcon();
				}
				if (tConnectedSides[0] && !tConnectedSides[3] && !tConnectedSides[1] && tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 8].getIcon();
				}
				if (tConnectedSides[0] && tConnectedSides[3] && !tConnectedSides[1] && !tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 9].getIcon();
				}
				if (!tConnectedSides[0] && tConnectedSides[3] && tConnectedSides[1] && !tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 10].getIcon();
				}
				if (!tConnectedSides[0] && !tConnectedSides[3] && !tConnectedSides[1] && !tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
				}
				if (!tConnectedSides[0] && !tConnectedSides[1]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 0].getIcon();
				}
				if (!tConnectedSides[3] && !tConnectedSides[5]) {
					return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 1].getIcon();
				}
				break;
			}
			}
			return TexturesGtBlock.CONNECTED_FUSION_HULLS_MK4[tStartIndex + 7].getIcon();
		}
		return CasingTextureHandler6.getIcon(aSide, tMeta);
	}
}
