package boss_tools_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.client.gui.AdvancedCompressorScreen;
import boss_tools_giselle_addon.client.gui.ElectricBlastFurnaceScreen;
import boss_tools_giselle_addon.common.block.AddonBlocks;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.inventory.container.AdvancedCompressorContainer;
import boss_tools_giselle_addon.common.inventory.container.ElectricBlastFurnaceContainer;
import boss_tools_giselle_addon.common.item.crafting.AddonRecipes;
import boss_tools_giselle_addon.common.item.crafting.ExtrudingRecipe;
import boss_tools_giselle_addon.common.item.crafting.RollingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.boss_tools.jei.JeiPlugin.BlastingFurnaceJeiCategory;
import net.mrscauthd.boss_tools.jei.JeiPlugin.CompressorJeiCategory;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin
{
	public static final String JEI_CATEGORY = "jei.category";
	public static final String JEI_INFO = "jei.info";

	private static JeiPlugin instance;

	public static JeiPlugin instance()
	{
		return instance;
	}

	public static final ResourceLocation createUid(IRecipeType<?> recipeType)
	{
		ResourceLocation key = Registry.RECIPE_TYPE.getKey(recipeType);
		return new ResourceLocation(key.getNamespace(), JEI_CATEGORY + "." + key.getPath());
	}

	public static final ITextComponent getCategoryTitle(IRecipeType<?> recipeType)
	{
		ResourceLocation key = Registry.RECIPE_TYPE.getKey(recipeType);
		return new TranslationTextComponent(JEI_CATEGORY + "." + key.getNamespace() + "." + key.getPath());
	}

	private List<IItemStackToitemStackRegistration<?, ?>> is2isRegistrations;
	private IItemStackToitemStackRegistration<ElectricBlastFurnaceScreen, ElectricBlastFurnaceContainer> electricBlastFurnace;
	private IItemStackToitemStackRegistration<AdvancedCompressorScreen, AdvancedCompressorContainer> advancedCompressor;

	private List<RecipeCategory<?, ?>> categoires;
	private RecipeCategory<ItemStackToItemStackRecipeType<RollingRecipe>, RollingRecipe> rollingCategory;
	private RecipeCategory<ItemStackToItemStackRecipeType<ExtrudingRecipe>, ExtrudingRecipe> extrudingCategory;

	public JeiPlugin()
	{
		instance = this;

		this.categoires = new ArrayList<>();
		this.categoires.add(this.rollingCategory = new RecipeCategoryItemStackToItemStack<>(AddonRecipes.ROLLING, RollingRecipe.class));
		this.categoires.add(this.extrudingCategory = new RecipeCategoryItemStackToItemStack<>(AddonRecipes.EXTRUDING, ExtrudingRecipe.class));

		this.is2isRegistrations = new ArrayList<>();

		this.is2isRegistrations.add(this.electricBlastFurnace = new ItemStackToItemStackRegistration<>(ElectricBlastFurnaceScreen.class, ElectricBlastFurnaceContainer.class));
		this.electricBlastFurnace.getCategories().add(BlastingFurnaceJeiCategory.Uid);
		this.electricBlastFurnace.getItemstacks().add(new ItemStack(AddonBlocks.ELECTRIC_BLAST_FURNACE.get()));

		this.is2isRegistrations.add(this.advancedCompressor = new ItemStackToItemStackRegistration<>(AdvancedCompressorScreen.class, AdvancedCompressorContainer.class));
		this.advancedCompressor.getCategories().add(CompressorJeiCategory.Uid);
		this.advancedCompressor.getCategories().add(this.getRollingCategory().getUid());
		this.advancedCompressor.getCategories().add(this.getExtrudingCategory().getUid());
		this.advancedCompressor.getItemstacks().add(new ItemStack(AddonBlocks.ADVANCED_COMPRESSOR.get()));
	}

	@Override
	public ResourceLocation getPluginUid()
	{
		return BossToolsAddon.rl("jei");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{
		IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

		for (RecipeCategory<?, ?> recipeCategory : this.getCategoires())
		{
			recipeCategory.createGui(guiHelper);
			registration.addRecipeCategories(recipeCategory);
		}

	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration)
	{
		for (IItemStackToitemStackRegistration<?, ?> cr : this.getItemStackToItemStackRegistrations())
		{
			cr.addRecipeTransferHandler(registration);
		}

	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		for (IItemStackToitemStackRegistration<?, ?> cr : this.getItemStackToItemStackRegistrations())
		{
			cr.registerRecipeCatalysts(registration);
		}

	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
		for (IItemStackToitemStackRegistration<?, ?> cr : this.getItemStackToItemStackRegistrations())
		{
			cr.registerGuiHandlers(registration);
		}

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		for (RecipeCategory<?, ?> recipeCategory : this.getCategoires())
		{
			recipeCategory.registerRecipes(registration);
		}

		this.addIngredientInfo(registration, AddonBlocks.FUEL_LOADER.get(), AddonConfigs.Common.machines.fuelLoader_range.get(), ModInnet.FLUID_VEHICLE_FUEL_TAG.toString());
		this.addIngredientInfo(registration, AddonBlocks.GRAVITY_NORMALIZER.get());
	}

	public void addIngredientInfo(IRecipeRegistration registration, IItemProvider itemProvider, Object... objects)
	{
		registration.addIngredientInfo(new ItemStack(itemProvider), VanillaTypes.ITEM, new TranslationTextComponent(BossToolsAddon.tl(JEI_INFO, itemProvider.asItem().getRegistryName()), objects));
	}

	public List<IItemStackToitemStackRegistration<?, ?>> getItemStackToItemStackRegistrations()
	{
		return this.is2isRegistrations;
	}

	public List<RecipeCategory<?, ?>> getCategoires()
	{
		return Collections.unmodifiableList(this.categoires);
	}

	public RecipeCategory<ItemStackToItemStackRecipeType<RollingRecipe>, RollingRecipe> getRollingCategory()
	{
		return this.rollingCategory;
	}

	public RecipeCategory<ItemStackToItemStackRecipeType<ExtrudingRecipe>, ExtrudingRecipe> getExtrudingCategory()
	{
		return this.extrudingCategory;
	}

}
