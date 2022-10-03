package gtPlusPlus.api.interfaces;

import java.util.Random;
import net.minecraft.world.World;

public interface IGeneratorWorld {
    boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2);

    void initiate();

    int getExtentX();

    int getExtentZ();

    int getRange();
}
