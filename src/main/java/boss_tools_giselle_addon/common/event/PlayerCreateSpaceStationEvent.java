package boss_tools_giselle_addon.common.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerCreateSpaceStationEvent extends PlayerEvent
{
	public PlayerCreateSpaceStationEvent(PlayerEntity player)
	{
		super(player);
	}

}
