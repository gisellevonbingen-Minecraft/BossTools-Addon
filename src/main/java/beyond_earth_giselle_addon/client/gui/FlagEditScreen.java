package beyond_earth_giselle_addon.client.gui;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.client.util.SkullRenderUtils;
import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.network.AddonNetwork;
import beyond_earth_giselle_addon.common.network.FlagEditMessageSave;
import beyond_earth_giselle_addon.common.util.GameProfileUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.common.blocks.entities.FlagBlockEntity;

public class FlagEditScreen extends Screen
{
	public static final ResourceLocation BACKGROUND = texture("back");
	public static final Component TITLE = language("title");
	public static final Component VERIFY = language("verify");
	public static final Component VERIFYING = language("verifying");
	public static final Component SAVE = language("save");

	public static ResourceLocation texture(String name)
	{
		return BeyondEarthAddon.rl("textures/gui/flag_edit_" + name + ".png");
	}

	public static Component language(String key, Object... objects)
	{
		return Component.translatable(BeyondEarthAddon.tl("gui", "flag_edit." + key), objects);
	}

	private final FlagBlockEntity blockEntity;

	protected int imageWidth;
	protected int imageHeight;
	protected int leftPos;
	protected int topPos;

	protected EditBox valueTextField;
	protected Button verityButton;
	protected Button saveButton;

	private String lastRequestText;
	private Thread requestThread;
	private GameProfile gameProfile;
	private int verifyCounter;
	private boolean changed;

	private boolean willClose;

	public FlagEditScreen(FlagBlockEntity blockEntity)
	{
		this(blockEntity, language("title"));
	}

	public FlagEditScreen(FlagBlockEntity blockEntity, Component text)
	{
		super(text);
		this.blockEntity = blockEntity;
		GameProfile gameProfile = blockEntity.getPlayerProfile();

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
		this.verityButton = this.addRenderableWidget(new Button(this.leftPos + imageWidth - buttonWidth - 10, this.topPos + 25, buttonWidth, buttonHeight, VERIFY, this::performClick));
		this.saveButton = this.addRenderableWidget(new Button(this.verityButton.x, this.topPos + imageHeight - buttonHeight - 10, this.verityButton.getWidth(), this.verityButton.getHeight(), SAVE, this::performClick));

		EditBox prevValueTestFie = this.getValueTextField();
		int valueTextLeft = this.leftPos + 10;
		this.valueTextField = this.addRenderableWidget(new EditBox(this.font, valueTextLeft, this.topPos + 25, this.verityButton.x - 5 - valueTextLeft, 20, Component.empty()));
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
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBg(stack, mouseX, mouseY, partialTicks);
		super.render(stack, mouseX, mouseY, partialTicks);
		this.renderSkull(stack, mouseX, mouseY, partialTicks);

		this.font.draw(stack, TITLE, this.getLeftPos() + 10, this.getTopPos() + 10, 0xFF404040);

		if (this.isWillClose() == true)
		{
			this.onClose();
		}

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
				AddonNetwork.sendToServer(new FlagEditMessageSave(this.getBlockEntity(), gameProfile));
			}

			this.setWillClose(true);
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
		GameProfileUtils.fillTextures(new GameProfile(null, text), text, gameProfile ->
		{
			this.setGameProfile(gameProfile);
			this.requestThread = null;
			this.setChanged(true);
		});
	}

	protected void renderBg(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(stack);

		EditBox valueTextField = this.getValueTextField();
		this.setFocused(valueTextField);
		valueTextField.setFocus(true);

		RenderSystem.enableBlend();
		RenderSystem.setShaderTexture(0, BACKGROUND);
		this.blit(stack, this.getLeftPos(), this.getTopPos(), 0, 0, this.getImageWidth(), this.getImageHeight());

		this.getVerityButton().active = this.isDirty();
		this.getSaveButton().active = this.isChanged();
	}

	protected void renderSkull(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{
		GameProfile gameProfile = this.getGameProfile();
		EditBox valueTextField = this.getValueTextField();
		int x = valueTextField.x;
		int y = valueTextField.y + valueTextField.getHeight() + 10;

		if (gameProfile == null && this.requestThread != null)
		{
			Component text = Component.literal("").append(VERIFYING).append(StringUtils.repeat('.', this.verifyCounter + 1));
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

	public FlagBlockEntity getBlockEntity()
	{
		return this.blockEntity;
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

	public EditBox getValueTextField()
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
		return this.gameProfile;
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

	public boolean isWillClose()
	{
		return this.willClose;
	}

	public void setWillClose(boolean willClose)
	{
		this.willClose = willClose;
	}

}
