package boss_tools_giselle_addon.common.content.proof.fire;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.content.proof.AbstractSpaceProofUtils;
import boss_tools_giselle_addon.common.content.proof.LivingSpaceProofEvent;
import boss_tools_giselle_addon.common.content.proof.SpaceProofSession;
import boss_tools_giselle_addon.common.enchantment.AddonEnchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSetFireInHotPlanetEvent;

public class SpaceFireProofUtils extends AbstractSpaceProofUtils
{
	public static final String NBT_KEY = BossToolsAddon.rl("space_fire_proof").toString();
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
	public LivingSpaceProofEvent createEvent(LivingEntity entity)
	{
		return new LivingSpaceFireProofEvent(entity);
	}

	@SubscribeEvent
	public void onProofEnchantment(LivingSpaceFireProofEvent e)
	{
		LivingEntity entity = e.getEntityLiving();
		SpaceProofSession session = new SpaceFireProofEnchantmentSession(entity, AddonEnchantments.SPACE_FIRE_PROOF.get());
		e.setProofDuration(session.provide());
	}

	@SubscribeEvent
	public void onLivingSetFireInHotPlanet(LivingSetFireInHotPlanetEvent e)
	{
		if (e.isCanceled() == true)
		{
			return;
		}
		else if (this.tryProvideProof(e.getEntityLiving()) == true)
		{
			e.setCanceled(true);
		}

	}

}
