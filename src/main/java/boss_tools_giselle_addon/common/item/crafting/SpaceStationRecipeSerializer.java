package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class SpaceStationRecipeSerializer extends BossToolsRecipeSerializer<SpaceStationRecipe>
{
	@Override
	public SpaceStationRecipe fromJson(ResourceLocation id, JsonObject json)
	{
		return new SpaceStationRecipe(id, json);
	}

	@Override
	public SpaceStationRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer)
	{
		return new SpaceStationRecipe(id, buffer);
	}

	@Override
	public void toNetwork(PacketBuffer buffer, SpaceStationRecipe recipe)
	{
		recipe.write(buffer);
	}

}
