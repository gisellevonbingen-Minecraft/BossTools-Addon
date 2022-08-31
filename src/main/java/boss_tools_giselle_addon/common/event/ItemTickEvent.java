package boss_tools_giselle_addon.common.event;

import net.minecraft.entity.item.ItemEntity;
import net.minecraftforge.event.entity.item.ItemEvent;

public class ItemTickEvent extends ItemEvent
{
	public ItemTickEvent(ItemEntity itemEntity)
	{
		super(itemEntity);
	}

}
