package boss_tools_giselle_addon.common.compat.jaopca;

import boss_tools_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.util.ResourceLocation;

public class JaopcaCompat extends CompatibleMod
{
	public static final String MODID = "jaopca";

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
