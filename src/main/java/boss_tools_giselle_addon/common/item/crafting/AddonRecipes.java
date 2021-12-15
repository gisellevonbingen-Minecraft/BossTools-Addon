package boss_tools_giselle_addon.common.item.crafting;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;

public class AddonRecipes
{
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BossToolsAddon.MODID);
	
	public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_ROLLING = RECIPE_SERIALIZERS.register("rolling", () -> new RollingRecipeSerializer());
	public static final ItemStackToItemStackRecipeType<RollingRecipe> ROLLING = create(new ItemStackToItemStackRecipeType<>("rolling"));
	
	public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_EXTRUDING = RECIPE_SERIALIZERS.register("extruding", () -> new ExtrudingRecipeSerializer());
	public static final ItemStackToItemStackRecipeType<ExtrudingRecipe> EXTRUDING = create(new ItemStackToItemStackRecipeType<>("extruding"));

	private static <T extends BossToolsRecipeType<?>> T create(T value)
	{
		Registry.register(Registry.RECIPE_TYPE, BossToolsAddon.rl(value.getName()), value);
		return value;
	}

	private AddonRecipes()
	{

	}

}
