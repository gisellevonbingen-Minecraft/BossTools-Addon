package boss_tools_giselle_addon.compat.jei;

import java.util.List;

import boss_tools_giselle_addon.common.inventory.container.ItemStackToItemStackContainer;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IItemStackToitemStackRegistration<S extends ContainerScreen<? extends C>, C extends ItemStackToItemStackContainer<?>>
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
			registration.addRecipeCatalyst(itemStack, this.getCategories().toArray(new ResourceLocation[0]));
		}

	}

	public default void addRecipeTransferHandler(IRecipeTransferRegistration registration)
	{
		for (ResourceLocation uid : this.getCategories())
		{
			registration.addRecipeTransferHandler(new ItemStackToitemStackRecipeTransferInfo<C>(this.getContainerClass(), uid));
		}

	}

}
