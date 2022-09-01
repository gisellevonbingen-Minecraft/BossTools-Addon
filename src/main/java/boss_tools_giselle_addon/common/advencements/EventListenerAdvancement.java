package boss_tools_giselle_addon.common.advencements;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.event.ItemTickEvent;
import boss_tools_giselle_addon.common.event.LanderExplodeEvent;
import boss_tools_giselle_addon.common.event.PlayerCreateSpaceStationEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.entity.LanderEntity;
import net.mrscauthd.boss_tools.events.Methodes;

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

		if (player.getUUID().equals(item.getThrower()) == true && Methodes.isOrbitWorld(level) == true)
		{
			CompoundNBT persistent = item.getPersistentData();
			persistent.putBoolean(NBT_KEY_THROW_ON_ORBIT, true);
		}

	}

	@SubscribeEvent
	public static void onItemTick(ItemTickEvent event)
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
	public static void onLivingDeath(LivingDeathEvent event)
	{
		LivingEntity living = event.getEntityLiving();

		if (!(living instanceof ServerPlayerEntity))
		{
			return;
		}

		ServerPlayerEntity player = (ServerPlayerEntity) living;
		AddonCriteriaTriggers.PLAYER_DIED.trigger(player, event.getSource().getMsgId());
	}

	@SubscribeEvent
	public static void onLanderExplode(LanderExplodeEvent event)
	{
		LanderEntity lander = event.getLanderEntity();
		World level = lander.level;

		if (level.isClientSide() == true)
		{
			return;
		}

		for (Entity passenger : lander.getPassengers())
		{
			if (passenger instanceof ServerPlayerEntity)
			{
				AddonCriteriaTriggers.LANDER_EXPLODE.trigger((ServerPlayerEntity) passenger, lander.position());
			}

		}

	}

	@SubscribeEvent
	public static void onPlayerCreateSpaceStation(PlayerCreateSpaceStationEvent event)
	{
		PlayerEntity player = event.getPlayer();

		if (player instanceof ServerPlayerEntity)
		{
			AddonCriteriaTriggers.PLAYER_CREATE_SPACE_STATION.trigger((ServerPlayerEntity) player);
		}

	}

}
