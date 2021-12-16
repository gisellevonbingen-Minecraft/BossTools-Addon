package astrocraft_giselle_addon.common.util;

import astrocraft_giselle_addon.common.AstroCraftAddon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.mrscauthd.astrocraft.events.Gravity;
import net.mrscauthd.astrocraft.events.Gravity.GravityType;

public class GravityNormalizeUtils
{
	public static final String NBT_KEY = AstroCraftAddon.rl("gravitynormalizing").toString();
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
		CompoundTag compound = NBTUtils.getOrCreateTag(entity.getPersistentData(), NBT_KEY);
		compound.putBoolean(NBT_NORMALIZING_KEY, normalzing);
	}

	public static boolean isNormalizing(Entity entity)
	{
		return NBTUtils.getTag(entity.getPersistentData(), NBT_KEY).getBoolean(NBT_NORMALIZING_KEY);
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
