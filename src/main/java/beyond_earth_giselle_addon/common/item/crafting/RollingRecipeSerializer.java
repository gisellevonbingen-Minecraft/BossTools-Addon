package beyond_earth_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.crafting.BeyondEarthRecipeSerializer;

public class RollingRecipeSerializer extends BeyondEarthRecipeSerializer<RollingRecipe>
{
	@Override
	public RollingRecipe fromJson(ResourceLocation id, JsonObject json)
	{
		return new RollingRecipe(id, json);
	}

	@Override
	public RollingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
	{
		return new RollingRecipe(id, buffer);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, RollingRecipe recipe)
	{
		recipe.write(buffer);
	}

}
