package boss_tools_giselle_addon.common.item.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;

public class IS2ISRecipeCache
{
	private static final List<IS2ISRecipeType<? extends IRecipeType<? extends IRecipe<IInventory>>, ? extends IRecipe<IInventory>>> types;
	private static final Map<IRecipe<IInventory>, ItemStackToItemStackRecipe> caches;

	static
	{
		types = new ArrayList<>();
		addType(new IS2ISRecipeTypeBossTools());
		addType(new IS2ISRecipeTypeMinecraftBlasting());

		caches = new HashMap<>();
	}

	public static void clearCaches()
	{
		caches.clear();
	}

	public static List<IS2ISRecipeType<? extends IRecipeType<? extends IRecipe<IInventory>>, ? extends IRecipe<IInventory>>> getTypes()
	{
		return Collections.unmodifiableList(types);
	}

	public static <T extends IRecipeType<? extends R>, R extends IRecipe<IInventory>> void addType(IS2ISRecipeType<T, R> type)
	{
		types.add(type);
	}

	public static ItemStackToItemStackRecipe cache(RecipeManager recipeManager, World world, ItemStack itemStack, List<IRecipeType<? extends IRecipe<IInventory>>> types)
	{
		for (IRecipeType<? extends IRecipe<IInventory>> type : types)
		{
			ItemStackToItemStackRecipe cache = cache(recipeManager, world, itemStack, type);

			if (cache != null)
			{
				return cache;
			}

		}

		return null;
	}

	public static ItemStackToItemStackRecipe cache(RecipeManager recipeManager, World world, ItemStack itemStack, IRecipeType<? extends IRecipe<IInventory>> type)
	{
		IS2ISRecipeType<IRecipeType<? extends IRecipe<IInventory>>, IRecipe<IInventory>> findCacheType = findCacheType(type);
		return cache(recipeManager, world, itemStack, type, findCacheType);
	}

	public static <T extends IRecipeType<? extends R>, R extends IRecipe<IInventory>> ItemStackToItemStackRecipe cache(RecipeManager recipeManager, World world, ItemStack itemStack, T recipeType, IS2ISRecipeType<T, R> cacheType)
	{
		R recipe = cacheType.find(recipeManager, world, recipeType, itemStack);

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
	public static <T extends IRecipeType<? extends R>, R extends IRecipe<IInventory>> IS2ISRecipeType<T, R> findCacheType(T recipeType)
	{
		for (IS2ISRecipeType<? extends IRecipeType<? extends IRecipe<IInventory>>, ? extends IRecipe<IInventory>> cacheType : types)
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
