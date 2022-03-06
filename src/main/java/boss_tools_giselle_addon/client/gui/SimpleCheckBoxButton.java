package boss_tools_giselle_addon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class SimpleCheckBoxButton extends AbstractButton
{
	public static final ResourceLocation TEXTURE_NORMAL_CHECKED = BossToolsAddon.rl("textures/gui/checkbox_normal_true.png");
	public static final ResourceLocation TEXTURE_NORMAL_UNCHECKED = BossToolsAddon.rl("textures/gui/checkbox_normal_false.png");
	public static final ResourceLocation TEXTURE_HOVER_CHECKED = BossToolsAddon.rl("textures/gui/checkbox_hover_true.png");
	public static final ResourceLocation TEXTURE_HOVER_UNCHECKED = BossToolsAddon.rl("textures/gui/checkbox_hover_false.png");
	public static final int CHECKER_WIDTH = 10;
	public static final int CHECKER_HEIGHT = 10;

	private boolean checked;
	private boolean autoToggle;

	private boolean textVisible;
	private int textColor;

	public SimpleCheckBoxButton(int x, int y, ITextComponent text)
	{
		this(x, y, false, text);
	}

	public SimpleCheckBoxButton(int x, int y, boolean checked)
	{
		this(x, y, checked, StringTextComponent.EMPTY, false);
	}

	public SimpleCheckBoxButton(int x, int y, boolean checked, ITextComponent message)
	{
		this(x, y, checked, message, true);
	}

	public SimpleCheckBoxButton(int x, int y, boolean checked, ITextComponent message, boolean showText)
	{
		super(x, y, CHECKER_WIDTH, CHECKER_HEIGHT, message);
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
		if (this.isHovered() == true)
		{
			return this.isChecked() ? TEXTURE_HOVER_CHECKED : TEXTURE_HOVER_UNCHECKED;
		}
		else
		{
			return this.isChecked() ? TEXTURE_NORMAL_CHECKED : TEXTURE_NORMAL_UNCHECKED;
		}

	}

	@SuppressWarnings("deprecation")
	public void renderButton(MatrixStack stack, int mouseX, int mouseY, float p_230431_4_)
	{
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bind(this.getTexture());
		RenderSystem.enableDepthTest();
		FontRenderer font = minecraft.font;
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		blit(stack, this.x, this.y, 0, 0, CHECKER_WIDTH, CHECKER_HEIGHT, CHECKER_WIDTH, CHECKER_HEIGHT);
		this.renderBg(stack, minecraft, mouseX, mouseY);

		if (this.isTextVisible() == true)
		{
			ITextComponent message = this.getMessage();
			int messageWidth = font.width(message);
			int hpadding = 2;
			int vpadding = 2;
			int messageX = this.x + CHECKER_WIDTH + hpadding;
			int messageY = this.y + (CHECKER_HEIGHT - font.lineHeight) / 2 + vpadding;
			font.draw(stack, message, messageX, messageY, this.getTextColor() | MathHelper.ceil(this.alpha * 255.0F) << 24);
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

}
