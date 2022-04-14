package beyond_earth_giselle_addon.common.capability;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface IChargeMode
{
	public static final String LANGUGE_CATEGORY_CHARGEMODE = "chargemode";

	public static Component createDisplayName(ResourceLocation name)
	{
		return new TranslatableComponent(BeyondEarthAddon.tl(LANGUGE_CATEGORY_CHARGEMODE, name));
	}

	public ResourceLocation getName();

	public Component getDisplayName();

	public Iterable<ItemStack> getItemStacks(Entity entity);
}
