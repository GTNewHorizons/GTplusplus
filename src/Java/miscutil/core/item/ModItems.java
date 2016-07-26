package miscutil.core.item;
import static miscutil.core.creative.AddToCreativeTab.tabMachines;
import static miscutil.core.creative.AddToCreativeTab.tabMisc;
import static miscutil.core.lib.CORE.LOAD_ALL_CONTENT;
import gregtech.api.util.GT_OreDictUnificator;
import miscutil.core.creative.AddToCreativeTab;
import miscutil.core.item.base.BaseItemHotFood;
import miscutil.core.item.base.CoreItem;
import miscutil.core.item.base.dusts.BaseItemDust;
import miscutil.core.item.base.foods.BaseItemFood;
import miscutil.core.item.base.ingots.BaseItemIngot;
import miscutil.core.item.base.ingots.BaseItemIngotHot;
import miscutil.core.item.effects.RarityUncommon;
import miscutil.core.item.general.BufferCore;
import miscutil.core.item.general.fuelrods.FuelRod_Base;
import miscutil.core.item.tool.misc.SandstoneHammer;
import miscutil.core.item.tool.staballoy.StaballoyAxe;
import miscutil.core.item.tool.staballoy.StaballoyPickaxe;
import miscutil.core.lib.CORE;
import miscutil.core.lib.LoadedMods;
import miscutil.core.util.Utils;
import miscutil.core.util.debug.DEBUG_INIT;
import miscutil.core.util.item.UtilsItems;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.registry.GameRegistry;
public final class ModItems {
	/* A name for the material. This should be the same as the name of the variable we use to store the material (in this case "TUTORIAL").
A harvest level for pickaxes. This is a value between 0 and 3 and defines which blocks can be mined with this tool. Its also possible to create blocks which need a higher harvest level than 3, but then you are not able to mine them with vanilla tools.
Common values for the harvest level are:
Wood/Gold Tool: 0
Stone Tool: 1
Iron Tool: 2
Diamond Tool: 3
The durability of the tool or sword. This value defines how often you can use a tool until it breaks. The tools always last one use longer than the entered value.
Common values for the durability are:
Wood Tool: 59
Stone Tool: 131
Iron Tool: 250
Diamond Tool: 1561
Gold Tool: 32
The mining speed of the tool. This value defines how much faster you are with this tool than with your hand.
Common values for the mining speed are:
Wood Tool: 2.0F
Stone Tool: 4.0F
Iron Tool: 6.0F
Diamond Tool: 8.0F
Gold Tool: 12.0F
The damage versus Entites. This value is used to calculate the damage an entity takes if you hit it with this tool/sword. This value defines the basic damage to which different values are added, depending on the type of tool. A sword always causes 4 more damage than written in the ToolMaterial. So, if you want to create a sword which adds 10 damage to your normal damage, the value in the ToolMaterial needs to be 6.0F. Of course the values can be below zero.
Common values for the damage versus Entities are:
Wood Tool: 0.0F (Sword adds 4.0 damage)
Stone Tool: 1.0F (Sword adds 5.0 damage)
Iron Tool: 2.0F (Sword adds 6.0 damage)
Diamond Tool: 3.0F (Sword adds 7.0 damage)
Gold Tool: 0.0F (Sword adds 4.0 damage)
The enchantability of this tool. This value is quite complex to understand and I have to admit that I don't quite know how it is calculated. Basically you can say that a higher enchantability leads to better enchantements with the same amount of XP.
Common values for the enchantability are:
Wood Tool: 15
Stone Tool: 5
Iron Tool: 14
Diamond Tool: 10
Gold Tool: 22*/	
	//Tool Materials
	//public static ToolMaterial TUTORIAL = EnumHelper.addToolMaterial("TUTORIAL", harvestLevel, durability, miningSpeed, damageVsEntities, enchantability);
	public static ToolMaterial STABALLOY = EnumHelper.addToolMaterial("Staballoy", 3, 2500, 7, 1.0F, 18);

	public static Item AAA_Broken;

	public static Item itemDebugShapeSpawner;

	public static Item itemBaseSpawnEgg;

	//EnderIO
	public static Item itemPlateSoularium;
	public static Item itemPlateRedstoneAlloy;
	public static Item itemPlateElectricalSteel;
	public static Item itemPlatePulsatingIron;
	public static Item itemPlateEnergeticAlloy;
	public static Item itemPlateVibrantAlloy;
	public static Item itemPlateConductiveIron;
	public static Item itemPlateDarkSteel;
	//Big Reactors
	public static Item itemPlateBlutonium;
	public static Item itemPlateCyanite;
	public static Item itemPlateLudicrite;
	//Thaumcraft
	public static Item itemPlateVoidMetal;
	//ExtraUtils
	public static Item itemPlateBedrockium;
	//Pneumaticraft
	public static Item itemPlateCompressedIron;
	//SimplyJetpacks
	public static Item itemPlateEnrichedSoularium;
	//rfTools
	public static Item itemPlateDimensionShard;
	//Blood Steel Items
	public static Item itemIngotBloodSteel;
	public static Item itemPlateBloodSteel;
	//Staballoy
	public static Item itemStaballoyPickaxe;
	public static Item itemStaballoyAxe;
	public static Item itemPlateStaballoy;
	public static Item itemIngotStaballoy;
	public static Item itemDustStaballoy;
	public static Item itemDustTinyStaballoy;
	public static Item itemDustSmallStaballoy;
	public static Item itemDustBloodSteel;
	public static Item itemDustTinyBloodSteel;
	public static Item itemDustSmallBloodSteel;
	//Tools
	public static Item itemSandstoneHammer;
	//Machine Related
	public static Item itemBufferCore0;
	//Material related
	public static Item itemStickyRubber;
	public static Item itemIngotBatteryAlloy;
	public static Item itemPlateBatteryAlloy;
	public static Item itemHeliumBlob;
	public static Item itemPLACEHOLDER_Circuit;

	public static Item FuelRod_Empty;
	public static Item FuelRod_Thorium;
	public static Item FuelRod_Uranium;
	public static Item FuelRod_Plutonium;

	public static Item itemBedLocator_Base;
	public static Item itemBaseItemWithCharge;

	public static Item itemHotIngotStaballoy;

	public static Item itemIngotRaisinBread;
	public static Item itemHotIngotRaisinBread;

	public static BaseItemIngot itemIngotTantalloy60;
	public static BaseItemIngotHot itemHotIngotTantalloy60;
	public static BaseItemIngot itemIngotTantalloy61;
	public static BaseItemIngotHot itemHotIngotTantalloy61;

	public static ItemFood itemFoodRaisinToast;
	public static BaseItemHotFood itemHotFoodRaisinToast;
	public static BaseItemFood itemFoodCurriedSausages;
	public static BaseItemHotFood itemHotFoodCurriedSausages;

	//@SuppressWarnings("unused")
	public static final void init(){

		AAA_Broken = new BaseItemIngot("AAA_Broken", "Errors - Tell Alkalus", Utils.rgbtoHexValue(128, 128, 0));

		//Debug Loading
		if (CORE.DEBUG){
			DEBUG_INIT.registerItems();
		}		

		//EnderIO Resources
		if (LoadedMods.EnderIO || LOAD_ALL_CONTENT){
			Utils.LOG_INFO("EnderIO Found - Loading Resources.");
			//Item Init
			itemPlateSoularium = new Item().setUnlocalizedName("itemPlateSoularium").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateSoularium");
			itemPlateRedstoneAlloy = new Item().setUnlocalizedName("itemPlateRedstoneAlloy").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateRedstoneAlloy");
			itemPlateElectricalSteel = new Item().setUnlocalizedName("itemPlateElectricalSteel").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateElectricalSteel");
			itemPlatePulsatingIron = new Item().setUnlocalizedName("itemPlatePulsatingIron").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlatePulsatingIron");
			itemPlateEnergeticAlloy = new Item().setUnlocalizedName("itemPlateEnergeticAlloy").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateEnergeticAlloy");
			itemPlateVibrantAlloy = new Item().setUnlocalizedName("itemPlateVibrantAlloy").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateVibrantAlloy");
			itemPlateConductiveIron = new Item().setUnlocalizedName("itemPlateConductiveIron").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateConductiveIron");
			itemPlateDarkSteel = new Item().setUnlocalizedName("itemPlateDarkSteel").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateDarkSteel");
			//Registry
			GameRegistry.registerItem(itemPlateSoularium, "itemPlateSoularium");
			GameRegistry.registerItem(itemPlateRedstoneAlloy, "itemPlateRedstoneAlloy");
			GameRegistry.registerItem(itemPlateElectricalSteel, "itemPlateElectricalSteel");
			GameRegistry.registerItem(itemPlatePulsatingIron, "itemPlatePulsatingIron");
			GameRegistry.registerItem(itemPlateEnergeticAlloy, "itemPlateEnergeticAlloy");
			GameRegistry.registerItem(itemPlateVibrantAlloy, "itemPlateVibrantAlloy");
			GameRegistry.registerItem(itemPlateConductiveIron, "itemPlateConductiveIron");
			GameRegistry.registerItem(itemPlateDarkSteel, "itemPlateDarkSteel");
		}
		else {
			Utils.LOG_WARNING("EnderIO not Found - Skipping Resources.");
		}
		//Big Reactors
		if (LoadedMods.Big_Reactors|| LOAD_ALL_CONTENT){
			Utils.LOG_INFO("BigReactors Found - Loading Resources.");
			//Item Init
			itemPlateBlutonium = new Item().setUnlocalizedName("itemPlateBlutonium").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateBlutonium");
			itemPlateCyanite = new Item().setUnlocalizedName("itemPlateCyanite").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateCyanite");
			itemPlateLudicrite = new Item().setUnlocalizedName("itemPlateLudicrite").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateLudicrite");
			//Registry
			GameRegistry.registerItem(itemPlateBlutonium, "itemPlateBlutonium");
			GameRegistry.registerItem(itemPlateCyanite, "itemPlateCyanite");
			GameRegistry.registerItem(itemPlateLudicrite, "itemPlateLudicrite");
		}
		else {
			Utils.LOG_WARNING("BigReactors not Found - Skipping Resources.");
		}
		//Thaumcraft
		if (LoadedMods.Thaumcraft|| LOAD_ALL_CONTENT){
			Utils.LOG_INFO("Thaumcraft Found - Loading Resources.");
			//Item Init
			try {
				itemPlateVoidMetal = new Item().setUnlocalizedName("itemPlateVoidMetal").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateVoidMetal");
			} catch (NullPointerException e){
				e.getClass();
			}
			//Registry
			GameRegistry.registerItem(itemPlateVoidMetal, "itemPlateVoidMetal");
		}
		else {
			Utils.LOG_WARNING("Thaumcraft not Found - Skipping Resources.");
		}
		//ExtraUtils
		if (LoadedMods.Extra_Utils|| LOAD_ALL_CONTENT){
			Utils.LOG_INFO("ExtraUtilities Found - Loading Resources.");
			//Item Init
			try {
				itemPlateBedrockium = new Item().setUnlocalizedName("itemPlateBedrockium").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateBedrockium");
			} catch (NullPointerException e){
				e.getClass();
			}
			//Registry
			GameRegistry.registerItem(itemPlateBedrockium, "itemPlateBedrockium");
		}
		else {
			Utils.LOG_WARNING("ExtraUtilities not Found - Skipping Resources.");
		}
		//Pneumaticraft
		if (LoadedMods.PneumaticCraft|| LOAD_ALL_CONTENT){
			Utils.LOG_INFO("PneumaticCraft Found - Loading Resources.");
			//Item Init
			itemPlateCompressedIron = new Item().setUnlocalizedName("itemPlateCompressedIron").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateCompressedIron");
			//Registry
			GameRegistry.registerItem(itemPlateCompressedIron, "itemPlateCompressedIron");
		}
		else {
			Utils.LOG_WARNING("PneumaticCraft not Found - Skipping Resources.");
		}
		//Simply Jetpacks
		if (LoadedMods.Simply_Jetpacks|| LOAD_ALL_CONTENT){
			Utils.LOG_INFO("SimplyJetpacks Found - Loading Resources.");
			//Item Init
			itemPlateEnrichedSoularium = new RarityUncommon().setUnlocalizedName("itemPlateEnrichedSoularium").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateSoularium");
			//Registry
			GameRegistry.registerItem(itemPlateEnrichedSoularium, "itemPlateEnrichedSoularium");
		}
		else {
			Utils.LOG_WARNING("SimplyJetpacks not Found - Skipping Resources.");
		}
		//rfTools
		if (LoadedMods.RFTools|| LOAD_ALL_CONTENT){
			Utils.LOG_INFO("rfTools Found - Loading Resources.");
			//Item Init
			itemPlateDimensionShard = new Item().setUnlocalizedName("itemPlateDimensionShard").setCreativeTab(AddToCreativeTab.tabMisc).setTextureName(CORE.MODID + ":itemPlateDimensionShard");
			//Registry
			GameRegistry.registerItem(itemPlateDimensionShard, "itemPlateDimensionShard");
		}
		else {
			Utils.LOG_WARNING("rfTools not Found - Skipping Resources.");
		}
		//IC2 Exp
		if (LoadedMods.IndustrialCraft2|| LOAD_ALL_CONTENT){
			Utils.LOG_INFO("IndustrialCraft2 Found - Loading Resources.");
			//Item Init
			FuelRod_Empty = new FuelRod_Base("itemFuelRod_Empty", "Empty", 0, 1000);
			FuelRod_Thorium = new FuelRod_Base("itemFuelRod_Thorium", "Thorium", 1000, 1000);
			FuelRod_Uranium = new FuelRod_Base("itemFuelRod_Uranium", "Uranium", 2500, 2500);
			FuelRod_Plutonium = new FuelRod_Base("itemFuelRod_Plutonium", "Plutonium", 5000, 5000);
			//Registry
			//GameRegistry.registerItem(FuelRod_Empty, "itemFuelRod_Empty");
			//GameRegistry.registerItem(FuelRod_Thorium, "itemFuelRod_Thorium");
			//GameRegistry.registerItem(FuelRod_Uranium, "itemFuelRod_Uranium");
			//GameRegistry.registerItem(FuelRod_Plutonium, "itemFuelRod_Plutonium");
		}
		else {
			Utils.LOG_WARNING("IndustrialCraft2 not Found - Skipping Resources.");
		}


		//UtilsItems.generateSpawnEgg("ic2", "boatcarbon", Utils.generateSingularRandomHexValue(), Utils.generateSingularRandomHexValue());



		/*
		 * Misc Items
		 */

		//Staballoy Equipment
		itemStaballoyPickaxe = new StaballoyPickaxe("itemStaballoyPickaxe", STABALLOY).setCreativeTab(AddToCreativeTab.tabTools);
		GameRegistry.registerItem(itemStaballoyPickaxe, itemStaballoyPickaxe.getUnlocalizedName());
		itemStaballoyAxe = new StaballoyAxe("itemStaballoyAxe", STABALLOY).setCreativeTab(AddToCreativeTab.tabTools);
		GameRegistry.registerItem(itemStaballoyAxe, itemStaballoyAxe.getUnlocalizedName());

		//Staballoy Ingot/Plate
		itemIngotStaballoy = new BaseItemIngot("itemIngotStaballoy", "Staballoy", Utils.rgbtoHexValue(68, 75, 66));
		GT_OreDictUnificator.registerOre("ingotStaballoy", UtilsItems.getItemStack(CORE.MODID+":itemIngotStaballoy", 1));
		itemPlateStaballoy = new Item().setUnlocalizedName("itemPlateStaballoy").setCreativeTab(tabMisc).setTextureName(CORE.MODID + ":itemPlateStaballoy");
		GameRegistry.registerItem(itemPlateStaballoy, "itemPlateStaballoy");
		GT_OreDictUnificator.registerOre("plateStaballoy", UtilsItems.getItemStack(CORE.MODID+":itemPlateStaballoy", 1));
		/*itemIngotStaballoy = new Item().setUnlocalizedName("itemIngotStaballoy").setCreativeTab(tabMisc).setTextureName(CORE.MODID + ":itemIngotStaballoy");
		GameRegistry.registerItem(itemIngotStaballoy, "itemIngotStaballoy");
		 */


		//Staballoy Dusts
		itemDustStaballoy = new BaseItemDust("itemDustStaballoy", "Staballoy", Utils.rgbtoHexValue(68, 75, 66), "Dust").setCreativeTab(tabMisc);
		GT_OreDictUnificator.registerOre("dustStaballoy", UtilsItems.getItemStack(CORE.MODID+":itemDustStaballoy", 1));
		itemDustTinyStaballoy = new BaseItemDust("itemDustTinyStaballoy", "Staballoy", Utils.rgbtoHexValue(68, 75, 66), "Tiny").setCreativeTab(tabMisc);
		GT_OreDictUnificator.registerOre("dustTinyStaballoy", UtilsItems.getItemStack(CORE.MODID+":itemDustTinyStaballoy", 1));
		itemDustSmallStaballoy = new BaseItemDust("itemDustSmallStaballoy", "Staballoy", Utils.rgbtoHexValue(68, 75, 66), "Small").setCreativeTab(tabMisc);
		GT_OreDictUnificator.registerOre("dustSmallStaballoy", UtilsItems.getItemStack(CORE.MODID+":itemDustSmallStaballoy", 1));


		//Blood Steel Ingot/Plate
		itemIngotBloodSteel = new Item().setUnlocalizedName("itemIngotBloodSteel").setCreativeTab(tabMisc).setTextureName(CORE.MODID + ":itemIngotBloodSteel");
		GameRegistry.registerItem(itemIngotBloodSteel, "itemIngotBloodSteel");
		itemPlateBloodSteel = new Item().setUnlocalizedName("itemPlateBloodSteel").setCreativeTab(tabMisc).setTextureName(CORE.MODID + ":itemPlateBloodSteel");
		GameRegistry.registerItem(itemPlateBloodSteel, "itemPlateBloodSteel");

		//BloodSteel Dusts
		itemDustBloodSteel = new BaseItemDust("itemDustBloodSteel", "BloodSteel", Utils.rgbtoHexValue(142, 28, 0), "Dust").setCreativeTab(tabMisc);
		GT_OreDictUnificator.registerOre("dustBloodSteel", UtilsItems.getItemStack(CORE.MODID+":itemDustBloodSteel", 1));
		itemDustTinyBloodSteel = new BaseItemDust("itemDustTinyBloodSteel", "BloodSteel", Utils.rgbtoHexValue(142, 28, 0), "Tiny").setCreativeTab(tabMisc);
		GT_OreDictUnificator.registerOre("dustTinyBloodSteel", UtilsItems.getItemStack(CORE.MODID+":itemDustTinyBloodSteel", 1));
		itemDustSmallBloodSteel = new BaseItemDust("itemDustSmallBloodSteel", "BloodSteel", Utils.rgbtoHexValue(142, 28, 0), "Small").setCreativeTab(tabMisc);
		GT_OreDictUnificator.registerOre("dustSmallBloodSteel", UtilsItems.getItemStack(CORE.MODID+":itemDustSmallBloodSteel", 1));

		//Hot Staballoy Ingot
		itemHotIngotStaballoy = new BaseItemIngotHot("itemHotIngotStaballoy", "Staballoy", UtilsItems.getItemStack(CORE.MODID+":itemIngotStaballoy", 1));
		GT_OreDictUnificator.registerOre("ingotHotStaballoy", UtilsItems.getItemStack(CORE.MODID+":itemHotIngotStaballoy", 1));

		//Tantalloy Ingot/Plate
		itemIngotTantalloy60 = new BaseItemIngot("itemIngotTantalloy60", "Tantalloy-60", Utils.rgbtoHexValue(68, 75, 166));
		GT_OreDictUnificator.registerOre("ingotTantalloy-60", UtilsItems.getItemStack(CORE.MODID+":itemIngotTantalloy60", 1));
		//Hot Tantalloy Ingot
		itemHotIngotTantalloy60 = new BaseItemIngotHot("itemHotIngotTantalloy60", "Tantalloy-60", UtilsItems.getItemStack(CORE.MODID+":itemIngotTantalloy60", 1));
		GT_OreDictUnificator.registerOre("ingotHotTantalloy-60", UtilsItems.getItemStack(CORE.MODID+":itemHotIngotTantalloy60", 1));

		//Tantalloy Ingot/Plate
		itemIngotTantalloy61 = new BaseItemIngot("itemIngotTantalloy61", "Tantalloy-61", Utils.rgbtoHexValue(122, 135, 196));
		GT_OreDictUnificator.registerOre("ingotTantalloy-61", UtilsItems.getItemStack(CORE.MODID+":itemIngotTantalloy61", 1));
		//Hot Tantalloy Ingot
		itemHotIngotTantalloy61 = new BaseItemIngotHot("itemHotIngotTantalloy61", "Tantalloy-61", UtilsItems.getItemStack(CORE.MODID+":itemIngotTantalloy61", 1));
		GT_OreDictUnificator.registerOre("ingotHotTantalloy-61", UtilsItems.getItemStack(CORE.MODID+":itemHotIngotTantalloy61", 1));

		//Sandstone Hammer
		itemSandstoneHammer = new SandstoneHammer("itemSandstoneHammer").setCreativeTab(AddToCreativeTab.tabTools);
		GameRegistry.registerItem(itemSandstoneHammer, itemSandstoneHammer.getUnlocalizedName());
		//Buffer Cores!
		Item itemBufferCore;
		for(int i=1; i<=10; i++){
			//Utils.LOG_INFO(""+i);
			itemBufferCore = new BufferCore("itemBufferCore", i).setCreativeTab(AddToCreativeTab.tabMachines);
			GameRegistry.registerItem(itemBufferCore, itemBufferCore.getUnlocalizedName()+i);
			//System.out.println("Buffer Core registration count is: "+i);
		}
		itemStickyRubber = new Item().setUnlocalizedName("itemStickyRubber").setCreativeTab(tabMachines).setTextureName(CORE.MODID + ":itemStickyRubber");
		GameRegistry.registerItem(itemStickyRubber, "itemStickyRubber");
		GT_OreDictUnificator.registerOre("ingotRubber", UtilsItems.getItemStack(CORE.MODID+":itemStickyRubber", 1));
		//Battery Alloy For cheap Niggers
		itemIngotBatteryAlloy = new Item().setUnlocalizedName("itemIngotBatteryAlloy").setCreativeTab(tabMachines).setTextureName(CORE.MODID + ":itemIngotBatteryAlloy");
		GameRegistry.registerItem(itemIngotBatteryAlloy, "itemIngotBatteryAlloy");
		GT_OreDictUnificator.registerOre("ingotBatteryAlloy", UtilsItems.getItemStack(CORE.MODID+":itemIngotBatteryAlloy", 1));
		itemPlateBatteryAlloy = new Item().setUnlocalizedName("itemPlateBatteryAlloy").setCreativeTab(tabMachines).setTextureName(CORE.MODID + ":itemPlateBatteryAlloy");
		GameRegistry.registerItem(itemPlateBatteryAlloy, "itemPlateBatteryAlloy");
		GT_OreDictUnificator.registerOre("plateBatteryAlloy", UtilsItems.getItemStack(CORE.MODID+":itemPlateBatteryAlloy", 1));

		itemHeliumBlob = new CoreItem("itemHeliumBlob", tabMisc).setTextureName(CORE.MODID + ":itemHeliumBlob");
		//GameRegistry.registerItem(itemHeliumBlob, "itemHeliumBlob");

		itemPLACEHOLDER_Circuit = new Item().setUnlocalizedName("itemPLACEHOLDER_Circuit").setTextureName(CORE.MODID + ":itemPLACEHOLDER_Circuit");
		GameRegistry.registerItem(itemPLACEHOLDER_Circuit, "itemPLACEHOLDER_Circuit");



		//Raisin Bread		
		itemIngotRaisinBread = new BaseItemFood("itemIngotRaisinBread", 3, 1.5f, false, new PotionEffect(Potion.weakness.id, 40, 1)).setAlwaysEdible();
		GT_OreDictUnificator.registerOre("foodRaisinBread", UtilsItems.getItemStack(CORE.MODID+":itemIngotRaisinBread", 1));
		//Hot Raisin Bread 
		itemHotIngotRaisinBread = new BaseItemHotFood("itemHotIngotRaisinBread", 1, 0.5f, "Raisin Bread", 120, itemIngotRaisinBread);
		GT_OreDictUnificator.registerOre("foodHotRaisinBread", UtilsItems.getItemStack(CORE.MODID+":itemHotIngotRaisinBread", 1));

		//Raisin Bread		
		itemFoodRaisinToast = new BaseItemFood("itemFoodRaisinToast", 1, 0.5f, false).setAlwaysEdible();
		GT_OreDictUnificator.registerOre("foodRaisinToast", UtilsItems.getItemStack(CORE.MODID+":itemFoodRaisinToast", 1));
		//Hot Raisin Bread 
		itemHotFoodRaisinToast = new BaseItemHotFood("itemHotFoodRaisinToast", 1, 0.5f, "Raisin Toast", 20, itemFoodRaisinToast);
		GT_OreDictUnificator.registerOre("foodHotRaisinToast", UtilsItems.getItemStack(CORE.MODID+":itemHotFoodRaisinToast", 1));

		//Raisin Bread		
		itemFoodCurriedSausages = new BaseItemFood("itemFoodCurriedSausages", 5, 2f, false);
		GT_OreDictUnificator.registerOre("foodCurriedSausages", UtilsItems.getItemStack(CORE.MODID+":itemFoodCurriedSausages", 1));
		//Hot Raisin Bread 
		itemHotFoodCurriedSausages = new BaseItemHotFood("itemHotFoodCurriedSausages", 1, 0.5f, "Raisin Bread", 240, itemFoodCurriedSausages);
		GT_OreDictUnificator.registerOre("foodHotCurriedSausages", UtilsItems.getItemStack(CORE.MODID+":itemHotFoodCurriedSausages", 1));
	}
}