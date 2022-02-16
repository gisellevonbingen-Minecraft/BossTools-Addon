package beyond_earth_giselle_addon.common.registries;

import net.minecraft.world.item.Item;

public class ItemDeferredRegister extends DeferredRegisterWrapper<Item>
{
	public ItemDeferredRegister(String modid)
	{
		super(modid, Item.class);
	}

}
