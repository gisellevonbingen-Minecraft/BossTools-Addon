package boss_tools_giselle_addon.common.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants.NBT;

public class NBTUtils
{
	public static CompoundNBT getTag(CompoundNBT parent, String name)
	{
		if (parent == null)
		{
			return new CompoundNBT();
		}
		else if (parent.contains(name, NBT.TAG_COMPOUND) == true)
		{
			return parent.getCompound(name);
		}
		else
		{
			return new CompoundNBT();
		}

	}

	public static CompoundNBT getOrCreateTag(CompoundNBT parent, String name)
	{
		if (parent == null)
		{
			return new CompoundNBT();
		}
		else if (parent.contains(name, NBT.TAG_COMPOUND) == true)
		{
			return parent.getCompound(name);
		}
		else if (parent.contains(name) == false)
		{
			CompoundNBT compound = new CompoundNBT();
			parent.put(name, compound);
			return compound;
		}
		else
		{
			return new CompoundNBT();
		}

	}

	private NBTUtils()
	{

	}

}
