package beyond_earth_giselle_addon.common.compat.mekanism;

import java.util.function.Function;

import javax.annotation.Nullable;

import beyond_earth_giselle_addon.common.config.AddonConfigs;
import mekanism.api.gear.ICustomModule;
import mekanism.api.math.FloatingLong;
import mekanism.api.providers.IModuleDataProvider;
import mekanism.common.content.gear.Module;
import mekanism.common.content.gear.ModuleHelper;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.util.MekanismUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;

public class AddonModuleHelper
{
	public static <T extends ICustomModule<T>> Module<T> findArmorEnabledModule(Entity entity, IModuleDataProvider<T> type)
	{
		for (ItemStack itemStack : entity.getArmorSlots())
		{
			Module<T> module = ModuleHelper.INSTANCE.load(itemStack, type);

			if (module != null && module.isEnabled() == true)
			{
				return module;
			}

		}

		return null;
	}

	public static boolean testFullParts(LivingEntity living)
	{
		if (!AddonConfigs.Common.mekanism.modulesWorkFullParts.get())
		{
			return true;
		}

		for (EquipmentSlot slot : EquipmentSlot.values())
		{
			if (slot.getType() == EquipmentSlot.Type.ARMOR)
			{
				ItemStack stack = living.getItemBySlot(slot);

				if (!(stack.getItem() instanceof ItemMekaSuitArmor))
				{
					return false;
				}

			}

		}

		return true;
	}

	/**
	 *
	 * @param <T>
	 *            T extends mekanism.common.content.gear.Module
	 * @param e
	 *            Cancelable LivingEvent
	 * @param type
	 *            Module Type
	 * @param getEnergyUsing
	 *            Energy function for cancel
	 * @return Whether canceled in this method
	 */
	public static <T extends ICustomModule<T>> boolean tryCancel(EntityEvent e, IModuleDataProvider<T> type, @Nullable Function<T, FloatingLong> getEnergyUsing)
	{
		return tryCancel(e, type, getEnergyUsing, false);
	}

	/**
	 *
	 * @param <T>
	 *            T extends mekanism.common.content.gear.Module
	 * @param e
	 *            Cancelable LivingEvent
	 * @param type
	 *            Module Type
	 * @param getEnergyUsing
	 *            Energy function for cancel
	 * @return Whether canceled in this method
	 */
	public static <T extends ICustomModule<T>> boolean tryCancel(EntityEvent e, IModuleDataProvider<T> type, @Nullable Function<T, FloatingLong> getEnergyUsing, boolean testFullParts)
	{
		if (e.isCancelable() == false || e.isCanceled() == true || !(e.getEntity() instanceof LivingEntity entity))
		{
			return false;
		}

		Module<T> module = AddonModuleHelper.findArmorEnabledModule(entity, type);

		if (module != null)
		{
			boolean cancel = false;

			if (entity instanceof Player player && MekanismUtils.isPlayingMode(player) == false)
			{
				cancel = true;
			}
			else if (testFullParts && !testFullParts(entity))
			{
				cancel = false;
			}
			else if (getEnergyUsing != null)
			{
				FloatingLong usingEnergy = getEnergyUsing.apply(module.getCustomInstance());

				if (module.canUseEnergy(entity, usingEnergy) == true)
				{
					if (entity.level.isClientSide() == false)
					{
						module.useEnergy(entity, usingEnergy);
					}

					cancel = true;
				}

			}
			else
			{
				cancel = true;
			}

			if (cancel == true)
			{
				e.setCanceled(true);
				return true;
			}

		}

		return false;
	}

	private AddonModuleHelper()
	{

	}

}
