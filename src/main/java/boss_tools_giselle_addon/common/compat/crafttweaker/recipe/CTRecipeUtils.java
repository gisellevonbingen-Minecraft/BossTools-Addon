package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

public class CTRecipeUtils
{
	private static final Gson JSON_RECIPE_GSON = new GsonBuilder().create();

	public static void addRecipe(IRecipe<?> recipe)
	{
		IRecipeType<?> recipeType = recipe.getType();
		IRecipeManager recipeManager = RecipeTypeBracketHandler.getOrDefault(recipeType);
		CraftTweakerAPI.apply(new ActionAddRecipe(recipeManager, recipe, ""));
	}

	public static IRecipe<?> parseRecipe(ResourceLocation id, MapData mapData)
	{
		JsonObject json = JSON_RECIPE_GSON.fromJson(mapData.toJsonString(), JsonObject.class);
		IRecipe<?> recipe = parseRecipe(id, json);
		return recipe;
	}

	public static IRecipe<?> parseRecipe(ResourceLocation id, JsonObject json)
	{
		IRecipe<?> recipe = RecipeManager.fromJson(id, json);
		return recipe;
	}

}
