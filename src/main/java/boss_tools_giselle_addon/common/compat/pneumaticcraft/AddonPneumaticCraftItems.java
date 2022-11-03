package boss_tools_giselle_addon.common.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.compat.pneumaticcraft.item.ItemMachineAddonUpgrade;
import boss_tools_giselle_addon.common.registries.DeferredRegisterWrapper;
import boss_tools_giselle_addon.common.registries.ItemDeferredRegister;
import me.desht.pneumaticcraft.api.item.EnumUpgrade;
import me.desht.pneumaticcraft.common.core.ModItems;
import me.desht.pneumaticcraft.common.util.upgrade.ApplicableUpgradesDB;
import me.desht.pneumaticcraft.common.util.upgrade.BossToolsAddonUpgradesDBSetup;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class AddonPneumaticCraftItems
{
	public static final DeferredRegisterWrapper<Item> ITEMS = new ItemDeferredRegister(BossToolsAddon.MODID);

	public static final Collection<RegistryObject<ItemMachineAddonUpgrade>> SPACE_BREATHING = register(AddonEnumUpgrade.SPACE_BREATHING.get());
	public static final Collection<RegistryObject<ItemMachineAddonUpgrade>> GRAVITY_NORMALIZING = register(AddonEnumUpgrade.GRAVITY_NORMALIZING.get());
	public static final Collection<RegistryObject<ItemMachineAddonUpgrade>> SPACE_FIRE_PROOF = register(AddonEnumUpgrade.SPACE_FIRE_PROOF.get());
	public static final Collection<RegistryObject<ItemMachineAddonUpgrade>> VENUS_ACID_PROOF = register(AddonEnumUpgrade.VENUS_ACID_PROOF.get());

	private AddonPneumaticCraftItems()
	{

	}

	public static List<RegistryObject<ItemMachineAddonUpgrade>> register(EnumUpgrade upgrade)
	{
		String name = upgrade.getName();
		int maxTier = upgrade.getMaxTier();
		List<RegistryObject<ItemMachineAddonUpgrade>> items = new ArrayList<>();

		for (int i = 0; i < upgrade.getMaxTier(); i++)
		{
			int tier = i + 1;
			items.add(ITEMS.register(AddonEnumUpgrade.getItemName(name, maxTier, tier), () -> new ItemMachineAddonUpgrade(upgrade, tier)));
		}

		return Collections.unmodifiableList(items);
	}

	public static void setupDB()
	{
		ApplicableUpgradesDB db = ApplicableUpgradesDB.getInstance();
		db.addApplicableUpgrades(ModItems.PNEUMATIC_HELMET.get(), BossToolsAddonUpgradesDBSetup.AddonBuilder.of(AddonEnumUpgrade.SPACE_BREATHING.get(), 1));
		db.addApplicableUpgrades(ModItems.PNEUMATIC_BOOTS.get(), BossToolsAddonUpgradesDBSetup.AddonBuilder.of(AddonEnumUpgrade.GRAVITY_NORMALIZING.get(), 1));
		db.addApplicableUpgrades(ModItems.PNEUMATIC_CHESTPLATE.get(), BossToolsAddonUpgradesDBSetup.AddonBuilder.of(AddonEnumUpgrade.SPACE_FIRE_PROOF.get(), 1));
		db.addApplicableUpgrades(ModItems.PNEUMATIC_CHESTPLATE.get(), BossToolsAddonUpgradesDBSetup.AddonBuilder.of(AddonEnumUpgrade.VENUS_ACID_PROOF.get(), 1));
	}

}
