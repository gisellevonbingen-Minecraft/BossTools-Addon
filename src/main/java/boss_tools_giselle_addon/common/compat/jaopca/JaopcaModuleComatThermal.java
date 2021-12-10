package boss_tools_giselle_addon.common.compat.jaopca;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.compat.thermal.AddonThermalItems;
import boss_tools_giselle_addon.common.compat.thermal.ThermalCompat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
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
import thelm.jaopca.compat.thermalexpansion.recipes.PressRecipeSupplier;

@JAOPCAModule(modDependencies = ThermalCompat.MODID)
public class JaopcaModuleComatThermal implements IModule
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
			this.registerIngotCompressingRecipe(material, compressedsForm, compressingDie, JaopcaModule.COMPRESSEDS_NAME);
		}

	}

	public void registerIngotCompressingRecipe(IMaterial material, IForm form, Item die, String suffix)
	{
		JAOPCAApi api = JAOPCAApi.instance();

		ResourceLocation id = JaopcaCompat.rl(BossToolsAddon.PMODID + ".press." + material.getName() + "_to_" + suffix);
		api.registerRecipe(id, () ->
		{
			IMiscHelper miscHelper = api.miscHelper();
			ResourceLocation ingotsTag = miscHelper.getTagLocation("ingots", material.getName());
			Ingredient ingredient = miscHelper.getIngredient(ingotsTag);

			IItemFormType itemFormType = api.itemFormType();
			IItemInfo outputItemInfo = itemFormType.getMaterialFormInfo(form, material);
			ItemStack output = miscHelper.getItemStack(outputItemInfo, 1);

			return new PressRecipeSupplier(id, ingredient, 1, die, 1, output, 1, 2400, 0.0F).get();
		});

	}

}
