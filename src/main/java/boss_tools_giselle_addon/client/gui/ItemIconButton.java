package boss_tools_giselle_addon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.client.util.ItemStackRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ItemIconButton extends Button
{
	public static final int CHECKER_WIDTH = 20;
	public static final int CHECKER_HEIGHT = 20;

	private ItemStack itemStack;
	private int textColor = 0x404040;
	private boolean isRenderButton;

	public ItemIconButton(int x, int y, ItemStack icon, IPressable pressListener, ITooltip tooltipListener)
	{
		super(x, y, CHECKER_WIDTH, CHECKER_HEIGHT, StringTextComponent.EMPTY, pressListener, tooltipListener);
		this.setItemStack(icon);
	}

	public ItemIconButton(int x, int y, ItemStack icon, IPressable pressListener)
	{
		super(x, y, CHECKER_WIDTH, CHECKER_HEIGHT, StringTextComponent.EMPTY, pressListener);
		this.setItemStack(icon);
	}

	@Override
	public void renderButton(MatrixStack stack, int mouseX, int mouseY, float p_230431_4_)
	{
		boolean prev = this.isRenderButton;

		try
		{
			this.isRenderButton = true;
			this.width = CHECKER_WIDTH;
			super.renderButton(stack, mouseX, mouseY, p_230431_4_);
		}
		finally
		{
			this.isRenderButton = prev;
		}

		int iconWidth = 16;
		int iconHeight = 16;
		int iconX = this.x + (CHECKER_WIDTH - iconWidth) / 2;
		int itemY = this.y + (this.getHeight() - iconHeight) / 2;
		ItemStackRenderer.render(this.getItemStack(), iconX, itemY);

		ITextComponent message = this.getMessage();
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer font = minecraft.font;
		int messageWidth = font.width(message);
		int hpadding = 2;
		int vpadding = 2;
		int messageX = this.x + CHECKER_WIDTH + hpadding;
		int messageY = this.y + (CHECKER_HEIGHT - font.lineHeight) / 2 + vpadding;
		font.draw(stack, message, messageX, messageY, this.getTextColor() | MathHelper.ceil(this.alpha * 255.0F) << 24);
		this.width = messageX + messageWidth - this.x;
		this.height = CHECKER_HEIGHT;
	}

	@Override
	public ITextComponent getMessage()
	{
		if (this.isRenderButton == true)
		{
			return StringTextComponent.EMPTY;
		}
		else
		{
			return super.getMessage();
		}

	}

	public ItemStack getItemStack()
	{
		return this.itemStack;
	}

	public void setItemStack(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}

	public int getTextColor()
	{
		return this.textColor;
	}

	public void setTextColor(int textColor)
	{
		this.textColor = textColor;
	}

}
