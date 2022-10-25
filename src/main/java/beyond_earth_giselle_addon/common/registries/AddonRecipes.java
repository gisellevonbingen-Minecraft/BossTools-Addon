package beyond_earth_giselle_addon.common.registries;

import java.util.function.Function;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.item.crafting.ExtrudingRecipe;
import beyond_earth_giselle_addon.common.item.crafting.ExtrudingRecipeSerializer;
import beyond_earth_giselle_addon.common.item.crafting.RecyclingBlastingRecipe;
import beyond_earth_giselle_addon.common.item.crafting.RecyclingSmeltingRecipe;
import beyond_earth_giselle_addon.common.item.crafting.RecyclingVanillaRecipeSerializer;
import beyond_earth_giselle_addon.common.item.crafting.RollingRecipe;
import beyond_earth_giselle_addon.common.item.crafting.RollingRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mrscauthd.beyond_earth.common.data.recipes.BeyondEarthRecipeType;
import net.mrscauthd.beyond_earth.common.data.recipes.ItemStackToItemStackRecipeType;

public class AddonRecipes
{
	public static final DeferredRegisterWrapper<RecipeType<?>> RECIPE_TYPES = DeferredRegisterWrapper.create(BeyondEarthAddon.MODID, ForgeRegistries.RECIPE_TYPES);
	public static final DeferredRegisterWrapper<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegisterWrapper.create(BeyondEarthAddon.MODID, ForgeRegistries.RECIPE_SERIALIZERS);

	public static final RegistryObject<RollingRecipeSerializer> RECIPE_SERIALIZER_ROLLING = RECIPE_SERIALIZERS.register("rolling", RollingRecipeSerializer::new);
	public static final RegistryObject<ItemStackToItemStackRecipeType<RollingRecipe>> ROLLING = register("rolling", ItemStackToItemStackRecipeType<RollingRecipe>::new);

	public static final RegistryObject<ExtrudingRecipeSerializer> RECIPE_SERIALIZER_EXTRUDING = RECIPE_SERIALIZERS.register("extruding", ExtrudingRecipeSerializer::new);
	public static final RegistryObject<ItemStackToItemStackRecipeType<ExtrudingRecipe>> EXTRUDING = register("extruding", ItemStackToItemStackRecipeType<ExtrudingRecipe>::new);

	public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_RECYCLING_SMELTING = RECIPE_SERIALIZERS.register("recycling_smelting", () -> new RecyclingVanillaRecipeSerializer<>(RecipeSerializer.SMELTING_RECIPE, RecyclingSmeltingRecipe::new));
	public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_RECYCLING_BLASTING = RECIPE_SERIALIZERS.register("recycling_blasting", () -> new RecyclingVanillaRecipeSerializer<>(RecipeSerializer.BLASTING_RECIPE, RecyclingBlastingRecipe::new));

	private static <T extends BeyondEarthRecipeType<?>> RegistryObject<T> register(String name, Function<String, T> func)
	{
		return RECIPE_TYPES.register(name, () -> func.apply(name));
	}

	private AddonRecipes()
	{

	}

}
