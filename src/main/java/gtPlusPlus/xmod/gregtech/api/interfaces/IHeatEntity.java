package gtPlusPlus.xmod.gregtech.api.interfaces;

import ic2.api.energy.tile.IHeatSource;
import net.minecraftforge.common.util.ForgeDirection;

public interface IHeatEntity extends IHeatSource, IHeatSink {

    int getHeatBuffer();

    void setHeatBuffer(int HeatBuffer);

    void addtoHeatBuffer(int heat);

    int getTransmitHeat();

    int fillHeatBuffer(int maxAmount);

    int getMaxHeatEmittedPerTick();

    void updateHeatEntity();

    int maxrequestHeatTick(ForgeDirection directionFrom);

    int requestHeat(ForgeDirection directionFrom, int requestheat);
}
