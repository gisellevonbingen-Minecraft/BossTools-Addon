package beyond_earth_giselle_addon.common.compat.jei;

import java.util.List;

import javax.annotation.Nullable;

import beyond_earth_giselle_addon.client.gui.ItemStackToItemStackScreen;
import beyond_earth_giselle_addon.common.inventory.ItemStackToItemStackContainerMenu;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.world.item.ItemStack;

public interface IIS2ISRegistration<S extends ItemStackToItemStackScreen<? extends C>, C extends ItemStackToItemStackContainerMenu<C, ?>>
{
	public IGuiContainerHandler<S> createContainerHandler();

	public List<ItemStack> getItemstacks();

	public List<RecipeType<?>> getRecipeTypes();

	public Class<S> getScreenClass();

	public Class<C> getContainerClass();

	public default void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
		registration.addGuiContainerHandler(this.getScreenClass(), this.createContainerHandler());
	}

	public default void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		for (ItemStack itemStack : this.getItemstacks())
		{
			for (RecipeType<?> recipeType : this.getRecipeTypes())
			{
				registration.addRecipeCatalyst(itemStack, recipeType.getUid());
			}

		}

	}

	public default void addRecipeTransferHandler(IRecipeTransferRegistration registration)
	{
		for (RecipeType<?> recipeType : this.getRecipeTypes())
		{
			IS2ISRecipeTransferInfo<C, ?> info = this.createRecipeTransferHandler(recipeType);

			if (info != null)
			{
				registration.addRecipeTransferHandler(info);
			}

		}

	}

	@Nullable
	public default IS2ISRecipeTransferInfo<C, ?> createRecipeTransferHandler(RecipeType<?> recipeType)
	{
		Class<C> containerClass = this.getContainerClass();
		return new IS2ISRecipeTransferInfo<>(containerClass, recipeType);
	}

}
