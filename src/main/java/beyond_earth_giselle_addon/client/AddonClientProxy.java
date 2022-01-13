package beyond_earth_giselle_addon.client;

import beyond_earth_giselle_addon.client.gui.AdvancedCompressorScreen;
import beyond_earth_giselle_addon.client.gui.ElectricBlastFurnaceScreen;
import beyond_earth_giselle_addon.client.gui.FuelLoaderScreen;
import beyond_earth_giselle_addon.client.gui.GravityNormalizerScreen;
import beyond_earth_giselle_addon.client.renderer.blockentity.FuelLoaderRenderer;
import beyond_earth_giselle_addon.client.renderer.blockentity.GravityNormalizerRenderer;
import beyond_earth_giselle_addon.client.util.RenderHelper;
import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.block.entity.AddonBlockEntities;
import beyond_earth_giselle_addon.common.inventory.AddonMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonClientProxy
{
	public AddonClientProxy()
	{
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		fml_bus.addListener(this::onAtlasPreStitch);
		fml_bus.addListener(this::onRegisterRenderers);
		fml_bus.addGenericListener(MenuType.class, this::registerMenuType);

		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
		forgeEventBus.register(EventListenerRenderSpaceSuitOverlay.class);
		forgeEventBus.register(EventListenerRenderOxygenCanOverlay.class);
		forgeEventBus.addListener(this::onRecipesUpdated);
	}

	public void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerBlockEntityRenderer(AddonBlockEntities.FUEL_LOADER.get(), FuelLoaderRenderer::new);
		event.registerBlockEntityRenderer(AddonBlockEntities.GRAVITY_NORMALIZER.get(), GravityNormalizerRenderer::new);
	}

	public void registerMenuType(RegistryEvent.Register<MenuType<?>> event)
	{
		MenuScreens.register(AddonMenuTypes.FUEL_LOADER.get(), FuelLoaderScreen::new);
		MenuScreens.register(AddonMenuTypes.ELECTRIC_BLAST_FURNACE.get(), ElectricBlastFurnaceScreen::new);
		MenuScreens.register(AddonMenuTypes.ADVANCED_COMPRESSOR.get(), AdvancedCompressorScreen::new);
		MenuScreens.register(AddonMenuTypes.GRAVITY_NORMALIZER.get(), GravityNormalizerScreen::new);
	}

	public void onAtlasPreStitch(TextureStitchEvent.Pre event)
	{
		event.addSprite(RenderHelper.TILE_SURFACE);
	}

	public void onRecipesUpdated(RecipesUpdatedEvent event)
	{
		BeyondEarthAddon.resetRecipeCaches(event.getRecipeManager());
	}
	
}
