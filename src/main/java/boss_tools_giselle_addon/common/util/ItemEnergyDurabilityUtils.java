package boss_tools_giselle_addon.common.util;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ItemEnergyDurabilityUtils
{
	public static boolean extractEnergyOrDurability(ItemStack stack, int energy, int damage, boolean simulate, Random random, @Nullable ServerPlayerEntity player)
	{
		IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);

		if (energyStorage != null)
		{
			if (energyStorage.extractEnergy(energy, true) == energy)
			{
				if (simulate == false)
				{
					energyStorage.extractEnergy(damage, false);
				}

				return true;
			}

		}

		if (stack.isDamageableItem() == true)
		{
			int damageValue = stack.getDamageValue();
			int remainDamage = stack.getMaxDamage() - damageValue;

			if (remainDamage > damage)
			{
				if (simulate == false)
				{
					if (player == null || player.level.isClientSide() == false)
					{
						stack.hurt(damage, random, player);
					}

				}

				return true;
			}

		}

		return false;
	}

	private ItemEnergyDurabilityUtils()
	{

	}

}
