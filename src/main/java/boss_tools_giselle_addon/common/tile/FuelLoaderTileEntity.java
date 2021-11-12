package boss_tools_giselle_addon.common.tile;

import java.util.List;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.adapter.FuelAdapter;
import boss_tools_giselle_addon.common.adapter.FuelAdapterCreateEntityEvent;
import boss_tools_giselle_addon.common.inventory.ItemHandlerHelper2;
import boss_tools_giselle_addon.common.inventory.container.FuelLoaderContainer;
import boss_tools_giselle_addon.config.AddonConfigs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;

public class FuelLoaderTileEntity extends AbstractMachineTileEntity
{
	public static final int SLOTS_FLUID = 2;
	public static final int SLOT_FLUID_SOURCE = 0;
	public static final int SLOT_FLUID_SINK = 1;

	public static final int SLOTS_INPUT = 6;
	public static final int SLOTS_OUTPUT = 6;

	private FluidTank fluidTank;
	private IItemHandlerModifiable fluidItemHandler;
	private IItemHandlerModifiable inputItemHandler;
	private IItemHandlerModifiable outputItemHandler;

	public FuelLoaderTileEntity()
	{
		super(AddonTiles.FUEL_LOADER.get());
	}

	@Override
	public int getMaxStackSize()
	{
		return 1;
	}

	@Override
	public boolean hasSpaceInOutput()
	{
		return true;
	}

	@Override
	protected void createItemHandlers()
	{
		super.createItemHandlers();

		IItemHandlerModifiable itemHandler = this.getItemHandler();
		this.fluidItemHandler = new RangedWrapper(itemHandler, this.getSlotFluidStart(), this.getSlotFluidEnd());
		this.inputItemHandler = new RangedWrapper(itemHandler, this.getSlotInputStart(), this.getSlotInputEnd());
		this.outputItemHandler = new RangedWrapper(itemHandler, this.getSlotOutputStart(), this.getSlotOutputEnd());
	}

	@Override
	protected int getInitialInventorySize()
	{
		return super.getInitialInventorySize() + this.getSlotsFluid() + this.getSlotsInput() + this.getSlotsOutput();
	}

	@Override
	protected void createFluidHandlers(NamedComponentRegistry<IFluidHandler> registry)
	{
		super.createFluidHandlers(registry);

		registry.put(this.fluidTank = new FluidTank(AddonConfigs.Common.machines.fuelLoader_capacity.get(), fs -> FluidUtil2.isEquivalentTo(fs, this.getFluid()))
		{
			@Override
			protected void onContentsChanged()
			{
				super.onContentsChanged();
				FuelLoaderTileEntity.this.setChanged();
			}

			@Override
			public FluidStack drain(int maxDrain, FluidAction action)
			{
				return super.drain(Math.max(getTransferPerTick(), maxDrain), action);
			}

			@Override
			public int fill(FluidStack resource, FluidAction action)
			{
				FluidStack copy = resource.copy();
				copy.setAmount(Math.max(getTransferPerTick(), copy.getAmount()));
				return super.fill(copy, action);
			}

		});
	}

	@Override
	protected void getSlotsForFace(Direction direction, List<Integer> slots)
	{
		super.getSlotsForFace(direction, slots);
		slots.add(this.getSlotFluidSource());

		for (int i = this.getSlotInputStart(); i < this.getSlotInputEnd(); i++)
		{
			slots.add(i);
		}

		for (int i = this.getSlotOutputStart(); i < this.getSlotOutputEnd(); i++)
		{
			slots.add(i);
		}

	}

	@Override
	public boolean onCanInsertItem(int index, ItemStack stack, @Nullable Direction direction)
	{
		if (index == getSlotFluidSource())
		{
			return FluidUtil2.canDrain(stack, this.getFluid()) == true;
		}
		else if (index == getSlotFluidSink())
		{
			return FluidUtil2.canFill(stack, this.getFluid()) == true;
		}
		else if (this.getSlotInputStart() <= index && index < this.getSlotInputEnd())
		{
			return true;
		}
		else if (this.getSlotOutputStart() <= index && index < this.getSlotOutputEnd())
		{
			return direction == null;
		}

		return super.onCanInsertItem(index, stack, direction);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction)
	{
		if (index == getSlotFluidSource())
		{
			return FluidUtil2.canDrain(stack, getFluid()) == false;
		}
		else if (index == getSlotFluidSink())
		{
			return FluidUtil2.canFill(stack, this.getFluid()) == false;
		}
		else if (this.getSlotInputStart() <= index && index < this.getSlotInputEnd())
		{
			return direction == null;
		}
		else if (this.getSlotOutputStart() <= index && index < this.getSlotOutputEnd())
		{
			return true;
		}

		return super.canTakeItemThroughFace(index, stack, direction);
	}

	@Override
	@Nullable
	public Container createMenu(int windowId, PlayerInventory inv)
	{
		return new FuelLoaderContainer(windowId, inv, this);
	}

	@Override
	protected void tickProcessing()
	{
		this.processTank();
		this.exchangeFuelItemAround();
	}

	public void processTank()
	{
		IItemHandlerModifiable itemHandler = this.getItemHandler();
		int transferPerTick = this.getTransferPerTick();
		IFluidHandler fluidTank = this.getFluidTank();
		FluidUtil2.drainSource(itemHandler, this.getSlotFluidSource(), fluidTank, transferPerTick);
		FluidUtil2.fillSink(itemHandler, this.getSlotFluidSink(), fluidTank, transferPerTick);
	}

	public int getTransferPerTick()
	{
		return AddonConfigs.Common.machines.fuelLoader_transfer.get();
	}

	public boolean exchangeFuelItemAround()
	{
		World level = this.getLevel();
		double workingRange = this.getWorkingRange();
		AxisAlignedBB workingArea = new AxisAlignedBB(this.getBlockPos()).inflate(workingRange, 0.0D, workingRange);
		List<Entity> entities = level.getEntities(null, workingArea);
		boolean worked = false;

		for (Entity entity : entities)
		{
			FuelAdapter<? extends Entity> adapter = new FuelAdapterCreateEntityEvent(entity).resolve();

			if (this.exchangeFuelItem(adapter) == true)
			{
				worked = true;
			}

		}

		return worked;
	}

	public double getWorkingRange()
	{
		return AddonConfigs.Common.machines.fuelLoader_range.get();
	}

	public boolean exchangeFuelItem(FuelAdapter<? extends Entity> adapter)
	{
		if (adapter == null)
		{
			return false;
		}

		IItemHandler _itemHandler = adapter.getTarget().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);

		if (_itemHandler instanceof IItemHandlerModifiable)
		{
			IItemHandlerModifiable itemHandler = (IItemHandlerModifiable) _itemHandler;
			int fuelSlot = adapter.getFuelSlot();
			this.takeEmptyItem(adapter, itemHandler, fuelSlot);

			if (adapter.canInsertFuel() == true)
			{
				this.giveFullitem(adapter, itemHandler, fuelSlot);
			}

		}

		return false;
	}

	public void giveFullitem(FuelAdapter<? extends Entity> adapter, IItemHandlerModifiable itemHandler, int fuelSlot)
	{
		ItemStack stackInSlot = itemHandler.getStackInSlot(fuelSlot);

		if (stackInSlot.isEmpty() == true)
		{
			Fluid fluid = this.getFluid();
			ItemStack fuelFullItem = new ItemStack(adapter.getFuelFullItem());
			ItemStack fuelEmptyItem = FluidUtil2.makeEmpty(fuelFullItem, fluid);
			FluidStack fluidStack = new FluidStack(fluid, FluidUtil2.getMaxCapacity(fuelEmptyItem));
			IFluidHandler fluidTank = this.getFluidTank();

			IItemHandlerModifiable inputInventory = this.getInputItemHandler();
			int inputSlot = ItemHandlerHelper2.indexOf(inputInventory, fuelEmptyItem.getItem());

			if (inputSlot > -1 && fluidTank.drain(fluidStack, FluidAction.SIMULATE).getAmount() == fluidStack.getAmount())
			{
				inputInventory.extractItem(inputSlot, 1, false);
				itemHandler.setStackInSlot(fuelSlot, fuelFullItem);
				fluidTank.drain(fluidStack, FluidAction.EXECUTE);
			}

		}

	}

	public void takeEmptyItem(FuelAdapter<? extends Entity> adapter, IItemHandlerModifiable itemHandler, int fuelSlot)
	{
		ItemStack stackInSlot = itemHandler.getStackInSlot(fuelSlot);

		if (FluidUtil2.canDrain(stackInSlot, this.getFluid()) == false)
		{
			ItemStack remain = ItemHandlerHelper.insertItemStacked(this.getOutputItemHandler(), stackInSlot, false);
			itemHandler.setStackInSlot(fuelSlot, remain);
		}

	}

	public Fluid getFluid()
	{
		return ModInnet.FUEL_STILL.get();
	}

	public FluidTank getFluidTank()
	{
		return this.fluidTank;
	}

	public int getSlotFluidStart()
	{
		return 0;
	}

	public int getSlotFluidEnd()
	{
		return this.getSlotFluidStart() + this.getSlotsFluid();
	}

	public int getSlotsFluid()
	{
		return SLOTS_FLUID;
	}

	public int getSlotFluidSource()
	{
		return this.getSlotFluidStart() + SLOT_FLUID_SOURCE;
	}

	public int getSlotFluidSink()
	{
		return this.getSlotFluidStart() + SLOT_FLUID_SINK;
	}

	public int getSlotInputStart()
	{
		return this.getSlotFluidEnd();
	}

	public int getSlotInputEnd()
	{
		return this.getSlotInputStart() + this.getSlotsInput();
	}

	public int getSlotsInput()
	{
		return SLOTS_INPUT;
	}

	public int getSlotOutputStart()
	{
		return this.getSlotInputEnd();
	}

	public int getSlotOutputEnd()
	{
		return this.getSlotOutputStart() + this.getSlotsOutput();
	}

	public int getSlotsOutput()
	{
		return SLOTS_OUTPUT;
	}

	public IItemHandlerModifiable getFluidItemHandler()
	{
		return this.fluidItemHandler;
	}

	public IItemHandlerModifiable getInputItemHandler()
	{
		return this.inputItemHandler;
	}

	public IItemHandlerModifiable getOutputItemHandler()
	{
		return this.outputItemHandler;
	}

}
