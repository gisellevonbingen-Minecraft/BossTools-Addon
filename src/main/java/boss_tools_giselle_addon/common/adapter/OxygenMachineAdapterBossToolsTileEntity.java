package boss_tools_giselle_addon.common.adapter;

import net.minecraft.tileentity.TileEntity;

public class OxygenMachineAdapterBossToolsTileEntity extends OxygenMachineAdapter<TileEntity>
{
	public static final String KEY_FUEL = "fuel";
	public static final String KEY_MAXFUEL = "maxFuel";

	private final double fallbackMaxFuel;

	public OxygenMachineAdapterBossToolsTileEntity(TileEntity target, double fallbackMaxFuel)
	{
		super(target);
		this.fallbackMaxFuel = fallbackMaxFuel;
	}

	@Override
	public double getFuel()
	{
		return this.getTarget().getTileData().getDouble(KEY_FUEL);
	}

	@Override
	public void setFuel(double fuel)
	{
		this.getTarget().getTileData().putDouble(KEY_FUEL, fuel);
	}

	@Override
	public double getMaxFuel(double fallback)
	{
		double maxFuel = this.getTarget().getTileData().getDouble(KEY_MAXFUEL);
		return maxFuel > 0.0D ? maxFuel : fallback;
	}

	@Override
	public void setMaxFuel(double maxFuel)
	{
		this.getTarget().getTileData().putDouble(KEY_MAXFUEL, maxFuel);
	}

	@Override
	public final double getFallbackMaxFuel()
	{
		return this.fallbackMaxFuel;
	}

}
