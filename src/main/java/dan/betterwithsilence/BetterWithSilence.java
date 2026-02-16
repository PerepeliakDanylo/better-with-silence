package dan.betterwithsilence;

import dan.betterwithsilence.block.BlockLogicAcousticDampener;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.BlockSounds;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.TextureHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class BetterWithSilence implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
	public static final String MOD_ID = "betterwithsilence";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// The Acoustic Dampener block
	public static Block<BlockLogicAcousticDampener> acousticDampener;

	// Dampening radius in blocks
	public static final int DAMPENING_RADIUS = 8;

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
	}

	@Override
	public void beforeGameStart() {
		// Register block textures from assets/betterwithsilence/textures/block/
		TextureHelper.initializeAllFiles(MOD_ID, TextureRegistry.blockAtlas);

		// Register the Acoustic Dampener block
		acousticDampener = new BlockBuilder(MOD_ID)
			.setHardness(1.5f)
			.setResistance(10.0f)
			.setLuminance(0)
			.setBlockSound(BlockSounds.WOOD)
			.setTags(BlockTags.MINEABLE_BY_AXE)
			.build("acoustic_dampener", 3850, BlockLogicAcousticDampener::new);

		LOGGER.info("Better With Silence: Acoustic Dampener block registered!");
	}

	@Override
	public void afterGameStart() {
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Better With Silence initialized!");
	}

	@Override
	public void onRecipesReady() {
		// Recipe: Planks in corners, Wool on sides, Slimeball in center
		// P W P
		// W S W
		// P W P
		RecipeBuilder.Shaped(MOD_ID)
			.setShape("PWP", "WSW", "PWP")
			.addInput('P', Blocks.PLANKS_OAK)
			.addInput('W', Blocks.WOOL)
			.addInput('S', Items.SLIMEBALL)
			.create("acoustic_dampener", new ItemStack(acousticDampener, 1));

		LOGGER.info("Better With Silence: Crafting recipe registered!");
	}
}
