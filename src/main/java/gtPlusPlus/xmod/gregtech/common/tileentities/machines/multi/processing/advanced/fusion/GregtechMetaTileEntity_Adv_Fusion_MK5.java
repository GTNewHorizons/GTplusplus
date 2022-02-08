package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing.advanced.fusion;

import gregtech.api.enums.*;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock.CustomIcon;
import net.minecraft.block.Block;

public class GregtechMetaTileEntity_Adv_Fusion_MK5 extends GregtechMetaTileEntity_Adv_Fusion_Base {

	public GregtechMetaTileEntity_Adv_Fusion_MK5(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional, 10);
	}

	public GregtechMetaTileEntity_Adv_Fusion_MK5(String aName) {
		super(aName);
	}

	@Override
	public int tier() {
		return 10;
	}

	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GregtechMetaTileEntity_Adv_Fusion_MK5(this.mName);
	}

	@Override
	public Block getCasing() {
		return getFusionCoil();
	}

	@Override
	public int getCasingMeta() {
		return 12;
	}

	@Override
	public Block getFusionCoil() {
		return ModBlocks.blockCasings3Misc;
	}

	@Override
	public int getFusionCoilMeta() {
		return 13;
	}

	@Override
	public int getMaxParallelRecipes() {
		return 8;
	}

	@Override
	public int getEuDiscountForParallelism() {
		return 0;
	}

	@Override
	public String getMinimumHatchTier() {
		return GT_Values.VN[10];
	}

	@Override
	public String getCasingName() {
		return "Fusion Machine Casings MK IV";
	}

	@Override
	public String getCoilName() {
		return "Advanced Fusion Coils II";
	}

	@Override
	public String getTooltipText() {
		return "HARNESSING THE POWER OF A NEUTRON STAR";
	}

	@Override
	public int getCasingOverlayActive() {
		return TAE.getIndexFromPage(3, 6);
	}

	@Override
	public int getCasingOverlayInactive() {
		return 53;
	}

	@Override
	public CustomIcon getFusionCasingTexture() {
		return TexturesGtBlock.TEXTURE_CASING_FUSION_CASING_HYPER;
	}

	@Override
	public int getFusionTier() {
		return 5;
	}

	@Override
	public long getEuPerTickInPerHatch() {
		return GT_Values.V[10];
	}

	@Override
	public long getEuStoragePerHatch() {
		return 640000000;
	}

}
