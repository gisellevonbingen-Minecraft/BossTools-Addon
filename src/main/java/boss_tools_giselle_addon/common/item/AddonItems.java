package boss_tools_giselle_addon.common.item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.block.AddonBlocks;
import boss_tools_giselle_addon.common.tab.AddonTabs;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AddonItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, BossToolsAddon.MODID);
	public static final Map<RegistryObject<Block>, RegistryObject<BlockItem>> BLOCKS = new HashMap<>();

	public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(getMainItemProperties()));
	public static final RegistryObject<Item> DESH_NUGGET = ITEMS.register("desh_nugget", () -> new Item(getMainItemProperties()));
	public static final RegistryObject<Item> SILICON_NUGGET = ITEMS.register("silicon_nugget", () -> new Item(getMainItemProperties()));

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
