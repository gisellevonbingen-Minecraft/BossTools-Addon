package astrocraft_giselle_addon.common.compat.curios;

import astrocraft_giselle_addon.common.inventory.ItemHandlerHelper2;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosHelper
{
	public static NonNullList<ItemStack> getEquippedCurios(LivingEntity entity)
	{
		IItemHandlerModifiable handler = CuriosApi.getCuriosHelper().getEquippedCurios(entity).orElse(null);
		return ItemHandlerHelper2.getStacks(handler);
	}

	private CuriosHelper()
	{

	}

}
