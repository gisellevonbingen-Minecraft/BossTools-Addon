package boss_tools_giselle_addon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.ElectricBlastFurnaceContainer;
import boss_tools_giselle_addon.common.tile.ElectricBlastFurnaceTileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;

public class ElectricBlastFurnaceScreen extends ContainerScreen<ElectricBlastFurnaceContainer>
{
	public static final ResourceLocation TEXTURE = BossToolsAddon.rl("textures/gui/container/electric_blast_furnace.png");
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;
	public static final int ARROW_LEFT = 62;
	public static final int ARROW_TOP = 36;

	public ElectricBlastFurnaceScreen(ElectricBlastFurnaceContainer container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
		this.imageWidth = 176;
		this.imageHeight = 168;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrix);
		super.render(matrix, mouseX, mouseY, partialTicks);

		this.renderTooltip(matrix, mouseX, mouseY);

		ElectricBlastFurnaceTileEntity tileEntity = this.menu.getTileEntity();
		IEnergyStorage energyStorage = tileEntity.getPrimaryEnergyStorage();

		int energyLeft = this.leftPos + ENERGY_LEFT;
		int energyTop = this.topPos + ENERGY_TOP;
		int arrowLeft = this.leftPos + ARROW_LEFT;
		int arrowTop = this.topPos + ARROW_TOP;

		if (GuiHelper.isHover(GuiHelper.getEnergyBounds(energyLeft, energyTop), mouseX, mouseY) == true)
		{
			this.renderTooltip(matrix, GaugeDataHelper.getEnergy(tileEntity).getText(), mouseX, mouseY);
		}

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
		GuiHelper.drawEnergy(matrix, energyLeft, energyTop, energyStorage);
		GuiHelper.drawArrow(matrix, arrowLeft, arrowTop, tileEntity.getTimerRatio());
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TEXTURE);
		this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

}