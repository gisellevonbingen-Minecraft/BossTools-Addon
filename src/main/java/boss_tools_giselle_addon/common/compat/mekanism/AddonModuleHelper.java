package boss_tools_giselle_addon.common.compat.mekanism;

import java.util.function.Function;

import javax.annotation.Nullable;

import mekanism.api.gear.ICustomModule;
import mekanism.api.math.FloatingLong;
import mekanism.api.providers.IModuleDataProvider;
import mekanism.common.content.gear.Module;
import mekanism.common.content.gear.ModuleHelper;
import mekanism.common.util.MekanismUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

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
	public static <T extends ICustomModule<T>> boolean tryCancel(LivingEvent e, IModuleDataProvider<T> type, @Nullable Function<T, FloatingLong> getEnergyUsing)
	{
		if (e.isCancelable() == false || e.isCanceled() == true)
		{
			return false;
		}

		LivingEntity entity = e.getEntityLiving();
		Module<T> module = AddonModuleHelper.findArmorEnabledModule(entity, type);

		if (module != null)
		{
			boolean cancel = false;

			if (entity instanceof PlayerEntity && MekanismUtils.isPlayingMode((PlayerEntity) entity) == false)
			{
				cancel = true;
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
