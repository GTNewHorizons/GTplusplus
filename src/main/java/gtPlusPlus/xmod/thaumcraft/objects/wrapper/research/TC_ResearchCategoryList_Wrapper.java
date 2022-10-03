package gtPlusPlus.xmod.thaumcraft.objects.wrapper.research;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.ResourceLocation;

public class TC_ResearchCategoryList_Wrapper {
    public int minDisplayColumn;
    public int minDisplayRow;
    public int maxDisplayColumn;
    public int maxDisplayRow;
    public final ResourceLocation icon;
    public final ResourceLocation background;
    public final Map<String, TC_ResearchItem_Wrapper> research = new HashMap<>();

    public TC_ResearchCategoryList_Wrapper(ResourceLocation icon, ResourceLocation background) {
        this.icon = icon;
        this.background = background;
    }
}
