package beyond_earth_giselle_addon.common.compat.curios;

import java.util.Collections;
import java.util.List;

import beyond_earth_giselle_addon.common.inventory.ItemHandlerHelper3;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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
