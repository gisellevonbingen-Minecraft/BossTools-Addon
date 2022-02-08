package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipe;

public class SpaceStationRecipe extends BossToolsRecipe
{
	public static final ResourceLocation KEY;

	static
	{
		ResourceLocation id = AddonRecipes.RECIPE_SERIALIZER_SPACE_STATION.getId();
		KEY = new ResourceLocation(id.getNamespace(), id.getPath() + "/space_station");
	}

	public static SpaceStationRecipe getRecipe(RecipeManager recipeManager)
	{
		return (SpaceStationRecipe) recipeManager.byKey(SpaceStationRecipe.KEY).orElse(null);
	}

	private final NonNullList<IngredientStack> ingredients;

	public SpaceStationRecipe(ResourceLocation id, PacketBuffer buffer)
	{
		super(id);
		this.ingredients = NonNullList.withSize(buffer.readInt(), IngredientStack.EMPTY);

		for (int i = 0; i < this.ingredients.size(); i++)
		{
			this.ingredients.set(i, new IngredientStack(buffer));
		}

	}

	public SpaceStationRecipe(ResourceLocation id, JsonObject json)
	{
		super(id);

		JsonArray asJsonArray = JSONUtils.getAsJsonArray(json, "ingredients");
		this.ingredients = NonNullList.withSize(asJsonArray.size(), IngredientStack.EMPTY);

		for (int i = 0; i < this.ingredients.size(); i++)
		{
			this.ingredients.set(i, new IngredientStack(asJsonArray.get(i)));
		}

	}

	public SpaceStationRecipe(ResourceLocation id, NonNullList<IngredientStack> ingredients)
	{
		super(id);
		this.ingredients = NonNullList.of(IngredientStack.EMPTY, ingredients.toArray(IngredientStack[]::new));
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		super.write(buffer);

		buffer.writeInt(this.ingredients.size());
		this.ingredients.forEach(e -> e.toNetwork(buffer));
	}

	@Override
	public boolean canCraftInDimensions(int p_43999_, int p_44000_)
	{
		return true;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_SPACE_STATION.get();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return AddonRecipes.SPACE_STATION;
	}

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(Ingredient.EMPTY, this.ingredients.stream().map(IngredientStack::getIngredient).toArray(Ingredient[]::new));
	}

	public NonNullList<IngredientStack> getIngredientStacks()
	{
		return NonNullList.of(IngredientStack.EMPTY, this.ingredients.toArray(IngredientStack[]::new));
	}

}
