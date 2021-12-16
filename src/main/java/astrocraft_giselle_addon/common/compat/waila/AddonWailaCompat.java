package astrocraft_giselle_addon.common.compat.waila;

import astrocraft_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.resources.ResourceLocation;

public class AddonWailaCompat extends CompatibleMod
{
	public static final String MODID = "waila";

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
