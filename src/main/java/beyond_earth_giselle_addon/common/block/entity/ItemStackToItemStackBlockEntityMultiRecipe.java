package beyond_earth_giselle_addon.common.block.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import beyond_earth_giselle_addon.common.item.crafting.IS2ISRecipeCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipe;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.beyond_earth.gauge.IGaugeValue;
import net.mrscauthd.beyond_earth.inventory.StackCacher;
import net.mrscauthd.beyond_earth.machines.tile.ItemStackToItemStackBlockEntity;

public abstract class ItemStackToItemStackBlockEntityMultiRecipe extends ItemStackToItemStackBlockEntity
{
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
	public List<IGaugeValue> getGaugeValues()
	{
		List<IGaugeValue> list = super.getGaugeValues();

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
