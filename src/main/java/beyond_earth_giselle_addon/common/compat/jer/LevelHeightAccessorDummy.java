package beyond_earth_giselle_addon.common.compat.jer;

import net.minecraft.world.level.LevelHeightAccessor;

public class LevelHeightAccessorDummy implements LevelHeightAccessor
{
	@Override
	public int getHeight()
	{
		return 0;
	}

	@Override
	public int getMinBuildHeight()
	{
		return 0;
	}

}
