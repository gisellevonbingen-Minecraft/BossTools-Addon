package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class RollingRecipeSerializer extends BossToolsRecipeSerializer<RollingRecipe>
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
