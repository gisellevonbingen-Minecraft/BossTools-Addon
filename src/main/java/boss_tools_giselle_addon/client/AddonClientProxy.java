package boss_tools_giselle_addon.client;

import boss_tools_giselle_addon.client.gui.AdvancedCompressorScreen;
import boss_tools_giselle_addon.client.gui.ElectricBlastFurnaceScreen;
import boss_tools_giselle_addon.client.gui.FuelLoaderScreen;
import boss_tools_giselle_addon.client.gui.GravityNormalizerScreen;
import boss_tools_giselle_addon.client.renderer.tileentity.FlagRenderer;
import boss_tools_giselle_addon.client.renderer.tileentity.FuelLoaderRenderer;
import boss_tools_giselle_addon.client.renderer.tileentity.GravityNormalizerRenderer;
import boss_tools_giselle_addon.client.util.RenderHelper;
import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.AddonContainers;
import boss_tools_giselle_addon.common.tile.AddonTiles;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mrscauthd.boss_tools.ModInnet;

public class AddonClientProxy
{
	public AddonClientProxy()
	{
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		fml_bus.addListener(this::onAtlasPreStitch);
		fml_bus.addListener(this::onClientSetup);
		fml_bus.addGenericListener(ContainerType.class, this::registerContainer);

		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
		forgeEventBus.register(EventListenerRenderSpaceSuitOverlay.class);
		forgeEventBus.register(EventListenerRenderOxygenCanOverlay.class);
		forgeEventBus.addListener(this::onRecipesUpdated);
	}

	public void onClientSetup(FMLClientSetupEvent event)
	{
		ClientRegistry.bindTileEntityRenderer(AddonTiles.FUEL_LOADER.get(), FuelLoaderRenderer::new);
		ClientRegistry.bindTileEntityRenderer(AddonTiles.GRAVITY_NORMALIZER.get(), GravityNormalizerRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ModInnet.FLAG.get(), FlagRenderer::new);
	}

	public void registerContainer(RegistryEvent.Register<ContainerType<?>> event)
	{
		ScreenManager.register(AddonContainers.FUEL_LOADER.get(), FuelLoaderScreen::new);
		ScreenManager.register(AddonContainers.ELECTRIC_BLAST_FURNACE.get(), ElectricBlastFurnaceScreen::new);
		ScreenManager.register(AddonContainers.ADVANCED_COMPRESSOR.get(), AdvancedCompressorScreen::new);
		ScreenManager.register(AddonContainers.GRAVITY_NORMALIZER.get(), GravityNormalizerScreen::new);
	}

	public void onAtlasPreStitch(TextureStitchEvent.Pre event)
	{
		event.addSprite(RenderHelper.TILE_SURFACE);
	}

	public void onRecipesUpdated(RecipesUpdatedEvent event)
	{
		BossToolsAddon.resetRecipeCaches(event.getRecipeManager());
	}

}
