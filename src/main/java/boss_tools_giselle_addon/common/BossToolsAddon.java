package boss_tools_giselle_addon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import boss_tools_giselle_addon.client.AddonClientProxy;
import boss_tools_giselle_addon.common.block.AddonBlocks;
import boss_tools_giselle_addon.common.capability.CapabilityChargeModeHandler;
import boss_tools_giselle_addon.common.compat.AddonCompatibleManager;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.inventory.container.AddonContainers;
import boss_tools_giselle_addon.common.item.AddonItems;
import boss_tools_giselle_addon.common.item.crafting.AddonRecipes;
import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.tile.AddonTiles;
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

		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		fml_bus.addListener(BossToolsAddon::init);
		AddonBlocks.BLOCKS.register(fml_bus);
		AddonItems.ITEMS.register(fml_bus);
		AddonRecipes.RECIPE_SERIALIZERS.register(fml_bus);
		AddonTiles.TILES.register(fml_bus);
		AddonContainers.CONTAINERS.register(fml_bus);
		AddonNetwork.registerAll();

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> AddonClientProxy::new);

		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
		forgeEventBus.register(EventListenerFuelAdapter.class);
		forgeEventBus.register(EventListenerGauge.class);
		forgeEventBus.register(EventListenerGravity.class);
		forgeEventBus.register(EventListenerFlagEdit.class);

		AddonCompatibleManager.visit();
	}

	public static void init(FMLCommonSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			CapabilityChargeModeHandler.register();
		});
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
