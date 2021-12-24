package boss_tools_giselle_addon.common.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Predicate;

import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.item.crafting.AddonRecipes;
import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipe;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.mrscauthd.boss_tools.entity.alien.AlienTrade;
import net.mrscauthd.boss_tools.entity.alien.AlienTrade.ITrade;

public class AddonAlienTrade implements ITrade
{
	public static final Map<VillagerProfession, Int2ObjectMap<ITrade[]>> VILLAGER_DEFAULT_TRADES = new HashMap<>();

	public static void initialize()
	{
		backupDefaultTrades();
	}

	public static void backupDefaultTrades()
	{
		VILLAGER_DEFAULT_TRADES.clear();

		for (Entry<VillagerProfession, Int2ObjectMap<ITrade[]>> entry : AlienTrade.VILLAGER_DEFAULT_TRADES.entrySet())
		{
			VillagerProfession job = entry.getKey();
			Int2ObjectMap<ITrade[]> values = new Int2ObjectOpenHashMap<>();

			for (int level : entry.getValue().keySet())
			{
				ITrade[] trades = entry.getValue().get(level);
				values.put(level, trades);
			}

			VILLAGER_DEFAULT_TRADES.put(job, values);
		}

	}

	public static void registerTrades(RecipeManager recipeManager)
	{
		Map<VillagerProfession, Int2ObjectMap<List<ITrade>>> jobMap = new HashMap<>();
		VillagerProfession[] jobs = ForgeRegistries.PROFESSIONS.getValues().toArray(new VillagerProfession[0]);

		for (VillagerProfession job : jobs)
		{
			jobMap.put(job, new Int2ObjectOpenHashMap<>());
		}

		if (AddonConfigs.Common.contents.alien_trade_removeDefaults.get() == false)
		{
			for (Entry<VillagerProfession, Int2ObjectMap<ITrade[]>> entry : VILLAGER_DEFAULT_TRADES.entrySet())
			{
				Int2ObjectMap<List<ITrade>> listMap = jobMap.get(entry.getKey());
				Int2ObjectMap<ITrade[]> values = entry.getValue();

				for (int level : values.keySet())
				{
					ITrade[] trades = values.get(level);
					listMap.computeIfAbsent(level, l -> new ArrayList<>()).addAll(Arrays.asList(trades));
				}

			}

		}

		List<AlienTradingRecipe> recipes = recipeManager.getAllRecipesFor(AddonRecipes.ALIEN_TRADING);

		for (AlienTradingRecipe recipe : recipes)
		{
			Int2ObjectMap<List<ITrade>> listMap = jobMap.get(recipe.getJob());
			listMap.computeIfAbsent(recipe.getLevel(), l -> new ArrayList<>()).add(new AddonAlienTrade(recipe));
		}

		Map<VillagerProfession, Int2ObjectMap<ITrade[]>> map = AlienTrade.VILLAGER_DEFAULT_TRADES;
		map.clear();

		for (Entry<VillagerProfession, Int2ObjectMap<List<ITrade>>> entry : jobMap.entrySet())
		{
			Int2ObjectMap<ITrade[]> listMap = map.computeIfAbsent(entry.getKey(), l -> new Int2ObjectOpenHashMap<>());
			Int2ObjectMap<List<ITrade>> values = entry.getValue();

			for (int level : values.keySet())
			{
				List<ITrade> trades = values.get(level);
				listMap.put(level, trades.toArray(new ITrade[0]));
			}

		}

	}

	public static void addReloadListener(AddReloadListenerEvent event)
	{
		RecipeManager recipeManager = event.getDataPackRegistries().getRecipeManager();

		event.addListener(new ISelectiveResourceReloadListener()
		{
			@Override
			public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate)
			{
				AddonAlienTrade.registerTrades(recipeManager);
			}

		});

	}

	private AlienTradingRecipe recipe;

	private AddonAlienTrade(AlienTradingRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public MerchantOffer getOffer(Entity entity, Random random)
	{
		AlienTradingRecipe recipe = this.getRecipe();
		return new MerchantOffer(recipe.getCostA(), recipe.getCostB(), recipe.getResult(), 0, recipe.getMaxUses(), recipe.getXP(), recipe.getPriceMultiplier());
	}

	public AlienTradingRecipe getRecipe()
	{
		return this.recipe;
	}

}
