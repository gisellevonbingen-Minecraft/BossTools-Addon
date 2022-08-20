package boss_tools_giselle_addon.common.compat.crafttweaker.bracket;

import net.minecraft.util.ResourceLocation;

public class CTBracketUtils
{
	public static String toBracketString(String bracket, ResourceLocation name)
	{
		return new StringBuilder("<").append(bracket).append(":").append(name).append(">").toString();
	}

}
