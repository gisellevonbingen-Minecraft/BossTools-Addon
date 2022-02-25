package boss_tools_giselle_addon.common.capability;

import java.util.Collections;
import java.util.function.Function;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.ItemHandlerHelper3;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

public enum ChargeMode implements IChargeMode
{
	NONE(BossToolsAddon.rl("none"), e -> Collections.emptyList()),
	ARMORS(BossToolsAddon.rl("armors"), Entity::getArmorSlots),
	ALL(BossToolsAddon.rl("all"), e -> e.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).lazyMap(ItemHandlerHelper3::getStacks).orElse(NonNullList.withSize(0, ItemStack.EMPTY))),
	//
	;

	private final ResourceLocation name;
	private final Function<Entity, Iterable<ItemStack>> function;
	private final ITextComponent displayName;

	private ChargeMode(ResourceLocation name, Function<Entity, Iterable<ItemStack>> function)
	{
		this.name = name;
		this.function = function;
		this.displayName = IChargeMode.createDisplayName(name);
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

	@Override
	public ITextComponent getDisplayName()
	{
		return this.displayName;
	}

}
