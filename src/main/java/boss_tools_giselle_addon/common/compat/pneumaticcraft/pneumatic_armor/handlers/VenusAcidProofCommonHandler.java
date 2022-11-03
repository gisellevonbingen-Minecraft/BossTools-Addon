package boss_tools_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers;

import boss_tools_giselle_addon.common.compat.pneumaticcraft.AddonEnumUpgrade;
import me.desht.pneumaticcraft.api.item.EnumUpgrade;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorExtensionData;
import me.desht.pneumaticcraft.api.pneumatic_armor.ICommonArmorHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class VenusAcidProofCommonHandler extends AddonArmorUpgradeCommonHandler<IArmorExtensionData>
{
	public VenusAcidProofCommonHandler(ResourceLocation id)
	{
		super(id);
	}

	@Override
	public EquipmentSlotType getEquipmentSlot()
	{
		return EquipmentSlotType.CHEST;
	}

	@Override
	public float getIdleAirUsage(ICommonArmorHandler arg0)
	{
		return 0;
	}

	@Override
	public EnumUpgrade[] getRequiredUpgrades()
	{
		return new EnumUpgrade[]{AddonEnumUpgrade.VENUS_ACID_PROOF.get()};
	}

}
