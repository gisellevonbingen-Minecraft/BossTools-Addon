package boss_tools_giselle_addon.common.compat.jer;

import jeresources.api.distributions.DistributionBase;
import jeresources.api.distributions.DistributionSquare;

public abstract class DistributionShape
{
	public DistributionShape()
	{

	}

	public abstract DistributionBase build(OreGenBuilder builder);

	public static class DistributionShapeSqaure extends DistributionShape
	{
		public int minY;
		public int maxY;

		public DistributionShapeSqaure()
		{

		}

		@Override
		public DistributionBase build(OreGenBuilder builder)
		{
			return new DistributionSquare(builder.veinCountPerChunk, builder.veinSize, this.minY, this.maxY);
		}

	}

}
