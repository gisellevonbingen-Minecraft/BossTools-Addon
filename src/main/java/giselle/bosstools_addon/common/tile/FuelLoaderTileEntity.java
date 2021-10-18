package giselle.bosstools_addon.common.tile;

import java.util.List;

import javax.annotation.Nullable;

import giselle.bosstools_addon.common.fluid.FluidUtil2;
import giselle.bosstools_addon.common.inventory.InventoryHelper2;
import giselle.bosstools_addon.common.inventory.container.FuelLoaderContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.mrscauthd.boss_tools.block.FuelBlock;
import net.mrscauthd.boss_tools.entity.RoverEntity;

public class FuelLoaderTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider
{
	public static final int SLOT_FLUID_SOURCE = 0;
	public static final int SLOT_FLUID_SINK = 1;

	private IFluidHandler fluidTank;
	private Inventory fluidInventory;
	private IInventory inputInventory;
	private IInventory outputInventory;
	private IItemHandlerModifiable itemHandler;

	public FuelLoaderTileEntity()
	{
		super(AddonTiles.FUEL_LOADER.get());

		this.fluidTank = new FluidTank(8000, fs -> fs.getFluid().isSame(this.getFluid()))
		{
			@Override
			protected void onContentsChanged()
			{
				super.onContentsChanged();
				onContentChanged();
			}
		};
		this.inputInventory = new Inventory(6)
		{
			@Override
			public void setChanged()
			{
				super.setChanged();
				onContentChanged();
			}
		};
		this.outputInventory = new Inventory(6)
		{
			@Override
			public void setChanged()
			{
				super.setChanged();
				onContentChanged();
			}
		};
		this.fluidInventory = new Inventory(2)
		{
			@Override
			public void setChanged()
			{
				super.setChanged();
				onContentChanged();
			}

			@Override
			public int getMaxStackSize()
			{
				return 1;
			}
		};
		InvWrapper fluidInventoryWrapper = new InvWrapper(this.getFluidInventory())
		{
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
			{
				if (slot == getSlotFluidSource() && FluidUtil2.canDrain(stack, getFluid()) == true)
				{
					return super.insertItem(slot, stack, simulate);
				}
				else if (slot == getSlotFluidSink() && FluidUtil2.canFill(stack, getFluid()) == true)
				{
					return super.insertItem(slot, stack, simulate);
				}

				return stack;
			}

			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate)
			{
				ItemStack stack = this.getStackInSlot(slot);

				if (slot == getSlotFluidSource() && FluidUtil2.canDrain(stack, getFluid()) == false)
				{
					return super.extractItem(slot, amount, simulate);
				}
				else if (slot == getSlotFluidSink() && FluidUtil2.canFill(stack, getFluid()) == false)
				{
					return super.extractItem(slot, amount, simulate);
				}

				return ItemStack.EMPTY;
			}

		};
		this.itemHandler = new CombinedInvWrapper(fluidInventoryWrapper, new InvWrapper(this.getInputInventory())
		{
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate)
			{
				return ItemStack.EMPTY;
			}
		}, new InvWrapper(this.getOutputInventory())
		{
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
			{
				return stack;
			}
		});
	}

	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		return new SUpdateTileEntityPacket(this.getBlockPos(), 0, this.getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag()
	{
		return this.save(new CompoundNBT());
	}

	@Override
	public final void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
	{
		this.load(this.getBlockState(), packet.getTag());
	}
    
	protected void onContentChanged()
	{
		this.setChanged();
		
		AbstractChunkProvider chunkSource = this.getLevel().getChunkSource();

		if (chunkSource instanceof ServerChunkProvider)
		{
			((ServerChunkProvider) chunkSource).blockChanged(this.getBlockPos());
		}

	}

	public void openGui(ServerPlayerEntity entity)
	{
		NetworkHooks.openGui(entity, this, this.getBlockPos());
	}

	@Override
	@Nullable
	public Container createMenu(int windowId, PlayerInventory inv, PlayerEntity player)
	{
		return new FuelLoaderContainer(windowId, inv, this);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("container.boss_tools_addon.fuel_loader");
	}

	@Override
	public void load(BlockState blockState, CompoundNBT compound)
	{
		super.load(blockState, compound);

		this.readMetadata(compound);
	}

	@Override
	public CompoundNBT save(CompoundNBT compound)
	{
		super.save(compound);

		this.saveMetadata(compound);

		return compound;
	}

	protected void readMetadata(CompoundNBT compound)
	{
		CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.readNBT(this.getFluidTank(), null, compound.getCompound("fluidTank"));
		InventoryHelper2.load(this.getFluidInventory(), compound.getCompound("fluidInventory"));
		InventoryHelper2.load(this.getInputInventory(), compound.getCompound("inputInventory"));
		InventoryHelper2.load(this.getOutputInventory(), compound.getCompound("outputInventory"));
	}

	protected void saveMetadata(CompoundNBT compound)
	{
		compound.put("fluidTank", CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.writeNBT(this.getFluidTank(), null));
		compound.put("fluidInventory", InventoryHelper2.save(this.getFluidInventory()));
		compound.put("inputInventory", InventoryHelper2.save(this.getInputInventory()));
		compound.put("outputInventory", InventoryHelper2.save(this.getOutputInventory()));
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return LazyOptional.of(this::getFluidTank).cast();
		}
		else if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return LazyOptional.of(this::getItemHandler).cast();
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void tick()
	{
		this.processTank();
		this.giveFuelAround();
	}

	public void processTank()
	{
		IItemHandlerModifiable itemHandler = new InvWrapper(this.getFluidInventory());
		int transferPerTick = this.getTransferPerTick();
		IFluidHandler fluidTank = this.getFluidTank();
		FluidUtil2.drainSource(itemHandler, this.getSlotFluidSource(), fluidTank, transferPerTick);
		FluidUtil2.fillSink(itemHandler, this.getSlotFluidSink(), fluidTank, transferPerTick);
	}

	public int getTransferPerTick()
	{
		return 256;
	}

	public boolean giveFuelAround()
	{
		World level = this.getLevel();
		double workingLength = this.getWorkingLength();
		AxisAlignedBB workingArea = new AxisAlignedBB(this.getBlockPos()).inflate(workingLength, 0.0D, workingLength);
		List<Entity> entities = level.getEntities(null, workingArea);
		boolean worked = false;

		for (Entity entity : entities)
		{
			FuelEntityAdapter adapter = this.createAdapter(entity);

			if (this.giveFuel(adapter) == true)
			{
				worked = true;
			}

		}

		return worked;
	}

	public double getWorkingLength()
	{
		return 2.0D;
	}

	public FuelEntityAdapter createAdapter(Entity entity)
	{
		FuelEntityAdapter adapter = null;

		if (entity instanceof RoverEntity.CustomEntity)
		{
			adapter = new FuelEntityAdapterRover((RoverEntity.CustomEntity) entity);
		}

		return adapter;
	}

	public boolean giveFuel(FuelEntityAdapter adapter)
	{
		if (adapter == null || adapter.canInsertFuel() == false)
		{
			return false;
		}

		IItemHandler itemHandler = adapter.getEntity().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);

		if (itemHandler instanceof IItemHandlerModifiable)
		{
			int fuelSlot = adapter.getFuelSlot();
			itemHandler.getStackInSlot(fuelSlot);
		}

		return false;
	}

	public Fluid getFluid()
	{
		return FuelBlock.still;
	}

	public IFluidHandler getFluidTank()
	{
		return this.fluidTank;
	}

	public Inventory getFluidInventory()
	{
		return this.fluidInventory;
	}

	public int getSlotFluidSource()
	{
		return SLOT_FLUID_SOURCE;
	}

	public int getSlotFluidSink()
	{
		return SLOT_FLUID_SINK;
	}

	public IInventory getInputInventory()
	{
		return this.inputInventory;
	}

	public IInventory getOutputInventory()
	{
		return this.outputInventory;
	}

	public IItemHandlerModifiable getItemHandler()
	{
		return this.itemHandler;
	}

}
