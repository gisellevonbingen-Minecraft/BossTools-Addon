package boss_tools_giselle_addon.client.gui;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.AdvancedCompressorContainer;
import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.network.AdvancedCompressorMessageMode;
import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity;
import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity.ICompressorMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AdvancedCompressorScreen extends ItemStackToItemStackScreen<AdvancedCompressorContainer>
{
	public static final ResourceLocation TEXTURE = BossToolsAddon.rl("textures/gui/container/advanced_compressor.png");
	public static final int CHANGE_MODE_LEFT = 38;
	public static final int CHANGE_MODE_TOP = 42;
	public static final int AUTO_PULL_Y_OFFSET = 22;

	private ItemIconButton changeModeButton;

	public AdvancedCompressorScreen(AdvancedCompressorContainer container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
		this.imageHeight = 187;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	protected void init()
	{
		super.init();

		this.changeModeButton = this.addButton(new ItemIconButton(this.leftPos + CHANGE_MODE_LEFT, this.topPos + CHANGE_MODE_TOP, 20, 20, ItemStack.EMPTY, this::onChangeModeButtonClick));
		this.refreshChnageModeButtonText();

		this.getAutoPullButton().y += AUTO_PULL_Y_OFFSET;
		this.getAutoEjectButton().y += AUTO_PULL_Y_OFFSET;
	}

	@Override
	public void tick()
	{
		super.tick();

		this.refreshChnageModeButtonText();
	}

	private int getCyclicDirection()
	{
		long window = Minecraft.getInstance().getWindow().getWindow();
		return InputMappings.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT) ? -1 : 1;
	}

	@Override
	protected void renderLabels(MatrixStack stack, int mouseX, int mouseY)
	{
		super.renderLabels(stack, mouseX, mouseY);

		ItemIconButton button = this.getChangeModeButton();
		this.font.draw(stack, this.getMenu().getTileEntity().getMode().getText(), button.x + button.getWidth() + 2 - this.leftPos, button.y + button.getHeight() - this.font.lineHeight - this.topPos, 0x404040);
	}

	public void refreshChnageModeButtonText()
	{
		this.getChangeModeButton().setItemStack(this.getMenu().getTileEntity().getMode().getIcon());
	}

	public void onChangeModeButtonClick(Button button)
	{
		AdvancedCompressorTileEntity tileEntity = this.getMenu().getTileEntity();
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
