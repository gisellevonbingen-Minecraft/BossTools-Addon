package boss_tools_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.client.gui.ItemStackToItemStackScreen;
import boss_tools_giselle_addon.common.inventory.container.ItemStackToItemStackContainer;
import boss_tools_giselle_addon.common.tile.ItemStackToItemStackTileEntityMultiRecipe;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class IS2ISRegistration<S extends ItemStackToItemStackScreen<? extends C>, C extends ItemStackToItemStackContainer<C, ? extends T>, T extends ItemStackToItemStackTileEntityMultiRecipe> implements IIS2ISRegistration<S, C>
{
	private Class<S> screenClass;
	private Class<C> containerClass;
	private final List<ItemStack> itemstacks;
	private final List<ResourceLocation> categories;

	public IS2ISRegistration(Class<S> screenClass, Class<C> containerClass)
	{
		this.screenClass = screenClass;
		this.containerClass = containerClass;
		this.itemstacks = new ArrayList<ItemStack>();
		this.categories = new ArrayList<ResourceLocation>();
	}

	public IGuiContainerHandler<S> createContainerHandler()
	{
		return new IS2ISGuiContainerHandler<S, C, T>()
		{
			@Override
			public List<ResourceLocation> getCategories(T tileEntity)
			{
				return IS2ISRegistration.this.getCategories(tileEntity);
			}

		};
	}

	protected List<ResourceLocation> getCategories(T tileEntity)
	{
		return this.getCategories();
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

	public List<ResourceLocation> getCategories()
	{
		return this.categories;
	}

}
