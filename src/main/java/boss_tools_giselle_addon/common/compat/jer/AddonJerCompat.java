package boss_tools_giselle_addon.common.compat.jer;

import java.util.Arrays;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.compat.CompatibleMod;
import jeresources.api.IWorldGenRegistry;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.compatibility.JERAPI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mrscauthd.boss_tools.ModInnet;

public class AddonJerCompat extends CompatibleMod
{
	public static final String MODID = "jeresources";
	public static final String LANGPREFIX = "jer";

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

	@Override
	public String getModID()
	{
		return MODID;
	}

	@Override
	protected void onLoad()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::onFMLCommonSetup);
	}

	private void onFMLCommonSetup(FMLCommonSetupEvent event)
	{
		registerMoon();
		registerMars();
		registerMercury();
		registerVenus();
	}

	public static Restriction getRestriction(Restriction.Type type, ResourceLocation dimension)
	{
		RegistryKey<World> key = RegistryKey.create(Registry.DIMENSION_REGISTRY, dimension);
		return new Restriction(new DimensionRestriction(type, key));
	}

	public static void registerMoon()
	{
		Restriction restriction = getRestriction(Restriction.Type.WHITELIST, BossToolsAddon.prl("moon"));
		register(restriction, BossToolsAddon.prl("moon_cheese_ore"));
		register(restriction, BossToolsAddon.prl("moon_glowstone_ore"), Items.GLOWSTONE_DUST);
		register(restriction, BossToolsAddon.prl("moon_iron_ore"));
		register(restriction, BossToolsAddon.prl("moon_desh_ore"));
	}

	public static void registerMars()
	{
		Restriction restriction = getRestriction(Restriction.Type.WHITELIST, BossToolsAddon.prl("mars"));
		register(restriction, BossToolsAddon.prl("mars_ice_shard_ore"), ModInnet.ICE_SHARD.get());
		register(restriction, BossToolsAddon.prl("mars_iron_ore"));
		register(restriction, BossToolsAddon.prl("mars_diamond_ore"), Items.DIAMOND);
		register(restriction, BossToolsAddon.prl("mars_silicon_ore"));
	}

	public static void registerMercury()
	{
		Restriction restriction = getRestriction(Restriction.Type.WHITELIST, BossToolsAddon.prl("mercury"));
		register(restriction, BossToolsAddon.prl("mercury_iron_ore"));
	}

	public static void registerVenus()
	{
		Restriction restriction = getRestriction(Restriction.Type.WHITELIST, BossToolsAddon.prl("venus"));
		register(restriction, BossToolsAddon.prl("venus_coal_ore"), Items.COAL);
		register(restriction, BossToolsAddon.prl("venus_gold_ore"));
		register(restriction, BossToolsAddon.prl("venus_diamond_ore"), Items.DIAMOND);
	}

	public static void register(Restriction restriction, ResourceLocation worldGenRegistryName, Item... silkTouchItems)
	{
		try
		{
			IWorldGenRegistry jer_registry = JERAPI.getInstance().getWorldGenRegistry();
			Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
			OreGenBuilder builder = new OreGenBuilder().featureConfig(registry.get(worldGenRegistryName).config);
			builder.restriction = restriction;
			builder.silkTouch = silkTouchItems.length > 0;
			Arrays.stream(silkTouchItems).map(ItemStack::new).map(LootDrop::new).forEach(builder.drops::add);
			builder.register(jer_registry);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
