package beyond_earth_giselle_addon.common.capability;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface IChargeMode
{
	public static final String LANGUGE_CATEGORY_CHARGEMODE = "chargemode";

	@Nonnull
	public static IChargeMode find(@Nullable List<IChargeMode> availableModes, @Nullable String name)
	{
		if (name == null)
		{
			return ChargeMode.NONE;
		}

		ResourceLocation resourceLocation = ResourceLocation.tryParse(name);
		return find(availableModes, resourceLocation);
	}

	@Nonnull
	public static IChargeMode find(@Nullable List<IChargeMode> availableModes, @Nullable ResourceLocation name)
	{
		if (availableModes == null || name == null)
		{
			return ChargeMode.NONE;
		}

		for (IChargeMode mode : availableModes)
		{
			if (mode.getName().equals(name) == true)
			{
				return mode;
			}

		}

		return ChargeMode.NONE;
	}

	public static Tag writeNBT(@Nullable IChargeMode mode)
	{
		ResourceLocation name = (mode != null ? mode : ChargeMode.NONE).getName();
		return StringTag.valueOf(name.toString());
	}

	@Nonnull
	public static IChargeMode readNBT(@Nullable List<IChargeMode> availableModes, @Nullable Tag tag)
	{
		if (tag == null)
		{
			return ChargeMode.NONE;
		}

		ResourceLocation name = ResourceLocation.tryParse(tag.getAsString());
		return find(availableModes, name);
	}

	public static Component createDisplayName(ResourceLocation name)
	{
		return Component.translatable(BeyondEarthAddon.tl(LANGUGE_CATEGORY_CHARGEMODE, name));
	}

	public ResourceLocation getName();

	public Component getDisplayName();

	public Iterable<ItemStack> getItemStacks(Entity entity);
}
