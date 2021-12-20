package boss_tools_giselle_addon.common.tile;

import java.lang.reflect.Field;

import boss_tools_giselle_addon.common.util.ReflectionUtils;
import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;
import net.mrscauthd.boss_tools.inventory.StackCacher;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;

public class ItemStackToItemStackTileEntityUtils
{
	private static final Field cachedRecipeField;
	private static final Field itemStackCacherField;

	static
	{
		Class<ItemStackToItemStackTileEntity> klass = ItemStackToItemStackTileEntity.class;
		cachedRecipeField = ReflectionUtils.getDeclaredAccessibleField(klass, "cachedRecipe");
		itemStackCacherField = ReflectionUtils.getDeclaredAccessibleField(klass, "itemStackCacher");
	}

	public static ItemStackToItemStackRecipe getCachedRecipe(ItemStackToItemStackTileEntity tileEntity)
	{
		try
		{
			return (ItemStackToItemStackRecipe) cachedRecipeField.get(tileEntity);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	public static void setCachedRecipe(ItemStackToItemStackTileEntity tileEntity, ItemStack ingredient, ItemStackToItemStackRecipe recipe)
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
			getItemStackCacher(tileEntity).set(ingredient);
			cachedRecipeField.set(tileEntity, recipe);

			if (empty == false && recipe != null)
			{
				tileEntity.setMaxTimer(recipe.getCookTime());
			}
			else
			{
				tileEntity.setMaxTimer(0);
			}

		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}

	}

	public static StackCacher getItemStackCacher(ItemStackToItemStackTileEntity tileEntity)
	{
		try
		{
			return (StackCacher) itemStackCacherField.get(tileEntity);
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
