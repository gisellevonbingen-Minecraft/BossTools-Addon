package giselle.bosstools_addon.common.tile;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

public abstract class FuelEntityAdapter
{
	public abstract int getFuelSlot();

	public abstract boolean canInsertFuel();

	public abstract Item getFuelItem();

	public abstract Entity getEntity();
}
