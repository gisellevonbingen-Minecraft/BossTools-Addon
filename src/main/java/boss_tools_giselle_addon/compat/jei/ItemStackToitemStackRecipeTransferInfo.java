package boss_tools_giselle_addon.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.inventory.container.ItemStackToItemStackContainer;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;

public class ItemStackToitemStackRecipeTransferInfo<C extends ItemStackToItemStackContainer<C, ?>> implements IRecipeTransferInfo<C>
{
	private final Class<C> clazz;
	private final ResourceLocation uid;

	public ItemStackToitemStackRecipeTransferInfo(Class<C> clazz, ResourceLocation uid)
	{
		this.clazz = clazz;
		this.uid = uid;
	}

	@Override
	public Class<C> getContainerClass()
	{
		return this.clazz;
	}

	@Override
	public ResourceLocation getRecipeCategoryUid()
	{
		return this.uid;
	}

	@Override
	public boolean canHandle(C container)
	{
		return true;
	}

	@Override
	public List<Slot> getRecipeSlots(C container)
	{
		return Collections.singletonList(container.getInputSlot());
	}

	@Override
	public List<Slot> getInventorySlots(C container)
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
	public boolean requireCompleteSets()
	{
		return false;
	}

}
