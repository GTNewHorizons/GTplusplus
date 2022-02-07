package gtPlusPlus.xmod.gregtech.common.blocks.textures;

import gtPlusPlus.xmod.gregtech.common.blocks.GregtechMetaCasingBlocks6;
import net.minecraft.util.IIcon;

public class CasingTextureHandler6 {

	public static  IIcon getIcon(final int aSide, final int aMeta) { //Texture ID's. case 0 == ID[57]
		if ((aMeta >= 0) && (aMeta < 16)) {
			switch (aMeta) {
			case 0:
				return TexturesGtBlock.TEXTURE_CASING_FUSION_COIL_II.getIcon();
			case 1:
				return TexturesGtBlock.TEXTURE_CASING_FUSION_COIL_II_INNER.getIcon();
			case 2:
				return TexturesGtBlock.TEXTURE_CASING_FUSION_CASING_ULTRA.getIcon();
			default:
				return TexturesGtBlock._PlaceHolder.getIcon();

			}
		}
		return TexturesGtBlock._PlaceHolder.getIcon();
	}

	static {
		GregtechMetaCasingBlocks6.mConnectedMachineTextures = true;
	}

}