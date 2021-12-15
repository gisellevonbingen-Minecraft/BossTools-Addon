package boss_tools_giselle_addon.common.compat.jei;

import boss_tools_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.resources.ResourceLocation;

public class AddonJeiCompat extends CompatibleMod
{
	public static final String MODID = "jei";

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
