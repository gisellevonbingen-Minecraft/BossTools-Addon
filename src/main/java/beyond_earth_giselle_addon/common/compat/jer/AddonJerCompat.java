package beyond_earth_giselle_addon.common.compat.jer;

import java.util.Arrays;
import java.util.function.Supplier;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.compat.CompatibleMod;
import jeresources.api.IWorldGenRegistry;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.compatibility.JERAPI;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mrscauthd.beyond_earth.ModInit;

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
		registerOreGen();
	}

	public static Restriction getRestriction(Restriction.Type type, ResourceLocation dimension)
	{
		ResourceKey<Level> key = ResourceKey.create(Registry.DIMENSION_REGISTRY, dimension);
		return new Restriction(new DimensionRestriction(type, key));
	}

	public static void registerOreGen()
	{
		registerMoon();
		registerMars();
		registerMercury();
		registerVenus();
		registerGlacio();
	}

	public static void registerMoon()
	{
		Restriction restriction = getRestriction(Restriction.Type.WHITELIST, BeyondEarthAddon.prl("moon"));
		register(restriction, BeyondEarthAddon.prl("moon_cheese_ore"));
		register(restriction, BeyondEarthAddon.prl("soul_soil"), Items.GLOWSTONE_DUST);
		register(restriction, BeyondEarthAddon.prl("moon_ice_shard_ore"), ModInit.ICE_SHARD);
		register(restriction, BeyondEarthAddon.prl("moon_iron_ore"), Items.RAW_IRON);
		register(restriction, BeyondEarthAddon.prl("moon_desh_ore"), ModInit.RAW_DESH);
	}

	public static void registerMars()
	{
		Restriction restriction = getRestriction(Restriction.Type.WHITELIST, BeyondEarthAddon.prl("mars"));
		register(restriction, BeyondEarthAddon.prl("mars_ice_shard_ore"), ModInit.ICE_SHARD);
		register(restriction, BeyondEarthAddon.prl("mars_iron_ore"), Items.RAW_IRON);
		register(restriction, BeyondEarthAddon.prl("mars_diamond_ore"), Items.DIAMOND);
		register(restriction, BeyondEarthAddon.prl("mars_ostrum_ore"), ModInit.RAW_OSTRUM);
	}

	public static void registerMercury()
	{
		Restriction restriction = getRestriction(Restriction.Type.WHITELIST, BeyondEarthAddon.prl("mercury"));
		register(restriction, BeyondEarthAddon.prl("mercury_iron_ore"), Items.RAW_IRON);
	}

	public static void registerVenus()
	{
		Restriction restriction = getRestriction(Restriction.Type.WHITELIST, BeyondEarthAddon.prl("venus"));
		register(restriction, BeyondEarthAddon.prl("venus_coal_ore"), Items.COAL);
		register(restriction, BeyondEarthAddon.prl("venus_gold_ore"), Items.RAW_GOLD);
		register(restriction, BeyondEarthAddon.prl("venus_diamond_ore"), Items.DIAMOND);
		register(restriction, BeyondEarthAddon.prl("venus_calorite_ore"), ModInit.RAW_CALORITE);
	}

	public static void registerGlacio()
	{
		Restriction restriction = getRestriction(Restriction.Type.WHITELIST, BeyondEarthAddon.prl("glacio"));
		register(restriction, BeyondEarthAddon.prl("glacio_ice_shard_ore"), ModInit.ICE_SHARD);
		register(restriction, BeyondEarthAddon.prl("glacio_coal_ore"), Items.COAL);
		register(restriction, BeyondEarthAddon.prl("glacio_copper_ore"), Items.RAW_COPPER);
		register(restriction, BeyondEarthAddon.prl("glacio_iron_ore"), Items.RAW_IRON);
		register(restriction, BeyondEarthAddon.prl("glacio_lapis_ore"), new ItemStack(Items.LAPIS_LAZULI, 4));

		register(restriction, BeyondEarthAddon.prl("deepslate_coal_ore"), Items.COAL);
		register(restriction, BeyondEarthAddon.prl("deepslate_copper_ore"), Items.RAW_COPPER);
		register(restriction, BeyondEarthAddon.prl("deepslate_iron_ore"), Items.RAW_IRON);
		register(restriction, BeyondEarthAddon.prl("deepslate_lapis_ore"), new ItemStack(Items.LAPIS_LAZULI, 4));
	}

	public static void register(Restriction restriction, ResourceLocation worldGenRegistryName)
	{
		register(restriction, worldGenRegistryName, new ItemStack[0]);
	}

	@SafeVarargs
	public static void register(Restriction restriction, ResourceLocation worldGenRegistryName, Supplier<Item>... silkTouchItems)
	{
		register(restriction, worldGenRegistryName, Arrays.stream(silkTouchItems).map(Supplier::get).toArray(ItemLike[]::new));
	}

	public static void register(Restriction restriction, ResourceLocation worldGenRegistryName, ItemLike... silkTouchItems)
	{
		register(restriction, worldGenRegistryName, Arrays.stream(silkTouchItems).map(ItemStack::new).toArray(ItemStack[]::new));
	}

	public static void register(Restriction restriction, ResourceLocation worldGenRegistryName, ItemStack... silkTouchItems)
	{
		register(restriction, worldGenRegistryName, Arrays.stream(silkTouchItems).map(LootDrop::new).toArray(LootDrop[]::new));
	}

	public static void register(Restriction restriction, ResourceLocation worldGenRegistryName, LootDrop... silkTouchItems)
	{
		try
		{
			Registry<PlacedFeature> registry = BuiltinRegistries.PLACED_FEATURE;
			PlacedFeature placedFeature = registry.get(worldGenRegistryName);

			if (placedFeature != null)
			{
				OreGenBuilder builder = new OreGenBuilder().placedFeature(placedFeature);
				builder.restriction = restriction;
				builder.silkTouch = silkTouchItems.length > 0;
				Arrays.stream(silkTouchItems).forEach(builder.drops::add);

				IWorldGenRegistry jer_registry = JERAPI.getInstance().getWorldGenRegistry();
				builder.register(jer_registry);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
