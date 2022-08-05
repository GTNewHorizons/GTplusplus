package gtPlusPlus.xmod.gregtech.common.covers;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import net.minecraft.item.ItemStack;

public class GTPP_Tiered_Upgrade extends GT_CoverBehavior {
    public final byte mTier;

    public GTPP_Tiered_Upgrade(byte mTier) {
        this.mTier = mTier;
    }

    @Override
    public boolean isCoverPlaceable(byte aSide, ItemStack aStack, ICoverable aTileEntity) {
        IMetaTileEntity iGregTechTileEntityOffset = aTileEntity.getIGregTechTileEntityOffset(0, 0, 0).getMetaTileEntity();
        if (iGregTechTileEntityOffset instanceof GregtechMeta_MultiBlockBase) {
            GregtechMeta_MultiBlockBase multi = (GregtechMeta_MultiBlockBase) iGregTechTileEntityOffset;
            return multi.isTieredMachine() && !multi.mTierCover;
        }
        return false;
    }

    @Override
    public void placeCover(byte aSide, ItemStack aCover, ICoverable aTileEntity) {
        IMetaTileEntity iGregTechTileEntityOffset = aTileEntity.getIGregTechTileEntityOffset(0, 0, 0).getMetaTileEntity();
        if (iGregTechTileEntityOffset instanceof GregtechMeta_MultiBlockBase) {
            GregtechMeta_MultiBlockBase multi = (GregtechMeta_MultiBlockBase) iGregTechTileEntityOffset;
            multi.mTierCover = true;
            multi.mMaxTier = mTier;
        }
        super.placeCover(aSide, aCover, aTileEntity);
    }

    @Override
    public boolean onCoverRemoval(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, boolean aForced) {
        IMetaTileEntity iGregTechTileEntityOffset = aTileEntity.getIGregTechTileEntityOffset(0, 0, 0).getMetaTileEntity();
        if (iGregTechTileEntityOffset instanceof GregtechMeta_MultiBlockBase) {
            GregtechMeta_MultiBlockBase multi = (GregtechMeta_MultiBlockBase) iGregTechTileEntityOffset;
            multi.mTierCover = false;
            multi.mMaxTier = 0;
        }
        return true;
    }
}
