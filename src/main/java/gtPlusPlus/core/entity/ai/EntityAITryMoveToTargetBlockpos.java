package gtPlusPlus.core.entity.ai;

import gtPlusPlus.api.objects.minecraft.BlockPos;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.util.Vec3;

public class EntityAITryMoveToTargetBlockpos extends EntityAIBase {

	private EntityCreature mThisEntity;
	private BlockPos mTarget;
	private double mMovePosX;
	private double mMovePosY;
	private double mMovePosZ;
	private double mSpeed;
	/**
	 * If the distance to the target entity is further than this, this AI task will
	 * not run.
	 */
	private float maxTargetDistance;

	public EntityAITryMoveToTargetBlockpos(EntityCreature aThisEntity, double aSpeed, float aMaxDistance, BlockPos aPos) {
		this.mThisEntity = aThisEntity;
		this.mSpeed = aSpeed;
		this.maxTargetDistance = aMaxDistance;
		this.mTarget = aPos;
		this.setMutexBits(1);
	}

	public static boolean hasTask(EntityLiving aEntity) {
		return getTask(aEntity) != null;
	}

	public static EntityAITryMoveToTargetBlockpos getTask(EntityLiving aEntity) {
		EntityAITasks aTasks = aEntity.tasks;
		if (aTasks != null && aTasks.taskEntries != null && !aTasks.taskEntries.isEmpty()) {
			for (Object aTaskObj : aTasks.taskEntries) {
				if (aTaskObj instanceof EntityAITaskEntry) {
					EntityAITaskEntry aTaskEntry = (EntityAITaskEntry) aTaskObj;
					EntityAIBase aTask = aTaskEntry.action;
					if (aTask instanceof EntityAITryMoveToTargetBlockpos) {
						return (EntityAITryMoveToTargetBlockpos) aTask;
					}
				}
			}
		}
		return null;
	}

	public static boolean setTaskLocation(EntityLiving aEntity, BlockPos aPos) {
		if (hasTask(aEntity)) {
			EntityAITryMoveToTargetBlockpos aTask = getTask(aEntity);
			aTask.mTarget = aPos;
			return true;
		}
		return false;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {

		if (this.mTarget == null) {
			return false;
		}
		else if (this.mTarget.getDistanceSqToEntity(this.mThisEntity) > this.maxTargetDistance * this.maxTargetDistance) {
			return false;
		}
		else {
			Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.mThisEntity, 16, 7,
					Vec3.createVectorHelper(this.mTarget.xPos, this.mTarget.yPos, this.mTarget.zPos));

			if (vec3 == null) {
				return false;
			} else {
				this.mMovePosX = vec3.xCoord;
				this.mMovePosY = vec3.yCoord;
				this.mMovePosZ = vec3.zCoord;
				return true;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !this.mThisEntity.getNavigator().noPath() && this.mTarget
				.getDistanceSqToEntity(this.mThisEntity) < this.maxTargetDistance * this.maxTargetDistance;
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		this.mTarget = null;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.mThisEntity.getNavigator().tryMoveToXYZ(this.mMovePosX, this.mMovePosY, this.mMovePosZ, this.mSpeed);
	}
}