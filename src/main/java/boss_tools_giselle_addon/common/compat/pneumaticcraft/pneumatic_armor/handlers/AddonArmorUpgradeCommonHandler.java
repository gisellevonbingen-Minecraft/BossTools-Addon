package boss_tools_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers;

import me.desht.pneumaticcraft.api.pneumatic_armor.BaseArmorUpgradeHandler;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorExtensionData;
import net.minecraft.util.ResourceLocation;

public abstract class AddonArmorUpgradeCommonHandler<T extends IArmorExtensionData> extends BaseArmorUpgradeHandler<T>
{
	private final ResourceLocation id;

	public AddonArmorUpgradeCommonHandler(ResourceLocation id)
	{
		this.id = id;
	}

	@Override
	public final ResourceLocation getID()
	{
		return this.id;
	}

}
