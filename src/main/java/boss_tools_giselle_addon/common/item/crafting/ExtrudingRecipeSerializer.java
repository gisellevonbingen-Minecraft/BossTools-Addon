package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class ExtrudingRecipeSerializer extends BossToolsRecipeSerializer<ExtrudingRecipe>
{
	@Override
	public ExtrudingRecipe fromJson(ResourceLocation id, JsonObject json)
	{
		return new ExtrudingRecipe(id, json);
	}

	@Override
	public ExtrudingRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer)
	{
		return new ExtrudingRecipe(id, buffer);
	}

	@Override
	public void toNetwork(PacketBuffer buffer, ExtrudingRecipe recipe)
	{
		recipe.write(buffer);
	}

}
