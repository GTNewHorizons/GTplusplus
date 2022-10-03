package gtPlusPlus.xmod.gregtech.common.helpers.autocrafter;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.GT4Entity_AutoCrafter;
import java.util.*;
import java.util.Map.Entry;

public class AC_Helper_Utils {

    // AC maps
    public static final Map<Integer, GT4Entity_AutoCrafter> sAutocrafterMap = new HashMap<>();

    // Add Crafter
    public static int addCrafter(GT4Entity_AutoCrafter AC) {
        if (!sAutocrafterMap.containsValue(AC)) {
            int increase = sAutocrafterMap.size() + 1;
            sAutocrafterMap.put(increase, AC);
            Logger.INFO("[A-C] " + "Added Auto-Crafter to index on position " + increase + ".");
            return increase;
        } else {
            Logger.INFO("[A-C] Tried adding an Auto-Crafter to Index, but found one already there.");
        }
        return 0;
    }

    // Remove Crafter
    public static boolean removeCrafter(int frequency) {
        if (!sAutocrafterMap.isEmpty()) {
            if (sAutocrafterMap.containsKey(frequency)) {
                sAutocrafterMap.remove(frequency);
                return true;
            }
        }
        return false;
    }

    public static boolean removeCrafter(GT4Entity_AutoCrafter AC) {
        if (!sAutocrafterMap.isEmpty()) {
            if (sAutocrafterMap.containsValue(AC)) {
                sAutocrafterMap.remove(getIDByCrafter(AC));
                return true;
            }
        }
        return false;
    }

    // Get Crafter
    public static GT4Entity_AutoCrafter getCrafterByID(int ID) {
        if (!sAutocrafterMap.isEmpty()) {
            Set<Entry<Integer, GT4Entity_AutoCrafter>> players = sAutocrafterMap.entrySet();
            for (Entry<Integer, GT4Entity_AutoCrafter> current : players) {
                if (current.getKey().equals(ID)) {
                    return current.getValue();
                }
            }
        }
        Logger.WARNING("Failed. [getCrafterByID]");
        return null;
    }

    public static int getIDByCrafter(GT4Entity_AutoCrafter AC) {
        if (!sAutocrafterMap.isEmpty()) {
            Set<Entry<Integer, GT4Entity_AutoCrafter>> players = sAutocrafterMap.entrySet();
            for (Entry<Integer, GT4Entity_AutoCrafter> current : players) {
                if (current.getValue().equals(AC)) {
                    return current.getKey();
                }
            }
        }
        Logger.WARNING("Failed. [getIDByCrafter]");
        return 0;
    }
}
