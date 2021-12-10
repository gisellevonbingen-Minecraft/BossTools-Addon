package boss_tools_giselle_addon.common.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.compat.hwyla.HwylaCompat;
import boss_tools_giselle_addon.common.compat.jaopca.JaopcaCompat;
import boss_tools_giselle_addon.common.compat.mekanism.MekanismCompat;
import boss_tools_giselle_addon.common.compat.theoneprobe.TOPCompat;
import boss_tools_giselle_addon.common.compat.thermal.ThermalCompat;

public class CompatibleManager
{
	public static final List<CompatibleMod> MODS;
	public static final MekanismCompat MEKANISM;
	public static final ThermalCompat THERMAL;
	public static final JaopcaCompat JAOPCA;

	public static final HwylaCompat HWYLA;
	public static final TOPCompat TOP;

	static
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(MEKANISM = new MekanismCompat());
		mods.add(THERMAL = new ThermalCompat());
		mods.add(JAOPCA = new JaopcaCompat());
		mods.add(HWYLA = new HwylaCompat());
		mods.add(TOP = new TOPCompat());

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
