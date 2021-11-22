package boss_tools_giselle_addon.common.tile;

import java.util.List;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.adapter.FuelAdapter;
import boss_tools_giselle_addon.common.adapter.FuelAdapterCreateEntityEvent;
import boss_tools_giselle_addon.common.fluid.FluidUtil3;
import boss_tools_giselle_addon.common.inventory.ItemHandlerHelper2;
import boss_tools_giselle_addon.common.inventory.container.FuelLoaderContainer;
import boss_tools_giselle_addon.config.AddonConfigs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;

public class FuelLoaderTileEntity extends AbstractMachineTileEntity
{
	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";

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

		registry.put(this.fluidTank = new FluidTank(AddonConfigs.Common.machines.fuelLoader_capacity.get(), this::testFluidStack)
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
			return FluidUtil3.canDrainStack(stack, this::testFluidStack) == true;
		}
		else if (index == getSlotFluidSink())
		{
			FluidStack fluidStack = this.getFluidTank().getFluid();
			return FluidUtil2.canFill(stack, fluidStack.getFluid()) == true;
		}
		else if (this.getSlotInputStart() <= index && index < this.getSlotInputEnd())
		{
			return stack.getItem() == Items.BUCKET;
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
			return FluidUtil3.canDrainStack(stack, this::testFluidStack) == false;
		}
		else if (index == getSlotFluidSink())
		{
			FluidStack fluidStack = this.getFluidTank().getFluid();
			return FluidUtil2.canFill(stack, fluidStack.getFluid()) == false;
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
		AxisAlignedBB workingArea = this.getWorkingArea();
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

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return this.getWorkingArea();
	}

	public boolean isWorkingAreaVisible()
	{
		return this.getTileData().getBoolean(DATA_WORKINGAREA_VISIBLE_KEY);
	}

	public void setWorkingAreaVisible(boolean visible)
	{
		if (this.isWorkingAreaVisible() != visible)
		{
			this.getTileData().putBoolean(DATA_WORKINGAREA_VISIBLE_KEY, visible);
			this.setChanged();
		}

	}

	public int getWorkingRange()
	{
		return AddonConfigs.Common.machines.fuelLoader_range.get();
	}

	public AxisAlignedBB getWorkingArea()
	{
		return this.getWorkingArea(this.getWorkingRange());
	}

	public AxisAlignedBB getWorkingArea(double range)
	{
		return this.getWorkingArea(this.getBlockPos(), range);
	}

	public AxisAlignedBB getWorkingArea(BlockPos pos, double range)
	{
		double half = range / 2.0D;
		return new AxisAlignedBB(pos).inflate(range, half, range).move(0.0D, half, 0.0D);
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

		if (stackInSlot.isEmpty() == false)
		{
			return;
		}

		IFluidHandler fluidTank = this.getFluidTank();

		for (int i = 0; i < fluidTank.getTanks(); i++)
		{
			FluidStack tankFluidStack = fluidTank.getFluidInTank(i);

			if (tankFluidStack.isEmpty() == true)
			{
				continue;
			}

			Fluid tankFluid = tankFluidStack.getFluid();
			FluidStack fluidStack = new FluidStack(tankFluid, FluidUtil2.BUCKET_SIZE);
			Item bucket = Items.BUCKET;

			IItemHandlerModifiable inputInventory = this.getInputItemHandler();
			int inputSlot = ItemHandlerHelper2.indexOf(inputInventory, bucket);

			if (inputSlot > -1 && fluidTank.drain(fluidStack, FluidAction.SIMULATE).getAmount() == fluidStack.getAmount())
			{
				inputInventory.extractItem(inputSlot, 1, false);
				itemHandler.setStackInSlot(fuelSlot, new ItemStack(tankFluid.getBucket()));
				fluidTank.drain(fluidStack, FluidAction.EXECUTE);
				break;
			}

		}

	}

	public void takeEmptyItem(FuelAdapter<? extends Entity> adapter, IItemHandlerModifiable itemHandler, int fuelSlot)
	{
		ItemStack stackInSlot = itemHandler.getStackInSlot(fuelSlot);

		if (FluidUtil3.canDrainStack(stackInSlot, this::testFluidStack) == false)
		{
			ItemStack remain = ItemHandlerHelper.insertItemStacked(this.getOutputItemHandler(), stackInSlot, false);
			itemHandler.setStackInSlot(fuelSlot, remain);
		}

	}

	public boolean testFluidStack(FluidStack fluidStack)
	{
		return this.testFluid(fluidStack.getFluid());
	}

	public boolean testFluid(Fluid fluid)
	{
		return Methodes.tagCheck(fluid, ModInnet.FLUID_VEHICLE_FUEL_TAG);
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
