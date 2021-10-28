package boss_tools_giselle_addon.compat.mekanism;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.tab.AddonTabs;
import boss_tools_giselle_addon.compat.mekanism.gear.AddonMekanismModules;
import mekanism.common.content.gear.Modules.ModuleData;
import mekanism.common.item.ItemModule;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registries.MekanismItems;

public class AddonMekanismItems
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(BossToolsAddon.MODID);

	static
	{
		MekanismItems.MODULE_BASE.getInternalRegistryName();
		
		for (ModuleData<?> module : AddonMekanismModules.getModules())
		{
			MekanismItems.MODULES.put(module, ITEMS.register("module_" + module.getName(), properties -> new ItemModule(module, properties.tab(AddonTabs.tab_main))));
		}

	}

	private AddonMekanismItems()
	{

	}

}
