package beyond_earth_giselle_addon.common.registries;

import java.util.ArrayList;
import java.util.List;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.item.crafting.ExtrudingRecipe;
import beyond_earth_giselle_addon.common.item.crafting.ExtrudingRecipeSerializer;
import beyond_earth_giselle_addon.common.item.crafting.RecyclingBlastingRecipe;
import beyond_earth_giselle_addon.common.item.crafting.RecyclingSmeltingRecipe;
import beyond_earth_giselle_addon.common.item.crafting.RecyclingVanillaRecipeSerializer;
import beyond_earth_giselle_addon.common.item.crafting.RollingRecipe;
import beyond_earth_giselle_addon.common.item.crafting.RollingRecipeSerializer;
import beyond_earth_giselle_addon.common.item.crafting.conditions.RecyclingEnabledCondition;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mrscauthd.beyond_earth.crafting.BeyondEarthRecipeType;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipeType;

public class AddonRecipes
{
	private static final List<BeyondEarthRecipeType<?>> RECIPES = new ArrayList<>();
	public static final DeferredRegisterWrapper<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegisterWrapper.create(BeyondEarthAddon.MODID, ForgeRegistries.RECIPE_SERIALIZERS);

	public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_ROLLING = RECIPE_SERIALIZERS.register("rolling", () -> new RollingRecipeSerializer());
	public static final ItemStackToItemStackRecipeType<RollingRecipe> ROLLING = create(new ItemStackToItemStackRecipeType<>("rolling"));

	public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_EXTRUDING = RECIPE_SERIALIZERS.register("extruding", () -> new ExtrudingRecipeSerializer());
	public static final ItemStackToItemStackRecipeType<ExtrudingRecipe> EXTRUDING = create(new ItemStackToItemStackRecipeType<>("extruding"));

	public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_RECYCLING_SMELTING = RECIPE_SERIALIZERS.register("recycling_smelting", () -> new RecyclingVanillaRecipeSerializer<>(RecipeSerializer.SMELTING_RECIPE, RecyclingSmeltingRecipe::new));
	public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_RECYCLING_BLASTING = RECIPE_SERIALIZERS.register("recycling_blasting", () -> new RecyclingVanillaRecipeSerializer<>(RecipeSerializer.BLASTING_RECIPE, RecyclingBlastingRecipe::new));

	public static void register()
	{
		CraftingHelper.register(RecyclingEnabledCondition.Serializer.INSTANCE);

		RECIPES.forEach(r -> Registry.register(Registry.RECIPE_TYPE, BeyondEarthAddon.rl(r.getName()), r));
	}

	private static <T extends BeyondEarthRecipeType<?>> T create(T value)
	{
		RECIPES.add(value);
		return value;
	}

	private AddonRecipes()
	{

	}

}
