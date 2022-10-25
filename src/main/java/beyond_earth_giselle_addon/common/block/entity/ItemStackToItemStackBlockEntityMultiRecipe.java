package beyond_earth_giselle_addon.common.block.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import beyond_earth_giselle_addon.common.inventory.ItemHandlerHelper3;
import beyond_earth_giselle_addon.common.item.crafting.IS2ISRecipeCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.ItemStackToItemStackBlockEntity;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.gauge.IGaugeValue;
import net.mrscauthd.beyond_earth.common.data.recipes.ItemStackToItemStackRecipe;
import net.mrscauthd.beyond_earth.common.data.recipes.ItemStackToItemStackRecipeType;
import net.mrscauthd.beyond_earth.common.menus.nasaworkbench.StackCacher;

public abstract class ItemStackToItemStackBlockEntityMultiRecipe extends ItemStackToItemStackBlockEntity
{
	public static final String KEY_AUTO_PULL = "auto_pull";
	public static final String KEY_AUTO_EJECT = "auto_eject";
	public static final String KEY_AUTO_TIMER = "auto_timer";

	private StackCacher itemStackCacher;
	private ItemStackToItemStackRecipe cachedRecipe = null;

	public ItemStackToItemStackBlockEntityMultiRecipe(BlockEntityType<? extends ItemStackToItemStackBlockEntityMultiRecipe> type, BlockPos pos, BlockState state)
	{
		super(type, pos, state);

		this.itemStackCacher = new StackCacher();
		this.cachedRecipe = null;
	}

	public ItemStackToItemStackRecipe getCachedRecipe()
	{
		return this.cachedRecipe;
	}

	@Override
	public List<IGaugeValue> getDisplayGaugeValues()
	{
		List<IGaugeValue> list = super.getDisplayGaugeValues();

		if (this.getCachedRecipe() != null)
		{
			list.add(this.getCookTimeGaugeValue());
		}

		return list;
	}

	@Override
	protected boolean onCanPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction)
	{
		if (index == this.getSlotIngredient() && this.nullOrMatch(direction, Direction.UP))
		{
			return this.test(stack) != null;
		}
		else if (index == this.getSlotOutput() && direction == null)
		{
			return true;
		}

		return super.onCanPlaceItemThroughFace(index, stack, direction);
	}

	public List<RecipeType<? extends Recipe<Container>>> getRecipeTypes()
	{
		return new ArrayList<>();
	}

	public ItemStackToItemStackRecipe test(ItemStack itemStack)
	{
		Level level = this.getLevel();
		return IS2ISRecipeCache.cache(level.getRecipeManager(), level, itemStack, this.getRecipeTypes());
	}

	@Override
	public ItemStackToItemStackRecipeType<?> getRecipeType()
	{
		return null;
	}

	protected void clearRecipeCache()
	{
		this.itemStackCacher.set(ItemStack.EMPTY);
		this.cachedRecipe = null;
		this.setMaxTimer(0);
		this.resetTimer();
	}

	protected void tickAutoTimer()
	{
		int autoTimer = this.getAutoTimer();
		autoTimer++;

		if (autoTimer >= this.getAutoMaxTimer())
		{
			autoTimer = 0;
			int amount = 1;

			if (this.isAutoPull() == true)
			{
				this.tryPull(amount);
			}

			if (this.isAutoEject() == true)
			{
				this.tryEject(amount);
			}

		}

		this.setAutoTimer(autoTimer);
	}

	protected LazyOptional<IItemHandler> getTargetItemHandler(Direction direction)
	{
		BlockPos pos = this.getBlockPos().offset(direction.getNormal());
		BlockEntity blockEntity = this.getLevel().getBlockEntity(pos);

		if (blockEntity != null)
		{
			return blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction.getOpposite());
		}
		else
		{
			return LazyOptional.empty();
		}

	}

	protected void tryPull(int amount)
	{
		Direction direction = Direction.UP;
		IItemHandler fromItemHandler = this.getTargetItemHandler(direction).orElse(null);

		if (fromItemHandler != null)
		{
			IItemHandler toItemHandler = this.getCapability(ForgeCapabilities.ITEM_HANDLER, direction).orElse(null);

			if (fromItemHandler != null)
			{
				ItemHandlerHelper3.tryStackTransfer(fromItemHandler, toItemHandler, amount);
			}

		}

	}

	protected void tryEject(int amount)
	{
		Direction direction = Direction.DOWN;
		IItemHandler toItemHandler = this.getTargetItemHandler(direction).orElse(null);

		if (toItemHandler != null)
		{
			IItemHandler fromItemHandler = this.getCapability(ForgeCapabilities.ITEM_HANDLER, direction).orElse(null);

			if (fromItemHandler != null)
			{
				ItemHandlerHelper3.tryStackTransfer(fromItemHandler, toItemHandler, amount);
			}

		}

	}

	@Override
	protected void tickProcessing()
	{
		super.tickProcessing();

		this.tickAutoTimer();
	}

	@Override
	protected ItemStackToItemStackRecipe cacheRecipe()
	{
		ItemStack itemStack = this.getItem(this.getSlotIngredient());

		if (itemStack == null || itemStack.isEmpty())
		{
			this.itemStackCacher.set(itemStack);
			this.cachedRecipe = null;
			this.setMaxTimer(0);
		}
		else if (!this.itemStackCacher.test(itemStack))
		{
			this.itemStackCacher.set(itemStack);
			this.cachedRecipe = this.test(itemStack);

			if (this.cachedRecipe != null)
			{
				this.setMaxTimer(this.cachedRecipe.getCookTime());
			}
			else
			{
				this.setMaxTimer(0);
			}

		}

		return this.cachedRecipe;
	}

	public boolean isAutoPull()
	{
		return this.getPersistentData().getBoolean(KEY_AUTO_PULL);
	}

	public void setAutoPull(boolean autoPull)
	{
		if (this.isAutoPull() != autoPull)
		{
			this.getPersistentData().putBoolean(KEY_AUTO_PULL, autoPull);
			this.setChanged();
		}

	}

	public boolean isAutoEject()
	{
		return this.getPersistentData().getBoolean(KEY_AUTO_EJECT);
	}

	public void setAutoEject(boolean autoEject)
	{
		if (this.isAutoEject() != autoEject)
		{
			this.getPersistentData().putBoolean(KEY_AUTO_EJECT, autoEject);
			this.setChanged();
		}

	}

	public int getAutoTimer()
	{
		return this.getPersistentData().getInt(KEY_AUTO_TIMER);
	}

	public void setAutoTimer(int autoTimer)
	{
		autoTimer = Math.max(autoTimer, 0);

		if (this.getAutoTimer() != autoTimer)
		{
			this.getPersistentData().putInt(KEY_AUTO_TIMER, autoTimer);
			this.setChanged();
		}

	}

	public int getAutoMaxTimer()
	{
		return 10;
	}

}
