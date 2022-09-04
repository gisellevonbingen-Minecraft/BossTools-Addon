package boss_tools_giselle_addon.common.entity;

import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ExperienceOrbEntityHelper
{
	public static void award(World level, Vector3d position, int amount)
	{
		while (amount > 0)
		{
			int extracting = ExperienceOrbEntity.getExperienceValue(amount);
			amount -= extracting;
			level.addFreshEntity(new ExperienceOrbEntity(level, position.x, position.y, position.z, extracting));
		}

	}

}
