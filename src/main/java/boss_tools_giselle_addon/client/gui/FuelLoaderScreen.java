package boss_tools_giselle_addon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.FuelLoaderContainer;
import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.network.FuelLoaderMessageWorkingAreaVisible;
import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;

public class FuelLoaderScreen extends AbstractMachineScreen<FuelLoaderContainer>
{
	public static final ResourceLocation TEXTURE = BossToolsAddon.rl("textures/gui/container/fuel_loader.png");
	public static final int TANK_LEFT = 71;
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

	@Override
	public void renderContents(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
	{
		super.renderContents(stack, mouseX, mouseY, partialTicks);

		FuelLoaderTileEntity tileEntity = this.getMenu().getTileEntity();
		this.renderTank(stack, mouseX, mouseY, this.leftPos + TANK_LEFT, this.topPos + TANK_TOP, tileEntity.getFluidTank());
	}

	@Override
	protected void renderBg(MatrixStack stack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(stack, partialTicks, mouseX, mouseY);

		this.minecraft.getTextureManager().bind(TEXTURE);
		this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

}
