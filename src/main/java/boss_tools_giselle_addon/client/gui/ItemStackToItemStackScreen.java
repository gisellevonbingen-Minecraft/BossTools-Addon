package boss_tools_giselle_addon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.ItemStackToItemStackContainer;
import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.network.IS2ISMachineMessageAutoEject;
import boss_tools_giselle_addon.common.network.IS2ISMachineMessageAutoPull;
import boss_tools_giselle_addon.common.network.IS2ISMachineMessageAutoTransfer;
import boss_tools_giselle_addon.common.tile.ItemStackToItemStackTileEntityMultiRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;

public class ItemStackToItemStackScreen<C extends ItemStackToItemStackContainer<C, ?>> extends AbstractMachineScreen<C>
{
	public static final ResourceLocation TEXTURE = BossToolsAddon.rl("textures/gui/container/itemstack_to_itemstack.png");
	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;
	public static final int ARROW_LEFT = 62;
	public static final int ARROW_TOP = 21;
	public static final int AUTO_PULL_LEFT = 43;
	public static final int AUTO_PULL_TOP = 42;
	public static final ITextComponent AUTO_PULL_TEXT = new TranslationTextComponent(ctl("auto_pull"));
	public static final int AUTO_EJECT_LEFT = 43;
	public static final int AUTO_EJECT_TOP = 55;
	public static final ITextComponent AUTO_EJECT_TEXT = new TranslationTextComponent(ctl("auto_eject"));

	private SimpleCheckBoxButton autoPullButton;
	private SimpleCheckBoxButton autoEjectButton;

	public ItemStackToItemStackScreen(C container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
		this.imageWidth = 176;
		this.imageHeight = 168;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	protected void init()
	{
		super.init();

		this.autoPullButton = this.addButton(new SimpleCheckBoxButton(this.leftPos + AUTO_PULL_LEFT, this.topPos + AUTO_PULL_TOP, AUTO_PULL_TEXT)
		{
			@Override
			public void onPress()
			{
				super.onPress();
				sendmeAutoTransferPacket(IS2ISMachineMessageAutoPull::new, !this.isChecked());
			}
		});
		this.autoPullButton.setAutoToggle(false);
		this.autoEjectButton = this.addButton(new SimpleCheckBoxButton(this.leftPos + AUTO_EJECT_LEFT, this.topPos + AUTO_EJECT_TOP, AUTO_EJECT_TEXT)
		{
			@Override
			public void onPress()
			{
				super.onPress();
				sendmeAutoTransferPacket(IS2ISMachineMessageAutoEject::new, !this.isChecked());
			}
		});
		this.autoEjectButton.setAutoToggle(false);
	}

	@FunctionalInterface
	public interface PacketConstructor
	{
		public IS2ISMachineMessageAutoTransfer invoke(ItemStackToItemStackTileEntityMultiRecipe tileEntity, boolean value);
	}

	public void sendmeAutoTransferPacket(PacketConstructor constructor, boolean nextState)
	{
		AddonNetwork.sendToServer(constructor.invoke(this.getMenu().getTileEntity(), nextState));
	}

	@Override
	public void tick()
	{
		super.tick();

		this.refreshAutoTransferCheckBoxStates();
	}

	public void refreshAutoTransferCheckBoxStates()
	{
		ItemStackToItemStackTileEntityMultiRecipe tileEntity = this.getMenu().getTileEntity();
		this.getAutoPullButton().setChecked(tileEntity.isAutoPull());
		this.getAutoEjectButton().setChecked(tileEntity.isAutoEject());
	}

	@Override
	protected void renderContents(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
	{
		super.renderContents(stack, mouseX, mouseY, partialTicks);

		ItemStackToItemStackTileEntityMultiRecipe tileEntity = this.getMenu().getTileEntity();
		GuiHelper.drawArrow(stack, this.leftPos + ARROW_LEFT, this.topPos + ARROW_TOP, tileEntity.getTimerRatio());
		this.renderEnergy(stack, mouseX, mouseY, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP, tileEntity.getPrimaryEnergyStorage());
	}

	@Override
	protected void renderBg(MatrixStack stack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(stack, partialTicks, mouseX, mouseY);

		this.minecraft.getTextureManager().bind(this.getTexture());
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
