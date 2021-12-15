package boss_tools_giselle_addon.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class NBTUtils
{
	public static CompoundTag getTag(CompoundTag parent, String name)
	{
		if (parent == null)
		{
			return new CompoundTag();
		}
		else if (parent.contains(name, Tag.TAG_COMPOUND) == true)
		{
			return parent.getCompound(name);
		}
		else
		{
			return new CompoundTag();
		}

	}

	public static CompoundTag getOrCreateTag(CompoundTag parent, String name)
	{
		if (parent == null)
		{
			return new CompoundTag();
		}
		else if (parent.contains(name, Tag.TAG_COMPOUND) == true)
		{
			return parent.getCompound(name);
		}
		else if (parent.contains(name) == false)
		{
			CompoundTag compound = new CompoundTag();
			parent.put(name, compound);
			return compound;
		}
		else
		{
			return new CompoundTag();
		}

	}

	private NBTUtils()
	{

	}

}
