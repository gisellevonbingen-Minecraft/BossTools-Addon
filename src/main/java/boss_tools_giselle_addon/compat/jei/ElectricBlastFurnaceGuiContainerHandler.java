package boss_tools_giselle_addon.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.client.gui.ElectricBlastFurnaceScreen;
import boss_tools_giselle_addon.common.tile.ElectricBlastFurnaceTileEntity;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.gui.guihelper.GuiHelper;
import net.mrscauthd.boss_tools.jei.JeiPlugin.BlastingFurnaceJeiCategory;

public class ElectricBlastFurnaceGuiContainerHandler implements IGuiContainerHandler<ElectricBlastFurnaceScreen>
{
	public ElectricBlastFurnaceGuiContainerHandler()
	{

	}

	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(ElectricBlastFurnaceScreen containerScreen, double mouseX, double mouseY)
	{
		return Collections.singleton(new IGuiClickableArea()
		{
			@Override
			public Rectangle2d getArea()
			{
				return GuiHelper.getArrowBounds(ElectricBlastFurnaceScreen.ARROW_LEFT, ElectricBlastFurnaceScreen.ARROW_TOP);
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui)
			{
				recipesGui.showCategories(Arrays.asList(BlastingFurnaceJeiCategory.Uid));
			}

			@Override
			public List<ITextComponent> getTooltipStrings()
			{
				List<ITextComponent> list = new ArrayList<>();
				ElectricBlastFurnaceTileEntity tileEntity = containerScreen.getMenu().getTileEntity();
				list.add(tileEntity.getCookTimeGaugeData().getText());
				list.add(new TranslationTextComponent("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

}