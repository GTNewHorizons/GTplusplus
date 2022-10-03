package gtPlusPlus.api.interfaces;

import gregtech.api.interfaces.IDescribable;
import gregtech.api.interfaces.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IGregtechPower
        extends IGearEnergyTileEntity, ITurnable, IGregTechDeviceInformation, IDescribable, IBasicEnergyContainer {

    @Override
    String[] getDescription();

    @Override
    default boolean isUniversalEnergyStored(long p0) {
        return false;
    }

    @Override
    long getOutputAmperage();

    @Override
    long getOutputVoltage();

    @Override
    long getInputAmperage();

    @Override
    long getInputVoltage();

    @Override
    boolean decreaseStoredEnergyUnits(long p0, boolean p1);

    @Override
    boolean increaseStoredEnergyUnits(long p0, boolean p1);

    @Override
    boolean drainEnergyUnits(byte p0, long p1, long p2);

    @Override
    long getAverageElectricInput();

    @Override
    long getAverageElectricOutput();

    @Override
    long getStoredEU();

    @Override
    long getEUCapacity();

    @Override
    long getStoredSteam();

    @Override
    long getSteamCapacity();

    @Override
    boolean increaseStoredSteam(long p0, boolean p1);

    @Override
    Block getBlockAtSide(byte p0);

    @Override
    Block getBlockAtSideAndDistance(byte p0, int p1);

    @Override
    Block getBlockOffset(int p0, int p1, int p2);

    @Override
    TileEntity getTileEntity(int p0, int p1, int p2);

    @Override
    TileEntity getTileEntityAtSide(byte p0);

    @Override
    TileEntity getTileEntityAtSideAndDistance(byte p0, int p1);

    @Override
    TileEntity getTileEntityOffset(int p0, int p1, int p2);

    @Override
    World getWorld();

    @Override
    int getXCoord();

    @Override
    short getYCoord();

    @Override
    int getZCoord();

    @Override
    boolean isClientSide();

    @Override
    boolean isDead();

    @Override
    boolean isInvalidTileEntity();

    @Override
    boolean isServerSide();

    @Override
    void readFromNBT(NBTTagCompound p0);

    @Override
    void writeToNBT(NBTTagCompound p0);

    @Override
    boolean acceptsRotationalEnergy(byte p0);

    @Override
    boolean injectRotationalEnergy(byte p0, long p1, long p2);

    @Override
    long injectEnergyUnits(byte p0, long p1, long p2);

    @Override
    boolean inputEnergyFrom(byte p0);

    @Override
    boolean outputsEnergyTo(byte p0);

    @Override
    String[] getInfoData();

    @Override
    default boolean isGivingInformation() {
        return true;
    }

    /*	boolean onPreTick(TilePoweredGT tilePoweredGT, long mTickTimer2);

    boolean onTick(TilePoweredGT iGregTechTileEntity, long mTickTimer2);

    boolean onPostTick(TilePoweredGT iGregTechTileEntity, long mTickTimer2);*/

}
