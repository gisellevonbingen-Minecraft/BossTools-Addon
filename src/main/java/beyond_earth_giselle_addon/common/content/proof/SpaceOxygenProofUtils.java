package beyond_earth_giselle_addon.common.content.proof;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.ModInit;

public class SpaceOxygenProofUtils extends ProofAbstractUtils
{
	public static final String NBT_KEY = BeyondEarthAddon.rl("space_breathing").toString();
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
	public LivingProofEvent createEvent(LivingEntity entity)
	{
		return new LivingSpaceOxygenProofEvent(entity);
	}

	@SubscribeEvent
	public void onProofEnchantment(LivingSpaceOxygenProofEvent e)
	{
		LivingEntity entity = e.getEntityLiving();
		ProofSession session = new SpaceOxygenProofEnchantmentSession(entity, AddonEnchantments.SPACE_BREATHING.get());
		e.setProofDuration(session.provide());
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e)
	{
		if (e.getSource() != ModInit.DAMAGE_SOURCE_OXYGEN)
		{
			return;
		}
		else if (this.tryProvideProof(e) == true)
		{
			e.setCanceled(true);
		}

	}

}
