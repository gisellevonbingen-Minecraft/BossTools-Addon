package boss_tools_giselle_addon.common.item.crafting;

import boss_tools_giselle_addon.common.BossToolsAddon;
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

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_ROLLING = RECIPE_SERIALIZERS.register("rolling", () -> new RollingRecipeSerializer());
	public static final ItemStackToItemStackRecipeType<RollingRecipe> ROLLING = create(new ItemStackToItemStackRecipeType<>("rolling"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_EXTRUDING = RECIPE_SERIALIZERS.register("extruding", () -> new ExtrudingRecipeSerializer());
	public static final ItemStackToItemStackRecipeType<ExtrudingRecipe> EXTRUDING = create(new ItemStackToItemStackRecipeType<>("extruding"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_ALIEN_TRADING_ITEMSTACK = RECIPE_SERIALIZERS.register("alien_trading_itemstack", () -> new AlienTradingRecipeItemStack.Serializer());
	public static final AlienTradingRecipeType<AlienTradingRecipeItemStack> ALIEN_TRADING_ITEMSTACK = create(new AlienTradingRecipeType<>("alien_trading_itemstack"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_ALIEN_TRADING_ENCHANTEDBOOK = RECIPE_SERIALIZERS.register("alien_trading_enchantedbook", () -> new AlienTradingRecipeEnchantedBook.Serializer());
	public static final AlienTradingRecipeType<AlienTradingRecipeEnchantedBook> ALIEN_TRADING_ENCHANTEDBOOK = create(new AlienTradingRecipeType<>("alien_trading_enchantedbook"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_ALIEN_TRADING_MAP = RECIPE_SERIALIZERS.register("alien_trading_map", () -> new AlienTradingRecipeMap.Serializer());
	public static final AlienTradingRecipeType<AlienTradingRecipeMap> ALIEN_TRADING_MAP = create(new AlienTradingRecipeType<>("alien_trading_map"));

	private static <T extends BossToolsRecipeType<?>> T create(T value)
	{
		Registry.register(Registry.RECIPE_TYPE, BossToolsAddon.rl(value.getName()), value);
		return value;
	}

	private AddonRecipes()
	{

	}

}
