package boss_tools_giselle_addon.common.registries;

import net.minecraft.item.Item;

public class ItemDeferredRegister extends DeferredRegisterWrapper<Item>
{
	public ItemDeferredRegister(String modid)
	{
		super(modid, Item.class);
	}

}
