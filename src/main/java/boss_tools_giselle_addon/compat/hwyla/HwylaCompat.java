package boss_tools_giselle_addon.compat.hwyla;

import boss_tools_giselle_addon.compat.CompatibleMod;
import net.minecraft.util.ResourceLocation;

public class HwylaCompat extends CompatibleMod
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
