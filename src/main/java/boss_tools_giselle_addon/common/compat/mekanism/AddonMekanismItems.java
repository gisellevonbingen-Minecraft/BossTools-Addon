package boss_tools_giselle_addon.common.compat.mekanism;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.registries.AddonItemGroups;
import mekanism.common.content.gear.ModuleHelper;
import mekanism.common.item.ItemModule;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.registration.impl.ModuleRegistryObject;

public class AddonMekanismItems
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(BossToolsAddon.MODID);

	public static final ItemRegistryObject<ItemModule> SPACE_BREATHING_UNIT = registerModule(ITEMS, AddonMekanismModules.SPACE_BREATHING_UNIT);
	public static final ItemRegistryObject<ItemModule> GRAVITY_NORMALIZING_UNIT = registerModule(ITEMS, AddonMekanismModules.GRAVITY_NORMALIZING_UNIT);
	public static final ItemRegistryObject<ItemModule> SPACE_FIRE_PROOF_UNIT = registerModule(ITEMS, AddonMekanismModules.SPACE_FIRE_PROOF_UNIT);
	public static final ItemRegistryObject<ItemModule> VENUS_ACID_PROOF_UNIT = registerModule(ITEMS, AddonMekanismModules.VENUS_ACID_PROOF_UNIT);

	public static ItemRegistryObject<ItemModule> registerModule(ItemDeferredRegister register, ModuleRegistryObject<?> moduleDataSupplier)
	{
		return register.register("module_" + moduleDataSupplier.getInternalRegistryName(), () -> ModuleHelper.INSTANCE.createModuleItem(moduleDataSupplier, ItemDeferredRegister.getMekBaseProperties().tab(AddonItemGroups.tab_main)));
	}

	private AddonMekanismItems()
	{

	}

}
