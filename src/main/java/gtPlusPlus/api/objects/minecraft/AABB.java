package gtPlusPlus.api.objects.minecraft;

import gtPlusPlus.core.util.minecraft.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * Generates an AABB around an entity.
 * @author Alkalus
 *
 */
public class AABB {

	private final AxisAlignedBB mAabb;
	private final World mWorld;

	/**
	 * Creates a AxisAlignedBB based around an Entity.
	 * @param aEntity - The Entity to work with.
	 * @param x - Maximum X from origin.
	 * @param y - Maximum Y from origin.
	 * @param z - Maximum Z from origin.
	 */
	public AABB(Entity aEntity, int x, int y, int z) {
		if (aEntity == null) {
			this.mAabb = null;
			this.mWorld = null;
		}
		else {
			this.mWorld = aEntity.worldObj;
			BlockPos aEntityLocation = EntityUtils.findBlockPosUnderEntity(aEntity);
			int xMin, xMax, yMin, yMax, zMin, zMax;
			xMin = aEntityLocation.xPos - x;
			yMin = aEntityLocation.yPos - y;
			zMin = aEntityLocation.zPos - z;
			xMax = aEntityLocation.xPos + x;
			yMax = aEntityLocation.yPos + y;
			zMax = aEntityLocation.zPos + z;
			this.mAabb = AxisAlignedBB.getBoundingBox(xMin, yMin, zMin, xMax, yMax, zMax);
		}

	}

	public AABB(TileEntity aTile, int x, int y, int z) {
		if (aTile == null) {
			this.mAabb = null;
			this.mWorld = null;
		}
		else {
			this.mWorld = aTile.getWorldObj();
			BlockPos aEntityLocation = new BlockPos(aTile);
			int xMin, xMax, yMin, yMax, zMin, zMax;
			xMin = aEntityLocation.xPos - x;
			yMin = aEntityLocation.yPos - y;
			zMin = aEntityLocation.zPos - z;
			xMax = aEntityLocation.xPos + x;
			yMax = aEntityLocation.yPos + y;
			zMax = aEntityLocation.zPos + z;
			this.mAabb = AxisAlignedBB.getBoundingBox(xMin, yMin, zMin, xMax, yMax, zMax);
		}

	}

	/**
	 * Used to get the AxisAlignedBB from this class.
	 * @return
	 */
	public AxisAlignedBB get() {
		return this.mAabb;
	}

	/**
	 * Used to determine if this object is valid or not.
	 * @return
	 */
	public boolean valid() {
		return this.mAabb != null && this.mWorld != null;
	}

	public World world() {
		return this.mWorld;
	}

}
