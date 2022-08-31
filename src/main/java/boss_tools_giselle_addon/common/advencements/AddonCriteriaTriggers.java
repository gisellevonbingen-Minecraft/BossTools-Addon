package boss_tools_giselle_addon.common.advencements;

import boss_tools_giselle_addon.common.advencements.criterion.LanderExplodeTrigger;
import boss_tools_giselle_addon.common.advencements.criterion.PlayerDiedTrigger;
import boss_tools_giselle_addon.common.advencements.criterion.ThrowOnOrbitTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;

public class AddonCriteriaTriggers
{
	public static final ThrowOnOrbitTrigger THROW_ON_ORBIT = register(new ThrowOnOrbitTrigger());
	public static final PlayerDiedTrigger PLAYER_DIED = register(new PlayerDiedTrigger());
	public static final LanderExplodeTrigger LANDER_EXPLODE = register(new LanderExplodeTrigger());

	public static void visit()
	{

	}

	public static <T extends ICriterionTrigger<?>> T register(T trigger)
	{
		return CriteriaTriggers.register(trigger);
	}

}
