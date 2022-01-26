package boss_tools_giselle_addon.common.content.proof.oxygen;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.content.proof.AbstractSpaceProofUtils;
import boss_tools_giselle_addon.common.content.proof.LivingSpaceProofEvent;
import boss_tools_giselle_addon.common.content.proof.SpaceProofSession;
import boss_tools_giselle_addon.common.enchantment.AddonEnchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.ModInnet;

public class SpaceOxygenProofUtils extends AbstractSpaceProofUtils
{
	public static final String NBT_KEY = BossToolsAddon.rl("space_breathing").toString();
	public static final SpaceOxygenProofUtils INSTANCE = new SpaceOxygenProofUtils();

	private SpaceOxygenProofUtils()
	{

	}

	@Override
	public String getNBTKey()
	{
		return NBT_KEY;
	}

	@Override
	public LivingSpaceProofEvent createEvent(LivingEntity entity)
	{
		return new LivingSpaceOxygenProofEvent(entity);
	}

	@SubscribeEvent
	public void onProofEnchantment(LivingSpaceOxygenProofEvent e)
	{
		LivingEntity entity = e.getEntityLiving();
		SpaceProofSession session = new SpaceOxygenProofEnchantmentSession(entity, AddonEnchantments.SPACE_BREATHING.get());
		e.setProofDuration(session.provide());
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e)
	{
		LivingEntity entity = e.getEntityLiving();

		if (e.isCanceled() == true)
		{
			return;
		}
		else if (e.getSource() != ModInnet.DAMAGE_SOURCE_OXYGEN)
		{
			return;
		}
		else if (this.tryProvideProof(entity) == true)
		{
			e.setCanceled(true);
		}

	}

}
