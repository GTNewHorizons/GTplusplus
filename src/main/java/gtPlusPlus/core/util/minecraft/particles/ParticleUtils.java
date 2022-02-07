package gtPlusPlus.core.util.minecraft.particles;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.*;

public class ParticleUtils {


	public static void SpawnParticles(World aWorld, String aParticleName, double aX, double aY, double aZ, int aAmount, double aVelocity) {
		SpawnParticles(aWorld, aParticleName, aX, aY, aZ, 0, 0, 0, aAmount, 128, aVelocity);
	}

	public static void SpawnParticles(World aWorld, String aParticleName, double aX, double aY, double aZ, double aDisX, double aDisY, double aDisZ, int aAmount) {
		SpawnParticles(aWorld, aParticleName, aX, aY, aZ, aDisX, aDisY, aDisZ, aAmount, 128, 0.1);
	}

	//WorldServer#func_147487_a(String particleName, double x, double y, double z, int numParticles, double displacementX, double displacementY, double displacementZ, double velocity)
	public static void SpawnParticles(World aWorld, String aParticleName, double aX, double aY, double aZ, double aDisX, double aDisY, double aDisZ, int aAmount, double aRange, double aVelocity) {
		if (aWorld == null || aParticleName == null || aParticleName.length() <= 0) {
			return;
		}
		if (aWorld instanceof WorldServer) {
			WorldServer aWorldServer = (WorldServer) aWorld;
			S2APacketParticles s2apacketparticles = new S2APacketParticles(aParticleName, (float) aX, (float) aY, (float) aZ,
					(float) aDisX, (float) aDisY, (float) aDisZ, (float) aVelocity, aAmount);
			for (Object element : aWorldServer.playerEntities) {
				EntityPlayerMP entityplayermp = (EntityPlayerMP) element;
				ChunkCoordinates chunkcoordinates = entityplayermp.getPlayerCoordinates();
				double d7 = aX - chunkcoordinates.posX;
				double d8 = aY - chunkcoordinates.posY;
				double d9 = aZ - chunkcoordinates.posZ;
				double d10 = d7 * d7 + d8 * d8 + d9 * d9;
				if (d10 <= aRange) {
					entityplayermp.playerNetServerHandler.sendPacket(s2apacketparticles);
				}
			}

		}





	}

}
