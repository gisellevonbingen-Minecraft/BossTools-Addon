package boss_tools_giselle_addon.client;

import boss_tools_giselle_addon.client.gui.AdvancedCompressorScreen;
import boss_tools_giselle_addon.client.gui.ElectricBlastFurnaceScreen;
import boss_tools_giselle_addon.client.gui.FuelLoaderScreen;
import boss_tools_giselle_addon.client.gui.GravityNormalizerScreen;
import boss_tools_giselle_addon.client.renderer.tileentity.FuelLoaderRenderer;
import boss_tools_giselle_addon.client.renderer.tileentity.GravityNormalizerRenderer;
import boss_tools_giselle_addon.client.util.RenderHelper;
import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.registries.AddonContainerTypes;
import boss_tools_giselle_addon.common.registries.AddonTileEntityTypes;
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
		fml_bus.addListener(this::onClientSetup);
		fml_bus.addGenericListener(ContainerType.class, this::registerContainer);
	}

	public void registerForge()
	{
		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(EventListenerRenderSpaceSuitOverlay.class);
		forge_bus.register(EventListenerRenderOxygenCanOverlay.class);
		forge_bus.addListener(this::onRecipesUpdated);
	}

	public void onClientSetup(FMLClientSetupEvent event)
	{
		ClientRegistry.bindTileEntityRenderer(AddonTileEntityTypes.FUEL_LOADER.get(), FuelLoaderRenderer::new);
		ClientRegistry.bindTileEntityRenderer(AddonTileEntityTypes.GRAVITY_NORMALIZER.get(), GravityNormalizerRenderer::new);
	}

	public void registerContainer(RegistryEvent.Register<ContainerType<?>> event)
	{
		ScreenManager.register(AddonContainerTypes.FUEL_LOADER.get(), FuelLoaderScreen::new);
		ScreenManager.register(AddonContainerTypes.ELECTRIC_BLAST_FURNACE.get(), ElectricBlastFurnaceScreen::new);
		ScreenManager.register(AddonContainerTypes.ADVANCED_COMPRESSOR.get(), AdvancedCompressorScreen::new);
		ScreenManager.register(AddonContainerTypes.GRAVITY_NORMALIZER.get(), GravityNormalizerScreen::new);
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
