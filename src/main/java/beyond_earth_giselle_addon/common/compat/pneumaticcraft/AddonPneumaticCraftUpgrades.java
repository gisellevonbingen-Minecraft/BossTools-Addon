package beyond_earth_giselle_addon.common.compat.pneumaticcraft;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.registries.AddonItems;
import me.desht.pneumaticcraft.api.item.IUpgradeRegistry;
import me.desht.pneumaticcraft.common.core.ModItems;
import me.desht.pneumaticcraft.common.item.UpgradeItem;
import me.desht.pneumaticcraft.common.util.upgrade.ApplicableUpgradesDB;

public class AddonPneumaticCraftUpgrades
{
	public static final AddonPneumaticCraftUpgradeDeferredRegister UPGRADES = new AddonPneumaticCraftUpgradeDeferredRegister(BeyondEarthAddon.MODID);

	public static final AddonPneumaticCraftUpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> SPACE_BREATHING = UPGRADES.register("space_breathing", AddonItems::getMainItemProperties);
	public static final AddonPneumaticCraftUpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> GRAVITY_NORMALIZING = UPGRADES.register("gravity_normalizing", AddonItems::getMainItemProperties);
	public static final AddonPneumaticCraftUpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> SPACE_FIRE_PROOF = UPGRADES.register("space_fire_proof", AddonItems::getMainItemProperties);
	public static final AddonPneumaticCraftUpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> VENUS_ACID_PROOF = UPGRADES.register("venus_acid_proof", AddonItems::getMainItemProperties);

	private AddonPneumaticCraftUpgrades()
	{

	}

	public static void setupDB()
	{
		ApplicableUpgradesDB db = ApplicableUpgradesDB.getInstance();
		db.addApplicableUpgrades(ModItems.PNEUMATIC_HELMET.get(), IUpgradeRegistry.Builder.of(SPACE_BREATHING.get(), 1));
		db.addApplicableUpgrades(ModItems.PNEUMATIC_BOOTS.get(), IUpgradeRegistry.Builder.of(GRAVITY_NORMALIZING.get(), 1));
		db.addApplicableUpgrades(ModItems.PNEUMATIC_CHESTPLATE.get(), IUpgradeRegistry.Builder.of(SPACE_FIRE_PROOF.get(), 1));
		db.addApplicableUpgrades(ModItems.PNEUMATIC_CHESTPLATE.get(), IUpgradeRegistry.Builder.of(VENUS_ACID_PROOF.get(), 1));
	}

}
