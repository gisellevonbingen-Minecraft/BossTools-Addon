package boss_tools_giselle_addon.common.compat.jaopca;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.item.crafting.AddonRecipes;
import boss_tools_giselle_addon.common.item.crafting.ExtrudingRecipe;
import boss_tools_giselle_addon.common.item.crafting.RollingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.CompressingRecipe;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.forms.IFormRequest;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.items.IItemFormType;
import thelm.jaopca.api.items.IItemInfo;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.materials.MaterialType;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;

@JAOPCAModule
public class JaopcaModule implements IModule
{
	public static final HashSet<String> COMMON_BLACKLIST = Sets.newHashSet("netherite_scrap");
	public static final Set<String> BUILTIN_COMPRESSEDS_MATERIALS = Collections.unmodifiableSet(Sets.newHashSet("steel", "desh", "silicon"));
	public static final Set<String> BUILTIN_PLATES_MATERIALS = Collections.unmodifiableSet(Sets.newHashSet("iron", "desh"));
	public static final Set<String> BUILTIN_RODS_MATERIALS = Collections.unmodifiableSet(Sets.newHashSet("iron"));

	public static final String COMPRESSEDS_NAME = "compresseds";
	public static final String COMPRESSEDS_TAG = BossToolsAddon.prl(COMPRESSEDS_NAME).toString();
	public static final String COMPRESSEDS_FORM_NAME = BossToolsAddon.PMODID + "_" + COMPRESSEDS_NAME;
	public static final Set<String> COMRESSING_BLACKLIST = Sets.union(BUILTIN_COMPRESSEDS_MATERIALS, COMMON_BLACKLIST);

	public static final Set<String> ROLLING_BLACKLIST = Sets.union(BUILTIN_PLATES_MATERIALS, COMMON_BLACKLIST);
	public static final Set<String> EXTRUDING_BLACKLIST = Sets.union(BUILTIN_RODS_MATERIALS, COMMON_BLACKLIST);

	private final IForm compressedsForm;

	public JaopcaModule()
	{
		JAOPCAApi api = JAOPCAApi.instance();
		this.compressedsForm = JAOPCAApi.instance().newForm(this, COMPRESSEDS_FORM_NAME, api.itemFormType()).setMaterialTypes(MaterialType.INGOTS).setSecondaryName(COMPRESSEDS_TAG).setDefaultMaterialBlacklist(COMRESSING_BLACKLIST);
	}

	@Override
	public String getName()
	{
		return BossToolsAddon.MODID;
	}

	@Override
	public Set<MaterialType> getMaterialTypes()
	{
		return EnumSet.allOf(MaterialType.class);
	}

	@Override
	public List<IFormRequest> getFormRequests()
	{
		return Collections.singletonList(JAOPCAApi.instance().newFormRequest(this, this.getCompressedsForm()));
	}

	@Override
	public void onCommonSetup(IModuleData moduleData, FMLCommonSetupEvent event)
	{
		IForm compressedForm = this.getCompressedsForm();

		for (IMaterial material : compressedForm.getMaterials())
		{
			this.registerIngotCompressingRecipe(material, compressedForm);
		}

		for (IMaterial material : moduleData.getMaterials())
		{
			String name = material.getName();

			if (ROLLING_BLACKLIST.contains(name) == false)
			{
				this.registerIngotRollingRecipe(material);
			}

			if (EXTRUDING_BLACKLIST.contains(name) == false)
			{
				this.registerIngotExtrudingRecipe(material);
			}

		}

	}

	public void registerIngotCompressingRecipe(IMaterial material, IForm form)
	{
		JAOPCAApi api = JAOPCAApi.instance();
		IMiscHelper miscHelper = api.miscHelper();
		ResourceLocation ingotsTag = miscHelper.getTagLocation("ingots", material.getName());

		ResourceLocation id = JaopcaCompat.rl(BossToolsAddon.PMODID + "." + BossToolsRecipeTypes.COMPRESSING.getName() + "." + material.getName());
		api.registerRecipe(id, () ->
		{
			Ingredient ingredient = miscHelper.getIngredient(ingotsTag);

			IItemFormType itemFormType = api.itemFormType();
			IItemInfo outputItemInfo = itemFormType.getMaterialFormInfo(form, material);
			ItemStack output = miscHelper.getItemStack(outputItemInfo, 1);

			return new CompressingRecipe(id, ingredient, output, 200);
		});
	}

	public void registerIngotRollingRecipe(IMaterial material)
	{
		JAOPCAApi api = JAOPCAApi.instance();
		IMiscHelper miscHelper = api.miscHelper();
		Set<ResourceLocation> itemTags = api.getItemTags();
		ResourceLocation ingotsTag = miscHelper.getTagLocation("ingots", material.getName());
		ResourceLocation platesTag = miscHelper.getTagLocation("plates", material.getName());

		if (itemTags.contains(ingotsTag) == false || itemTags.contains(platesTag) == false)
		{
			return;
		}

		ResourceLocation id = JaopcaCompat.rl(BossToolsAddon.MODID + "." + AddonRecipes.ROLLING.getName() + "." + material.getName());
		api.registerRecipe(id, () ->
		{
			Ingredient ingredient = miscHelper.getIngredient(ingotsTag);
			ItemStack output = miscHelper.getItemStack(platesTag, 1);

			return new RollingRecipe(id, ingredient, output, 200);
		});
	}

	public void registerIngotExtrudingRecipe(IMaterial material)
	{
		JAOPCAApi api = JAOPCAApi.instance();
		IMiscHelper miscHelper = api.miscHelper();
		Set<ResourceLocation> itemTags = api.getItemTags();
		ResourceLocation ingotsTag = miscHelper.getTagLocation("ingots", material.getName());
		ResourceLocation rodsTag = miscHelper.getTagLocation("rods", material.getName());

		if (itemTags.contains(ingotsTag) == false || itemTags.contains(rodsTag) == false)
		{
			return;
		}

		ResourceLocation id = JaopcaCompat.rl(BossToolsAddon.MODID + "." + AddonRecipes.EXTRUDING.getName() + "." + material.getName());
		api.registerRecipe(id, () ->
		{
			Ingredient ingredient = miscHelper.getIngredient(ingotsTag);
			ItemStack output = miscHelper.getItemStack(rodsTag, 2);

			return new ExtrudingRecipe(id, ingredient, output, 50);
		});
	}

	public IForm getCompressedsForm()
	{
		return this.compressedsForm;
	}

}