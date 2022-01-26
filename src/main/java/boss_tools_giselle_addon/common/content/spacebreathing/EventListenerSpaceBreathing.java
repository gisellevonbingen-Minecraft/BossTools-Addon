package boss_tools_giselle_addon.common.content.spacebreathing;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.ModInnet;

public class EventListenerSpaceBreathing
{
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent e)
	{
		LivingEntity entity = e.getEntityLiving();

		if (entity.level.isClientSide() == true)
		{
			return;
		}

		SpaceBreathingUtils.reduceProofDuration(entity);
	}

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent e)
	{
		if (e.isCanceled() == true)
		{
			return;
		}
		else if (e.getSource() != ModInnet.DAMAGE_SOURCE_OXYGEN)
		{
			return;
		}

		LivingEntity entity = e.getEntityLiving();

		if (SpaceBreathingUtils.tryProvideOxygen(entity) == true)
		{
			e.setCanceled(true);
		}

	}

}
