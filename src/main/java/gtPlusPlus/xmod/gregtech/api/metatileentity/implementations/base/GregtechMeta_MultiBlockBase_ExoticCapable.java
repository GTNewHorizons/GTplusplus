package gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base;

import java.util.ArrayList;
import java.util.List;

import gtPlusPlus.api.objects.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyTunnel;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;

import static gregtech.api.util.GT_Utility.filterValidMTEs;

// how to use this class
// extend this instead of GregtechMeta_MultiBlockBase
// change Energy to Energy.or(ExoticEnergy) in structure definition
// make sure checkHatch is called in checkMachine

public abstract class GregtechMeta_MultiBlockBase_ExoticCapable<T extends GregtechMeta_MultiBlockBase<T>>
        extends GregtechMeta_MultiBlockBase<T> {

    private boolean multiAmp = false;

    public GregtechMeta_MultiBlockBase_ExoticCapable(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GregtechMeta_MultiBlockBase_ExoticCapable(String aName) {
        super(aName);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide() && aTick % 20 == 0 && !multiAmp) {
            ItemStack aGuiStack = this.getControllerSlot();
            if (GregtechItemList.MultiAmp_Upgrade_Chip.isStackEqual(aGuiStack, false, true)) {
                multiAmp = true;
                mInventory[1] = ItemUtils.depleteStack(aGuiStack);
                markDirty();
                // schedule a structure check
                mUpdated = true;
            }
        }
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, ForgeDirection side,
            float aX, float aY, float aZ) {
        if (!multiAmp && !aPlayer.isSneaking()) {
            ItemStack heldItem = aPlayer.getHeldItem();
            if (GregtechItemList.MultiAmp_Upgrade_Chip.isStackEqual(heldItem, false, true)) {
                multiAmp = true;
                aPlayer.setCurrentItemOrArmor(0, ItemUtils.depleteStack(heldItem));
                if (getBaseMetaTileEntity().isServerSide()) {
                    markDirty();
                    aPlayer.inventory.markDirty();
                    // schedule a structure check
                    mUpdated = true;
                }
                return true;
            }
        }
        return super.onRightclick(aBaseMetaTileEntity,aPlayer,side,aX,aY,aZ);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("mExotic", multiAmp);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        if (!aNBT.hasKey("mExotic")) multiAmp = false;
        else multiAmp = aNBT.getBoolean("mExotic");
    }

    @Override
    public void initDefaultModes(NBTTagCompound aNBT) {
        super.initDefaultModes(aNBT);
        if (aNBT == null || !aNBT.hasKey("mExotic")) {
            multiAmp = false;
        } else {
            multiAmp = aNBT.getBoolean("mExotic");
        }
    }

    @Override
    public void setItemNBT(NBTTagCompound aNBT) {
        super.setItemNBT(aNBT);
        aNBT.setBoolean("mExotic", multiAmp);
    }

    @Override
    public void addAdditionalTooltipInformation(ItemStack stack, List<String> tooltip) {
        super.addAdditionalTooltipInformation(stack, tooltip);
        NBTTagCompound aNBT = stack.getTagCompound();
        int tier;
        if (aNBT == null || !aNBT.hasKey("mExotic") || !aNBT.getBoolean("mExotic")) {
            return;
        } else {
            tooltip.add(StatCollector.translateToLocal("tooltip.multi_amp_enabled"));
        }
    }

    @Override
    public List<GT_MetaTileEntity_Hatch> getExoticAndNormalEnergyHatchList() {
        List<GT_MetaTileEntity_Hatch> tHatches = new ArrayList<>();
        tHatches.addAll(mExoticEnergyHatches);
        tHatches.addAll(mEnergyHatches);
        return tHatches;
    }

    private boolean checkExotics() {
        for (GT_MetaTileEntity_Hatch tHatch : getExoticAndNormalEnergyHatchList()) {
            if (tHatch instanceof GT_MetaTileEntity_Hatch_EnergyTunnel
                    || (!multiAmp && tHatch instanceof GT_MetaTileEntity_Hatch_EnergyMulti)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkHatch() {
        return checkExotics() && super.checkHatch();
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        logic.setAvailableVoltage(getMaxInputEu());
        logic.setAvailableAmperage(1);
    }

    @Override
    public String[] getInfoData() {System.out.println("MCMEME");
        for(GT_MetaTileEntity_Hatch tHatch : this.getExoticEnergyHatches()){
            System.out.println("FUGGO");
        }
        return super.getInfoData();
    }
}
