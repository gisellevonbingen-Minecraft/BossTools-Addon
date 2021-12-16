package astrocraft_giselle_addon.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import astrocraft_giselle_addon.common.BossToolsAddon;
import astrocraft_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import astrocraft_giselle_addon.common.compat.AddonCompatibleManager;
import astrocraft_giselle_addon.common.inventory.FuelLoaderContainerMenu;
import astrocraft_giselle_addon.common.network.AddonNetwork;
import astrocraft_giselle_addon.common.network.FuelLoaderMessageWorkingAreaVisible;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.AABB;

public class FuelLoaderScreen extends AbstractMachineScreen<FuelLoaderContainerMenu>
{
	public static final ResourceLocation TEXTURE = BossToolsAddon.rl("textures/gui/container/fuel_loader.png");
	public static final int TANK_LEFT = 71;
	public static final int TANK_TOP = 27;

	public FuelLoaderScreen(FuelLoaderContainerMenu menu, Inventory inventory, Component text)
	{
		super(menu, inventory, text);
		this.imageWidth = 176;
		this.imageHeight = 182;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	public boolean hasWorkingArea()
	{
		return true;
	}

	@Override
	public boolean isWorkingAreaVisible()
	{
		return this.getMenu().getBlockEntity().isWorkingAreaVisible();
	}

	@Override
	public AABB getWorkingArea()
	{
		return this.getMenu().getBlockEntity().getWorkingArea();
	}

	@Override
	public void setWorkingAreaVisible(boolean visible)
	{
		super.setWorkingAreaVisible(visible);
		AddonNetwork.CHANNEL.sendToServer(new FuelLoaderMessageWorkingAreaVisible(this.getMenu().getBlockEntity(), visible));
	}

	@Override
	public void renderContents(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{
		super.renderContents(stack, mouseX, mouseY, partialTicks);

		boolean renderTooltip = AddonCompatibleManager.JEI.isLoaded() == false;
		FuelLoaderBlockEntity blockEntity = this.getMenu().getBlockEntity();
		this.renderTank(stack, mouseX, mouseY, this.leftPos + TANK_LEFT, this.topPos + TANK_TOP, blockEntity.getFluidTank(), renderTooltip);
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(stack, partialTicks, mouseX, mouseY);

		RenderSystem.setShaderTexture(0, TEXTURE);
		this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

}
