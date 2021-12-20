package beyond_earth_giselle_addon.common.compat.jei;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import beyond_earth_giselle_addon.client.gui.FuelLoaderScreen;
import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.block.AddonBlocks;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.beyond_earth.ModInnet;
import net.mrscauthd.beyond_earth.events.Methodes;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;

public class RecipeCategoryFuelLoader extends RecipeCategory<Fluid>
{
	public static final ResourceLocation BACKGROUND_LOCATION = BeyondEarthAddon.rl("textures/jei/fuel_loader.png");
	public static final int BACKGROUND_WIDTH = 144;
	public static final int BACKGROUND_HEIGHT = 84;
	public static final int TANK_LEFT = 55;
	public static final int TANK_TOP = 18;

	public static IDrawable createBackground(IGuiHelper guiHelper)
	{
		return guiHelper.createDrawable(BACKGROUND_LOCATION, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
	}

	private IDrawable background;

	public RecipeCategoryFuelLoader(Class<? extends Fluid> recipeClass)
	{
		super(recipeClass);
	}

	@Override
	public void createGui(IGuiHelper guiHelper)
	{
		super.createGui(guiHelper);
		this.background = createBackground(guiHelper);
	}

	@Override
	public IDrawable getBackground()
	{
		return this.background;
	}

	@Override
	public void setIngredients(Fluid recipe, IIngredients ingredients)
	{
		int capacity = AddonConfigs.Common.machines.fuelLoader_capacity.get();
		ingredients.setInputs(VanillaTypes.FLUID, Collections.singletonList(new FluidStack(recipe, capacity)));
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Fluid recipe, IIngredients ingredients)
	{
		int capacity = AddonConfigs.Common.machines.fuelLoader_capacity.get();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(0, true, TANK_LEFT, TANK_TOP, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT, capacity, false, AddonJeiPlugin.instance().getFluidOverlay());
		fluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
	}

	public boolean testFluid(Fluid fluid)
	{
		return Methodes.tagCheck(fluid, ModInnet.FLUID_VEHICLE_FUEL_TAG) && fluid.isSource(fluid.defaultFluidState());
	}

	@Override
	public List<ItemStack> getRecipeCatalystItemStacks()
	{
		List<ItemStack> list = super.getRecipeCatalystItemStacks();
		list.add(new ItemStack(AddonBlocks.FUEL_LOADER.get()));
		return list;
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		super.registerRecipes(registration);

		registration.addRecipes(ForgeRegistries.FLUIDS.getValues().stream().filter(this::testFluid).collect(Collectors.toList()), this.getUid());
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
		super.registerGuiHandlers(registration);

		registration.addGuiContainerHandler(FuelLoaderScreen.class, new FuelLoaderGuiContainerHandler());
	}

	@Override
	public ResourceLocation getKey()
	{
		return AddonBlocks.FUEL_LOADER.getId();
	}

}
