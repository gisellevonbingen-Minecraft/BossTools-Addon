package giselle.bosstools_addon.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import giselle.bosstools_addon.compat.mekanism.MekanismCompat;
import giselle.bosstools_addon.compat.tinkers.TinkersCompat;

public class CompatibleManager
{

	public static final List<CompatibleMod> MODS;
	public static final TinkersCompat TINKERS;
	public static final MekanismCompat MEKANISM;

	static
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(TINKERS = new TinkersCompat());
		mods.add(MEKANISM = new MekanismCompat());

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
