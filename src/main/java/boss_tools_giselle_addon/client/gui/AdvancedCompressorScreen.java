package boss_tools_giselle_addon.client.gui;

import java.util.List;

import boss_tools_giselle_addon.common.inventory.container.AdvancedCompressorContainer;
import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.network.AdvancedCompressorMessageMode;
import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity;
import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity.ICompressorMode;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class AdvancedCompressorScreen extends ItemStackToItemStackScreen<AdvancedCompressorContainer>
{
	private Button changeModeButton;

	public AdvancedCompressorScreen(AdvancedCompressorContainer container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
	}

	@Override
	protected void init()
	{
		super.init();

		this.changeModeButton = this.addButton(new Button(this.leftPos - 20, this.topPos - 20, 20, 20, new StringTextComponent(""), this::onWorkingAreaVisibleButtonClick));
		this.refreshChnageModeButtonText();
	}

	public void refreshChnageModeButtonText()
	{

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

	public Button getChangeModeButton()
	{
		return this.changeModeButton;
	}

}
