package gtPlusPlus.nei;

import codechicken.nei.recipe.TemplateRecipeHandler;
import gregtech.api.util.GTPP_Recipe.GTPP_Recipe_Map;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.core.util.math.MathUtils;

public class GT_NEI_FluidReactor extends GTPP_NEI_DefaultHandler {

	public GT_NEI_FluidReactor() {
		super(GTPP_Recipe_Map.sChemicalPlantRecipes);
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		return new GT_NEI_FluidReactor();
	}

	@Override
	public void drawExtras(final int aRecipeIndex) {
		//todo: delegate most of the properties to GT
		final long tEUt = ((CachedDefaultRecipe) this.arecipes.get(aRecipeIndex)).mRecipe.mEUt;
		final int tDuration = ((CachedDefaultRecipe) this.arecipes.get(aRecipeIndex)).mRecipe.mDuration;
		if (tEUt != 0) {
			drawText(10, 73, "Total: " + MathUtils.formatNumbers((long) (tDuration * tEUt)) + " EU", -16777216);
			//drawText(10, 83, "Usage: " + tEUt + " EU/t", -16777216);
			if (this.mRecipeMap.mShowVoltageAmperageInNEI) {
				drawText(10, 83, "Voltage: " + MathUtils.formatNumbers((tEUt / this.mRecipeMap.mAmperage)) + " EU/t", -16777216);
				drawText(10, 93, "Amperage: " + this.mRecipeMap.mAmperage, -16777216);
			} else {
				drawText(10, 93, "Voltage: unspecified", -16777216);
				drawText(10, 103, "Amperage: unspecified", -16777216);
			}
		}
		if (tDuration > 0) {
			drawText(10, 103, "Time: " + (tDuration < 20 ? "< 1" : MathUtils.formatNumbers(0.05d * tDuration)) + " secs", -16777216);
		}
		if ((GT_Utility.isStringValid(this.mRecipeMap.mNEISpecialValuePre)) || (GT_Utility.isStringValid(this.mRecipeMap.mNEISpecialValuePost))) {
			int aTier = (((CachedDefaultRecipe) this.arecipes.get(aRecipeIndex)).mRecipe.mSpecialValue);
			String aTierMaterial = " - ";
			if (aTier <= 0) {
				aTierMaterial += "Bronze";
			}
			else if (aTier == 1) {
				aTierMaterial += "Steel";
			}
			else if (aTier == 2) {
				aTierMaterial += "Aluminium";
			}
			else if (aTier == 3) {
				aTierMaterial += "Stainless Steel";
			}
			else if (aTier == 4) {
				aTierMaterial += "Titanium";
			}
			else if (aTier == 5) {
				aTierMaterial += "Tungsten Steel";
			}
			else if (aTier == 6) {
				aTierMaterial += "Laurenium";
			}
			else if (aTier == 7) {
				aTierMaterial += "Botmium";
			}

			drawText(10, 113, this.mRecipeMap.mNEISpecialValuePre + (((CachedDefaultRecipe) this.arecipes.get(aRecipeIndex)).mRecipe.mSpecialValue * this.mRecipeMap.mNEISpecialValueMultiplier) + aTierMaterial, -16777216);
		}
	}
}
