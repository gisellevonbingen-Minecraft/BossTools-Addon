package beyond_earth_giselle_addon.common.compat.mekanism;

import beyond_earth_giselle_addon.common.compat.mekanism.gear.ModuleGravityNormalizingUnit;
import beyond_earth_giselle_addon.common.compat.mekanism.gear.ModuleSpaceBreathingUnit;
import beyond_earth_giselle_addon.common.compat.mekanism.gear.ModuleSpaceFireProofUnit;
import beyond_earth_giselle_addon.common.compat.mekanism.gear.ModuleVenusAcidProofUnit;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.content.proof.LivingSpaceOxygenProofEvent;
import mekanism.api.math.FloatingLong;
import mekanism.common.content.gear.Module;
import mekanism.common.registries.MekanismModules;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.events.forge.EntityGravityEvent;
import net.mrscauthd.beyond_earth.events.forge.LivingSetFireInHotPlanetEvent;
import net.mrscauthd.beyond_earth.events.forge.LivingSetVenusRainEvent;

public class AddonMekanismCommonEventListener
{
	@SubscribeEvent
	public static void onLivingSpaceOxygenProofEvent(LivingSpaceOxygenProofEvent e)
	{
		LivingEntity entity = e.getEntityLiving();
		Module<ModuleSpaceBreathingUnit> module = AddonModuleHelper.findArmorEnabledModule(entity, AddonMekanismModules.SPACE_BREATHING_UNIT);

		if (module != null)
		{
			int provideOxygen = module.getCustomInstance().provideOxygen(module, entity);
			e.setProofDuration(provideOxygen);
		}

	}

	@SubscribeEvent
	public static void onLivingGravityEvent(EntityGravityEvent e)
	{
		if (AddonConfigs.Common.mekanism.moduleGravitationalModulating_normalizable.get() == true)
		{
			AddonModuleHelper.tryCancel(e, MekanismModules.GRAVITATIONAL_MODULATING_UNIT, m -> FloatingLong.create(AddonConfigs.Common.mekanism.moduleGravitationalModulating_energyUsing.get()));
		}

		AddonModuleHelper.tryCancel(e, AddonMekanismModules.GRAVITY_NORMALIZING_UNIT, ModuleGravityNormalizingUnit::getEnergyUsing);
	}

	@SubscribeEvent
	public static void onLivingSetFireInHotPlanet(LivingSetFireInHotPlanetEvent e)
	{
		AddonModuleHelper.tryCancel(e, AddonMekanismModules.SPACE_FIRE_PROOF_UNIT, ModuleSpaceFireProofUnit::getEnergyUsing, true);
	}

	@SubscribeEvent
	public static void onLivingSetVenusRain(LivingSetVenusRainEvent e)
	{
		AddonModuleHelper.tryCancel(e, AddonMekanismModules.VENUS_ACID_PROOF_UNIT, ModuleVenusAcidProofUnit::getEnergyUsing, true);
	}

	private AddonMekanismCommonEventListener()
	{

	}

}
