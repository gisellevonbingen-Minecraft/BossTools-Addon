package giselle.bosstools_addon.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import giselle.bosstools_addon.compat.mekanism.MekanismCompat;

public class CompatibleManager {

	public static final List<CompatibleMod> MODS;
	public static final MekanismCompat MEKANISM;

	static {
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(MEKANISM = new MekanismCompat());

		MODS = Collections.unmodifiableList(mods);
	}

	public static void loadAll() {
		for (CompatibleMod mod : MODS) {
			mod.tryLoad();
		}
	}
}
