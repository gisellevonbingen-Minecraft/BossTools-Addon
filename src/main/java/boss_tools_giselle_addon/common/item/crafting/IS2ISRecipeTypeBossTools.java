package boss_tools_giselle_addon.common.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;

public class IS2ISRecipeTypeBossTools extends IS2ISRecipeType<ItemStackToItemStackRecipeType<ItemStackToItemStackRecipe>, ItemStackToItemStackRecipe>
{
	public IS2ISRecipeTypeBossTools()
	{

	}

	@Override
	public boolean testRecipeType(IRecipeType<? extends IRecipe<IInventory>> recipeType)
	{
		return recipeType instanceof ItemStackToItemStackRecipeType;
	}

	@Override
	public ItemStackToItemStackRecipe find(RecipeManager recipeManager, World world, ItemStackToItemStackRecipeType<ItemStackToItemStackRecipe> recipeType, ItemStack itemStack)
	{
		return recipeType.findFirst(world, r -> r.test(itemStack));
	}

	@Override
	public ItemStackToItemStackRecipe createCache(ItemStackToItemStackRecipe recipe)
	{
		return recipe;
	}

}
