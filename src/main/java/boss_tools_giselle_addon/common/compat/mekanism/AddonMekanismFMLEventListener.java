package boss_tools_giselle_addon.common.compat.mekanism;

import boss_tools_giselle_addon.common.config.AddonConfigs;
import mekanism.api.MekanismIMC;
import mekanism.common.capabilities.chemical.item.RateLimitMultiTankGasHandler.GasTankSpec;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismItems;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public class AddonMekanismFMLEventListener
{
	public AddonMekanismFMLEventListener()
	{

	}


	@SubscribeEvent
	public void onInterModeEnqueue(InterModEnqueueEvent event)
	{
		String MODID = AddonMekanismCompat.MODID;
		InterModComms.sendTo(MODID, MekanismIMC.ADD_MEKA_SUIT_HELMET_MODULES, AddonMekanismModules.SPACE_BREATHING_UNIT);
		InterModComms.sendTo(MODID, MekanismIMC.ADD_MEKA_SUIT_BODYARMOR_MODULES, AddonMekanismModules.SPACE_FIRE_PROOF_UNIT);
		InterModComms.sendTo(MODID, MekanismIMC.ADD_MEKA_SUIT_BODYARMOR_MODULES, AddonMekanismModules.VENUS_ACID_PROOF_UNIT);
		InterModComms.sendTo(MODID, MekanismIMC.ADD_MEKA_SUIT_BOOTS_MODULES, AddonMekanismModules.GRAVITY_NORMALIZING_UNIT);
	}

	@SubscribeEvent
	public void onRegistryRegister(RegistryEvent.Register<Item> event)
	{
		ItemMekaSuitArmor helmet = MekanismItems.MEKASUIT_HELMET.get();
		MekaSuitGasSpecHelper.addSpec(helmet, GasTankSpec.createFillOnly(AddonConfigs.Common.mekanism.mekaSuitHelmet_OxygenTransfer::get, AddonConfigs.Common.mekanism.mekaSuitHelmet_OxygenCapacity::get, g -> g == MekanismGases.OXYGEN.get()));
	}

}
