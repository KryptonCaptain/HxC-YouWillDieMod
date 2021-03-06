package YouWillDie.worldgen;

import YouWillDie.Config;
import YouWillDie.ModRegistry;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class PillarGen implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(Config.spawnRandomPillars && world.provider.dimensionId == 0 && random.nextInt(Config.pillarGenChance) == 0) {
			boolean placed = false;

			int max = random.nextInt(Config.maxPillarsPerChunk) + 1;

			for(int y = 127, added = 0; y > 30 && !placed && added < max; y--) {
				for(int x = chunkX * 16; x < chunkX * 16 + 16 && !placed && added < max; x++) {
					for(int z = chunkZ * 16; z < chunkZ * 16 + 16 && !placed && added < max; z++) {
						if(random.nextInt(15) != 0 || world.isAirBlock(x, y, z) || world.getBlock(x, y, z).isReplaceable(world, x, y, z) || world.getBlock(x, y, z) == ModRegistry.blockTrap || world.getBlock(x, y, z) == Blocks.leaves) {continue;}

						Block block = ModRegistry.blockPillar;

						if(block.canPlaceBlockAt(world, x, y + 1, z)) {
							world.setBlock(x, y + 1, z, block);
							world.setBlockMetadataWithNotify(x, y + 1, z, 0, 0);

							world.setBlock(x, y + 2, z, block);
							world.setBlockMetadataWithNotify(x, y + 2, z, 1, 0);

							placed = random.nextBoolean();

							y -= 10;
							added++;
						}
					}
				}
			}
		}
	}
}
