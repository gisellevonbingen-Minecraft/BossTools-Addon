package beyond_earth_giselle_addon.common.compat.jer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import jeresources.api.distributions.DistributionBase;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.BiomeRestriction;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.entry.WorldGenEntry;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenBuilder
{
	public OreConfiguration ore;
	public int veinCountPerChunk;
	public final List<DistributionShape> distributionShapes;
	public final List<Biome> biomes;

	public DimensionRestriction dimensionRestriction;
	public boolean silkTouch;
	public final List<LootDrop> drops;

	public final List<FeatureConfiguration> unsupporedFeatures;
	public final List<PlacementModifier> unsupporedPlacements;

	public OreGenBuilder()
	{
		this.distributionShapes = new ArrayList<>();
		this.biomes = new ArrayList<>();
		this.drops = new ArrayList<>();

		this.unsupporedFeatures = new ArrayList<>();
		this.unsupporedPlacements = new ArrayList<>();
	}

	public OreGenBuilder defaultDrops()
	{
		return defaultDrops(false);
	}

	public OreGenBuilder defaultDrops(boolean silkTouch)
	{
		this.silkTouch = silkTouch;
		this.drops.clear();
		this.getItemStacks().stream().map(LootDrop::new).forEach(this.drops::add);

		return this;
	}

	public Stream<WorldGenEntry> build()
	{
		List<ItemStack> itemStacks = this.getItemStacks();
		LootDrop[] drops = this.drops.toArray(new LootDrop[0]);
		Restriction restriction = new Restriction(this.getBiomeRestriction(), this.dimensionRestriction);

		return this.distributionShapes.stream().flatMap(shape ->
		{
			DistributionBase distribution = shape.build(this);
			return itemStacks.stream().map(is -> new WorldGenEntry(is, distribution, restriction, this.silkTouch, drops));
		});
	}

	public BiomeRestriction getBiomeRestriction()
	{
		int size = this.biomes.size();

		if (size == 0)
		{
			return BiomeRestriction.NO_RESTRICTION;
		}
		else
		{
			Biome[] array = this.biomes.toArray(Biome[]::new);
			Biome primary = array[0];
			Biome[] mores = new Biome[size - 1];
			System.arraycopy(array, 1, mores, 0, mores.length);
			return new BiomeRestriction(primary, mores);
		}

	}

	public List<ItemStack> getItemStacks()
	{
		return this.ore.targetStates.stream().map(s -> new ItemStack(s.state.getBlock())).toList();
	}

	public OreGenBuilder placedFeature(PlacedFeature placedFeature)
	{
		List<PlacementModifier> placements = placedFeature.placement();
		List<ConfiguredFeature<?, ?>> children = placedFeature.getFeatures().toList();

		for (PlacementModifier placementModifier : placements)
		{
			this.placementModifier(placedFeature, placementModifier);
		}

		for (ConfiguredFeature<?, ?> child : children)
		{
			this.featureConfig(child.config());
		}

		return this;
	}

	public OreGenBuilder featureConfig(FeatureConfiguration config)
	{
		if (config instanceof OreConfiguration oreConfig)
		{
			this.ore = oreConfig;
		}
		else
		{
			this.unsupporedFeatures.add(config);
		}

		return this;
	}

	public OreGenBuilder placementModifier(PlacedFeature placedFeature, PlacementModifier placementModifier)
	{
		if (placementModifier instanceof InSquarePlacement inSquarePlacement)
		{

		}
		else if (placementModifier instanceof CountPlacement countPlacement)
		{
			IntProvider intProvider = OreGenHelper.getCountPlacementCount(countPlacement);
			this.veinCountPerChunk = intProvider.getMinValue();
		}
		else if (placementModifier instanceof HeightRangePlacement heightRangePlacement)
		{
			DistributionShape shape = OreGenHelper.getHeightRangePlacementShape(heightRangePlacement);
			this.distributionShapes.add(shape);
		}
		else if (placementModifier instanceof BiomeFilter biomeFilter)
		{
			ForgeRegistries.BIOMES.getValues().stream().filter(p -> p.getGenerationSettings().hasFeature(placedFeature)).forEach(this.biomes::add);
		}
		else
		{
			this.unsupporedPlacements.add(placementModifier);
		}

		return this;
	}

}
