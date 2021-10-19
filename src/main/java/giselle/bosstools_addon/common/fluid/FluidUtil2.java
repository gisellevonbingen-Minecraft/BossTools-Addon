package giselle.bosstools_addon.common.fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.util.TriPredicate;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidUtil2
{
	public static final int BUCKET_SIZE = 1000;
	private static final Map<Item, Fluid> fluidCacheds = new HashMap<>();
	private static final List<FluidContainerRegistration> fluidContainers = new ArrayList<FluidContainerRegistration>();

	public static void registerFluidContainer(FluidContainerRegistration registration)
	{
		fluidContainers.add(registration);
	}

	public static FluidContainerRegistration findFluidContainer(Item item, Fluid fluid, TriPredicate<FluidContainerRegistration, Item, Fluid> predicate)
	{
		Optional<FluidContainerRegistration> optional = fluidContainers.stream().filter(r -> predicate.test(r, item, fluid)).findFirst();
		return optional.orElse(null);
	}

	public static Fluid findFluidInItem(Item item)
	{
		if (item == Items.AIR)
		{
			return null;
		}

		FluidContainerRegistration registration = findFluidContainer(item, null, FluidContainerRegistration::testFull);

		if (registration != null)
		{
			return registration.getFluid();
		}
		else
		{
			return fluidCacheds.computeIfAbsent(item, FluidUtil2::findFluidInBucket);
		}

	}

	public static Fluid findFluidInBucket(Item item)
	{
		return ForgeRegistries.FLUIDS.getValues().stream().filter(f -> f.isSource(null) && f.getBucket() == item).findFirst().orElse(null);
	}

	public static boolean isEquivalentTo(FluidStack left, Fluid right)
	{
		if (left.isEmpty() == true)
		{
			return right == Fluids.EMPTY;
		}
		else if (right == Fluids.EMPTY)
		{
			return false;
		}

		return left.getFluid().isSame(right);
	}

	public static boolean isEquivalentTo(FluidStack left, FluidStack right)
	{
		if (left.isEmpty() == true)
		{
			return right.isEmpty();
		}
		else if (right.isEmpty() == true)
		{
			return false;
		}

		return left.getFluid().isSame(right.getFluid()) && FluidStack.areFluidStackTagsEqual(left, right);
	}

	@Nullable
	public static Fluid getFluid(FluidStack stack)
	{
		return Optional.ofNullable(stack).map(fs -> fs.getFluid()).orElse(null);
	}

	public static IFluidHandlerItem getItemStackFluidHandler(ItemStack itemStack)
	{
		return itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
	}

	/**
	 * test insert fluid to itemstack
	 * 
	 * @param itemStack
	 * @param fluid
	 * @return
	 */
	public static boolean canFill(ItemStack itemStack, Fluid fluid)
	{
		if (itemStack.isEmpty() == true || fluid == null)
		{
			return false;
		}

		Item item = itemStack.getItem();
		if (item == Items.BUCKET)
		{
			return fluid.getBucket() != Items.AIR;
		}

		FluidContainerRegistration registration = findFluidContainer(item, fluid, FluidContainerRegistration::testEmpty);
		if (registration != null)
		{
			return true;
		}

		IFluidHandlerItem handlerInItemStack = getItemStackFluidHandler(itemStack);
		if (handlerInItemStack != null && handlerInItemStack.fill(new FluidStack(fluid, 1), FluidAction.SIMULATE) > 0)
		{
			return true;
		}

		return false;
	}

	/**
	 * test drain fluid from itemstack
	 * 
	 * @param itemStack
	 * @param fluid
	 * @return
	 */
	public static boolean canDrain(ItemStack itemStack, Fluid fluid)
	{
		if (itemStack.isEmpty() == true || fluid == null)
		{
			return false;
		}

		Item item = itemStack.getItem();
		if (item == fluid.getBucket())
		{
			return true;
		}

		FluidContainerRegistration registration = findFluidContainer(item, fluid, FluidContainerRegistration::testFull);
		if (registration != null)
		{
			return true;
		}

		IFluidHandlerItem handlerInItemStack = getItemStackFluidHandler(itemStack);
		if (handlerInItemStack != null && !handlerInItemStack.drain(new FluidStack(fluid, 1), FluidAction.SIMULATE).isEmpty())
		{
			return true;
		}

		return false;
	}

	public static int getMaxCapacity(ItemStack itemStack)
	{
		if (itemStack.isEmpty() == true)
		{
			return 0;
		}

		Item item = itemStack.getItem();
		if (item == Items.BUCKET)
		{
			return BUCKET_SIZE;
		}

		FluidContainerRegistration registration = findFluidContainer(item, null, FluidContainerRegistration::testEmpty);
		if (registration != null)
		{
			return registration.getCapacity();
		}

		IFluidHandlerItem handlerInItemStack = getItemStackFluidHandler(itemStack);

		if (handlerInItemStack != null)
		{
			return getMaxCapacity(handlerInItemStack);
		}

		return 0;
	}

	public static int getMaxCapacity(IFluidHandler fluidHandler)
	{
		int capacity = 0;

		for (int i = 0; i < fluidHandler.getTanks(); i++)
		{
			capacity = Math.max(capacity, fluidHandler.getTankCapacity(i));
		}

		return capacity;
	}

	public static ItemStack makeEmpty(ItemStack itemStack, Fluid fluid)
	{
		if (itemStack.isEmpty() == true)
		{
			return itemStack;
		}

		Item item = itemStack.getItem();
		if (item == fluid.getBucket())
		{
			return new ItemStack(Items.BUCKET);
		}

		FluidContainerRegistration registration = findFluidContainer(item, fluid, FluidContainerRegistration::testFull);
		if (registration != null)
		{
			return new ItemStack(registration.getEmtpy());
		}

		IFluidHandlerItem handlerInItemStack = getItemStackFluidHandler(itemStack);
		if (handlerInItemStack != null)
		{
			FluidStack fluidStack = new FluidStack(fluid, getMaxCapacity(handlerInItemStack));
			handlerInItemStack.drain(fluidStack, FluidAction.EXECUTE);
		}

		return itemStack;
	}

	public static ItemStack makeFull(ItemStack itemStack, Fluid fluid)
	{
		if (itemStack.isEmpty() == true)
		{
			return itemStack;
		}

		Item item = itemStack.getItem();
		if (item == Items.BUCKET && fluid.getBucket() != null)
		{
			return new ItemStack(fluid.getBucket());
		}

		FluidContainerRegistration registration = findFluidContainer(item, fluid, FluidContainerRegistration::testEmpty);
		if (registration != null)
		{
			return new ItemStack(registration.getFull());
		}

		IFluidHandlerItem handlerInItemStack = getItemStackFluidHandler(itemStack);
		if (handlerInItemStack != null)
		{
			FluidStack fluidStack = new FluidStack(fluid, getMaxCapacity(handlerInItemStack));
			handlerInItemStack.fill(fluidStack, FluidAction.EXECUTE);
		}

		return itemStack;
	}

	public static List<FluidStack> getFluidStacks(ItemStack itemStack)
	{
		List<FluidStack> fluidStacks = new ArrayList<>();

		if (itemStack.isEmpty() == true)
		{
			return fluidStacks;
		}

		Item item = itemStack.getItem();

		if (item == Items.BUCKET)
		{
			fluidStacks.add(new FluidStack(Fluids.EMPTY, 0));
		}
		else
		{
			IFluidHandlerItem handlerInItemStack = getItemStackFluidHandler(itemStack);
			if (handlerInItemStack != null)
			{
				for (int i = 0; i < handlerInItemStack.getTanks(); i++)
				{
					fluidStacks.add(handlerInItemStack.getFluidInTank(i));
				}

			}
			else
			{
				Fluid fluid = findFluidInItem(item);
				if (fluid != null)
				{
					int maxCapacity = getMaxCapacity(itemStack);
					fluidStacks.add(new FluidStack(fluid, maxCapacity));
				}

			}

		}

		return fluidStacks;
	}

	public static boolean fillSink(IItemHandlerModifiable itemHandler, int sinkItemSlot, IFluidHandler source, int transfer)
	{
		ItemStack sinkItemStack = itemHandler.getStackInSlot(sinkItemSlot);

		if (fillSinkItem(itemHandler, sinkItemSlot, source, sinkItemStack))
		{
			return true;
		}
		else if (!fillSinkCapability(source, sinkItemStack, transfer).isEmpty())
		{
			return true;
		}

		return false;
	}

	public static boolean fillSinkItem(IItemHandlerModifiable itemHandler, int sinkItemSlot, IFluidHandler source, ItemStack itemStack)
	{
		int capacity = getMaxCapacity(itemStack);

		if (capacity > 0)
		{
			FluidStack fluidStack = source.drain(capacity, FluidAction.SIMULATE);

			if (fluidStack.getAmount() == capacity)
			{
				source.drain(capacity, FluidAction.EXECUTE);
				itemHandler.setStackInSlot(sinkItemSlot, makeFull(itemStack, fluidStack.getFluid()));
				return true;
			}

		}

		return false;
	}

	public static FluidStack fillSinkCapability(IFluidHandler source, ItemStack sinkItemStack, int transfer)
	{
		IFluidHandlerItem sink = getItemStackFluidHandler(sinkItemStack);
		return tryTransfer(sink, source, transfer);
	}

	public static boolean drainSource(IItemHandlerModifiable itemHandler, int sourceItemSlot, IFluidHandler sink, int transfer)
	{
		ItemStack sourceItemStack = itemHandler.getStackInSlot(sourceItemSlot);

		if (drainSourceItem(itemHandler, sourceItemSlot, sink, sourceItemStack))
		{
			return true;
		}
		else if (!drainSourceCapability(sink, sourceItemStack, transfer).isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	public static boolean drainSourceItem(IItemHandlerModifiable itemHandler, int itemSlot, IFluidHandler sink, ItemStack sourceItemStack)
	{
		Fluid sourceFluid = FluidUtil2.findFluidInItem(sourceItemStack.getItem());

		if (sourceFluid != null)
		{
			int capacity = FluidUtil2.getMaxCapacity(sourceItemStack);
			FluidStack fluidStack = new FluidStack(sourceFluid, capacity);

			if (sink.fill(fluidStack, FluidAction.SIMULATE) == capacity)
			{
				sink.fill(fluidStack, FluidAction.EXECUTE);
				itemHandler.setStackInSlot(itemSlot, FluidUtil2.makeEmpty(sourceItemStack, fluidStack.getFluid()));
				return true;
			}

		}

		return false;
	}

	public static FluidStack drainSourceCapability(IFluidHandler sink, ItemStack sourceItemStack, int transfer)
	{
		IFluidHandlerItem source = getItemStackFluidHandler(sourceItemStack);
		return tryTransfer(sink, source, transfer);
	}

	public static FluidStack tryTransfer(IFluidHandler sink, IFluidHandler source, int transfer)
	{
		if (sink != null && source != null && transfer > 0)
		{
			return FluidUtil.tryFluidTransfer(sink, source, transfer, true);
		}
		else
		{
			return FluidStack.EMPTY;
		}

	}

	private FluidUtil2()
	{

	}

}
