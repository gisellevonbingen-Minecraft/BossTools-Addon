package boss_tools_giselle_addon.common.compat.mekanism.gear;

import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.util.ReflectionUtils;
import mekanism.api.math.FloatingLong;
import mekanism.api.text.ILangEntry;
import mekanism.common.content.gear.Modules;
import mekanism.common.content.gear.Modules.ModuleData;
import mekanism.common.util.MekanismUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.living.LivingEvent;

public class ModulesHelper
{
	private static final Method registerMethod = ReflectionUtils.getDeclaredAcessibleMethod(Modules.class, "register", String.class, ILangEntry.class, ILangEntry.class, Supplier.class, int.class);

	public static <M extends mekanism.common.content.gear.Module> ModuleData<M> register(String name, ILangEntry langEntry, ILangEntry description, Supplier<M> moduleSupplier)
	{
		return register(name, langEntry, description, moduleSupplier, 1);
	}

	@SuppressWarnings("unchecked")
	public static <M extends mekanism.common.content.gear.Module> ModuleData<M> register(String name, ILangEntry langEntry, ILangEntry description, Supplier<M> moduleSupplier, int maxStackSize)
	{
		try
		{
			return (ModuleData<M>) registerMethod.invoke(Modules.class, name, langEntry, description, moduleSupplier, maxStackSize);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

	}

	public static <T extends mekanism.common.content.gear.Module> T findArmorEnabledModule(Entity entity, ModuleData<T> type)
	{
		for (ItemStack itemStack : entity.getArmorSlots())
		{
			T module = Modules.load(itemStack, type);

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
	public static <T extends mekanism.common.content.gear.Module> boolean tryCancel(LivingEvent e, ModuleData<T> type, @Nullable Function<T, FloatingLong> getEnergyUsing)
	{
		if (e.isCancelable() == false || e.isCanceled() == true)
		{
			return false;
		}

		LivingEntity entity = e.getEntityLiving();
		T module = ModulesHelper.findArmorEnabledModule(entity, type);

		if (module != null)
		{
			boolean cancel = false;

			if (entity instanceof PlayerEntity && MekanismUtils.isPlayingMode((PlayerEntity) entity) == false)
			{
				cancel = true;
			}
			else if (getEnergyUsing != null)
			{
				FloatingLong usingEnergy = getEnergyUsing.apply(module);

				if (module.canUseEnergy(entity, usingEnergy) == true)
				{
					module.useEnergy(entity, usingEnergy);
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

	public static CompoundNBT getCustomTag(mekanism.common.content.gear.Module module, ItemStack container)
	{
		return container.getTagElement(getCustomTagKey(module));
	}

	public static CompoundNBT getOrCreateCustomTag(mekanism.common.content.gear.Module module, ItemStack container)
	{
		return container.getOrCreateTagElement(getCustomTagKey(module));
	}

	public static String getCustomTagKey(mekanism.common.content.gear.Module module)
	{
		return BossToolsAddon.rl("compat/mekanism/module/" + module.getName()).toString();
	}

	private ModulesHelper()
	{

	}

}
