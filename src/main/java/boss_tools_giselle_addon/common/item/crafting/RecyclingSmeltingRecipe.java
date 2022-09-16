package boss_tools_giselle_addon.common.item.crafting;

import boss_tools_giselle_addon.common.registries.AddonRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

public class RecyclingSmeltingRecipe extends FurnaceRecipe implements IRecyclingVanillaRecipe<FurnaceRecipe, IInventory>
{
	private final FurnaceRecipe parent;
	private final ItemOutput output;

	public RecyclingSmeltingRecipe(FurnaceRecipe parent, ItemOutput output)
	{
		super(parent.getId(), parent.getGroup(), parent.getIngredients().get(0), ItemStack.EMPTY, parent.getExperience(), parent.getCookingTime());
		this.parent = parent;
		this.output = output;
	}

	@Override
	public ItemStack assemble(IInventory container)
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

	public FurnaceRecipe getParent()
	{
		return this.parent;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_RECYCLING_SMELTING.get();
	}

}
