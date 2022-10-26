package beyond_earth_giselle_addon.common.compat.jer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.jei.JEIConfig;
import jeresources.jei.worldgen.WorldGenWrapper;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class WorldGenRegister
{
	public final Map<Ingredient, ItemStack> drops = new HashMap<>();
	public final Map<DimensionRestrictionPredicate, DimensionRestriction> restrictions = new HashMap<>();

	public WorldGenRegister()
	{

	}

	public void register(IRecipeRegistration registration, ResourceLocation path, PlacedFeature config)
	{
		OreGenBuilder oreGenBuilder = new OreGenBuilder().placedFeature(config);

		if (oreGenBuilder.ore == null)
		{
			return;
		}

		for (FeatureConfiguration unsupportedFeature : oreGenBuilder.unsupporedFeatures)
		{
			BeyondEarthAddon.LOGGER.error(path + ": Unsupported FeatureConfiguration, " + unsupportedFeature);
		}

		for (PlacementModifier unsupportedPlacement : oreGenBuilder.unsupporedPlacements)
		{
			BeyondEarthAddon.LOGGER.error(path + ": Unsupported PlacementModifier, " + unsupportedPlacement);
		}

		for (TargetBlockState targetBlockState : oreGenBuilder.ore.targetStates)
		{
			oreGenBuilder.drops.clear();
			this.register(registration, path, oreGenBuilder, targetBlockState);
		}

	}

	private void register(IRecipeRegistration registration, ResourceLocation path, OreGenBuilder oreGenBuilder, TargetBlockState targetBlockState)
	{
		DimensionRestriction dimensionRestriction = this.findDimensionRestrictions(path, oreGenBuilder, targetBlockState).findFirst().orElse(null);

		if (dimensionRestriction == null)
		{
			BeyondEarthAddon.LOGGER.error(path + ": Missing DimensionRestriction, " + targetBlockState.target + ", " + targetBlockState.state);
			return;
		}

		ItemStack drop = this.drops.entrySet().stream().filter(e -> e.getKey().test(new ItemStack(targetBlockState.state.getBlock()))).map(e -> e.getValue()).findFirst().orElse(null);
		oreGenBuilder.dimensionRestriction = dimensionRestriction;
		oreGenBuilder.silkTouch = drop != null;

		if (drop != null)
		{
			oreGenBuilder.drops.add(new LootDrop(drop));
		}

		registration.addRecipes(JEIConfig.WORLD_GEN_TYPE, oreGenBuilder.build().map(WorldGenWrapper::new).toList());
	}

	public Stream<DimensionRestriction> findDimensionRestrictions(ResourceLocation path, OreGenBuilder oreGenBuilder, TargetBlockState targetBlockState)
	{
		return this.restrictions.entrySet().stream().filter(e -> e.getKey().test(path, oreGenBuilder, targetBlockState)).map(e -> e.getValue());
	}

	public interface DimensionRestrictionPredicate
	{
		public static DimensionRestrictionPredicate byPathStartsWith(String prefix)
		{
			return (path, oreGenBuilder, target) -> path.getPath().startsWith(prefix);
		}

		public static DimensionRestrictionPredicate byRuleTestEquals(RuleTest ruleTest)
		{
			return (path, oreGenBuilder, target) -> target.target == ruleTest;
		}

		public boolean test(ResourceLocation path, OreGenBuilder oreGenBuilder, TargetBlockState targetBlockState);
	}

}
