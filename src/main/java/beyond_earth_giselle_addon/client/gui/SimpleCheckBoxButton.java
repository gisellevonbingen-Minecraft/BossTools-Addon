package beyond_earth_giselle_addon.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SimpleCheckBoxButton extends AbstractButton
{
	public static final ResourceLocation TEXTURE_NORMAL_CHECKED = BeyondEarthAddon.rl("textures/gui/checkbox_normal_true.png");
	public static final ResourceLocation TEXTURE_NORMAL_UNCHECKED = BeyondEarthAddon.rl("textures/gui/checkbox_normal_false.png");
	public static final ResourceLocation TEXTURE_HOVER_CHECKED = BeyondEarthAddon.rl("textures/gui/checkbox_hover_true.png");
	public static final ResourceLocation TEXTURE_HOVER_UNCHECKED = BeyondEarthAddon.rl("textures/gui/checkbox_hover_false.png");
	public static final int CHECKER_WIDTH = 10;
	public static final int CHECKER_HEIGHT = 10;

	private boolean checked;
	private boolean autoToggle;

	private boolean textVisible;
	private int textColor;

	public SimpleCheckBoxButton(int x, int y, Component text)
	{
		this(x, y, text, false);
	}

	public SimpleCheckBoxButton(int x, int y, Component text, boolean checked)
	{
		this(x, y, text, checked, true);
	}

	public SimpleCheckBoxButton(int x, int y, Component text, boolean checked, boolean showText)
	{
		super(x, y, CHECKER_WIDTH, CHECKER_HEIGHT, text);
		this.checked = checked;
		this.autoToggle = true;

		this.textVisible = showText;
		this.textColor = 0x404040;
	}

	public void onPress()
	{
		if (this.isAutoToggle() == true)
		{
			this.setChecked(this.isChecked() ^ true);
		}

	}

	public ResourceLocation getTexture()
	{
		if (this.isHoveredOrFocused() == true)
		{
			return this.isChecked() ? TEXTURE_HOVER_CHECKED : TEXTURE_HOVER_UNCHECKED;
		}
		else
		{
			return this.isChecked() ? TEXTURE_NORMAL_CHECKED : TEXTURE_NORMAL_UNCHECKED;
		}

	}

	public void renderButton(PoseStack stack, int mouseX, int mouseY, float p_230431_4_)
	{
		Minecraft minecraft = Minecraft.getInstance();
		RenderSystem.setShaderTexture(0, this.getTexture());
		RenderSystem.enableDepthTest();
		Font font = minecraft.font;
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		blit(stack, this.x, this.y, 0, 0, CHECKER_WIDTH, CHECKER_HEIGHT, CHECKER_WIDTH, CHECKER_HEIGHT);
		this.renderBg(stack, minecraft, mouseX, mouseY);

		if (this.isTextVisible() == true)
		{
			Component message = this.getMessage();
			int messageWidth = font.width(message);
			int hpadding = 2;
			int vpadding = 2;
			int messageX = this.x + CHECKER_WIDTH + hpadding;
			int messageY = this.y + (CHECKER_HEIGHT - font.lineHeight) / 2 + vpadding;
			font.draw(stack, message, messageX, messageY, this.getTextColor() | Mth.ceil(this.alpha * 255.0F) << 24);
			this.width = messageX + messageWidth - this.x;
		}
		else
		{
			this.width = CHECKER_WIDTH;
		}

		this.height = CHECKER_HEIGHT;
	}

	public boolean isChecked()
	{
		return this.checked;
	}

	public void setChecked(boolean checked)
	{
		if (this.isChecked() != checked)
		{
			this.checked = checked;
			this.onCheckedChanged(checked);
		}

	}

	protected void onCheckedChanged(boolean checked)
	{

	}

	public boolean isAutoToggle()
	{
		return this.autoToggle;
	}

	public void setAutoToggle(boolean autoToggle)
	{
		this.autoToggle = autoToggle;
	}

	public boolean isTextVisible()
	{
		return this.textVisible;
	}

	public void setTextVisible(boolean textVisible)
	{
		this.textVisible = textVisible;
	}

	public int getTextColor()
	{
		return this.textColor;
	}

	public void setTextColor(int textColor)
	{
		this.textColor = textColor;
	}

	@Override
	public void updateNarration(NarrationElementOutput output)
	{
		output.add(NarratedElementType.TITLE, this.createNarrationMessage());

		if (this.active == true)
		{
			if (this.isFocused() == true)
			{
				output.add(NarratedElementType.USAGE, new TranslatableComponent("narration.checkbox.usage.focused"));
			}
			else
			{
				output.add(NarratedElementType.USAGE, new TranslatableComponent("narration.checkbox.usage.hovered"));
			}

		}

	}

}
