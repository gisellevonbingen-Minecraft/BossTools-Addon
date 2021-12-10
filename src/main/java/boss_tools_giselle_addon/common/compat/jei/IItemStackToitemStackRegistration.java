package boss_tools_giselle_addon.common.compat.jei;

import java.util.List;

import boss_tools_giselle_addon.client.gui.ItemStackToItemStackScreen;
import boss_tools_giselle_addon.common.inventory.container.ItemStackToItemStackContainer;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IItemStackToitemStackRegistration<S extends ItemStackToItemStackScreen<? extends C>, C extends ItemStackToItemStackContainer<C, ?>>
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
			for (ResourceLocation category : this.getCategories())
			{
				registration.addRecipeCatalyst(itemStack, category);
			}

		}

	}

	public default void addRecipeTransferHandler(IRecipeTransferRegistration registration)
	{
		Class<C> containerClass = this.getContainerClass();

		for (ResourceLocation uid : this.getCategories())
		{
			registration.addRecipeTransferHandler(new ItemStackToitemStackRecipeTransferInfo<C>(containerClass, uid));
		}

	}

}
