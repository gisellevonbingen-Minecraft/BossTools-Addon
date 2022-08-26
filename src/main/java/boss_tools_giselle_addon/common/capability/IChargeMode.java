package boss_tools_giselle_addon.common.capability;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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

	public static INBT writeNBT(@Nullable IChargeMode mode)
	{
		ResourceLocation name = (mode != null ? mode : ChargeMode.NONE).getName();
		return StringNBT.valueOf(name.toString());
	}

	@Nonnull
	public static IChargeMode readNBT(@Nullable List<IChargeMode> availableModes, @Nullable INBT nbt)
	{
		if (nbt == null)
		{
			return ChargeMode.NONE;
		}

		ResourceLocation name = ResourceLocation.tryParse(nbt.getAsString());
		return find(availableModes, name);
	}

	public static ITextComponent createDisplayName(ResourceLocation name)
	{
		return new TranslationTextComponent(BossToolsAddon.tl(LANGUGE_CATEGORY_CHARGEMODE, name));
	}

	public ResourceLocation getName();

	public ITextComponent getDisplayName();

	public Iterable<ItemStack> getItemStacks(Entity entity);
}
