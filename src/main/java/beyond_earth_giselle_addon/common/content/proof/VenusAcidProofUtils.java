package beyond_earth_giselle_addon.common.content.proof;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.enchantment.AddonEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.events.forgeevents.LivingSetVenusRainEvent;

public class VenusAcidProofUtils extends ProofAbstractUtils
{
	public static final String NBT_KEY = BeyondEarthAddon.rl("venus_acid_proof").toString();
	public static final VenusAcidProofUtils INSTANCE = new VenusAcidProofUtils();

	private VenusAcidProofUtils()
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
		ProofSession session = new VenusAcidProofEnchantmentSession(entity, AddonEnchantments.VENUS_ACID_PROOF.get());
		e.setProofDuration(session.provide());
	}

	@SubscribeEvent
	public void onLivingSetVenusRain(LivingSetVenusRainEvent e)
	{
		if (this.tryProvideProof(e) == true)
		{
			e.setCanceled(true);
		}

	}

}
