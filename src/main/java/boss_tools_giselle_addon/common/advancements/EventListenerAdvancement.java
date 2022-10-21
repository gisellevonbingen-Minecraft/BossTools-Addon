package boss_tools_giselle_addon.common.advancements;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.event.PlayerCreateSpaceStationEvent;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.events.forgeevents.ItemGravityEvent;

public class EventListenerAdvancement
{
	private static final String NBT_KEY_THROW_ON_ORBIT = BossToolsAddon.rl("throw_on_orbit").toString();

	@SubscribeEvent
	public static void onItemToss(ItemTossEvent event)
	{
		PlayerEntity player = event.getPlayer();
		World level = player.level;

		if (level.isClientSide() == true)
		{
			return;
		}

		ItemEntity item = event.getEntityItem();

		if (player.getUUID().equals(item.getThrower()) && Methodes.isOrbitWorld(level) == true)
		{
			CompoundNBT persistent = item.getPersistentData();
			persistent.putBoolean(NBT_KEY_THROW_ON_ORBIT, true);
		}

	}

	@SubscribeEvent
	public static void onItemTick(ItemGravityEvent event)
	{
		ItemEntity item = event.getEntityItem();
		World level = item.level;

		if (level.isClientSide() == true)
		{
			return;
		}

		CompoundNBT persistent = item.getPersistentData();

		if (persistent.getBoolean(NBT_KEY_THROW_ON_ORBIT) == true)
		{
			if (Methodes.isOrbitWorld(level) == true)
			{
				if (item.position().y < 0.0D)
				{
					PlayerEntity player = level.getPlayerByUUID(item.getThrower());

					if (player != null)
					{
						AddonCriteriaTriggers.THROW_ON_ORBIT.trigger((ServerPlayerEntity) player, item.getItem());
					}

					persistent.remove(NBT_KEY_THROW_ON_ORBIT);
				}

			}

		}

	}

	@SubscribeEvent
	public static void onPlayerCreateSpaceStation(PlayerCreateSpaceStationEvent event)
	{
		PlayerEntity player = event.getPlayer();
		World level = player.level;

		if (level.isClientSide() == true)
		{
			return;
		}

		AddonCriteriaTriggers.PLAYER_CREATE_SPACE_STATION.trigger((ServerPlayerEntity) player);
	}

}
