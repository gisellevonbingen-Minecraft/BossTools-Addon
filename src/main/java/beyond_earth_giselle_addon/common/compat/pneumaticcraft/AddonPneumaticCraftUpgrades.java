package beyond_earth_giselle_addon.common.compat.pneumaticcraft;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.registries.AddonItems;
import me.desht.pneumaticcraft.common.item.UpgradeItem;

public class AddonPneumaticCraftUpgrades
{
	public static final AddonPneumaticCraftUpgradeDeferredRegister UPGRADES = new AddonPneumaticCraftUpgradeDeferredRegister(BeyondEarthAddon.MODID);

	public static final AddonPneumaticCraftUpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> SPACE_BREATHING = UPGRADES.register("space_breathing", AddonItems::getMainItemProperties);
	public static final AddonPneumaticCraftUpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> SPACE_FIRE_PROOF = UPGRADES.register("space_fire_proof", AddonItems::getMainItemProperties);
	public static final AddonPneumaticCraftUpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> VENUS_ACID_PROOF = UPGRADES.register("venus_acid_proof", AddonItems::getMainItemProperties);

	private AddonPneumaticCraftUpgrades()
	{

	}

}
