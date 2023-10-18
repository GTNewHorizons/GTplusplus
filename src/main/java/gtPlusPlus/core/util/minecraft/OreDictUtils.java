package gtPlusPlus.core.util.minecraft;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class OreDictUtils {

    public static boolean containsValidEntries(String aOreName) {
        boolean a = OreDictionary.doesOreNameExist(aOreName);
        List<ItemStack> b = OreDictionary.getOres(aOreName, false);

        if (!a) {
            return false;
        } else {
            if (b == null || b.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
