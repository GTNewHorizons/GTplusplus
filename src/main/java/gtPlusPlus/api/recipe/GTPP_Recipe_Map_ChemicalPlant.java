package gtPlusPlus.api.recipe;

import static net.minecraft.util.EnumChatFormatting.GRAY;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.common.widget.ProgressBar;

import gregtech.api.gui.modularui.GT_UITextures;
import gregtech.api.util.GT_Recipe;
import gregtech.common.gui.modularui.UIHelper;
import gregtech.nei.GT_NEI_DefaultHandler;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.xmod.gregtech.api.gui.GTPP_UITextures;

public class GTPP_Recipe_Map_ChemicalPlant extends GT_Recipe.GT_Recipe_Map {

    private static final List<String> tierMaterialNames = Arrays.asList(
            "Bronze",
            "Steel",
            "Aluminium",
            "Stainless Steel",
            "Titanium",
            "Tungsten Steel",
            "Laurenium",
            "Botmium");

    public GTPP_Recipe_Map_ChemicalPlant(Collection<GT_Recipe> aRecipeList, String aUnlocalizedName, String aLocalName,
            String aNEIName, String aNEIGUIPath, int aUsualInputCount, int aUsualOutputCount, int aMinimalInputItems,
            int aMinimalInputFluids, int aAmperage, String aNEISpecialValuePre, int aNEISpecialValueMultiplier,
            String aNEISpecialValuePost, boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed) {
        super(
                aRecipeList,
                aUnlocalizedName,
                aLocalName,
                aNEIName,
                aNEIGUIPath,
                aUsualInputCount,
                aUsualOutputCount,
                aMinimalInputItems,
                aMinimalInputFluids,
                aAmperage,
                aNEISpecialValuePre,
                aNEISpecialValueMultiplier,
                aNEISpecialValuePost,
                aShowVoltageAmperageInNEI,
                aNEIAllowed);
        setSlotOverlay(false, false, GT_UITextures.OVERLAY_SLOT_MOLECULAR_1);
        setSlotOverlay(false, true, GT_UITextures.OVERLAY_SLOT_VIAL_1);
        setSlotOverlay(true, false, GT_UITextures.OVERLAY_SLOT_MOLECULAR_3);
        setSlotOverlay(true, true, GT_UITextures.OVERLAY_SLOT_VIAL_2);
        setProgressBar(GTPP_UITextures.PROGRESSBAR_FLUID_REACTOR, ProgressBar.Direction.CIRCULAR_CW);
        setProgressBarPos(82, 24);
        setUsualFluidInputCount(4);
        setUsualFluidOutputCount(2);
        setNEISpecialInfoFormatter((recipeInfo, applyPrefixAndSuffix) -> {
            int specialValue = recipeInfo.recipe.mSpecialValue;
            String tierMaterial = "";
            for (int i = 0; i < tierMaterialNames.size(); i++) {
                if (i == specialValue) {
                    tierMaterial = tierMaterialNames.get(i);
                }
            }
            // blockrenderer uses 1-indexed
            return Collections.singletonList(applyPrefixAndSuffix.apply(specialValue + 1) + " - " + tierMaterial);
        });
    }

    @Override
    public List<Pos2d> getItemInputPositions(int itemInputCount) {
        return UIHelper.getGridPositions(itemInputCount, 7, 6, itemInputCount, 1);
    }

    @Override
    public List<Pos2d> getItemOutputPositions(int itemOutputCount) {
        return UIHelper.getGridPositions(itemOutputCount, 106, 15, 2);
    }

    @Override
    public List<Pos2d> getFluidInputPositions(int fluidInputCount) {
        return UIHelper.getGridPositions(fluidInputCount, 7, 41, fluidInputCount, 1);
    }

    @Override
    public List<Pos2d> getFluidOutputPositions(int fluidOutputCount) {
        return UIHelper.getGridPositions(fluidOutputCount, 142, 15, 1, fluidOutputCount);
    }

    @Override
    protected List<String> handleNEIItemInputTooltip(List<String> currentTip,
            GT_NEI_DefaultHandler.FixedPositionedStack pStack) {
        if (ItemUtils.isCatalyst(pStack.item)) {
            currentTip.add(GRAY + "Does not always get consumed in the process");
            currentTip.add(GRAY + "Higher tier pipe casings allow this item to last longer");
        } else {
            super.handleNEIItemInputTooltip(currentTip, pStack);
        }
        return currentTip;
    }

    @Override
    protected void drawNEIOverlayForInput(GT_NEI_DefaultHandler.FixedPositionedStack stack) {
        if (ItemUtils.isCatalyst(stack.item)) {
            drawNEIOverlayText("NC*", stack);
        } else {
            super.drawNEIOverlayForInput(stack);
        }
    }
}
