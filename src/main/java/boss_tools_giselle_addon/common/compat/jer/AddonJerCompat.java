package boss_tools_giselle_addon.common.compat.jer;

import boss_tools_giselle_addon.common.compat.AddonCompatibleMod;
import net.minecraft.util.ResourceLocation;

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
