package gtPlusPlus.core.material.nuclear;

import gregtech.api.enums.Materials;
import gtPlusPlus.core.material.ELEMENT;
import gtPlusPlus.core.material.MISC_MATERIALS;
import gtPlusPlus.core.material.Material;
import gtPlusPlus.core.material.MaterialStack;
import gtPlusPlus.core.material.state.MaterialState;

public class FLUORIDES {

    public static final Material FLUORITE = new Material(
            "Fluorite (F)", // Material Name
            MaterialState.ORE, // State
            null, // Material Colour
            Materials.Fluorine.mMeltingPoint, // Melting Point in C
            Materials.Fluorine.mBlastFurnaceTemp, // Boiling Point in C
            -1, // Protons
            -1, // Neutrons
            false, // Uses Blast furnace?
            false, // Generate cells
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().CALCIUM, 16),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 32),
                    new MaterialStack(ELEMENT.getInstance().IRON, 4),
                    new MaterialStack(ELEMENT.getInstance().CARBON, 2) });

    // ThF4
    public static final Material THORIUM_TETRAFLUORIDE = new Material(
            "Thorium Tetrafluoride", // Material Name
            MaterialState.LIQUID, // State
            null, // Material Colour
            -1, // Melting Point in C
            -1, // Boiling Point in C
            -1, // Protons
            -1, // Neutrons
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().THORIUM232, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 4) });

    // ThF6
    public static final Material THORIUM_HEXAFLUORIDE = new Material(
            "Thorium Hexafluoride", // Material Name
            MaterialState.LIQUID, // State
            null, // Material Colour
            -1, // Melting Point in C
            -1, // Boiling Point in C
            -1, // Protons
            -1, // Neutrons
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().THORIUM232, 1),
                    new MaterialStack(ELEMENT.getInstance().THORIUM, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 12) });

    // UF4
    public static final Material URANIUM_TETRAFLUORIDE = new Material(
            "Uranium Tetrafluoride", // Material Name
            MaterialState.LIQUID, // State
            null, // Material Colour
            -1, // Melting Point in C
            -1, // Boiling Point in C
            -1, // Protons
            -1, // Neutrons
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().URANIUM233, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 4) });

    // UF6
    public static final Material URANIUM_HEXAFLUORIDE = new Material(
            "Uranium Hexafluoride", // Material Name
            MaterialState.LIQUID, // State
            null, // Material Colour
            -1, // Melting Point in C
            -1, // Boiling Point in C
            -1, // Protons
            -1, // Neutrons
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(FLUORIDES.URANIUM_TETRAFLUORIDE, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 2) });

    // ZrF4

    public static final Material ZIRCONIUM_TETRAFLUORIDE = new Material(
            "Zirconium Tetrafluoride", // Material Name
            MaterialState.LIQUID, // State
            null, // Texture Set (Autogenerated)
            0,
            null, // Material Colour
            -1,
            -1,
            -1,
            -1,
            false,
            "ZrF\u2084",
            -1,
            true,
            false,
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().ZIRCONIUM, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 4) });

    /*
     * public static final Material ZIRCONIUM_TETRAFLUORIDE = new Material( "Zirconium Tetrafluoride", //Material Name
     * MaterialState.LIQUID, //State null, //Material Colour -1, //Melting Point in C -1, //Boiling Point in C -1,
     * //Protons -1, //Neutrons false, //Uses Blast furnace? //Material Stacks with Percentage of required elements. new
     * MaterialStack[]{ new MaterialStack(ELEMENT.getInstance().ZIRCONIUM, 1), new
     * MaterialStack(ELEMENT.getInstance().FLUORINE, 4) });
     */

    // BeF2
    public static final Material BERYLLIUM_FLUORIDE = new Material(
            "Beryllium Fluoride", // Material Name
            MaterialState.LIQUID, // State
            null, // Material Colour
            -1,
            -1,
            -1,
            -1,
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().BERYLLIUM, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 2) });

    // LiF
    public static final Material LITHIUM_FLUORIDE = new Material(
            "Lithium Fluoride", // Material Name
            MaterialState.LIQUID, // State
            null, // Material Colour
            -1, // Melting Point in C
            -1, // Boiling Point in C
            -1, // Protons
            -1, // Neutrons
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().LITHIUM7, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 1) });

    // LFTR sub components

    // (NH4)HF2
    public static final Material AMMONIUM_BIFLUORIDE = new Material(
            "Ammonium Bifluoride", // Material Name
            MaterialState.PURE_LIQUID, // State
            null, // Material Colour
            126, // Melting Point in C
            240, // Boiling Point in C
            -1, // Protons
            -1,
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(MISC_MATERIALS.AMMONIUM, 1),
                    new MaterialStack(ELEMENT.getInstance().HYDROGEN, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 2) });

    // Be(OH)2
    public static final Material BERYLLIUM_HYDROXIDE = new Material(
            "Beryllium Hydroxide", // Material Name
            MaterialState.PURE_LIQUID, // State
            null, // Material Colour
            -1, // Melting Point in C
            -1, // Boiling Point in C
            -1, // Protons
            -1, // Neutrons
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().BERYLLIUM, 1),
                    new MaterialStack(MISC_MATERIALS.HYDROXIDE, 2) });

    // (NH4)2Be(OH)2 / (NH4)2BeF4
    public static final Material AMMONIUM_TETRAFLUOROBERYLLATE = new Material(
            "Ammonium Tetrafluoroberyllate", // Material Name
            MaterialState.PURE_LIQUID, // State
            null, // Material Colour
            280, // Melting Point in C
            -1, // Boiling Point in C
            -1, // Protons
            -1,
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(MISC_MATERIALS.AMMONIUM, 2),
                    new MaterialStack(FLUORIDES.BERYLLIUM_HYDROXIDE, 1) });

    // LFTR Output
    public static final Material NEPTUNIUM_HEXAFLUORIDE = new Material(
            "Neptunium Hexafluoride", // Material Name
            MaterialState.GAS, // State
            null, // Material Colour
            -1, // Melting Point in C
            -1, // Boiling Point in C
            -1, // Protons
            -1, // Neutrons
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().NEPTUNIUM, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 6) });

    public static final Material TECHNETIUM_HEXAFLUORIDE = new Material(
            "Technetium Hexafluoride", // Material Name
            MaterialState.GAS, // State
            null, // Material Colour
            -1, // Melting Point in C
            -1, // Boiling Point in C
            -1, // Protons
            -1, // Neutrons
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().TECHNETIUM, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 6) });

    public static final Material SELENIUM_HEXAFLUORIDE = new Material(
            "Selenium Hexafluoride", // Material Name
            MaterialState.GAS, // State
            null, // Material Colour
            -1, // Melting Point in C
            -1, // Boiling Point in C
            -1, // Protons
            -1, // Neutrons
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().SELENIUM, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 6) });

    public static final Material SODIUM_FLUORIDE = new Material(
            "Sodium Fluoride", // Material Name
            MaterialState.PURE_LIQUID, // State
            null, // Material Colour
            -1, // Melting Point in C
            -1, // Boiling Point in C
            -1, // Protons
            -1, // Neutrons
            false, // Uses Blast furnace?
            // Material Stacks with Percentage of required elements.
            new MaterialStack[] { new MaterialStack(ELEMENT.getInstance().SODIUM, 1),
                    new MaterialStack(ELEMENT.getInstance().FLUORINE, 1) });

    private static final FLUORIDES INSTANCE = new FLUORIDES();

    public static FLUORIDES getInstance() {
        return INSTANCE;
    }
}
