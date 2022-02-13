package beyond_earth_giselle_addon.common.compat.jaopca;

import beyond_earth_giselle_addon.common.compat.immersiveengineering.AddonIECompat;
import beyond_earth_giselle_addon.common.compat.immersiveengineering.AddonIEItems;
import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.items.IItemFormType;
import thelm.jaopca.api.items.IItemInfo;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.compat.immersiveengineering.ImmersiveEngineeringHelper;

@JAOPCAModule(modDependencies = AddonIECompat.MODID)
public class JaopcaModuleCompatImmersiveEngineering implements IModule
{
	@Override
	public String getName()
	{
		return BeyondEarthAddon.MODID + "_compat_ie";
	}

	@Override
	public void onCommonSetup(IModuleData moduleData, FMLCommonSetupEvent event)
	{
		JAOPCAApi api = JAOPCAApi.instance();
		Item moldCompressing = AddonIEItems.MOLD_COMPRESSING.get();
		IForm compressedsForm = api.getForm(JaopcaModule.COMPRESSEDS_FORM_NAME);

		for (IMaterial material : compressedsForm.getMaterials())
		{
			String name = material.getName();

			if (JaopcaModule.COMPRESSING_BLACKLIST.contains(name) == false)
			{
				this.registerIngotCompressingRecipe(material, compressedsForm, moldCompressing, JaopcaModule.COMPRESSEDS_NAME);
			}

		}

	}

	public void registerIngotCompressingRecipe(IMaterial material, IForm form, Item mold, String suffix)
	{
		JAOPCAApi api = JAOPCAApi.instance();
		IMiscHelper miscHelper = api.miscHelper();
		ResourceLocation ingotsTag = miscHelper.getTagLocation("ingots", material.getName());
		ResourceLocation id = AddonJaopcaCompat.rl(BeyondEarthAddon.PMODID + ".metalpress." + material.getName() + "_to_" + suffix);
		IItemFormType itemFormType = api.itemFormType();
		IItemInfo outputItemInfo = itemFormType.getMaterialFormInfo(form, material);
		ImmersiveEngineeringHelper.INSTANCE.registerMetalPressRecipe(id, ingotsTag, 1, mold, outputItemInfo, 1, 200);
	}

}
