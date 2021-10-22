package giselle.bosstools_addon.compat.mekanism;

import giselle.bosstools_addon.BossToolsAddon;
import giselle.bosstools_addon.common.tab.AddonTabs;
import giselle.bosstools_addon.compat.mekanism.gear.AddonMekanismModules;
import mekanism.common.content.gear.Modules.ModuleData;
import mekanism.common.item.ItemModule;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registries.MekanismItems;

public class AddonMekanismItems
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(BossToolsAddon.MODID);

	static
	{
		for (ModuleData<?> module : AddonMekanismModules.getModules())
		{
			MekanismItems.MODULES.put(module, ITEMS.register("module_" + module.getName(), properties -> new ItemModule(module, properties.tab(AddonTabs.tab_main))));
		}

	}

	private AddonMekanismItems()
	{

	}

}
