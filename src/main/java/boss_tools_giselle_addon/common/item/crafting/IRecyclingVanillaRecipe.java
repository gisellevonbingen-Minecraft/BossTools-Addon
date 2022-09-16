package boss_tools_giselle_addon.common.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public interface IRecyclingVanillaRecipe<P extends IRecipe<C>, C extends IInventory> extends IRecipe<C>
{
	@Override
	public default ItemStack assemble(IInventory container)
	{
		return this.getOutput().getItemStack().copy();
	}

	@Override
	public default ItemStack getResultItem()
	{
		return this.getOutput().getItemStack().copy();
	}

	public default ItemStack getToastSymbol()
	{
		return this.getParent().getToastSymbol();
	}

	public P getParent();

	public ItemOutput getOutput();
}
