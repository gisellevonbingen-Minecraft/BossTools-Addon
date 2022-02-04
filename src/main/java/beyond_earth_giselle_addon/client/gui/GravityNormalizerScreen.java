package beyond_earth_giselle_addon.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import beyond_earth_giselle_addon.common.inventory.GravityNormalizerContainerMenu;
import beyond_earth_giselle_addon.common.network.AddonNetwork;
import beyond_earth_giselle_addon.common.network.GravityNormalizerMessageRange;
import beyond_earth_giselle_addon.common.network.GravityNormalizerMessageWorkingAreaVisible;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.AABB;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;

public class GravityNormalizerScreen extends AbstractMachineScreen<GravityNormalizerContainerMenu>
{
	public static final ResourceLocation TEXTURE = BeyondEarthAddon.rl("textures/gui/container/gravity_normalizer.png");
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;

	private Button workingAreaPlusButton;
	private Button workingAreaMinusButton;

	public GravityNormalizerScreen(GravityNormalizerContainerMenu menu, Inventory inventory, Component text)
	{
		super(menu, inventory, text);
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

		AddonNetwork.sendToServer(new GravityNormalizerMessageWorkingAreaVisible(this.getMenu().getBlockEntity(), visible));
	}

	@Override
	protected void renderContents(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{
		super.renderContents(stack, mouseX, mouseY, partialTicks);

		GravityNormalizerBlockEntity blockEntity = this.getMenu().getBlockEntity();
		this.renderEnergy(stack, mouseX, mouseY, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP, blockEntity.getPrimaryEnergyStorage());
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(stack, partialTicks, mouseX, mouseY);

		RenderSystem.setShaderTexture(0, TEXTURE);
		this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	protected void renderLabels(PoseStack stack, int mouseX, int mouseY)
	{
		super.renderLabels(stack, mouseX, mouseY);

		stack.pushPose();
		float usingScale = 0.8F;
		stack.scale(usingScale, usingScale, usingScale);
		GravityNormalizerBlockEntity blockEntity = this.getMenu().getBlockEntity();
		Component usingText = GaugeTextHelper.getUsingText(GaugeValueHelper.getEnergy(blockEntity.getEnergyPowerSystem().getPowerForOperation())).build();
		int usingWidth = this.font.width(usingText);

		this.font.draw(stack, usingText, (int) ((this.imageWidth - 5) / usingScale) - usingWidth, (int) (this.inventoryLabelY / usingScale), 0x333333);
		stack.popPose();
	}

	@Override
	protected void init()
	{
		super.init();

		this.workingAreaPlusButton = this.addRenderableWidget(new Button(this.leftPos - 20, this.topPos + 5, 20, 20, new TextComponent("+"), this::onWorkingAreaUpButtonClick));
		this.workingAreaMinusButton = this.addRenderableWidget(new Button(this.leftPos - 20, this.topPos + 25, 20, 20, new TextComponent("-"), this::onWorkingAreaDownButtonClick));
	}

	public void onWorkingAreaUpButtonClick(Button button)
	{
		GravityNormalizerBlockEntity blockEntity = this.getMenu().getBlockEntity();
		int nextRange = blockEntity.getRange() + 1;
		AddonNetwork.sendToServer(new GravityNormalizerMessageRange(blockEntity, nextRange));
	}

	public void onWorkingAreaDownButtonClick(Button button)
	{
		GravityNormalizerBlockEntity blockEntity = this.getMenu().getBlockEntity();
		int nextRange = blockEntity.getRange() - 1;
		AddonNetwork.sendToServer(new GravityNormalizerMessageRange(blockEntity, nextRange));
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
