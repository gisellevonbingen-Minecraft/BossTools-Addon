package beyond_earth_giselle_addon.common.content.proof;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.events.forgeevents.LivingSetFireInHotPlanetEvent;

public class SpaceFireProofUtils extends ProofAbstractUtils
{
	public static final String NBT_KEY = BeyondEarthAddon.rl("space_fire_proof").toString();
	public static final SpaceFireProofUtils INSTANCE = new SpaceFireProofUtils();

	private SpaceFireProofUtils()
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
		return new LivingSpaceFireProofEvent(entity);
	}

	@SubscribeEvent
	public void onProofEnchantment(LivingSpaceFireProofEvent e)
	{
		LivingEntity entity = e.getEntityLiving();
		ProofSession session = new SpaceFireProofEnchantmentSession(entity, AddonEnchantments.SPACE_FIRE_PROOF.get());
		e.setProofDuration(session.provide());
	}

	@SubscribeEvent
	public void onLivingSetFireInHotPlanet(LivingSetFireInHotPlanetEvent e)
	{
		if (this.tryProvideProof(e) == true)
		{
			e.setCanceled(true);
		}

	}

}
