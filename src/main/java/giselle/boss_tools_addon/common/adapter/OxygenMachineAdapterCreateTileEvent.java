package giselle.boss_tools_addon.common.adapter;

import net.minecraft.tileentity.TileEntity;

public class OxygenMachineAdapterCreateTileEvent extends AdapterCreateEvent<TileEntity, OxygenMachineAdapter<? extends TileEntity>>
{
	public OxygenMachineAdapterCreateTileEvent(TileEntity target)
	{
		super(target);
	}

}
