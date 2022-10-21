package boss_tools_giselle_addon.common.advancements;

import boss_tools_giselle_addon.common.advancements.criterion.PlayerCreateSpaceStationTrigger;
import boss_tools_giselle_addon.common.advancements.criterion.ThrowOnOrbitTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;

public class AddonCriteriaTriggers
{
	public static final ThrowOnOrbitTrigger THROW_ON_ORBIT = register(new ThrowOnOrbitTrigger());
	public static final PlayerCreateSpaceStationTrigger PLAYER_CREATE_SPACE_STATION = register(new PlayerCreateSpaceStationTrigger());

	public static void visit()
	{

	}

	public static <T extends AbstractCriterionTrigger<?>> T register(T trigger)
	{
		return CriteriaTriggers.register(trigger);
	}

}
