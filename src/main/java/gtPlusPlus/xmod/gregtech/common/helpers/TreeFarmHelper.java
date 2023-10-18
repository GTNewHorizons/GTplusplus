package gtPlusPlus.xmod.gregtech.common.helpers;

import gregtech.common.items.GT_MetaGenerated_Tool_01;
import net.minecraft.item.ItemStack;

public class TreeFarmHelper {

    public static boolean isValidForGUI(final ItemStack aStack) {
        return isCorrectMachinePart(aStack) != SAWTOOL.NONE;
    }

    public static SAWTOOL isCorrectMachinePart(final ItemStack aStack) {
        if (aStack != null && aStack.getItem() instanceof GT_MetaGenerated_Tool_01) {
            switch (aStack.getItemDamage()) {
                case GT_MetaGenerated_Tool_01.SAW -> {
                    return SAWTOOL.SAW;
                }
                case GT_MetaGenerated_Tool_01.BUZZSAW_LV, GT_MetaGenerated_Tool_01.BUZZSAW_MV, GT_MetaGenerated_Tool_01.BUZZSAW_HV -> {
                    return SAWTOOL.BUZZSAW;
                }
                case GT_MetaGenerated_Tool_01.CHAINSAW_LV, GT_MetaGenerated_Tool_01.CHAINSAW_MV, GT_MetaGenerated_Tool_01.CHAINSAW_HV -> {
                    return SAWTOOL.CHAINSAW;
                }
            }
        }
        return SAWTOOL.NONE;
    }

    public enum SAWTOOL {
        NONE,
        SAW,
        BUZZSAW,
        CHAINSAW
    }

}
