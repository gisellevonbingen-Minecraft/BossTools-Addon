package beyond_earth_giselle_addon.common.content.proof;

import beyond_earth_giselle_addon.common.capability.IOxygenCharger;
import beyond_earth_giselle_addon.common.capability.OxygenChargerUtils;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.enchantment.EnchantmentEnergyStorageOrDamageable;
import beyond_earth_giselle_addon.common.util.ItemUsableResource;
import beyond_earth_giselle_addon.common.util.LivingEntityHelper;
import net.minecraft.world.entity.LivingEntity;
import net.mrscauthd.beyond_earth.common.capabilities.oxygen.IOxygenStorage;

public class SpaceOxygenProofEnchantmentSession extends ProofEnchantmentSession
{
	private IOxygenStorage oxygenStorage;

	public SpaceOxygenProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyStorageOrDamageable enchantment)
	{
		super(entity, enchantment);

		if (LivingEntityHelper.isPlayingMode(entity) == true)
		{
			int oxygenUsing = this.getOxygenUsing();
			IOxygenCharger oxygenCharger = OxygenChargerUtils.firstExtractableOxygenCharger(entity, oxygenUsing);
			this.oxygenStorage = oxygenCharger != null ? oxygenCharger.getOxygenStorage() : null;
		}
		else
		{
			this.oxygenStorage = null;
		}

	}

	@Override
	public boolean canProvide()
	{
		if (super.canProvide() == false)
		{
			return false;
		}

		LivingEntity entity = this.getEntity();

		if (LivingEntityHelper.isPlayingMode(entity) == true)
		{
			IOxygenStorage oxygenStorage = this.getOxygenStorage();
			int oxygenUsing = this.getOxygenUsing();

			if (oxygenStorage == null || oxygenStorage.extractOxygen(oxygenUsing, true) < oxygenUsing)
			{
				return false;
			}

		}

		return true;
	}

	@Override
	public void onProvide()
	{
		super.onProvide();

		LivingEntity entity = this.getEntity();

		if (LivingEntityHelper.isPlayingMode(entity) == true)
		{
			IOxygenStorage oxygenStorage = this.getOxygenStorage();

			if (oxygenStorage != null && entity.level.isClientSide() == false)
			{
				int oxygenUsing = this.getOxygenUsing();
				oxygenStorage.extractOxygen(oxygenUsing, false);
			}

		}

	}

	@Override
	public int getResourceUsingAmount(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return AddonConfigs.Common.enchantments.space_breathing_energy_using.get();
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return AddonConfigs.Common.enchantments.space_breathing_durabiltiy_using.get();
		}
		else
		{
			return 0;
		}

	}

	@Override
	public int getProofDuration(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return AddonConfigs.Common.enchantments.space_breathing_energy_duration.get();
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return AddonConfigs.Common.enchantments.space_breathing_durability_duration.get();
		}
		else
		{
			return 0;
		}

	}

	public IOxygenStorage getOxygenStorage()
	{
		return this.oxygenStorage;
	}

	public int getOxygenUsing()
	{
		ItemUsableResource resource = this.getTestedUsableResource();
		return this.getOxygenUsing(resource);
	}

	public int getOxygenUsing(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return AddonConfigs.Common.enchantments.space_breathing_energy_oxygen.get();
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return AddonConfigs.Common.enchantments.space_breathing_durability_oxygen.get();
		}
		else
		{
			return 0;
		}

	}

}
