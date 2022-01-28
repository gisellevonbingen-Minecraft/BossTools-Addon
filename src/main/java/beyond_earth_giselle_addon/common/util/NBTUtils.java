package beyond_earth_giselle_addon.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class NBTUtils
{
	public static CompoundTag getTag(ItemStack itemStack, String name)
	{
		CompoundTag compound = itemStack.getTagElement(name);
		return compound != null ? compound : new CompoundTag();
	}

	public static CompoundTag getTag(Entity entity, String name)
	{
		return getTag(entity.getPersistentData(), name);
	}

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

	public static CompoundTag getOrCreateTag(ItemStack itemStack, String name)
	{
		return itemStack.getOrCreateTagElement(name);
	}

	public static CompoundTag getOrCreateTag(Entity entity, String name)
	{
		return getOrCreateTag(entity.getPersistentData(), name);
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
