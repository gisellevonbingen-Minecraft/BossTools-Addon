package boss_tools_giselle_addon.common.compat.jaopca;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.compat.thermal.AddonThermalCompat;
import boss_tools_giselle_addon.common.compat.thermal.AddonThermalItems;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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
import thelm.jaopca.compat.thermalexpansion.ThermalExpansionHelper;

@JAOPCAModule(modDependencies = AddonThermalCompat.MODID)
public class JaopcaModuleCompatThermal implements IModule
{
	@Override
	public String getName()
	{
		return BossToolsAddon.MODID + "_compat_thermal";
	}

	@Override
	public void onCommonSetup(IModuleData moduleData, FMLCommonSetupEvent event)
	{
		JAOPCAApi api = JAOPCAApi.instance();
		Item compressingDie = AddonThermalItems.PRESS_COMPRESSING_DIE.get();
		IForm compressedsForm = api.getForm(JaopcaModule.COMPRESSEDS_FORM_NAME);

		for (IMaterial material : compressedsForm.getMaterials())
		{
			String name = material.getName();

			if (JaopcaModule.COMPRESSING_BLACKLIST.contains(name) == false)
			{
				this.registerIngotCompressingRecipe(material, compressedsForm, compressingDie, JaopcaModule.COMPRESSEDS_NAME);
			}

		}

	}

	public void registerIngotCompressingRecipe(IMaterial material, IForm form, Item die, String suffix)
	{
		JAOPCAApi api = JAOPCAApi.instance();
		IMiscHelper miscHelper = api.miscHelper();
		ResourceLocation ingotsTag = miscHelper.getTagLocation("ingots", material.getName());
		ResourceLocation id = AddonJaopcaCompat.rl(BossToolsAddon.PMODID + ".press." + material.getName() + "_to_" + suffix);
		IItemFormType itemFormType = api.itemFormType();
		IItemInfo outputItemInfo = itemFormType.getMaterialFormInfo(form, material);
		ThermalExpansionHelper.INSTANCE.registerPressRecipe(id, ingotsTag, 1, die, 1, outputItemInfo, 1, 2400, 0.0F);
	}

}
