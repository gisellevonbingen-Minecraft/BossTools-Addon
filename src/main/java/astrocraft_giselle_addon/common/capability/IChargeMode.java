package astrocraft_giselle_addon.common.capability;

import astrocraft_giselle_addon.common.AstroCraftAddon;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface IChargeMode
{
	public static final String LANGUGE_CATEGORY_CHARGEMODE = "chargemode";

	public ResourceLocation getName();

	public default Component getDisplayName()
	{
		return new TranslatableComponent(AstroCraftAddon.tl(LANGUGE_CATEGORY_CHARGEMODE, this.getName()));
	}

	public Iterable<ItemStack> getItemStacks(Entity entity);
}
