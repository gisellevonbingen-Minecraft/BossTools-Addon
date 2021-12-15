package net.mrscauthd.boss_tools.events;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.renderer.TileEntityBoxRenderer;
import net.mrscauthd.boss_tools.entity.renderer.alien.AlienModel;
import net.mrscauthd.boss_tools.entity.renderer.alienzombie.AlienZombieModel;
import net.mrscauthd.boss_tools.entity.renderer.alienzombie.AlienZombieRenderer;
import net.mrscauthd.boss_tools.entity.renderer.flag.TileEntityHeadModel;
import net.mrscauthd.boss_tools.entity.renderer.flag.TileEntityHeadRenderer;
import net.mrscauthd.boss_tools.entity.renderer.lander.LanderModel;
import net.mrscauthd.boss_tools.entity.renderer.lander.LanderRenderer;
import net.mrscauthd.boss_tools.entity.renderer.mogler.MoglerModel;
import net.mrscauthd.boss_tools.entity.renderer.mogler.MoglerRenderer;
import net.mrscauthd.boss_tools.entity.renderer.pygro.PygroModel;
import net.mrscauthd.boss_tools.entity.renderer.pygro.PygroRenderer;
import net.mrscauthd.boss_tools.entity.renderer.rockettier1.RocketTier1Model;
import net.mrscauthd.boss_tools.entity.renderer.rockettier1.RocketTier1Renderer;
import net.mrscauthd.boss_tools.entity.renderer.rockettier2.RocketTier2Model;
import net.mrscauthd.boss_tools.entity.renderer.rockettier2.RocketTier2Renderer;
import net.mrscauthd.boss_tools.entity.renderer.rockettier3.RocketTier3Model;
import net.mrscauthd.boss_tools.entity.renderer.rockettier3.RocketTier3Renderer;
import net.mrscauthd.boss_tools.entity.renderer.rover.RoverModel;
import net.mrscauthd.boss_tools.entity.renderer.rover.RoverRenderer;
import net.mrscauthd.boss_tools.entity.renderer.spacesuit.SpaceSuitModel;
import net.mrscauthd.boss_tools.entity.renderer.starcrawler.StarCrawlerModel;
import net.mrscauthd.boss_tools.entity.renderer.starcrawler.StarCrawlerRenderer;
import net.mrscauthd.boss_tools.gui.screens.blastfurnace.BlastFurnaceGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.coalgenerator.CoalGeneratorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.compressor.CompressorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.fuelrefinery.FuelRefineryGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.lander.LanderGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.nasaworkbench.NasaWorkbenchGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.oxygenbubbledistributor.OxygenBubbleDistributorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.oxygenloader.OxygenLoaderGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.planetselection.PlanetSelectionGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.rocket.RocketGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.rover.RoverGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.solarpanel.SolarPanelGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.waterpump.WaterPumpGuiWindow;
import net.mrscauthd.boss_tools.particle.LargeFlameParticle;
import net.mrscauthd.boss_tools.particle.SmokeParticle;
import net.mrscauthd.boss_tools.particle.VenusRainParticle;
import net.mrscauthd.boss_tools.entity.renderer.alien.AlienRenderer;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
	public static KeyMapping key1;

	@SubscribeEvent
	public static void registerEntityRenderingHandler(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModInnet.ALIEN.get(), AlienRenderer::new);
		event.registerEntityRenderer(ModInnet.ALIEN_ZOMBIE.get(), AlienZombieRenderer::new);
		event.registerEntityRenderer(ModInnet.STAR_CRAWLER.get(), StarCrawlerRenderer::new);
		event.registerEntityRenderer(ModInnet.PYGRO.get(), (p_174068_) -> {
			return new PygroRenderer(p_174068_, PygroModel.LAYER_LOCATION, ModelLayers.PIGLIN_INNER_ARMOR, ModelLayers.PIGLIN_OUTER_ARMOR);
		});
		event.registerEntityRenderer(ModInnet.MOGLER.get(), MoglerRenderer::new);
		event.registerEntityRenderer(ModInnet.TIER_1_ROCKET.get(), RocketTier1Renderer::new);
		event.registerEntityRenderer(ModInnet.TIER_2_ROCKET.get(), RocketTier2Renderer::new);
		event.registerEntityRenderer(ModInnet.TIER_3_ROCKET.get(), RocketTier3Renderer::new);
		event.registerEntityRenderer(ModInnet.LANDER.get(), LanderRenderer::new);
		event.registerEntityRenderer(ModInnet.ROVER.get(), RoverRenderer::new);

		event.registerEntityRenderer(ModInnet.ALIEN_SPIT_ENTITY.get(), renderManager -> new ThrownItemRenderer(renderManager, 1, true));

		event.registerBlockEntityRenderer(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR.get(), TileEntityBoxRenderer::new);

		event.registerBlockEntityRenderer(ModInnet.FLAG.get(), TileEntityHeadRenderer::new);
	}

	@SubscribeEvent
	public static void registerEntityRenderingHandler(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(AlienModel.LAYER_LOCATION, AlienModel::createBodyLayer);
		event.registerLayerDefinition(AlienZombieModel.LAYER_LOCATION, AlienZombieModel::createBodyLayer);
		event.registerLayerDefinition(StarCrawlerModel.LAYER_LOCATION, StarCrawlerModel::createBodyLayer);
		event.registerLayerDefinition(PygroModel.LAYER_LOCATION, PygroModel::createBodyLayer);
		event.registerLayerDefinition(MoglerModel.LAYER_LOCATION, MoglerModel::createBodyLayer);

		event.registerLayerDefinition(TileEntityHeadModel.LAYER_LOCATION, TileEntityHeadModel::createHumanoidHeadLayer);
		
		event.registerLayerDefinition(RocketTier1Model.LAYER_LOCATION, RocketTier1Model::createBodyLayer);
		event.registerLayerDefinition(RocketTier2Model.LAYER_LOCATION, RocketTier2Model::createBodyLayer);
		event.registerLayerDefinition(RocketTier3Model.LAYER_LOCATION, RocketTier3Model::createBodyLayer);
		event.registerLayerDefinition(LanderModel.LAYER_LOCATION, LanderModel::createBodyLayer);
		event.registerLayerDefinition(RoverModel.LAYER_LOCATION, RoverModel::createBodyLayer);

		event.registerLayerDefinition(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION, SpaceSuitModel.SPACE_SUIT_P1::createBodyLayer);
		event.registerLayerDefinition(SpaceSuitModel.SPACE_SUIT_P2.LAYER_LOCATION, SpaceSuitModel.SPACE_SUIT_P2::createBodyLayer);
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		//GUIS
		MenuScreens.register(ModInnet.ROCKET_GUI.get(), RocketGuiWindow::new);
		MenuScreens.register(ModInnet.BLAST_FURNACE_GUI.get(), BlastFurnaceGuiWindow::new);
		MenuScreens.register(ModInnet.COMPRESSOR_GUI.get(), CompressorGuiWindow::new);
		MenuScreens.register(ModInnet.FUEL_REFINERY_GUI.get(), FuelRefineryGuiWindow::new);
		MenuScreens.register(ModInnet.COAL_GENERATOR_GUI.get(), CoalGeneratorGuiWindow::new);
		MenuScreens.register(ModInnet.NASA_WORKBENCH_GUI.get(), NasaWorkbenchGuiWindow::new);
		MenuScreens.register(ModInnet.OXYGEN_LOADER_GUI.get(), OxygenLoaderGuiWindow::new);
		MenuScreens.register(ModInnet.SOLAR_PANEL_GUI.get(), SolarPanelGuiWindow::new);
		MenuScreens.register(ModInnet.WATER_PUMP_GUI.get(), WaterPumpGuiWindow::new);
		MenuScreens.register(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR_GUI.get(), OxygenBubbleDistributorGuiWindow::new);
		MenuScreens.register(ModInnet.LANDER_GUI.get(), LanderGuiWindow::new);
		MenuScreens.register(ModInnet.ROVER_GUI.get(), RoverGuiWindow::new);
		MenuScreens.register(ModInnet.PLANET_SELECTION_GUI.get(), PlanetSelectionGuiWindow::new);

		//Key Binding Registrys
		key1 = new KeyMapping("key." + BossToolsMod.ModId + ".rocket_start", GLFW.GLFW_KEY_SPACE, "key.categories." + BossToolsMod.ModId);
		ClientRegistry.registerKeyBinding(key1);

		//Fluid Translucent Renderer
		ItemBlockRenderTypes.setRenderLayer(ModInnet.FLOWING_FUEL.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(ModInnet.FUEL_STILL.get(), RenderType.translucent());

		//Block Translucent Renderer
		ItemBlockRenderTypes.setRenderLayer(ModInnet.COAL_LANTERN_BLOCK.get(), RenderType.translucent());

		//Cutout
		ItemBlockRenderTypes.setRenderLayer(ModInnet.NASA_WORKBENCH_BLOCK.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(ModInnet.WATER_PUMP_BLOCK.get(), RenderType.cutout());
	}

	@SubscribeEvent
	public static void registerParticlesFactory(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particleEngine.register(ModInnet.VENUS_RAIN_PARTICLE.get(), VenusRainParticle.ParticleFactory::new);
		Minecraft.getInstance().particleEngine.register(ModInnet.LARGE_FLAME_PARTICLE.get(), LargeFlameParticle.ParticleFactory::new);
		Minecraft.getInstance().particleEngine.register(ModInnet.SMOKE_PARTICLE.get(), SmokeParticle.ParticleFactory::new);
	}
}
