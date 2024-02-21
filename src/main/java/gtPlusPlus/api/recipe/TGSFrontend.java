package gtPlusPlus.api.recipe;

import static net.minecraft.util.EnumChatFormatting.GRAY;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.ParametersAreNonnullByDefault;

import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.common.widget.ProgressBar;
import gregtech.common.gui.modularui.UIHelper;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.GregtechMetaTileEntityTreeFarm;
import net.minecraft.util.StatCollector;

import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.GT_NEI_DefaultHandler;
import gregtech.nei.RecipeDisplayInfo;
import gregtech.nei.formatter.INEISpecialInfoFormatter;
import gtPlusPlus.core.item.ModItems;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TGSFrontend extends RecipeMapFrontend {

    public TGSFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder, NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(
                uiPropertiesBuilder.addNEITransferRect(new Rectangle(70, 50, 36, 20)),
                neiPropertiesBuilder.neiSpecialInfoFormatter(new TGSSpecialValueFormatter())
        );
    }

    @Override
    protected void drawEnergyInfo(RecipeDisplayInfo recipeInfo) {}

    @Override
    protected void drawDurationInfo(RecipeDisplayInfo recipeInfo) {}

    @Override
    public List<Pos2d> getItemInputPositions(int itemInputCount) {
        return UIHelper.getGridPositions(GregtechMetaTileEntityTreeFarm.Mode.values().length, 34, 42, 2);
    }

    @Override
    public List<Pos2d> getItemOutputPositions(int itemOutputCount) {
        return UIHelper.getGridPositions(GregtechMetaTileEntityTreeFarm.Mode.values().length, 106, 42, 2);
    }

    @Override
    public Pos2d getSpecialItemPosition() {
        return new Pos2d(80, 15);
    }

    @Override
    public void addProgressBar(ModularWindow.Builder builder, Supplier<Float> progressSupplier, Pos2d windowOffset) {
        assert uiProperties.progressBarTexture != null;
        builder.widget(
                new ProgressBar().setTexture(uiProperties.progressBarTexture.get(), 20)
                        .setDirection(uiProperties.progressBarDirection)
                        .setProgress(progressSupplier)
                        .setSynced(false, false)
                        .setPos(uiProperties.progressBarPos.add(windowOffset).add(0, 27))
                        .setSize(uiProperties.progressBarSize));
    }

    @Override
    protected List<String> handleNEIItemOutputTooltip(List<String> currentTip,
            GT_NEI_DefaultHandler.FixedPositionedStack pStack) {
        if (ModItems.fluidFertBasic != null && pStack.isChanceBased()) {
            currentTip.add(
                    GRAY + StatCollector.translateToLocalFormatted(
                            "gtpp.nei.tgs.sapling",
                            StatCollector.translateToLocal(ModItems.fluidFertBasic.getUnlocalizedName())));
        } else {
            super.handleNEIItemOutputTooltip(currentTip, pStack);
        }
        return currentTip;
    }

    @Override
    protected void drawNEIOverlayForOutput(GT_NEI_DefaultHandler.FixedPositionedStack stack) {}

    private static class TGSSpecialValueFormatter implements INEISpecialInfoFormatter {

        @Override
        public List<String> format(RecipeDisplayInfo recipeInfo) {
            if (ModItems.fluidFertBasic == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(
                    StatCollector.translateToLocal("gtpp.nei.tgs.1"),
                    StatCollector.translateToLocalFormatted(
                            "gtpp.nei.tgs.2",
                            StatCollector.translateToLocal(ModItems.fluidFertBasic.getUnlocalizedName())),
                    StatCollector.translateToLocal("gtpp.nei.tgs.3"));
        }
    }
}
