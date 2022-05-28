package beyond_earth_giselle_addon.common.content.gravity;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.util.NBTUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;

public class GravityNormalizeUtils
{
	public static final String NBT_KEY = BeyondEarthAddon.rl("gravity_normalizing").toString();
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

	private GravityNormalizeUtils()
	{

	}

}
