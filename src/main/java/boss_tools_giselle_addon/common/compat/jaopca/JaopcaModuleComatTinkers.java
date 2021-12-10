package boss_tools_giselle_addon.common.compat.jaopca;

import java.util.function.ToIntFunction;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mrscauthd.boss_tools.compat.tinkers.TinkersCompat;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.compat.tconstruct.TConstructHelper;

@JAOPCAModule(modDependencies = TinkersCompat.MODID)
public class JaopcaModuleComatTinkers implements IModule
{
	@Override
	public String getName()
	{
		return BossToolsAddon.MODID + "_compat_timkers";
	}

	@Override
	public void onCommonSetup(IModuleData moduleData, FMLCommonSetupEvent event)
	{
		JAOPCAApi api = JAOPCAApi.instance();
		IForm compressedsForm = api.getForm(JaopcaModule.COMPRESSEDS_FORM_NAME);

		for (IMaterial material : compressedsForm.getMaterials())
		{
			this.registerCompressedsMeltingRecipe(material, compressedsForm, JaopcaModule.COMPRESSEDS_NAME);
		}

	}

	public void registerCompressedsMeltingRecipe(IMaterial material, IForm form, String suffix)
	{
		JAOPCAApi api = JAOPCAApi.instance();
		IMiscHelper miscHelper = api.miscHelper();

		ResourceLocation id = JaopcaCompat.rl(BossToolsAddon.PMODID + ".smeltery.melting." + material.getName() + "_" + suffix);
		ResourceLocation compressedsTag = miscHelper.getTagLocation(JaopcaModule.COMPRESSEDS_TAG, material.getName());
		ResourceLocation moltenLocation = miscHelper.getTagLocation("molten", material.getName(), "_");

		int ingots = 1;
		ToIntFunction<FluidStack> tempFunction = stack -> stack.getFluid().getAttributes().getTemperature(stack) - 300;
		ToIntFunction<FluidStack> moltenTime = stack -> IMeltingRecipe.calcTime(tempFunction.applyAsInt(stack), ingots);

		if (api.getItemTags().contains(compressedsTag) == false || api.getFluidTags().contains(moltenLocation) == false)
		{
			return;
		}

		TConstructHelper.INSTANCE.registerMeltingRecipe(id, compressedsTag, moltenLocation, FluidValues.INGOT * ingots, tempFunction, moltenTime, false);
	}

}
