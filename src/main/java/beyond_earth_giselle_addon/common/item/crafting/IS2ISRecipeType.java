package beyond_earth_giselle_addon.common.item.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipe;

public abstract class IS2ISRecipeType<T extends RecipeType<? extends R>, R extends Recipe<Container>>
{
	public abstract boolean testRecipeType(RecipeType<? extends Recipe<Container>> recipeType);

	public abstract R find(RecipeManager recipeManager, Level level, T recipeType, ItemStack itemStack);

	public abstract ItemStackToItemStackRecipe createCache(R recipe);
}
