package boss_tools_giselle_addon.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class FallGravityProcedureEvent extends EntityEvent
{
	public FallGravityProcedureEvent(Entity entity)
	{
		super(entity);
	}

	@Override
	public boolean isCancelable()
	{
		return true;
	}

}
