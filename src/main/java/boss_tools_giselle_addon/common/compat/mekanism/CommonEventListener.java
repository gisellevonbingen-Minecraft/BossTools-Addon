package boss_tools_giselle_addon.common.compat.mekanism;

import boss_tools_giselle_addon.common.compat.mekanism.gear.AddonMekanismModules;
import boss_tools_giselle_addon.common.compat.mekanism.gear.ModulesHelper;
import boss_tools_giselle_addon.common.compat.mekanism.gear.mekasuit.ModuleGravityNormalizingUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.mekasuit.ModuleSpaceBreathingUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.mekasuit.ModuleSpaceFireProofUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.mekasuit.ModuleVenusAcidProofUnit;
import boss_tools_giselle_addon.config.AddonConfigs;
import mekanism.api.math.FloatingLong;
import mekanism.common.content.gear.Modules;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.events.forgeevents.LivingGravityEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSetFireInHotPlanetEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSetVenusRainEvent;

public class CommonEventListener
{
	public CommonEventListener()
	{

	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e)
	{
		if (e.isCanceled() == true)
		{
			return;
		}
		else if (e.getSource() != ModInnet.DAMAGE_SOURCE_OXYGEN)
		{
			return;
		}

		LivingEntity entity = e.getEntityLiving();
		ModuleSpaceBreathingUnit module = ModulesHelper.findArmorEnabledModule(entity, AddonMekanismModules.SPACE_BREATHING_UNIT);

		if (module != null)
		{
			if (module.provideOxygen(entity) == true)
			{
				e.setCanceled(true);
			}

		}

	}

	@SubscribeEvent
	public void onLivingGravityEvent(LivingGravityEvent e)
	{
		if (AddonConfigs.Common.mekanism.moduleGravitationalModulating_normalizable.get() == true)
		{
			ModulesHelper.tryCancel(e, Modules.GRAVITATIONAL_MODULATING_UNIT, m -> FloatingLong.create(AddonConfigs.Common.mekanism.moduleGravitationalModulating_energyUsing.get()));
		}

		ModulesHelper.tryCancel(e, AddonMekanismModules.GRAVITY_NORMALIZING_UNIT, ModuleGravityNormalizingUnit::getEnergyUsing);
	}

	@SubscribeEvent
	public void onLivingSetFireInHotPlanet(LivingSetFireInHotPlanetEvent e)
	{
		ModulesHelper.tryCancel(e, AddonMekanismModules.SPACE_FIRE_PROOF_UNIT, ModuleSpaceFireProofUnit::getEnergyUsing);
	}

	@SubscribeEvent
	public void onLivingSetVenusRain(LivingSetVenusRainEvent e)
	{
		ModulesHelper.tryCancel(e, AddonMekanismModules.VENUS_ACID_PROOF_UNIT, ModuleVenusAcidProofUnit::getEnergyUsing);
	}

}
