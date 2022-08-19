package beyond_earth_giselle_addon.common.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beyond_earth_giselle_addon.common.compat.crafttweaker.AddonCraftTweakerCompat;
import beyond_earth_giselle_addon.common.compat.curios.AddonCuriosCompat;
import beyond_earth_giselle_addon.common.compat.immersiveengineering.AddonIECompat;
import beyond_earth_giselle_addon.common.compat.jaopca.AddonJaopcaCompat;
import beyond_earth_giselle_addon.common.compat.jei.AddonJeiCompat;
import beyond_earth_giselle_addon.common.compat.jer.AddonJerCompat;
import beyond_earth_giselle_addon.common.compat.mekanism.AddonMekanismCompat;
import beyond_earth_giselle_addon.common.compat.theoneprobe.AddonTOPCompat;
import beyond_earth_giselle_addon.common.compat.thermal.AddonThermalCompat;
import beyond_earth_giselle_addon.common.compat.waila.AddonWailaCompat;

public class AddonCompatibleManager
{
	public static final List<AddonCompatibleMod> MODS;
	public static final AddonJeiCompat JEI;
	public static final AddonJerCompat JER;
	public static final AddonMekanismCompat MEKANISM;
	public static final AddonThermalCompat THERMAL;
	public static final AddonIECompat IMMERSIVE_ENGINEERING;
	public static final AddonJaopcaCompat JAOPCA;
	public static final AddonCuriosCompat CURIOS;
	public static final AddonCraftTweakerCompat CRAFTTWEAKER;

	public static final AddonWailaCompat WAILA;
	public static final AddonTOPCompat TOP;

	static
	{
		List<AddonCompatibleMod> mods = new ArrayList<>();
		mods.add(JEI = new AddonJeiCompat());
		mods.add(JER = new AddonJerCompat());
		mods.add(MEKANISM = new AddonMekanismCompat());
		mods.add(THERMAL = new AddonThermalCompat());
		mods.add(IMMERSIVE_ENGINEERING = new AddonIECompat());
		mods.add(JAOPCA = new AddonJaopcaCompat());
		mods.add(CURIOS = new AddonCuriosCompat());
		mods.add(CRAFTTWEAKER = new AddonCraftTweakerCompat());
		mods.add(WAILA = new AddonWailaCompat());
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
