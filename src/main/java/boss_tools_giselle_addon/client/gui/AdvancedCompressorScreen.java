package boss_tools_giselle_addon.client.gui;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.matrix.MatrixStack;

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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AdvancedCompressorScreen extends ItemStackToItemStackScreen<AdvancedCompressorContainer>
{
	private ItemIconButton changeModeButton;

	public AdvancedCompressorScreen(AdvancedCompressorContainer container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
	}

	@Override
	protected void init()
	{
		super.init();

		this.changeModeButton = this.addButton(new ItemIconButton(this.leftPos + 38, this.topPos + 50, 20, 20, ItemStack.EMPTY, this::onChangeModeButtonClick));
		this.refreshChnageModeButtonText();
	}

	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
	{
		super.render(stack, mouseX, mouseY, partialTicks);
		this.refreshChnageModeButtonText();

		if (this.getChangeModeButton().isHovered() == true)
		{
			AdvancedCompressorTileEntity tileEntity = this.getMenu().getTileEntity();
			int direction = this.getCyclicDirection();
			ICompressorMode cyclicMode = tileEntity.getCyclicMode(direction);
			String text = direction > 0 ? "next" : "prev";
			this.renderTooltip(stack, new TranslationTextComponent(this.rtl("change." + text), cyclicMode.getText()), mouseX, mouseY);
		}

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
		this.font.draw(stack, this.getMenu().getTileEntity().getMode().getText(), button.x + button.getWidth() + 2 - this.leftPos, button.y + button.getHeight() - this.font.lineHeight - this.topPos, 4210752);
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
		AddonNetwork.CHANNEL.sendToServer(new AdvancedCompressorMessageMode(tileEntity, cyclicMode.getRecipeTypeKey()));
	}

	public ItemIconButton getChangeModeButton()
	{
		return this.changeModeButton;
	}

}
