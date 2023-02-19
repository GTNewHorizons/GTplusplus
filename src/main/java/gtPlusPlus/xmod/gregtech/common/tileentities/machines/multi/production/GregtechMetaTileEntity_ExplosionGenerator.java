package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static gregtech.api.enums.GT_HatchElement.*;
import static gregtech.api.util.GT_StructureUtility.buildHatchAdder;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.ImmutableMap;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ParticleFX;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.WorldSpawnedEventBuilder;
import gtPlusPlus.core.util.minecraft.gregtech.PollutionUtils;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import ic2.core.block.EntityIC2Explosive;

public class GregtechMetaTileEntity_ExplosionGenerator extends
        GregtechMeta_MultiBlockBase<GregtechMetaTileEntity_ExplosionGenerator> implements ISurvivalConstructable {

    public static final byte SOUND_EVENT_EXPLODE = 1;
    private int mCasing;
    private static IStructureDefinition<GregtechMetaTileEntity_ExplosionGenerator> STRUCTURE_DEFINITION = null;

    public GregtechMetaTileEntity_ExplosionGenerator(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GregtechMetaTileEntity_ExplosionGenerator(String aName) {
        super(aName);
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType(getMachineType()).addInfo("Prohibited by the Partial Test Ban Treaty")
                .addInfo("Generates EU from TNT or Industrial TNT detonating near the pusher plates")
                .addInfo(
                        "Causes: " + EnumChatFormatting.DARK_PURPLE
                                + "250000-500000 "
                                + EnumChatFormatting.GRAY
                                + "pollution per operation")
                .beginStructureBlock(3, 3, 4, false).addController("Front Center")
                .addCasingInfo("Heat Proof Machine Casing", 6).addCasingInfo("Steel Gear Box Machine Casing", 2)
                .addCasingInfo("Solid Steel Machine Casing", 10).addMaintenanceHatch("next to a Gear Box", 1)
                .addDynamoHatch("next to a Gear Box, 1 maximum", 2).toolTipFinisher(
                        EnumChatFormatting.RED + "REDACTED "
                                + EnumChatFormatting.GRAY
                                + "via "
                                + EnumChatFormatting.RED
                                + "GT++");
        return tt;
    }

    @Override
    protected IIconContainer getActiveOverlay() {
        return Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE_ACTIVE;
    }

    @Override
    protected IIconContainer getInactiveOverlay() {
        return Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE;
    }

    @Override
    protected int getCasingTextureId() {
        return 11;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return getMaxEfficiency(aStack) > 0;
    }

    @Override
    public boolean checkRecipe(final ItemStack aStack) {
        this.mProgresstime = 0;
        this.mMaxProgresstime = 200;
        this.lEUt = 0;
        this.mEfficiencyIncrease = 10000;
        return true;
    }

    @Override
    public IStructureDefinition<GregtechMetaTileEntity_ExplosionGenerator> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<GregtechMetaTileEntity_ExplosionGenerator>builder().addShape(
                    mName,
                    transpose(
                            new String[][] { { "HHH", "HCH", "HCH", "   ", "SSS" },
                                    { "H~H", "CGC", "CGC", " S ", "SSS" }, { "HHH", "HCH", "HCH", "   ", "SSS" }, }))
                    // Regular casings or hatches
                    .addElement(
                            'C',
                            buildHatchAdder(GregtechMetaTileEntity_ExplosionGenerator.class)
                                    .atLeast(ImmutableMap.of(Maintenance, 1, Dynamo, 1)).casingIndex(11).dot(1)
                                    .buildAndChain(
                                            onElementPass(x -> ++x.mCasing, ofBlock(GregTech_API.sBlockCasings1, 11))))
                    // Gearbox
                    .addElement('G', ofBlock(GregTech_API.sBlockCasings2, 3))
                    // Heat-Proof Casings
                    .addElement('H', ofBlock(GregTech_API.sBlockCasings1, 11))
                    // Solid Steel
                    .addElement('S', ofBlock(GregTech_API.sBlockCasings2, 0)).build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        mProgresstime = 0;
        IGregTechTileEntity baseTile = getBaseMetaTileEntity();
        int offsetX = ForgeDirection.getOrientation(baseTile.getBackFacing()).offsetX * -6;
        int offsetY = ForgeDirection.getOrientation(baseTile.getBackFacing()).offsetY * -6;
        int offsetZ = ForgeDirection.getOrientation(baseTile.getBackFacing()).offsetZ * -6;
        List<Entity> entities = baseTile.getWorld().getEntitiesWithinAABBExcludingEntity(
                null,
                AxisAlignedBB.getBoundingBox(
                        baseTile.getXCoord() - 0.5 - offsetX,
                        baseTile.getYCoord() - 0.5 - offsetY,
                        baseTile.getZCoord() - 0.5 - offsetZ,
                        baseTile.getXCoord() + 1.5 - offsetX,
                        baseTile.getYCoord() + 1.5 - offsetY,
                        baseTile.getZCoord() + 1.5 - offsetZ));
        for (Entity tnt : entities) {
            if (tnt instanceof EntityIC2Explosive) {
                if (((EntityIC2Explosive) tnt).fuse == 1 && !tnt.isDead) {
                    if (!baseTile.getWorld().isRemote) {
                        tnt.setDead();
                        sendSound(SOUND_EVENT_EXPLODE);
                        baseTile.increaseStoredEnergyUnits(5000000, false);
                        PollutionUtils.addPollution(getBaseMetaTileEntity(), 500000);
                        break;
                    }
                }
            }
            if (tnt instanceof EntityTNTPrimed) {
                if (((EntityTNTPrimed) tnt).fuse == 1 && !tnt.isDead) {
                    if (!baseTile.getWorld().isRemote) {
                        tnt.setDead();
                        sendSound(SOUND_EVENT_EXPLODE);
                        baseTile.increaseStoredEnergyUnits(2500000, false);
                        PollutionUtils.addPollution(getBaseMetaTileEntity(), 250000);
                        break;
                    }
                }
            }
        }
        for (GT_MetaTileEntity_Hatch tHatch : this.mAllDynamoHatches) {
            addEnergyToHatch(tHatch);
        }
        return true;
    }

    @Override
    public void doSound(byte aIndex, double aX, double aY, double aZ) {
        if (aIndex == GregtechMetaTileEntity_ExplosionGenerator.SOUND_EVENT_EXPLODE) {
            IGregTechTileEntity baseTile = getBaseMetaTileEntity();
            int offsetX = ForgeDirection.getOrientation(baseTile.getBackFacing()).offsetX * -5;
            int offsetY = ForgeDirection.getOrientation(baseTile.getBackFacing()).offsetY * -5;
            int offsetZ = ForgeDirection.getOrientation(baseTile.getBackFacing()).offsetZ * -5;
            GT_Utility.doSoundAtClient(SoundResource.RANDOM_EXPLODE, 2, 1.0F, aX - offsetX, aY - offsetY, aZ - offsetZ);
            new WorldSpawnedEventBuilder.ParticleEventBuilder().setIdentifier(ParticleFX.LARGE_EXPLODE)
                    .setWorld(getBaseMetaTileEntity().getWorld())
                    .setMotion(0D, 0D, 0D).<WorldSpawnedEventBuilder.ParticleEventBuilder>times(
                            1,
                            x -> x.setPosition(aX - offsetX, aY - offsetY, aZ - offsetZ).run());
        }
    }

    @Override
    public long maxEUStore() {
        return 10000000;
    }

    private long addEnergyToHatch(MetaTileEntity aHatch) {
        if (!isValidMetaTileEntity(aHatch)) {
            return 0;
        }

        long voltage = aHatch.maxEUOutput() * aHatch.maxAmperesOut();

        if (aHatch.getEUVar() > aHatch.maxEUStore() - voltage) {
            return 0;
        }

        if (this.getBaseMetaTileEntity().decreaseStoredEnergyUnits(voltage, false)) {
            aHatch.getBaseMetaTileEntity().increaseStoredEnergyUnits(voltage, false);
            return voltage;
        }
        return 0;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(mName, stackSize, hintsOnly, 1, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivialBuildPiece(mName, stackSize, 1, 1, 0, elementBudget, env, false, true);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        mDynamoHatches.clear();
        mMaintenanceHatches.clear();
        return checkPiece(mName, 1, 1, 0) && mCasing >= 6 && checkHatch() && mDynamoHatches.size() <= 1;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GregtechMetaTileEntity_ExplosionGenerator(this.mName);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 1;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerSecond(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    @Override
    public String[] getExtraInfoData() {
        return new String[] { getIdealStatus() == getRepairStatus() ? "No Maintainance issues" : "Needs Maintainance" };
    }

    @Override
    public String getMachineType() {
        return "Explosion Generator";
    }

    @Override
    public int getMaxParallelRecipes() {
        return 1;
    }

    @Override
    public int getEuDiscountForParallelism() {
        return 0;
    }
}
