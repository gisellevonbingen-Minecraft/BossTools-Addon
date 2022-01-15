package beyond_earth_giselle_addon.common.compat.jer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jeresources.api.IWorldGenRegistry;
import jeresources.api.distributions.DistributionBase;
import jeresources.api.distributions.DistributionCustom;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.Restriction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

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
		List<DistributionShape> distributionShapes = this.distributionShapes;

		if (distributionShapes.size() == 0)
		{
			registry.register(this.getItemStack(), new DistributionCustom(new float[0]), this.restriction, this.silkTouch, this.drops.toArray(new LootDrop[0]));
		}
		else
		{
			for (DistributionShape shape : distributionShapes)
			{
				DistributionBase distribution = shape.build(this);
				registry.register(this.getItemStack(), distribution, this.restriction, this.silkTouch, this.drops.toArray(new LootDrop[0]));
			}

		}

	}

	private ItemStack getItemStack()
	{
		return new ItemStack(this.block);
	}

	public OreGenBuilder placedFeature(PlacedFeature placedFeature)
	{
		List<PlacementModifier> placements = placedFeature.getPlacement();
		List<ConfiguredFeature<?, ?>> children = placedFeature.getFeatures().collect(Collectors.toList());

		for (PlacementModifier placementModifier : placements)
		{
			this.placementModifier(placementModifier);
		}

		for (ConfiguredFeature<?, ?> child : children)
		{
			this.featureConfig(child.config());
		}

		return this;
	}

	public OreGenBuilder featureConfig(FeatureConfiguration config)
	{
		if (config instanceof OreConfiguration)
		{
			OreConfiguration oreConfig = (OreConfiguration) config;
			this.block = oreConfig.targetStates.get(0).state.getBlock();
			this.veinSize = oreConfig.size;
		}

		return this;
	}

	public OreGenBuilder placementModifier(PlacementModifier placementModifier)
	{
		if (placementModifier instanceof CountPlacement)
		{
			IntProvider intProvider = OreGenHelper.getCountPlacementCount((CountPlacement) placementModifier);
			this.veinCountPerChunk = intProvider.getMinValue();
		}
		else if (placementModifier instanceof HeightRangePlacement)
		{
			DistributionShape shape = OreGenHelper.getHeightRangePlacementShape((HeightRangePlacement) placementModifier);
			this.distributionShapes.add(shape);
		}

		return this;
	}

}
