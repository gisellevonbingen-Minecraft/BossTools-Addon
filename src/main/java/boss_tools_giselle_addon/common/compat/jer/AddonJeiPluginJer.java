package boss_tools_giselle_addon.common.compat.jer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.compat.AddonCompatibleManager;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.api.restrictions.Restriction.Type;
import jeresources.compatibility.CompatBase;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.Tags;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.alien.AlienEntity;
import net.mrscauthd.boss_tools.entity.alien.AlienTrade;
import net.mrscauthd.boss_tools.entity.alien.AlienTrade.ITrade;
import net.mrscauthd.boss_tools.world.oregen.RuleTests;

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
		return BossToolsAddon.rl("jer");
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

		for (Entry<VillagerProfession, Int2ObjectMap<ITrade[]>> entry : AlienTrade.VILLAGER_DEFAULT_TRADES.entrySet())
		{
			VillagerProfession villagerProfession = entry.getKey();
			Int2ObjectMap<ITrade[]> values = entry.getValue();

			if (values.values().stream().mapToInt(r -> r.length).sum() > 0)
			{
				AlienEntity alienEntity = (AlienEntity) ModInnet.ALIEN.get().create(CompatBase.getWorld());
				alienEntity.setVillagerData(alienEntity.getVillagerData().setProfession(villagerProfession));
				AlienEntry alienEntry = new AlienEntry(villagerProfession, values, alienEntity);

				if (alienEntry.tradeList.isEmpty() == false)
				{
					list.add(new AlienWrapper(alienEntry));
				}

			}

		}

		registration.addRecipes(list, AlienCategory.Uid);
	}

	private void registerWorldGenRecipes(IRecipeRegistration registration)
	{
		WorldGenRegister worldGenRegister = new WorldGenRegister();
		worldGenRegister.drops.put(Ingredient.of(ModInnet.MOON_GLOWSTONE_ORE.get()), new ItemStack(Items.GLOWSTONE_DUST));
		worldGenRegister.drops.put(Ingredient.of(ModInnet.MARS_ICE_SHARD_ORE.get()), new ItemStack(ModInnet.ICE_SHARD.get()));
		worldGenRegister.drops.put(Ingredient.of(Tags.Items.ORES_DIAMOND), new ItemStack(Items.DIAMOND));
		worldGenRegister.drops.put(Ingredient.of(Tags.Items.ORES_COAL), new ItemStack(Items.COAL));

		worldGenRegister.restrictions.put(t -> t instanceof RuleTests.MoonRuleTest, getRestriction(Type.WHITELIST, BossToolsAddon.prl("moon")));
		worldGenRegister.restrictions.put(t -> t instanceof RuleTests.MarsRuleTest, getRestriction(Type.WHITELIST, BossToolsAddon.prl("mars")));
		worldGenRegister.restrictions.put(t -> t instanceof RuleTests.MercuryRuleTest, getRestriction(Type.WHITELIST, BossToolsAddon.prl("mercury")));
		worldGenRegister.restrictions.put(t -> t instanceof RuleTests.VenusRuleTest, getRestriction(Type.WHITELIST, BossToolsAddon.prl("venus")));

		for (Entry<RegistryKey<ConfiguredFeature<?, ?>>, ConfiguredFeature<?, ?>> entry : WorldGenRegistries.CONFIGURED_FEATURE.entrySet())
		{
			ResourceLocation path = entry.getKey().location();

			if (path.getNamespace().equals(BossToolsMod.ModId) == true)
			{
				worldGenRegister.register(registration, path, entry.getValue());
			}

		}

	}

	public static Restriction getRestriction(Restriction.Type type, ResourceLocation dimension)
	{
		RegistryKey<World> key = RegistryKey.create(Registry.DIMENSION_REGISTRY, dimension);
		return new Restriction(new DimensionRestriction(type, key));
	}

}
