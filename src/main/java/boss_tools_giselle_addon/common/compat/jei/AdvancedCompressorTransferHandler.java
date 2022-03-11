package boss_tools_giselle_addon.common.compat.jei;

import java.util.List;

import boss_tools_giselle_addon.common.inventory.container.AdvancedCompressorContainer;
import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity.ICompressorMode;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import mezz.jei.transfer.BasicRecipeTransferHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AdvancedCompressorTransferHandler extends BasicRecipeTransferHandler<AdvancedCompressorContainer>
{
	private final IRecipeTransferInfo<AdvancedCompressorContainer> transferHelper;

	public AdvancedCompressorTransferHandler(IStackHelper stackHelper, IRecipeTransferHandlerHelper handlerHelper, IRecipeTransferInfo<AdvancedCompressorContainer> transferHelper)
	{
		super(stackHelper, handlerHelper, transferHelper);
		this.transferHelper = transferHelper;
	}

	@Override
	public IRecipeTransferError transferRecipe(AdvancedCompressorContainer container, Object recipe, IRecipeLayout recipeLayout, PlayerEntity player, boolean maxTransfer, boolean doTransfer)
	{
		ICompressorMode selectedMode = container.getTileEntity().getMode();
		ResourceLocation selectedUid = AddonJeiCompressorModeHelper.INSTANCE.categoryUidByMode(selectedMode);
		ResourceLocation showCategoryUid = this.transferHelper.getRecipeCategoryUid();

		if (selectedUid == null || selectedUid.equals(showCategoryUid) == false)
		{
			ICompressorMode showMode = AddonJeiCompressorModeHelper.INSTANCE.modeByCategoryUid(showCategoryUid);
			List<ITextComponent> tooltip = AddonJeiTooltipHelper.getIncompatibleModeTooltip(showMode != null ? showMode.getText() : null);
			return new RecipeTransferErrorTooltip2(tooltip);
		}

		return super.transferRecipe(container, recipe, recipeLayout, player, maxTransfer, doTransfer);
	}

}
