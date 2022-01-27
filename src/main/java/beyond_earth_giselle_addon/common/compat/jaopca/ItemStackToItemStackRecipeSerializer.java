package beyond_earth_giselle_addon.common.compat.jaopca;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipe;
import thelm.jaopca.api.recipes.IRecipeSerializer;

public class ItemStackToItemStackRecipeSerializer implements IRecipeSerializer
{
	private ItemStackToItemStackRecipe recipe;

	public ItemStackToItemStackRecipeSerializer(ItemStackToItemStackRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public JsonElement get()
	{
		ItemStackToItemStackRecipe recipe = this.getRecipe();

		JsonObject json = new JsonObject();
		json.addProperty("type", recipe.getSerializer().getRegistryName().toString());

		Ingredient ingredient = recipe.getIngredient();
		JsonObject inputJson = new JsonObject();
		inputJson.add("ingredient", ingredient.toJson());
		json.add("input", inputJson);

		ItemStack output = recipe.getOutput();
		JsonObject outputJson = new JsonObject();
		outputJson.addProperty("item", output.getItem().getRegistryName().toString());
		outputJson.addProperty("count", output.getCount());
		json.add("output", outputJson);

		json.addProperty("cookTime", recipe.getCookTime());

		return json;
	}

	public ItemStackToItemStackRecipe getRecipe()
	{
		return this.recipe;
	}

}
