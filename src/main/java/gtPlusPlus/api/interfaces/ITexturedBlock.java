package gtPlusPlus.api.interfaces;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.ITexturedTileEntity;
import net.minecraft.block.Block;
import net.minecraftforge.common.util.ForgeDirection;

public interface ITexturedBlock extends ITexturedTileEntity {

    ITexture[] getTexture(ForgeDirection side);

    @Override
    ITexture[] getTexture(Block block, ForgeDirection side);
}
