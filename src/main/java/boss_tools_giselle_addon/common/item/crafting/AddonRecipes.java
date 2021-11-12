package boss_tools_giselle_addon.common.item.crafting;

import boss_tools_giselle_addon.BossToolsAddon;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;

public class AddonRecipes
{
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BossToolsAddon.MODID);
	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_PLATING = RECIPE_SERIALIZERS.register("plating", () -> new PlatingRecipeSerializer());
	public static final ItemStackToItemStackRecipeType<PlatingRecipe> PLATING = create(new ItemStackToItemStackRecipeType<>("plating"));

	private static <T extends BossToolsRecipeType<?>> T create(T value)
	{
		Registry.register(Registry.RECIPE_TYPE, BossToolsAddon.rl(value.getName()), value);
		return value;
	}

	private AddonRecipes()
	{

	}

}
