package boss_tools_giselle_addon.common.compat.curios;

import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.inventory.ItemHandlerHelper3;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosHelper
{
	public static List<ItemStack> getEquippedCurios(LivingEntity entity)
	{
		IItemHandlerModifiable handler = CuriosApi.getCuriosHelper().getEquippedCurios(entity).orElse(null);
		return handler != null ? ItemHandlerHelper3.getStacks(handler) : Collections.emptyList();
	}

	private CuriosHelper()
	{

	}

}
