package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.advanced;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.enums.GT_HatchElement.Energy;
import static gregtech.api.enums.GT_HatchElement.InputBus;
import static gregtech.api.enums.GT_HatchElement.Maintenance;
import static gregtech.api.enums.GT_HatchElement.Muffler;
import static gregtech.api.enums.GT_HatchElement.OutputBus;
import static gregtech.api.util.GT_StructureUtility.buildHatchAdder;

import java.util.ArrayList;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IItemSource;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTech_API;
import gregtech.api.enums.TAE;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.util.GTPP_Recipe;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Recipe;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GregtechMetaTileEntity_Adv_AlloyBlastSmelter extends GregtechMeta_MultiBlockBase<GregtechMetaTileEntity_Adv_AlloyBlastSmelter> implements ISurvivalConstructable {

	private int mCasing;
	private IStructureDefinition<GregtechMetaTileEntity_Adv_AlloyBlastSmelter> STRUCTURE_DEFINITION = null;


	public GregtechMetaTileEntity_Adv_AlloyBlastSmelter(final int aID, final String aName, final String aNameRegional) {
		super(aID, aName, aNameRegional);
	}

	public GregtechMetaTileEntity_Adv_AlloyBlastSmelter(final String aName) {
		super(aName);
	}

	@Override
	public IMetaTileEntity newMetaEntity(final IGregTechTileEntity aTileEntity) {
		return new GregtechMetaTileEntity_Adv_AlloyBlastSmelter(this.mName);
	}

	@Override
	public String getMachineType() {
		return "Fluid Alloy Cooker";
	}

	@Override
	protected GT_Multiblock_Tooltip_Builder createTooltip() {
		GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
		tt.addMachineType(getMachineType())
		.addInfo("Controller Block for the Refined Amalgam Foundry")
		.addInfo("Can perform a maximum of "+getMaxParallelRecipes()+" recipes in parallel")
		.addInfo("Allows Complex GT++ alloys to be created")
		.addInfo("Accepts only one Energy Hatch")
		.addInfo("Each input bus can support a unique Circuit")
		.addPollutionAmount(getPollutionPerSecond(null))
		.addSeparator()
		.beginStructureBlock(5, 7, 5, true) // @Steelux TODO
		.addController("Bottom Center")
		.addCasingInfo("Blast Smelter Casings", 10)
		.addCasingInfo("Blast Smelter Heat Containment Coils", 16)
		.addInputBus("Any Casing", 1)
		.addInputHatch("Any Casing", 1)
		.addOutputHatch("Any Casing", 1)
		.addEnergyHatch("Any Casing", 1)
		.addMaintenanceHatch("Any Casing", 1)
		.addMufflerHatch("Any Casing", 1)
		.toolTipFinisher(CORE.GT_Tooltip_Builder);
		return tt;
	}

	@Override
	public IStructureDefinition<GregtechMetaTileEntity_Adv_AlloyBlastSmelter> getStructureDefinition() {
		if (this.STRUCTURE_DEFINITION == null) {
			this.STRUCTURE_DEFINITION = StructureDefinition.<GregtechMetaTileEntity_Adv_AlloyBlastSmelter>builder()
					.addShape(this.mName, new String[][]{
							{"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","   AAAABAAAA   ","   ABBA ABBA   ","   ABA   ABA   ","   AA     AA   ","   A       A   ","   A       A   ","  AAA     AAA  "},
							{"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","  ABBB   BBBA  ","               ","               ","               ","               ","               ","   A       A   "},
							{"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","               "," AB         BA ","               ","               ","               ","               ","               ","               "},
							{"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","               ","               ","AB           BA","               ","               ","               ","               ","               ","               "},
							{"      DDD      ","      EEE      ","      EEE      ","      EEE      ","      DDD      ","      EEE      ","      DDD      ","      EEE      ","      EEE      ","      EEE      ","      DDD      ","      EEE      ","      EEE      ","      EEE      ","AB    DDD    BA","      EEE      ","      DDD      ","      EEE      ","      EEE      ","      EEE      ","A     H~H     A"},
							{"     DMMMD     ","     ECCCE     ","     ECCCE     ","     ECCCE     ","     D   D     ","     ECCCE     ","     D   D     ","     ECCCE     ","     ECCCE     ","     ECCCE     ","     D   D     ","     ECCCE     ","     ECCCE     ","     ECCCE     ","AB   D   D   BA","     ECCCE     ","     D   D     ","     ECCCE     ","A    ECCCE    A","A    ECCCE    A","A    HHHHH    A"},
							{"    DMMMMMD    ","    ECCCCCE    ","    EC   CE    ","    EC   CE    ","    D     D    ","    EC   CE    ","    D     D    ","    EC   CE    ","    EC   CE    ","    EC   CE    ","    D     D    ","   AEC   CEA   ","  A EC   CE A  "," A  EC   CE  A ","A   D     D   A","A   EC   CE   A","A   D     D   A","A   EC   CE   A","B   EC   CE   B","B   EC   CE   B","A   HHHHHHH   A"},
							{"    DMMMMMD    ","    ECCCCCE    ","    EC   CE    ","    EC   CE    ","    D     D    ","    EC   CE    ","    D     D    ","    EC   CE    ","    EC   CE    ","    EC   CE    ","    D     D    ","   BEC   CEB   ","  B EC   CE B  "," B  EC   CE  B ","B   D     D   B","B   EC   CE   B","B   D     D   B","B   EC   CE   B","B   EC   CE   B","B   EC   CE   B","A   HHHHHHH   A"},
							{"    DMMMMMD    ","    ECCCCCE    ","    EC   CE    ","    EC   CE    ","    D     D    ","    EC   CE    ","    D     D    ","    EC   CE    ","    EC   CE    ","    EC   CE    ","    D     D    ","   AEC   CEA   ","  A EC   CE A  "," A  EC   CE  A ","A   D     D   A","A   EC   CE   A","A   D     D   A","A   EC   CE   A","B   EC   CE   B","B   EC   CE   B","A   HHHHHHH   A"},
							{"     DMMMD     ","     ECCCE     ","     ECCCE     ","     ECCCE     ","     D   D     ","     ECCCE     ","     D   D     ","     ECCCE     ","     ECCCE     ","     ECCCE     ","     D   D     ","     ECCCE     ","     ECCCE     ","     ECCCE     ","AB   D   D   BA","     ECCCE     ","     D   D     ","     ECCCE     ","A    ECCCE    A","A    ECCCE    A","A    HHHHH    A"},
							{"      DDD      ","      EEE      ","      EEE      ","      EEE      ","      DDD      ","      EEE      ","      DDD      ","      EEE      ","      EEE      ","      EEE      ","      DDD      ","      EEE      ","      EEE      ","      EEE      ","AB    DDD    BA","      EEE      ","      DDD      ","      EEE      ","      EEE      ","      EEE      ","A     HHH     A"},
							{"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","               ","               ","AB           BA","               ","               ","               ","               ","               ","               "},
							{"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","               "," AB         BA ","               ","               ","               ","               ","               ","               "},
							{"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","      ABA      ","  ABBB   BBBA  ","               ","               ","               ","               ","               ","               "},
							{"               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","               ","   AAAABAAAA   ","      ABA      ","      ABA      ","      ABA      ","     ABBBA     ","     ABBBA     ","    AAAAAAA    "},
					})
					.addElement(
							'A',
							ofChain(
									ofBlock(ModBlocks.blockSpecialMultiCasings2, 2),
									ofBlock(ModBlocks.blockSpecialMultiCasings2, 3)
									)
							)
					.addElement(
							'B',
							ofChain(
									ofBlock(ModBlocks.blockCasings4Misc, 2),
									ofBlock(ModBlocks.blockCasingsMisc, 8)
									)
							)
					.addElement(
							'C',
							ofBlock(
									ModBlocks.blockSpecialMultiCasings, 15
							)
					)
					.addElement(
							'D',
							ofBlock(
									ModBlocks.blockCasings2Misc, 12
							)
					)
					.addElement(
							'E',
							ofChain(
									ofBlock(ModBlocks.blockSpecialMultiCasings, 13),
									ofBlock(ModBlocks.blockCasings3Misc, 15)
									)
					)
					.addElement(
							'H',
							buildHatchAdder(GregtechMetaTileEntity_Adv_AlloyBlastSmelter.class)
									.atLeast(InputBus, OutputBus, Maintenance, Energy)
									.casingIndex(TAE.GTPP_INDEX(29))
									.dot(1)
									.buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(ModBlocks.blockCasings2Misc, 12)))
					)
					.addElement(
							'M',
							buildHatchAdder(GregtechMetaTileEntity_Adv_AlloyBlastSmelter.class)
									.atLeast(Muffler)
									.casingIndex(TAE.GTPP_INDEX(29))
									.dot(2)
									.buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(ModBlocks.blockCasings2Misc, 12)))
					)
					.build();
		} //ofBlockUnlocalizedName("IC2", "blockAlloyGlass", 0, true)
		return this.STRUCTURE_DEFINITION;
	}

	@Override
	public void construct(ItemStack stackSize, boolean hintsOnly) {
		buildPiece(this.mName , stackSize, hintsOnly, 7, 20, 4);
	}

	@Override
	public int survivalConstruct(ItemStack stackSize, int elementBudget, IItemSource source, EntityPlayerMP actor) {
		if (this.mMachine) return -1;
		return survivialBuildPiece(this.mName, stackSize, 7, 20, 4, elementBudget, source, actor, false, true);
	}

	@Override
	public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
		this.mCasing = 0;
		return checkPiece(this.mName, 7, 20, 4) && this.mCasing >= 10 && this.mEnergyHatches.size() == 1 && checkHatch();
	}

	@Override
	public String getSound() {
		return GregTech_API.sSoundList.get(Integer.valueOf(208));
	}

	@Override
	protected IIconContainer getActiveOverlay() {
		return TexturesGtBlock.Overlay_Machine_Controller_Advanced_Active;
	}

	@Override
	protected IIconContainer getInactiveOverlay() {
		return TexturesGtBlock.Overlay_Machine_Controller_Advanced;
	}

	@Override
	protected int getCasingTextureId() {
		return 84;
	}

	@Override
	public boolean hasSlotInGUI() {
		return false;
	}

	@Override
	public String getCustomGUIResourceName() {
		return null;
	}

	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GTPP_Recipe.GTPP_Recipe_Map.sAlloyBlastSmelterRecipes;
	}

	@Override
	public boolean isCorrectMachinePart(final ItemStack aStack) {
		return true;
	}

	@Override
	public boolean checkRecipe(final ItemStack aStack) {
		for (GT_MetaTileEntity_Hatch_InputBus tBus : this.mInputBusses) {
			ArrayList<ItemStack> tBusItems = new ArrayList<ItemStack>();
			tBus.mRecipeMap = getRecipeMap();
			if (isValidMetaTileEntity(tBus)) {
				for (int i = tBus.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
					if (tBus.getBaseMetaTileEntity().getStackInSlot(i) != null)
						tBusItems.add(tBus.getBaseMetaTileEntity().getStackInSlot(i));
				}
			}
			ItemStack[] inputs = new ItemStack[tBusItems.size()];
			int slot = 0;
			for (ItemStack g : tBusItems) {
				inputs[slot++] = g;
			}
			if (inputs.length > 0) {
				if (checkRecipeGeneric(inputs, new FluidStack[]{}, getMaxParallelRecipes(), 100, 250, 10000)) {
					return true;
				}
			}

		}
		return false;
	}

	@Override
	public int getMaxParallelRecipes() {
		return 8;
	}

	@Override
	public int getEuDiscountForParallelism() {
		return 0;
	}

	@Override
	public int getMaxEfficiency(final ItemStack aStack) {
		return 10000;
	}

	@Override
	public int getPollutionPerSecond(final ItemStack aStack) {
		return CORE.ConfigSwitches.pollutionPerSecondMultiABS * 4;
	}

	@Override
	public int getDamageToComponent(final ItemStack aStack) {
		return 0;
	}

	@Override
	public int getAmountOfOutputs() {
		return 2;
	}

	@Override
	public boolean explodesOnComponentBreak(final ItemStack aStack) {
		return false;
	}
}
