package gtPlusPlus.xmod.gregtech.api.metatileentity.implementations;

import com.gtnewhorizons.modularui.api.screen.ModularWindow.Builder;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.Scrollable;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.extensions.ArrayExt;
import gtPlusPlus.core.lib.CORE;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_SuperBus_Output extends GT_MetaTileEntity_Hatch_OutputBus {

    public GT_MetaTileEntity_SuperBus_Output(int id, String name, String nameRegional, int tier) {
        super(id, name, nameRegional, tier, getSlots(tier));
    }

    public GT_MetaTileEntity_SuperBus_Output(String name, int tier, String[] description, ITexture[][][] textures) {
        super(name, tier, getSlots(tier), description, textures);
    }

    /**
     * Returns a factor of 16 based on tier.
     *
     * @param aTier The tier of this bus.
     * @return (1 + aTier) * 16
     */
    public static int getSlots(int aTier) {
        return (1 + aTier) * 16;
    }

    public boolean isValidSlot(int aIndex) {
        return true;
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_SuperBus_Output(
                this.mName, this.mTier, ArrayExt.of(this.mDescription), this.mTextures);
    }

    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
        if (aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.hasInventoryBeenModified()) {
            this.fillStacksIntoFirstSlots();
        }
        super.onPostTick(aBaseMetaTileEntity, aTimer);
    }

    public void updateSlots() {
        for (int i = 0; i < this.mInventory.length; ++i) {
            if (this.mInventory[i] != null && this.mInventory[i].stackSize <= 0) {
                this.mInventory[i] = null;
            }
        }
        this.fillStacksIntoFirstSlots();
    }

    protected void fillStacksIntoFirstSlots() {
        for (int i = 0; i < this.mInventory.length; ++i) {
            for (int j = i + 1; j < this.mInventory.length; ++j) {
                if (this.mInventory[j] != null
                        && (this.mInventory[i] == null
                                || GT_Utility.areStacksEqual(this.mInventory[i], this.mInventory[j]))) {
                    GT_Utility.moveStackFromSlotAToSlotB(
                            (IInventory) this.getBaseMetaTileEntity(),
                            (IInventory) this.getBaseMetaTileEntity(),
                            j,
                            i,
                            (byte) 64,
                            (byte) 1,
                            (byte) 64,
                            (byte) 1);
                }
            }
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
    }

    @Override
    public String[] getDescription() {
        String[] aDesc = new String[] {
            "Item Output for Multiblocks", "This bus has no GUI", "" + getSlots(this.mTier) + " Slots", CORE.GT_Tooltip
        };
        return aDesc;
    }

    @Override
    public int getMaxItemCount() {
        // TODO Auto-generated method stub
        return super.getMaxItemCount();
    }

    @Override
    public int getSizeInventory() {
        // TODO Auto-generated method stub
        return super.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int aIndex) {
        // TODO Auto-generated method stub
        return super.getStackInSlot(aIndex);
    }

    @Override
    public boolean canInsertItem(int aIndex, ItemStack aStack, int aSide) {
        // TODO Auto-generated method stub
        return super.canInsertItem(aIndex, aStack, aSide);
    }

    @Override
    public boolean canExtractItem(int aIndex, ItemStack aStack, int aSide) {
        // TODO Auto-generated method stub
        return super.canExtractItem(aIndex, aStack, aSide);
    }

    @Override
    public ItemStack[] getRealInventory() {
        // TODO Auto-generated method stub
        return super.getRealInventory();
    }

    @Override
    public void addUIWidgets(Builder builder, UIBuildContext buildContext) {
        final Scrollable scrollable = new Scrollable().setVerticalScroll();
        for (int rows = 0; rows * 4 < inventoryHandler.getSlots() - 1; rows++) {
            int columnsToMake = Math.min(inventoryHandler.getSlots() - rows * 4, 4);
            for (int column = 0; column < columnsToMake; column++) {
                scrollable.widget(new SlotWidget(inventoryHandler, rows * 4 + column)
                        .setPos(column * 18, rows * 18)
                        .setSize(18, 18));
            }
        }
        builder.widget(scrollable.setSize(18 * 4 + 4, 18 * 4).setPos(52, 7));
    }
}
