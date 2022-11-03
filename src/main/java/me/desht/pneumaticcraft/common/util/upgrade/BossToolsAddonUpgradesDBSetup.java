package me.desht.pneumaticcraft.common.util.upgrade;

import java.util.List;

import me.desht.pneumaticcraft.api.item.EnumUpgrade;

public class BossToolsAddonUpgradesDBSetup extends UpgradesDBSetup
{
	private BossToolsAddonUpgradesDBSetup()
	{

	}

	public static class AddonBuilder extends Builder
	{
		public static AddonBuilder of(EnumUpgrade upgrade, int amount)
		{
			return new AddonBuilder().with(upgrade, amount);
		}

		public AddonBuilder()
		{
			super();
		}

		public AddonBuilder(Builder other)
		{
			super(other);
		}

		@Override
		public AddonBuilder with(EnumUpgrade upgrade, int amount)
		{
			super.with(upgrade, amount);
			return this;
		}

		@Override
		public List<Integer> upgrades()
		{
			return super.upgrades();
		}

	}

}
