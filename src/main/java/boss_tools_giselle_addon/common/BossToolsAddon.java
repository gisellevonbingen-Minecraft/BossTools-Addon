package boss_tools_giselle_addon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import boss_tools_giselle_addon.client.AddonClientProxy;
import boss_tools_giselle_addon.common.capability.CapabilityChargeModeHandler;
import boss_tools_giselle_addon.common.capability.CapabilityOxygenCharger;
import boss_tools_giselle_addon.common.compat.AddonCompatibleManager;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.content.alien.AddonAlienTrade;
import boss_tools_giselle_addon.common.content.flag.EventListenerFlagEdit;
import boss_tools_giselle_addon.common.content.fuel.EventListenerFuelAdapter;
import boss_tools_giselle_addon.common.content.fuel.EventListenerFuelGauge;
import boss_tools_giselle_addon.common.content.gravity.EventListenerGravityNormalizing;
import boss_tools_giselle_addon.common.content.proof.ProofAbstractUtils;
import boss_tools_giselle_addon.common.enchantment.EventListenerEnchantmentTooltip;
import boss_tools_giselle_addon.common.item.crafting.IS2ISRecipeCache;
import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.registries.AddonBlocks;
import boss_tools_giselle_addon.common.registries.AddonContainerTypes;
import boss_tools_giselle_addon.common.registries.AddonEnchantments;
import boss_tools_giselle_addon.common.registries.AddonItems;
import boss_tools_giselle_addon.common.registries.AddonRecipes;
import boss_tools_giselle_addon.common.registries.AddonTileEntityTypes;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mrscauthd.boss_tools.BossToolsMod;

@Mod(BossToolsAddon.MODID)
public class BossToolsAddon
{
	public static final String PMODID = BossToolsMod.ModId;
	public static final String MODID = "boss_tools_giselle_addon";
	public static final Logger LOGGER = LogManager.getLogger();

	public BossToolsAddon()
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

		fml_bus.addListener(BossToolsAddon::init);
		AddonBlocks.BLOCKS.register(fml_bus);
		AddonItems.ITEMS.register(fml_bus);
		AddonEnchantments.ENCHANTMENTS.register(fml_bus);
		AddonRecipes.RECIPE_SERIALIZERS.register(fml_bus);
		AddonTileEntityTypes.TILE_ENTITY_TYPES.register(fml_bus);
		AddonContainerTypes.CONTAINER_TYPES.register(fml_bus);

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

	public static void init(FMLCommonSetupEvent event)
	{
		CapabilityChargeModeHandler.register();
		CapabilityOxygenCharger.register();
	}

	public static void resetRecipeCaches(RecipeManager recipeManager)
	{
		IS2ISRecipeCache.clearCaches();
		AddonAlienTrade.registerTrades(recipeManager);
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
