package boss_tools_giselle_addon.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants.NBT;

public class NBTUtils
{
	public static CompoundNBT getTag(ItemStack itemStack, String name)
	{
		CompoundNBT compound = itemStack.getTagElement(name);
		return compound != null ? compound : new CompoundNBT();
	}

	public static CompoundNBT getTag(Entity entity, String name)
	{
		return getTag(entity.getPersistentData(), name);
	}

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

	public static CompoundNBT getOrCreateTag(ItemStack itemStack, String name)
	{
		return itemStack.getOrCreateTagElement(name);
	}

	public static CompoundNBT getOrCreateTag(Entity entity, String name)
	{
		return getOrCreateTag(entity.getPersistentData(), name);
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
