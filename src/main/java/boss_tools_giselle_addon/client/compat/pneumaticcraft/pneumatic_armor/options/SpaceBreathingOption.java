package boss_tools_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.options;

import static me.desht.pneumaticcraft.common.util.PneumaticCraftUtils.xlate;

import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IArmorUpgradeClientHandler;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IGuiScreen;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IOptionPage.SimpleOptionPage;
import me.desht.pneumaticcraft.client.gui.pneumatic_armor.GuiMoveStat;
import me.desht.pneumaticcraft.client.gui.widget.WidgetButtonExtended;
import me.desht.pneumaticcraft.common.config.subconfig.ArmorHUDLayout.LayoutType;
import me.desht.pneumaticcraft.common.config.subconfig.BossToolsAddonArmorHUDLayout;
import net.minecraft.client.Minecraft;

public class SpaceBreathingOption<T extends IArmorUpgradeClientHandler<?>> extends SimpleOptionPage<T>
{
	public SpaceBreathingOption(IGuiScreen screen, T clientUpgradeHandler)
	{
		super(screen, clientUpgradeHandler);
	}

	@Override
	public void populateGui(IGuiScreen gui)
	{
		super.populateGui(gui);
		LayoutType.values();

		gui.addWidget(new WidgetButtonExtended(30, 128, 150, 20, xlate("pneumaticcraft.armor.gui.misc.moveStatScreen"), b ->
		{
			Minecraft minecraft = Minecraft.getInstance();
			minecraft.player.closeContainer();
			minecraft.setScreen(new GuiMoveStat(this.getClientUpgradeHandler(), BossToolsAddonArmorHUDLayout.LayoutType.SPACE_BREATHING));
		}));

	}

}
