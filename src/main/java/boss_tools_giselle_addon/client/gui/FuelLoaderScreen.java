package boss_tools_giselle_addon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.FuelLoaderContainer;
import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;

public class FuelLoaderScreen extends ContainerScreen<FuelLoaderContainer>
{
	public static final ResourceLocation TEXTURE = BossToolsAddon.rl("textures/gui/container/fuel_loader.png");
	public static final int TANK_LEFT = 127;
	public static final int TANK_TOP = 27;

	public FuelLoaderScreen(FuelLoaderContainer container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
		this.imageWidth = 176;
		this.imageHeight = 182;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrix);
		super.render(matrix, mouseX, mouseY, partialTicks);

		this.renderTooltip(matrix, mouseX, mouseY);

		FuelLoaderTileEntity tileEntity = this.menu.getTileEntity();
		IFluidHandler fluidTank = tileEntity.getFluidTank();
		int tank = 0;
		FluidStack fluidInTank = fluidTank.getFluidInTank(tank);

		int tankLeft = this.leftPos + TANK_LEFT;
		int tankTop = this.topPos + TANK_TOP;

		if (GuiHelper.isHover(GuiHelper.getFluidTankBounds(tankLeft, tankTop), mouseX, mouseY) == true)
		{
			this.renderTooltip(matrix, GaugeDataHelper.getFluid(tileEntity.getFluidTank()).getText(), mouseX, mouseY);
		}

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
		GuiHelper.drawFluidTank(matrix, tankLeft, tankTop, fluidInTank, fluidTank.getTankCapacity(tank));
	}

	@Override
	protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TEXTURE);
		this.blit(matrix, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

}
