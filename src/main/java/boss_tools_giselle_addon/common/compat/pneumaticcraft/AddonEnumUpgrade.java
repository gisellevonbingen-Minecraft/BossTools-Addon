package boss_tools_giselle_addon.common.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.util.LazyReference;
import me.desht.pneumaticcraft.api.item.EnumUpgrade;
import net.minecraft.util.ResourceLocation;

public class AddonEnumUpgrade
{
	private static final List<LazyEnumUpgrade> INTERNAL_LAZYS = new ArrayList<>();
	public static final List<LazyEnumUpgrade> LAZYS = Collections.unmodifiableList(INTERNAL_LAZYS);
	private static final List<EnumUpgrade> INTERNAL_ADDEDS = new ArrayList<>();
	public static final List<EnumUpgrade> ADDEDS = Collections.unmodifiableList(INTERNAL_ADDEDS);

	public static final LazyEnumUpgrade SPACE_BREATHING = register(new LazyEnumUpgrade("SPACE_BREATHING"));
	public static final LazyEnumUpgrade GRAVITY_NORMALIZING = register(new LazyEnumUpgrade("GRAVITY_NORMALIZING"));
	public static final LazyEnumUpgrade SPACE_FIRE_PROOF = register(new LazyEnumUpgrade("SPACE_FIRE_PROOF"));
	public static final LazyEnumUpgrade VENUS_ACID_PROOF = register(new LazyEnumUpgrade("VENUS_ACID_PROOF"));

	private static LazyEnumUpgrade register(LazyEnumUpgrade lazy)
	{
		INTERNAL_LAZYS.add(lazy);
		return lazy;
	}

	public static ResourceLocation getItemName(EnumUpgrade upgrade, int tier)
	{
		return new ResourceLocation(BossToolsAddon.MODID, getItemName(upgrade.getName(), upgrade.getMaxTier(), tier));
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

	public static class LazyEnumUpgrade extends LazyReference<EnumUpgrade>
	{
		public final String internalName;
		public final String name;

		public LazyEnumUpgrade(String name)
		{
			this(name.toUpperCase(), name.toLowerCase());
		}

		public LazyEnumUpgrade(String internalName, String name)
		{
			this.internalName = internalName;
			this.name = name;
		}

		@Override
		protected void onSet(EnumUpgrade value)
		{
			super.onSet(value);
			INTERNAL_ADDEDS.add(value);
		}

	}

}
