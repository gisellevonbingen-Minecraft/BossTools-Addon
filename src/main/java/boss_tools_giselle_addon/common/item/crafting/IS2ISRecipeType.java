package boss_tools_giselle_addon.common.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;

public abstract class IS2ISRecipeType<T extends IRecipeType<? extends R>, R extends IRecipe<IInventory>>
{
	public abstract boolean testRecipeType(IRecipeType<? extends IRecipe<IInventory>> recipeType);

	public abstract R find(RecipeManager recipeManager, World world, T recipeType, ItemStack itemStack);

	public abstract ItemStackToItemStackRecipe createCache(R recipe);
}
