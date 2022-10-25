package beyond_earth_giselle_addon.common.item.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.mrscauthd.beyond_earth.common.data.recipes.ItemStackToItemStackRecipe;

public class ItemStackToItemStackRecipeWrapper extends ItemStackToItemStackRecipe
{
	private final Recipe<Container> parent;
	private final float experience;

	public ItemStackToItemStackRecipeWrapper(Recipe<Container> parent, Ingredient ingredient, ItemStack result, int cookTime)
	{
		this(parent, ingredient, result, cookTime, 0.0F);
	}

	public ItemStackToItemStackRecipeWrapper(Recipe<Container> parent, Ingredient ingredient, ItemStack result, int cookTime, float experience)
	{
		super(parent.getId(), ingredient, result, cookTime);
		this.parent = parent;
		this.experience = experience;
	}

	public final Recipe<Container> getParent()
	{
		return this.parent;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return this.getParent().getSerializer();
	}

	@Override
	public RecipeType<?> getType()
	{
		return this.getParent().getType();
	}

	public float getExperience()
	{
		return this.experience;
	}

}
