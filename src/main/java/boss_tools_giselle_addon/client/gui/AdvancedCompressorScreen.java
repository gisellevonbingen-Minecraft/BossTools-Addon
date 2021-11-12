package boss_tools_giselle_addon.client.gui;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.BossToolsAddon;
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

		this.changeModeButton = this.addButton(new ItemIconButton(this.leftPos + 38, this.topPos + 50, 20, 20, ItemStack.EMPTY, this::onWorkingAreaVisibleButtonClick));
		this.refreshChnageModeButtonText();
	}

	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		super.render(matrix, mouseX, mouseY, partialTicks);
		this.refreshChnageModeButtonText();

		if (this.getChangeModeButton().isHovered() == true)
		{
			int direction = 1;
			String text = "next";

			if (InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) == true)
			{
				direction = -1;
				text = "prev";
			}

			AdvancedCompressorTileEntity tileEntity = this.getMenu().getTileEntity();
			ICompressorMode cyclicMode = tileEntity.getCyclicMode(direction);
			ResourceLocation registryName = tileEntity.getType().getRegistryName();
			this.renderTooltip(matrix, new TranslationTextComponent(BossToolsAddon.tl("gui", registryName, "change." + text), cyclicMode.getText()), mouseX, mouseY);
		}

	}

	@Override
	protected void renderLabels(MatrixStack matrix, int mouseX, int mouseY)
	{
		super.renderLabels(matrix, mouseX, mouseY);

		ItemIconButton button = this.getChangeModeButton();
		this.font.draw(matrix, this.getMenu().getTileEntity().getMode().getText(), button.x + button.getWidth() + 2 - this.leftPos, button.y + button.getHeight() - this.font.lineHeight - this.topPos, 4210752);
	}

	public void refreshChnageModeButtonText()
	{
		this.getChangeModeButton().setItemStack(this.getMenu().getTileEntity().getMode().getIcon());
	}

	public void onWorkingAreaVisibleButtonClick(Button button)
	{
		AdvancedCompressorTileEntity tileEntity = this.getMenu().getTileEntity();
		ICompressorMode nextMode = this.getNextMode();
		AddonNetwork.CHANNEL.sendToServer(new AdvancedCompressorMessageMode(tileEntity, nextMode.getRecipeTypeKey()));
	}

	public ICompressorMode getNextMode()
	{
		AdvancedCompressorTileEntity tileEntity = this.getMenu().getTileEntity();
		List<ICompressorMode> modes = tileEntity.getAvailableModes();
		ICompressorMode nextMode = modes.get((modes.indexOf(tileEntity.getMode()) + 1) % modes.size());
		return nextMode;
	}

	public ItemIconButton getChangeModeButton()
	{
		return this.changeModeButton;
	}

}
