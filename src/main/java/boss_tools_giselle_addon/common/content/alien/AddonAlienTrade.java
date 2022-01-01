package boss_tools_giselle_addon.common.content.alien;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.lang3.tuple.Triple;

import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipe;
import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipeType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.entity.alien.AlienTrade;
import net.mrscauthd.boss_tools.entity.alien.AlienTrade.ITrade;

@SuppressWarnings("deprecation")
public class AddonAlienTrade implements ITrade
{
	public static void initialize()
	{

	}

	public static void registerTrades(RecipeManager recipeManager)
	{
		Map<VillagerProfession, Int2ObjectMap<List<ITrade>>> jobMap = new HashMap<>();
		VillagerProfession[] jobs = ForgeRegistries.PROFESSIONS.getValues().toArray(new VillagerProfession[0]);

		for (VillagerProfession job : jobs)
		{
			jobMap.put(job, new Int2ObjectOpenHashMap<>());
		}

		for (AlienTradingRecipeType<? extends AlienTradingRecipe> type : AlienTradingRecipeType.getTypes())
		{
			List<? extends AlienTradingRecipe> recipes = recipeManager.getAllRecipesFor(type);

			for (AlienTradingRecipe recipe : recipes)
			{
				Int2ObjectMap<List<ITrade>> listMap = jobMap.get(recipe.getJob());
				listMap.computeIfAbsent(recipe.getLevel(), l -> new ArrayList<>()).add(new AddonAlienTrade(recipe));
			}

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

		event.addListener(new IResourceManagerReloadListener()
		{
			@Override
			public void onResourceManagerReload(IResourceManager resourceManager)
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
		Triple<ItemStack, ItemStack, ItemStack> trade = recipe.getTrade(entity, random);
		return new MerchantOffer(trade.getLeft(), trade.getMiddle(), trade.getRight(), 0, recipe.getMaxUses(), recipe.getXP(), recipe.getPriceMultiplier());
	}

	public AlienTradingRecipe getRecipe()
	{
		return this.recipe;
	}

}
