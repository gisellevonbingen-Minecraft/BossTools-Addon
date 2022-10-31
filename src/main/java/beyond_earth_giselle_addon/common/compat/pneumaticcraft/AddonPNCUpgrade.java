package beyond_earth_giselle_addon.common.compat.pneumaticcraft;

import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class AddonPNCUpgrade extends PNCUpgrade
{
	public static ResourceLocation getItemName(AddonPNCUpgrade upgrade, int tier)
	{
		ForgeRegistry<PNCUpgrade> registry = RegistryManager.ACTIVE.getRegistry(AddonPneumaticCraftUpgradeDeferredRegister.REGISTRY_KEY);
		ResourceLocation name = registry.getKey(upgrade);
		return new ResourceLocation(name.getNamespace(), getItemName(name.getPath(), upgrade.getMaxTier(), tier));
	}

	public static String getItemName(String name, int maxTier, int tier)
	{
		String prefix = "pneumatic_" + name + "_upgrade";

		if (maxTier == 1)
		{
			return prefix;
		}
		else
		{
			return prefix + "_" + tier;
		}

	}

	public AddonPNCUpgrade()
	{
		super();
	}

	public AddonPNCUpgrade(int maxTier, String... depModIds)
	{
		super(maxTier, depModIds);
	}

	
}
