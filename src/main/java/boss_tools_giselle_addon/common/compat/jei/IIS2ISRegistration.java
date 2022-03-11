package boss_tools_giselle_addon.common.compat.jei;

import java.util.List;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.client.gui.ItemStackToItemStackScreen;
import boss_tools_giselle_addon.common.inventory.container.ItemStackToItemStackContainer;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.transfer.BasicRecipeTransferHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IIS2ISRegistration<S extends ItemStackToItemStackScreen<? extends C>, C extends ItemStackToItemStackContainer<C, ?>>
{
	public IGuiContainerHandler<S> createContainerHandler();

	public List<ItemStack> getItemstacks();

	public List<ResourceLocation> getCategories();

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
			for (ResourceLocation uid : this.getCategories())
			{
				registration.addRecipeCatalyst(itemStack, uid);
			}

		}

	}

	public default void addRecipeTransferHandler(IRecipeTransferRegistration registration)
	{
		Class<C> containerClass = this.getContainerClass();

		for (ResourceLocation uid : this.getCategories())
		{
			this.addRecipeTransferHandler(registration, containerClass, uid);
		}

	}

	public default void addRecipeTransferHandler(IRecipeTransferRegistration registration, Class<C> containerClass, ResourceLocation uid)
	{
		IRecipeTransferHandler<C> handler = this.ceateRecipeTransferHandler(registration, containerClass, uid);

		if (handler != null)
		{
			registration.addRecipeTransferHandler(handler, uid);
		}

	}

	@Nullable
	public default IRecipeTransferHandler<C> ceateRecipeTransferHandler(IRecipeTransferRegistration registration, Class<C> containerClass, ResourceLocation uid)
	{
		IStackHelper stackHelper = registration.getJeiHelpers().getStackHelper();
		IRecipeTransferHandlerHelper transferHelper = registration.getTransferHelper();
		IS2ISRecipeTransferInfo<C> info = this.ceateRecipeTransferInfo(registration, containerClass, uid);

		if (info != null)
		{
			return new BasicRecipeTransferHandler<>(stackHelper, transferHelper, info);
		}
		else
		{
			return null;
		}

	}

	@Nullable
	public default IS2ISRecipeTransferInfo<C> ceateRecipeTransferInfo(IRecipeTransferRegistration registration, Class<C> containerClass, ResourceLocation uid)
	{
		return new IS2ISRecipeTransferInfo<>(containerClass, uid);
	}

}
