package boss_tools_giselle_addon.common.event;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventListener;

public class LivingProvideProofDurationEvent extends LivingEvent
{
	public static <E extends LivingProvideProofDurationEvent> E postUntilDuration(E event)
	{
		MinecraftForge.EVENT_BUS.post(event, LivingProvideProofDurationEvent::dispatch);
		return event;
	}

	public static void dispatch(IEventListener listener, Event event)
	{
		if (event instanceof LivingProvideProofDurationEvent)
		{
			LivingProvideProofDurationEvent event2 = (LivingProvideProofDurationEvent) event;

			if (event2.getProofDuration() > 0)
			{
				return;
			}

		}

		listener.invoke(event);
	}

	private int proofDuration;

	public LivingProvideProofDurationEvent(LivingEntity entity)
	{
		super(entity);
	}

	public int getProofDuration()
	{
		return this.proofDuration;
	}

	public void setProofDuration(int proofDuration)
	{
		this.proofDuration = proofDuration;
	}

}
