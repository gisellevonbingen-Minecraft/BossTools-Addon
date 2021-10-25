package giselle.boss_tools_addon.common.adapter;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;

public class FuelAdapterBossToolsEntity extends FuelAdapter<Entity>
{
	private final Item fuelFullItem;

	public FuelAdapterBossToolsEntity(Entity target, Item fuelFullItem)
	{
		super(target);
		this.fuelFullItem = fuelFullItem;
	}

	@Override
	public int getFuelSlot()
	{
		return 0;
	}

	@Override
	public boolean canInsertFuel()
	{
		CompoundNBT compound = this.getTarget().getPersistentData();
		return compound.getDouble("fuel") == 0.0D && compound.getDouble("Rocketfuel") == 0.0D;
	}

	@Override
	public final Item getFuelFullItem()
	{
		return this.fuelFullItem;
	}

}
