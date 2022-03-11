package beyond_earth_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import beyond_earth_giselle_addon.client.gui.ItemStackToItemStackScreen;
import beyond_earth_giselle_addon.common.block.entity.ItemStackToItemStackBlockEntityMultiRecipe;
import beyond_earth_giselle_addon.common.inventory.ItemStackToItemStackContainerMenu;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
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
		this.itemstacks = new ArrayList<ItemStack>();
		this.recipeTypes = new ArrayList<RecipeType<?>>();
	}

	public IGuiContainerHandler<S> createContainerHandler()
	{
		return new IS2ISGuiContainerHandler<S, C, T>()
		{
			@Override
			public List<RecipeType<?>> getRecipeTypes(T tileEntity)
			{
				return IS2ISRegistration.this.getRecipeTypes(tileEntity);
			}

		};
	}

	protected List<RecipeType<?>> getRecipeTypes(T tileEntity)
	{
		return this.getRecipeTypes();
	}

	public Class<S> getScreenClass()
	{
		return this.screenClass;
	}

	public Class<C> getContainerClass()
	{
		return this.containerClass;
	}

	public List<ItemStack> getItemstacks()
	{
		return this.itemstacks;
	}

	public List<RecipeType<?>> getRecipeTypes()
	{
		return this.recipeTypes;
	}

}
