package boss_tools_giselle_addon.common.tile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.item.crafting.IS2ISRecipeCache;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;
import net.mrscauthd.boss_tools.inventory.StackCacher;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;

public abstract class ItemStackToItemStackTileEntityMultiRecipe extends ItemStackToItemStackTileEntity
{
	private StackCacher itemStackCacher;
	private ItemStackToItemStackRecipe cachedRecipe = null;

	public ItemStackToItemStackTileEntityMultiRecipe(TileEntityType<? extends ItemStackToItemStackTileEntityMultiRecipe> type)
	{
		super(type);

		this.itemStackCacher = new StackCacher();
		this.cachedRecipe = null;
	}

	@Override
	public List<IGaugeValue> getGaugeValues()
	{
		List<IGaugeValue> list = super.getGaugeValues();

		if (this.getCachedRecipe() != null)
		{
			list.add(this.getCookTimeGaugeValue());
		}

		return list;
	}

	public ItemStackToItemStackRecipe getCachedRecipe()
	{
		return this.cachedRecipe;
	}

	@Override
	protected boolean onCanInsertItem(int index, ItemStack stack, @Nullable Direction direction)
	{
		if (index == this.getSlotIngredient() && this.nullOrMatch(direction, Direction.UP))
		{
			return this.test(stack) != null;
		}
		else if (index == this.getSlotOutput() && direction == null)
		{
			return true;
		}

		return super.onCanInsertItem(index, stack, direction);
	}

	public List<IRecipeType<? extends IRecipe<IInventory>>> getRecipeTypes()
	{
		return new ArrayList<>();
	}

	public ItemStackToItemStackRecipe test(ItemStack itemStack)
	{
		World world = this.getLevel();
		return IS2ISRecipeCache.cache(world.getRecipeManager(), world, itemStack, this.getRecipeTypes());
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

}
