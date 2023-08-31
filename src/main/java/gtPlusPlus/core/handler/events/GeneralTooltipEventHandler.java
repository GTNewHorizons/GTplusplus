package gtPlusPlus.core.handler.events;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import advsolar.common.AdvancedSolarPanel;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gregtech.api.enums.Mods;
import gtPlusPlus.GTplusplus;
import gtPlusPlus.GTplusplus.INIT_PHASE;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.util.math.MathUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;

public class GeneralTooltipEventHandler {

    @Optional.Method(modid = Mods.Names.ADVANCED_SOLAR_PANEL)
    public static void molecularTransformer(ItemTooltipEvent event) {
        if (event.itemStack.getItem() == Item.getItemFromBlock(AdvancedSolarPanel.blockMolecularTransformer)) {
            event.toolTip.add("" + EnumChatFormatting.RED + "Disabled, Use the multiblock");
        }
    }
}
