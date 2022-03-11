package boss_tools_giselle_addon.common.compat.jei;

import boss_tools_giselle_addon.client.gui.AdvancedCompressorScreen;
import boss_tools_giselle_addon.common.inventory.container.AdvancedCompressorContainer;
import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.util.ResourceLocation;

public class AdvancedCompressorRegistration extends IS2ISRegistration<AdvancedCompressorScreen, AdvancedCompressorContainer, AdvancedCompressorTileEntity>
{
	public AdvancedCompressorRegistration(Class<AdvancedCompressorScreen> screenClass, Class<AdvancedCompressorContainer> containerClass)
	{
		super(screenClass, containerClass);
	}

	@Override
	public IRecipeTransferHandler<AdvancedCompressorContainer> ceateRecipeTransferHandler(IRecipeTransferRegistration registration, Class<AdvancedCompressorContainer> containerClass, ResourceLocation uid)
	{
		IStackHelper stackHelper = registration.getJeiHelpers().getStackHelper();
		IRecipeTransferHandlerHelper transferHelper = registration.getTransferHelper();
		IS2ISRecipeTransferInfo<AdvancedCompressorContainer> info = this.ceateRecipeTransferInfo(registration, containerClass, uid);
		return new AdvancedCompressorTransferHandler(stackHelper, transferHelper, info);
	}

}
