package boss_tools_giselle_addon.common.tile;

import java.lang.reflect.Field;

import boss_tools_giselle_addon.util.ReflectionUtils;
import net.mrscauthd.boss_tools.inventory.StackCacher;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;

public class ItemStackToItemStackTileEntityUtils
{
	private static final Field itemStackCacherField;

	static
	{
		itemStackCacherField = ReflectionUtils.getDeclaredAccessibleField(ItemStackToItemStackTileEntity.class, "itemStackCacher");
	}

	public static StackCacher getItemStackCache(ItemStackToItemStackTileEntity tileEntity)
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
