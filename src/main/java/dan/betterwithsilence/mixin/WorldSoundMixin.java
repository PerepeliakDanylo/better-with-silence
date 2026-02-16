package dan.betterwithsilence.mixin;

import dan.betterwithsilence.SoundDampener;
import dan.betterwithsilence.block.BlockLogicAcousticDampener;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin into World to intercept ALL sound playback methods.
 *
 * BTA has two ways to play sounds:
 *   1. playSoundEffect(Entity, SoundCategory, x, y, z, name, volume, pitch)
 *   2. playSoundAtEntity(Entity excluded, Entity source, name, volume, pitch)
 *
 * We intercept both to completely silence everything within the dampener radius,
 * except hostile mob sounds.
 *
 * Note: remap = false because BTA is distributed without obfuscation.
 */
@Mixin(value = World.class, remap = false)
public abstract class WorldSoundMixin {

	/**
	 * Intercept playSoundEffect — used for block/environment/misc sounds.
	 */
	@Inject(method = "playSoundEffect", at = @At("HEAD"), cancellable = true)
	private void bws_onPlaySoundEffect(Entity entity, SoundCategory category, double x, double y, double z, String soundName, float volume, float pitch, CallbackInfo ci) {
		// Always allow the toggle click sound through
		if (BlockLogicAcousticDampener.TOGGLE_SOUND.equals(soundName)) {
			return;
		}

		if (SoundDampener.isHostileMobSound(soundName)) {
			return;
		}

		World self = (World) (Object) this;
		if (SoundDampener.shouldSilence(self, x, y, z)) {
			ci.cancel();
		}
	}

	/**
	 * Intercept playSoundAtEntity — used for mob/entity sounds (hurt, ambient, death).
	 *
	 * BTA 7.3 signature: World.playSoundAtEntity(Entity excluded, Entity source, String name, float volume, float pitch)
	 */
	@Inject(method = "playSoundAtEntity", at = @At("HEAD"), cancellable = true)
	private void bws_onPlaySoundAtEntity(Entity excluded, Entity source, String soundName, float volume, float pitch, CallbackInfo ci) {
		if (SoundDampener.isHostileMobSound(soundName)) {
			return;
		}

		if (source == null) {
			return;
		}

		World self = (World) (Object) this;
		if (SoundDampener.shouldSilence(self, source.x, source.y, source.z)) {
			ci.cancel();
		}
	}
}
