package giselle.bosstools_addon.common.tile;

import net.minecraft.tileentity.TileEntity;

public abstract class OxygenMachineAdapter
{
	public abstract double getFuel();

	public abstract void setFuel(double fuel);

	public double getMaxFuel()
	{
		return getMaxFuel(this.getFallbackMaxFuel());
	}

	public abstract double getMaxFuel(double fallback);

	public abstract void setMaxFuel(double maxFuel);

	public abstract double getFallbackMaxFuel();

	public abstract TileEntity getTile();
}
