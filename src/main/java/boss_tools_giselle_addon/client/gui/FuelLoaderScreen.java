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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FuelLoaderScreen extends ContainerScreen<FuelLoaderContainer>
{
	public static final ResourceLocation TEXTURE = BossToolsAddon.rl("textures/gui/container/fuel_loader.png");
	public static final int TANK_LEFT = 21;
	public static final int TANK_TOP = 129;

	public FuelLoaderScreen(FuelLoaderContainer container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
		this.imageWidth = 176;
		this.imageHeight = 182;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);

		this.renderTooltip(matrixStack, mouseX, mouseY);

		FuelLoaderTileEntity tileEntity = this.menu.getTileEntity();
		IFluidHandler fluidTank = tileEntity.getFluidTank();
		int tank = 0;
		FluidStack fluidInTank = fluidTank.getFluidInTank(tank);

		int tankLeft = this.leftPos + TANK_TOP;
		int tankTop = this.topPos + TANK_LEFT;

		if (GuiHelper.getFluidTankBounds(tankLeft, tankTop).contains(mouseX, mouseY))
		{
			this.renderTooltip(matrixStack, new TranslationTextComponent(fluidInTank.getTranslationKey()).append(new StringTextComponent(": " + fluidInTank.getAmount() + " mB")), mouseX, mouseY);
		}

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
		GuiHelper.drawFluidTank(matrixStack, tankLeft, tankTop, fluidInTank, fluidTank.getTankCapacity(tank));
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TEXTURE);
		this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

}
