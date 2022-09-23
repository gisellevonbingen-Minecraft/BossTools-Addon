package beyond_earth_giselle_addon.common.compat.jaopca;

import java.util.Collection;
import java.util.Set;
import java.util.function.ToIntFunction;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mrscauthd.beyond_earth.compats.tinkers.TinkersCompat;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.compat.tconstruct.recipes.MeltingRecipeSerializer;

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
			this.registerCompressedsMeltingRecipe(material, compressedsForm, JaopcaModule.COMPRESSEDS_NAME, 1);
		}

	}

	public void registerCompressedsMeltingRecipe(IMaterial material, IForm form, String suffix, int ingots)
	{
		JAOPCAApi api = JAOPCAApi.instance();
		IMiscHelper miscHelper = api.miscHelper();

		String name = material.getName();
		ResourceLocation compressedsTag = miscHelper.getTagLocation(JaopcaModule.COMPRESSEDS_TAG, name);
		ResourceLocation moltenTag = this.getMoltenTag(material);

		if (api.getItemTags().contains(compressedsTag) == false || moltenTag == null)
		{
			return;
		}

		ResourceLocation id = AddonJaopcaCompat.rl(BeyondEarthAddon.PMODID + ".smeltery.melting." + name + "_" + suffix);
		ToIntFunction<FluidStack> tempFunction = stack -> stack.getFluid().getAttributes().getTemperature(stack) - 300;
		ToIntFunction<FluidStack> moltenTime = stack -> IMeltingRecipe.calcTime(tempFunction.applyAsInt(stack), ingots);

		api.registerRecipe(id, () ->
		{
			int ingotAmount = this.getIngotAmount(moltenTag);
			return new MeltingRecipeSerializer(id, compressedsTag, moltenTag, ingotAmount * ingots, tempFunction, moltenTime, new Object[0]).get();
		});
	}

	public ResourceLocation getMoltenTag(IMaterial material)
	{
		Set<ResourceLocation> fluidTags = JAOPCAApi.instance().getFluidTags();

		for (String namespace : new String[]{"forge", TinkersCompat.MODID})
		{
			ResourceLocation moltenTag = this.getMoltenTag(namespace, material);

			if (fluidTags.contains(moltenTag) == true)
			{
				return moltenTag;
			}

		}

		return null;
	}

	public ResourceLocation getMoltenTag(String namespace, IMaterial material)
	{
		String name = material.getName();
		String moltenName = name;

		if (name.equals("brick") == true)
		{
			moltenName = "clay";
		}

		return new ResourceLocation(namespace, "molten_" + moltenName);
	}

	public int getIngotAmount(ResourceLocation moltenTag)
	{
		if (this.containsFluid(TinkerTags.Fluids.METAL_TOOLTIPS.location(), moltenTag) == true)
		{
			return FluidValues.INGOT;
		}
		else if (this.containsFluid(TinkerTags.Fluids.CLAY_TOOLTIPS.location(), moltenTag) == true)
		{
			return FluidValues.BRICK;
		}
		else
		{
			return 0;
		}

	}

	public boolean containsFluid(ResourceLocation tooltipTag, ResourceLocation moltenTag)
	{
		IMiscHelper miscHelper = JAOPCAApi.instance().miscHelper();
		Collection<Fluid> moltenFluids = miscHelper.getFluidTagValues(moltenTag);
		return miscHelper.getFluidTagValues(tooltipTag).stream().anyMatch(moltenFluids::contains);
	}

}
