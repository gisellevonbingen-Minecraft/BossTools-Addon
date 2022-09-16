package boss_tools_giselle_addon.common.registries;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipeDyedItem;
import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipeEnchantedBook;
import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipeEnchantedItem;
import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipeItemStack;
import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipeMap;
import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipePotionedItem;
import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipeType;
import boss_tools_giselle_addon.common.item.crafting.ExtrudingRecipe;
import boss_tools_giselle_addon.common.item.crafting.ExtrudingRecipeSerializer;
import boss_tools_giselle_addon.common.item.crafting.RollingRecipe;
import boss_tools_giselle_addon.common.item.crafting.RollingRecipeSerializer;
import boss_tools_giselle_addon.common.item.crafting.SpaceStationRecipe;
import boss_tools_giselle_addon.common.item.crafting.SpaceStationRecipeSerializer;
import boss_tools_giselle_addon.common.item.crafting.conditions.RecyclingEnabledCondition;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;

public class AddonRecipes
{
	private static final List<BossToolsRecipeType<?>> RECIPES = new ArrayList<>();
	public static final DeferredRegisterWrapper<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegisterWrapper.create(BossToolsAddon.MODID, ForgeRegistries.RECIPE_SERIALIZERS);

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_SPACE_STATION = RECIPE_SERIALIZERS.register("space_station", () -> new SpaceStationRecipeSerializer());
	public static final BossToolsRecipeType<SpaceStationRecipe> SPACE_STATION = create(new BossToolsRecipeType<>("space_station"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_ROLLING = RECIPE_SERIALIZERS.register("rolling", () -> new RollingRecipeSerializer());
	public static final ItemStackToItemStackRecipeType<RollingRecipe> ROLLING = create(new ItemStackToItemStackRecipeType<>("rolling"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_EXTRUDING = RECIPE_SERIALIZERS.register("extruding", () -> new ExtrudingRecipeSerializer());
	public static final ItemStackToItemStackRecipeType<ExtrudingRecipe> EXTRUDING = create(new ItemStackToItemStackRecipeType<>("extruding"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_ALIEN_TRADING_ITEMSTACK = RECIPE_SERIALIZERS.register("alien_trading_itemstack", () -> new AlienTradingRecipeItemStack.Serializer());
	public static final AlienTradingRecipeType<AlienTradingRecipeItemStack> ALIEN_TRADING_ITEMSTACK = create(new AlienTradingRecipeType<>("alien_trading_itemstack"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_ALIEN_TRADING_ENCHANTEDBOOK = RECIPE_SERIALIZERS.register("alien_trading_enchantedbook", () -> new AlienTradingRecipeEnchantedBook.Serializer());
	public static final AlienTradingRecipeType<AlienTradingRecipeEnchantedBook> ALIEN_TRADING_ENCHANTEDBOOK = create(new AlienTradingRecipeType<>("alien_trading_enchantedbook"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_ALIEN_TRADING_ENCHANTEDITEM = RECIPE_SERIALIZERS.register("alien_trading_enchanteditem", () -> new AlienTradingRecipeEnchantedItem.Serializer());
	public static final AlienTradingRecipeType<AlienTradingRecipeEnchantedItem> ALIEN_TRADING_ENCHANTEDITEM = create(new AlienTradingRecipeType<>("alien_trading_enchanteditem"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_ALIEN_TRADING_MAP = RECIPE_SERIALIZERS.register("alien_trading_map", () -> new AlienTradingRecipeMap.Serializer());
	public static final AlienTradingRecipeType<AlienTradingRecipeMap> ALIEN_TRADING_MAP = create(new AlienTradingRecipeType<>("alien_trading_map"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_ALIEN_TRADING_POTIONEDITEM = RECIPE_SERIALIZERS.register("alien_trading_potioneditem", () -> new AlienTradingRecipePotionedItem.Serializer());
	public static final AlienTradingRecipeType<AlienTradingRecipePotionedItem> ALIEN_TRADING_POTIONEDITEM = create(new AlienTradingRecipeType<>("alien_trading_potioneditem"));

	public static final RegistryObject<IRecipeSerializer<?>> RECIPE_SERIALIZER_ALIEN_TRADING_DYEDITEM = RECIPE_SERIALIZERS.register("alien_trading_dyeditem", () -> new AlienTradingRecipeDyedItem.Serializer());
	public static final AlienTradingRecipeType<AlienTradingRecipeDyedItem> ALIEN_TRADING_DYEDITEM = create(new AlienTradingRecipeType<>("alien_trading_dyeditem"));

	public static void register()
	{
		CraftingHelper.register(RecyclingEnabledCondition.Serializer.INSTANCE);

		RECIPES.forEach(r -> Registry.register(Registry.RECIPE_TYPE, BossToolsAddon.rl(r.getName()), r));
	}

	private static <T extends BossToolsRecipeType<?>> T create(T value)
	{
		RECIPES.add(value);
		return value;
	}

	private AddonRecipes()
	{

	}

}
