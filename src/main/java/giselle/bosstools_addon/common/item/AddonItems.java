package giselle.bosstools_addon.common.item;

import giselle.bosstools_addon.BossToolsAddon;
import giselle.bosstools_addon.common.block.AddonBlocks;
import giselle.bosstools_addon.common.tab.AddonTabs;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AddonItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, BossToolsAddon.MODID);
	public static final RegistryObject<BlockItem> OXYGEN_ACCEPTER = ITEMS.register("oxygen_accepter", () -> new BlockItem(AddonBlocks.OXYGEN_ACCEPTER.get(), getMainItemProperties()));

	public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(getMainItemProperties()));
	public static final RegistryObject<Item> DESH_NUGGET = ITEMS.register("desh_nugget", () -> new Item(getMainItemProperties()));
	public static final RegistryObject<Item> SILICON_NUGGET = ITEMS.register("silicon_nugget", () -> new Item(getMainItemProperties()));

	public static final RegistryObject<Item> SPACE_FIRE_PROOF_PLATE = ITEMS.register("space_fire_proof_plate", () -> new Item(getMainItemProperties()));
	public static final RegistryObject<Item> SPACE_BREATH_GEAR = ITEMS.register("space_breath_gear", () -> new Item(getMainItemProperties()));
	public static final RegistryObject<Item> VENUS_ACID_PROOF_PLATE = ITEMS.register("venus_acid_proof_plate", () -> new Item(getMainItemProperties()));

	public static Item.Properties getMainItemProperties()
	{
		return new Item.Properties().tab(AddonTabs.tab_main);
	}

	private AddonItems()
	{

	}

}
