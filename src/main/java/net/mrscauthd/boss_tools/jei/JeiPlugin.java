package net.mrscauthd.boss_tools.jei;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.OxygenUtil;
import net.mrscauthd.boss_tools.crafting.BlastingRecipe;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.CompressingRecipe;
import net.mrscauthd.boss_tools.crafting.FuelRefiningRecipe;
import net.mrscauthd.boss_tools.crafting.GeneratingRecipe;
import net.mrscauthd.boss_tools.crafting.OxygenBubbleDistributorRecipe;
import net.mrscauthd.boss_tools.crafting.OxygenLoaderRecipe;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipe;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.helper.GridPlacer;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.gui.helper.IPlacer;
import net.mrscauthd.boss_tools.gui.helper.RocketPartGridPlacer;
import net.mrscauthd.boss_tools.gui.screens.blastfurnace.BlastFurnaceGui;
import net.mrscauthd.boss_tools.gui.screens.blastfurnace.BlastFurnaceGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.coalgenerator.CoalGeneratorGui;
import net.mrscauthd.boss_tools.gui.screens.coalgenerator.CoalGeneratorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.compressor.CompressorGui;
import net.mrscauthd.boss_tools.gui.screens.compressor.CompressorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.fuelrefinery.FuelRefineryGui;
import net.mrscauthd.boss_tools.gui.screens.fuelrefinery.FuelRefineryGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.nasaworkbench.NasaWorkbenchGui;
import net.mrscauthd.boss_tools.gui.screens.nasaworkbench.NasaWorkbenchGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.oxygenbubbledistributor.OxygenBubbleDistributorGui;
import net.mrscauthd.boss_tools.gui.screens.oxygenbubbledistributor.OxygenBubbleDistributorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.oxygenloader.OxygenLoaderGui;
import net.mrscauthd.boss_tools.gui.screens.oxygenloader.OxygenLoaderGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.planetselection.PlanetSelectionGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.rocket.RocketGui;
import net.mrscauthd.boss_tools.jei.jeiguihandlers.BlastFurnaceGuiContainerHandler;
import net.mrscauthd.boss_tools.jei.jeiguihandlers.CoalGeneratorGuiContainerHandler;
import net.mrscauthd.boss_tools.jei.jeiguihandlers.CompressorGuiContainerHandler;
import net.mrscauthd.boss_tools.jei.jeiguihandlers.PlanetSlecetionGuiJeiHandler;
import net.mrscauthd.boss_tools.machines.tile.BlastingFurnaceBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.CoalGeneratorBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.CompressorBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.FuelRefineryBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.NASAWorkbenchBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.OxygenMakingBlockEntity;
import net.mrscauthd.boss_tools.util.Rectangle2d;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
	public static IJeiHelpers jeiHelper;

	private Map<Fluid, List<ItemStack>> fluidFullItemStacks;
	private List<ItemStack> oxygenFullItemStacks;
	private List<Fluid> fuelTagFluids;

	private ClientLevel getLevel() {
		Minecraft mc = Minecraft.getInstance();
		return mc.level;
	}

	public List<ItemStack> getFluidFullItemStacks(Fluid fluid) {
		return this.fluidFullItemStacks.computeIfAbsent(fluid, this::generateFluidFullIngredients);
	}

	public List<ItemStack> getFluidFullItemStacks(Collection<Fluid> fluids) {
		return fluids.stream().flatMap(f -> this.getFluidFullItemStacks(f).stream()).collect(Collectors.toList());
	}

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(BossToolsMod.ModId, "default");
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		int inventorySlotCount = 36;
		// OxygenLoader
		registration.addRecipeTransferHandler(OxygenLoaderGui.GuiContainer.class, OxygenLoaderJeiCategory.Uid, OxygenMakingBlockEntity.SLOT_INPUT_SOURCE, 1, 0, inventorySlotCount);
		// OxygenBubbleDistributor
		registration.addRecipeTransferHandler(OxygenBubbleDistributorGui.GuiContainer.class, OxygenBubbleDistributorJeiCategory.Uid, OxygenMakingBlockEntity.SLOT_INPUT_SOURCE, 1, 0, inventorySlotCount);
		// Generator
		registration.addRecipeTransferHandler(CoalGeneratorGui.GuiContainer.class, CoalGeneratorJeiCategory.Uid, CoalGeneratorBlockEntity.SLOT_FUEL, 1, CoalGeneratorBlockEntity.SLOT_FUEL + 1, inventorySlotCount);
		// BlastFurnace
		int blastInventoryStartIndex = BlastingFurnaceBlockEntity.SLOT_FUEL + 1;
		registration.addRecipeTransferHandler(BlastFurnaceGui.GuiContainer.class, BlastingFurnaceJeiCategory.Uid, ItemStackToItemStackBlockEntity.SLOT_INGREDIENT, 1, blastInventoryStartIndex, inventorySlotCount);
		registration.addRecipeTransferHandler(BlastFurnaceGui.GuiContainer.class, VanillaRecipeCategoryUid.FUEL, BlastingFurnaceBlockEntity.SLOT_FUEL, 1, blastInventoryStartIndex, inventorySlotCount);
		// Compressor
		registration.addRecipeTransferHandler(CompressorGui.GuiContainer.class, CompressorJeiCategory.Uid, ItemStackToItemStackBlockEntity.SLOT_INGREDIENT, 1, ItemStackToItemStackBlockEntity.SLOT_OUTPUT + 1, inventorySlotCount);
		// WorkBench
		int workbenchPartSlotStart = 1 + NASAWorkbenchBlockEntity.SLOT_PARTS;
		int workbenchPartSlotCount = NASAWorkbenchBlockEntity.getBasicPartSlots();
		registration.addRecipeTransferHandler(NasaWorkbenchGui.GuiContainer.class, NASAWorkbenchJeiCategory.Uid, workbenchPartSlotStart, workbenchPartSlotCount, workbenchPartSlotStart + workbenchPartSlotCount, inventorySlotCount);
		// Fuel Refinery
		registration.addRecipeTransferHandler(FuelRefineryGui.GuiContainer.class, FuelRefineryJeiCategory.Uid, FuelRefineryBlockEntity.SLOT_INPUT_SOURCE, 1, 0, inventorySlotCount);
		// Rocket Fuel
		registration.addRecipeTransferHandler(RocketGui.GuiContainer.class, Tier1RocketJeiCategory.Uid, 0, 1, 0, inventorySlotCount);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(NasaWorkbenchGuiWindow.class, 108, 49, 14, 14, NASAWorkbenchJeiCategory.Uid);
		registration.addGuiContainerHandler(CoalGeneratorGuiWindow.class, new CoalGeneratorGuiContainerHandler());
		registration.addRecipeClickArea(FuelRefineryGuiWindow.class, FuelRefineryGuiWindow.ARROW_LEFT, FuelRefineryGuiWindow.ARROW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, FuelRefineryJeiCategory.Uid);
		registration.addGuiContainerHandler(BlastFurnaceGuiWindow.class, new BlastFurnaceGuiContainerHandler());
		registration.addGuiContainerHandler(CompressorGuiWindow.class, new CompressorGuiContainerHandler());
		registration.addRecipeClickArea(OxygenLoaderGuiWindow.class, OxygenLoaderGuiWindow.ARROW_LEFT, OxygenLoaderGuiWindow.ARROW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, OxygenLoaderJeiCategory.Uid);
		registration.addRecipeClickArea(OxygenBubbleDistributorGuiWindow.class, OxygenBubbleDistributorGuiWindow.ARROW_LEFT, OxygenBubbleDistributorGuiWindow.ARROW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, OxygenBubbleDistributorJeiCategory.Uid);

		registration.addGuiContainerHandler(PlanetSelectionGuiWindow.class, new PlanetSlecetionGuiJeiHandler());
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		jeiHelper = registration.getJeiHelpers();
		registration.addRecipeCategories(new OxygenLoaderJeiCategory(this, jeiHelper.getGuiHelper()));
		registration.addRecipeCategories(new OxygenBubbleDistributorJeiCategory(this, jeiHelper.getGuiHelper()));
		// Genrator
		registration.addRecipeCategories(new CoalGeneratorJeiCategory(jeiHelper.getGuiHelper()));
		// workbench
		registration.addRecipeCategories(new NASAWorkbenchJeiCategory(jeiHelper.getGuiHelper()));
		// BlastFurnace
		registration.addRecipeCategories(new BlastingFurnaceJeiCategory(jeiHelper.getGuiHelper()));
		// RocketTier1Gui
		registration.addRecipeCategories(new Tier1RocketJeiCategory(jeiHelper.getGuiHelper()));
		// RocketTier2Gui
		registration.addRecipeCategories(new Tier2RocketJeiCategory(jeiHelper.getGuiHelper()));
		// RocketItem3Gui
		registration.addRecipeCategories(new Tier3RocketJeiCategory(jeiHelper.getGuiHelper()));
		// Compressor
		registration.addRecipeCategories(new CompressorJeiCategory(jeiHelper.getGuiHelper()));
		// Fuel Maker
		registration.addRecipeCategories(new FuelRefineryJeiCategory(this, jeiHelper.getGuiHelper()));
		// Rover
		registration.addRecipeCategories(new RoverJeiCategory(jeiHelper.getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		this.fluidFullItemStacks = new HashMap<>();
		this.oxygenFullItemStacks = this.generateOxygenLoadingItems();
		this.fuelTagFluids = this.generateFuelTagFluids();

		List<FuelLoadingRecipe> fuelLoadingRecipes = this.generateFuelLoadingRecipes();

		// OxygenLoader
		registration.addRecipes(generateOxygenLoaderRecipes(), OxygenLoaderJeiCategory.Uid);
		// OxygenBubbleDistributor
		registration.addRecipes(generateOxygenBubbleDistributorRecipes(), OxygenBubbleDistributorJeiCategory.Uid);
		// Generator
		registration.addRecipes(generateGeneratorRecipes(), CoalGeneratorJeiCategory.Uid);
		// workbench
		registration.addRecipes(generateWorkbenchRecipes(), NASAWorkbenchJeiCategory.Uid);
		// BlastFurnace
		registration.addRecipes(generateBlastingFurnaceRecipes(), BlastingFurnaceJeiCategory.Uid);
		// RocketTier1Gui
		registration.addRecipes(fuelLoadingRecipes, Tier1RocketJeiCategory.Uid);
		// RocketTier2Gui
		registration.addRecipes(fuelLoadingRecipes, Tier2RocketJeiCategory.Uid);
		// RocketTier3Gui
		registration.addRecipes(fuelLoadingRecipes, Tier3RocketJeiCategory.Uid);
		// Rover
		registration.addRecipes(fuelLoadingRecipes, RoverJeiCategory.Uid);
		// Compressor
		registration.addRecipes(generateCompressingRecipes(), CompressorJeiCategory.Uid);
		// Fuel Maker
		registration.addRecipes(generateFuelMakerRecipes(), FuelRefineryJeiCategory.Uid);
		// Oil
		Component oilDescriptionKey = new TranslatableComponent("jei.tooltip." + BossToolsMod.ModId + ".oil");
		registration.addIngredientInfo(new ItemStack(ModInnet.OIL_BUCKET.get(), 1), VanillaTypes.ITEM, oilDescriptionKey);
		registration.addIngredientInfo(new FluidStack(ModInnet.OIL_STILL.get(), 1000), VanillaTypes.FLUID, oilDescriptionKey);
		// ...
	}

	// Oxygen Loading
	private List<ItemStack> generateOxygenLoadingItems() {
		return ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).filter(OxygenUtil::canReceive).map(OxygenUtil::makeFull).collect(Collectors.toList());
	}

	// Oxygen Loader
	private List<OxygenLoaderRecipe> generateOxygenLoaderRecipes() {
		return BossToolsRecipeTypes.OXYGENLOADER.getRecipes(this.getLevel());
	}

	// Oxygen Bubble Distributor
	private List<OxygenBubbleDistributorRecipe> generateOxygenBubbleDistributorRecipes() {
		return BossToolsRecipeTypes.OXYGENBUBBLEDISTRIBUTOR.getRecipes(this.getLevel());
	}

	// Generator
	private List<GeneratingRecipe> generateGeneratorRecipes() {
		return BossToolsRecipeTypes.GENERATING.getRecipes(this.getLevel());
	}

	// Workbench
	private List<WorkbenchingRecipe> generateWorkbenchRecipes() {
		return BossToolsRecipeTypes.WORKBENCHING.getRecipes(this.getLevel());
	}

	// BlastFurnace
	private List<BlastingRecipe> generateBlastingFurnaceRecipes() {
		return BossToolsRecipeTypes.BLASTING.getRecipes(this.getLevel());
	}

	// Compressor
	private List<CompressingRecipe> generateCompressingRecipes() {
		return BossToolsRecipeTypes.COMPRESSING.getRecipes(this.getLevel());
	}

	// Fuel Maker
	private List<ItemStack> generateFluidFullIngredients(Fluid fluid) {
		return ForgeRegistries.ITEMS.getValues().stream().map(i -> new ItemStack(i)).filter(is -> FluidUtil2.canFill(is, fluid)).map(is -> FluidUtil2.makeFull(is, fluid)).collect(Collectors.toList());
	}

	private List<FuelRefiningRecipe> generateFuelMakerRecipes() {
		return BossToolsRecipeTypes.FUELREFINING.getRecipes(this.getLevel());
	}

	// Fuel Loading
	private List<Fluid> generateFuelTagFluids() {
		return ForgeRegistries.FLUIDS.getValues().stream().filter(f -> f.isSource(f.defaultFluidState()) && Methodes.tagCheck(f, ModInnet.FLUID_VEHICLE_FUEL_TAG)).collect(Collectors.toList());
	}

	private List<FuelLoadingRecipe> generateFuelLoadingRecipes() {
		List<ItemStack> itemStacks = new ArrayList<>();

		for (Fluid fluid : this.fuelTagFluids) {
			itemStacks.add(new ItemStack(fluid.getBucket()));
		}

		FuelLoadingRecipe recipe = new FuelLoadingRecipe(itemStacks, this.fuelTagFluids);

		List<FuelLoadingRecipe> recipes = new ArrayList<>();
		recipes.add(recipe);
		return recipes;
	}

	public static class FuelLoadingRecipe {
		private final List<ItemStack> itemStacks;
		private final List<Fluid> fluids;

		public FuelLoadingRecipe(List<ItemStack> itemStacks, List<Fluid> fluids) {
			this.itemStacks = Collections.unmodifiableList(itemStacks);
			this.fluids = Collections.unmodifiableList(fluids);
		}

		public List<ItemStack> getItemStacks() {
			return this.itemStacks;
		}

		public List<FluidStack> getFluidStacks(int amount) {
			return this.getFluid().stream().map(f -> new FluidStack(f, amount)).collect(Collectors.toList());
		}

		public List<Fluid> getFluid() {
			return this.fluids;
		}

	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(ModInnet.OXYGEN_LOADER_BLOCK.get()), OxygenLoaderJeiCategory.Uid);
		// Neue maschine
		registration.addRecipeCatalyst(new ItemStack(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK.get()), OxygenBubbleDistributorJeiCategory.Uid);
		// Genrator
		registration.addRecipeCatalyst(new ItemStack(ModInnet.COAL_GENERATOR_BLOCK.get()), CoalGeneratorJeiCategory.Uid);
		// workbench
		registration.addRecipeCatalyst(new ItemStack(ModInnet.NASA_WORKBENCH_ITEM.get()), NASAWorkbenchJeiCategory.Uid);
		// BlastingFurnace
		registration.addRecipeCatalyst(new ItemStack(ModInnet.BLAST_FURNACE_BLOCK.get()), BlastingFurnaceJeiCategory.Uid, VanillaRecipeCategoryUid.FUEL);
		// RocketTier1Gui
		registration.addRecipeCatalyst(new ItemStack(ModInnet.TIER_1_ROCKET_ITEM.get()), Tier1RocketJeiCategory.Uid);
		// RocketTier2Gui
		registration.addRecipeCatalyst(new ItemStack(ModInnet.TIER_2_ROCKET_ITEM.get()), Tier2RocketJeiCategory.Uid);
		// RocketTier3Gui
		registration.addRecipeCatalyst(new ItemStack(ModInnet.TIER_3_ROCKET_ITEM.get()), Tier3RocketJeiCategory.Uid);
		// Compressor
		registration.addRecipeCatalyst(new ItemStack(ModInnet.COMPRESSOR_BLOCK.get()), CompressorJeiCategory.Uid);
		// FuelMaker
		registration.addRecipeCatalyst(new ItemStack(ModInnet.FUEL_REFINERY_BLOCK.get()), FuelRefineryJeiCategory.Uid);
		// Rover
		registration.addRecipeCatalyst(new ItemStack(ModInnet.ROVER_ITEM.get()), RoverJeiCategory.Uid);
	}

	public static class OxygenLoaderJeiCategory implements IRecipeCategory<OxygenLoaderRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BossToolsMod.ModId, "oxygen_loader");
		public static final int INPUT_TANK_LEFT = 8;
		public static final int INPUT_TANK_TOP = 8;
		public static final int OUTPUT_TANK_LEFT = 74;
		public static final int OUTPUT_TANK_TOP = 8;
		public static final int ENERGY_LEFT = 114;
		public static final int ENERGY_TOP = 8;

		private final JeiPlugin plugin;
		private final Component title;
		private final IDrawable background;
		private final IDrawable fluidOverlay;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public OxygenLoaderJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
			this.plugin = plugin;
			this.title = new TranslatableComponent("container." + BossToolsMod.ModId + ".oxygen_loader");
			this.background = guiHelper.createDrawable(new ResourceLocation(BossToolsMod.ModId, "textures/jei/oxygen_loader_jei.png"), 0, 0, 148, 64);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public List<Component> getTooltipStrings(OxygenLoaderRecipe recipe, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getUsingPerTickText(GaugeValueHelper.getEnergy(FuelRefineryBlockEntity.ENERGY_PER_TICK)).build());
			} else if (GuiHelper.isHover(this.getOutputTankBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getValueText(GaugeValueHelper.getOxygen(recipe.getOxygen())).build());
			}

			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends OxygenLoaderRecipe> getRecipeClass() {
			return OxygenLoaderRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void draw(OxygenLoaderRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, stack, mouseX, mouseY);

			this.cachedEnergies.getUnchecked(200).draw(stack, ENERGY_LEFT, ENERGY_TOP);
			GuiHelper.drawOxygenTank(stack, OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP, 1.0D);
		}

		@Override
		public void setIngredients(OxygenLoaderRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputs(VanillaTypes.ITEM, this.plugin.getFluidFullItemStacks(recipe.getInput().getFluids()));
			iIngredients.setOutputs(VanillaTypes.ITEM, this.plugin.oxygenFullItemStacks);

			iIngredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getInput().toStacks()));
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, OxygenLoaderRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
			itemStacks.init(FuelRefineryBlockEntity.SLOT_INPUT_SOURCE, true, 24, 8);
			itemStacks.init(FuelRefineryBlockEntity.SLOT_OUTPUT_SINK, false, 90, 38);
			itemStacks.init(FuelRefineryBlockEntity.SLOT_INPUT_SINK, false, 24, 38);
			itemStacks.init(FuelRefineryBlockEntity.SLOT_OUTPUT_SOURCE, false, 90, 8);

			itemStacks.set(FuelRefineryBlockEntity.SLOT_INPUT_SOURCE, iIngredients.getInputs(VanillaTypes.ITEM).stream().flatMap(Collection::stream).collect(Collectors.toList()));
			itemStacks.set(FuelRefineryBlockEntity.SLOT_OUTPUT_SINK, iIngredients.getOutputs(VanillaTypes.ITEM).stream().flatMap(Collection::stream).collect(Collectors.toList()));

			IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
			int tanks = 0;

			fluidStacks.init(tanks, true, INPUT_TANK_LEFT, INPUT_TANK_TOP, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT, 1, false, this.fluidOverlay);
			fluidStacks.set(tanks, iIngredients.getInputs(VanillaTypes.FLUID).stream().flatMap(Collection::stream).collect(Collectors.toList()));
			tanks++;
		}

		public Rectangle2d getInputTankBounds() {
			return GuiHelper.getFluidTankBounds(INPUT_TANK_LEFT, INPUT_TANK_TOP);
		}

		public Rectangle2d getOutputTankBounds() {
			return GuiHelper.getFluidTankBounds(OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}
	}

	public static class OxygenBubbleDistributorJeiCategory implements IRecipeCategory<OxygenBubbleDistributorRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BossToolsMod.ModId, "oxygen_bubble_distributor");
		public static final int INPUT_TANK_LEFT = 8;
		public static final int INPUT_TANK_TOP = 8;
		public static final int OUTPUT_TANK_LEFT = 74;
		public static final int OUTPUT_TANK_TOP = 8;
		public static final int ENERGY_LEFT = 114;
		public static final int ENERGY_TOP = 8;

		private final JeiPlugin plugin;
		private final Component title;
		private final IDrawable background;
		private final IDrawable fluidOverlay;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public OxygenBubbleDistributorJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
			this.plugin = plugin;
			this.title = new TranslatableComponent("container." + BossToolsMod.ModId + ".oxygen_bubble_distributor");
			this.background = guiHelper.createDrawable(new ResourceLocation(BossToolsMod.ModId, "textures/jei/oxygen_bubble_distributor_jei.png"), 0, 0, 148, 64);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public List<Component> getTooltipStrings(OxygenBubbleDistributorRecipe recipe, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getUsingPerTickText(GaugeValueHelper.getEnergy(FuelRefineryBlockEntity.ENERGY_PER_TICK)).build());
			} else if (GuiHelper.isHover(this.getOutputTankBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getValueText(GaugeValueHelper.getOxygen(recipe.getOxygen())).build());
			}

			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends OxygenBubbleDistributorRecipe> getRecipeClass() {
			return OxygenBubbleDistributorRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void draw(OxygenBubbleDistributorRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, stack, mouseX, mouseY);

			this.cachedEnergies.getUnchecked(200).draw(stack, ENERGY_LEFT, ENERGY_TOP);
			GuiHelper.drawOxygenTank(stack, OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP, 1.0D);
		}

		@Override
		public void setIngredients(OxygenBubbleDistributorRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputs(VanillaTypes.ITEM, this.plugin.getFluidFullItemStacks(recipe.getInput().getFluids()));
			iIngredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getInput().toStacks()));
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, OxygenBubbleDistributorRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
			itemStacks.init(FuelRefineryBlockEntity.SLOT_INPUT_SOURCE, true, 24, 8);
			itemStacks.init(FuelRefineryBlockEntity.SLOT_INPUT_SINK, false, 24, 38);

			itemStacks.set(FuelRefineryBlockEntity.SLOT_INPUT_SOURCE, iIngredients.getInputs(VanillaTypes.ITEM).stream().flatMap(Collection::stream).collect(Collectors.toList()));

			IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
			int tanks = 0;

			fluidStacks.init(tanks, true, INPUT_TANK_LEFT, INPUT_TANK_TOP, GuiHelper.OXYGEN_TANK_WIDTH, GuiHelper.OXYGEN_TANK_HEIGHT, 1, false, this.fluidOverlay);
			fluidStacks.set(tanks, iIngredients.getInputs(VanillaTypes.FLUID).stream().flatMap(Collection::stream).collect(Collectors.toList()));
			tanks++;
		}

		public Rectangle2d getInputTankBounds() {
			return GuiHelper.getFluidTankBounds(INPUT_TANK_LEFT, INPUT_TANK_TOP);
		}

		public Rectangle2d getOutputTankBounds() {
			return GuiHelper.getFluidTankBounds(OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}
	}

	// Genrator
	public static class CoalGeneratorJeiCategory implements IRecipeCategory<GeneratingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BossToolsMod.ModId, "generatorcategory");// muss klein geschrieben sein
		public static final int FIRE_LEFT = 45;
		public static final int FIRE_TOP = 45;
		public static final int ENERGY_LEFT = 103;
		public static final int ENERGY_TOP = 15;

		// ...
		private final Component title;
		private final IDrawable background;
		private final LoadingCache<Integer, IDrawableAnimated> fires;
		private final LoadingCache<Integer, IDrawableAnimated> energies;

		public CoalGeneratorJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslatableComponent("container." + BossToolsMod.ModId + ".coal_generator");
			this.background = guiHelper.createDrawable(new ResourceLocation(BossToolsMod.ModId, "textures/jei/generator_gui_jei.png"), 0, 0, 144, 84);
			this.fires = createFires(guiHelper);
			this.energies = createGeneratingEnergies(guiHelper);
		}

		@Override
		public List<Component> getTooltipStrings(GeneratingRecipe recipe, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getFireBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getValueText(GaugeValueHelper.getBurnTime(recipe.getBurnTime())).build());
			} else if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getGeneratingPerTickText(GaugeValueHelper.getEnergy(CoalGeneratorBlockEntity.ENERGY_PER_TICK)).build());
			}
			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends GeneratingRecipe> getRecipeClass() {
			return GeneratingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		public Rectangle2d getFireBounds() {
			return GuiHelper.getFireBounds(FIRE_LEFT, FIRE_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}

		@Override
		public void draw(GeneratingRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, stack, mouseX, mouseY);

			int burnTime = recipe.getBurnTime();
			this.fires.getUnchecked(burnTime).draw(stack, FIRE_LEFT, FIRE_TOP);
			this.energies.getUnchecked(200).draw(stack, ENERGY_LEFT, ENERGY_TOP);
			drawTextTime(stack, this.getBackground(), burnTime);
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(GeneratingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputIngredients(recipe.getIngredients());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, GeneratingRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(CoalGeneratorBlockEntity.SLOT_FUEL, true, 44, 25);// Numern wie im GUI
			// ...

			stacks.set(CoalGeneratorBlockEntity.SLOT_FUEL, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
			// ...
		}
	}

	// workbench
	public static class NASAWorkbenchJeiCategory implements IRecipeCategory<WorkbenchingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BossToolsMod.ModId, "workbenchcategory"); // muss klein sein !
		// ...
		private final Component title;
		private final IDrawable background;

		public NASAWorkbenchJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslatableComponent("container." + BossToolsMod.ModId + ".nasa_workbench");
			this.background = guiHelper.createDrawable(new ResourceLocation(BossToolsMod.ModId, "textures/jei/nasaworkbenchjei.png"), 0, 0, 176, 122);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends WorkbenchingRecipe> getRecipeClass() {
			return WorkbenchingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(WorkbenchingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputIngredients(recipe.getIngredients());
			iIngredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, WorkbenchingRecipe recipe, IIngredients iIngredients) {
			IDrawable background = this.getBackground();
			iRecipeLayout.moveRecipeTransferButton(background.getWidth() - 20, background.getHeight() - 20);

			int slots = NASAWorkbenchBlockEntity.SLOT_PARTS;
			GridPlacer placer = new GridPlacer();
			slots = placeRcketParts(slots, 38, 7, 1, placer::placeBottom, ModInnet.ROCKET_PART_NOSE.get(), iRecipeLayout, recipe);
			slots = placeRcketParts(slots, 29, 25, 2, placer::placeBottom, ModInnet.ROCKET_PART_BODY.get(), iRecipeLayout, recipe);
			slots = placeRcketParts(slots, 29, 79, 1, placer::placeRight, ModInnet.ROCKET_PART_TANK.get(), iRecipeLayout, recipe);
			slots = placeRcketParts(slots, 11, 79, 1, placer::placeBottom, ModInnet.ROCKET_PART_FIN_LEFT.get(), iRecipeLayout, recipe);
			slots = placeRcketParts(slots, 65, 79, 1, placer::placeBottom, ModInnet.ROCKET_PART_FIN_RIGHT.get(), iRecipeLayout, recipe);
			slots = placeRcketParts(slots, 38, 97, 1, placer::placeBottom, ModInnet.ROCKET_PART_ENGINE.get(), iRecipeLayout, recipe);

			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(slots, false, 126, 72);
			stacks.set(slots, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
			slots++;
		}
	}

	public static int placeRcketParts(int slot, int left, int top, int mod, IPlacer placer, RocketPart part, IRecipeLayout iRecipeLayout, WorkbenchingRecipe recipe) {
		IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
		List<Ingredient> ingredients = recipe.getParts().get(part);

		return RocketPartGridPlacer.place(slot, left, top, mod, placer, part, (i, s, bounds) -> {
			Ingredient ingredient = (ingredients != null && i < ingredients.size()) ? ingredients.get(i) : Ingredient.EMPTY;
			stacks.init(s, true, bounds.getX(), bounds.getY());
			stacks.set(s, Lists.newArrayList(ingredient.getItems()));
		});
	}

	public static IDrawableStatic createFireStatic(IGuiHelper guiHelper) {
		return drawableBuilder(guiHelper, GuiHelper.FIRE_PATH, GuiHelper.FIRE_WIDTH, GuiHelper.FIRE_HEIGHT).build();
	}

	public static IDrawableAnimated createFireAnimated(IGuiHelper guiHelper) {
		return createFireAnimated(guiHelper, 200);
	}

	public static IDrawableAnimated createFireAnimated(IGuiHelper guiHelper, int ticks) {
		return createFireAnimated(guiHelper, createFireStatic(guiHelper), ticks);
	}

	public static IDrawableAnimated createFireAnimated(IGuiHelper guiHelper, IDrawableStatic fireStatic, int ticks) {
		return guiHelper.createAnimatedDrawable(fireStatic, ticks, IDrawableAnimated.StartDirection.TOP, true);
	}

	public static IDrawableBuilder drawableBuilder(IGuiHelper guiHelper, ResourceLocation path, int width, int height) {
		return guiHelper.drawableBuilder(path, 0, 0, width, height).setTextureSize(width, height);
	}

	public static LoadingCache<Integer, IDrawableAnimated> createFires(IGuiHelper guiHelper) {
		return CacheBuilder.newBuilder().build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer time) {
				return drawableBuilder(guiHelper, GuiHelper.FIRE_PATH, GuiHelper.FIRE_WIDTH, GuiHelper.FIRE_HEIGHT).buildAnimated(time, IDrawableAnimated.StartDirection.TOP, true);
			}
		});
	}

	public static LoadingCache<Integer, IDrawableAnimated> createArrows(IGuiHelper guiHelper) {
		return CacheBuilder.newBuilder().build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer time) {
				return drawableBuilder(guiHelper, GuiHelper.ARROW_PATH, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT).buildAnimated(time, IDrawableAnimated.StartDirection.LEFT, false);
			}
		});
	}

	public static LoadingCache<Integer, IDrawableAnimated> createEnergies(IGuiHelper guiHelper, boolean inverted) {
		return CacheBuilder.newBuilder().build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer time) {
				return drawableBuilder(guiHelper, GuiHelper.ENERGY_PATH, GuiHelper.ENERGY_WIDTH, GuiHelper.ENERGY_HEIGHT).buildAnimated(time, inverted ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.BOTTOM, inverted);
			}
		});

	}

	public static LoadingCache<Integer, IDrawableAnimated> createUsingEnergies(IGuiHelper guiHelper) {
		return createEnergies(guiHelper, true);
	}

	public static LoadingCache<Integer, IDrawableAnimated> createGeneratingEnergies(IGuiHelper guiHelper) {
		return createEnergies(guiHelper, false);
	}

	public static void drawText(PoseStack stack, IDrawable background, String text) {
		Minecraft mc = Minecraft.getInstance();
		Font font = mc.font;
		int stringWidth = font.width(text);
		font.draw(stack, text, background.getWidth() - 5 - stringWidth, background.getHeight() - font.lineHeight - 5, 0x808080);
	}

	public static void drawTextTime(PoseStack stack, IDrawable background, int ticks) {
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		String text = numberInstance.format(ticks / 20.0F) + "s";

		drawText(stack, background, text);
	}

	// BlastingFurnace
	public static class BlastingFurnaceJeiCategory implements IRecipeCategory<BlastingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BossToolsMod.ModId, "blastingfurnacecategory");
		public static final int FIRE_LEFT = 37;
		public static final int FIRE_TOP = 37;
		public static final int ARROW_LEFT = 55;
		public static final int ARROW_TOP = 35;

		private final Component title;
		private final IDrawable background;
		private final LoadingCache<Integer, IDrawableAnimated> fire;
		private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

		public BlastingFurnaceJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslatableComponent("container." + BossToolsMod.ModId + ".blast_furnace");
			this.background = guiHelper.createDrawable(new ResourceLocation(BossToolsMod.ModId, "textures/jei/blast_furnace_gui_jei.png"), 0, 0, 144, 84);
			this.fire = createFires(guiHelper);
			this.cachedArrows = createArrows(guiHelper);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends BlastingRecipe> getRecipeClass() {
			return BlastingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public void draw(BlastingRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, stack, mouseX, mouseY);

			int cookTime = recipe.getCookTime();
			this.fire.getUnchecked(cookTime).draw(stack, FIRE_LEFT, FIRE_TOP);
			this.cachedArrows.getUnchecked(cookTime).draw(stack, ARROW_LEFT, ARROW_TOP);
			drawTextTime(stack, this.getBackground(), cookTime);
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(BlastingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputIngredients(recipe.getIngredients());
			iIngredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, BlastingRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(ItemStackToItemStackBlockEntity.SLOT_INGREDIENT, true, 36, 16);// Iron
			stacks.init(ItemStackToItemStackBlockEntity.SLOT_OUTPUT, false, 86, 35);// steel
			// ...

			stacks.set(ItemStackToItemStackBlockEntity.SLOT_INGREDIENT, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
			stacks.set(ItemStackToItemStackBlockEntity.SLOT_OUTPUT, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
			// ...
		}
	}

	// RocketTier1Gui
	public static class Tier1RocketJeiCategory implements IRecipeCategory<FuelLoadingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BossToolsMod.ModId, "rocket_t_1_category");

		private final Component title;
		private final IDrawable background;

		public Tier1RocketJeiCategory(IGuiHelper guiHelper) {
			this.title = new TextComponent("Tier 1 Rocket");
			this.background = guiHelper.createDrawable(new ResourceLocation(BossToolsMod.ModId, "textures/jei/rocket_gui_jei.png"), 0, 0, 128, 71);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelLoadingRecipe> getRecipeClass() {
			return FuelLoadingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		public int getCapacity() {
			return FluidUtil2.BUCKET_SIZE * RocketTier1Entity.FUEL_BUCKETS;
		}

		@Override
		public void setIngredients(FuelLoadingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(recipe.getItemStacks()));
			iIngredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getFluidStacks(this.getCapacity())));
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, FuelLoadingRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
			itemStacks.init(0, true, 13, 18);
			itemStacks.set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));

			IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
			fluidStacks.init(0, true, 66, 12, 46, 46, this.getCapacity(), false, null);
			fluidStacks.set(0, iIngredients.getInputs(VanillaTypes.FLUID).get(0));
		}
	}

	// RocketTier2Gui
	public static class Tier2RocketJeiCategory implements IRecipeCategory<FuelLoadingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BossToolsMod.ModId, "rocket_t_2_category");

		private final Component title;
		private final IDrawable background;

		public Tier2RocketJeiCategory(IGuiHelper guiHelper) {
			this.title = new TextComponent("Tier 2 Rocket");
			this.background = guiHelper.createDrawable(new ResourceLocation(BossToolsMod.ModId, "textures/jei/rocket_gui_jei.png"), 0, 0, 128, 71);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelLoadingRecipe> getRecipeClass() {
			return FuelLoadingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		public int getCapacity() {
			return FluidUtil2.BUCKET_SIZE * RocketTier2Entity.FUEL_BUCKETS;
		}

		@Override
		public void setIngredients(FuelLoadingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(recipe.getItemStacks()));
			iIngredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getFluidStacks(this.getCapacity())));
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, FuelLoadingRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
			itemStacks.init(0, true, 13, 18);
			itemStacks.set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));

			IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
			fluidStacks.init(0, true, 66, 12, 46, 46, this.getCapacity(), false, null);
			fluidStacks.set(0, iIngredients.getInputs(VanillaTypes.FLUID).get(0));
		}
	}

	// RocketTier3Gui
	public static class Tier3RocketJeiCategory implements IRecipeCategory<FuelLoadingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BossToolsMod.ModId, "rocket_t_3_category");

		private final Component title;
		private final IDrawable background;

		public Tier3RocketJeiCategory(IGuiHelper guiHelper) {
			this.title = new TextComponent("Tier 3 Rocket");
			this.background = guiHelper.createDrawable(new ResourceLocation(BossToolsMod.ModId, "textures/jei/rocket_gui_jei.png"), 0, 0, 128, 71);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelLoadingRecipe> getRecipeClass() {
			return FuelLoadingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		public int getCapacity() {
			return FluidUtil2.BUCKET_SIZE * RocketTier3Entity.FUEL_BUCKETS;
		}

		@Override
		public void setIngredients(FuelLoadingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(recipe.getItemStacks()));
			iIngredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getFluidStacks(this.getCapacity())));
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, FuelLoadingRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
			itemStacks.init(0, true, 13, 18);
			itemStacks.set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));

			IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
			fluidStacks.init(0, true, 66, 12, 46, 46, this.getCapacity(), false, null);
			fluidStacks.set(0, iIngredients.getInputs(VanillaTypes.FLUID).get(0));
		}
	}

	// Compressor
	public static class CompressorJeiCategory implements IRecipeCategory<CompressingRecipe> {

		public static final ResourceLocation Uid = new ResourceLocation(BossToolsMod.ModId, "compressorcategory");
		public static final int ARROW_LEFT = 36;
		public static final int ARROW_TOP = 29;
		public static final int ENERGY_LEFT = 103;
		public static final int ENERGY_TOP = 15;
		// ...
		private final Component title;
		private final IDrawable background;
		private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public CompressorJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslatableComponent("container." + BossToolsMod.ModId + ".compressor");
			this.background = guiHelper.createDrawable(new ResourceLocation(BossToolsMod.ModId, "textures/jei/compressor_gui_jei.png"), 0, 0, 144, 84);
			this.cachedArrows = createArrows(guiHelper);
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public List<Component> getTooltipStrings(CompressingRecipe recipe, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList((GaugeTextHelper.getUsingPerTickText(GaugeValueHelper.getEnergy(CompressorBlockEntity.ENERGY_PER_TICK)).build()));
			} else {
				return Collections.emptyList();
			}

		}

		private Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends CompressingRecipe> getRecipeClass() {
			return CompressingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public void draw(CompressingRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, stack, mouseX, mouseY);

			int cookTime = recipe.getCookTime();
			this.cachedArrows.getUnchecked(cookTime).draw(stack, ARROW_LEFT, ARROW_TOP);
			this.cachedEnergies.getUnchecked(cookTime).draw(stack, ENERGY_LEFT, ENERGY_TOP);
			drawTextTime(stack, this.getBackground(), cookTime);
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void setIngredients(CompressingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputIngredients(recipe.getIngredients());
			iIngredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, CompressingRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
			stacks.init(ItemStackToItemStackBlockEntity.SLOT_INGREDIENT, true, 14, 29);
			stacks.init(ItemStackToItemStackBlockEntity.SLOT_OUTPUT, false, 69, 28);
			// ...

			stacks.set(ItemStackToItemStackBlockEntity.SLOT_INGREDIENT, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
			stacks.set(ItemStackToItemStackBlockEntity.SLOT_OUTPUT, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
			// ...
		}
	}

	// FuelMaker
	public static class FuelRefineryJeiCategory implements IRecipeCategory<FuelRefiningRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BossToolsMod.ModId, "fuelmakercategory");
		public static final int INPUT_TANK_LEFT = 8;
		public static final int INPUT_TANK_TOP = 8;
		public static final int OUTPUT_TANK_LEFT = 74;
		public static final int OUTPUT_TANK_TOP = 8;
		public static final int ENERGY_LEFT = 114;
		public static final int ENERGY_TOP = 8;

		private final JeiPlugin plugin;
		private final Component title;
		private final IDrawable background;
		private final IDrawable fluidOverlay;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public FuelRefineryJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
			this.plugin = plugin;
			this.title = new TranslatableComponent("container." + BossToolsMod.ModId + ".fuel_refinery");
			this.background = guiHelper.createDrawable(new ResourceLocation(BossToolsMod.ModId, "textures/jei/fuel_refinery_jei.png"), 0, 0, 148, 64);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public List<Component> getTooltipStrings(FuelRefiningRecipe recipe, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getUsingPerTickText(GaugeValueHelper.getEnergy(FuelRefineryBlockEntity.ENERGY_PER_TICK)).build());
			} else {
				return Collections.emptyList();
			}

		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelRefiningRecipe> getRecipeClass() {
			return FuelRefiningRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		@Override
		public void draw(FuelRefiningRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, stack, mouseX, mouseY);

			this.cachedEnergies.getUnchecked(200).draw(stack, ENERGY_LEFT, ENERGY_TOP);
		}

		@Override
		public void setIngredients(FuelRefiningRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputs(VanillaTypes.ITEM, this.plugin.getFluidFullItemStacks(recipe.getInput().getFluids()));
			iIngredients.setOutputs(VanillaTypes.ITEM, this.plugin.getFluidFullItemStacks(recipe.getOutput().getFluids()));

			iIngredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getInput().toStacks()));
			iIngredients.setOutputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getOutput().toStacks()));
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, FuelRefiningRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
			itemStacks.init(FuelRefineryBlockEntity.SLOT_INPUT_SOURCE, true, 24, 8);
			itemStacks.init(FuelRefineryBlockEntity.SLOT_OUTPUT_SINK, false, 90, 38);
			itemStacks.init(FuelRefineryBlockEntity.SLOT_INPUT_SINK, false, 24, 38);
			itemStacks.init(FuelRefineryBlockEntity.SLOT_OUTPUT_SOURCE, false, 90, 8);

			itemStacks.set(FuelRefineryBlockEntity.SLOT_INPUT_SOURCE, iIngredients.getInputs(VanillaTypes.ITEM).stream().flatMap(i -> i.stream()).collect(Collectors.toList()));
			itemStacks.set(FuelRefineryBlockEntity.SLOT_OUTPUT_SINK, iIngredients.getOutputs(VanillaTypes.ITEM).stream().flatMap(i -> i.stream()).collect(Collectors.toList()));

			IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
			int tanks = 0;

			fluidStacks.init(tanks, true, INPUT_TANK_LEFT, INPUT_TANK_TOP, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT, 1, false, this.fluidOverlay);
			fluidStacks.set(tanks, iIngredients.getInputs(VanillaTypes.FLUID).get(0));
			tanks++;

			fluidStacks.init(tanks, false, OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT, 1, false, this.fluidOverlay);
			fluidStacks.set(tanks, iIngredients.getOutputs(VanillaTypes.FLUID).get(0));
			tanks++;
		}

		public Rectangle2d getInputTankBounds() {
			return GuiHelper.getFluidTankBounds(INPUT_TANK_LEFT, INPUT_TANK_TOP);
		}

		public Rectangle2d getOutputTankBounds() {
			return GuiHelper.getFluidTankBounds(OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}
	}

	// Rover
	public static class RoverJeiCategory implements IRecipeCategory<FuelLoadingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BossToolsMod.ModId, "rovercategory");

		private final Component title;
		private final IDrawable background;
		private final IDrawable fluidOverlay;

		public RoverJeiCategory(IGuiHelper guiHelper) {
			this.title = new TextComponent("Rover");
			this.background = guiHelper.createDrawable(new ResourceLocation(BossToolsMod.ModId, "textures/jei/rover_jei.png"), 0, 0, 144, 84);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelLoadingRecipe> getRecipeClass() {
			return FuelLoadingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		public int getCapacity() {
			return FluidUtil2.BUCKET_SIZE * RoverEntity.FUEL_BUCKETS;
		}

		@Override
		public void setIngredients(FuelLoadingRecipe recipe, IIngredients iIngredients) {
			iIngredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(recipe.getItemStacks()));
			iIngredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getFluidStacks(this.getCapacity())));
		}

		@Override
		public void setRecipe(IRecipeLayout iRecipeLayout, FuelLoadingRecipe recipe, IIngredients iIngredients) {
			IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
			itemStacks.init(0, true, 7, 59);
			itemStacks.set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));

			IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();
			fluidStacks.init(0, true, 9, 8, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT, this.getCapacity(), false, this.fluidOverlay);
			fluidStacks.set(0, iIngredients.getInputs(VanillaTypes.FLUID).get(0));
		}
	}

}