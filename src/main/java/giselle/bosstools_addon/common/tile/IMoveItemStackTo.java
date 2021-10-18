package giselle.bosstools_addon.common.tile;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IMoveItemStackTo
{
	boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection);
}
