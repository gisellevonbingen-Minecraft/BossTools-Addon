package boss_tools_giselle_addon.util;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class AnalogOutputSignalUtils
{
	public static int getAnalogOutputSignal(@Nullable IItemHandler inv)
	{
		return ItemHandlerHelper.calcRedstoneFromInventory(inv);
	}

	public static int getAnalogOutputSignal(@Nullable ItemStack itemStack)
	{
		if (itemStack == null || itemStack.isEmpty() == true)
		{
			return 0;
		}
		else
		{
			float proportion = (float) itemStack.getCount() / itemStack.getMaxStackSize();
			return 1 + MathHelper.floor(14.0F * proportion);
		}

	}

	private AnalogOutputSignalUtils()
	{

	}

}
