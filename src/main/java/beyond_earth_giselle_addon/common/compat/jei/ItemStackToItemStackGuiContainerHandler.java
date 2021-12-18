package beyond_earth_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.mojang.datafixers.util.Pair;

import beyond_earth_giselle_addon.client.gui.ItemStackToItemStackScreen;
import beyond_earth_giselle_addon.common.inventory.ItemStackToItemStackContainerMenu;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipe;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;
import net.mrscauthd.beyond_earth.machines.tile.ItemStackToItemStackBlockEntity;

public abstract class ItemStackToItemStackGuiContainerHandler<S extends ItemStackToItemStackScreen<? extends C>, C extends ItemStackToItemStackContainerMenu<C, ? extends T>, T extends ItemStackToItemStackBlockEntity> implements IGuiContainerHandler<S>
{
	public ItemStackToItemStackGuiContainerHandler()
	{

	}

	public abstract List<Pair<ResourceLocation, Class<? extends ItemStackToItemStackRecipe>>> getCategories(T tileEntity);

	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(S containerScreen, double mouseX, double mouseY)
	{
		T blockEntity = containerScreen.getMenu().getBlockEntity();

		return Collections.singleton(new IGuiClickableArea()
		{
			@Override
			public Rect2i getArea()
			{
				return GuiHelper.getArrowBounds(ItemStackToItemStackScreen.ARROW_LEFT, ItemStackToItemStackScreen.ARROW_TOP).toRect2i();
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui)
			{
				recipesGui.showCategories(getCategories(blockEntity).stream().map(Pair::getFirst).collect(Collectors.toList()));
			}

			@Override
			public List<Component> getTooltipStrings()
			{
				List<Component> list = new ArrayList<>();
				list.add(GaugeTextHelper.getStorageText(blockEntity.getCookTimeGaugeValue()).build());
				list.add(new TranslatableComponent("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

}
