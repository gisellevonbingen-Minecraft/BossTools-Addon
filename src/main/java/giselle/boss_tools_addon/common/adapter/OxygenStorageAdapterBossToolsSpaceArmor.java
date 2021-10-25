package giselle.boss_tools_addon.common.adapter;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class OxygenStorageAdapterBossToolsSpaceArmor extends OxygenStorageAdapter<ItemStack>
{
	private static final String KEY_ENERGY = "Energy";

	public OxygenStorageAdapterBossToolsSpaceArmor(ItemStack target)
	{
		super(target);
	}

	@Override
	public double getStoredOxygen()
	{
		CompoundNBT compound = this.getTarget().getTag();
		return compound != null ? compound.getDouble(KEY_ENERGY) : 0;
	}

	@Override
	public void setStoredOxygen(double oxygen)
	{
		CompoundNBT compound = this.getTarget().getOrCreateTag();
		compound.putDouble(KEY_ENERGY, oxygen);
	}

	@Override
	public double getOxygenCapacity()
	{
		return 48000;
	}

}
