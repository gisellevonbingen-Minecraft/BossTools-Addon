package beyond_earth_giselle_addon.common.compat.jer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.mojang.serialization.Codec;

import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.blending.Blender;

public class ChunkGeneratorDummy extends ChunkGenerator
{
	public ChunkGeneratorDummy()
	{
		super(null, null);
	}

	@Override
	protected Codec<? extends ChunkGenerator> codec()
	{
		return null;
	}

	@Override
	public ChunkGenerator withSeed(long p_62156_)
	{
		return null;
	}

	@Override
	public Sampler climateSampler()
	{
		return null;
	}

	@Override
	public void applyCarvers(WorldGenRegion p_187691_, long p_187692_, BiomeManager p_187693_, StructureFeatureManager p_187694_, ChunkAccess p_187695_, Carving p_187696_)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void buildSurface(WorldGenRegion p_187697_, StructureFeatureManager p_187698_, ChunkAccess p_187699_)
	{

	}

	@Override
	public void spawnOriginalMobs(WorldGenRegion p_62167_)
	{

	}

	@Override
	public int getGenDepth()
	{
		return 0;
	}

	@Override
	public CompletableFuture<ChunkAccess> fillFromNoise(Executor p_187748_, Blender p_187749_, StructureFeatureManager p_187750_, ChunkAccess p_187751_)
	{
		return null;
	}

	@Override
	public int getSeaLevel()
	{
		return 0;
	}

	@Override
	public int getMinY()
	{
		return 0;
	}

	@Override
	public int getBaseHeight(int p_156153_, int p_156154_, Types p_156155_, LevelHeightAccessor p_156156_)
	{
		return 0;
	}

	@Override
	public NoiseColumn getBaseColumn(int p_156150_, int p_156151_, LevelHeightAccessor p_156152_)
	{
		return null;
	}

}
