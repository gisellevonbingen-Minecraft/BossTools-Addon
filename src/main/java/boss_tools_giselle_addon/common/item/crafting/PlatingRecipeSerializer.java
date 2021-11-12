package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class PlatingRecipeSerializer extends BossToolsRecipeSerializer<PlatingRecipe>
{
	@Override
	public PlatingRecipe fromJson(ResourceLocation id, JsonObject json)
	{
		return new PlatingRecipe(id, json);
	}

	@Override
	public PlatingRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer)
	{
		return new PlatingRecipe(id, buffer);
	}

	@Override
	public void toNetwork(PacketBuffer buffer, PlatingRecipe recipe)
	{
		recipe.write(buffer);
	}

}
