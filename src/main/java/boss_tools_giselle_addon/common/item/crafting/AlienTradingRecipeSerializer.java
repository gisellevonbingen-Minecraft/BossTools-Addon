package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class AlienTradingRecipeSerializer extends BossToolsRecipeSerializer<AlienTradingRecipe>
{
	@Override
	public AlienTradingRecipe fromJson(ResourceLocation id, JsonObject json)
	{
		return new AlienTradingRecipe(id, json);
	}

	@Override
	public AlienTradingRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer)
	{
		return new AlienTradingRecipe(id, buffer);
	}

	@Override
	public void toNetwork(PacketBuffer buffer, AlienTradingRecipe recipe)
	{
		recipe.write(buffer);
	}

}
