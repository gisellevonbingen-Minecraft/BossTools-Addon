package beyond_earth_giselle_addon.common.advencements;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.event.LanderExplodeEvent;
import beyond_earth_giselle_addon.common.event.PlayerCreateSpaceStationEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.entities.LanderEntity;
import net.mrscauthd.beyond_earth.events.Methods;
import net.mrscauthd.beyond_earth.events.forge.ItemEntityTickEndEvent;

public class EventListenerAdvancement
{
	private static final String NBT_KEY_THROW_ON_ORBIT = BeyondEarthAddon.rl("throw_on_orbit").toString();

	@SubscribeEvent
	public static void onItemToss(ItemTossEvent event)
	{
		Player player = event.getPlayer();
		Level level = player.level;

		if (level.isClientSide() == true)
		{
			return;
		}

		ItemEntity item = event.getEntityItem();

		if (player.getUUID().equals(item.getThrower()) == true && Methods.isOrbitWorld(level) == true)
		{
			CompoundTag persistent = item.getPersistentData();
			persistent.putBoolean(NBT_KEY_THROW_ON_ORBIT, true);
		}

	}

	@SubscribeEvent
	public static void onItemTick(ItemEntityTickEndEvent event)
	{
		ItemEntity item = event.getEntityItem();
		Level level = item.level;

		if (level.isClientSide() == true)
		{
			return;
		}

		CompoundTag persistent = item.getPersistentData();

		if (persistent.getBoolean(NBT_KEY_THROW_ON_ORBIT) == true)
		{
			if (Methods.isOrbitWorld(level) == true)
			{
				if (item.position().y < 0.0D)
				{
					Player player = level.getPlayerByUUID(item.getThrower());

					if (player != null)
					{
						AddonCriteriaTriggers.THROW_ON_ORBIT.trigger((ServerPlayer) player, item.getItem());
					}

					persistent.remove(NBT_KEY_THROW_ON_ORBIT);
				}

			}

		}

	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event)
	{
		LivingEntity living = event.getEntityLiving();

		if (living instanceof ServerPlayer player)
		{
			AddonCriteriaTriggers.PLAYER_DIED.trigger(player, event.getSource().getMsgId());
		}

	}

	@SubscribeEvent
	public static void onLanderExplode(LanderExplodeEvent event)
	{
		LanderEntity lander = event.getLanderEntity();
		Level level = lander.getLevel();

		if (level.isClientSide() == true)
		{
			return;
		}
		else
		{
			for (Entity passenger : lander.getPassengers())
			{
				if (passenger instanceof ServerPlayer player)
				{
					AddonCriteriaTriggers.LANDER_EXPLODE.trigger(player, lander.position());
				}

			}

		}

	}

	@SubscribeEvent
	public static void onPlayerCreateSpaceStation(PlayerCreateSpaceStationEvent event)
	{
		Player player = event.getPlayer();

		if (player instanceof ServerPlayer)
		{
			AddonCriteriaTriggers.PLAYER_CREATE_SPACE_STATION.trigger((ServerPlayer) player);
		}

	}

}
