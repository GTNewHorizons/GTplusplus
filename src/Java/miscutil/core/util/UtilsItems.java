package miscutil.core.util;

import gregtech.api.util.GT_OreDictUnificator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import miscutil.core.handler.registration.RegistrationHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class UtilsItems {

	public static ItemStack getItemStackOfItem(Boolean modToCheck, String mod_itemname_meta){
		if (modToCheck){
			try{
				Item em = null;

				Item em1 = getItem(mod_itemname_meta);
				Utils.LOG_WARNING("Found: "+em1.toString());
				if (!em1.equals(null)){
					em = em1;
				}			
				else {
					em = null;
					return null;
				}			
				if (!em.equals(null)){
					ItemStack returnStack = new ItemStack(em,1,16);				
					return returnStack;
				}
				Utils.LOG_WARNING(mod_itemname_meta+" not found.");
				return null;
			} catch (NullPointerException e) {
				Utils.LOG_ERROR(mod_itemname_meta+" not found. [NULL]");
				return null;
			}
		}
		return null;
	}

	public static void getItemForOreDict(String FQRN, String oreDictName, String itemName, int meta){
		try {
			Item em = null;			
			Item em1 = getItem(FQRN);
			Utils.LOG_WARNING("Found: "+em1.getUnlocalizedName()+":"+meta);
			if (em1 != null){
				em = em1;
			}
			if (em != null){

				ItemStack metaStack = new ItemStack(em,1,meta);
				GT_OreDictUnificator.registerOre(oreDictName, metaStack);

				/*ItemStack itemStackWithMeta = new ItemStack(em,1,meta);
				GT_OreDictUnificator.registerOre(oreDictName, new ItemStack(itemStackWithMeta.getItem()));*/
			}
		} catch (NullPointerException e) {
			Utils.LOG_ERROR(itemName+" not found. [NULL]");
		}
	}

	public static void recipeBuilder(Object slot_1, Object slot_2, Object slot_3, Object slot_4, Object slot_5, Object slot_6, Object slot_7, Object slot_8, Object slot_9, ItemStack resultItem){	

		ArrayList<Object> validSlots = new ArrayList<Object>();

		//, String lineFirst, String lineSecond, String lineThird
		Utils.LOG_INFO("Trying to add a recipe for "+resultItem.toString());
		String a,b,c,d,e,f,g,h,i;		
		//ItemStack empty = new ItemStack(Blocks.air);
		if (slot_1 == null){ a = " ";} else { a = "1";validSlots.add('1');validSlots.add(slot_1);}
		Utils.LOG_WARNING(a);
		if (slot_2 == null){ b = " ";} else { b = "2";validSlots.add('2');validSlots.add(slot_2);}
		Utils.LOG_WARNING(b);
		if (slot_3 == null){ c = " ";} else { c = "3";validSlots.add('3');validSlots.add(slot_3);}
		Utils.LOG_WARNING(c);
		if (slot_4 == null){ d = " ";} else { d = "4";validSlots.add('4');validSlots.add(slot_4);}
		Utils.LOG_WARNING(d);
		if (slot_5 == null){ e = " ";} else { e = "5";validSlots.add('5');validSlots.add(slot_5);}
		Utils.LOG_WARNING(e);
		if (slot_6 == null){ f = " ";} else { f = "6";validSlots.add('6');validSlots.add(slot_6);}
		Utils.LOG_WARNING(f);
		if (slot_7 == null){ g = " ";} else { g = "7";validSlots.add('7');validSlots.add(slot_7);}
		Utils.LOG_WARNING(g);
		if (slot_8 == null){ h = " ";} else { h = "8";validSlots.add('8');validSlots.add(slot_8);}
		Utils.LOG_WARNING(h);
		if (slot_9 == null){ i = " ";} else { i = "9";validSlots.add('9');validSlots.add(slot_9);}
		Utils.LOG_WARNING(i);


		Utils.LOG_ERROR("_______");
		String lineOne = a+b+c;
		Utils.LOG_ERROR("|"+a+"|"+b+"|"+c+"|");
		Utils.LOG_ERROR("_______");
		String lineTwo = d+e+f;
		Utils.LOG_ERROR("|"+d+"|"+e+"|"+f+"|");
		Utils.LOG_ERROR("_______");
		String lineThree = g+h+i;
		Utils.LOG_ERROR("|"+g+"|"+h+"|"+i+"|");
		Utils.LOG_ERROR("_______");

		validSlots.add(0, lineOne);
		validSlots.add(1, lineTwo);
		validSlots.add(2, lineThree);
		Boolean AadvancedLog = true;
		if (AadvancedLog){
			int j = 0;
			int l = validSlots.size();
			Utils.LOG_WARNING("l:"+l);
			while (j <= l) {
				Utils.LOG_WARNING("j:"+j);
				if (j <= 2){
					Utils.LOG_WARNING("ArrayList Values: "+validSlots.get(j));
					Utils.LOG_WARNING("Adding 1.");
					j++;
				}
				else if (j >= 3){
					Utils.LOG_WARNING("ArrayList Values: '"+validSlots.get(j)+"' "+validSlots.get(j+1));
					if (j < (l-2)){
						Utils.LOG_WARNING("Adding 2.");
						j=j+2;
					}
					else {
						Utils.LOG_WARNING("Done iteration.");
						break;
					}
				}
				else if (j == l){
					Utils.LOG_WARNING("Done iteration.");
					break;
				}
				if (validSlots.get(j) instanceof String || validSlots.get(j) instanceof ItemStack){
					//Utils.LOG_WARNING("Is Valid: "+validSlots.get(j));
				}
			}	
		}

		try {
			/*Utils.LOG_WARNING("validSlots to array: "+validSlots.toArray());
			Object[] validSlotsArray = (Object[]) validSlots.toArray();

			for(int j = 0; j < validSlotsArray.length; j++)
			{
				Utils.LOG_ERROR(""+validSlotsArray[j]);
			}*/

			GameRegistry.addRecipe(new ShapedOreRecipe(resultItem.copy(), (Object[]) validSlots.toArray()));		
			Utils.LOG_INFO("Success! Added a recipe for "+resultItem.toString());
			RegistrationHandler.recipesSuccess++;
			/*try {
			try {
				try {
				//Code
				}
					catch (NullPointerException | ClassCastException r){
						Utils.LOG_WARNING("@@@: Invalid Recipe detected for: "+resultItem.getUnlocalizedName());
						RegistrationHandler.recipesFailed++;
						r.printStackTrace();
						//System.exit(1);
					}
				}
				catch (NullPointerException o){

					Utils.LOG_WARNING("@@@: Invalid Recipe detected for: "+resultItem.getUnlocalizedName());
					o.printStackTrace();
					RegistrationHandler.recipesFailed++;
					//System.exit(1);
				}
			}
			catch (ClassCastException r){
				Utils.LOG_WARNING("@@@: Casting to ObjectArray Failed :(");
			}*/
		}
		catch(NullPointerException | ClassCastException k){
			k.getMessage();
			k.getClass();
			k.printStackTrace();
			k.getLocalizedMessage();
			Utils.LOG_WARNING("@@@: Invalid Recipe detected for: "+resultItem.getUnlocalizedName());
			RegistrationHandler.recipesFailed++;
			//System.exit(1);
		}
	}

	public static Item getItem(String fqrn) // fqrn = fully qualified resource name
	{
		String[] fqrnSplit = fqrn.split(":");
		return GameRegistry.findItem(fqrnSplit[0], fqrnSplit[1]);
	}

	public static ItemStack getItemStack(String fqrn, int Size) // fqrn = fully qualified resource name
	{
		String[] fqrnSplit = fqrn.split(":");
		return GameRegistry.findItemStack(fqrnSplit[0], fqrnSplit[1], Size);
	}
	
	// TODO
	/*public static FluidStack getFluidStack(Materials m, int Size) // fqrn = fully qualified resource name
	{
		String[] fqrnSplit = fqrn.split(":");
		
		FluidStack x = (FluidStack) "Materials."+m+".getFluid"(Size);
		
		return GameRegistry.findItemStack(fqrnSplit[0], fqrnSplit[1], Size);
	}*/

	public static Item getItemInPlayersHand(){
		Minecraft mc = Minecraft.getMinecraft();
		Item heldItem = null;

		try{heldItem = mc.thePlayer.getHeldItem().getItem();
		}catch(NullPointerException e){return null;}

		if (heldItem != null){
			return heldItem;
		}

		return null;
	}

	public static boolean removeCraftingRecipe(Object x){
		if (null == x){return false;}
		if (x instanceof String){	
			Item R = getItem((String) x);
			if (R != null){
				x = R;
			}
			else {
				return false;
			}
		}
		if (x instanceof Item || x instanceof ItemStack){
			if (x instanceof Item){
				ItemStack r = new ItemStack((Item) x);
				Utils.LOG_INFO("Removing Recipe for "+r.getUnlocalizedName());
			}
			else {
				Utils.LOG_INFO("Removing Recipe for "+((ItemStack) x).getUnlocalizedName());
			}
			if (x instanceof ItemStack){
				Item r = ((ItemStack) x).getItem();		
				if (null != r){
					x = r;
				}
				else {
					Utils.LOG_INFO("Recipe removal failed - Tell Alkalus.");
					return false;
				}
			}			
			if (attemptRecipeRemoval((Item) x)){
				Utils.LOG_INFO("Recipe removal successful");
				return true;
			}
			Utils.LOG_INFO("Recipe removal failed - Tell Alkalus.");
			return false;			
		}
		return false;
	}

	private static boolean attemptRecipeRemoval(Item I){
		Utils.LOG_WARNING("Create list of recipes.");
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		Iterator<IRecipe> items = recipes.iterator();
		Utils.LOG_WARNING("Begin list iteration.");
		while (items.hasNext()) {
			ItemStack is = items.next().getRecipeOutput();
			if (is != null && is.getItem() == I){
				items.remove();
				Utils.LOG_INFO("Remove a recipe with "+I.getUnlocalizedName()+" as output.");
				continue;
			}
		}
		Utils.LOG_WARNING("All recipes should be gone?");
		if (!items.hasNext()){
			Utils.LOG_WARNING("We iterated once, let's try again to double check.");
			Iterator<IRecipe> items2 = recipes.iterator();
			while (items2.hasNext()) {
				ItemStack is = items2.next().getRecipeOutput();
				if (is != null && is.getItem() == I){
					items.remove();
					Utils.LOG_WARNING("REMOVING MISSED RECIPE - RECHECK CONSTRUCTORS");
					return true;
				}
			}
			Utils.LOG_WARNING("Should be all gone now after double checking, so return true.");
			return true;
		}
		Utils.LOG_INFO("Return false, because something went wrong.");
		return false;
	}
}
