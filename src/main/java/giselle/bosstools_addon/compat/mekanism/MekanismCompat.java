package giselle.bosstools_addon.compat.mekanism;

import giselle.bosstools_addon.compat.CompatibleMod;
import giselle.bosstools_addon.compat.mekanism.gear.AddonMekanismModules;
import giselle.bosstools_addon.compat.mekanism.gear.mekasuit.MekaSuitGasSpecHelper;
import giselle.bosstools_addon.config.AddonConfigs;
import mekanism.common.capabilities.chemical.item.RateLimitMultiTankGasHandler.GasTankSpec;
import mekanism.common.content.gear.Modules;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismItems;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class MekanismCompat extends CompatibleMod
{
	public static final String MODID = "mekanism";

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

	@Override
	public String getModID()
	{
		return MODID;
	}

	@Override
	protected void onLoad()
	{
		MekanismItems.MODULE_BASE.getClass();
		AddonMekanismModules.registerAll();

		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		AddonMekanismItems.ITEMS.register(fml_bus);
		fml_bus.addGenericListener(Item.class, this::addEntries);

		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(new CommonEventListener());
	}

	private void addEntries(RegistryEvent.Register<Item> event)
	{
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
