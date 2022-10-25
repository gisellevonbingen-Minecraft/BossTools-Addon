package beyond_earth_giselle_addon.common.compat.mekanism;

import beyond_earth_giselle_addon.common.compat.mekanism.gear.ModuleSpaceBreathingUnit;
import beyond_earth_giselle_addon.common.compat.mekanism.gear.ModuleSpaceFireProofUnit;
import beyond_earth_giselle_addon.common.compat.mekanism.gear.ModuleVenusAcidProofUnit;
import beyond_earth_giselle_addon.common.content.proof.LivingSpaceOxygenProofEvent;
import mekanism.common.content.gear.Module;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.common.events.forge.LivingSetFireInHotPlanetEvent;
import net.mrscauthd.beyond_earth.common.events.forge.LivingSetVenusRainEvent;

public class AddonMekanismCommonEventListener
{
	@SubscribeEvent
	public static void onLivingSpaceOxygenProofEvent(LivingSpaceOxygenProofEvent e)
	{
		LivingEntity entity = e.getEntity();
		Module<ModuleSpaceBreathingUnit> module = AddonModuleHelper.findArmorEnabledModule(entity, AddonMekanismModules.SPACE_BREATHING_UNIT);

		if (module != null)
		{
			int provideOxygen = module.getCustomInstance().provideOxygen(module, entity);
			e.setProofDuration(provideOxygen);
		}

	}

	@SubscribeEvent
	public static void onLivingSetFireInHotPlanet(LivingSetFireInHotPlanetEvent e)
	{
		AddonModuleHelper.tryCancel(e, AddonMekanismModules.SPACE_FIRE_PROOF_UNIT, ModuleSpaceFireProofUnit::getEnergyUsing);
	}

	@SubscribeEvent
	public static void onLivingSetVenusRain(LivingSetVenusRainEvent e)
	{
		AddonModuleHelper.tryCancel(e, AddonMekanismModules.VENUS_ACID_PROOF_UNIT, ModuleVenusAcidProofUnit::getEnergyUsing);
	}

	private AddonMekanismCommonEventListener()
	{

	}

}
