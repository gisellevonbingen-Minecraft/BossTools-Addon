package giselle.boss_tools_addon.common.inventory.container;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IMoveItemStackTo
{
	boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection);
}
