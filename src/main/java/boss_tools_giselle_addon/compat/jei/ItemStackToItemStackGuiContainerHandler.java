package boss_tools_giselle_addon.compat.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.client.gui.ItemStackToItemStackScreen;
import boss_tools_giselle_addon.common.inventory.container.ItemStackToItemStackContainer;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;

public abstract class ItemStackToItemStackGuiContainerHandler<S extends ItemStackToItemStackScreen<? extends C>, C extends ItemStackToItemStackContainer<C, ? extends T>, T extends ItemStackToItemStackTileEntity> implements IGuiContainerHandler<S>
{
	public ItemStackToItemStackGuiContainerHandler()
	{

	}

	public abstract List<ResourceLocation> getCategories(T tileEntity);

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
				recipesGui.showCategories(getCategories(tileEntity));
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

}
