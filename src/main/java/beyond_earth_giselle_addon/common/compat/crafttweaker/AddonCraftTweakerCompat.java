package beyond_earth_giselle_addon.common.compat.crafttweaker;

import beyond_earth_giselle_addon.common.compat.AddonCompatibleMod;
import net.minecraft.resources.ResourceLocation;

public class AddonCraftTweakerCompat extends AddonCompatibleMod
{
	public static final String MODID = "crafttweaker";

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
