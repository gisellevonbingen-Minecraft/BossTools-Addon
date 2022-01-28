package boss_tools_giselle_addon.common.content.gravity;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.util.NBTUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.EntityEvent;
import net.mrscauthd.boss_tools.events.Gravity;
import net.mrscauthd.boss_tools.events.Gravity.GravityType;

public class GravityNormalizeUtils
{
	public static final String NBT_KEY = BossToolsAddon.rl("gravity_normalizing").toString();
	public static final String NBT_NORMALIZING_KEY = "normalizing";

	public static void tryCancelGravity(EntityEvent e)
	{
		if (e.isCanceled() == true)
		{
			return;
		}

		Entity entity = e.getEntity();

		if (isNormalizing(entity) == true)
		{
			e.setCanceled(true);
			setNormalizing(entity, false);
		}

	}

	public static void setNormalizing(Entity entity, boolean normalzing)
	{
		CompoundNBT compound = NBTUtils.getOrCreateTag(entity, NBT_KEY);
		compound.putBoolean(NBT_NORMALIZING_KEY, normalzing);
	}

	public static boolean isNormalizing(Entity entity)
	{
		return NBTUtils.getTag(entity, NBT_KEY).getBoolean(NBT_NORMALIZING_KEY);
	}

	public static void resetNormalizingWithCheckType(GravityType type, LivingEntity entity)
	{
		if (Gravity.checkType(type, entity) == true)
		{
			setNormalizing(entity, false);
		}

	}

	private GravityNormalizeUtils()
	{

	}

}
