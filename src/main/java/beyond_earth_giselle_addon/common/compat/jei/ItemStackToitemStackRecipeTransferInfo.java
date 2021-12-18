package beyond_earth_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beyond_earth_giselle_addon.common.inventory.ItemStackToItemStackContainerMenu;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipe;

public class ItemStackToitemStackRecipeTransferInfo<C extends ItemStackToItemStackContainerMenu<C, ?>> implements IRecipeTransferInfo<C, ItemStackToItemStackRecipe>
{
	private final Class<C> containerClass;
	private final Class<ItemStackToItemStackRecipe> recipeClass;
	private final ResourceLocation uid;

	public ItemStackToitemStackRecipeTransferInfo(Class<C> containerClass, Class<ItemStackToItemStackRecipe> recipeClass, ResourceLocation uid)
	{
		this.containerClass = containerClass;
		this.recipeClass = recipeClass;
		this.uid = uid;
	}

	@Override
	public Class<C> getContainerClass()
	{
		return this.containerClass;
	}

	@Override
	public Class<ItemStackToItemStackRecipe> getRecipeClass()
	{
		return this.recipeClass;
	}

	@Override
	public ResourceLocation getRecipeCategoryUid()
	{
		return this.uid;
	}

	@Override
	public boolean canHandle(C container, ItemStackToItemStackRecipe recipe)
	{
		return true;
	}

	@Override
	public List<Slot> getRecipeSlots(C container, ItemStackToItemStackRecipe recipe)
	{
		return Collections.singletonList(container.getInputSlot());
	}

	@Override
	public List<Slot> getInventorySlots(C container, ItemStackToItemStackRecipe recipe)
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
	public boolean requireCompleteSets(C container, ItemStackToItemStackRecipe recipe)
	{
		return false;
	}

}
