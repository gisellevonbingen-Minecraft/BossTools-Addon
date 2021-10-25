package boss_tools_giselle_addon.compat.mekanism;

import boss_tools_giselle_addon.common.world.BossToolsWorlds;
import boss_tools_giselle_addon.compat.mekanism.gear.AddonMekanismModules;
import boss_tools_giselle_addon.compat.mekanism.gear.mekasuit.IProofModule;
import boss_tools_giselle_addon.compat.mekanism.gear.mekasuit.ModuleVenusAcidProofUnit;
import boss_tools_giselle_addon.event.FallGravityProcedureEvent;
import mekanism.common.content.gear.Modules;
import mekanism.common.content.gear.Modules.ModuleData;
import mekanism.common.content.gear.mekasuit.ModuleMekaSuit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonEventListener
{
	public CommonEventListener()
	{

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
