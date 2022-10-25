package beyond_earth_giselle_addon.common.compat.jei;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.client.gui.AdvancedCompressorScreen;
import beyond_earth_giselle_addon.client.gui.ElectricBlastFurnaceScreen;
import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity.CompressorMode;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.inventory.AdvancedCompressorContainerMenu;
import beyond_earth_giselle_addon.common.inventory.ElectricBlastFurnaceContainerMenu;
import beyond_earth_giselle_addon.common.item.crafting.ExtrudingRecipe;
import beyond_earth_giselle_addon.common.item.crafting.RollingRecipe;
import beyond_earth_giselle_addon.common.registries.AddonBlocks;
import beyond_earth_giselle_addon.common.registries.AddonMenuTypes;
import beyond_earth_giselle_addon.common.registries.AddonRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.common.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.beyond_earth.client.util.GuiHelper;
import net.mrscauthd.beyond_earth.common.data.recipes.CompressingRecipe;
import net.mrscauthd.beyond_earth.common.jei.Jei;
import net.mrscauthd.beyond_earth.common.registries.TagRegistry;

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

	public static ResourceLocation createUid(ResourceLocation key)
	{
		return new ResourceLocation(key.getNamespace(), JEI_CATEGORY + "." + key.getPath());
	}

	public static Component getCategoryTitle(ResourceLocation key)
	{
		return Component.translatable(JEI_CATEGORY + "." + key.getNamespace() + "." + key.getPath());
	}

	public static LoadingCache<Integer, IDrawableAnimated> createArrows(IGuiHelper guiHelper)
	{
		return CacheBuilder.newBuilder().build(new CacheLoader<>()
		{
			@Override
			public IDrawableAnimated load(Integer cookTime)
			{
				return guiHelper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17).buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
			}
		});
	}

	public static void drawText(PoseStack stack, IDrawable background, String text)
	{
		Minecraft mc = Minecraft.getInstance();
		Font font = mc.font;
		int stringWidth = font.width(text);
		font.draw(stack, text, background.getWidth() - 5 - stringWidth, background.getHeight() - font.lineHeight - 5, 0x808080);
	}

	public static void drawTextTime(PoseStack stack, IDrawable background, int ticks)
	{
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		String text = numberInstance.format(ticks / 20.0F) + "s";

		drawText(stack, background, text);
	}

	private final List<IIS2ISRegistration<?, ?>> is2isRegistrations;
	private IIS2ISRegistration<ElectricBlastFurnaceScreen, ElectricBlastFurnaceContainerMenu> electricBlastFurnace;
	private IIS2ISRegistration<AdvancedCompressorScreen, AdvancedCompressorContainerMenu> advancedCompressor;

	private final List<RecipeCategory<?>> categories;
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
		return BeyondEarthAddon.rl("jei");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{
		IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
		this.categories.clear();
		this.is2isRegistrations.clear();

		this.categories.add(this.rollingCategory = new RecipeCategoryItemStackToItemStack<>(RollingRecipe.class, AddonRecipes.ROLLING.get()));
		this.categories.add(this.extrudingCategory = new RecipeCategoryItemStackToItemStack<>(ExtrudingRecipe.class, AddonRecipes.EXTRUDING.get()));
		this.categories.add(this.fuelLoaderCategory = new RecipeCategoryFuelLoader(Fluid.class));

		AddonJeiCompressorModeHelper compressorModeHelper = AddonJeiCompressorModeHelper.INSTANCE;
		compressorModeHelper.register(CompressorMode.COMPRESSING, Jei.COMPRESS_TYPE.getUid());
		compressorModeHelper.register(CompressorMode.ROLLING, this.getRollingCategory().getRecipeType().getUid());
		compressorModeHelper.register(CompressorMode.EXTRUDING, this.getExtrudingCategory().getRecipeType().getUid());

		this.is2isRegistrations.add(this.electricBlastFurnace = new IS2ISRegistration<>(ElectricBlastFurnaceScreen.class, ElectricBlastFurnaceContainerMenu.class, AddonMenuTypes.ELECTRIC_BLAST_FURNACE.get()));
		this.electricBlastFurnace.getRecipeTypes().add(RecipeTypes.BLASTING);
		this.electricBlastFurnace.getItemstacks().add(new ItemStack(AddonBlocks.ELECTRIC_BLAST_FURNACE.get()));

		this.is2isRegistrations.add(this.advancedCompressor = new IS2ISRegistration<>(AdvancedCompressorScreen.class, AdvancedCompressorContainerMenu.class, AddonMenuTypes.ADVANCED_COMPRESSOR.get())
		{
			@Override
			public <R> IS2ISRecipeTransferInfo<AdvancedCompressorContainerMenu, R> ceateRecipeTransferInfo(IRecipeTransferRegistration registration, Class<AdvancedCompressorContainerMenu> containerClass, MenuType<AdvancedCompressorContainerMenu> menuType, RecipeType<R> recipeType)
			{
				return new AdvancedCompressorTransferInfo<>(containerClass, menuType, recipeType);
			}
		});
		this.advancedCompressor.getRecipeTypes().add(new RecipeType<>(Jei.COMPRESS_TYPE.getUid(), CompressingRecipe.class));
		this.advancedCompressor.getRecipeTypes().add(this.getRollingCategory().getRecipeType());
		this.advancedCompressor.getRecipeTypes().add(this.getExtrudingCategory().getRecipeType());
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

		this.addIngredientInfo(registration, AddonBlocks.FUEL_LOADER.get(), AddonConfigs.Common.machines.fuelLoader_range.get(), TagRegistry.FLUID_VEHICLE_FUEL_TAG.location());
	}

	public void addIngredientInfo(IRecipeRegistration registration, ItemLike itemLike, Object... objects)
	{
		registration.addIngredientInfo(new ItemStack(itemLike), VanillaTypes.ITEM_STACK, Component.translatable(BeyondEarthAddon.tl(JEI_INFO, ForgeRegistries.ITEMS.getKey(itemLike.asItem())), objects));
	}

	public List<IIS2ISRegistration<?, ?>> getItemStackToItemStackRegistrations()
	{
		return this.is2isRegistrations;
	}

	public List<RecipeCategory<?>> getCategories()
	{
		return Collections.unmodifiableList(this.categories);
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
