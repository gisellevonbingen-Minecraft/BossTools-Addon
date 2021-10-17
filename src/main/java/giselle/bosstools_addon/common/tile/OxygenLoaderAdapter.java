package giselle.bosstools_addon.common.tile;

import net.mrscauthd.boss_tools.block.OxygenMachineBlock;
import net.mrscauthd.boss_tools.block.OxygenMachineBlock.CustomTileEntity;

public class OxygenLoaderAdapter extends OxygenMachineAdapter
{
	public static final String KEY_FUEL = "fuel";
	public static final String KEY_MAXFUEL = "maxFuel";
	public static final double FALLBACK_MAXFUEL = 200.0D;

	private OxygenMachineBlock.CustomTileEntity tile;

	public OxygenLoaderAdapter(OxygenMachineBlock.CustomTileEntity tile)
	{
		this.tile = tile;
	}

	@Override
	public double getFuel()
	{
		return this.getTile().getTileData().getDouble(KEY_FUEL);
	}

	@Override
	public void setFuel(double fuel)
	{
		this.getTile().getTileData().putDouble(KEY_FUEL, fuel);
	}

	@Override
	public double getMaxFuel(double fallback)
	{
		double maxFuel = this.getTile().getTileData().getDouble(KEY_MAXFUEL);
		return maxFuel > 0.0D ? maxFuel : fallback;
	}

	@Override
	public void setMaxFuel(double maxFuel)
	{
		this.getTile().getTileData().putDouble(KEY_MAXFUEL, maxFuel);
	}

	@Override
	public double getFallbackMaxFuel()
	{
		return FALLBACK_MAXFUEL;
	}

	@Override
	public CustomTileEntity getTile()
	{
		return this.tile;
	}

}
