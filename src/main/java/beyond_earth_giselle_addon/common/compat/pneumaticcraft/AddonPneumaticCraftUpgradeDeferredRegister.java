package beyond_earth_giselle_addon.common.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import beyond_earth_giselle_addon.common.registries.DeferredRegisterWrapper;
import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import me.desht.pneumaticcraft.common.core.ModUpgrades;
import me.desht.pneumaticcraft.common.item.UpgradeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AddonPneumaticCraftUpgradeDeferredRegister
{
	public static final ResourceLocation REGISTRY_NAME = ModUpgrades.UPGRADES_DEFERRED.getRegistryName();

	private final String modid;
	private final Set<AddonPneumaticCraftUpgradeRegistryObject<?, ?>> objects;
	private final Set<AddonPneumaticCraftUpgradeRegistryObject<?, ?>> readonlyObjects;

	protected final DeferredRegisterWrapper<PNCUpgrade> primaryRegister;
	protected final DeferredRegisterWrapper<Item> secondaryRegister;

	public AddonPneumaticCraftUpgradeDeferredRegister(String modid)
	{
		this.modid = modid;
		this.objects = new HashSet<>();
		this.readonlyObjects = Collections.unmodifiableSet(this.objects);

		this.primaryRegister = DeferredRegisterWrapper.create(modid, REGISTRY_NAME);
		this.secondaryRegister = DeferredRegisterWrapper.create(modid, ForgeRegistries.ITEMS);
	}

	public AddonPneumaticCraftUpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> register(String name, Supplier<Item.Properties> propertiesSup)
	{
		return this.register(name, propertiesSup, 1);
	}

	public AddonPneumaticCraftUpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> register(String name, Supplier<Item.Properties> propertiesSup, int maxTier, String... depModIds)
	{
		return this.register(name, () -> new AddonPNCUpgrade(maxTier, depModIds), u -> new UpgradeItem(u, maxTier, propertiesSup.get()));
	}

	@SuppressWarnings("unchecked")
	public <U extends AddonPNCUpgrade, I extends UpgradeItem> AddonPneumaticCraftUpgradeRegistryObject<U, I> register(String name, Supplier<? extends U> upgradeSup, Function<RegistryObject<PNCUpgrade>, ? extends I> itemFunc)
	{
		U upgrade = upgradeSup.get();
		int maxTier = upgrade.getMaxTier();
		RegistryObject<? extends PNCUpgrade> upgradeRegistry = this.primaryRegister.register(name, () -> upgrade);

		List<RegistryObject<I>> items = new ArrayList<>();

		for (int i = 0; i < upgrade.getMaxTier(); i++)
		{
			items.add(this.secondaryRegister.register(AddonPNCUpgrade.getItemName(name, maxTier, i + 1), () -> itemFunc.apply((RegistryObject<PNCUpgrade>) upgradeRegistry)));
		}

		AddonPneumaticCraftUpgradeRegistryObject<U, I> registryObject = new AddonPneumaticCraftUpgradeRegistryObject<>((RegistryObject<U>) upgradeRegistry, items);
		this.objects.add(registryObject);
		return registryObject;
	}

	public void register(IEventBus bus)
	{
		this.primaryRegister.register(bus);
		this.secondaryRegister.register(bus);
	}

	public String getModid()
	{
		return this.modid;
	}

	public Collection<AddonPneumaticCraftUpgradeRegistryObject<?, ?>> getObjects()
	{
		return this.readonlyObjects;
	}

}
