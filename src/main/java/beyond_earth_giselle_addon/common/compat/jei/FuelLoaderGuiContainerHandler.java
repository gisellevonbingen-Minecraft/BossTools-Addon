package beyond_earth_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import beyond_earth_giselle_addon.client.gui.FuelLoaderScreen;
import beyond_earth_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import net.mrscauthd.beyond_earth.guis.helper.GuiHelper;

public class FuelLoaderGuiContainerHandler implements IGuiContainerHandler<FuelLoaderScreen>
{
	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(FuelLoaderScreen containerScreen, double mouseX, double mouseY)
	{
		FuelLoaderBlockEntity blockEntity = containerScreen.getMenu().getBlockEntity();

		return Collections.singleton(new IGuiClickableArea()
		{
			@Override
			public Rect2i getArea()
			{
				return GuiHelper.getFluidTankBounds(FuelLoaderScreen.TANK_LEFT, FuelLoaderScreen.TANK_TOP).toRect2i();
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui)
			{
				recipesGui.showTypes(Collections.singletonList(AddonJeiPlugin.instance().getFuelLoaderCategory().getRecipeType()));
			}

			@Override
			public List<Component> getTooltipStrings()
			{
				List<Component> list = new ArrayList<>();
				list.add(GaugeTextHelper.getStorageText(GaugeValueHelper.getFluid(blockEntity.getFluidTank())).build());

				ResourceLocation registryName = blockEntity.getFluidTank().getFluid().getFluid().getRegistryName();

				if (registryName != null)
				{
					ModContainer container = ModList.get().getModContainerById(registryName.getNamespace()).orElse(null);

					if (container != null)
					{
						list.add(new TextComponent(container.getModInfo().getDisplayName()).withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
					}

				}

				list.add(new TranslatableComponent("jei.tooltip.show.recipes"));
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
			return containerScreen.getMenu().getBlockEntity().getFluidTank().getFluid();
		}

		return IGuiContainerHandler.super.getIngredientUnderMouse(containerScreen, mouseX, mouseY);
	}

}
