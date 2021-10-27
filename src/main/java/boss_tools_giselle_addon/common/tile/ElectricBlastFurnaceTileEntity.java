package boss_tools_giselle_addon.common.tile;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.block.ElectricBlastFurnaceBlock;
import boss_tools_giselle_addon.common.capability.EnergyStorageBasic;
import boss_tools_giselle_addon.common.capability.IEnergyStorageHolder;
import boss_tools_giselle_addon.common.inventory.ItemHandlerHelper2;
import boss_tools_giselle_addon.common.inventory.StackCacher;
import boss_tools_giselle_addon.common.inventory.container.ElectricBlastFurnaceContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.mrscauthd.boss_tools.item.SteahlItem;

public class ElectricBlastFurnaceTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IEnergyStorageHolder
{
	private EnergyStorageBasic energyStorage;
	private ItemStackHandler inputInventory;
	private ItemStackHandler outputInventory;
	private CombinedInvWrapper itemHandler;

	private int timer;
	private int maxTimer;

	private StackCacher itemStackCacher;
	private ItemStack cachedRecipe;
	private boolean processedInThisTick;

	public ElectricBlastFurnaceTileEntity()
	{
		super(AddonTiles.ELECTRIC_BLAST_FURNACE.get());

		this.energyStorage = new EnergyStorageBasic(this, 9000);
		this.inputInventory = new ItemStackHandler(1)
		{
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
			{
				if (stack.getItem() == SteahlItem.block)
				{
					return super.insertItem(slot, stack, simulate);
				}

				return stack;
			}

			@Override
			protected void onContentsChanged(int slot)
			{
				super.onContentsChanged(slot);
				onContentChanged();
			}
		};
		this.outputInventory = new ItemStackHandler(1)
		{
			@Override
			protected void onContentsChanged(int slot)
			{
				super.onContentsChanged(slot);
				onContentChanged();
			}
		};
		this.itemHandler = new CombinedInvWrapper(this.getInputInventory(), this.getOutputInventory())
		{
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
			{
				int index = this.getIndexForSlot(slot);
				IItemHandlerModifiable handler = this.getHandlerFromIndex(index);

				if (handler == getOutputInventory())
				{
					return stack;
				}

				return super.insertItem(slot, stack, simulate);
			}

			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate)
			{
				int index = this.getIndexForSlot(slot);
				IItemHandlerModifiable handler = this.getHandlerFromIndex(index);

				if (handler == getInputInventory())
				{
					return ItemStack.EMPTY;
				}

				return super.extractItem(slot, amount, simulate);
			};

		};

		this.timer = 0;
		this.maxTimer = 0;

		this.itemStackCacher = new StackCacher();
		this.cachedRecipe = ItemStack.EMPTY;
		this.processedInThisTick = false;
	}

	@Override
	public void onEnergyChanged(IEnergyStorage energyStorage, int energyDelta)
	{
		this.onContentChanged();
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
		return new ElectricBlastFurnaceContainer(windowId, inv, this);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("container.boss_tools_giselle_addon.electric_blast_furnace");
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
		this.energyStorage.deserializeNBT(compound.getCompound("energyStorage"));
		this.inputInventory.deserializeNBT(compound.getCompound("inputInventory"));
		this.outputInventory.deserializeNBT(compound.getCompound("outputInventory"));
	}

	protected void saveMetadata(CompoundNBT compound)
	{
		compound.put("fluidTank", this.energyStorage.serializeNBT());
		compound.put("inputInventory", this.inputInventory.serializeNBT());
		compound.put("outputInventory", this.outputInventory.serializeNBT());
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if (cap == CapabilityEnergy.ENERGY)
		{
			return LazyOptional.of(this::getEnergyStorage).cast();
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
		this.cook();
		this.refreshBlockActivatedChanged();
	}

	protected void refreshBlockActivatedChanged()
	{
		BooleanProperty property = ElectricBlastFurnaceBlock.LIT;
		World level = this.getLevel();
		BlockPos pos = this.getBlockPos();
		BlockState state = this.getBlockState();
		boolean lit = this.isProcessedInThisTick();

		if (state.hasProperty(property) && state.getValue(property).booleanValue() != lit)
		{
			level.setBlock(pos, state.setValue(property, lit), 3);
		}

	}

	private void cook()
	{
		ItemStack recipe = this.cacheRecipe();

		if (recipe.isEmpty() == false && this.hasSpaceInOutput(recipe) == true)
		{
			if (this.consumePower(1) == true)
			{
				this.onCooking();

				if (this.getTimer() >= this.getMaxTimer())
				{
					ItemHandlerHelper2.take(this.getInputInventory(), 1, false);
					ItemHandlerHelper.insertItem(this.getOutputInventory(), recipe.copy(), false);
					this.setTimer(0);
				}

			}
			else
			{
				this.onCantCooking();
			}

		}
		else
		{
			this.resetTimer();
		}

	}

	protected void onCooking()
	{
		this.setTimer(this.getTimer() + 1);
		this.setProcessedInThisTick();
	}

	protected void onCantCooking()
	{
		this.setTimer(this.getTimer() - 1);
	}

	public boolean consumePower(int power)
	{
		IEnergyStorage energyStorage = this.getEnergyStorage();

		if (energyStorage.receiveEnergy(power, true) == power)
		{
			energyStorage.receiveEnergy(power, false);
			return true;
		}
		else
		{
			return false;
		}

	}

	public boolean hasSpaceInOutput(ItemStack recipeOutput)
	{
		return ItemHandlerHelper.insertItemStacked(this.getOutputInventory(), recipeOutput, true).isEmpty();
	}

	protected ItemStack cacheRecipe()
	{
		NonNullList<ItemStack> inputs = ItemHandlerHelper2.getStacks(this.getInputInventory());

		if (this.itemStackCacher.test(inputs) == false)
		{
			this.itemStackCacher.set(inputs);

			if (inputs.size() == 0 && inputs.get(0).getItem() == Items.IRON_INGOT)
			{
				this.cachedRecipe = new ItemStack(SteahlItem.block);
			}
			else
			{
				this.cachedRecipe = ItemStack.EMPTY;
			}

			this.setMaxTimer(this.cachedRecipe != null ? 200 : 0);
		}

		return this.cachedRecipe;
	}

	public IEnergyStorage getEnergyStorage()
	{
		return this.energyStorage;
	}

	public IItemHandlerModifiable getInputInventory()
	{
		return this.inputInventory;
	}

	public IItemHandlerModifiable getOutputInventory()
	{
		return this.outputInventory;
	}

	public CombinedInvWrapper getItemHandler()
	{
		return this.itemHandler;
	}

	public boolean resetTimer()
	{
		if (this.getTimer() > 0)
		{
			this.setTimer(0);
			return true;
		}
		else
		{
			return false;
		}

	}

	public int getTimer()
	{
		return this.timer;
	}

	public void setTimer(int timer)
	{
		timer = MathHelper.clamp(timer, 0, this.getMaxTimer());

		if (this.getTimer() != timer)
		{
			this.timer = timer;
			this.onContentChanged();
		}

	}

	public int getMaxTimer()
	{
		return this.maxTimer;
	}

	public void setMaxTimer(int maxTimer)
	{
		maxTimer = Math.max(maxTimer, 0);

		if (this.getMaxTimer() != maxTimer)
		{
			this.maxTimer = maxTimer;
			this.setTimer(this.getTimer());
		}

	}

	public double getTimerRatio()
	{
		return this.getTimer() / (double) this.getMaxTimer();
	}

	protected boolean isProcessedInThisTick()
	{
		return this.processedInThisTick;
	}

	protected void setProcessedInThisTick()
	{
		this.processedInThisTick = true;
	}

}
