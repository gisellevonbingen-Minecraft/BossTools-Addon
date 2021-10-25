package giselle.boss_tools_addon.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import giselle.boss_tools_addon.compat.mekanism.MekanismCompat;
import giselle.boss_tools_addon.compat.thermal.ThermalCompat;
import giselle.boss_tools_addon.compat.tinkers.TinkersCompat;

public class CompatibleManager
{
	public static final List<CompatibleMod> MODS;
	public static final TinkersCompat TINKERS;
	public static final MekanismCompat MEKANISM;
	public static final ThermalCompat THERMAL;

	static
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(TINKERS = new TinkersCompat());
		mods.add(MEKANISM = new MekanismCompat());
		mods.add(THERMAL = new ThermalCompat());

		MODS = Collections.unmodifiableList(mods);
	}

	public static void loadAll()
	{
		for (CompatibleMod mod : MODS)
		{
			mod.tryLoad();
		}

	}

}