package astrocraft_giselle_addon.common.compat.jei;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import astrocraft_giselle_addon.client.gui.ItemStackToItemStackScreen;
import astrocraft_giselle_addon.common.inventory.ItemStackToItemStackContainerMenu;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.astrocraft.crafting.ItemStackToItemStackRecipe;

public interface IItemStackToitemStackRegistration<S extends ItemStackToItemStackScreen<? extends C>, C extends ItemStackToItemStackContainerMenu<C, ?>>
{
	public IGuiContainerHandler<S> createContainerHandler();

	public List<ItemStack> getItemstacks();

	public List<Pair<ResourceLocation, Class<? extends ItemStackToItemStackRecipe>>> getCategories();

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
			for (Pair<ResourceLocation, Class<? extends ItemStackToItemStackRecipe>> tuple : this.getCategories())
			{
				registration.addRecipeCatalyst(itemStack, tuple.getFirst());
			}

		}

	}

	@SuppressWarnings("unchecked")
	public default void addRecipeTransferHandler(IRecipeTransferRegistration registration)
	{
		Class<C> containerClass = this.getContainerClass();

		for (Pair<ResourceLocation, Class<? extends ItemStackToItemStackRecipe>> tuple : this.getCategories())
		{
			ResourceLocation uid = tuple.getFirst();
			Class<? extends ItemStackToItemStackRecipe> recipeClass = tuple.getSecond();
			registration.addRecipeTransferHandler(new ItemStackToitemStackRecipeTransferInfo<C>(containerClass, (Class<ItemStackToItemStackRecipe>) recipeClass, uid));
		}

	}

}
