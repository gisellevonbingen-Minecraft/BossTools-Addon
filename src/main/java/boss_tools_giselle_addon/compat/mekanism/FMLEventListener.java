package boss_tools_giselle_addon.compat.mekanism;

import boss_tools_giselle_addon.compat.mekanism.gear.AddonMekanismModules;
import boss_tools_giselle_addon.compat.mekanism.gear.mekasuit.MekaSuitGasSpecHelper;
import boss_tools_giselle_addon.config.AddonConfigs;
import mekanism.common.capabilities.chemical.item.RateLimitMultiTankGasHandler.GasTankSpec;
import mekanism.common.content.gear.Modules;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismItems;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FMLEventListener
{
	public FMLEventListener()
	{
		MekanismItems.MODULE_BASE.getInternalRegistryName();
	}

	@SubscribeEvent
	public void onRegistryRegister(RegistryEvent.Register<Item> event)
	{
		AddonMekanismModules.registerAll();

		ItemMekaSuitArmor helmet = MekanismItems.MEKASUIT_HELMET.get();
		MekaSuitGasSpecHelper.addSpec(helmet, GasTankSpec.createFillOnly(AddonConfigs.Common.mekanism.mekaSuitHelmet_OxygenTransfer::get, AddonConfigs.Common.mekanism.mekaSuitHelmet_OxygenCapacity::get, g -> g == MekanismGases.OXYGEN.get()));
		Modules.setSupported(helmet, AddonMekanismModules.SPACE_BREATHING_UNIT);

		ItemMekaSuitArmor bodyArmor = MekanismItems.MEKASUIT_BODYARMOR.get();
		Modules.setSupported(bodyArmor, AddonMekanismModules.VENUS_ACID_PROOF_UNIT);
		Modules.setSupported(bodyArmor, AddonMekanismModules.SPACE_FIRE_PROOF_UNIT);

		ItemMekaSuitArmor boots = MekanismItems.MEKASUIT_BOOTS.get();
		Modules.setSupported(boots, AddonMekanismModules.GRAVITY_NORMALIZING_UNIT);
	}

}
