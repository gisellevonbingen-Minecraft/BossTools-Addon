package giselle.bosstools_addon.compat.mekanism;

import giselle.bosstools_addon.common.world.BossToolsWorlds;
import giselle.bosstools_addon.compat.mekanism.gear.AddonModules;
import giselle.bosstools_addon.compat.mekanism.gear.mekasuit.IProofModule;
import mekanism.common.content.gear.Modules;
import mekanism.common.content.gear.Modules.ModuleData;
import mekanism.common.content.gear.mekasuit.ModuleMekaSuit;
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
	public void onLivingAttackEvent(LivingAttackEvent e)
	{
		LivingEntity entity = e.getEntityLiving();
		RegistryKey<World> key = entity.level.dimension();

		if (key == BossToolsWorlds.VENUS)
		{
			this.proofAttack(e, AddonModules.VENUS_ACID_PROOF_UNIT);
		}

		if (key == BossToolsWorlds.VENUS || key == BossToolsWorlds.MERCURY)
		{
			this.proofAttack(e, AddonModules.SPACE_FIRE_PROOF_UNIT);
		}

	}

	private <T extends ModuleMekaSuit & IProofModule> void proofAttack(LivingAttackEvent e, ModuleData<T> type)
	{
		if (e.isCanceled() == true)
		{
			return;
		}

		LivingEntity entity = e.getEntityLiving();

		for (ItemStack itemStack : entity.getArmorSlots())
		{
			T module = Modules.load(itemStack, type);

			if (module != null && module.isEnabled() == true && module.testDamage(e.getSource()) == true)
			{
				if (module.useProofResources(entity) == true)
				{
					e.setCanceled(true);
					break;
				}

			}

		}

	}

}
