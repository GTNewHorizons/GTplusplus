package gtPlusPlus.core.util.minecraft;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import baubles.api.BaubleType;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.api.objects.data.Pair;

public class ModularArmourUtils {

    public enum BT {

        TYPE_AMULET(BaubleType.AMULET, 0),
        TYPE_RING(BaubleType.RING, 1),
        TYPE_BELT(BaubleType.BELT, 2);

        private final BaubleType mType;
        private final int mID;

        BT(final BaubleType tType, int tID) {
            this.mType = tType;
            this.mID = tID;
        }

        public BaubleType getType() {
            return this.mType;
        }

        public BT getThis() {
            return this;
        }

        public int getID() {
            return this.mID;
        }

    }

}
