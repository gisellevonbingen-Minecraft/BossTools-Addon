package beyond_earth_giselle_addon.common.content.proof;

import beyond_earth_giselle_addon.common.capability.IOxygenCharger;
import beyond_earth_giselle_addon.common.capability.OxygenChargerUtils;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.enchantment.EnchantmentEnergyStorage;
import beyond_earth_giselle_addon.common.util.LivingEntityHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.beyond_earth.capability.oxygen.IOxygenStorage;

public class SpaceOxygenProofEnchantmentSession extends ProofEnchantmentSession
{
	private IOxygenStorage oxygenStorage;

	public SpaceOxygenProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyStorage enchantment)
	{
		super(entity, enchantment);

		if (LivingEntityHelper.isPlayingMode(entity) == true)
		{
			int oxygenUsing = this.getOxygenUsing();
			ItemStack enchantedItem = this.getEnchantedItem();
			IOxygenCharger oxygenCharger = OxygenChargerUtils.firstExtractableOxygenCharger(entity, oxygenUsing, enchantedItem);
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
		IOxygenStorage oxygenStorage = this.getOxygenStorage();
		int oxygenUsing = this.getOxygenUsing();
		LivingEntity entity = this.getEntity();

		if (LivingEntityHelper.isPlayingMode(entity) == true)
		{
			if (oxygenStorage == null || oxygenStorage.extractOxygen(oxygenUsing, true) < oxygenUsing)
			{
				return false;
			}

		}

		return super.canProvide();
	}

	@Override
	public void onProvide()
	{
		super.onProvide();

		IOxygenStorage oxygenStorage = this.getOxygenStorage();
		int oxygenUsing = this.getOxygenUsing();
		oxygenStorage.extractOxygen(oxygenUsing, false);
	}

	@Override
	public int getEnergyUsing()
	{
		return AddonConfigs.Common.enchantments.space_breathing_energyUsing.get();
	}

	@Override
	public int getProofDuration()
	{
		return AddonConfigs.Common.enchantments.space_breathing_oxygenDuration.get();
	}

	public IOxygenStorage getOxygenStorage()
	{
		return this.oxygenStorage;
	}

	public int getOxygenUsing()
	{
		return 1;
	}

}
