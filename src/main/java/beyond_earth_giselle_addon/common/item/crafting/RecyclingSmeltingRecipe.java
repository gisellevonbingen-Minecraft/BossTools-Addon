package beyond_earth_giselle_addon.common.item.crafting;

import beyond_earth_giselle_addon.common.registries.AddonRecipes;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class RecyclingSmeltingRecipe extends SmeltingRecipe implements IRecyclingVanillaRecipe<SmeltingRecipe, Container>
{
	private final SmeltingRecipe parent;
	private final ItemOutput output;

	public RecyclingSmeltingRecipe(SmeltingRecipe parent, ItemOutput output)
	{
		super(parent.getId(), parent.getGroup(), parent.getIngredients().get(0), ItemStack.EMPTY, parent.getExperience(), parent.getCookingTime());
		this.parent = parent;
		this.output = output;
	}

	@Override
	public ItemStack assemble(Container container)
	{
		return IRecyclingVanillaRecipe.super.assemble(container);
	}

	@Override
	public ItemStack getResultItem()
	{
		return IRecyclingVanillaRecipe.super.getResultItem();
	}

	@Override
	public ItemOutput getOutput()
	{
		return this.output;
	}

	@Override
	public SmeltingRecipe getParent()
	{
		return this.parent;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_RECYCLING_SMELTING.get();
	}

}
