package boss_tools_giselle_addon.common.mixin.boss_tools;

import java.util.List;
import java.util.Map.Entry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipe;
import net.mrscauthd.boss_tools.inventory.RocketPartsItemHandler;

@Mixin(WorkbenchingRecipe.class)
public abstract class WorkbenchingRecipeMixin
{
	@Inject(at = @At(value = "HEAD"), method = "test(Lnet/mrscauthd/boss_tools/inventory/RocketPartsItemHandler;Ljava/lang/Boolean;)Z", cancellable = true, remap = false)
	public void test(RocketPartsItemHandler itemHandler, Boolean ignoreAir, CallbackInfoReturnable<Boolean> info)
	{
		info.cancel();
		WorkbenchingRecipe recipe = (WorkbenchingRecipe) ((Object) this);

		for (Entry<RocketPart, List<Ingredient>> entry : recipe.getParts().entrySet())
		{
			RocketPart part = entry.getKey();
			IItemHandlerModifiable subHandler = itemHandler.getSubHandlers().get(part);

			if (subHandler == null)
			{
				info.setReturnValue(false);
				return;
			}

			int subHandlerSlots = subHandler.getSlots();
			List<Ingredient> ingredients = entry.getValue();

			if (subHandlerSlots < ingredients.size())
			{
				info.setReturnValue(false);
				return;
			}

			for (int i = 0; i < subHandlerSlots; i++)
			{
				if (i >= ingredients.size())
				{
					continue;
				}

				ItemStack stack = subHandler.getStackInSlot(i);
				Ingredient ingredient = ingredients.get(i);

				if (ignoreAir && stack.isEmpty())
				{
					continue;
				}
				else if (!ingredient.test(stack))
				{
					info.setReturnValue(false);
					return;
				}

			}

		}

		info.setReturnValue(true);
	}

}
