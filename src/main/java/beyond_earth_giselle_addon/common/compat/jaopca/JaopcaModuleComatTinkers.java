package beyond_earth_giselle_addon.common.compat.jaopca;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mrscauthd.beyond_earth.compat.tinkers.TinkersCompat;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.api.recipes.IRecipeSerializer;

@JAOPCAModule(modDependencies = TinkersCompat.MODID)
public class JaopcaModuleComatTinkers implements IModule
{
	public JaopcaModuleComatTinkers()
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
		ResourceLocation id = JaopcaCompat.rl(BeyondEarthAddon.PMODID + ".smeltery.melting." + name + "_" + suffix);
		ResourceLocation compressedsTag = miscHelper.getTagLocation(JaopcaModule.COMPRESSEDS_TAG, name);
		List<ResourceLocation> moltenTags = new ArrayList<>();
		moltenTags.add(miscHelper.getTagLocation("molten", name, "_"));
		moltenTags.add(TinkersCompat.rl("molten_" + name));

		Set<ResourceLocation> itemTags = api.getItemTags();
		Set<ResourceLocation> fluidTags = api.getFluidTags();

		if (itemTags.contains(compressedsTag) == false || moltenTags.stream().anyMatch(rl -> fluidTags.contains(rl)) == false)
		{
			return;
		}

		api.registerRecipe(id, new IRecipeSerializer()
		{
			@Override
			public JsonElement get()
			{
				Tag<Fluid> moltenFluidTag = findFirst(miscHelper, moltenTags);
				Fluid fluid = moltenFluidTag.getValues().get(0);
				Tag<Fluid> metalTooltipTag = miscHelper.getFluidTag(TinkersCompat.rl("tooltips/metal"));
				FinishedRecipe[] recipes = new FinishedRecipe[1];
				Ingredient ingredient = null;
				
				if (metalTooltipTag.contains(fluid) == true)
				{
					ingredient = miscHelper.getIngredient(compressedsTag);					
				}
				else
				{
					ingredient = Ingredient.EMPTY;
				}

				MeltingRecipeBuilder.melting(ingredient, fluid, FluidValues.INGOT, 1.0F).save(f -> recipes[0] = f, id);
				JsonObject json = recipes[0].serializeRecipe();
				return json;
			}

		});

	}

	public Tag<Fluid> findFirst(IMiscHelper miscHelper, List<ResourceLocation> tags)
	{
		for (ResourceLocation tag : tags)
		{
			Tag<Fluid> fluidTag = miscHelper.getFluidTag(tag);

			if (fluidTag.getValues().size() > 0)
			{
				return fluidTag;
			}

		}

		return null;
	}

}
