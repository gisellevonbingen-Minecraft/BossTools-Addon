package boss_tools_giselle_addon.common.compat.jer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;

public class OreGenHelper
{
	public static final NBTDynamicOps Ops = NBTDynamicOps.INSTANCE;

	public static <T> INBT encode(Codec<T> codec, T input)
	{
		return codec.encodeStart(Ops, input).result().get();
	}

	public static <T> T decode(Codec<T> codec, INBT tag)
	{
		return codec.decode(new Dynamic<INBT>(Ops, tag)).result().get().getFirst();
	}

	public static FeatureSpread getFeatureSpread(FeatureSpreadConfig config)
	{
		CompoundNBT compound = (CompoundNBT) encode(FeatureSpreadConfig.CODEC, config);
		INBT countTag = compound.get("count");
		return decode(FeatureSpread.CODEC, countTag);
	}

	public static int getFeatureSpreadBase(FeatureSpread featureSpread)
	{
		INBT tag = encode(FeatureSpread.CODEC, featureSpread);

		if (tag instanceof NumberNBT)
		{
			return ((NumberNBT) tag).getAsInt();
		}
		else if (tag instanceof CompoundNBT)
		{
			return ((CompoundNBT) tag).getInt("base");
		}

		return 0;
	}

	private OreGenHelper()
	{

	}

}
