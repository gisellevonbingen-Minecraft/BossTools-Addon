package boss_tools_giselle_addon.client.gui;

import boss_tools_giselle_addon.common.inventory.container.ElectricBlastFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ElectricBlastFurnaceScreen extends ItemStackToItemStackScreen<ElectricBlastFurnaceContainer>
{
	public ElectricBlastFurnaceScreen(ElectricBlastFurnaceContainer container, PlayerInventory inv, ITextComponent text)
	{
		super(container, inv, text);
	}

}
