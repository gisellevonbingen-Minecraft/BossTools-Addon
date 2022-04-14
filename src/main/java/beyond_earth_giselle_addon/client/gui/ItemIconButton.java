package beyond_earth_giselle_addon.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.client.util.ItemStackRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class ItemIconButton extends Button
{
	public static final int CHECKER_WIDTH = 20;
	public static final int CHECKER_HEIGHT = 20;

	private ItemStack itemStack;
	private int textColor = 0x404040;
	private boolean isRenderButton;

	public ItemIconButton(int x, int y, ItemStack icon, OnPress pressListener, OnTooltip tooltipListener)
	{
		super(x, y, CHECKER_WIDTH, CHECKER_HEIGHT, TextComponent.EMPTY, pressListener, tooltipListener);
		this.setItemStack(icon);
	}

	public ItemIconButton(int x, int y, ItemStack icon, OnPress pressListener)
	{
		super(x, y, CHECKER_WIDTH, CHECKER_HEIGHT, TextComponent.EMPTY, pressListener);
		this.setItemStack(icon);
	}

	@Override
	public void renderButton(PoseStack stack, int mouseX, int mouseY, float p_230431_4_)
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

		Component message = this.getMessage();
		Minecraft minecraft = Minecraft.getInstance();
		Font font = minecraft.font;
		int messageWidth = font.width(message);
		int hpadding = 2;
		int vpadding = 2;
		int messageX = this.x + CHECKER_WIDTH + hpadding;
		int messageY = this.y + (CHECKER_HEIGHT - font.lineHeight) / 2 + vpadding;
		font.draw(stack, message, messageX, messageY, this.getTextColor() | Mth.ceil(this.alpha * 255.0F) << 24);
		this.width = messageX + messageWidth - this.x;
		this.height = CHECKER_HEIGHT;
	}

	@Override
	public Component getMessage()
	{
		if (this.isRenderButton == true)
		{
			return TextComponent.EMPTY;
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
