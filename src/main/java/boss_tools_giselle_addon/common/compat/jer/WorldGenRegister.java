package boss_tools_giselle_addon.common.compat.jer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import boss_tools_giselle_addon.common.BossToolsAddon;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.Restriction;
import jeresources.jei.JEIConfig;
import jeresources.jei.worldgen.WorldGenWrapper;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class WorldGenRegister
{
	public final Map<Ingredient, ItemStack> drops = new HashMap<>();
	public final Map<Predicate<RuleTest>, Restriction> restrictions = new HashMap<>();

	public WorldGenRegister()
	{

	}

	public void register(IRecipeRegistration registration, ResourceLocation path, ConfiguredFeature<?, ?> feature)
	{
		IFeatureConfig config = feature.config;
		OreGenBuilder oreGenBuilder = new OreGenBuilder().featureConfig(config);

		if (oreGenBuilder.ore == null)
		{
			return;
		}

		for (IFeatureConfig unsupportedFeature : oreGenBuilder.unsupporedFeatures)
		{
			BossToolsAddon.LOGGER.error(path + ": Unsupported FeatureConfig, " + unsupportedFeature);
		}

		for (IPlacementConfig unsupportedPlacement : oreGenBuilder.unsupporedPlacements)
		{
			BossToolsAddon.LOGGER.error(path + ": Unsupported PlacementConfig, " + unsupportedPlacement);
		}

		Restriction restriction = this.findRestrictions(oreGenBuilder.ore.target).findFirst().orElse(null);

		if (registration == null)
		{
			BossToolsAddon.LOGGER.error(path + ": Missing Restriction, " + oreGenBuilder.ore.target);
			return;
		}

		ItemStack drop = this.drops.entrySet().stream().filter(e -> e.getKey().test(new ItemStack(oreGenBuilder.ore.state.getBlock()))).map(e -> e.getValue()).findFirst().orElse(null);
		oreGenBuilder.restriction = restriction;
		oreGenBuilder.silkTouch = drop != null;

		if (drop != null)
		{
			oreGenBuilder.drops.add(new LootDrop(drop));
		}

		registration.addRecipes(oreGenBuilder.build().map(WorldGenWrapper::new).collect(Collectors.toList()), JEIConfig.WORLD_GEN);
	}

	public Stream<Restriction> findRestrictions(RuleTest target)
	{
		return this.restrictions.entrySet().stream().filter(e -> e.getKey().test(target)).map(e -> e.getValue());
	}

}
