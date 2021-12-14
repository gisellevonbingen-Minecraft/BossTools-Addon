package boss_tools_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.client.gui.FuelLoaderScreen;
import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;

public class FuelLoaderGuiContainerHandler implements IGuiContainerHandler<FuelLoaderScreen>
{
	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(FuelLoaderScreen containerScreen, double mouseX, double mouseY)
	{
		FuelLoaderTileEntity tileEntity = containerScreen.getMenu().getTileEntity();

		return Collections.singleton(new IGuiClickableArea()
		{
			@Override
			public Rectangle2d getArea()
			{
				return GuiHelper.getFluidTankBounds(FuelLoaderScreen.TANK_LEFT, FuelLoaderScreen.TANK_TOP).toVanila();
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui)
			{
				recipesGui.showCategories(Collections.singletonList(AddonJeiPlugin.instance().getFuelLoaderCategory().getUid()));
			}

			@Override
			public List<ITextComponent> getTooltipStrings()
			{
				List<ITextComponent> list = new ArrayList<>();
				list.add(GaugeTextHelper.getStorageText(GaugeValueHelper.getFluid(tileEntity.getFluidTank())).build());
				list.add(new TranslationTextComponent("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

	@Override
	public Object getIngredientUnderMouse(FuelLoaderScreen containerScreen, double mouseX, double mouseY)
	{
		int left = containerScreen.getGuiLeft() + FuelLoaderScreen.TANK_LEFT;
		int top = containerScreen.getGuiTop() + FuelLoaderScreen.TANK_TOP;

		if (GuiHelper.isHover(GuiHelper.getFluidTankBounds(left, top), mouseX, mouseY) == true)
		{
			return containerScreen.getMenu().getTileEntity().getFluidTank().getFluid();
		}

		return IGuiContainerHandler.super.getIngredientUnderMouse(containerScreen, mouseX, mouseY);
	}

}
