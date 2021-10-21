package giselle.bosstools_addon.common.adapter;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.mrscauthd.boss_tools.block.FuelBlock;
import net.mrscauthd.boss_tools.entity.RocketEntity;

public class FuelEntityAdapterRocket1 extends FuelEntityAdapter
{
	private RocketEntity.CustomEntity entity;

	public FuelEntityAdapterRocket1(RocketEntity.CustomEntity entity)
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
	public Item getFuelFullItem()
	{
		return FuelBlock.bucket;
	}

	@Override
	public Entity getEntity()
	{
		return this.entity;
	}

}
