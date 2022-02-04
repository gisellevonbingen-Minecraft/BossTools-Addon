package boss_tools_giselle_addon.common.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.compat.curios.AddonCuriosCompat;
import boss_tools_giselle_addon.common.compat.hwyla.AddonHwylaCompat;
import boss_tools_giselle_addon.common.compat.jaopca.AddonJaopcaCompat;
import boss_tools_giselle_addon.common.compat.jei.AddonJeiCompat;
import boss_tools_giselle_addon.common.compat.jer.AddonJerCompat;
import boss_tools_giselle_addon.common.compat.mekanism.AddonMekanismCompat;
import boss_tools_giselle_addon.common.compat.redstonearsenal.AddonRedstoneArsenalCompat;
import boss_tools_giselle_addon.common.compat.theoneprobe.AddonTOPCompat;
import boss_tools_giselle_addon.common.compat.thermal.AddonThermalCompat;

public class AddonCompatibleManager
{
	public static final List<AddonCompatibleMod> MODS;
	public static final AddonJeiCompat JEI;
	public static final AddonJerCompat JER;
	public static final AddonMekanismCompat MEKANISM;
	public static final AddonThermalCompat THERMAL;
	public static final AddonRedstoneArsenalCompat REDSTONE_ARSENAL;
	public static final AddonJaopcaCompat JAOPCA;
	public static final AddonCuriosCompat CURIOS;

	public static final AddonHwylaCompat HWYLA;
	public static final AddonTOPCompat TOP;

	static
	{
		List<AddonCompatibleMod> mods = new ArrayList<>();
		mods.add(JEI = new AddonJeiCompat());
		mods.add(JER = new AddonJerCompat());
		mods.add(MEKANISM = new AddonMekanismCompat());
		mods.add(THERMAL = new AddonThermalCompat());
		mods.add(REDSTONE_ARSENAL = new AddonRedstoneArsenalCompat());
		mods.add(JAOPCA = new AddonJaopcaCompat());
		mods.add(CURIOS = new AddonCuriosCompat());
		mods.add(HWYLA = new AddonHwylaCompat());
		mods.add(TOP = new AddonTOPCompat());

		for (AddonCompatibleMod mod : mods)
		{
			mod.tryLoad();
		}

		MODS = Collections.unmodifiableList(mods);
	}

	public static void visit()
	{

	}

}
