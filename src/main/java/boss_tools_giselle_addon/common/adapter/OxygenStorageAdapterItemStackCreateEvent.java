package boss_tools_giselle_addon.common.adapter;

import net.minecraft.item.ItemStack;

public class OxygenStorageAdapterItemStackCreateEvent extends AdapterCreateEvent<ItemStack, OxygenStorageAdapter<? extends ItemStack>>
{
	public OxygenStorageAdapterItemStackCreateEvent(ItemStack target)
	{
		super(target);
	}

}