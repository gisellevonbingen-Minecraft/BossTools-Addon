package giselle.bosstools_addon.common.adapter;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.item.FuelBucketBigItem;

public class FuelEntityAdapterRocket3 extends FuelEntityAdapter
{
	private RocketTier3Entity.CustomEntity entity;

	public FuelEntityAdapterRocket3(RocketTier3Entity.CustomEntity entity)
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
		return FuelBucketBigItem.block;
	}

	@Override
	public Entity getEntity()
	{
		return this.entity;
	}

}
