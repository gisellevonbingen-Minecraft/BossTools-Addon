package beyond_earth_giselle_addon.common.compat.jei;

import beyond_earth_giselle_addon.client.gui.AdvancedCompressorScreen;
import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity;
import beyond_earth_giselle_addon.common.inventory.AdvancedCompressorContainerMenu;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeTransferRegistration;

public class AdvancedCompressorRegistration extends IS2ISRegistration<AdvancedCompressorScreen, AdvancedCompressorContainerMenu, AdvancedCompressorBlockEntity>
{
	public AdvancedCompressorRegistration(Class<AdvancedCompressorScreen> screenClass, Class<AdvancedCompressorContainerMenu> containerClass)
	{
		super(screenClass, containerClass);
	}

	@Override
	public <R> IS2ISRecipeTransferInfo<AdvancedCompressorContainerMenu, R> ceateRecipeTransferInfo(IRecipeTransferRegistration registration, Class<AdvancedCompressorContainerMenu> containerClass, RecipeType<R> recipeType)
	{
		return new AdvancedCompressorTransferInfo<>(containerClass, recipeType);
	}

}
