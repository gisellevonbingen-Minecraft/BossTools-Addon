package boss_tools_giselle_addon.common.compat.jer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import jeresources.api.IWorldGenRegistry;
import jeresources.api.distributions.DistributionBase;
import jeresources.api.distributions.DistributionSquare;
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
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.DecoratedPlacementConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public class WorldGenBuilder
{
	public Block block;
	public int veinSize;
	public int veinCountPerChunk;
	public boolean square;
	public int bottomOffset;
	public int topOffset;
	public int maxHeight;
	public int chance;

	public Restriction restriction;
	public boolean silkTouch;
	public final List<LootDrop> drops;

	public WorldGenBuilder()
	{
		this.drops = new ArrayList<LootDrop>();
	}

	public WorldGenBuilder(Restriction restriction)
	{
		this();
		this.restriction = restriction;
	}

	public WorldGenBuilder defaultDrops()
	{
		return defaultDrops(false);
	}

	public WorldGenBuilder defaultDrops(boolean silkTouch)
	{
		this.silkTouch = silkTouch;
		this.drops.clear();
		this.drops.add(new LootDrop(this.getItemStack()));

		return this;
	}

	public void register(IWorldGenRegistry registry)
	{
		DistributionBase distribution = this.getDistribution();

		if (distribution != null)
		{
			registry.register(this.getItemStack(), distribution, this.restriction, this.silkTouch, this.drops.toArray(new LootDrop[0]));
		}

	}

	private ItemStack getItemStack()
	{
		return new ItemStack(this.block);
	}

	public DistributionBase getDistribution()
	{
		DistributionBase distribution = null;

		if (this.square == true)
		{
			// random.nextInt(maxHeight - topOffset) + bottomOffset
			int minY = this.bottomOffset;
			int maxY = (this.maxHeight - this.topOffset) + minY;
			distribution = new DistributionSquare(this.veinCountPerChunk, this.veinSize, minY, maxY);
		}

		return distribution;
	}

	public WorldGenBuilder featureConfig(ConfiguredFeature<?, ?> configuredFeature)
	{
		return this.featureConfig(configuredFeature.config());
	}

	public WorldGenBuilder featureConfig(IFeatureConfig config)
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

	public WorldGenBuilder decoratedFeatureConfig(IPlacementConfig config)
	{
		if (config instanceof NoPlacementConfig)
		{
			this.square = true;
		}
		else if (config instanceof TopSolidRangeConfig)
		{
			TopSolidRangeConfig topSolidRangeConfig = (TopSolidRangeConfig) config;
			this.bottomOffset = topSolidRangeConfig.bottomOffset;
			this.topOffset = topSolidRangeConfig.topOffset;
			this.maxHeight = topSolidRangeConfig.maximum;
		}
		else if (config instanceof DecoratedPlacementConfig)
		{
			DecoratedPlacementConfig decoratedPlacementConfig = (DecoratedPlacementConfig) config;
			IPlacementConfig inner = decoratedPlacementConfig.inner().config();
			IPlacementConfig outer = decoratedPlacementConfig.outer().config();
			this.decoratedFeatureConfig(inner);
			this.decoratedFeatureConfig(outer);
		}
		else if (config instanceof ChanceConfig)
		{
			this.chance = ((ChanceConfig) config).chance;
		}
		else if (config instanceof FeatureSpreadConfig)
		{
			FeatureSpreadConfig featureSpreadConfig = (FeatureSpreadConfig) config;
			FeatureSpread count = featureSpreadConfig.count();
			DataResult<JsonElement> result = FeatureSpread.CODEC.encodeStart(JsonOps.INSTANCE, count);
			JsonElement json = result.get().left().orElse(null);

			if (json != null)
			{
				this.veinCountPerChunk = json.getAsInt();
			}

		}

		return this;
	}

}
