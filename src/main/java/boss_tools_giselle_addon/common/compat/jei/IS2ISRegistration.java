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
		this.itemstacks = new ArrayList<>();
		this.categories = new ArrayList<>();
	}

	@Override
	public IGuiContainerHandler<S> createContainerHandler()
	{
		return new IS2ISGuiContainerHandler<>(this);
	}

	public List<ResourceLocation> getCategories(T tileEntity)
	{
		return this.getCategories();
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
	public List<ResourceLocation> getCategories()
	{
		return this.categories;
	}

}
