package net.mrscauthd.boss_tools.jei.jeiguihandlers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.gui.screens.coalgenerator.CoalGeneratorGuiWindow;
import net.mrscauthd.boss_tools.jei.JeiPlugin.CoalGeneratorJeiCategory;

public class CoalGeneratorGuiContainerHandler  implements IGuiContainerHandler<CoalGeneratorGuiWindow> {
	
	public CoalGeneratorGuiContainerHandler() {

	}
	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(CoalGeneratorGuiWindow containerScreen, double mouseX, double mouseY) {
		return Collections.singleton(new IGuiClickableArea() {
			@Override
			public Rect2i getArea() {
				return GuiHelper.getFireBounds(CoalGeneratorGuiWindow.FIRE_LEFT, CoalGeneratorGuiWindow.FIRE_TOP).toRect2i();
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui) {
				recipesGui.showCategories(Arrays.asList(CoalGeneratorJeiCategory.Uid));
			}

			@Override
			public List<Component> getTooltipStrings() {
				List<Component> list = new ArrayList<>();
				list.add(GaugeTextHelper.getStorageText(GaugeValueHelper.getBurnTime(containerScreen.getMenu().getBlockEntity().getPowerSystemGenerating())).build());
				list.add(new TranslatableComponent("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

 

}