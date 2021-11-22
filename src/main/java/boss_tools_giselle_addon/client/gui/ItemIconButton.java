package boss_tools_giselle_addon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.client.ItemStackRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

public class ItemIconButton extends Button
{
	private ItemStack itemStack;

	public ItemIconButton(int p_i232256_1_, int p_i232256_2_, int p_i232256_3_, int p_i232256_4_, ItemStack p_i232256_5_, IPressable p_i232256_6_, ITooltip p_i232256_7_)
	{
		super(p_i232256_1_, p_i232256_2_, p_i232256_3_, p_i232256_4_, new StringTextComponent(""), p_i232256_6_, p_i232256_7_);
		this.setItemStack(p_i232256_5_);
	}

	public ItemIconButton(int p_i232255_1_, int p_i232255_2_, int p_i232255_3_, int p_i232255_4_, ItemStack p_i232255_5_, IPressable p_i232255_6_)
	{
		super(p_i232255_1_, p_i232255_2_, p_i232255_3_, p_i232255_4_, new StringTextComponent(""), p_i232255_6_);
		this.setItemStack(p_i232255_5_);
	}

	@Override
	public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float p_230431_4_)
	{
		super.renderButton(matrix, mouseX, mouseY, p_230431_4_);
		ItemStackRenderer.render(itemStack, matrix, x, y);
	}

	public ItemStack getItemStack()
	{
		return this.itemStack;
	}

	public void setItemStack(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}

}
