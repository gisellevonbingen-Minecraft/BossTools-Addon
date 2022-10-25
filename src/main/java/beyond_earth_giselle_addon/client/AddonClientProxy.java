package beyond_earth_giselle_addon.client;

import beyond_earth_giselle_addon.client.gui.AdvancedCompressorScreen;
import beyond_earth_giselle_addon.client.gui.ElectricBlastFurnaceScreen;
import beyond_earth_giselle_addon.client.gui.FuelLoaderScreen;
import beyond_earth_giselle_addon.client.overlay.OxygenCanOverlay;
import beyond_earth_giselle_addon.client.renderer.blockentity.FuelLoaderRenderer;
import beyond_earth_giselle_addon.client.util.RenderHelper;
import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.registries.AddonBlockEntityTypes;
import beyond_earth_giselle_addon.common.registries.AddonMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonClientProxy
{
	public AddonClientProxy()
	{
		this.registerFML();
		this.registerForge();
	}

	public void registerFML()
	{
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		fml_bus.addListener(this::onAtlasPreStitch);
		fml_bus.addListener(this::onRegisterRenderers);
		fml_bus.addListener(this::registerMenuType);
		fml_bus.addListener(this::onRegisterGuiOverlay);
	}

	public void registerForge()
	{
		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(EventListenerEnchantmentTooltip.class);
		forge_bus.addListener(this::onRecipesUpdated);
	}

	public void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerBlockEntityRenderer(AddonBlockEntityTypes.FUEL_LOADER.get(), FuelLoaderRenderer::new);
	}

	public void registerMenuType(FMLClientSetupEvent event)
	{
		MenuScreens.register(AddonMenuTypes.FUEL_LOADER.get(), FuelLoaderScreen::new);
		MenuScreens.register(AddonMenuTypes.ELECTRIC_BLAST_FURNACE.get(), ElectricBlastFurnaceScreen::new);
		MenuScreens.register(AddonMenuTypes.ADVANCED_COMPRESSOR.get(), AdvancedCompressorScreen::new);
	}

	public void onRegisterGuiOverlay(RegisterGuiOverlaysEvent event)
	{
		event.registerBelowAll("oxygen_can", new OxygenCanOverlay());
	}

	public void onAtlasPreStitch(TextureStitchEvent.Pre event)
	{
		event.addSprite(RenderHelper.TILE_SURFACE);
	}

	public void onRecipesUpdated(RecipesUpdatedEvent event)
	{
		BeyondEarthAddon.resetRecipeCaches();
	}

}
