
package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.api.objects.data.AutoMap;
import gtPlusPlus.api.objects.minecraft.ThreadFakeWorldGenerator;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.core.util.math.MathUtils;
import gtPlusPlus.core.util.minecraft.EnergyUtils.EU;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import gtPlusPlus.xmod.gregtech.common.helpers.TreeFarmHelper;
import gtPlusPlus.xmod.gregtech.common.helpers.treefarm.TreeGenerator;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing.GregtechMetaTileEntity_IndustrialSifter;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdder;

public class GregtechMetaTileEntityTreeFarm extends GregtechMeta_MultiBlockBase {

	public static int CASING_TEXTURE_ID;
	public static String mCasingName = "Advanced Cryogenic Casing";
	public static TreeGenerator mTreeData;
	private int mCasing;
	private IStructureDefinition<GregtechMetaTileEntityTreeFarm> STRUCTURE_DEFINITION = null;
	
	static {
		mTreeData = new TreeGenerator();	
	}

	public GregtechMetaTileEntityTreeFarm(final int aID, final String aName, final String aNameRegional) {
		super(aID, aName, aNameRegional);
		CASING_TEXTURE_ID = TAE.getIndexFromPage(1, 15);
		mCasingName = ItemUtils.getLocalizedNameOfBlock(ModBlocks.blockCasings2Misc, 15);
	}



/*
	 * Static thread for Fake World Handling
	 */



	private static ScheduledExecutorService executor;
	private static ThreadFakeWorldGenerator aThread;

	public GregtechMetaTileEntityTreeFarm(final String aName) {
		super(aName);
		CASING_TEXTURE_ID = TAE.getIndexFromPage(1, 15);
		mCasingName = ItemUtils.getLocalizedNameOfBlock(ModBlocks.blockCasings2Misc, 15);

if (executor == null || mTreeData == null) {
			if (executor == null) {
				executor = Executors.newScheduledThreadPool(10);
			}
			if (executor != null) {				
				if (aThread == null) {
					aThread = new ThreadFakeWorldGenerator();	
					executor.scheduleAtFixedRate(aThread, 0, 1, TimeUnit.SECONDS);
					while (aThread.mGenerator == null) {
						if (aThread.mGenerator != null) {
							break;
						}
					}		
					if (aThread.mGenerator != null) {
						mTreeData = aThread.mGenerator;
					}
				}
			}			
		}





	}

	public IMetaTileEntity newMetaEntity(final IGregTechTileEntity aTileEntity) {
		return (IMetaTileEntity) new GregtechMetaTileEntityTreeFarm(this.mName);
	}

	@Override
	public String getMachineType() {
		return "Tree Farm";
	}

	@Override
	protected GT_Multiblock_Tooltip_Builder createTooltip() {
		if (mCasingName.toLowerCase().contains(".name")) {
			mCasingName = ItemUtils.getLocalizedNameOfBlock(ModBlocks.blockCasings2Misc, 15);
		}
		GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
		tt.addMachineType(getMachineType())
				.addInfo("Converts EU to Oak Logs")
				.addInfo("Eu Usage: 100% | Parallel: 1")
				.addInfo("Requires a Saw or Chainsaw in GUI slot")
				.addInfo("Constructed exactly the same as a normal Vacuum Freezer")
				.addPollutionAmount(getPollutionPerTick(null) * 20)
				.addSeparator()
				.beginStructureBlock(3, 3, 3, true)
				.addController("Front center")
				.addCasingInfo(mCasingName, 10)
				.addInputBus("Any casing", 1)
				.addOutputBus("Any casing", 1)
				.addEnergyHatch("Any casing", 1)
				.addMaintenanceHatch("Any casing", 1)
				.addMufflerHatch("Any casing", 1)
				.toolTipFinisher("GT++");
		return tt;
	}

	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
			final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.getCasingTextureForId(CASING_TEXTURE_ID),
					new GT_RenderedTexture((IIconContainer) (aActive ? TexturesGtBlock.Overlay_Machine_Controller_Advanced_Active : TexturesGtBlock.Overlay_Machine_Controller_Advanced))};
		}
		return new ITexture[]{Textures.BlockIcons.getCasingTextureForId(CASING_TEXTURE_ID)};
	}

	@Override
	public boolean hasSlotInGUI() {
		return true;
	}

	@Override
	public boolean requiresVanillaGtGUI() {
		return true;
	}

	@Override
	public String getCustomGUIResourceName() {
		return "VacuumFreezer";
	}

	public GT_Recipe.GT_Recipe_Map getRecipeMap() {			
		return null;
	}

	public boolean isCorrectMachinePart(final ItemStack aStack) {
		return TreeFarmHelper.isCorrectPart(aStack);
		//return true;
	}

//	public boolean isFacingValid(final byte aFacing) {
//		return aFacing > 1;
//	}

	public boolean checkRecipe(final ItemStack aStack) {

		if (mTreeData != null) {

			long tVoltage = getMaxInputVoltage();
			byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));

			this.mMaxProgresstime = 100;
			this.mEUt = (int) tVoltage;

			this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
			this.mEfficiencyIncrease = 10000;

			// Overclock
			if (this.mEUt <= 16) {
				this.mEUt = (this.mEUt * (1 << tTier - 1) * (1 << tTier - 1));
				this.mMaxProgresstime = (this.mMaxProgresstime / (1 << tTier - 1));
			} else {
				while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)]) {
					this.mEUt *= 4;
					this.mMaxProgresstime /= 2;
				}
			}

			if (this.mEUt > 0) {
				this.mEUt = (-this.mEUt);
			}



			int aChance = MathUtils.randInt(0, 10);
			AutoMap<ItemStack> aOutputs = new AutoMap<ItemStack>();

			try {
				if (aChance < 8) {
					//1% Chance per Tick				
					for (int u=0; u<(Math.max(4, (MathUtils.randInt((3*tTier), 100)*tTier*tTier)/14));u++) {
						aOutputs = mTreeData.generateOutput(0);		
						if (aOutputs.size() > 0) {

							ItemStack aLeaves = ItemUtils.getSimpleStack(Blocks.leaves);

							for (ItemStack aOutputItemStack : aOutputs) {
								if (!GT_Utility.areStacksEqual(aLeaves, aOutputItemStack)) {
									this.addOutput(aOutputItemStack);
								}
							}
							this.updateSlots();
						}	
					}			

				}				
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
			return true;
		}
		else {
			return false;
		}
		//return this.checkRecipeGeneric(4, 100, 100);
	}

	@Override
	public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
		mCasing = 0;
		return checkPiece(mName, 1, 1, 0) && mCasing >= 10 - 8 && checkHatch();
	}

	@Override
	public int getMaxParallelRecipes() {
		return 1;
	}

	@Override
	public int getEuDiscountForParallelism() {
		return 0;
	}

	@Override
	public IStructureDefinition<GregtechMetaTileEntityTreeFarm> getStructureDefinition() {
		if (STRUCTURE_DEFINITION == null) {
			STRUCTURE_DEFINITION = StructureDefinition.<GregtechMetaTileEntityTreeFarm>builder()
					.addShape(mName, transpose(new String[][]{
							{"XXX", "CCC", "CCC"},
							{"XXX", "C-C", "CCC"},
							{"X~X", "CCC", "CCC"},
					}))
					.addElement(
							'C',
							ofChain(
									ofHatchAdder(
											GregtechMetaTileEntityTreeFarm::addTreeFarmList, getCasingTextureIndex(), 1
									),
									onElementPass(
											x -> ++x.mCasing,
											ofBlock(
													ModBlocks.blockCasingsMisc, 0
											)
									)
							)
					)
					.addElement(
							'X',
							ofBlock(
									ModBlocks.blockCasingsMisc, 0
							)
					)
					.build();
		}
		return STRUCTURE_DEFINITION;
	}

	public final boolean addTreeFarmList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		} else {
			IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
			if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_InputBus){
				((GT_MetaTileEntity_Hatch)aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return this.mInputBusses.add((GT_MetaTileEntity_Hatch_InputBus)aMetaTileEntity);
			} else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Maintenance){
				((GT_MetaTileEntity_Hatch)aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return this.mMaintenanceHatches.add((GT_MetaTileEntity_Hatch_Maintenance)aMetaTileEntity);
			} else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Energy){
				((GT_MetaTileEntity_Hatch)aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return this.mEnergyHatches.add((GT_MetaTileEntity_Hatch_Energy)aMetaTileEntity);
			} else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_OutputBus) {
				((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return this.mOutputBusses.add((GT_MetaTileEntity_Hatch_OutputBus) aMetaTileEntity);
			} else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Muffler) {
				((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return this.mMufflerHatches.add((GT_MetaTileEntity_Hatch_Muffler) aMetaTileEntity);
			} else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Input) {
				((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return this.mInputHatches.add((GT_MetaTileEntity_Hatch_Input) aMetaTileEntity);
			} else if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Output) {
				((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return this.mOutputHatches.add((GT_MetaTileEntity_Hatch_Output) aMetaTileEntity);
			}
		}
		return false;
	}

	public byte getCasingTextureIndex() {
		return (byte) TAE.GTPP_INDEX(0);
	}

	public int getMaxEfficiency(final ItemStack aStack) {
		return 10000;
	}

	public int getPollutionPerTick(final ItemStack aStack) {
		return 5;
	}

	public int getDamageToComponent(final ItemStack aStack) {
		return MathUtils.balance((int) (75 - ((GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).getMass()))), 5, 120);
	}

	public boolean explodesOnComponentBreak(final ItemStack aStack) {
		return false;
	}

	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);		
		if (this.mInventory[1] != null && aTick % 200 == 0 && this.getBaseMetaTileEntity().isServerSide()) {
			ItemStack invItem = this.mInventory[1];			
			if (isCorrectMachinePart(invItem)) {

				boolean didElectricDamage = false;
				if (EU.isElectricItem(invItem)) {
					if (EU.hasCharge(invItem)) {
						long tVoltage = getMaxInputVoltage();
						byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));						
						if (EU.getCharge(invItem) >= tVoltage) {
							//Logger.WARNING("Can drain.");
							if (EU.discharge(invItem, (int) tVoltage, -1)) {
								//Logger.WARNING("Drained Power.");
								didElectricDamage = true;
							}
							else {
								//Logger.WARNING("Failed when draining Power.");
								this.getBaseMetaTileEntity().disableWorking();
							}
						}							
					}
				}
				//Logger.WARNING("Drained Power? "+didElectricDamage);



				if (!didElectricDamage && invItem.getItem() instanceof GT_MetaGenerated_Tool) {
					long aDmg = GT_MetaGenerated_Tool.getToolDamage(invItem);
					long aDmgMax = GT_MetaGenerated_Tool.getToolMaxDamage(invItem);
					if (aDmg < aDmgMax && GT_MetaGenerated_Tool.getPrimaryMaterial(invItem) != Materials._NULL) {
						//Logger.WARNING("dmg: "+aDmg+" | max: "+aDmgMax);
						GT_MetaGenerated_Tool.setToolDamage(invItem, aDmg+getDamageToComponent(invItem));							
					}
					else if (aDmg >= aDmgMax) {
						this.mInventory[1] = null;
					}
				}
			}			
		}		
	}

	@Override
	public void construct(ItemStack stackSize, boolean hintsOnly) {
		buildPiece(mName , stackSize, hintsOnly, 1, 1, 0);
	}
}
