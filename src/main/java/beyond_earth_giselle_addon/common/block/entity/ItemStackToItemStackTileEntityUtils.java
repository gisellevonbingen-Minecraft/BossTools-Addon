package beyond_earth_giselle_addon.common.block.entity;

import java.lang.reflect.Field;

import beyond_earth_giselle_addon.common.util.ReflectionUtils;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipe;
import net.mrscauthd.beyond_earth.inventory.StackCacher;
import net.mrscauthd.beyond_earth.machines.tile.ItemStackToItemStackBlockEntity;

public class ItemStackToItemStackTileEntityUtils
{
	private static final Field cachedRecipeField;
	private static final Field itemStackCacherField;

	static
	{
		Class<ItemStackToItemStackBlockEntity> klass = ItemStackToItemStackBlockEntity.class;
		cachedRecipeField = ReflectionUtils.getDeclaredAccessibleField(klass, "cachedRecipe");
		itemStackCacherField = ReflectionUtils.getDeclaredAccessibleField(klass, "itemStackCacher");
	}

	public static ItemStackToItemStackRecipe getCachedRecipe(ItemStackToItemStackBlockEntity blockEntity)
	{
		try
		{
			return (ItemStackToItemStackRecipe) cachedRecipeField.get(blockEntity);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	public static void setCachedRecipe(ItemStackToItemStackBlockEntity blockEntity, ItemStack ingredient, ItemStackToItemStackRecipe recipe)
	{
		boolean empty = ingredient == null || ingredient.isEmpty() == true;

		if (recipe != null && empty == false)
		{
			if (recipe.test(ingredient) == false)
			{
				throw new IllegalArgumentException("ingredient");
			}

		}

		try
		{
			getItemStackCacher(blockEntity).set(ingredient);
			cachedRecipeField.set(blockEntity, recipe);

			if (empty == false && recipe != null)
			{
				blockEntity.setMaxTimer(recipe.getCookTime());
			}
			else
			{
				blockEntity.setMaxTimer(0);
			}

		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}

	}

	public static StackCacher getItemStackCacher(ItemStackToItemStackBlockEntity blockEntity)
	{
		try
		{
			return (StackCacher) itemStackCacherField.get(blockEntity);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	private ItemStackToItemStackTileEntityUtils()
	{

	}

}
