package beyond_earth_giselle_addon.common.advencements;

import beyond_earth_giselle_addon.common.advencements.criterion.LanderExplodeTrigger;
import beyond_earth_giselle_addon.common.advencements.criterion.PlayerCreateSpaceStationTrigger;
import beyond_earth_giselle_addon.common.advencements.criterion.PlayerDiedTrigger;
import beyond_earth_giselle_addon.common.advencements.criterion.ThrowOnOrbitTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;

public class AddonCriteriaTriggers
{
	public static final ThrowOnOrbitTrigger THROW_ON_ORBIT = register(new ThrowOnOrbitTrigger());
	public static final PlayerDiedTrigger PLAYER_DIED = register(new PlayerDiedTrigger());
	public static final LanderExplodeTrigger LANDER_EXPLODE = register(new LanderExplodeTrigger());
	public static final PlayerCreateSpaceStationTrigger PLAYER_CREATE_SPACE_STATION = register(new PlayerCreateSpaceStationTrigger());

	public static void visit()
	{

	}

	public static <T extends CriterionTrigger<?>> T register(T trigger)
	{
		return CriteriaTriggers.register(trigger);
	}

}
