package boss_tools_giselle_addon.common.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public enum ItemUsableResource
{
	None()
		{
			@Override
			public boolean test(ItemStack stack)
			{
				return false;
			}

			@Override
			public int extract(ItemStack stack, int amount, boolean simulate)
			{
				return 0;
			}
		},
	Energy()
		{
			@Override
			public boolean test(ItemStack stack)
			{
				return stack.getCapability(CapabilityEnergy.ENERGY).isPresent();
			}

			@Override
			public int extract(ItemStack stack, int amount, boolean simulate)
			{
				IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);
				return energyStorage != null ? energyStorage.extractEnergy(amount, simulate) : 0;
			}
		},
	Durability()
		{
			@Override
			public boolean test(ItemStack stack)
			{
				return stack.isDamageableItem();
			}

			@Override
			public int extract(ItemStack stack, int amount, boolean simulate)
			{
				if (stack.isDamageableItem() == true)
				{
					int prevDamage = stack.getDamageValue();
					int maxDamage = stack.getMaxDamage();
					int damaging = Math.min(maxDamage - prevDamage, amount);
					int nextDamage = prevDamage + damaging;

					if (nextDamage < maxDamage)
					{
						if (simulate == false)
						{
							stack.setDamageValue(nextDamage);
						}

						return damaging;
					}

				}

				return 0;
			}
		},
	// EOL
	;

	private ItemUsableResource()
	{

	}

	public abstract boolean test(ItemStack stack);

	public abstract int extract(ItemStack stack, int amount, boolean simulate);

	public boolean use(ItemStack stack, int amount, boolean simulate)
	{
		return this.extract(stack, amount, simulate) >= amount;
	}

}
