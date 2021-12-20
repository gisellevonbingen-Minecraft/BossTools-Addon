package beyond_earth_giselle_addon.common.item.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipe;

public class IS2ISRecipeCache
{
	private static final List<IS2ISRecipeType<? extends RecipeType<? extends Recipe<Container>>, ? extends Recipe<Container>>> types;
	private static final Map<Recipe<Container>, ItemStackToItemStackRecipe> caches;

	static
	{
		types = new ArrayList<>();
		addType(new IS2ISRecipeTypeBossTools());
		addType(new IS2ISRecipeTypeMinecraftBlasting());

		caches = new HashMap<>();
	}

	public static List<IS2ISRecipeType<? extends RecipeType<? extends Recipe<Container>>, ? extends Recipe<Container>>> getTypes()
	{
		return Collections.unmodifiableList(types);
	}

	public static <T extends RecipeType<? extends R>, R extends Recipe<Container>> void addType(IS2ISRecipeType<T, R> type)
	{
		types.add(type);
	}

	public static ItemStackToItemStackRecipe cache(RecipeManager recipeManager, Level level, ItemStack itemStack, List<RecipeType<? extends Recipe<Container>>> types)
	{
		for (RecipeType<? extends Recipe<Container>> type : types)
		{
			ItemStackToItemStackRecipe cache = cache(recipeManager, level, itemStack, type);

			if (cache != null)
			{
				return cache;
			}

		}

		return null;
	}

	public static ItemStackToItemStackRecipe cache(RecipeManager recipeManager, Level level, ItemStack itemStack, RecipeType<? extends Recipe<Container>> type)
	{
		IS2ISRecipeType<RecipeType<? extends Recipe<Container>>, Recipe<Container>> findCacheType = findCacheType(type);
		return cache(recipeManager, level, itemStack, type, findCacheType);
	}

	public static <T extends RecipeType<? extends R>, R extends Recipe<Container>> ItemStackToItemStackRecipe cache(RecipeManager recipeManager, Level level, ItemStack itemStack, T recipeType, IS2ISRecipeType<T, R> cacheType)
	{
		R recipe = cacheType.find(recipeManager, level, recipeType, itemStack);

		if (recipe != null)
		{
			return caches.computeIfAbsent(recipe, r -> cacheType.createCache(recipe));
		}
		else
		{
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public static <T extends RecipeType<? extends R>, R extends Recipe<Container>> IS2ISRecipeType<T, R> findCacheType(T recipeType)
	{
		for (IS2ISRecipeType<? extends RecipeType<? extends Recipe<Container>>, ? extends Recipe<Container>> cacheType : types)
		{
			if (cacheType.testRecipeType(recipeType) == true)
			{
				return (IS2ISRecipeType<T, R>) cacheType;
			}

		}

		return null;
	}

	private IS2ISRecipeCache()
	{

	}

}
