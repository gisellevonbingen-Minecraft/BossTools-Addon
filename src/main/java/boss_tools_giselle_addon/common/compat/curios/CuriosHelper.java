package boss_tools_giselle_addon.common.compat.curios;

import boss_tools_giselle_addon.common.inventory.ItemHandlerHelper3;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosHelper
{
	public static NonNullList<ItemStack> getEquippedCurios(LivingEntity entity)
	{
		IItemHandlerModifiable handler = CuriosApi.getCuriosHelper().getEquippedCurios(entity).orElse(null);
		return ItemHandlerHelper3.getStacks(handler);
	}

	private CuriosHelper()
	{

	}

}
