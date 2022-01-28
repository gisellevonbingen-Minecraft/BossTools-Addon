package beyond_earth_giselle_addon.common.util;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ItemEnergyDurabilityUtils
{
	public static boolean extractEnergyOrDurability(ItemStack stack, int energy, int damage, boolean simulate, Random random, @Nullable LivingEntity entity)
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
					ServerPlayer serverPlayer = (entity instanceof ServerPlayer) ? (ServerPlayer) entity : null;

					if (entity == null || serverPlayer != null)
					{
						stack.hurt(damage, random, serverPlayer);
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
