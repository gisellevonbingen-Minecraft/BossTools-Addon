package boss_tools_giselle_addon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.GravityNormalizerContainer;
import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.network.GravityNormalizerMessageRange;
import boss_tools_giselle_addon.common.network.GravityNormalizerMessageWorkingAreaVisible;
import boss_tools_giselle_addon.common.tile.GravityNormalizerTileEntity;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.gauge.GaugeDataHelper;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;

public class GravityNormalizerScreen extends AbstractMachineScreen<GravityNormalizerContainer>
{
	public static final ResourceLocation TEXTURE = BossToolsAddon.rl("textures/gui/container/gravity_normalizer.png");
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;

	private Button workingAreaPlusButton;
	private Button workingAreaMinusButton;

	public GravityNormalizerScreen(GravityNormalizerContainer container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
		this.imageWidth = 176;
		this.imageHeight = 166;
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

		AddonNetwork.CHANNEL.sendToServer(new GravityNormalizerMessageWorkingAreaVisible(this.getMenu().getTileEntity(), visible));
	}

	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrix);
		super.render(matrix, mouseX, mouseY, partialTicks);

		this.renderTooltip(matrix, mouseX, mouseY);

		GravityNormalizerTileEntity tileEntity = this.getMenu().getTileEntity();
		IEnergyStorage energyStorage = tileEntity.getPrimaryEnergyStorage();

		int energyLeft = this.leftPos + ENERGY_LEFT;
		int energyTop = this.topPos + ENERGY_TOP;

		if (GuiHelper.isHover(GuiHelper.getEnergyBounds(energyLeft, energyTop), mouseX, mouseY) == true)
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
		super.renderBg(matrix, partialTicks, mouseX, mouseY);

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TEXTURE);
		this.blit(matrix, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	protected void renderLabels(MatrixStack matrix, int mouseX, int mouseY)
	{
		super.renderLabels(matrix, mouseX, mouseY);

		RenderSystem.pushMatrix();
		double usingScale = 0.8D;
		RenderSystem.scaled(usingScale, usingScale, usingScale);
		GravityNormalizerTileEntity tileEntity = this.getMenu().getTileEntity();
		ITextComponent usingText = GaugeTextHelper.getUsingText(GaugeDataHelper.getEnergy(tileEntity.getEnergyPowerSystem().getPowerForOperation()));
		int usingWidth = this.font.width(usingText);

		this.font.draw(matrix, usingText, (int) ((this.imageWidth - 5) / usingScale) - usingWidth, (int) (this.inventoryLabelY / usingScale), 0x333333);
		RenderSystem.popMatrix();
	}

	@Override
	protected void init()
	{
		super.init();

		this.workingAreaPlusButton = this.addButton(new Button(this.leftPos - 20, this.topPos + 5, 20, 20, new StringTextComponent("+"), this::onWorkingAreaUpButtonClick));
		this.workingAreaMinusButton = this.addButton(new Button(this.leftPos - 20, this.topPos + 25, 20, 20, new StringTextComponent("-"), this::onWorkingAreaDownButtonClick));
	}

	public void onWorkingAreaUpButtonClick(Button button)
	{
		GravityNormalizerTileEntity tileEntity = this.getMenu().getTileEntity();
		int nextRange = tileEntity.getRange() + 1;
		AddonNetwork.CHANNEL.sendToServer(new GravityNormalizerMessageRange(tileEntity, nextRange));
	}

	public void onWorkingAreaDownButtonClick(Button button)
	{
		GravityNormalizerTileEntity tileEntity = this.getMenu().getTileEntity();
		int nextRange = tileEntity.getRange() - 1;
		AddonNetwork.CHANNEL.sendToServer(new GravityNormalizerMessageRange(tileEntity, nextRange));
	}

	public Button getWorkingAreaPlusButton()
	{
		return this.workingAreaPlusButton;
	}

	public Button getWorkingAreaMinusButton()
	{
		return this.workingAreaMinusButton;
	}

}
