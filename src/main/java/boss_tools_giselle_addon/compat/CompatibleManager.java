package boss_tools_giselle_addon.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.compat.jaopca.JaopcaCompat;
import boss_tools_giselle_addon.compat.mekanism.MekanismCompat;
import boss_tools_giselle_addon.compat.thermal.ThermalCompat;

public class CompatibleManager
{
	public static final List<CompatibleMod> MODS;
	public static final MekanismCompat MEKANISM;
	public static final ThermalCompat THERMAL;
	public static final JaopcaCompat JAOPCA;

	static
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(MEKANISM = new MekanismCompat());
		mods.add(THERMAL = new ThermalCompat());
		mods.add(JAOPCA = new JaopcaCompat());

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
