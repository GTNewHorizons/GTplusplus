package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing.advanced.fusion;

import gregtech.api.enums.*;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock.CustomIcon;
import net.minecraft.block.Block;

public class GregtechMetaTileEntity_Adv_Fusion_MK4 extends GregtechMetaTileEntity_Adv_Fusion_Base {

	public GregtechMetaTileEntity_Adv_Fusion_MK4(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional, 9);
	}

	public GregtechMetaTileEntity_Adv_Fusion_MK4(String aName) {
		super(aName);
	}

	@Override
	public int getMinimumHatchTier() {
		return 9;
	}

	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GregtechMetaTileEntity_Adv_Fusion_MK4(this.mName);
	}

	@Override
	public Block getCasing() {
		return getFusionCoil();
	}

	@Override
	public int getCasingMeta() {
		return 0;
	}

	@Override
	public Block getFusionCoil() {
		return ModBlocks.blockCasings6Misc;
	}

	@Override
	public int getFusionCoilMeta() {
		return 1;
	}

	@Override
	public int getEuDiscountForParallelism() {
		return 0;
	}

	@Override
	public String getCasingName() {
		return "Fusion Machine Casings MK III";
	}

	@Override
	public String getCoilName() {
		return "Advanced Fusion Coils I";
	}

	@Override
	public String getTooltipText() {
		return "HARNESSING THE POWER OF A BLUE GIANT";
	}

	@Override
	public int getCasingOverlayActive() {
		return TAE.getIndexFromPage(3, 4);
	}

	@Override
	public int getCasingOverlayInactive() {
		return 54;
	}

	@Override
	public CustomIcon getFusionCasingTexture() {
		return TexturesGtBlock.TEXTURE_CASING_FUSION_COIL_II;
	}

	@Override
	public int getFusionTier() {
		return 4;
	}

	@Override
	public long getEuPerTickInPerHatch() {
		return GT_Values.V[8];
	}

	@Override
	public long getEuStoragePerHatch() {
		return 160000000;
	}

}
