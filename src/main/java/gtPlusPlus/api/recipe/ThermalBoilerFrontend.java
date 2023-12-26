package gtPlusPlus.api.recipe;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import com.gtnewhorizons.modularui.api.math.Pos2d;

import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.recipe.maps.LargeNEIFrontend;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.common.gui.modularui.UIHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ThermalBoilerFrontend extends LargeNEIFrontend {

    private static final int tileSize = 18;
    private static final int xOrigin = 16;
    private static final int yOrigin = 8 + tileSize; // Aligned with second row of output items.
    private static final int maxInputs = 3;

    public ThermalBoilerFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
            NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(uiPropertiesBuilder, neiPropertiesBuilder);
    }

    @Override
    public List<Pos2d> getFluidInputPositions(int fluidInputCount) {
        return UIHelper.getGridPositions(
                fluidInputCount,
                xOrigin + tileSize * (maxInputs - fluidInputCount),
                yOrigin,
                maxInputs);
    }
}
