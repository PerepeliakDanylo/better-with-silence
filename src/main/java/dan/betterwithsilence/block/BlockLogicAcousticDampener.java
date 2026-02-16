package dan.betterwithsilence.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

/**
 * Acoustic Dampener BlockLogic.
 *
 * When placed, this block is ACTIVE by default (metadata 0) and silences all
 * sounds within an 8-block radius except hostile mob sounds.
 *
 * Right-click toggles the dampener ON/OFF:
 *   metadata 0 = ACTIVE (dampening sounds)
 *   metadata 1 = INACTIVE (not dampening)
 *
 * The toggle click sound always plays, even inside the dampened zone.
 */
public class BlockLogicAcousticDampener extends BlockLogic {

	/** The sound name used for toggle feedback. */
	public static final String TOGGLE_SOUND = "random.click";

	public BlockLogicAcousticDampener(Block<?> block) {
		super(block, Material.wood);
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
		int meta = world.getBlockMetadata(x, y, z);
		int newMeta = (meta == 0) ? 1 : 0;
		world.setBlockMetadataWithNotify(x, y, z, newMeta);

		// Play toggle sound — pitch varies to indicate state
		// High pitch (0.6) = ON, low pitch (0.5) = OFF
		float pitch = (newMeta == 0) ? 0.6f : 0.5f;
		world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS,
			(double) x + 0.5, (double) y + 0.5, (double) z + 0.5,
			TOGGLE_SOUND, 0.3f, pitch);

		return true;
	}

	/**
	 * Check if this dampener block is currently active.
	 * metadata 0 = active, metadata 1 = inactive.
	 */
	public static boolean isActive(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) == 0;
	}
}
