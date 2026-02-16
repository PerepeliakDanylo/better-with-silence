package dan.betterwithsilence;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class that handles sound dampening logic.
 *
 * The Acoustic Dampener block COMPLETELY SILENCES all sounds within an 8-block
 * radius, EXCEPT hostile mob sounds which are always allowed through so the
 * player can hear danger approaching.
 */
public final class SoundDampener {

	/**
	 * Sound prefixes for hostile mobs that should NEVER be silenced.
	 * These sounds are critical for gameplay — the player needs to hear
	 * creepers, zombies, skeletons, etc. approaching.
	 */
	private static final Set<String> HOSTILE_MOB_PREFIXES = new HashSet<>(Arrays.asList(
		"mob.creeper",
		"mob.ghast",
		"mob.skeleton",
		"mob.slime",
		"mob.spider",
		"mob.zombie",
		"mob.zombiepig"
	));

	private SoundDampener() {
		// Utility class, no instantiation
	}

	/**
	 * Check if the given sound is from a hostile mob and should be allowed through.
	 * Returns true if the sound should NOT be silenced.
	 */
	public static boolean isHostileMobSound(String soundName) {
		if (soundName == null) return false;
		for (String prefix : HOSTILE_MOB_PREFIXES) {
			if (soundName.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if there is an active Acoustic Dampener block within radius of the
	 * given position.
	 *
	 * @param world  The world to check
	 * @param soundX X coordinate of the sound source
	 * @param soundY Y coordinate of the sound source
	 * @param soundZ Z coordinate of the sound source
	 * @return true if a dampener is within range and the sound should be silenced
	 */
	public static boolean shouldSilence(World world, double soundX, double soundY, double soundZ) {
		if (BetterWithSilence.acousticDampener == null) {
			return false;
		}

		int radius = BetterWithSilence.DAMPENING_RADIUS;
		Block<?> dampenerBlock = BetterWithSilence.acousticDampener;

		int centerX = (int) Math.floor(soundX);
		int centerY = (int) Math.floor(soundY);
		int centerZ = (int) Math.floor(soundZ);

		for (int dx = -radius; dx <= radius; dx++) {
			for (int dy = -radius; dy <= radius; dy++) {
				for (int dz = -radius; dz <= radius; dz++) {
					int checkX = centerX + dx;
					int checkY = centerY + dy;
					int checkZ = centerZ + dz;

					// Skip positions outside valid world range
					if (checkY < 0 || checkY > 255) continue;

					// Check spherical distance
					double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
					if (distance > radius) continue;

					// Check if this block is an active Acoustic Dampener
					Block<?> blockAt = world.getBlock(checkX, checkY, checkZ);
					if (blockAt == dampenerBlock) {
						// Only silence if the dampener is toggled ON (metadata 0)
						if (dan.betterwithsilence.block.BlockLogicAcousticDampener.isActive(world, checkX, checkY, checkZ)) {
							return true; // Found an active dampener — silence this sound
						}
					}
				}
			}
		}

		return false;
	}
}
