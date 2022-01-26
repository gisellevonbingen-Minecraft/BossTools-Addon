package boss_tools_giselle_addon.common.content.proof;

import boss_tools_giselle_addon.common.content.proof.fire.SpaceFireProofUtils;
import boss_tools_giselle_addon.common.content.proof.oxygen.SpaceOxygenProofUtils;
import boss_tools_giselle_addon.common.util.NBTUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class AbstractSpaceProofUtils
{
	public static final String NBT_PROOF_DURATION_KEY = "proof_duration";

	public static void register(IEventBus bus)
	{
		bus.register(SpaceFireProofUtils.INSTANCE);
		bus.register(SpaceOxygenProofUtils.INSTANCE);
	}

	public AbstractSpaceProofUtils()
	{

	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e)
	{
		LivingEntity entity = e.getEntityLiving();

		if (entity.level.isClientSide() == true)
		{
			return;
		}

		this.reduceProofDuration(entity);
	}

	public void reduceProofDuration(LivingEntity entity)
	{
		int proofDuration = getProofDuration(entity);

		if (proofDuration > 0)
		{
			setProofDuration(entity, proofDuration - 1);
		}

	}

	public int getProofDuration(LivingEntity entity)
	{
		CompoundNBT compound = NBTUtils.getTag(entity, this.getNBTKey());
		return compound != null ? compound.getInt(NBT_PROOF_DURATION_KEY) : 0;
	}

	public void setProofDuration(LivingEntity entity, int proofDuration)
	{
		CompoundNBT compound = NBTUtils.getOrCreateTag(entity, this.getNBTKey());
		compound.putLong(NBT_PROOF_DURATION_KEY, Math.max(proofDuration, 0));
	}

	public boolean tryProvideProof(LivingEntity entity)
	{
		if (this.getProofDuration(entity) > 0)
		{
			return true;
		}
		else if (this.tryProvideProofEvent(entity) == true)
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	public abstract LivingSpaceProofEvent createEvent(LivingEntity entity);

	public boolean tryProvideProofEvent(LivingEntity entity)
	{
		LivingSpaceProofEvent event = this.createEvent(entity);

		if (event != null)
		{
			LivingSpaceProofEvent.postUntilDuration(event);
			int proofDuration = event.getProofDuration();

			if (proofDuration > 0)
			{
				this.setProofDuration(entity, proofDuration);
				return true;
			}

		}

		return false;
	}

	public abstract String getNBTKey();

}
