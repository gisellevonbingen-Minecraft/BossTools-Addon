package boss_tools_giselle_addon.common.block.entity;

import java.lang.reflect.Field;

import boss_tools_giselle_addon.common.util.ReflectionUtils;
import net.mrscauthd.boss_tools.inventory.StackCacher;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackBlockEntity;

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
