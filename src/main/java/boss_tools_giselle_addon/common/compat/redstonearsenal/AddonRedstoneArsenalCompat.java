package boss_tools_giselle_addon.common.compat.redstonearsenal;

import boss_tools_giselle_addon.common.compat.AddonCompatibleMod;
import net.minecraft.util.ResourceLocation;

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
