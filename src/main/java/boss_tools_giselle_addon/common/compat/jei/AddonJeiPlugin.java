package boss_tools_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.client.gui.AdvancedCompressorScreen;
import boss_tools_giselle_addon.client.gui.ElectricBlastFurnaceScreen;
import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.inventory.container.AdvancedCompressorContainer;
import boss_tools_giselle_addon.common.inventory.container.ElectricBlastFurnaceContainer;
import boss_tools_giselle_addon.common.item.crafting.ExtrudingRecipe;
import boss_tools_giselle_addon.common.item.crafting.RollingRecipe;
import boss_tools_giselle_addon.common.item.crafting.SpaceStationRecipe;
import boss_tools_giselle_addon.common.registries.AddonBlocks;
import boss_tools_giselle_addon.common.registries.AddonRecipes;
import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity.CompressorMode;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.jei.JeiPlugin.BlastingFurnaceJeiCategory;
import net.mrscauthd.boss_tools.jei.JeiPlugin.CompressorJeiCategory;

@mezz.jei.api.JeiPlugin
public class AddonJeiPlugin implements IModPlugin
{
	public static final String JEI_CATEGORY = "jei.category";
	public static final String JEI_INFO = "jei.info";
	public static final String JEI_TOOLTIP = "jei.tooltip";

	private static AddonJeiPlugin instance;

	public static AddonJeiPlugin instance()
	{
		return instance;
	}

	public static final ResourceLocation createUid(ResourceLocation key)
	{
		return new ResourceLocation(key.getNamespace(), JEI_CATEGORY + "." + key.getPath());
	}

	public static final ITextComponent getCategoryTitle(ResourceLocation key)
	{
		return new TranslationTextComponent(JEI_CATEGORY + "." + key.getNamespace() + "." + key.getPath());
	}

	private final List<IIS2ISRegistration<?, ?>> is2isRegistrations;
	private IIS2ISRegistration<ElectricBlastFurnaceScreen, ElectricBlastFurnaceContainer> electricBlastFurnace;
	private IIS2ISRegistration<AdvancedCompressorScreen, AdvancedCompressorContainer> advancedCompressor;

	private final List<RecipeCategory<?>> categories;
	private RecipeCategorySpaceStation spaceStationCategory;
	private RecipeCategory<RollingRecipe> rollingCategory;
	private RecipeCategory<ExtrudingRecipe> extrudingCategory;
	private RecipeCategoryFuelLoader fuelLoaderCategory;

	private IDrawable fluidOverlay;

	public AddonJeiPlugin()
	{
		instance = this;

		this.categories = new ArrayList<>();
		this.is2isRegistrations = new ArrayList<>();
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
		this.categories.clear();
		this.is2isRegistrations.clear();

		this.categories.add(this.spaceStationCategory = new RecipeCategorySpaceStation(SpaceStationRecipe.class, AddonRecipes.SPACE_STATION));
		this.categories.add(this.rollingCategory = new RecipeCategoryItemStackToItemStack<>(RollingRecipe.class, AddonRecipes.ROLLING));
		this.categories.add(this.extrudingCategory = new RecipeCategoryItemStackToItemStack<>(ExtrudingRecipe.class, AddonRecipes.EXTRUDING));
		this.categories.add(this.fuelLoaderCategory = new RecipeCategoryFuelLoader(Fluid.class));

		AddonJeiCompressorModeHelper compressorModeHelper = AddonJeiCompressorModeHelper.INSTANCE;
		compressorModeHelper.register(CompressorMode.COMPRESSING, CompressorJeiCategory.Uid);
		compressorModeHelper.register(CompressorMode.ROLLING, this.getRollingCategory().getUid());
		compressorModeHelper.register(CompressorMode.EXTRUDING, this.getExtrudingCategory().getUid());

		this.is2isRegistrations.add(this.electricBlastFurnace = new IS2ISRegistration<>(ElectricBlastFurnaceScreen.class, ElectricBlastFurnaceContainer.class));
		this.electricBlastFurnace.getCategories().add(VanillaRecipeCategoryUid.BLASTING);
		this.electricBlastFurnace.getCategories().add(BlastingFurnaceJeiCategory.Uid);
		this.electricBlastFurnace.getItemstacks().add(new ItemStack(AddonBlocks.ELECTRIC_BLAST_FURNACE.get()));

		this.is2isRegistrations.add(this.advancedCompressor = new AdvancedCompressorRegistration(AdvancedCompressorScreen.class, AdvancedCompressorContainer.class));
		this.advancedCompressor.getCategories().add(CompressorJeiCategory.Uid);
		this.advancedCompressor.getCategories().add(this.getRollingCategory().getUid());
		this.advancedCompressor.getCategories().add(this.getExtrudingCategory().getUid());
		this.advancedCompressor.getItemstacks().add(new ItemStack(AddonBlocks.ADVANCED_COMPRESSOR.get()));

		this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();

		for (RecipeCategory<?> recipeCategory : this.getCategories())
		{
			recipeCategory.createGui(guiHelper);
			registration.addRecipeCategories(recipeCategory);
		}

	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategories())
		{
			recipeCategory.addRecipeTransferHandler(registration);
		}

		for (IIS2ISRegistration<?, ?> cr : this.getItemStackToItemStackRegistrations())
		{
			cr.addRecipeTransferHandler(registration);
		}

	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategories())
		{
			recipeCategory.registerRecipeCatalysts(registration);
		}

		for (IIS2ISRegistration<?, ?> cr : this.getItemStackToItemStackRegistrations())
		{
			cr.registerRecipeCatalysts(registration);
		}

	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategories())
		{
			recipeCategory.registerGuiHandlers(registration);
		}

		for (IIS2ISRegistration<?, ?> cr : this.getItemStackToItemStackRegistrations())
		{
			cr.registerGuiHandlers(registration);
		}

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategories())
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

	public List<IIS2ISRegistration<?, ?>> getItemStackToItemStackRegistrations()
	{
		return this.is2isRegistrations;
	}

	public List<RecipeCategory<?>> getCategories()
	{
		return Collections.unmodifiableList(this.categories);
	}

	public RecipeCategorySpaceStation getSpaceStationCategory()
	{
		return this.spaceStationCategory;
	}

	public RecipeCategory<RollingRecipe> getRollingCategory()
	{
		return this.rollingCategory;
	}

	public RecipeCategory<ExtrudingRecipe> getExtrudingCategory()
	{
		return this.extrudingCategory;
	}

	public RecipeCategoryFuelLoader getFuelLoaderCategory()
	{
		return this.fuelLoaderCategory;
	}

	public IDrawable getFluidOverlay()
	{
		return this.fluidOverlay;
	}

}
