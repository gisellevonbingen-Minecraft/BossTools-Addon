package boss_tools_giselle_addon.common.content.proof;

import net.minecraft.entity.LivingEntity;

public abstract class SpaceProofSession
{
	private LivingEntity entity;

	public SpaceProofSession(LivingEntity entity)
	{
		this.entity = entity;
	}

	public boolean canProvide()
	{
		return true;
	}

	public void onProvide()
	{

	}

	public int getProofDuration()
	{
		return 0;
	}

	public int provide()
	{
		if (this.canProvide() == true)
		{
			int proofDuration = this.getProofDuration();
			this.onProvide();

			return proofDuration;
		}
		else
		{
			return 0;
		}

	}

	public LivingEntity getEntity()
	{
		return this.entity;
	}

}
