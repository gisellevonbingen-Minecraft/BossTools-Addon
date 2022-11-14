package beyond_earth_giselle_addon.common.compat.jer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.compat.AddonCompatibleManager;
import beyond_earth_giselle_addon.common.compat.jer.WorldGenRegister.DimensionRestrictionPredicate;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.api.restrictions.Restriction.Type;
import jeresources.compatibility.CompatBase;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.Tags;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entities.alien.AlienEntity;
import net.mrscauthd.beyond_earth.entities.alien.AlienTrade;
import net.mrscauthd.beyond_earth.registries.EntitiesRegistry;
import net.mrscauthd.beyond_earth.registries.ItemsRegistry;
import net.mrscauthd.beyond_earth.world.oregen.OreGeneration;

@mezz.jei.api.JeiPlugin
public class AddonJeiPluginJer implements IModPlugin
{
	private static AddonJeiPluginJer instance;

	public static AddonJeiPluginJer instance()
	{
		return instance;
	}

	public AddonJeiPluginJer()
	{
		instance = this;
	}

	@Override
	public ResourceLocation getPluginUid()
	{
		return BeyondEarthAddon.rl("jer");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{
		if (AddonCompatibleManager.JER.isLoaded() == true)
		{
			IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
			registration.addRecipeCategories(new AlienCategory(guiHelper));
		}

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		if (AddonCompatibleManager.JER.isLoaded() == true)
		{
			this.registerAlienTradeRecipes(registration);
			this.registerWorldGenRecipes(registration);
		}

	}

	private void registerAlienTradeRecipes(IRecipeRegistration registration)
	{
		List<AlienWrapper> list = new ArrayList<>();

		for (Entry<VillagerProfession, Int2ObjectMap<ItemListing[]>> entry : AlienTrade.TRADES.entrySet())
		{
			VillagerProfession villagerProfession = entry.getKey();
			Int2ObjectMap<ItemListing[]> values = entry.getValue();

			if (values.values().stream().mapToInt(r -> r.length).sum() > 0)
			{
				AlienEntity alienEntity = EntitiesRegistry.ALIEN.get().create(CompatBase.getLevel());
				alienEntity.setVillagerData(alienEntity.getVillagerData().setProfession(villagerProfession));
				AlienEntry alienEntry = new AlienEntry(villagerProfession, values, alienEntity);

				if (alienEntry.tradeList.isEmpty() == false)
				{
					list.add(new AlienWrapper(alienEntry));
				}

			}

		}

		registration.addRecipes(AlienCategory.TYPE, list);
	}

	private void registerWorldGenRecipes(IRecipeRegistration registration)
	{
		WorldGenRegister worldGenRegister = new WorldGenRegister();
		worldGenRegister.drops.put(Ingredient.of(ItemTags.create(new ResourceLocation("forge", "ores/ice_shard"))), new ItemStack(ItemsRegistry.ICE_SHARD.get()));
		worldGenRegister.drops.put(Ingredient.of(ItemTags.create(new ResourceLocation("forge", "ores/desh"))), new ItemStack(ItemsRegistry.RAW_DESH.get()));
		worldGenRegister.drops.put(Ingredient.of(ItemTags.create(new ResourceLocation("forge", "ores/ostrum"))), new ItemStack(ItemsRegistry.RAW_OSTRUM.get()));
		worldGenRegister.drops.put(Ingredient.of(ItemTags.create(new ResourceLocation("forge", "ores/calorite"))), new ItemStack(ItemsRegistry.RAW_CALORITE.get()));
		worldGenRegister.drops.put(Ingredient.of(Tags.Items.ORES_COPPER), new ItemStack(Items.RAW_COPPER));
		worldGenRegister.drops.put(Ingredient.of(Tags.Items.ORES_IRON), new ItemStack(Items.RAW_IRON));
		worldGenRegister.drops.put(Ingredient.of(Tags.Items.ORES_GOLD), new ItemStack(Items.RAW_GOLD));
		worldGenRegister.drops.put(Ingredient.of(Tags.Items.ORES_COAL), new ItemStack(Items.COAL));
		worldGenRegister.drops.put(Ingredient.of(Tags.Items.ORES_LAPIS), new ItemStack(Items.LAPIS_LAZULI, 4));
		worldGenRegister.drops.put(Ingredient.of(Tags.Items.ORES_DIAMOND), new ItemStack(Items.DIAMOND));

		worldGenRegister.restrictions.put(DimensionRestrictionPredicate.byRuleTestEquals(OreGeneration.MOON_MATCH), getRestriction(Type.WHITELIST, BeyondEarthAddon.prl("moon")));
		worldGenRegister.restrictions.put(DimensionRestrictionPredicate.byRuleTestEquals(OreGeneration.MARS_MATCH), getRestriction(Type.WHITELIST, BeyondEarthAddon.prl("mars")));
		worldGenRegister.restrictions.put(DimensionRestrictionPredicate.byRuleTestEquals(OreGeneration.MERCURY_MATCH), getRestriction(Type.WHITELIST, BeyondEarthAddon.prl("mercury")));
		worldGenRegister.restrictions.put(DimensionRestrictionPredicate.byRuleTestEquals(OreGeneration.VENUS_MATCH), getRestriction(Type.WHITELIST, BeyondEarthAddon.prl("venus")));
		worldGenRegister.restrictions.put(DimensionRestrictionPredicate.byRuleTestEquals(OreGeneration.GLACIO_MATCH), getRestriction(Type.WHITELIST, BeyondEarthAddon.prl("glacio")));
		worldGenRegister.restrictions.put(DimensionRestrictionPredicate.byPathStartsWith("glacio_deepslate"), getRestriction(Type.WHITELIST, BeyondEarthAddon.prl("glacio")));

		for (Entry<ResourceKey<PlacedFeature>, PlacedFeature> entry : BuiltinRegistries.PLACED_FEATURE.entrySet())
		{
			ResourceLocation path = entry.getKey().location();

			if (path.getNamespace().equals(BeyondEarthMod.MODID) == true)
			{
				worldGenRegister.register(registration, path, entry.getValue());
			}

		}

	}

	public static DimensionRestriction getRestriction(Restriction.Type type, ResourceLocation dimension)
	{
		ResourceKey<Level> key = ResourceKey.create(Registry.DIMENSION_REGISTRY, dimension);
		return new DimensionRestriction(type, key);
	}

}
