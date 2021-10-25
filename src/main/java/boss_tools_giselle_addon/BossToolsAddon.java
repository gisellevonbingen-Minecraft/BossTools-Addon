package boss_tools_giselle_addon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import boss_tools_giselle_addon.client.AddonClientProxy;
import boss_tools_giselle_addon.common.block.AddonBlocks;
import boss_tools_giselle_addon.common.fluid.FluidContainerRegistration;
import boss_tools_giselle_addon.common.fluid.FluidUtil2;
import boss_tools_giselle_addon.common.inventory.container.AddonContainers;
import boss_tools_giselle_addon.common.item.AddonItems;
import boss_tools_giselle_addon.common.tile.AddonTiles;
import boss_tools_giselle_addon.compat.CompatibleManager;
import boss_tools_giselle_addon.config.AddonConfigs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.mrscauthd.boss_tools.block.FuelBlock;

@Mod(BossToolsAddon.MODID)
public class BossToolsAddon
{
	public static final String PMODID = "boss_tools";
	public static final String MODID = "boss_tools_giselle_addon";
	public static final Logger LOGGER = LogManager.getLogger();

	public BossToolsAddon()
	{
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AddonConfigs.CommonSpec);

		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		AddonBlocks.BLOCKS.register(fml_bus);
		AddonItems.ITEMS.register(fml_bus);
		AddonTiles.TILES.register(fml_bus);
		AddonContainers.CONTAINERS.register(fml_bus);
		fml_bus.addGenericListener(Item.class, this::addEntries);

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> AddonClientProxy::new);

		MinecraftForge.EVENT_BUS.register(new EventListener());

		CompatibleManager.loadAll();
	}

	private void addEntries(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> registry = event.getRegistry();

		FluidUtil2.registerFluidContainer(new FluidContainerRegistration(registry.getValue(prl("barrel")), registry.getValue(prl("barrel_of_fuel")), FuelBlock.still, 3000));
	}

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

	public static ResourceLocation prl(String path)
	{
		return new ResourceLocation(PMODID, path);
	}

}
