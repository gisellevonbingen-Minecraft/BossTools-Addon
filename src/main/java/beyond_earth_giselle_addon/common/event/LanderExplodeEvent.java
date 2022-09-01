package beyond_earth_giselle_addon.common.event;

import net.minecraftforge.event.entity.EntityEvent;
import net.mrscauthd.beyond_earth.entities.LanderEntity;

public class LanderExplodeEvent extends EntityEvent
{
	private final LanderEntity landerEntity;

	public LanderExplodeEvent(LanderEntity landerEntity)
	{
		super(landerEntity);
		this.landerEntity = landerEntity;
	}

	public LanderEntity getLanderEntity()
	{
		return this.landerEntity;
	}

}
