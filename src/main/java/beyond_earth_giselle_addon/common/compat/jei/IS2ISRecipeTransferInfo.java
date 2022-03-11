package beyond_earth_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beyond_earth_giselle_addon.common.inventory.ItemStackToItemStackContainerMenu;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

public class IS2ISRecipeTransferInfo<C extends ItemStackToItemStackContainerMenu<C, ?>, R> implements IRecipeTransferInfo<C, R>
{
	private final Class<C> containerClass;
	private final RecipeType<R> recipeType;

	public IS2ISRecipeTransferInfo(Class<C> containerClass, RecipeType<R> recipeType)
	{
		this.containerClass = containerClass;
		this.recipeType = recipeType;
	}

	@Override
	public Class<C> getContainerClass()
	{
		return this.containerClass;
	}

	public RecipeType<R> getRecipeType()
	{
		return this.recipeType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<R> getRecipeClass()
	{
		return (Class<R>) this.getRecipeType().getRecipeClass();
	}

	@Override
	public ResourceLocation getRecipeCategoryUid()
	{
		return this.getRecipeType().getUid();
	}

	@Override
	public boolean canHandle(C container, R recipe)
	{
		return true;
	}

	@Override
	public List<Slot> getRecipeSlots(C container, R recipe)
	{
		return Collections.singletonList(container.getInputSlot());
	}

	@Override
	public List<Slot> getInventorySlots(C container, R recipe)
	{
		List<Slot> slots = new ArrayList<>();
		int inventorySlotStart = container.getHandlerEndIndex();
		int inventorySlotEnd = container.slots.size();

		for (int i = inventorySlotStart; i < inventorySlotEnd; i++)
		{
			slots.add(container.getSlot(i));
		}

		return slots;
	}

	@Override
	public boolean requireCompleteSets(C container, R recipe)
	{
		return false;
	}

}
