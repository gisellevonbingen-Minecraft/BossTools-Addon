package boss_tools_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.gui.TooltipRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class RecipeTransferErrorTooltip2 implements IRecipeTransferError
{
	private final List<ITextComponent> message = new ArrayList<>();

	public RecipeTransferErrorTooltip2(List<ITextComponent> message)
	{
		this.message.add(new TranslationTextComponent("jei.tooltip.transfer"));

		for (ITextComponent line : message)
		{
			this.message.add(line.copy().withStyle(TextFormatting.RED));
		}

	}

	@Override
	public Type getType()
	{
		return Type.USER_FACING;
	}

	@Override
	public void showError(MatrixStack stack, int mouseX, int mouseY, IRecipeLayout recipeLayout, int recipeX, int recipeY)
	{
		TooltipRenderer.drawHoveringText(this.message, mouseX, mouseY, stack);
	}

}
