package gtPlusPlus.xmod.eio.handler;

import static gtPlusPlus.core.lib.CORE.ConfigSwitches.disableEnderIOIngotTooltips;

import java.lang.reflect.Field;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import gregtech.api.enums.Materials;

import gtPlusPlus.core.lib.LoadedMods;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.xmod.eio.material.MaterialEIO;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class HandlerTooltip_EIO {

	private static Item mIngot;
	private static ItemStack mPulsatingIron;
	private static ItemStack mConductiveIron;
	private static ItemStack mRedstoneAlloy;
	private static ItemStack mElectricalSteel;
	private static ItemStack mEnergeticAlloy;
	private static ItemStack mVibrantAlloy;
	private static ItemStack mSoularium;
	private static ItemStack mDarkIron;
	Class oMainClass;
	Class oIngotClass;

	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event){
		//Is EIO loaded?
		if (!disableEnderIOIngotTooltips && LoadedMods.EnderIO){

			//Is the EIO Ingot Item null?
			//If it is, reflect in.
			if (mIngot == null){
				try {
					oMainClass = Class.forName("crazypants.enderio.EnderIO");
					oIngotClass = Class.forName("crazypants.enderio.material.ItemAlloy");
					if (oMainClass != null && oIngotClass != null){

						Field oAlloyField = oMainClass.getDeclaredField("itemAlloy");	
						oAlloyField.setAccessible(true);						
						Object oAlloy = oAlloyField.get(oMainClass);

						if (oAlloy != null){							
							if (oIngotClass.isInstance(oAlloy) || Item.class.isInstance(oAlloy)){
								mIngot = (Item) oAlloy;									
							}

						}
					}					
				}
				catch (Throwable e) {
				}
			}

			if (mIngot != null){
				//If the Item is an instance of ItemAlloy.class then proceed
				if (event.itemStack.getItem() == mIngot || oIngotClass.isInstance(event.itemStack.getItem()) || event.itemStack.getUnlocalizedName().toLowerCase().contains("item.itemAlloy")){

					//If EIO Item Is not Null, see if the ItemStacks for the ingots are null
					//if they stacks are null, set the stack using the item set via reflection.
					//The meta data is based on the oridinals of the materials in the EIO enum.

					if (mElectricalSteel == null){
						mElectricalSteel = ItemUtils.simpleMetaStack(mIngot, 0, 1);
					}
					if (mEnergeticAlloy == null){
						mEnergeticAlloy = ItemUtils.simpleMetaStack(mIngot, 1, 1);
					}
					if (mVibrantAlloy == null){
						mVibrantAlloy = ItemUtils.simpleMetaStack(mIngot, 2, 1);
					}
					if (mRedstoneAlloy == null){
						mRedstoneAlloy = ItemUtils.simpleMetaStack(mIngot, 3, 1);
					}
					if (mConductiveIron == null){
						mConductiveIron = ItemUtils.simpleMetaStack(mIngot, 4, 1);
					}
					if (mPulsatingIron == null){
						mPulsatingIron = ItemUtils.simpleMetaStack(mIngot, 5, 1);
					}
					if (mDarkIron == null){
						mDarkIron = ItemUtils.simpleMetaStack(mIngot, 6, 1);
					}
					if (mSoularium == null){
						mSoularium = ItemUtils.simpleMetaStack(mIngot, 7, 1);
					}


					//If stacks match, add a tooltip.						
					if (mIngot != null){
						if (event.itemStack.getItem() == mIngot){
							if (event.itemStack.getItemDamage() == 0){
								event.toolTip.add(MaterialEIO.ELECTRICAL_STEEL.vChemicalFormula);										
							}
							else if (event.itemStack.getItemDamage() == 1){
								event.toolTip.add(MaterialEIO.ENERGETIC_ALLOY.vChemicalFormula);										
							}
							else if (event.itemStack.getItemDamage() == 2){
								event.toolTip.add(MaterialEIO.VIBRANT_ALLOY.vChemicalFormula);										
							}
							else if (event.itemStack.getItemDamage() == 3){
								event.toolTip.add(MaterialEIO.REDSTONE_ALLOY.vChemicalFormula);										
							}
							else if (event.itemStack.getItemDamage() == 4){
								event.toolTip.add(MaterialEIO.CONDUCTIVE_IRON.vChemicalFormula);										
							}
							else if (event.itemStack.getItemDamage() == 5){
								event.toolTip.add(MaterialEIO.PULSATING_IRON.vChemicalFormula);										
							}
							else if (event.itemStack.getItemDamage() == 6){
								event.toolTip.add(Materials.DarkSteel.mChemicalFormula);									
							}
							else if (event.itemStack.getItemDamage() == 7){
								event.toolTip.add(MaterialEIO.SOULARIUM.vChemicalFormula);											
							}
						}
					}						
				}
			}
		}
	}
}
