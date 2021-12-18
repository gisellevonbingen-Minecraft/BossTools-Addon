package beyond_earth_giselle_addon.common.compat.waila;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

@mcp.mobius.waila.api.WailaPlugin
public class AddonWailaPlugin implements IWailaPlugin
{
	@Override
	public void register(IRegistrar registrar)
	{
		registrar.registerBlockDataProvider(AddonBlockDataProvider.INSTANCE, BlockEntity.class);
		registrar.registerComponentProvider(AddonBlockDataProvider.INSTANCE, TooltipPosition.BODY, Block.class);

		registrar.registerEntityDataProvider(AddonEntityDataProvider.INSTANCE, Entity.class);
		registrar.registerComponentProvider(AddonEntityDataProvider.INSTANCE, TooltipPosition.BODY, Entity.class);
	}

}
