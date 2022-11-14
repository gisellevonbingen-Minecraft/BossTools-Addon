package beyond_earth_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

import beyond_earth_giselle_addon.common.registries.AddonRecipes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipe;

public class ExtrudingRecipe extends ItemStackToItemStackRecipe
{
	public static final int DEFAULT_COOK_TIME = 50;

	public ExtrudingRecipe(ResourceLocation id, JsonObject json)
	{
		super(id, json);
	}

	public ExtrudingRecipe(ResourceLocation id, FriendlyByteBuf buffer)
	{
		super(id, buffer);
	}

	public ExtrudingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack output, int cookTime)
	{
		super(id, ingredient, output, cookTime);
	}

	@Override
	protected int getDefaultCookTime()
	{
		return DEFAULT_COOK_TIME;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_EXTRUDING.get();
	}

	@Override
	public RecipeType<?> getType()
	{
		return AddonRecipes.EXTRUDING;
	}

}
