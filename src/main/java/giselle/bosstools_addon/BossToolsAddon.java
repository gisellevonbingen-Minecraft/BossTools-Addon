package giselle.bosstools_addon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import giselle.bosstools_addon.common.block.AddonBlocks;
import giselle.bosstools_addon.common.item.AddonItems;
import giselle.bosstools_addon.common.tile.AddonTiles;
import giselle.bosstools_addon.compat.CompatibleManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BossToolsAddon.MODID)
public class BossToolsAddon
{
	public static final String PMODID = "boss_tools";
	public static final String MODID = "boss_tools_addon";
	public static final Logger LOGGER = LogManager.getLogger();

	public BossToolsAddon()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		AddonBlocks.BLOCKS.register(bus);
		AddonItems.ITEMS.register(bus);
		AddonTiles.TILES.register(bus);

		CompatibleManager.loadAll();
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
