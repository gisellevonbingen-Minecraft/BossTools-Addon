package boss_tools_giselle_addon.common.item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.block.AddonBlocks;
import boss_tools_giselle_addon.common.tab.AddonTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AddonItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, BossToolsAddon.MODID);
	public static final Map<RegistryObject<Block>, RegistryObject<BlockItem>> BLOCKS = new HashMap<>();

	static
	{
		for (RegistryObject<Block> blockRegistry : AddonBlocks.BLOCKS.getEntries())
		{
			String path = blockRegistry.getId().getPath();
			RegistryObject<BlockItem> itemRegistry = ITEMS.register(path, getBlockItemSupplier(blockRegistry));
			BLOCKS.put(blockRegistry, itemRegistry);
		}

	}

	public static Item.Properties getMainItemProperties()
	{
		return new Item.Properties().tab(AddonTabs.tab_main);
	}

	private static Supplier<? extends BlockItem> getBlockItemSupplier(RegistryObject<Block> registryObject)
	{
		return () -> new BlockItem(registryObject.get(), getMainItemProperties());
	}

	private AddonItems()
	{

	}

}
