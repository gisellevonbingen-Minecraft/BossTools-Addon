package boss_tools_giselle_addon.common.content.proof;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.content.gravitynormalizing.GravityNormalizeUtils;
import boss_tools_giselle_addon.common.enchantment.AddonEnchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingGravityEvent;

public class SpaceGravityProofUtils extends ProofAbstractUtils
{
	public static final String NBT_KEY = BossToolsAddon.rl("space_gravity_proof").toString();
	public static final SpaceGravityProofUtils INSTANCE = new SpaceGravityProofUtils();

	private SpaceGravityProofUtils()
	{

	}

	@Override
	public String getNBTKey()
	{
		return NBT_KEY;
	}

	@Override
	public LivingProofEvent createEvent(LivingEntity entity)
	{
		return new LivingVenusAcidProofEvent(entity);
	}

	@SubscribeEvent
	public void onProofEnchantment(LivingVenusAcidProofEvent e)
	{
		LivingEntity entity = e.getEntityLiving();
		ProofSession session = new SpaceGravityProofEnchantmentSession(entity, AddonEnchantments.GRAVITY_NORMALIZING.get());
		e.setProofDuration(session.provide());
	}

	@SubscribeEvent
	public void onLivingGravity(LivingGravityEvent e)
	{
		if (e.isCanceled() == true)
		{
			return;
		}
		else
		{
			LivingEntity entity = e.getEntityLiving();

			if (this.tryProvideProof(entity) == true)
			{
				GravityNormalizeUtils.setNormalizing(entity, true);
			}

		}

	}

}
