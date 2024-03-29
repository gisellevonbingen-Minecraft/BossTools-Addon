package beyond_earth_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import beyond_earth_giselle_addon.client.gui.ItemStackToItemStackScreen;
import beyond_earth_giselle_addon.common.block.entity.ItemStackToItemStackBlockEntityMultiRecipe;
import beyond_earth_giselle_addon.common.inventory.ItemStackToItemStackContainerMenu;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.mrscauthd.beyond_earth.client.util.GuiHelper;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.gauge.GaugeTextHelper;

public class IS2ISGuiContainerHandler<S extends ItemStackToItemStackScreen<? extends C>, C extends ItemStackToItemStackContainerMenu<C, ? extends T>, T extends ItemStackToItemStackBlockEntityMultiRecipe> implements IGuiContainerHandler<S>
{
	private final IS2ISRegistration<S, C, T> registration;

	public IS2ISGuiContainerHandler(IS2ISRegistration<S, C, T> registration)
	{
		this.registration = registration;
	}

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
				recipesGui.showTypes(getRegistration().getRecipeTypes(blockEntity));
			}

			@Override
			public List<Component> getTooltipStrings()
			{
				List<Component> list = new ArrayList<>();
				list.add(GaugeTextHelper.getStorageText(blockEntity.getCookTimeGaugeValue()).build());
				list.add(Component.translatable("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

	public IS2ISRegistration<S, C, T> getRegistration()
	{
		return this.registration;
	}

}
