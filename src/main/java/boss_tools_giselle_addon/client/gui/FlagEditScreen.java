package boss_tools_giselle_addon.client.gui;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import boss_tools_giselle_addon.client.SkullRenderUtils;
import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.network.FlagEditMessageSave;
import boss_tools_giselle_addon.common.util.GameProfileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;

public class FlagEditScreen extends Screen
{
	public static final ResourceLocation BACKGROUND = texture("back");
	public static final ITextComponent TITLE = language("title");
	public static final ITextComponent VERIFY = language("verify");
	public static final ITextComponent VERIFYING = language("verifying");
	public static final ITextComponent SAVE = language("save");
	public static final ITextComponent WARNING = language("warning");

	public static ResourceLocation texture(String name)
	{
		return BossToolsAddon.rl("textures/gui/flag_edit_" + name + ".png");
	}

	public static TranslationTextComponent language(String key, Object... objects)
	{
		return new TranslationTextComponent(BossToolsAddon.tl("gui", "flag_edit." + key), objects);
	}

	private final FlagTileEntity tileEntity;

	protected int imageWidth;
	protected int imageHeight;
	protected int leftPos;
	protected int topPos;

	protected TextFieldWidget valueTextField;
	protected Button verityButton;
	protected Button saveButton;

	private String lastRequestText;
	private Thread requestThread;
	private GameProfile gameProfile;
	private int verifyCounter;
	private boolean changed;

	public FlagEditScreen(FlagTileEntity tileEntity)
	{
		this(tileEntity, language("title"));
	}

	public FlagEditScreen(FlagTileEntity tileEntity, ITextComponent title)
	{
		super(title);
		this.tileEntity = tileEntity;
		GameProfile gameProfile = tileEntity.getPlayerProfile();

		this.imageWidth = 210;
		this.imageHeight = 138;

		this.lastRequestText = gameProfile != null ? gameProfile.getName() : "";
		this.requestThread = null;
		this.setGameProfile(gameProfile);
		this.verifyCounter = 0;
		this.setChanged(false);
	}

	@Override
	protected void init()
	{
		super.init();

		int imageWidth = this.getImageWidth();
		int imageHeight = this.getImageHeight();
		this.leftPos = (this.width - imageWidth) / 2;
		this.topPos = (this.height - imageHeight) / 2;

		int buttonWidth = 40;
		int buttonHeight = 20;
		this.verityButton = this.addButton(new Button(this.leftPos + imageWidth - buttonWidth - 10, this.topPos + 25, buttonWidth, buttonHeight, VERIFY, this::performClick));
		this.saveButton = this.addButton(new Button(this.verityButton.x, this.topPos + imageHeight - buttonHeight - this.font.lineHeight - 10, this.verityButton.getWidth(), this.verityButton.getHeight(), SAVE, this::performClick));

		TextFieldWidget prevValueTestFie = this.getValueTextField();
		int valueTextLeft = this.leftPos + 10;
		this.valueTextField = this.addButton(new TextFieldWidget(this.font, valueTextLeft, this.topPos + 25, this.verityButton.x - 5 - valueTextLeft, 20, new StringTextComponent("")));
		this.valueTextField.setMaxLength(65535);

		if (prevValueTestFie != null)
		{
			this.valueTextField.setValue(prevValueTestFie.getValue());
		}
		else
		{
			this.valueTextField.setValue(this.lastRequestText);
		}

	}

	@Override
	public void tick()
	{
		super.tick();

		this.verifyCounter = (this.verifyCounter + 1) % 6;
	}

	@Override
	public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_)
	{
		if (p_231046_1_ == GLFW.GLFW_KEY_ENTER || p_231046_1_ == GLFW.GLFW_KEY_KP_ENTER)
		{
			if (this.isDirty() == true)
			{
				this.performClick(this.getVerityButton());
			}
			else if (this.isChanged() == true)
			{
				this.performClick(this.getSaveButton());
			}

		}

		return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
	}

	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBg(stack, mouseX, mouseY, partialTicks);
		super.render(stack, mouseX, mouseY, partialTicks);
		this.renderSkull(stack, mouseX, mouseY, partialTicks);

		int x = this.getLeftPos() + 10;
		Button saveButton = this.getSaveButton();

		this.font.draw(stack, TITLE, x, this.getTopPos() + 10, 0xFF404040);

		stack.pushPose();
		int warningWidth = this.font.width(WARNING);
		float warningScale = Math.min((float) (this.getLeftPos() + this.getImageWidth() - 10 - x) / warningWidth, 1.0F);
		stack.scale(warningScale, warningScale, warningScale);
		int warningY = ((this.getTopPos() + this.getImageHeight()) + (saveButton.y + saveButton.getHeight()) - this.font.lineHeight) / 2;
		this.font.draw(stack, WARNING, x / warningScale, warningY / warningScale, 0xFF404040);
		stack.popPose();
	}

	public void performClick(Button button)
	{
		if (button == this.getSaveButton())
		{
			if (this.isChanged() == false)
			{
				return;
			}

			GameProfile gameProfile = this.getGameProfile();

			if (gameProfile != null)
			{
				AddonNetwork.CHANNEL.sendToServer(new FlagEditMessageSave(this.getTileEntity(), gameProfile));
			}

			this.onClose();
		}
		else if (button == this.getVerityButton())
		{
			String text = this.getValueTextField().getValue();

			if (this.isDirty(text) == false)
			{
				return;
			}

			this.lastRequestText = text;
			this.setGameProfile(null);

			if (this.requestThread != null)
			{
				try
				{
					this.requestThread.interrupt();
					this.requestThread.join();
				}
				catch (Exception e)
				{

				}

			}

			this.requestThread = new Thread(() -> runFillTextures(text));
			this.requestThread.start();
		}

	}

	public boolean isDirty()
	{
		return this.isDirty(this.getValueTextField().getValue());
	}

	public boolean isDirty(String text)
	{
		return StringUtils.isEmpty(text) == false && StringUtils.equalsIgnoreCase(this.lastRequestText, text) == false;
	}

	private void runFillTextures(String text)
	{
		this.setGameProfile(GameProfileUtils.fillTextures(new GameProfile(null, text), text));
		this.requestThread = null;
		this.setChanged(true);
	}

	protected void renderBg(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(stack);

		TextFieldWidget valueTextField = this.getValueTextField();
		this.setFocused(valueTextField);
		valueTextField.setFocus(true);

		RenderSystem.enableBlend();
		Minecraft.getInstance().getTextureManager().bind(BACKGROUND);
		this.blit(stack, this.getLeftPos(), this.getTopPos(), 0, 0, this.getImageWidth(), this.getImageHeight());

		this.getVerityButton().active = this.isDirty();
		this.getSaveButton().active = this.isChanged();
	}

	protected void renderSkull(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
	{
		GameProfile gameProfile = this.getGameProfile();
		TextFieldWidget valueTextField = this.getValueTextField();
		int x = valueTextField.x;
		int y = valueTextField.y + valueTextField.getHeight() + 10;

		if (gameProfile == null && this.requestThread != null)
		{
			ITextComponent text = new StringTextComponent("").append(VERIFYING).append(StringUtils.repeat('.', this.verifyCounter + 1));
			this.font.draw(stack, text, x, y, 0xFF404040);
			return;
		}

		try
		{
			float scale = 128.0F;

			stack.pushPose();
			stack.translate(x, y, 0.0D);
			stack.scale(scale, scale, scale);

			SkullRenderUtils.renderSkinModel(stack, gameProfile, 180.0F, 0.0F);

			stack.popPose();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public FlagTileEntity getTileEntity()
	{
		return this.tileEntity;
	}

	public int getLeftPos()
	{
		return this.leftPos;
	}

	public int getTopPos()
	{
		return this.topPos;
	}

	public int getImageWidth()
	{
		return this.imageWidth;
	}

	public int getImageHeight()
	{
		return this.imageHeight;
	}

	public TextFieldWidget getValueTextField()
	{
		return this.valueTextField;
	}

	public Button getVerityButton()
	{
		return this.verityButton;
	}

	public Button getSaveButton()
	{
		return this.saveButton;
	}

	@Nullable
	public GameProfile getGameProfile()
	{
		return gameProfile;
	}

	public void setGameProfile(@Nullable GameProfile gameProfile)
	{
		this.gameProfile = gameProfile;
	}

	public boolean isChanged()
	{
		return this.changed;
	}

	public void setChanged(boolean changed)
	{
		this.changed = changed;
	}

}
