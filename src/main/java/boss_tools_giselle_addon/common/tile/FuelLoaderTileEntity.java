package boss_tools_giselle_addon.common.tile;

import java.util.List;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.adapter.FuelAdapter;
import boss_tools_giselle_addon.common.adapter.FuelAdapterCreateEntityEvent;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.fluid.FluidUtil3;
import boss_tools_giselle_addon.common.inventory.container.FuelLoaderContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
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

	private FluidTank fluidTank;
	private IItemHandlerModifiable fluidItemHandler;

	public FuelLoaderTileEntity()
	{
		super(AddonTileEntitTypes.FUEL_LOADER.get());
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
				FuelLoaderTileEntity.this.setChanged();
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

	public IItemHandlerModifiable getFluidItemHandler()
	{
		return this.fluidItemHandler;
	}

}
