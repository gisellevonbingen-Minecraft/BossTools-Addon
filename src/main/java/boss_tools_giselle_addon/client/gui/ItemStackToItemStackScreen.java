package boss_tools_giselle_addon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.ItemStackToItemStackContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;

public class ItemStackToItemStackScreen<C extends ItemStackToItemStackContainer<C, ?>> extends AbstractMachineScreen<C>
{
	public static final ResourceLocation TEXTURE = BossToolsAddon.rl("textures/gui/container/itemstack_to_itemstack.png");
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;
	public static final int ARROW_LEFT = 62;
	public static final int ARROW_TOP = 30;

	public ItemStackToItemStackScreen(C container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
		this.imageWidth = 176;
		this.imageHeight = 168;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	protected void renderContents(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		super.renderContents(matrix, mouseX, mouseY, partialTicks);

		ItemStackToItemStackTileEntity tileEntity = this.getMenu().getTileEntity();
		this.renderEnergy(matrix, mouseX, mouseY, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP, tileEntity.getPrimaryEnergyStorage());
		GuiHelper.drawArrow(matrix, this.leftPos + ARROW_LEFT, this.topPos + ARROW_TOP, tileEntity.getTimerRatio());
	}

	@Override
	protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(matrix, partialTicks, mouseX, mouseY);

		this.minecraft.getTextureManager().bind(TEXTURE);
		this.blit(matrix, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

}
