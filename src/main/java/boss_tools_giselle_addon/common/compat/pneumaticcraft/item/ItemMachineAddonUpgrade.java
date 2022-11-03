package boss_tools_giselle_addon.common.compat.pneumaticcraft.item;

import boss_tools_giselle_addon.common.registries.AddonItemGroups;
import me.desht.pneumaticcraft.api.item.EnumUpgrade;
import me.desht.pneumaticcraft.common.item.ItemMachineUpgrade;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemMachineAddonUpgrade extends ItemMachineUpgrade
{
	public ItemMachineAddonUpgrade(EnumUpgrade upgrade, int tier)
	{
		super(upgrade, tier);
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items)
	{
		super.fillItemCategory(group, items);

		if (group == AddonItemGroups.tab_main)
		{
			if (this.getUpgradeType().isDepLoaded() == true)
			{
				items.add(new ItemStack(this));
			}

		}

	}

}
