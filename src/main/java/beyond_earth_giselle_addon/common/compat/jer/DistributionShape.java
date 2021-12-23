package beyond_earth_giselle_addon.common.compat.jer;

import jeresources.api.distributions.DistributionBase;
import jeresources.api.distributions.DistributionSquare;
import jeresources.api.distributions.DistributionTriangular;

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

	public static class DistributionShapeTriangle extends DistributionShape
	{
		public int minY;
		public int maxY;

		public DistributionShapeTriangle()
		{

		}

		@Override
		public DistributionBase build(OreGenBuilder builder)
		{
			int midY = (this.maxY + this.minY) / 2;
			int range = (this.maxY - this.minY) / 2;
			return new DistributionTriangular(builder.veinCountPerChunk, builder.veinSize, midY, range);
		}

	}

}
