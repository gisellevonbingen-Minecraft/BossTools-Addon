package beyond_earth_giselle_addon.common.compat.jaopca;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mrscauthd.beyond_earth.compat.tinkers.TinkersCompat;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;

@JAOPCAModule(modDependencies = TinkersCompat.MODID)
public class JaopcaModuleCompatTinkers implements IModule
{
	public JaopcaModuleCompatTinkers()
	{

	}

	@Override
	public String getName()
	{
		return BeyondEarthAddon.MODID + "_compat_tinkers";
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

		String name = material.getName();
		ResourceLocation compressedsTag = miscHelper.getTagLocation(JaopcaModule.COMPRESSEDS_TAG, name);
		ResourceLocation moltenLocation = miscHelper.getTagLocation("molten", name, "_");

		if (api.getItemTags().contains(compressedsTag) == false || api.getFluidTags().contains(moltenLocation) == false)
		{
			return;
		}

//		int ingots = 1;
//		ResourceLocation id = AddonJaopcaCompat.rl(BeyondEarthAddon.PMODID + ".smeltery.melting." + name + "_" + suffix);
//		ToIntFunction<FluidStack> tempFunction = stack -> stack.getFluid().getAttributes().getTemperature(stack) - 300;
//		ToIntFunction<FluidStack> moltenTime = stack -> IMeltingRecipe.calcTime(tempFunction.applyAsInt(stack), ingots);
//		TConstructHelper tinkerHelper = TConstructHelper.INSTANCE;
//		tinkerHelper.registerMeltingRecipe(id, compressedsTag, moltenLocation, FluidValues.INGOT * ingots, tempFunction, moltenTime);
	}

}
