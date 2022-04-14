package beyond_earth_giselle_addon.common.block.entity;

import java.util.List;

import javax.annotation.Nullable;

import beyond_earth_giselle_addon.common.adapter.FuelAdapter;
import beyond_earth_giselle_addon.common.adapter.FuelAdapterCreateEntityEvent;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.fluid.FluidUtil3;
import beyond_earth_giselle_addon.common.inventory.FuelLoaderContainerMenu;
import beyond_earth_giselle_addon.common.registries.AddonBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.mrscauthd.beyond_earth.events.Methods;
import net.mrscauthd.beyond_earth.fluids.FluidUtil2;
import net.mrscauthd.beyond_earth.machines.tile.AbstractMachineBlockEntity;
import net.mrscauthd.beyond_earth.machines.tile.NamedComponentRegistry;
import net.mrscauthd.beyond_earth.registries.TagsRegistry;

public class FuelLoaderBlockEntity extends AbstractMachineBlockEntity
{
	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";

	public static final int SLOTS_FLUID = 2;
	public static final int SLOT_FLUID_SOURCE = 0;
	public static final int SLOT_FLUID_SINK = 1;

	private FluidTank fluidTank;
	private IItemHandlerModifiable fluidItemHandler;

	public FuelLoaderBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntityTypes.FUEL_LOADER.get(), pos, state);
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
	}

	@Override
	protected int getInitialInventorySize()
	{
		return super.getInitialInventorySize() + this.getSlotsFluid();
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
				FuelLoaderBlockEntity.this.setChanged();
			}

		});
	}

	@Override
	protected void getSlotsForFace(Direction direction, List<Integer> slots)
	{
		super.getSlotsForFace(direction, slots);
		slots.add(this.getSlotFluidSource());
	}

	@Override
	protected boolean onCanPlaceItemThroughFace(int index, ItemStack stack, Direction direction)
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

		return super.onCanPlaceItemThroughFace(index, stack, direction);
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

		return super.canTakeItemThroughFace(index, stack, direction);
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int windowId, Inventory inv)
	{
		return new FuelLoaderContainerMenu(windowId, inv, this);
	}

	@Override
	protected void tickProcessing()
	{
		this.processTank();
		boolean worked = this.exchangeFuelItemAround();

		if (worked == true)
		{
			this.setProcessedInThisTick();
		}

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
		Level level = this.getLevel();
		AABB workingArea = this.getWorkingArea();
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
	public AABB getRenderBoundingBox()
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

	public AABB getWorkingArea()
	{
		return this.getWorkingArea(this.getWorkingRange());
	}

	public AABB getWorkingArea(double range)
	{
		return this.getWorkingArea(this.getBlockPos(), range);
	}

	public AABB getWorkingArea(BlockPos pos, double range)
	{
		double half = range / 2.0D;
		return new AABB(pos).inflate(range, half, range).move(0.0D, half, 0.0D);
	}

	public boolean exchangeFuelItem(FuelAdapter<? extends Entity> adapter)
	{
		if (adapter == null)
		{
			return false;
		}

		FluidTank fluidTank = this.getFluidTank();
		FluidStack draining = fluidTank.getFluid();

		if (draining.isEmpty() == true)
		{
			return false;
		}

		int filling = adapter.fill(draining.getAmount(), FluidAction.SIMULATE);

		if (filling <= 0)
		{
			return false;
		}

		fluidTank.drain(filling, FluidAction.EXECUTE);
		adapter.fill(filling, FluidAction.EXECUTE);

		return true;
	}

	public boolean testFluidStack(FluidStack fluidStack)
	{
		return this.testFluid(fluidStack.getFluid());
	}

	public boolean testFluid(Fluid fluid)
	{
		return Methods.tagCheck(fluid, TagsRegistry.FLUID_VEHICLE_FUEL_TAG);
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

	public IItemHandlerModifiable getFluidItemHandler()
	{
		return this.fluidItemHandler;
	}

}
