package boss_tools_giselle_addon.common.compat.mekanism;

import boss_tools_giselle_addon.common.compat.mekanism.gear.ModuleGravityNormalizingUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.ModuleSpaceBreathingUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.ModuleSpaceFireProofUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.ModuleVenusAcidProofUnit;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.content.proof.LivingSpaceOxygenProofEvent;
import mekanism.api.math.FloatingLong;
import mekanism.common.content.gear.Module;
import mekanism.common.registries.MekanismModules;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingGravityEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSetFireInHotPlanetEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSetVenusRainEvent;

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
	public static void onLivingGravityEvent(LivingGravityEvent e)
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