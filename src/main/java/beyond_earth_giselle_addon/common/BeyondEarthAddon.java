package beyond_earth_giselle_addon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import beyond_earth_giselle_addon.client.AddonClientProxy;
import beyond_earth_giselle_addon.common.compat.AddonCompatibleManager;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.content.flag.EventListenerFlagEdit;
import beyond_earth_giselle_addon.common.content.fuel.EventListenerFuelAdapter;
import beyond_earth_giselle_addon.common.content.fuel.EventListenerFuelGauge;
import beyond_earth_giselle_addon.common.content.gravity.EventListenerGravityNormalizing;
import beyond_earth_giselle_addon.common.content.proof.ProofAbstractUtils;
import beyond_earth_giselle_addon.common.item.crafting.IS2ISRecipeCache;
import beyond_earth_giselle_addon.common.network.AddonNetwork;
import beyond_earth_giselle_addon.common.registries.AddonBlockEntityTypes;
import beyond_earth_giselle_addon.common.registries.AddonBlocks;
import beyond_earth_giselle_addon.common.registries.AddonEnchantments;
import beyond_earth_giselle_addon.common.registries.AddonItems;
import beyond_earth_giselle_addon.common.registries.AddonMenuTypes;
import beyond_earth_giselle_addon.common.registries.AddonRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mrscauthd.beyond_earth.BeyondEarthMod;

@Mod(BeyondEarthAddon.MODID)
public class BeyondEarthAddon
{
	public static final String PMODID = BeyondEarthMod.MODID;
	public static final String MODID = "beyond_earth_giselle_addon";
	public static final Logger LOGGER = LogManager.getLogger();

	public BeyondEarthAddon()
	{
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AddonConfigs.CommonSpec);

		registerFML();
		registerForge();
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> AddonClientProxy::new);

		AddonCompatibleManager.visit();
	}

	public static void registerFML()
	{
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		AddonBlocks.BLOCKS.register(fml_bus);
		AddonItems.ITEMS.register(fml_bus);
		AddonEnchantments.ENCHANTMENTS.register(fml_bus);
		AddonRecipes.RECIPE_SERIALIZERS.register(fml_bus);
		AddonBlockEntityTypes.BLOCK_ENTITY_TYPES.register(fml_bus);
		AddonMenuTypes.MENU_TYPES.register(fml_bus);

		AddonNetwork.registerAll();
	}

	public static void registerForge()
	{
		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(EventListenerCommand.class);
		forge_bus.register(EventListenerFuelAdapter.class);
		forge_bus.register(EventListenerFuelGauge.class);
		forge_bus.register(EventListenerGravityNormalizing.class);
		forge_bus.register(EventListenerFlagEdit.class);
		forge_bus.register(EventListenerReload.class);

		ProofAbstractUtils.register(forge_bus);
	}

	public static void resetRecipeCaches(RecipeManager recipeManager)
	{
		IS2ISRecipeCache.clearCaches();
	}

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

	public static String tl(String category, String path)
	{
		return category + "." + MODID + "." + path;
	}

	public static String tl(String category, ResourceLocation rl)
	{
		return category + "." + rl.getNamespace() + "." + rl.getPath();
	}

	public static String tl(String category, ResourceLocation rl, String path)
	{
		return tl(category, rl) + "." + path;
	}

	public static ResourceLocation prl(String path)
	{
		return new ResourceLocation(PMODID, path);
	}

}
