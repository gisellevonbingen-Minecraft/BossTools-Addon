package boss_tools_giselle_addon;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListenerFlagEdit
{
	@SubscribeEvent
	public static void onFlagShiftRightClick(RightClickBlock e)
	{
		if (e.isCanceled() == true)
		{
			return;
		}

		PlayerEntity player = e.getPlayer();

		if (player.isShiftKeyDown() == false)
		{
			return;
		}

		
	}

}
