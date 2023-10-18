package gtPlusPlus.xmod.bartworks;

import com.github.bartimaeusnek.bartworks.system.material.Werkstoff;
import com.github.bartimaeusnek.bartworks.system.material.WerkstoffLoader;
import gregtech.api.enums.OrePrefixes;
import net.minecraft.item.ItemStack;

public class BW_Utils {

    public static ItemStack getCorrespondingItemStack(OrePrefixes orePrefixes, short werkstoffID, int amount) {
        Werkstoff werkstoff = Werkstoff.werkstoffHashMap.get(werkstoffID);
        if (werkstoff == null) return null;
        return WerkstoffLoader.getCorrespondingItemStackUnsafe(orePrefixes, werkstoff, amount);
    }
}
