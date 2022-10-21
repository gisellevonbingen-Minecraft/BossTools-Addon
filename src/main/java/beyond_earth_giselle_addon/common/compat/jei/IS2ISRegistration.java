package beyond_earth_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import beyond_earth_giselle_addon.client.gui.ItemStackToItemStackScreen;
import beyond_earth_giselle_addon.common.block.entity.ItemStackToItemStackBlockEntityMultiRecipe;
import beyond_earth_giselle_addon.common.inventory.ItemStackToItemStackContainerMenu;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;

public class IS2ISRegistration<S extends ItemStackToItemStackScreen<? extends C>, C extends ItemStackToItemStackContainerMenu<C, ? extends T>, T extends ItemStackToItemStackBlockEntityMultiRecipe> implements IIS2ISRegistration<S, C>
{
	private Class<S> screenClass;
	private Class<C> containerClass;
	private final List<ItemStack> itemstacks;
	private final List<RecipeType<?>> recipeTypes;

	public IS2ISRegistration(Class<S> screenClass, Class<C> containerClass)
	{
		this.screenClass = screenClass;
		this.containerClass = containerClass;
		this.itemstacks = new ArrayList<>();
		this.recipeTypes = new ArrayList<>();
	}

	@Override
	public IGuiContainerHandler<S> createContainerHandler()
	{
		return new IS2ISGuiContainerHandler<>(this);
	}

	protected List<RecipeType<?>> getRecipeTypes(T blockEntity)
	{
		return this.getRecipeTypes();
	}

	@Override
	public Class<S> getScreenClass()
	{
		return this.screenClass;
	}

	@Override
	public Class<C> getContainerClass()
	{
		return this.containerClass;
	}

	@Override
	public List<ItemStack> getItemstacks()
	{
		return this.itemstacks;
	}

	@Override
	public List<RecipeType<?>> getRecipeTypes()
	{
		return this.recipeTypes;
	}

}
