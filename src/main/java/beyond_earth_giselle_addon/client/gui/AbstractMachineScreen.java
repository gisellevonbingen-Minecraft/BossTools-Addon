package beyond_earth_giselle_addon.client.gui;

import java.text.NumberFormat;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.compat.AddonCompatibleManager;
import beyond_earth_giselle_addon.common.inventory.AbstractMachineContainerMenu;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.beyond_earth.client.util.GuiHelper;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.gauge.GaugeValueHelper;

public class AbstractMachineScreen<M extends AbstractMachineContainerMenu<?, ?>> extends AbstractContainerScreen<M>
{
	public static final String WORKINGAREA_TEXT_PREFIX = ctl("workingarea.");
	public static final String WORKINGAREA_TEXT_KEY = WORKINGAREA_TEXT_PREFIX + "text";
	public static final ResourceLocation WORKINGAREA_MIDDLE_TEXTURE = BeyondEarthAddon.rl("textures/gui/workingarea_middle.png");
	public static final ResourceLocation WORKINGAREA_SIDE_TEXTURE = BeyondEarthAddon.rl("textures/gui/workingarea_side.png");

	public static String ctl(String path)
	{
		return BeyondEarthAddon.tl("gui", path);
	}

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
			this.workingAreaVisibleButton = this.addRenderableWidget(new Button(this.leftPos, this.topPos - 20, 20, 20, Component.empty(), this::onChangeModeButtonClick));

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
		RenderSystem.setShaderTexture(0, WORKINGAREA_SIDE_TEXTURE);
		GuiComponent.blit(stack, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;
		RenderSystem.setShaderTexture(0, WORKINGAREA_MIDDLE_TEXTURE);
		GuiComponent.blit(stack, workingAreaOffsetX, workignAreaTop, 0, 0, workingAreaWidth, workingAreaHeight, workingAreaWidth, workingAreaHeight);
		workingAreaOffsetX += workingAreaWidth;
		RenderSystem.setShaderTexture(0, WORKINGAREA_SIDE_TEXTURE);
		GuiComponent.blit(stack, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
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
			if (renderTooltip = AddonCompatibleManager.JEI.isLoaded() == false)
			{
				this.renderTooltip(stack, GaugeTextHelper.getStorageText(GaugeValueHelper.getFluid(tank)).build(), mouseX, mouseY);
			}

		}

	}

	protected Component getWorkingAreaBoundsText(AABB workingArea)
	{
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		Component workingAreaText = Component.translatable(WORKINGAREA_TEXT_KEY, numberInstance.format(workingArea.getXsize()), numberInstance.format(workingArea.getYsize()), numberInstance.format(workingArea.getZsize()));
		return workingAreaText;
	}

	protected Component getWorkingAreaVisibleText(boolean visible)
	{
		String method = visible ? "hide" : "show";
		return Component.translatable(WORKINGAREA_TEXT_PREFIX + method);
	}

	protected String rtl(String path)
	{
		ResourceLocation registryName = ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(this.getMenu().getBlockEntity().getType());
		return BeyondEarthAddon.tl("gui", registryName, path);
	}

}
