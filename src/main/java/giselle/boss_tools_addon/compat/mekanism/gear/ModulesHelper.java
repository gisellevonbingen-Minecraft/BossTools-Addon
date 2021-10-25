package giselle.boss_tools_addon.compat.mekanism.gear;

import java.lang.reflect.Method;
import java.util.function.Supplier;

import giselle.boss_tools_addon.util.ReflectionUtil;
import mekanism.api.text.ILangEntry;
import mekanism.common.content.gear.Modules;
import mekanism.common.content.gear.Modules.ModuleData;

public class ModulesHelper
{
	private static final Method registerMethod = ReflectionUtil.getDeclaredAcessibleMethod(Modules.class, "register", String.class, ILangEntry.class, ILangEntry.class, Supplier.class, int.class);

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

	private ModulesHelper()
	{

	}

}
