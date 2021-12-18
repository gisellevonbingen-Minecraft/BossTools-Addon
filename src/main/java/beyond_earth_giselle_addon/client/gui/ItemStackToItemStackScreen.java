package beyond_earth_giselle_addon.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.inventory.ItemStackToItemStackContainerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;
import net.mrscauthd.beyond_earth.machines.tile.ItemStackToItemStackBlockEntity;

public class ItemStackToItemStackScreen<C extends ItemStackToItemStackContainerMenu<C, ?>> extends AbstractMachineScreen<C>
{
	public static final ResourceLocation TEXTURE = BeyondEarthAddon.rl("textures/gui/container/itemstack_to_itemstack.png");
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;
	public static final int ARROW_LEFT = 62;
	public static final int ARROW_TOP = 30;

	public ItemStackToItemStackScreen(C menu, Inventory inventory, Component text)
	{
		super(menu, inventory, text);
		this.imageWidth = 176;
		this.imageHeight = 168;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	protected void renderContents(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{
		super.renderContents(stack, mouseX, mouseY, partialTicks);

		ItemStackToItemStackBlockEntity blockEntity = this.getMenu().getBlockEntity();
		this.renderEnergy(stack, mouseX, mouseY, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP, blockEntity.getPrimaryEnergyStorage());
		GuiHelper.drawArrow(stack, this.leftPos + ARROW_LEFT, this.topPos + ARROW_TOP, blockEntity.getTimerRatio());
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(stack, partialTicks, mouseX, mouseY);

		RenderSystem.setShaderTexture(0, TEXTURE);
		this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

}