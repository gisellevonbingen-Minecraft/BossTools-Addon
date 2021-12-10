package boss_tools_giselle_addon.common.compat.mekanism;

import net.minecraftforge.common.MinecraftForge;

public class MekanismCompatClient
{
	public MekanismCompatClient()
	{
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
	}

}
