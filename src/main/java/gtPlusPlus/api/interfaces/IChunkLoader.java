package gtPlusPlus.api.interfaces;

import java.util.Set;
import net.minecraft.world.ChunkCoordIntPair;

public interface IChunkLoader {

    long getTicksRemaining();

    void setTicksRemaining(long aTicks);

    ChunkCoordIntPair getResidingChunk();

    void setResidingChunk(ChunkCoordIntPair aCurrentChunk);

    boolean getChunkLoadingActive();

    void setChunkLoadingActive(boolean aActive);

    boolean getDoesWorkChunkNeedReload();

    void setDoesWorkChunkNeedReload(boolean aActive);

    boolean addChunkToLoadedList(ChunkCoordIntPair aActiveChunk);

    boolean removeChunkFromLoadedList(ChunkCoordIntPair aActiveChunk);

    Set<ChunkCoordIntPair> getManagedChunks();

    int getChunkloaderTier();
}
