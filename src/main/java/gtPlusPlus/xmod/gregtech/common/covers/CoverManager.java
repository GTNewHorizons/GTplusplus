package gtPlusPlus.xmod.gregtech.common.covers;

import gtPlusPlus.core.lib.VanillaColours;
import gtPlusPlus.xmod.gregtech.common.items.MetaCustomCoverItem;
import gtPlusPlus.xmod.gregtech.common.items.covers.MetaItemCoverCasings;

import static gregtech.api.enums.Mods.ZTones;

public class CoverManager {

    // GT
    public static MetaItemCoverCasings Cover_Gt_Machine_Casing;

    public static void generateCustomCovers() {

        // GT Machine Casings
        Cover_Gt_Machine_Casing = new MetaItemCoverCasings();
    }

}
