package boss_tools_giselle_addon.common.compat.jer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import mezz.jei.api.recipe.IFocus;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.mrscauthd.boss_tools.entity.alien.AlienTrade;

public class AlienTradeList extends LinkedList<AlienTradeList.Trade>
{
	private static final long serialVersionUID = 1L;

	private AlienEntry villagerEntry;

	public AlienTradeList(AlienEntry villagerEntry)
	{
		this.villagerEntry = villagerEntry;
	}

	public List<ItemStack> getCostAs()
	{
		return this.stream().map(Trade::getMinCostA).collect(Collectors.toList());
	}

	public List<ItemStack> getCostBs()
	{
		List<ItemStack> costBs = this.stream().map(Trade::getMinCostB).collect(Collectors.toList());
		return costBs.stream().anyMatch(ItemStack::isEmpty) ? orElseBarrier(costBs) : costBs;
	}

	private List<ItemStack> orElseBarrier(List<ItemStack> costBs)
	{
		return costBs.stream().map(AlienTradeList::orElseBarrier).collect(Collectors.toList());
	}

	public static ItemStack orElseBarrier(ItemStack is)
	{
		return is.isEmpty() ? new ItemStack(Items.BARRIER) : is;
	}

	public List<ItemStack> getResults()
	{
		return this.stream().map(Trade::getMinResult).collect(Collectors.toList());
	}

	public AlienTradeList getSubListSell(ItemStack itemStack)
	{
		return this.stream().filter(trade -> trade.sellsItem(itemStack)).collect(Collectors.toCollection(() -> new AlienTradeList(villagerEntry)));
	}

	public AlienTradeList getSubListBuy(ItemStack itemStack)
	{
		return this.stream().filter(trade -> trade.buysItem(itemStack)).collect(Collectors.toCollection(() -> new AlienTradeList(villagerEntry)));
	}

	public AlienTradeList getFocusedList(IFocus<ItemStack> focus)
	{
		if (focus == null)
		{
			return this;
		}
		else
		{
			switch (focus.getMode())
			{
				case INPUT:
					return getSubListBuy(focus.getValue());
				case OUTPUT:
					return getSubListSell(focus.getValue());
				default:
					return this;
			}
		}
	}

	private void addMerchantRecipe(MerchantOffers merchantOffers, AlienTrade.ITrade trade, Random rand)
	{
		MerchantOffer offer = trade.getOffer(villagerEntry.getAlienEntity(), rand);
		if (offer != null)
		{
			merchantOffers.add(offer);
		}
	}

	public void addITradeList(AlienTrade.ITrade[] tradeList)
	{
		for (AlienTrade.ITrade trade : tradeList)
		{
			MerchantOffers tempList = new MerchantOffers();
			Random rand = new Random();
			for (int itr = 0; itr < 100; itr++)
				addMerchantRecipe(tempList, trade, rand);
			if (tempList.size() == 0)
				return; // Bad lists be bad
			ItemStack costA = tempList.get(0).getCostA();
			ItemStack costB = tempList.get(0).getCostB();
			ItemStack result = tempList.get(0).getResult();
			int minCostA, minCostB, minResult;
			int maxCostA, maxCostB, maxResult;
			minCostA = maxCostA = costA.getCount();
			if (!costB.isEmpty())
				minCostB = maxCostB = costB.getCount();
			else
				minCostB = maxCostB = 1; // Needs to be one with the new ItemStack.EMPTY implementation
			minResult = maxResult = result.getCount();
			for (MerchantOffer merchantRecipe : tempList)
			{
				if (minCostA > merchantRecipe.getBaseCostA().getCount())
					minCostA = merchantRecipe.getCostA().getCount();
				if (!costB.isEmpty() && minCostB > merchantRecipe.getCostB().getCount())
					minCostB = merchantRecipe.getCostB().getCount();
				if (minResult > merchantRecipe.getResult().getCount())
					minResult = merchantRecipe.getResult().getCount();

				if (maxCostA < merchantRecipe.getCostA().getCount())
					maxCostA = merchantRecipe.getCostA().getCount();
				if (!costB.isEmpty() && maxCostB < merchantRecipe.getCostB().getCount())
					maxCostB = merchantRecipe.getCostA().getCount();
				if (maxResult < merchantRecipe.getResult().getCount())
					maxResult = merchantRecipe.getResult().getCount();
			}
			this.add(new Trade(costA, minCostA, maxCostA, costB, minCostB, maxCostB, result, minResult, maxResult));
		}
	}

	public static class Trade
	{
		private final ItemStack costA, costB, result;
		private final int minCostA, minCostB, minResult;
		private final int maxCostA, maxCostB, maxResult;

		Trade(ItemStack costA, int minCostA, int maxCostA, ItemStack costB, int minCostB, int maxCostB, ItemStack result, int minResult, int maxResult)
		{
			this.costA = costA;
			this.minCostA = minCostA;
			this.maxCostA = maxCostA;
			this.costB = costB;
			this.minCostB = minCostB;
			this.maxCostB = maxCostB;
			this.result = result;
			this.minResult = minResult;
			this.maxResult = maxResult;
		}

		public boolean sellsItem(ItemStack itemStack)
		{
			return this.result.sameItem(itemStack);
		}

		public boolean buysItem(ItemStack itemStack)
		{
			return this.costA.sameItem(itemStack) || (!this.costB.isEmpty() && this.costB.sameItem(itemStack));
		}

		public ItemStack getMinCostA()
		{
			ItemStack minBuyStack = this.costA.copy();
			minBuyStack.setCount(this.minCostA);
			return minBuyStack;
		}

		public ItemStack getMinCostB()
		{
			if (this.costB == null)
				return ItemStack.EMPTY;
			ItemStack minBuyStack = this.costB.copy();
			minBuyStack.setCount(this.minCostB);
			return minBuyStack;
		}

		public ItemStack getMinResult()
		{
			ItemStack minSellStack = this.result.copy();
			minSellStack.setCount(this.minResult);
			return minSellStack;
		}

		public ItemStack getMaxCostA()
		{
			ItemStack maxBuyStack = this.costA.copy();
			maxBuyStack.setCount(this.maxCostA);
			return maxBuyStack;
		}

		public ItemStack getMaxCostB()
		{
			if (this.costB == null)
				return ItemStack.EMPTY;
			ItemStack maxBuyStack = this.costB.copy();
			maxBuyStack.setCount(this.maxCostB);
			return maxBuyStack;
		}

		public ItemStack getMaxResult()
		{
			ItemStack maxSellStack = this.result.copy();
			maxSellStack.setCount(this.maxResult);
			return maxSellStack;
		}

		@Override
		public String toString()
		{
			return "Buy1: " + this.costA + ", Buy2: " + this.costB + ", Sell: " + this.result;
		}
	}
}
