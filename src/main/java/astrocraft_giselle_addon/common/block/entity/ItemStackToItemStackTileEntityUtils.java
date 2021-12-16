package astrocraft_giselle_addon.common.block.entity;

import java.lang.reflect.Field;

import astrocraft_giselle_addon.common.util.ReflectionUtils;
import net.mrscauthd.astrocraft.inventory.StackCacher;
import net.mrscauthd.astrocraft.machines.tile.ItemStackToItemStackBlockEntity;

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
