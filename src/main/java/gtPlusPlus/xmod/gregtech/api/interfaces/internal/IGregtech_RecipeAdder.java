package gtPlusPlus.xmod.gregtech.api.interfaces.internal;

import gregtech.api.enums.Materials;
import gtPlusPlus.core.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IGregtech_RecipeAdder {
    /**
     * Adds a Coke Oven Recipe
     *
     * @param aInput1       = first Input (not null, and respects StackSize)
     * @param aInputb       = second Input (can be null, and respects StackSize)
     * @param aFluidOutput      = Output of the Creosote (not null, and respects StackSize)
     * @param aFluidInput   = fluid Input (can be null, and respects StackSize)
     * @param aOutput       = Output of the Coal/coke (can be null, and respects StackSize)
     * @param aDuration 	= Duration (must be >= 0)
     * @param aEUt			= EU needed for heating up (must be >= 0)
     * @return true if the Recipe got added, otherwise false.
     */
    // public boolean addCokeOvenRecipe(boolean aOptimize, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecial,
    // FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt, int aSpecialValue);
    boolean addCokeOvenRecipe(
            ItemStack aInput1,
            ItemStack aInput2,
            FluidStack aFluidInput,
            FluidStack aFluidOutput,
            ItemStack aOutput,
            int aDuration,
            int aEUt);

    boolean addCokeOvenRecipe(
            int aCircuit,
            ItemStack aInput2,
            FluidStack[] aFluidInputs,
            FluidStack[] aFluidOutputs,
            ItemStack[] aOutputs,
            int aDuration,
            int aEUt);

    boolean addCokeOvenRecipe(
            ItemStack aInput1,
            ItemStack aInput2,
            FluidStack[] aFluidInputs,
            FluidStack[] aFluidOutputs,
            ItemStack[] aOutputs,
            int aDuration,
            int aEUt);

    boolean addFuel(ItemStack aInput1, ItemStack aOutput1, int aEU, int aType);

    /**
     * Adds a Matter Fabricator Recipe
     *
     * @param aFluidOutput   = Output of the UU-Matter (not null, and respects StackSize)
     * @param aFluidInput   = fluid Input (can be UU_Amp or null, and respects StackSize)
     * @param aDuration 	= Duration (must be >= 0)
     * @param aEUt			= EU needed for heating up (must be >= 0)
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addMatterFabricatorRecipe(FluidStack aFluidInput, FluidStack aFluidOutput, int aDuration, int aEUt);

    /**
     * Adds a Matter Fabricator Recipe
     *
     * @param aItemInput = ItemStack Input, can be null I assume.
     * @param aFluidInput   = fluid Input (can be UU_Amp or null, and respects StackSize)
     * @param aFluidOutput   = Output of the UU-Matter (not null, and respects StackSize)
     * @param aDuration 	= Duration (must be >= 0)
     * @param aEUt			= EU needed for heating up (must be >= 0)
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addMatterFabricatorRecipe(
            ItemStack aItemInput, FluidStack aFluidInput, FluidStack aFluidOutput, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Dehydrator. (up to 9 Outputs)
     *
     * @param aInput   = Input itemstack (not null, and respects StackSize)
     * @param aFluidInput   = fluid Input (can be UU_Amp or null, and respects StackSize)
     * @param aOutputItems   = Itemstack[] (not null, and respects StackSize)
     * @param aDuration 	= Duration (must be >= 0)
     * @param aEUt			= EU needed for heating up (must be >= 0)
     * @return true if the Recipe got added, otherwise false.
     */

    // public boolean addDehydratorRecipe(ItemStack aInput, FluidStack aFluid, ItemStack[] aOutputItems, int aDuration,
    // int aEUt);
    /*public boolean addDehydratorRecipe(FluidStack aFluid, FluidStack aOutputFluid, ItemStack[] aOutputItems, int aDuration, int aEUt);*/
    /*public boolean addDehydratorRecipe(ItemStack aItemA, ItemStack aItemB, ItemStack[] aOutputItems, int aDuration, int aEUt);
    public boolean addDehydratorRecipe(ItemStack aItemA, ItemStack aItemB, FluidStack aFluid, ItemStack[] aOutputItems, FluidStack aOutputFluid, int aDuration, int aEUt);*/

    /**
     * Adds a Recipe for the Dehydrator. (up to 9 Outputs)
     *
     * @param aInput   = ItemStack[] (not null, and respects StackSize)
     * @param aFluidInput   = fluid Input (can be UU_Amp or null, and respects StackSize)
     * @param aFluidOutput   = Output of the UU-Matter (not null, and respects StackSize)
     * @param aOutputItems   = ItemStack[] (not null, and respects StackSize)
     * @param aChances 	= Output Change (can be == 0)
     * @param aDuration 	= Duration (must be >= 0)
     * @param aEUt			= EU needed for heating up (must be >= 0)
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addDehydratorRecipe(
            ItemStack[] aInput,
            FluidStack aFluidInput,
            FluidStack aFluidOutput,
            ItemStack[] aOutputItems,
            int[] aChances,
            int aDuration,
            int aEUt);

    /**
     * Adds a Recipe for the Alloy Blast Smelter. (up to 9 Inputs)
     *
     * @param aInput   = ItemStack[] (not null, and respects StackSize)
     * @param aFluidOutput   = Output of the Molten Metal (not null, and respects StackSize)
     * @param aChances 	= Output Chance (can be == 0)
     * @param aDuration 	= Duration (must be >= 0)
     * @param aEUt			= EU per tick needed for heating up (must be >= 0)
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addBlastSmelterRecipe(ItemStack[] aInput, FluidStack aOutput, int aChance, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Alloy Blast Smelter. (up to 9 Inputs)
     *
     * @param aInput   = ItemStack[] (not null, and respects StackSize)
     * @param aFluidInput   = Input of a fluid (can be null, and respects StackSize)
     * @param aFluidOutput   = Output of the Molten Metal (not null, and respects StackSize)
     * @param aChances 	= Output Chance (can be == 0)
     * @param aDuration 	= Duration (must be >= 0)
     * @param aEUt			= EU per tick needed for heating up (must be >= 0)
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addBlastSmelterRecipe(
            ItemStack[] aInput, FluidStack aInputFluid, FluidStack aOutput, int aChance, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Alloy Blast Smelter. (up to 9 Inputs)
     *
     * @param aInput   = ItemStack[] (not null, and respects StackSize)
     * @param aFluidInput   = Input of a fluid (can be null, and respects StackSize)
     * @param aFluidOutput   = Output of the Molten Metal (not null, and respects StackSize)
     * @param aOutputStack  = Item Output (Can be null)
     * @param aChances 	= Output Chance (can be == 0)
     * @param aDuration 	= Duration (must be >= 0)
     * @param aEUt			= EU per tick needed for heating up (must be >= 0)
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addBlastSmelterRecipe(
            ItemStack[] aInput,
            FluidStack aInputFluid,
            FluidStack aOutput,
            ItemStack[] aOutputStack,
            int[] aChance,
            int aDuration,
            int aEUt);

    boolean addBlastSmelterRecipe(
            ItemStack[] aInput,
            FluidStack aInputFluid,
            FluidStack aOutput,
            int aChance,
            int aDuration,
            int aEUt,
            int aSpecialValue);

    boolean addBlastSmelterRecipe(
            ItemStack[] aInput,
            FluidStack aInputFluid,
            FluidStack aOutput,
            ItemStack[] aOutputStack,
            int[] aChance,
            int aDuration,
            int aEUt,
            int aSpecialValue,
            boolean aOptimizeRecipe);

    /**
     * Adds a Recipe for the LFTRr. (up to 9 Inputs)
     *
     * @param aInput   = ItemStack[] (not null, and respects StackSize)
     * @param aFluidInput   = Input of a fluid (can be null, and respects StackSize)
     * @param aFluidOutput   = Output of the Molten Salts (not null, and respects StackSize)
     * @param aOutputStack  = Item Output (Can be null)
     * @param aChances 	= Output Chance (can be == 0)
     * @param aDuration 	= Duration (must be >= 0)
     * @param aEUt			= EU per tick needed for heating up (must be >= 0)
     * @param aSpecialValue			= Power produced in EU/t per dynamo
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addBlastSmelterRecipe(
            ItemStack[] aInput,
            FluidStack aInputFluid,
            FluidStack aOutput,
            ItemStack[] aOutputStack,
            int[] aChance,
            int aDuration,
            int aEUt,
            int aSpecialValue);

    boolean addLFTRRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt);

    boolean addLFTRRecipe(
            ItemStack aInput1, FluidStack aInput2, ItemStack aOutput1, FluidStack aOutput2, int aDuration, int aEUt);

    boolean addLFTRRecipe(FluidStack aInput1, FluidStack aInput2, FluidStack aOutput1, int aDuration, int aEUt);

    /**
     * Adds a custom Semifluid fuel for the GT++ SemiFluid Generators.
     * @param aFuelItem - A Fluidstack to be consumed.
     * @param aFuelValue - Fuel value in thousands (1 = 1000)
     * @return - Was the Fuel added?
     */
    boolean addSemifluidFuel(FluidStack aFuelItem, int aFuelValue);

    /**
     * Adds a custom Semifluid fuel for the GT++ SemiFluid Generators.
     * @param aFuelItem - A Fluidstack to be consumed.
     * @param aFuelValue - Fuel value in thousands (1 = 1000)
     * @return - Was the Fuel added?
     */
    boolean addSemifluidFuel(ItemStack aFuelItem, int aFuelValue);

    boolean addFissionFuel(
            FluidStack aInput1,
            FluidStack aInput2,
            FluidStack aInput3,
            FluidStack aInput4,
            FluidStack aInput5,
            FluidStack aInput6,
            FluidStack aInput7,
            FluidStack aInput8,
            FluidStack aInput9,
            FluidStack aOutput1,
            FluidStack aOutput2,
            int aDuration,
            int aEUt);

    boolean addFissionFuel(
            boolean aOptimise,
            FluidStack aInput1,
            FluidStack aInput2,
            FluidStack aInput3,
            FluidStack aInput4,
            FluidStack aInput5,
            FluidStack aInput6,
            FluidStack aInput7,
            FluidStack aInput8,
            FluidStack aInput9,
            FluidStack aOutput1,
            FluidStack aOutput2,
            int aDuration,
            int aEUt);

    boolean addCyclotronRecipe(
            ItemStack aInput,
            FluidStack aFluidInput,
            ItemStack[] aOutputs,
            FluidStack aFluidOutput,
            int[] aChances,
            int aDuration,
            int aEUt,
            int aSpecialValue);

    boolean addCyclotronRecipe(
            ItemStack[] aInputs,
            FluidStack aFluidInput,
            ItemStack[] aOutput,
            FluidStack aFluidOutput,
            int[] aChances,
            int aDuration,
            int aEUt,
            int aSpecialValue);

    boolean addMixerRecipe(
            ItemStack aInput1,
            ItemStack aInput2,
            ItemStack aInput3,
            ItemStack aInput4,
            FluidStack aFluidInput,
            FluidStack aFluidOutput,
            ItemStack aOutput1,
            ItemStack aOutput2,
            ItemStack aOutput3,
            ItemStack aOutput4,
            int aDuration,
            int aEUt);

    /**
     * Adds a Recipe for the Machine Component Assembler. (up to 6 Inputs)
     *
     * @param aInputs   = ItemStack[] (not null, and respects StackSize)
     * @param aFluidInput   = Input of a fluid (can be null, and respects StackSize)
     * @param aOutput1   = Output ItemStack (not null, and respects StackSize)
     * @param aDuration 	= Duration (must be >= 0)
     * @param aEUt			= EU per tick needed for heating up (must be >= 0)
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addComponentMakerRecipe(
            ItemStack[] aInputs, FluidStack aFluidInput, ItemStack aOutput1, int aDuration, int aEUt);

    boolean addMultiblockCentrifugeRecipe(
            ItemStack[] aInputs,
            FluidStack[] aFluidInputs,
            FluidStack[] aFluidOutputs,
            ItemStack[] aOutputs,
            int[] aChances,
            int aDuration,
            int aEUtick,
            int aSpecial);

    boolean addMultiblockElectrolyzerRecipe(
            ItemStack[] aInputs,
            FluidStack[] aFluidInputs,
            FluidStack[] aFluidOutputs,
            ItemStack[] aOutputs,
            int[] aChances,
            int aDuration,
            int aEUtick,
            int aSpecial);

    boolean addAdvancedFreezerRecipe(
            ItemStack[] aInputs,
            FluidStack[] aFluidInputs,
            FluidStack[] aFluidOutputs,
            ItemStack[] aOutputs,
            int[] aChances,
            int aDuration,
            int aEUtick,
            int aSpecial);

    boolean addAssemblerRecipeWithOreDict(
            Object aInput1, int aAmount1, Object aInput2, int aAmount2, ItemStack aOutput, int a1, int a2);

    boolean addAssemblerRecipeWithOreDict(
            Object aInput1,
            int aAmount1,
            Object aInput2,
            int aAmount2,
            FluidStack aInputFluid,
            ItemStack aOutput,
            int a1,
            int a2);

    boolean addSixSlotAssemblingRecipe(
            ItemStack[] aInputs, FluidStack aInputFluid, ItemStack aOutput1, int aDuration, int aEUt);
    /**
     * Adds an Assemblyline Recipe
     *
     * @param aInputs      must be != null, 4-16 inputs
     * @param aFluidInputs 0-4 fluids
     * @param aOutput1     must be != null
     * @param aDuration    must be > 0
     * @param aEUt         should be > 0
     */
    boolean addAssemblylineRecipe(
            ItemStack aResearchItem,
            int aResearchTime,
            ItemStack[] aInputs,
            FluidStack[] aFluidInputs,
            ItemStack aOutput,
            int aDuration,
            int aEUt);

    /**
     * Adds a Assemblyline Recipe
     *
     * @param aInputs elements should be: ItemStack for single item;
     *                ItemStack[] for multiple equivalent items;
     *                {OreDict, amount} for oredict.
     */
    boolean addAssemblylineRecipe(
            ItemStack aResearchItem,
            int aResearchTime,
            Object[] aInputs,
            FluidStack[] aFluidInputs,
            ItemStack aOutput1,
            int aDuration,
            int aEUt);

    boolean addChemicalRecipe(
            ItemStack input1,
            ItemStack input2,
            FluidStack inputFluid,
            FluidStack outputFluid,
            ItemStack output,
            int time,
            int eu);

    boolean addChemicalRecipe(
            ItemStack input1,
            ItemStack input2,
            FluidStack inputFluid,
            FluidStack outputFluid,
            ItemStack output,
            Object object,
            int time,
            int eu);

    boolean addChemicalRecipe(
            ItemStack input1,
            ItemStack input2,
            FluidStack inputFluid,
            FluidStack outputFluid,
            ItemStack output,
            ItemStack object,
            int time);

    boolean addChemicalRecipe(
            ItemStack input1,
            ItemStack input2,
            int aCircuit,
            FluidStack inputFluid,
            FluidStack outputFluid,
            ItemStack output,
            ItemStack output2,
            int time,
            int eu);

    boolean addMultiblockChemicalRecipe(
            ItemStack[] itemStacks,
            FluidStack[] fluidStacks,
            FluidStack[] fluidStacks2,
            ItemStack[] outputs,
            int time,
            int eu);

    boolean addCompressorRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt);

    boolean addBrewingRecipe(
            ItemStack aIngredient, FluidStack aInput, FluidStack aOutput, int aTime, int aEu, boolean aHidden);

    boolean addBrewingRecipe(
            int aCircuit, FluidStack aInput, FluidStack aOutput, int aTime, int aEu, boolean aHidden);

    boolean addSmeltingAndAlloySmeltingRecipe(ItemStack aDust, ItemStack aOutput);

    boolean addFluidExtractionRecipe(ItemStack input, FluidStack output, int aTime, int aEu);

    boolean addFluidExtractionRecipe(
            ItemStack aContainer, ItemStack aFullContainer, FluidStack rFluidOut, int aTime, int aEu);

    boolean addFluidCannerRecipe(ItemStack aContainer, ItemStack aFullContainer, FluidStack rFluidIn);

    boolean addFluidCannerRecipe(
            ItemStack aContainer, ItemStack aFullContainer, FluidStack rFluidIn, FluidStack rFluidOut);

    boolean addFluidCannerRecipe(
            ItemStack aContainer,
            ItemStack aFullContainer,
            FluidStack rFluidIn,
            FluidStack rFluidOut,
            int aTime,
            int aEu);

    /**
     * Adds a Fusion reactor Recipe
     *
     * @param aInput1                        = first Input (not null, and respects StackSize)
     * @param aInput2                        = second Input (not null, and respects StackSize)
     * @param plasma                        = Output of the Fusion (can be null, and respects StackSize)
     * @param aFusionDurationInTicks         = How many ticks the Fusion lasts (must be > 0)
     * @param aEu           = The EU generated per Tick (can even be negative!)
     * @param aSpecial = EU needed for heating the Reactor up (must be >= 0)
     */
    boolean addFusionReactorRecipe(
            ItemStack aInputStackA,
            ItemStack aInputStackB,
            FluidStack plasma,
            int aFusionDurationInTicks,
            int aEu,
            int aSpecial);
    /**
     * Adds a Fusion reactor Recipe
     *
     * @param aInput1                        = first Input (not null, and respects StackSize)
     * @param aInput2                        = second Input (not null, and respects StackSize)
     * @param plasma                        = Output of the Fusion (can be null, and respects StackSize)
     * @param aOutputChance = chance to output plasma (can be 0)
     * @param aFusionDurationInTicks         = How many ticks the Fusion lasts (must be > 0)
     * @param aEu           = The EU generated per Tick (can even be negative!)
     * @param aSpecial = EU needed for heating the Reactor up (must be >= 0)
     */
    boolean addFusionReactorRecipe(
            FluidStack aInputStackA,
            FluidStack aInputStackB,
            FluidStack plasma,
            int aOutputChance,
            int aFusionDurationInTicks,
            int aEu,
            int aSpecial);
    /**
     * Adds a Fusion reactor Recipe
     *
     * @param aInput1                        = first Input (not null, and respects StackSize)
     * @param aInput2                        = second Input (not null, and respects StackSize)
     * @param plasma                        = Output of the Fusion (can be null, and respects StackSize)
     * @param aOutputChance = chance to output plasma (can be 0)
     * @param aFusionDurationInTicks         = How many ticks the Fusion lasts (must be > 0)
     * @param aEu           = The EU generated per Tick (can even be negative!)
     * @param aSpecial = EU needed for heating the Reactor up (must be >= 0)
     */
    boolean addFusionReactorRecipe(
            ItemStack aInputStackA,
            ItemStack aInputStackB,
            FluidStack plasma,
            int aOutputChance,
            int aFusionDurationInTicks,
            int aEu,
            int aSpecial);

    boolean addVacuumFurnaceRecipe(
            ItemStack aInput1,
            ItemStack aInput2,
            FluidStack aFluidInput,
            FluidStack aFluidOutput,
            ItemStack aOutput1,
            ItemStack aOutput2,
            int aDuration,
            int aEUt,
            int aLevel);

    boolean addVacuumFurnaceRecipe(
            ItemStack[] aInputs,
            FluidStack[] aFluidInputs,
            ItemStack[] aOutputs,
            FluidStack[] aFluidOutputs,
            int aDuration,
            int aEUt,
            int aLevel);

    boolean addUvLaserRecipe(ItemStack aInput1, ItemStack aOutput, int time, long eu);

    boolean addIrLaserRecipe(ItemStack aInput1, ItemStack aOutput, int time, long eu);

    boolean addChemicalPlantRecipe(
            ItemStack[] aInputs,
            FluidStack[] aInputFluids,
            ItemStack[] aOutputs,
            FluidStack[] aFluidOutputs,
            int time,
            long eu,
            int aTier);

    boolean addChemicalPlantRecipe(
            ItemStack[] aInputs,
            FluidStack[] aInputFluids,
            ItemStack[] aOutputs,
            FluidStack[] aFluidOutputs,
            int[] aChances,
            int time,
            long eu,
            int aTier);

    boolean addBlastRecipe(
            ItemStack[] aInputs,
            FluidStack[] aInputFluids,
            ItemStack[] aOutputs,
            FluidStack[] aFluidOutputs,
            int time,
            long eu,
            int aHeat);

    boolean addPyrolyseRecipe(
            ItemStack aInput,
            FluidStack aFluidInput,
            int intCircuit,
            ItemStack aOutput,
            FluidStack aFluidOutput,
            int aDuration,
            int aEUt);

    boolean addExtractorRecipe(ItemStack aInput, ItemStack aOutput, int aDuration, int aEUt);

    boolean addExtractorRecipe(ItemStack aInput, ItemStack aOutput, int aChance, int aDuration, int aEUt);

    boolean addDistilleryRecipe(
            ItemStack aCircuit,
            FluidStack aInput,
            FluidStack aOutput,
            ItemStack aSolidOutput,
            int aDuration,
            int aEUt,
            boolean aHidden);

    boolean addPulverisationRecipe(
            final ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, final ItemStack aOutput3);

    boolean addMillingRecipe(Materials aMat, int aEU);

    boolean addMillingRecipe(Material aMat, int aEU);

    boolean addFlotationRecipe(
            Materials aMat,
            ItemStack aXanthate,
            FluidStack[] aInputFluids,
            FluidStack[] aOutputFluids,
            int aTime,
            int aEU);

    boolean addFlotationRecipe(
            Material aMat,
            ItemStack aXanthate,
            FluidStack[] aInputFluids,
            FluidStack[] aOutputFluids,
            int aTime,
            int aEU);

    boolean addpackagerRecipe(
            ItemStack aRecipeType, ItemStack aInput1, ItemStack aInput2, ItemStack aOutputStack1);

    boolean addFuelForRTG(ItemStack aFuelPellet, int aFuelDays, int aVoltage);

    boolean addColdTrapRecipe(
            int aCircuit,
            ItemStack aInput,
            FluidStack aFluidInput,
            ItemStack[] aOutputs,
            int[] aChances,
            FluidStack aFluidOutput,
            int aTime,
            int aEU);

    boolean addReactorProcessingUnitRecipe(
            ItemStack aInput1,
            ItemStack aInput2,
            FluidStack aFluidInput,
            ItemStack[] aOutputs,
            int[] aChances,
            FluidStack aFluidOutput,
            int aTime,
            int aEU);

    boolean addFluidHeaterRecipe(
            ItemStack aCircuit, FluidStack aInput, FluidStack aOutput, int aDuration, int aEUt);

    boolean addVacuumFreezerRecipe(ItemStack aInput, ItemStack aOutput, int aDuration, int aEU);

    boolean addMolecularTransformerRecipe(ItemStack aInput, ItemStack aOutput, int aDuration, int aEU);

    boolean addMolecularTransformerRecipe(
            ItemStack aInput, ItemStack aOutput, int aDuration, int aEU, int aAmps);
}
