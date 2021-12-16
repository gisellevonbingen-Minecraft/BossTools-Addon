package astrocraft_giselle_addon.client.gui;

import astrocraft_giselle_addon.common.inventory.ElectricBlastFurnaceContainerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ElectricBlastFurnaceScreen extends ItemStackToItemStackScreen<ElectricBlastFurnaceContainerMenu>
{
	public ElectricBlastFurnaceScreen(ElectricBlastFurnaceContainerMenu menu, Inventory inventory, Component text)
	{
		super(menu, inventory, text);
	}

}
