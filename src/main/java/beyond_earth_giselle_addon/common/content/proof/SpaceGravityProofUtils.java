package beyond_earth_giselle_addon.common.content.proof;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.content.gravity.GravityNormalizeUtils;
import beyond_earth_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.events.forge.EntityGravityEvent;

public class SpaceGravityProofUtils extends ProofAbstractUtils
{
	public static final String NBT_KEY = BeyondEarthAddon.rl("space_gravity_proof").toString();
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
	public void onLivingGravity(EntityGravityEvent e)
	{
		if (this.tryProvideProof(e) == true)
		{
			e.setCanceled(true);
		}

	}

}
