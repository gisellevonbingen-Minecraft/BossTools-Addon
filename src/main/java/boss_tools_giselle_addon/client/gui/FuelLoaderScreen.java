package boss_tools_giselle_addon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.FuelLoaderContainer;
import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.network.FuelLoaderMessageWorkingAreaVisible;
import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;

public class FuelLoaderScreen extends AbstractMachineScreen<FuelLoaderContainer>
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

	@Override
	public boolean hasWorkingArea()
	{
		return true;
	}

	@Override
	public boolean isWorkingAreaVisible()
	{
		return this.getMenu().getTileEntity().isWorkingAreaVisible();
	}

	@Override
	public AxisAlignedBB getWorkingArea()
	{
		return this.getMenu().getTileEntity().getWorkingArea();
	}

	@Override
	public void setWorkingAreaVisible(boolean visible)
	{
		super.setWorkingAreaVisible(visible);

		AddonNetwork.CHANNEL.sendToServer(new FuelLoaderMessageWorkingAreaVisible(this.getMenu().getTileEntity(), visible));
	}

	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrix);
		super.render(matrix, mouseX, mouseY, partialTicks);

		this.renderTooltip(matrix, mouseX, mouseY);

		FuelLoaderTileEntity tileEntity = this.getMenu().getTileEntity();
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
		super.renderBg(matrix, partialTicks, mouseX, mouseY);

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TEXTURE);
		this.blit(matrix, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

}
