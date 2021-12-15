package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;

public class ExtrudingRecipe extends ItemStackToItemStackRecipe
{
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
