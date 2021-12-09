package boss_tools_giselle_addon.client.gui;

import java.text.NumberFormat;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.AbstractMachineContainer;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;

public class AbstractMachineScreen<C extends AbstractMachineContainer<?, ?>> extends ContainerScreen<C>
{
	private boolean cachedWorkingAreaVisible;
	private Button workingAreaVisibleButton;

	public AbstractMachineScreen(C container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
	}

	@Override
	protected void init()
	{
		super.init();

		if (this.hasWorkingArea() == true)
		{
			this.workingAreaVisibleButton = this.addButton(new Button(this.leftPos, this.topPos - 20, 20, 20, new StringTextComponent(""), this::onChangeModeButtonClick));

			this.resizeWorkingAreaVisibleButton();
			this.refreshWorkingAreaVisibleButtonMessage();
		}

	}

	public boolean hasWorkingArea()
	{
		return false;
	}

	public boolean isWorkingAreaVisible()
	{
		return false;
	}

	@Nullable
	public AxisAlignedBB getWorkingArea()
	{
		return null;
	}

	public void setWorkingAreaVisible(boolean visible)
	{
		this.cachedWorkingAreaVisible = visible;
	}

	public void onChangeModeButtonClick(Button button)
	{
		this.setWorkingAreaVisible(!this.isWorkingAreaVisible());
	}

	public void updateWorkingAreaVisibleButton()
	{
		boolean next = this.isWorkingAreaVisible();

		if (this.cachedWorkingAreaVisible != next)
		{
			this.cachedWorkingAreaVisible = next;
			this.refreshWorkingAreaVisibleButtonMessage();
		}

	}

	public void refreshWorkingAreaVisibleButtonMessage()
	{
		ITextComponent message = this.getWorkingAreaVisibleText(this.cachedWorkingAreaVisible);
		this.getWorkingAreaVisibleButton().setMessage(message);
	}

	public void resizeWorkingAreaVisibleButton()
	{
		Button workingAreaVisibleButton = this.getWorkingAreaVisibleButton();
		int messageWidth = workingAreaVisibleButton.getHeight();
		messageWidth = Math.max(messageWidth, this.font.width(this.getWorkingAreaVisibleText(true)));
		messageWidth = Math.max(messageWidth, this.font.width(this.getWorkingAreaVisibleText(false)));
		workingAreaVisibleButton.setWidth(messageWidth + 8);
	}

	public Button getWorkingAreaVisibleButton()
	{
		return this.workingAreaVisibleButton;
	}

	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrix);
		super.render(matrix, mouseX, mouseY, partialTicks);

		RenderSystem.enableBlend();
		this.renderContents(matrix, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrix, mouseX, mouseY);

		if (this.hasWorkingArea() == true)
		{
			this.updateWorkingAreaVisibleButton();
		}

	}

	protected void renderContents(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{

	}

	@Override
	protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY)
	{

	}

	protected void drawWorkingAreaText(MatrixStack matrix, @Nullable AxisAlignedBB workingArea, Widget left)
	{
		if (workingArea == null)
		{
			return;
		}

		ITextComponent workingAreaText = this.getWorkingAreaBoundsText(workingArea);

		int sideWidth = 1;
		int sidePadding = 2;
		int workingAreaWidth = this.font.width(workingAreaText) + (sidePadding * 2);
		int workingAreaHeight = 11;
		int workingAreaLeft = left.x + left.getWidth() - this.leftPos;
		int workignAreaTop = -workingAreaHeight;
		int workingAreaOffsetX = workingAreaLeft;
		this.minecraft.getTextureManager().bind(BossToolsAddon.rl("textures/gui/workingarea_side.png"));
		AbstractGui.blit(matrix, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;
		this.minecraft.getTextureManager().bind(BossToolsAddon.rl("textures/gui/workingarea_middle.png"));
		AbstractGui.blit(matrix, workingAreaOffsetX, workignAreaTop, 0, 0, workingAreaWidth, workingAreaHeight, workingAreaWidth, workingAreaHeight);
		workingAreaOffsetX += workingAreaWidth;
		this.minecraft.getTextureManager().bind(BossToolsAddon.rl("textures/gui/workingarea_side.png"));
		AbstractGui.blit(matrix, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;

		this.font.draw(matrix, workingAreaText, workingAreaLeft + sideWidth + sidePadding, workignAreaTop + 2, 0x303030);
	}

	@Override
	protected void renderLabels(MatrixStack matrix, int mouseX, int mouseY)
	{
		super.renderLabels(matrix, mouseX, mouseY);

		if (this.hasWorkingArea() == true)
		{
			AxisAlignedBB workingArea = this.getWorkingArea();
			this.drawWorkingAreaText(matrix, workingArea, this.getWorkingAreaVisibleButton());
		}

	}

	protected void renderEnergy(MatrixStack matrix, int mouseX, int mouseY, int left, int top, IEnergyStorage energyStorage)
	{
		GuiHelper.drawEnergy(matrix, left, top, energyStorage);

		if (GuiHelper.isHover(GuiHelper.getEnergyBounds(left, top), mouseX, mouseY) == true)
		{
			this.renderTooltip(matrix, GaugeTextHelper.getStorageText(GaugeValueHelper.getEnergy(energyStorage)).build(), mouseX, mouseY);
		}

	}

	protected void renderTank(MatrixStack matrix, int mouseX, int mouseY, int left, int top, FluidTank tank)
	{
		FluidStack fluidInTank = tank.getFluid();
		GuiHelper.drawFluidTank(matrix, left, top, fluidInTank, tank.getCapacity());

		if (GuiHelper.isHover(GuiHelper.getFluidTankBounds(left, top), mouseX, mouseY) == true)
		{
			this.renderTooltip(matrix, GaugeTextHelper.getStorageText(GaugeValueHelper.getFluid(tank)).build(), mouseX, mouseY);
		}

	}

	protected ITextComponent getWorkingAreaBoundsText(AxisAlignedBB workingArea)
	{
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		TranslationTextComponent workingAreaText = new TranslationTextComponent(this.ctl("workingarea.text"), numberInstance.format(workingArea.getXsize()), numberInstance.format(workingArea.getYsize()), numberInstance.format(workingArea.getZsize()));
		return workingAreaText;
	}

	protected ITextComponent getWorkingAreaVisibleText(boolean visible)
	{
		String prefix = this.ctl("workingarea.");
		String method = visible ? "hide" : "show";
		return new TranslationTextComponent(prefix + method);
	}

	protected String rtl(String path)
	{
		ResourceLocation registryName = this.getMenu().getTileEntity().getType().getRegistryName();
		return BossToolsAddon.tl("gui", registryName, path);
	}

	protected String ctl(String path)
	{
		return BossToolsAddon.tl("gui", path);
	}

}
