package boss_tools_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.client.gui.ItemStackToItemStackScreen;
import boss_tools_giselle_addon.common.inventory.container.ItemStackToItemStackContainer;
import boss_tools_giselle_addon.common.tile.ItemStackToItemStackTileEntityMultiRecipe;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;

public class IS2ISGuiContainerHandler<S extends ItemStackToItemStackScreen<? extends C>, C extends ItemStackToItemStackContainer<C, ? extends T>, T extends ItemStackToItemStackTileEntityMultiRecipe> implements IGuiContainerHandler<S>
{
	private final IS2ISRegistration<S, C, T> registration;

	public IS2ISGuiContainerHandler(IS2ISRegistration<S, C, T> registration)
	{
		this.registration = registration;
	}

	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(S containerScreen, double mouseX, double mouseY)
	{
		T tileEntity = containerScreen.getMenu().getTileEntity();

		return Collections.singleton(new IGuiClickableArea()
		{
			@Override
			public Rectangle2d getArea()
			{
				return GuiHelper.getArrowBounds(ItemStackToItemStackScreen.ARROW_LEFT, ItemStackToItemStackScreen.ARROW_TOP).toVanila();
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui)
			{
				recipesGui.showCategories(getRegistration().getCategories(tileEntity));
			}

			@Override
			public List<ITextComponent> getTooltipStrings()
			{
				List<ITextComponent> list = new ArrayList<>();
				list.add(GaugeTextHelper.getStorageText(tileEntity.getCookTimeGaugeValue()).build());
				list.add(new TranslationTextComponent("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

	public IS2ISRegistration<S, C, T> getRegistration()
	{
		return this.registration;
	}

}
