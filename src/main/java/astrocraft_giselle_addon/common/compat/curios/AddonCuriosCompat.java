package astrocraft_giselle_addon.common.compat.curios;

import astrocraft_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.resources.ResourceLocation;

public class AddonCuriosCompat extends CompatibleMod
{
	public static final String MODID = "curios";

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
