package gtPlusPlus.core.item.base.rods;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.GT_Values;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.item.base.BaseItemComponent;
import gtPlusPlus.core.material.Material;
import gtPlusPlus.core.util.minecraft.ItemUtils;

public class BaseItemRodLong extends BaseItemComponent{

	public BaseItemRodLong(final Material material) {
		super(material, BaseItemComponent.ComponentTypes.RODLONG);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack p_77653_1_) {
		return ("Long "+this.materialName+ " Rod");
	}

}
