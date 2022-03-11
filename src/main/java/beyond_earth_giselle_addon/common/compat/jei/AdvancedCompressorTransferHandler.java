package beyond_earth_giselle_addon.common.compat.jei;

import java.util.List;

import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity.ICompressorMode;
import beyond_earth_giselle_addon.common.inventory.AdvancedCompressorContainerMenu;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import mezz.jei.transfer.BasicRecipeTransferHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class AdvancedCompressorTransferHandler<R> extends BasicRecipeTransferHandler<AdvancedCompressorContainerMenu, R>
{
	private final IRecipeTransferInfo<AdvancedCompressorContainerMenu, R> transferHelper;

	public AdvancedCompressorTransferHandler(IStackHelper stackHelper, IRecipeTransferHandlerHelper handlerHelper, IRecipeTransferInfo<AdvancedCompressorContainerMenu, R> transferHelper)
	{
		super(stackHelper, handlerHelper, transferHelper);
		this.transferHelper = transferHelper;
	}

	@Override
	public IRecipeTransferError transferRecipe(AdvancedCompressorContainerMenu container, R recipe, IRecipeSlotsView recipeSlotsView, Player player, boolean maxTransfer, boolean doTransfer)
	{
		ICompressorMode selectedMode = container.getBlockEntity().getMode();
		ResourceLocation selectedUid = AddonJeiCompressorModeHelper.INSTANCE.categoryUidByMode(selectedMode);
		ResourceLocation showCategoryUid = this.transferHelper.getRecipeType().getUid();

		if (selectedUid == null || selectedUid.equals(showCategoryUid) == false)
		{
			ICompressorMode showMode = AddonJeiCompressorModeHelper.INSTANCE.modeByCategoryUid(showCategoryUid);
			List<Component> tooltip = AddonJeiTooltipHelper.getIncompatibleModeTooltip(showMode != null ? showMode.getText() : null);
			return new RecipeTransferErrorTooltip2(tooltip);
		}

		return super.transferRecipe(container, recipe, recipeSlotsView, player, maxTransfer, doTransfer);
	}

}
