package gtPlusPlus.core.common.compat;

import static gtPlusPlus.core.handler.COMPAT_HANDLER.*;

import gtPlusPlus.core.recipe.ShapedRecipeObject;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import net.minecraft.item.ItemStack;

public class COMPAT_CompactWindmills {

    // Change IC2 Upgrades
    public static final ItemStack kineticWind = ItemUtils.simpleMetaStack("IC2:blockKineticGenerator", 0, 1);
    public static final ItemStack shaftIron = ItemUtils.simpleMetaStack("IC2:itemRecipePart", 11, 1);
    public static final ItemStack cableCopper = ItemUtils.simpleMetaStack("gregtech:gt.blockmachines", 1367, 1);
    public static final String plateRubber = "ore:plateRubber";

    // Machine Casings
    public static ItemStack elvCasing = ItemUtils.simpleMetaStack("gregtech:gt.blockcasings", 0, 1);
    public static final ItemStack lvCasing = ItemUtils.simpleMetaStack("gregtech:gt.blockcasings", 1, 1);
    public static final ItemStack mvCasing = ItemUtils.simpleMetaStack("gregtech:gt.blockcasings", 2, 1);
    public static final ItemStack hvCasing = ItemUtils.simpleMetaStack("gregtech:gt.blockcasings", 3, 1);
    public static final ItemStack evCasing = ItemUtils.simpleMetaStack("gregtech:gt.blockcasings", 4, 1);
    public static final ItemStack ivCasing = ItemUtils.simpleMetaStack("gregtech:gt.blockcasings", 5, 1);

    // GT Transformers
    public static final ItemStack elvTransformer = ItemUtils.simpleMetaStack("gregtech:gt.blockmachines", 20, 1);
    public static final ItemStack lvTransformer = ItemUtils.simpleMetaStack("gregtech:gt.blockmachines", 21, 1);
    public static final ItemStack mvTransformer = ItemUtils.simpleMetaStack("gregtech:gt.blockmachines", 22, 1);
    public static final ItemStack hvTransformer = ItemUtils.simpleMetaStack("gregtech:gt.blockmachines", 23, 1);
    public static final ItemStack evTransformer = ItemUtils.simpleMetaStack("gregtech:gt.blockmachines", 24, 1);

    // Compact Windmills
    public static final ItemStack elvWindmill = ItemUtils.simpleMetaStack("CompactWindmills:blockCompactWindmill", 0, 1);
    public static final ItemStack lvWindmill = ItemUtils.simpleMetaStack("CompactWindmills:blockCompactWindmill", 1, 1);
    public static final ItemStack mvWindmill = ItemUtils.simpleMetaStack("CompactWindmills:blockCompactWindmill", 2, 1);
    public static final ItemStack hvWindmill = ItemUtils.simpleMetaStack("CompactWindmills:blockCompactWindmill", 3, 1);
    public static final ItemStack evWindmill = ItemUtils.simpleMetaStack("CompactWindmills:blockCompactWindmill", 4, 1);

    // Compact Rotors
    public static final ItemStack rotor2 = ItemUtils.getItemStackFromFQRN("CompactWindmills:WOOL", 1);
    public static final ItemStack rotor1 = ItemUtils.getItemStackFromFQRN("CompactWindmills:WOOD", 1);
    public static final ItemStack rotor3 = ItemUtils.getItemStackFromFQRN("CompactWindmills:ALLOY", 1);
    public static final ItemStack rotor4 = ItemUtils.getItemStackFromFQRN("CompactWindmills:CARBON", 1);
    public static final ItemStack rotor5 = ItemUtils.getItemStackFromFQRN("CompactWindmills:IRIDIUM", 1);

    // IC2 Rotors
    public static final ItemStack rotorIC1 = ItemUtils.getItemStackFromFQRN("IC2:itemwoodrotor", 1);
    public static ItemStack rotorIC2 = ItemUtils.getItemStackFromFQRN("IC2:itemironrotor", 1);
    public static final ItemStack rotorIC3 = ItemUtils.getItemStackFromFQRN("IC2:itemsteelrotor", 1);
    public static ItemStack rotorIC4 = ItemUtils.getItemStackFromFQRN("IC2:itemwcarbonrotor", 1);
    public static final ItemStack rotorBlade1 = ItemUtils.simpleMetaStack("IC2:itemRecipePart", 7, 1);
    public static ItemStack rotorBlade2 = ItemUtils.simpleMetaStack("IC2:itemRecipePart", 8, 1);
    public static ItemStack rotorBlade3 = ItemUtils.simpleMetaStack("IC2:itemRecipePart", 10, 1);
    public static final ItemStack rotorBlade4 = ItemUtils.simpleMetaStack("IC2:itemRecipePart", 9, 1);

    // Plates
    public static final String plateTier1 = "ore:plateMagnalium";
    public static final String plateTier2 = "ore:plateStainlessSteel";
    public static final String plateTier3 = "ore:plateTitanium";
    public static final String plateTier4 = "ore:plateTungstenSteel";
    public static final String plateTier5 = "ore:plateNichrome";
    public static final String plateCarbon = "ore:plateAlloyCarbon";
    public static final String plateAlloy = "ore:plateAlloyAdvanced";

    // Circuits
    public static final String circuitTier1 = "ore:circuitGood";
    public static final String circuitTier2 = "ore:circuitAdvanced";
    public static final String circuitTier3 = "ore:circuitData";
    public static final String circuitTier4 = "ore:circuitElite";
    public static final String circuitTier5 = "ore:circuitMaster";

    // Wooden Rotor
    public static final ShapedRecipeObject Wooden_Rotor = new ShapedRecipeObject(
            rotorBlade1,
            "ore:plateAnyIron",
            "ore:plateAnyIron",
            "ore:screwAluminium",
            rotorIC1,
            "ore:screwAluminium",
            rotorBlade1,
            "plateAnyIron",
            rotorBlade1,
            rotor1);
    // Alloy Rotor
    public static final ShapedRecipeObject Alloy_Rotor = new ShapedRecipeObject(
            plateAlloy,
            plateAlloy,
            plateAlloy,
            plateAlloy,
            rotorIC3,
            plateAlloy,
            plateAlloy,
            plateAlloy,
            plateAlloy,
            rotor3);
    // Carbon Rotor
    public static final ShapedRecipeObject Carbon_Rotor = new ShapedRecipeObject(
            plateCarbon,
            rotorBlade4,
            plateCarbon,
            rotorBlade4,
            rotor3,
            rotorBlade4,
            plateCarbon,
            rotorBlade4,
            plateCarbon,
            rotor4);

    // Kinetic Wind Turbine
    public static final ShapedRecipeObject KWT = new ShapedRecipeObject(
            plateCarbon,
            shaftIron,
            plateCarbon,
            cableCopper,
            mvCasing,
            cableCopper,
            plateRubber,
            plateCarbon,
            plateRubber,
            kineticWind);

    // ELV Windmill
    public static final ShapedRecipeObject WM_ELV = new ShapedRecipeObject(
            circuitTier1,
            elvTransformer,
            circuitTier1,
            plateTier1,
            lvCasing,
            plateTier1,
            plateTier1,
            rotor1,
            plateTier1,
            elvWindmill);

    // LV Windmill
    public static final ShapedRecipeObject WM_LV = new ShapedRecipeObject(
            circuitTier2,
            lvTransformer,
            circuitTier2,
            plateTier2,
            mvCasing,
            plateTier2,
            plateTier2,
            rotor2,
            plateTier2,
            lvWindmill);

    // MV Windmill
    public static final ShapedRecipeObject WM_MV = new ShapedRecipeObject(
            circuitTier3,
            mvTransformer,
            circuitTier3,
            plateTier3,
            hvCasing,
            plateTier3,
            plateTier3,
            rotor3,
            plateTier3,
            mvWindmill);

    // HV Windmill
    public static final ShapedRecipeObject WM_HV = new ShapedRecipeObject(
            circuitTier4,
            hvTransformer,
            circuitTier4,
            plateTier4,
            evCasing,
            plateTier4,
            plateTier4,
            rotor4,
            plateTier4,
            hvWindmill);

    // EV Windmill
    public static final ShapedRecipeObject WM_EV = new ShapedRecipeObject(
            circuitTier5,
            evTransformer,
            circuitTier5,
            plateTier5,
            ivCasing,
            plateTier5,
            plateTier5,
            rotor5,
            plateTier5,
            evWindmill);

    public static void OreDict() {
        run();
    }

    private static final void run() {
        // RemoveRecipeQueue.add("CompactWindmills:WOOL");
        // RemoveRecipeQueue.add("CompactWindmills:WOOD");
        // RemoveRecipeQueue.add("CompactWindmills:ALLOY");
        // RemoveRecipeQueue.add("CompactWindmills:CARBON");
        // RemoveRecipeQueue.add("CompactWindmills:IRIDIUM");

        // Remove Recipes
        RemoveRecipeQueue.add(kineticWind);
        RemoveRecipeQueue.add(elvWindmill);
        RemoveRecipeQueue.add(lvWindmill);
        RemoveRecipeQueue.add(mvWindmill);
        RemoveRecipeQueue.add(hvWindmill);
        RemoveRecipeQueue.add(evWindmill);
        RemoveRecipeQueue.add(rotor1);
        RemoveRecipeQueue.add(rotor3);
        RemoveRecipeQueue.add(rotor4);

        // Add Recipes
        AddRecipeQueue.add(Wooden_Rotor);
        AddRecipeQueue.add(Alloy_Rotor);
        AddRecipeQueue.add(Carbon_Rotor);
        AddRecipeQueue.add(KWT);
        AddRecipeQueue.add(WM_ELV);
        AddRecipeQueue.add(WM_LV);
        AddRecipeQueue.add(WM_MV);
        AddRecipeQueue.add(WM_HV);
        AddRecipeQueue.add(WM_EV);
    }
}
