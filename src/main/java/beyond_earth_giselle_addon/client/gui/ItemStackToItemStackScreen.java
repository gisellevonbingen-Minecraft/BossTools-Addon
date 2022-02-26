package beyond_earth_giselle_addon.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.block.entity.ItemStackToItemStackBlockEntityMultiRecipe;
import beyond_earth_giselle_addon.common.inventory.ItemStackToItemStackContainerMenu;
import beyond_earth_giselle_addon.common.network.AddonNetwork;
import beyond_earth_giselle_addon.common.network.IS2ISMachineMessageAutoEject;
import beyond_earth_giselle_addon.common.network.IS2ISMachineMessageAutoPull;
import beyond_earth_giselle_addon.common.network.IS2ISMachineMessageAutoTransfer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;

public class ItemStackToItemStackScreen<C extends ItemStackToItemStackContainerMenu<C, ?>> extends AbstractMachineScreen<C>
{
	public static final ResourceLocation TEXTURE = BeyondEarthAddon.rl("textures/gui/container/itemstack_to_itemstack.png");
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;
	public static final int ARROW_LEFT = 62;
	public static final int ARROW_TOP = 21;
	public static final int AUTO_PULL_LEFT = 43;
	public static final int AUTO_PULL_TOP = 42;
	public static final Component AUTO_PULL_TEXT = new TranslatableComponent(ctl("auto_pull"));
	public static final int AUTO_EJECT_LEFT = 43;
	public static final int AUTO_EJECT_TOP = 55;
	public static final Component AUTO_EJECT_TEXT = new TranslatableComponent(ctl("auto_eject"));

	private SimpleCheckBoxButton autoPullButton;
	private SimpleCheckBoxButton autoEjectButton;

	public ItemStackToItemStackScreen(C menu, Inventory inventory, Component text)
	{
		super(menu, inventory, text);
		this.imageWidth = 176;
		this.imageHeight = 168;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	protected void init()
	{
		super.init();

		this.autoPullButton = this.addRenderableWidget(new SimpleCheckBoxButton(this.leftPos + AUTO_PULL_LEFT, this.topPos + AUTO_PULL_TOP, AUTO_PULL_TEXT)
		{
			@Override
			public void onPress()
			{
				super.onPress();
				sendmeAutoTransferPacket(IS2ISMachineMessageAutoPull::new, !this.isChecked());
			}
		});
		this.autoPullButton.setAutoToggle(false);
		this.autoEjectButton = this.addRenderableWidget(new SimpleCheckBoxButton(this.leftPos + AUTO_EJECT_LEFT, this.topPos + AUTO_EJECT_TOP, AUTO_EJECT_TEXT)
		{
			@Override
			public void onPress()
			{
				super.onPress();
				sendmeAutoTransferPacket(IS2ISMachineMessageAutoEject::new, !this.isChecked());
			}
		});
		this.autoEjectButton.setAutoToggle(false);
		this.refreshAutoTransferCheckBoxStates();
	}

	@FunctionalInterface
	public interface PacketConstructor
	{
		public IS2ISMachineMessageAutoTransfer invoke(ItemStackToItemStackBlockEntityMultiRecipe blockEntity, boolean value);
	}

	public void sendmeAutoTransferPacket(PacketConstructor constructor, boolean nextState)
	{
		AddonNetwork.sendToServer(constructor.invoke(this.getMenu().getBlockEntity(), nextState));
	}

	@Override
	protected void containerTick()
	{
		super.containerTick();

		this.refreshAutoTransferCheckBoxStates();
	}

	public void refreshAutoTransferCheckBoxStates()
	{
		ItemStackToItemStackBlockEntityMultiRecipe blockEntity = this.getMenu().getBlockEntity();
		this.getAutoPullButton().setChecked(blockEntity.isAutoPull());
		this.getAutoEjectButton().setChecked(blockEntity.isAutoEject());
	}

	@Override
	protected void renderContents(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{
		super.renderContents(stack, mouseX, mouseY, partialTicks);

		ItemStackToItemStackBlockEntityMultiRecipe blockEntity = this.getMenu().getBlockEntity();
		GuiHelper.drawArrow(stack, this.leftPos + ARROW_LEFT, this.topPos + ARROW_TOP, blockEntity.getTimerRatio());
		this.renderEnergy(stack, mouseX, mouseY, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP, blockEntity.getPrimaryEnergyStorage());
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(stack, partialTicks, mouseX, mouseY);

		RenderSystem.setShaderTexture(0, this.getTexture());
		this.blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

	public ResourceLocation getTexture()
	{
		return TEXTURE;
	}

	public SimpleCheckBoxButton getAutoPullButton()
	{
		return this.autoPullButton;
	}

	public SimpleCheckBoxButton getAutoEjectButton()
	{
		return this.autoEjectButton;
	}

}
