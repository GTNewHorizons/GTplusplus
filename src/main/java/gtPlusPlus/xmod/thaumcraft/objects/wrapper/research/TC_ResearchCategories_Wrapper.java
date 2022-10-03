package gtPlusPlus.xmod.thaumcraft.objects.wrapper.research;

import cpw.mods.fml.common.FMLLog;
import java.util.Collection;
import java.util.LinkedHashMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.logging.log4j.Level;

public class TC_ResearchCategories_Wrapper {

    public static final LinkedHashMap<String, TC_ResearchCategoryList_Wrapper> researchCategories =
            new LinkedHashMap<>();

    public static TC_ResearchCategoryList_Wrapper getResearchList(String key) {
        return (TC_ResearchCategoryList_Wrapper) researchCategories.get(key);
    }

    public static String getCategoryName(String key) {
        return StatCollector.translateToLocal("tc.research_category." + key);
    }

    public static TC_ResearchItem_Wrapper getResearch(String key) {
        Collection rc = researchCategories.values();

        for (Object cat : rc) {
            Collection rl = ((TC_ResearchCategoryList_Wrapper) cat).research.values();

            for (Object ri : rl) {
                if (((TC_ResearchItem_Wrapper) ri).key.equals(key)) {
                    return (TC_ResearchItem_Wrapper) ri;
                }
            }
        }

        return null;
    }

    public static void registerCategory(String key, ResourceLocation icon, ResourceLocation background) {
        if (getResearchList(key) == null) {
            TC_ResearchCategoryList_Wrapper rl = new TC_ResearchCategoryList_Wrapper(icon, background);
            researchCategories.put(key, rl);
        }
    }

    public static void addResearch(TC_ResearchItem_Wrapper ri) {
        TC_ResearchCategoryList_Wrapper rl = getResearchList(ri.category);
        if (rl != null && !rl.research.containsKey(ri.key)) {
            if (!ri.isVirtual()) {

                for (TC_ResearchItem_Wrapper rr : rl.research.values()) {
                    if (rr.displayColumn == ri.displayColumn && rr.displayRow == ri.displayRow) {
                        FMLLog.log(
                                Level.FATAL,
                                "[Thaumcraft] Research [" + ri.getName()
                                        + "] not added as it overlaps with existing research [" + rr.getName() + "]",
                                new Object[0]);
                        return;
                    }
                }
            }

            rl.research.put(ri.key, ri);
            if (ri.displayColumn < rl.minDisplayColumn) {
                rl.minDisplayColumn = ri.displayColumn;
            }

            if (ri.displayRow < rl.minDisplayRow) {
                rl.minDisplayRow = ri.displayRow;
            }

            if (ri.displayColumn > rl.maxDisplayColumn) {
                rl.maxDisplayColumn = ri.displayColumn;
            }

            if (ri.displayRow > rl.maxDisplayRow) {
                rl.maxDisplayRow = ri.displayRow;
            }
        }
    }
}
