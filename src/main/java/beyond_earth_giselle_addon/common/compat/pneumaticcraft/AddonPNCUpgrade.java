package beyond_earth_giselle_addon.common.compat.pneumaticcraft;

import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import net.minecraft.resources.ResourceLocation;

public class AddonPNCUpgrade extends PNCUpgrade
{
	public static ResourceLocation getItemName(AddonPNCUpgrade upgrade, int tier)
	{
		ResourceLocation name = upgrade.getRegistryName();
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

	@Override
	protected ResourceLocation getItemRegistryName(int tier)
	{
		return getItemName(this, tier);
	}

}
