package beyond_earth_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.common.gui.TooltipRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class RecipeTransferErrorTooltip2 implements IRecipeTransferError
{
	private final List<Component> message = new ArrayList<>();

	public RecipeTransferErrorTooltip2(List<Component> message)
	{
		this.message.add(Component.translatable("jei.tooltip.transfer"));

		for (Component line : message)
		{
			this.message.add(line.copy().withStyle(ChatFormatting.RED));
		}

	}

	@Override
	public Type getType()
	{
		return Type.USER_FACING;
	}

	@Override
	public void showError(PoseStack stack, int mouseX, int mouseY, IRecipeSlotsView recipeSlotsView, int recipeX, int recipeY)
	{
		TooltipRenderer.drawHoveringText(stack, this.message, mouseX, mouseY);
	}

}
