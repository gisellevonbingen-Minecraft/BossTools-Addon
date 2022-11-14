package beyond_earth_giselle_addon.common.compat.jer;

import beyond_earth_giselle_addon.common.compat.AddonCompatibleMod;
import net.minecraft.resources.ResourceLocation;

public class AddonJerCompat extends AddonCompatibleMod
{
	public static final String MODID = "jeresources";
	public static final String LANGPREFIX = "jer";

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

	@Override
	public String getModID()
	{
		return MODID;
	}

	@Override
	protected void onLoad()
	{

	}

}
