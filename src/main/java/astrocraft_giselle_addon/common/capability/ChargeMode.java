package astrocraft_giselle_addon.common.capability;

import java.util.Collections;
import java.util.function.Function;

import astrocraft_giselle_addon.common.AstroCraftAddon;
import astrocraft_giselle_addon.common.inventory.ItemHandlerHelper2;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

public enum ChargeMode implements IChargeMode
{
	NONE(AstroCraftAddon.rl("none"), e -> Collections.emptyList()),
	ARMORS(AstroCraftAddon.rl("armors"), Entity::getArmorSlots),
	ALL(AstroCraftAddon.rl("all"), e -> e.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).lazyMap(ItemHandlerHelper2::getStacks).orElse(NonNullList.withSize(0, ItemStack.EMPTY))),
	//
	;

	private final ResourceLocation name;
	private final Function<Entity, Iterable<ItemStack>> function;

	private ChargeMode(ResourceLocation name, Function<Entity, Iterable<ItemStack>> function)
	{
		this.name = name;
		this.function = function;
	}

	@Override
	public ResourceLocation getName()
	{
		return this.name;
	}

	public Function<Entity, Iterable<ItemStack>> getFunction()
	{
		return this.function;
	}

	@Override
	public Iterable<ItemStack> getItemStacks(Entity entity)
	{
		return this.getFunction().apply(entity);
	}

}
