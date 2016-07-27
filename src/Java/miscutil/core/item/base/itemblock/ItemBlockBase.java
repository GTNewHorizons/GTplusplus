package miscutil.core.item.base.itemblock;

import miscutil.core.creative.AddToCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockBase extends ItemBlockWithMetadata {

        public ItemBlockBase(Block block) {
                super(block, block);
                this.setCreativeTab(AddToCreativeTab.tabBlock);
        }

		@Override
		public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_) {
			
			return super.getColorFromItemStack(p_82790_1_, p_82790_2_);
		}
        
        
}