package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class RollingRecipeSerializer extends BossToolsRecipeSerializer<RollingRecipe>
{
	@Override
	public RollingRecipe fromJson(ResourceLocation id, JsonObject json)
	{
		return new RollingRecipe(id, json);
	}

	@Override
	public RollingRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer)
	{
		return new RollingRecipe(id, buffer);
	}

	@Override
	public void toNetwork(PacketBuffer buffer, RollingRecipe recipe)
	{
		recipe.write(buffer);
	}

}
