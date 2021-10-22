package giselle.bosstools_addon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import giselle.bosstools_addon.client.AddonClientProxy;
import giselle.bosstools_addon.common.block.AddonBlocks;
import giselle.bosstools_addon.common.fluid.FluidContainerRegistration;
import giselle.bosstools_addon.common.fluid.FluidUtil2;
import giselle.bosstools_addon.common.inventory.container.AddonContainers;
import giselle.bosstools_addon.common.item.AddonItems;
import giselle.bosstools_addon.common.tile.AddonTiles;
import giselle.bosstools_addon.compat.CompatibleManager;
import giselle.bosstools_addon.config.AddonConfigs;
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
import net.mrscauthd.boss_tools.block.FuelBlock;
import net.mrscauthd.boss_tools.item.BucketBigItem;
import net.mrscauthd.boss_tools.item.FuelBucketBigItem;

@Mod(BossToolsAddon.MODID)
public class BossToolsAddon
{
	public static final String PMODID = "boss_tools";
	public static final String MODID = "boss_tools_addon";
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
		FluidUtil2.registerFluidContainer(new FluidContainerRegistration(BucketBigItem.block, FuelBucketBigItem.block, FuelBlock.still, 3000));
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
