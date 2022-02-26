package beyond_earth_giselle_addon.client.gui;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity;
import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity.ICompressorMode;
import beyond_earth_giselle_addon.common.inventory.AdvancedCompressorContainerMenu;
import beyond_earth_giselle_addon.common.network.AddonNetwork;
import beyond_earth_giselle_addon.common.network.AdvancedCompressorMessageMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class AdvancedCompressorScreen extends ItemStackToItemStackScreen<AdvancedCompressorContainerMenu>
{
	public static final ResourceLocation TEXTURE = BeyondEarthAddon.rl("textures/gui/container/advanced_compressor.png");
	public static final int CHANGE_MODE_LEFT = 38;
	public static final int CHANGE_MODE_TOP = 42;
	public static final int AUTO_PULL_Y_OFFSET = 22;

	private ItemIconButton changeModeButton;

	public AdvancedCompressorScreen(AdvancedCompressorContainerMenu menu, Inventory inventory, Component text)
	{
		super(menu, inventory, text);
		this.imageHeight = 187;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	protected void init()
	{
		super.init();

		this.changeModeButton = this.addRenderableWidget(new ItemIconButton(this.leftPos + CHANGE_MODE_LEFT, this.topPos + CHANGE_MODE_TOP, ItemStack.EMPTY, this::onChangeModeButtonClick));
		this.refreshChnageModeButtonText();

		this.getAutoPullButton().y += AUTO_PULL_Y_OFFSET;
		this.getAutoEjectButton().y += AUTO_PULL_Y_OFFSET;
	}

	@Override
	protected void containerTick()
	{
		super.containerTick();

		this.refreshChnageModeButtonText();
	}

	private int getCyclicDirection()
	{
		long window = Minecraft.getInstance().getWindow().getWindow();
		return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT) ? -1 : 1;
	}

	public void refreshChnageModeButtonText()
	{
		ItemIconButton button = this.getChangeModeButton();
		ICompressorMode mode = this.getMenu().getBlockEntity().getMode();
		button.setItemStack(mode.getIcon());
		button.setMessage(mode.getText());
	}

	public void onChangeModeButtonClick(Button button)
	{
		AdvancedCompressorBlockEntity tileEntity = this.getMenu().getBlockEntity();
		int direction = this.getCyclicDirection();
		ICompressorMode cyclicMode = tileEntity.getCyclicMode(direction);
		AddonNetwork.sendToServer(new AdvancedCompressorMessageMode(tileEntity, cyclicMode.getRecipeTypeKey()));
	}

	@Override
	public ResourceLocation getTexture()
	{
		return TEXTURE;
	}

	public ItemIconButton getChangeModeButton()
	{
		return this.changeModeButton;
	}

}
