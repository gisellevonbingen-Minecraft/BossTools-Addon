package boss_tools_giselle_addon.common.compat.mekanism;

import net.minecraftforge.common.MinecraftForge;

public class AddonMekanismCompatClient
{
	public AddonMekanismCompatClient()
	{
		MinecraftForge.EVENT_BUS.register(new AddonMekanismClientEventListener());
	}

}
