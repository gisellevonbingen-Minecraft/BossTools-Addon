package astrocraft_giselle_addon.client.gui;

import java.text.NumberFormat;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import astrocraft_giselle_addon.common.AstroCraftAddon;
import astrocraft_giselle_addon.common.inventory.AbstractMachineContainerMenu;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.mrscauthd.astrocraft.gauge.GaugeTextHelper;
import net.mrscauthd.astrocraft.gauge.GaugeValueHelper;
import net.mrscauthd.astrocraft.gui.helper.GuiHelper;

public class AbstractMachineScreen<M extends AbstractMachineContainerMenu<?, ?>> extends AbstractContainerScreen<M>
{
	private boolean cachedWorkingAreaVisible;
	private Button workingAreaVisibleButton;

	public AbstractMachineScreen(M menu, Inventory inv, Component text)
	{
		super(menu, inv, text);
	}

	@Override
	protected void init()
	{
		super.init();

		if (this.hasWorkingArea() == true)
		{
			this.workingAreaVisibleButton = this.addRenderableWidget(new Button(this.leftPos, this.topPos - 20, 20, 20, new TextComponent(""), this::onChangeModeButtonClick));

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
	public AABB getWorkingArea()
	{
		return null;
	}

	public void setWorkingAreaVisible(boolean visible)
	{
		this.cachedWorkingAreaVisible = visible;
	}

	public void onChangeModeButtonClick(net.minecraft.client.gui.components.Button button)
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
		Component message = this.getWorkingAreaVisibleText(this.cachedWorkingAreaVisible);
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
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);

		RenderSystem.enableBlend();
		this.renderContents(stack, mouseX, mouseY, partialTicks);
		this.renderTooltip(stack, mouseX, mouseY);

		if (this.hasWorkingArea() == true)
		{
			this.updateWorkingAreaVisibleButton();
		}

	}

	protected void renderContents(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{

	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY)
	{

	}

	protected void drawWorkingAreaText(PoseStack stack, @Nullable AABB workingArea, AbstractWidget left)
	{
		if (workingArea == null)
		{
			return;
		}

		Component workingAreaText = this.getWorkingAreaBoundsText(workingArea);

		int sideWidth = 1;
		int sidePadding = 2;
		int workingAreaWidth = this.font.width(workingAreaText) + (sidePadding * 2);
		int workingAreaHeight = 11;
		int workingAreaLeft = left.x + left.getWidth() - this.leftPos;
		int workignAreaTop = -workingAreaHeight;
		int workingAreaOffsetX = workingAreaLeft;
		RenderSystem.setShaderTexture(0, AstroCraftAddon.rl("textures/gui/workingarea_side.png"));
		Screen.blit(stack, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;
		RenderSystem.setShaderTexture(0, AstroCraftAddon.rl("textures/gui/workingarea_middle.png"));
		Screen.blit(stack, workingAreaOffsetX, workignAreaTop, 0, 0, workingAreaWidth, workingAreaHeight, workingAreaWidth, workingAreaHeight);
		workingAreaOffsetX += workingAreaWidth;
		RenderSystem.setShaderTexture(0, AstroCraftAddon.rl("textures/gui/workingarea_side.png"));
		Screen.blit(stack, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;

		this.font.draw(stack, workingAreaText, workingAreaLeft + sideWidth + sidePadding, workignAreaTop + 2, 0x303030);
	}

	@Override
	protected void renderLabels(PoseStack stack, int mouseX, int mouseY)
	{
		super.renderLabels(stack, mouseX, mouseY);

		if (this.hasWorkingArea() == true)
		{
			AABB workingArea = this.getWorkingArea();
			this.drawWorkingAreaText(stack, workingArea, this.getWorkingAreaVisibleButton());
		}

	}

	protected void renderEnergy(PoseStack stack, int mouseX, int mouseY, int left, int top, IEnergyStorage energyStorage)
	{
		GuiHelper.drawEnergy(stack, left, top, energyStorage);

		if (GuiHelper.isHover(GuiHelper.getEnergyBounds(left, top), mouseX, mouseY) == true)
		{
			this.renderTooltip(stack, GaugeTextHelper.getStorageText(GaugeValueHelper.getEnergy(energyStorage)).build(), mouseX, mouseY);
		}

	}

	protected void renderTank(PoseStack stack, int mouseX, int mouseY, int left, int top, FluidTank tank)
	{
		this.renderTank(stack, mouseX, mouseY, left, top, tank, true);
	}

	protected void renderTank(PoseStack stack, int mouseX, int mouseY, int left, int top, FluidTank tank, boolean renderTooltip)
	{
		FluidStack fluidInTank = tank.getFluid();
		GuiHelper.drawFluidTank(stack, left, top, fluidInTank, tank.getCapacity());

		if (renderTooltip == true && GuiHelper.isHover(GuiHelper.getFluidTankBounds(left, top), mouseX, mouseY) == true)
		{
			this.renderTooltip(stack, GaugeTextHelper.getStorageText(GaugeValueHelper.getFluid(tank)).build(), mouseX, mouseY);
		}

	}

	protected Component getWorkingAreaBoundsText(AABB workingArea)
	{
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		TranslatableComponent workingAreaText = new TranslatableComponent(this.ctl("workingarea.text"), numberInstance.format(workingArea.getXsize()), numberInstance.format(workingArea.getYsize()), numberInstance.format(workingArea.getZsize()));
		return workingAreaText;
	}

	protected Component getWorkingAreaVisibleText(boolean visible)
	{
		String prefix = this.ctl("workingarea.");
		String method = visible ? "hide" : "show";
		return new TranslatableComponent(prefix + method);
	}

	protected String rtl(String path)
	{
		ResourceLocation registryName = this.getMenu().getBlockEntity().getType().getRegistryName();
		return AstroCraftAddon.tl("gui", registryName, path);
	}

	protected String ctl(String path)
	{
		return AstroCraftAddon.tl("gui", path);
	}

}
