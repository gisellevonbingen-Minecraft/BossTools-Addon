package beyond_earth_giselle_addon.common.compat.redstonearsenal;

import beyond_earth_giselle_addon.common.compat.AddonCompatibleMod;
import net.minecraft.resources.ResourceLocation;

public class AddonRedstoneArsenalCompat extends AddonCompatibleMod
{
	public static final String MODID = "redstone_arsenal";

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
