package boss_tools_giselle_addon.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.inventory.container.ElectricBlastFurnaceContainer;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.jei.JeiPlugin.BlastingFurnaceJeiCategory;

public class ElectricBlastFurnaceRecipeTransferInfo implements IRecipeTransferInfo<ElectricBlastFurnaceContainer>
{
	@Override
	public Class<ElectricBlastFurnaceContainer> getContainerClass()
	{
		return ElectricBlastFurnaceContainer.class;
	}

	@Override
	public ResourceLocation getRecipeCategoryUid()
	{
		return BlastingFurnaceJeiCategory.Uid;
	}

	@Override
	public boolean canHandle(ElectricBlastFurnaceContainer container)
	{
		return true;
	}

	@Override
	public List<Slot> getRecipeSlots(ElectricBlastFurnaceContainer container)
	{
		return Collections.singletonList(container.getInputSlot());
	}

	@Override
	public List<Slot> getInventorySlots(ElectricBlastFurnaceContainer container)
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
