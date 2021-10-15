package giselle.bosstools_addon.compat.draconicevolution;

import giselle.bosstools_addon.compat.CompatibleMod;
import net.minecraft.util.ResourceLocation;

public class DraconicEvolutionCompat extends CompatibleMod
{
	public static final String MODID = "draconicevolution";

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
