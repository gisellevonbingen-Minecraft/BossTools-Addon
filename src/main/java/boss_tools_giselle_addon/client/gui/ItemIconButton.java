package boss_tools_giselle_addon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
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

		RenderSystem.pushMatrix();
		RenderSystem.multMatrix(matrix.last().pose());
		RenderSystem.enableDepthTest();
		RenderHelper.turnBackOn();

		Minecraft minecraft = Minecraft.getInstance();
		ItemRenderer itemRenderer = minecraft.getItemRenderer();
		ItemStack itemStack = this.getItemStack();
		int x = this.x + (this.width - 16) / 2;
		int y = this.y + (this.height - 16) / 2;
		itemRenderer.renderAndDecorateItem(null, itemStack, x, y);
		itemRenderer.renderGuiItemDecorations(minecraft.font, itemStack, x, y, null);

		RenderSystem.disableBlend();
		RenderHelper.turnOff();
		RenderSystem.popMatrix();
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
