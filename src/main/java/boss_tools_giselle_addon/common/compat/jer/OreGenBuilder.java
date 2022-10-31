package boss_tools_giselle_addon.common.compat.jer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import boss_tools_giselle_addon.common.compat.jer.DistributionShape.DistributionShapeSqaure;
import jeresources.api.distributions.DistributionBase;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.Restriction;
import jeresources.entry.WorldGenEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public class OreGenBuilder
{
	public OreFeatureConfig ore;
	public int veinCountPerChunk;
	public final List<DistributionShape> distributionShapes;

	public Restriction restriction;
	public boolean silkTouch;
	public final List<LootDrop> drops;

	public final List<IFeatureConfig> unsupporedFeatures;
	public final List<IPlacementConfig> unsupporedPlacements;

	public OreGenBuilder()
	{
		this.distributionShapes = new ArrayList<>();
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
		this.drops.add(new LootDrop(this.getItemStack()));

		return this;
	}

	public Stream<WorldGenEntry> build()
	{
		ItemStack itemStack = this.getItemStack();
		LootDrop[] drops = this.drops.toArray(new LootDrop[0]);

		return this.distributionShapes.stream().map(shape ->
		{
			DistributionBase distribution = shape.build(this);
			return new WorldGenEntry(itemStack, distribution, this.restriction, this.silkTouch, drops);
		});
	}

	public ItemStack getItemStack()
	{
		return new ItemStack(this.ore.state.getBlock());
	}

	public OreGenBuilder featureConfig(IFeatureConfig config)
	{
		List<ConfiguredFeature<?, ?>> usedFeatures = new ArrayList<>();

		if (config instanceof OreFeatureConfig)
		{
			this.ore = (OreFeatureConfig) config;
		}
		else if (config instanceof DecoratedFeatureConfig)
		{
			DecoratedFeatureConfig decoratedFeatureConfig = (DecoratedFeatureConfig) config;
			this.decoratedFeatureConfig(decoratedFeatureConfig.decorator.config());
			usedFeatures.add(decoratedFeatureConfig.feature.get());
		}
		else if (config instanceof FeatureSpreadConfig)
		{
			this.decoratedFeatureConfig((FeatureSpreadConfig) config);
		}
		else
		{
			this.unsupporedFeatures.add(config);
		}

		List<ConfiguredFeature<?, ?>> children = config.getFeatures().collect(Collectors.toList());
		children.removeAll(usedFeatures);

		for (ConfiguredFeature<?, ?> child : children)
		{
			this.featureConfig(child.config);
		}

		return this;
	}

	public OreGenBuilder decoratedFeatureConfig(IPlacementConfig config)
	{
		if (config instanceof FeatureSpreadConfig)
		{
			FeatureSpread featureSpread = OreGenHelper.getFeatureSpread((FeatureSpreadConfig) config);
			this.veinCountPerChunk = OreGenHelper.getFeatureSpreadBase(featureSpread);
		}
		else if (config instanceof TopSolidRangeConfig)
		{
			TopSolidRangeConfig topSolidRangeConfig = (TopSolidRangeConfig) config;
			int minY = topSolidRangeConfig.bottomOffset;
			int maxY = (topSolidRangeConfig.maximum - topSolidRangeConfig.topOffset) + minY;

			DistributionShapeSqaure shape = new DistributionShapeSqaure();
			shape.minY = minY;
			shape.maxY = maxY;
			this.distributionShapes.add(shape);
		}
		else if (config instanceof NoPlacementConfig)
		{

		}
		else
		{
			this.unsupporedPlacements.add(config);
		}

		return this;
	}

}
