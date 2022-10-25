package beyond_earth_giselle_addon.common.compat.jei;

import java.util.List;

import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity.ICompressorMode;
import beyond_earth_giselle_addon.common.inventory.AdvancedCompressorContainerMenu;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

public class AdvancedCompressorTransferInfo<R> extends IS2ISRecipeTransferInfo<AdvancedCompressorContainerMenu, R>
{
	public AdvancedCompressorTransferInfo(Class<AdvancedCompressorContainerMenu> containerClass, MenuType<AdvancedCompressorContainerMenu> menuType, RecipeType<R> recipeType)
	{
		super(containerClass, menuType, recipeType);
	}

	@Override
	public boolean canHandle(AdvancedCompressorContainerMenu container, R recipe)
	{
		return this.isModeMatched(container);
	}

	public boolean isModeMatched(AdvancedCompressorContainerMenu container)
	{
		ICompressorMode selectedMode = container.getBlockEntity().getMode();
		ResourceLocation selectedUid = AddonJeiCompressorModeHelper.INSTANCE.categoryUidByMode(selectedMode);
		ResourceLocation showCategoryUid = this.getRecipeType().getUid();

		return selectedUid != null && selectedUid.equals(showCategoryUid) == true;
	}

	@Override
	public IRecipeTransferError getHandlingError(AdvancedCompressorContainerMenu container, R recipe)
	{
		if (this.isModeMatched(container) == false)
		{
			ResourceLocation showCategoryUid = this.getRecipeType().getUid();
			ICompressorMode showMode = AddonJeiCompressorModeHelper.INSTANCE.modeByCategoryUid(showCategoryUid);
			List<Component> tooltip = AddonJeiTooltipHelper.getIncompatibleModeTooltip(showMode != null ? showMode.getText() : null);
			return new RecipeTransferErrorTooltip2(tooltip);
		}

		return super.getHandlingError(container, recipe);
	}

}
