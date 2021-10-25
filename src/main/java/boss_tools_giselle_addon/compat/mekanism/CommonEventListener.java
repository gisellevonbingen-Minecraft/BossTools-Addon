package boss_tools_giselle_addon.compat.mekanism;

import boss_tools_giselle_addon.common.world.BossToolsWorlds;
import boss_tools_giselle_addon.compat.mekanism.gear.AddonMekanismModules;
import boss_tools_giselle_addon.compat.mekanism.gear.mekasuit.IProofModule;
import boss_tools_giselle_addon.compat.mekanism.gear.mekasuit.MekaSuitGasSpecHelper;
import boss_tools_giselle_addon.compat.mekanism.gear.mekasuit.ModuleVenusAcidProofUnit;
import boss_tools_giselle_addon.config.AddonConfigs;
import boss_tools_giselle_addon.event.FallGravityProcedureEvent;
import mekanism.common.capabilities.chemical.item.RateLimitMultiTankGasHandler.GasTankSpec;
import mekanism.common.content.gear.Modules;
import mekanism.common.content.gear.Modules.ModuleData;
import mekanism.common.content.gear.mekasuit.ModuleMekaSuit;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonEventListener
{
	public CommonEventListener()
	{

	}

	@SubscribeEvent
	public void onRegistryRegister(RegistryEvent.Register<Item> event)
	{
		MekanismItems.MODULE_BASE.get();

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

	@SubscribeEvent
	public void onFallGravityProcedure(FallGravityProcedureEvent e)
	{
		Entity entity = e.getEntity();

		if (e.isCanceled() == true)
		{
			return;
		}

		ModuleVenusAcidProofUnit module = this.findEnabledModule(entity, AddonMekanismModules.GRAVITY_NORMALIZING_UNIT);

		if (module != null)
		{
			e.setCanceled(true);
		}

	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e)
	{
		LivingEntity entity = e.getEntityLiving();
		RegistryKey<World> key = entity.level.dimension();

		if (key == BossToolsWorlds.VENUS)
		{
			this.proofAttack(e, AddonMekanismModules.VENUS_ACID_PROOF_UNIT);
		}

		if (key == BossToolsWorlds.VENUS || key == BossToolsWorlds.MERCURY)
		{
			this.proofAttack(e, AddonMekanismModules.SPACE_FIRE_PROOF_UNIT);
		}

	}

	private <T extends ModuleMekaSuit> T findEnabledModule(Entity entity, ModuleData<T> type)
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

	private <T extends ModuleMekaSuit & IProofModule> void proofAttack(LivingAttackEvent e, ModuleData<T> type)
	{
		if (e.isCanceled() == true)
		{
			return;
		}

		LivingEntity entity = e.getEntityLiving();
		T module = this.findEnabledModule(entity, type);

		if (module != null && module.testDamage(e.getSource()) == true)
		{
			if (module.useProofResources(entity) == true)
			{
				e.setCanceled(true);
			}

		}

	}

}
