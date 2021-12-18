package beyond_earth_giselle_addon.common.block.entity;

import java.lang.reflect.Field;

import beyond_earth_giselle_addon.common.util.ReflectionUtils;
import net.mrscauthd.beyond_earth.inventory.StackCacher;
import net.mrscauthd.beyond_earth.machines.tile.ItemStackToItemStackBlockEntity;

public class ItemStackToItemStackTileEntityUtils
{
	private static final Field itemStackCacherField;

	static
	{
		itemStackCacherField = ReflectionUtils.getDeclaredAccessibleField(ItemStackToItemStackBlockEntity.class, "itemStackCacher");
	}

	public static StackCacher getItemStackCache(ItemStackToItemStackBlockEntity blockEntity)
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
