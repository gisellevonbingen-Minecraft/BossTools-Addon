package beyond_earth_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.options;

import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IArmorUpgradeClientHandler;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IGuiScreen;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IOptionPage.SimpleOptionPage;
import me.desht.pneumaticcraft.client.render.pneumatic_armor.PneumaticHelmetRegistry;

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

		gui.addWidget(PneumaticHelmetRegistry.getInstance().makeStatMoveButton(30, 128, this.getClientUpgradeHandler()));
	}

}
