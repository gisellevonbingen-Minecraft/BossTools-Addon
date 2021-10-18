package giselle.bosstools_addon.common.tile;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.mrscauthd.boss_tools.block.FuelBlock;
import net.mrscauthd.boss_tools.entity.RoverEntity;

public class FuelEntityAdapterRover extends FuelEntityAdapter
{
	private RoverEntity.CustomEntity entity;

	public FuelEntityAdapterRover(RoverEntity.CustomEntity entity)
	{
		this.entity = entity;
	}

	@Override
	public int getFuelSlot()
	{
		return 0;
	}

	@Override
	public boolean canInsertFuel()
	{
		CompoundNBT compound = this.getEntity().getPersistentData();
		return compound.getDouble("fuel") == 0.0D && compound.getDouble("Rocketfuel") == 0.0D;
	}

	@Override
	public Item getFuelItem()
	{
		return FuelBlock.bucket;
	}

	@Override
	public Entity getEntity()
	{
		return this.entity;
	}

}
