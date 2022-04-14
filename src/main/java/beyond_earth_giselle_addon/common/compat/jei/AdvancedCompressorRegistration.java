package beyond_earth_giselle_addon.common.compat.jei;

import beyond_earth_giselle_addon.client.gui.AdvancedCompressorScreen;
import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity;
import beyond_earth_giselle_addon.common.inventory.AdvancedCompressorContainerMenu;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.registration.IRecipeTransferRegistration;

public class AdvancedCompressorRegistration extends IS2ISRegistration<AdvancedCompressorScreen, AdvancedCompressorContainerMenu, AdvancedCompressorBlockEntity>
{
	public AdvancedCompressorRegistration(Class<AdvancedCompressorScreen> screenClass, Class<AdvancedCompressorContainerMenu> containerClass)
	{
		super(screenClass, containerClass);
	}

	@Override
	public <R> IRecipeTransferHandler<AdvancedCompressorContainerMenu, R> ceateRecipeTransferHandler(IRecipeTransferRegistration registration, Class<AdvancedCompressorContainerMenu> containerClass, RecipeType<R> recipeType)
	{
		IStackHelper stackHelper = registration.getJeiHelpers().getStackHelper();
		IRecipeTransferHandlerHelper transferHelper = registration.getTransferHelper();
		IS2ISRecipeTransferInfo<AdvancedCompressorContainerMenu, R> info = this.ceateRecipeTransferInfo(registration, containerClass, recipeType);
		return new AdvancedCompressorTransferHandler<>(stackHelper, transferHelper, info);
	}

}
