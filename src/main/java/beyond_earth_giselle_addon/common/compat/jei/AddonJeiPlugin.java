package beyond_earth_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beyond_earth_giselle_addon.client.gui.AdvancedCompressorScreen;
import beyond_earth_giselle_addon.client.gui.ElectricBlastFurnaceScreen;
import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.inventory.AdvancedCompressorContainerMenu;
import beyond_earth_giselle_addon.common.inventory.ElectricBlastFurnaceContainerMenu;
import beyond_earth_giselle_addon.common.item.crafting.ExtrudingRecipe;
import beyond_earth_giselle_addon.common.item.crafting.RollingRecipe;
import beyond_earth_giselle_addon.common.registries.AddonBlocks;
import beyond_earth_giselle_addon.common.registries.AddonRecipes;
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;
import net.mrscauthd.beyond_earth.jei.JeiPlugin.CompressorJeiCategory;

@mezz.jei.api.JeiPlugin
public class AddonJeiPlugin implements IModPlugin
{
	public static final String JEI_CATEGORY = "jei.category";
	public static final String JEI_INFO = "jei.info";

	private static AddonJeiPlugin instance;

	public static AddonJeiPlugin instance()
	{
		return instance;
	}

	public static final ResourceLocation createUid(ResourceLocation key)
	{
		return new ResourceLocation(key.getNamespace(), JEI_CATEGORY + "." + key.getPath());
	}

	public static final Component getCategoryTitle(ResourceLocation key)
	{
		return new TranslatableComponent(JEI_CATEGORY + "." + key.getNamespace() + "." + key.getPath());
	}

	private List<IItemStackToitemStackRegistration<?, ?>> is2isRegistrations;
	private IItemStackToitemStackRegistration<ElectricBlastFurnaceScreen, ElectricBlastFurnaceContainerMenu> electricBlastFurnace;
	private IItemStackToitemStackRegistration<AdvancedCompressorScreen, AdvancedCompressorContainerMenu> advancedCompressor;

	private List<RecipeCategory<?>> categoires;
	private RecipeCategory<RollingRecipe> rollingCategory;
	private RecipeCategory<ExtrudingRecipe> extrudingCategory;
	private RecipeCategoryFuelLoader fuelLoaderCategory;

	private IDrawable fluidOverlay;

	public AddonJeiPlugin()
	{
		instance = this;

		this.categoires = new ArrayList<>();
		this.categoires.add(this.rollingCategory = new RecipeCategoryItemStackToItemStack<>(RollingRecipe.class, AddonRecipes.ROLLING));
		this.categoires.add(this.extrudingCategory = new RecipeCategoryItemStackToItemStack<>(ExtrudingRecipe.class, AddonRecipes.EXTRUDING));
		this.categoires.add(this.fuelLoaderCategory = new RecipeCategoryFuelLoader(Fluid.class));

		this.is2isRegistrations = new ArrayList<>();

		this.is2isRegistrations.add(this.electricBlastFurnace = new ItemStackToItemStackRegistration<>(ElectricBlastFurnaceScreen.class, ElectricBlastFurnaceContainerMenu.class));
		this.electricBlastFurnace.getCategories().add(VanillaRecipeCategoryUid.BLASTING);
		this.electricBlastFurnace.getItemstacks().add(new ItemStack(AddonBlocks.ELECTRIC_BLAST_FURNACE.get()));

		this.is2isRegistrations.add(this.advancedCompressor = new ItemStackToItemStackRegistration<>(AdvancedCompressorScreen.class, AdvancedCompressorContainerMenu.class));
		this.advancedCompressor.getCategories().add(CompressorJeiCategory.Uid);
		this.advancedCompressor.getCategories().add(this.getRollingCategory().getUid());
		this.advancedCompressor.getCategories().add(this.getExtrudingCategory().getUid());
		this.advancedCompressor.getItemstacks().add(new ItemStack(AddonBlocks.ADVANCED_COMPRESSOR.get()));
	}

	@Override
	public ResourceLocation getPluginUid()
	{
		return BeyondEarthAddon.rl("jei");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{
		IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
		this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();

		for (RecipeCategory<?> recipeCategory : this.getCategoires())
		{
			recipeCategory.createGui(guiHelper);
			registration.addRecipeCategories(recipeCategory);
		}

	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategoires())
		{
			recipeCategory.addRecipeTransferHandler(registration);
		}

		for (IItemStackToitemStackRegistration<?, ?> cr : this.getItemStackToItemStackRegistrations())
		{
			cr.addRecipeTransferHandler(registration);
		}

	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategoires())
		{
			recipeCategory.registerRecipeCatalysts(registration);
		}

		for (IItemStackToitemStackRegistration<?, ?> cr : this.getItemStackToItemStackRegistrations())
		{
			cr.registerRecipeCatalysts(registration);
		}

	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategoires())
		{
			recipeCategory.registerGuiHandlers(registration);
		}

		for (IItemStackToitemStackRegistration<?, ?> cr : this.getItemStackToItemStackRegistrations())
		{
			cr.registerGuiHandlers(registration);
		}

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategoires())
		{
			recipeCategory.registerRecipes(registration);
		}

		this.addIngredientInfo(registration, AddonBlocks.FUEL_LOADER.get(), AddonConfigs.Common.machines.fuelLoader_range.get(), ModInit.FLUID_VEHICLE_FUEL_TAG.location());
		this.addIngredientInfo(registration, AddonBlocks.GRAVITY_NORMALIZER.get());
	}

	public void addIngredientInfo(IRecipeRegistration registration, ItemLike itemLike, Object... objects)
	{
		registration.addIngredientInfo(new ItemStack(itemLike), VanillaTypes.ITEM, new TranslatableComponent(BeyondEarthAddon.tl(JEI_INFO, itemLike.asItem().getRegistryName()), objects));
	}

	public List<IItemStackToitemStackRegistration<?, ?>> getItemStackToItemStackRegistrations()
	{
		return this.is2isRegistrations;
	}

	public List<RecipeCategory<?>> getCategoires()
	{
		return Collections.unmodifiableList(this.categoires);
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
