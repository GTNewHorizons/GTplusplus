package gtPlusPlus.core.util.minecraft;

import com.mojang.authlib.GameProfile;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

public class MovableFakePlayer extends FakePlayer {

	public MovableFakePlayer(WorldServer world, GameProfile name) {
		super(world, name);
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		return super.getPlayerCoordinates();
	}

	@Override
	public void setPositionAndUpdate(double p_70634_1_, double p_70634_3_, double p_70634_5_) {
		super.setPositionAndUpdate(p_70634_1_, p_70634_3_, p_70634_5_);
	}

	@Override
	public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {
		super.setPositionAndRotation2(p_70056_1_, p_70056_3_, p_70056_5_, p_70056_7_, p_70056_8_, p_70056_9_);
	}

	@Override
	/**
	 * Sets the x,y,z of the entity from the given parameters. Also seems to set up a bounding box.
	 */
	public void setPosition(double aX, double aY, double aZ){
		this.posX = aX;
		this.posY = aY;
		this.posZ = aZ;
	}

	@Override
	public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_) {
		super.moveEntity(p_70091_1_, p_70091_3_, p_70091_5_);
	}

	@Override
	public void setPositionAndRotation(double p_70080_1_, double p_70080_3_, double p_70080_5_, float p_70080_7_, float p_70080_8_) {
		super.setPositionAndRotation(p_70080_1_, p_70080_3_, p_70080_5_, p_70080_7_, p_70080_8_);
	}

	@Override
	public void setLocationAndAngles(double p_70012_1_, double p_70012_3_, double p_70012_5_, float p_70012_7_,	float p_70012_8_) {
		super.setLocationAndAngles(p_70012_1_, p_70012_3_, p_70012_5_, p_70012_7_, p_70012_8_);
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean isInvisible() {
		return true;
	}

	@Override
	public boolean isSneaking() {
		return true;
	}

}
