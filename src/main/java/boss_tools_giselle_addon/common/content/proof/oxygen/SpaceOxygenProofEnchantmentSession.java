package boss_tools_giselle_addon.common.content.proof.oxygen;

import com.google.common.collect.Iterables;

import boss_tools_giselle_addon.common.capability.CapabilityOxygenCharger;
import boss_tools_giselle_addon.common.capability.IOxygenCharger;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.content.proof.SpaceProofEnchantmentSession;
import boss_tools_giselle_addon.common.enchantment.EnchantmentEnergyOrDurability;
import boss_tools_giselle_addon.common.inventory.InventoryHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;

public class SpaceOxygenProofEnchantmentSession extends SpaceProofEnchantmentSession
{
	private IOxygenStorage oxygenStorage;

	public SpaceOxygenProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyOrDurability enchantment)
	{
		super(entity, enchantment);

		ItemStack oxygenCan = InventoryHelper.getInventoryStacks(entity).stream().filter(is ->
		{
			IOxygenCharger oxygenCharger = is.getCapability(CapabilityOxygenCharger.OXYGEN_CHARGER).orElse(null);
			return oxygenCharger != null && Iterables.contains(oxygenCharger.getChargeMode().getItemStacks(entity), this.getEnchantedItem());
		}).findFirst().orElse(ItemStack.EMPTY);
		this.oxygenStorage = oxygenCan.getCapability(CapabilityOxygen.OXYGEN).orElse(null);
	}

	@Override
	public boolean canProvide()
	{
		IOxygenStorage oxygenStorage = this.getOxygenStorage();
		int oxygenUsing = this.getOxygenUsing();

		if (oxygenStorage == null || oxygenStorage.extractOxygen(oxygenUsing, true) < oxygenUsing)
		{
			return false;
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
	public int getDurabilityUsing()
	{
		return AddonConfigs.Common.enchantments.space_breathing_durabilityUsing.get();
	}

	@Override
	public int getProofDuration()
	{
		return AddonConfigs.Common.enchantments.space_breathing_proofDuration.get();
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
