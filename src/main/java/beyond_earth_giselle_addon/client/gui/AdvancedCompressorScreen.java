package beyond_earth_giselle_addon.client.gui;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity;
import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity.ICompressorMode;
import beyond_earth_giselle_addon.common.inventory.AdvancedCompressorContainerMenu;
import beyond_earth_giselle_addon.common.network.AddonNetwork;
import beyond_earth_giselle_addon.common.network.AdvancedCompressorMessageMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class AdvancedCompressorScreen extends ItemStackToItemStackScreen<AdvancedCompressorContainerMenu>
{
	private static final int CHANGE_MODE_LEFT = 38;
	private static final int CHANGE_MODE_TOP = 50;

	private ItemIconButton changeModeButton;

	public AdvancedCompressorScreen(AdvancedCompressorContainerMenu menu, Inventory inventory, Component text)
	{
		super(menu, inventory, text);
	}

	@Override
	protected void init()
	{
		super.init();

		this.changeModeButton = this.addRenderableWidget(new ItemIconButton(this.leftPos + CHANGE_MODE_LEFT, this.topPos + CHANGE_MODE_TOP, 20, 20, ItemStack.EMPTY, this::onChangeModeButtonClick));
		this.refreshChnageModeButtonText();
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{
		super.render(stack, mouseX, mouseY, partialTicks);
		this.refreshChnageModeButtonText();
	}

	private int getCyclicDirection()
	{
		long window = Minecraft.getInstance().getWindow().getWindow();
		return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT) ? -1 : 1;
	}

	@Override
	protected void renderLabels(PoseStack stack, int mouseX, int mouseY)
	{
		super.renderLabels(stack, mouseX, mouseY);

		ItemIconButton button = this.getChangeModeButton();
		this.font.draw(stack, this.getMenu().getBlockEntity().getMode().getText(), button.x + button.getWidth() + 2 - this.leftPos, button.y + button.getHeight() - this.font.lineHeight - this.topPos, 4210752);
	}

	public void refreshChnageModeButtonText()
	{
		this.getChangeModeButton().setItemStack(this.getMenu().getBlockEntity().getMode().getIcon());
	}

	public void onChangeModeButtonClick(Button button)
	{
		AdvancedCompressorBlockEntity tileEntity = this.getMenu().getBlockEntity();
		int direction = this.getCyclicDirection();
		ICompressorMode cyclicMode = tileEntity.getCyclicMode(direction);
		AddonNetwork.sendToServer(new AdvancedCompressorMessageMode(tileEntity, cyclicMode.getRecipeTypeKey()));
	}

	public ItemIconButton getChangeModeButton()
	{
		return this.changeModeButton;
	}

}
