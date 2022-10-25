package beyond_earth_giselle_addon.common.item.crafting.conditions;

import com.google.gson.JsonObject;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class RecyclingEnabledCondition implements ICondition
{
	private static final ResourceLocation NAME = BeyondEarthAddon.rl("recycling_enabled");

	public RecyclingEnabledCondition()
	{

	}

	@Override
	public ResourceLocation getID()
	{
		return NAME;
	}

	@Override
	public boolean test(IContext context)
	{
		return AddonConfigs.Common.recipes.recycling_enabled.get();
	}

	@Override
	public String toString()
	{
		return "recycling_enabled()";
	}

	public static class Serializer implements IConditionSerializer<RecyclingEnabledCondition>
	{
		public static final Serializer INSTANCE = new Serializer();

		@Override
		public void write(JsonObject json, RecyclingEnabledCondition value)
		{

		}

		@Override
		public RecyclingEnabledCondition read(JsonObject json)
		{
			return new RecyclingEnabledCondition();
		}

		@Override
		public ResourceLocation getID()
		{
			return RecyclingEnabledCondition.NAME;
		}

	}

}
