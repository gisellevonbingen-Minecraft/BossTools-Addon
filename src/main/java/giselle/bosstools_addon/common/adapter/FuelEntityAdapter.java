package giselle.bosstools_addon.common.adapter;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

public abstract class FuelEntityAdapter
{
	public abstract int getFuelSlot();

	public abstract boolean canInsertFuel();

	public abstract Item getFuelFullItem();

	public abstract Entity getEntity();
}
