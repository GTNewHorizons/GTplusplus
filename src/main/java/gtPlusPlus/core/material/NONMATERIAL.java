package gtPlusPlus.core.material;

import gregtech.api.enums.Materials;
import gregtech.api.enums.TextureSet;
import gtPlusPlus.core.util.minecraft.MaterialUtils;

public class NONMATERIAL {

    // Clay
    public static final Material CLAY = MaterialUtils.generateMaterialFromGtENUM(Materials.Clay);

    // Wrought Iron
    public static final Material WROUGHT_IRON = MaterialUtils.generateMaterialFromGtENUM(Materials.WroughtIron);

    static {
        CLAY.setTextureSet(TextureSet.SET_ROUGH);
    }
}
