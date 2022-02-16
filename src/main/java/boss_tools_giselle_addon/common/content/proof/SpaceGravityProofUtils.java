package boss_tools_giselle_addon.common.content.proof;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.content.gravity.GravityNormalizeUtils;
import boss_tools_giselle_addon.common.registries.AddonEnchantments;
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
	public boolean tryProvideProof(LivingEntity entity)
	{
		if (GravityNormalizeUtils.isNormalizing(entity) == true)
		{
			return true;
		}

		return super.tryProvideProof(entity);
	}

	@Override
	public String getNBTKey()
	{
		return NBT_KEY;
	}

	@Override
	public LivingProofEvent createEvent(LivingEntity entity)
	{
		return new LivingSpaceGravityProofEvent(entity);
	}

	@SubscribeEvent
	public void onProofEnchantment(LivingSpaceGravityProofEvent e)
	{
		LivingEntity entity = e.getEntityLiving();
		ProofSession session = new SpaceGravityProofEnchantmentSession(entity, AddonEnchantments.GRAVITY_NORMALIZING.get());
		e.setProofDuration(session.provide());
	}

	@SubscribeEvent
	public void onLivingGravity(LivingGravityEvent e)
	{
		if (this.tryProvideProof(e) == true)
		{
			GravityNormalizeUtils.setNormalizing(e.getEntityLiving(), true);
		}

	}

}
