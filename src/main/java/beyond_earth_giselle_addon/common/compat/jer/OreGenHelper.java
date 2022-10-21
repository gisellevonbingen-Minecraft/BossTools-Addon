package beyond_earth_giselle_addon.common.compat.jer;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;

import beyond_earth_giselle_addon.common.compat.jer.DistributionShape.DistributionShapeTriangle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;

public class OreGenHelper
{
	public static final WorldGenerationContext DUMMY_CONTEXT = new WorldGenerationContext(new ChunkGeneratorDummy(), new LevelHeightAccessorDummy());
	public static final NbtOps Ops = NbtOps.INSTANCE;

	public static <T> Tag encode(Codec<T> codec, T input)
	{
		return codec.encodeStart(Ops, input).result().get();
	}

	public static <T> T decode(Codec<T> codec, Tag tag)
	{
		return codec.decode(new Dynamic<>(Ops, tag)).result().get().getFirst();
	}

	public static IntProvider getCountPlacementCount(CountPlacement placement)
	{
		CompoundTag compound = (CompoundTag) encode(CountPlacement.CODEC, placement);
		Tag countTag = compound.get("count");
		return decode(IntProvider.CODEC, countTag);
	}

	@Nullable
	public static DistributionShape getHeightRangePlacementShape(HeightRangePlacement placement)
	{
		CompoundTag compound = (CompoundTag) encode(HeightRangePlacement.CODEC, placement);
		Tag heightTag = compound.get("height");
		return getHeightProvider(heightTag);
	}

	@Nullable
	public static DistributionShape getHeightProvider(Tag tag)
	{
		HeightProvider heightProvider = decode(HeightProvider.CODEC, tag);

		if (heightProvider instanceof TrapezoidHeight trapezoidHeight)
		{
			return getTrapezoidHeightShape(trapezoidHeight);
		}

		return null;
	}

	public static DistributionShapeTriangle getTrapezoidHeightShape(TrapezoidHeight trapezoidHeight)
	{
		CompoundTag compound = (CompoundTag) encode(TrapezoidHeight.CODEC, trapezoidHeight);
		VerticalAnchor min_inclusive = getVerticalAnchor(compound.get("min_inclusive"));
		VerticalAnchor max_inclusive = getVerticalAnchor(compound.get("max_inclusive"));

		DistributionShapeTriangle shape = new DistributionShapeTriangle();
		shape.minY = min_inclusive.resolveY(DUMMY_CONTEXT);
		shape.maxY = max_inclusive.resolveY(DUMMY_CONTEXT);
		return shape;
	}

	public static VerticalAnchor getVerticalAnchor(Tag tag)
	{
		return decode(VerticalAnchor.CODEC, tag);
	}

	private OreGenHelper()
	{

	}

}
