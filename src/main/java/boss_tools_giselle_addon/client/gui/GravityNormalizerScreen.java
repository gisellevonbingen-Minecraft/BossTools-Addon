package boss_tools_giselle_addon.client.gui;

import java.text.NumberFormat;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.GravityNormalizerContainer;
import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.network.GravityNormalizerMessageRange;
import boss_tools_giselle_addon.common.network.GravityNormalizerMessageWorkingAreaVisible;
import boss_tools_giselle_addon.common.tile.GravityNormalizerTileEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;

public class GravityNormalizerScreen extends ContainerScreen<GravityNormalizerContainer>
{
	public static final ResourceLocation TEXTURE = BossToolsAddon.rl("textures/gui/container/gravity_normalizer.png");
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;

	private boolean cachedWorkingAreaVisible;
	private Button workingAreaVisibleButton;

	public GravityNormalizerScreen(GravityNormalizerContainer container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
		this.imageWidth = 176;
		this.imageHeight = 166;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrix);
		this.updateWorkingAreaVisibleButton();
		super.render(matrix, mouseX, mouseY, partialTicks);

		this.renderTooltip(matrix, mouseX, mouseY);

		GravityNormalizerTileEntity tileEntity = this.menu.getTileEntity();
		IEnergyStorage energyStorage = tileEntity.getPrimaryEnergyStorage();

		int energyLeft = this.leftPos + ENERGY_LEFT;
		int energyTop = this.topPos + ENERGY_TOP;

		if (GuiHelper.getEnergyBounds(energyLeft, energyTop).contains(mouseX, mouseY))
		{
			this.renderTooltip(matrix, GaugeDataHelper.getEnergy(energyStorage).getText(), mouseX, mouseY);
		}

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
		GuiHelper.drawEnergy(matrix, energyLeft, energyTop, energyStorage);
	}

	@Override
	protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TEXTURE);
		this.blit(matrix, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	protected void renderLabels(MatrixStack matrix, int mouseX, int mouseY)
	{
		super.renderLabels(matrix, mouseX, mouseY);

		GravityNormalizerTileEntity tileEntity = this.menu.getTileEntity();
		double range = tileEntity.getRange();
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		String rangeToString = numberInstance.format((range * 2.0D) + 1.0D);
		TranslationTextComponent workingAreaText = new TranslationTextComponent(this.tl("workingarea.text"), rangeToString, rangeToString, rangeToString);

		int sideWidth = 2;
		int sidePadding = 2;
		int workingAreaWidth = this.font.width(workingAreaText) + (sidePadding * 2);
		int workingAreaHeight = 11;
		int workingAreaLeft = this.workingAreaVisibleButton.x + this.workingAreaVisibleButton.getWidth() - this.leftPos;
		int workignAreaTop = -workingAreaHeight;
		int workingAreaOffsetX = workingAreaLeft;
		this.minecraft.getTextureManager().bind(new ResourceLocation("boss_tools:textures/workingarea_side.png"));
		AbstractGui.blit(matrix, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;
		this.minecraft.getTextureManager().bind(new ResourceLocation("boss_tools:textures/workingarea_middle.png"));
		AbstractGui.blit(matrix, workingAreaOffsetX, workignAreaTop, 0, 0, workingAreaWidth, workingAreaHeight, workingAreaWidth, workingAreaHeight);
		workingAreaOffsetX += workingAreaWidth;
		this.minecraft.getTextureManager().bind(new ResourceLocation("boss_tools:textures/workingarea_side.png"));
		AbstractGui.blit(matrix, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;

		this.font.draw(matrix, workingAreaText, workingAreaLeft + sideWidth + sidePadding, workignAreaTop + 2, 0x339900);

		RenderSystem.pushMatrix();
		double usingScale = 0.8D;
		RenderSystem.scaled(usingScale, usingScale, usingScale);
		ITextComponent usingText = GaugeTextHelper.getUsingText(GaugeDataHelper.getEnergy(tileEntity.getEnergyPowerSystem().getPowerForOperation()));
		int usingWidth = this.font.width(usingText);

		this.font.draw(matrix, usingText, (int) ((this.imageWidth - 5) / usingScale) - usingWidth, (int) (this.inventoryLabelY / usingScale), 0x333333);
		RenderSystem.popMatrix();
	}

	@Override
	protected void init()
	{
		super.init();

		this.workingAreaVisibleButton = this.addButton(new Button(this.leftPos - 20, this.topPos - 20, 20, 20, new StringTextComponent(""), this::onWorkingAreaVisibleButtonClick));
		this.addButton(new Button(this.leftPos - 20, this.topPos + 5, 20, 20, new StringTextComponent("+"), this::onWorkingAreaUpButtonClick));
		this.addButton(new Button(this.leftPos - 20, this.topPos + 25, 20, 20, new StringTextComponent("-"), this::onWorkingAreaDownButtonClick));

		this.resizeWorkingAreaVisibleButton();
		this.refreshWorkingAreaVisibleButtonMessage();
	}

	protected void onWorkingAreaVisibleButtonClick(Button button)
	{
		GravityNormalizerTileEntity tileEntity = this.getMenu().getTileEntity();
		boolean nextVisible = !tileEntity.isWorkingAreaVisible();
		AddonNetwork.CHANNEL.sendToServer(new GravityNormalizerMessageWorkingAreaVisible(tileEntity, nextVisible));
	}

	protected void onWorkingAreaUpButtonClick(Button button)
	{
		GravityNormalizerTileEntity tileEntity = this.getMenu().getTileEntity();
		int nextRange = tileEntity.getRange() + 1;
		AddonNetwork.CHANNEL.sendToServer(new GravityNormalizerMessageRange(tileEntity, nextRange));

	}

	protected void onWorkingAreaDownButtonClick(Button button)
	{
		GravityNormalizerTileEntity tileEntity = this.getMenu().getTileEntity();
		int nextRange = tileEntity.getRange() - 1;
		AddonNetwork.CHANNEL.sendToServer(new GravityNormalizerMessageRange(tileEntity, nextRange));
	}

	protected void updateWorkingAreaVisibleButton()
	{
		boolean next = this.getMenu().getTileEntity().isWorkingAreaVisible();

		if (this.cachedWorkingAreaVisible != next)
		{
			this.cachedWorkingAreaVisible = next;
			this.refreshWorkingAreaVisibleButtonMessage();
		}

	}

	protected ITextComponent getWorkingAreaVisibleMessage(boolean visible)
	{
		String prefix = this.tl("workingarea.");
		String method = visible ? "hide" : "show";
		return new TranslationTextComponent(prefix + method);
	}

	protected String tl(String path)
	{
		ResourceLocation registryName = this.getMenu().getTileEntity().getType().getRegistryName();
		String prefix = BossToolsAddon.tl("gui", registryName, path);
		return prefix;
	}

	protected void refreshWorkingAreaVisibleButtonMessage()
	{
		ITextComponent message = this.getWorkingAreaVisibleMessage(this.cachedWorkingAreaVisible);
		this.workingAreaVisibleButton.setMessage(message);
	}

	protected void resizeWorkingAreaVisibleButton()
	{
		int messageWidth = this.workingAreaVisibleButton.getHeight();
		messageWidth = Math.max(messageWidth, this.font.width(this.getWorkingAreaVisibleMessage(true)));
		messageWidth = Math.max(messageWidth, this.font.width(this.getWorkingAreaVisibleMessage(false)));
		this.workingAreaVisibleButton.setWidth(messageWidth + 8);
	}

}
