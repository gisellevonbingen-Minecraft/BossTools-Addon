package beyond_earth_giselle_addon.common.registries;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.item.crafting.ExtrudingRecipeSerializer;
import beyond_earth_giselle_addon.common.item.crafting.RollingRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AddonRecipeSerializers
{
	public static final DeferredRegisterWrapper<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegisterWrapper.create(BeyondEarthAddon.MODID, ForgeRegistries.RECIPE_SERIALIZERS);
	public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_ROLLING = RECIPE_SERIALIZERS.register("rolling", () -> new RollingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_EXTRUDING = RECIPE_SERIALIZERS.register("extruding", () -> new ExtrudingRecipeSerializer());

	private AddonRecipeSerializers()
	{

	}

}
