package boss_tools_giselle_addon.common.compat.jer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boss_tools_giselle_addon.common.compat.jer.DistributionShape.DistributionShapeSqaure;
import jeresources.api.IWorldGenRegistry;
import jeresources.api.distributions.DistributionBase;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.Restriction;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public class OreGenBuilder
{
	public Block block;
	public int veinSize;
	public int veinCountPerChunk;
	public final List<DistributionShape> distributionShapes;

	public Restriction restriction;
	public boolean silkTouch;
	public final List<LootDrop> drops;

	public OreGenBuilder()
	{
		this.distributionShapes = new ArrayList<>();
		this.drops = new ArrayList<>();
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

	public void register(IWorldGenRegistry registry)
	{
		for (DistributionShape shape : this.distributionShapes)
		{
			DistributionBase distribution = shape.build(this);
			registry.register(this.getItemStack(), distribution, this.restriction, this.silkTouch, this.drops.toArray(new LootDrop[0]));
		}

	}

	private ItemStack getItemStack()
	{
		return new ItemStack(this.block);
	}

	public OreGenBuilder featureConfig(ConfiguredFeature<?, ?> configuredFeature)
	{
		return this.featureConfig(configuredFeature.config());
	}

	public OreGenBuilder featureConfig(IFeatureConfig config)
	{
		if (config instanceof OreFeatureConfig)
		{
			OreFeatureConfig oreFeatureConfig = (OreFeatureConfig) config;
			this.block = oreFeatureConfig.state.getBlock();
			this.veinSize = oreFeatureConfig.size;
		}
		else if (config instanceof DecoratedFeatureConfig)
		{
			this.decoratedFeatureConfig(((DecoratedFeatureConfig) config).decorator.config());
		}
		else if (config instanceof FeatureSpreadConfig)
		{
			this.decoratedFeatureConfig((FeatureSpreadConfig) config);
		}

		List<ConfiguredFeature<?, ?>> children = config.getFeatures().collect(Collectors.toList());

		for (ConfiguredFeature<?, ?> child : children)
		{
			this.featureConfig(child);
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

		return this;
	}

}
