package boss_tools_giselle_addon.common.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.compat.jei.AddonJeiCompat;
import boss_tools_giselle_addon.common.compat.theoneprobe.AddonTOPCompat;
import boss_tools_giselle_addon.common.compat.waila.AddonWailaCompat;

public class AddonCompatibleManager
{
	public static final List<CompatibleMod> MODS;

	public static final AddonJeiCompat JEI;
	public static final AddonWailaCompat WAILA;
	public static final AddonTOPCompat TOP;

	static
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(JEI = new AddonJeiCompat());
		mods.add(WAILA = new AddonWailaCompat());
		mods.add(TOP = new AddonTOPCompat());

		for (CompatibleMod mod : mods)
		{
			mod.tryLoad();
		}

		MODS = Collections.unmodifiableList(mods);
	}

	public static void visit()
	{

	}

}
