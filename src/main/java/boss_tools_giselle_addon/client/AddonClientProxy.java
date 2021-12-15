package boss_tools_giselle_addon.client;

import boss_tools_giselle_addon.client.gui.AdvancedCompressorScreen;
import boss_tools_giselle_addon.client.gui.ElectricBlastFurnaceScreen;
import boss_tools_giselle_addon.client.gui.FuelLoaderScreen;
import boss_tools_giselle_addon.client.gui.GravityNormalizerScreen;
import boss_tools_giselle_addon.client.renderer.blockentity.FuelLoaderRenderer;
import boss_tools_giselle_addon.client.renderer.blockentity.GravityNormalizerRenderer;
import boss_tools_giselle_addon.client.util.RenderHelper;
import boss_tools_giselle_addon.common.block.entity.AddonBlockEntities;
import boss_tools_giselle_addon.common.inventory.AddonMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonClientProxy
{
	public AddonClientProxy()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::onRegisterRenderers);
		bus.addListener(this::onAtlasPreStitch);
		bus.addGenericListener(MenuType.class, this::registerMenuType);
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

}
