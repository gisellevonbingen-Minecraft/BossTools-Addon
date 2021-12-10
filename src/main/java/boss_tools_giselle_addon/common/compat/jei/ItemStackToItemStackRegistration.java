package boss_tools_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.client.gui.ItemStackToItemStackScreen;
import boss_tools_giselle_addon.common.inventory.container.ItemStackToItemStackContainer;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;

public class ItemStackToItemStackRegistration<S extends ItemStackToItemStackScreen<? extends C>, C extends ItemStackToItemStackContainer<C, ? extends T>, T extends ItemStackToItemStackTileEntity> implements IItemStackToitemStackRegistration<S, C>
{
	private Class<S> screenClass;
	private Class<C> containerClass;
	private final List<ItemStack> itemstacks;
	private final List<ResourceLocation> categories;

	public ItemStackToItemStackRegistration(Class<S> screenClass, Class<C> containerClass)
	{
		this.screenClass = screenClass;
		this.containerClass = containerClass;
		this.itemstacks = new ArrayList<ItemStack>();
		this.categories = new ArrayList<ResourceLocation>();
	}

	public IGuiContainerHandler<S> createContainerHandler()
	{
		return new ItemStackToItemStackGuiContainerHandler<S, C, T>()
		{
			@Override
			public List<ResourceLocation> getCategories(T tileEntity)
			{
				return ItemStackToItemStackRegistration.this.getCategories(tileEntity);
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
