package boss_tools_giselle_addon.common.compat.jer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import boss_tools_giselle_addon.common.BossToolsAddon;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import jeresources.compatibility.CompatBase;
import mezz.jei.api.recipe.IFocus;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.alien.AlienEntity;
import net.mrscauthd.boss_tools.entity.alien.AlienTrade;

public class AlienEntry
{
	protected final List<AlienTradeList> tradeList;
	protected final VillagerProfession profession;

	public AlienEntry(VillagerProfession profession, Int2ObjectMap<AlienTrade.ITrade[]> tradesLists)
	{
		this.profession = profession;
		this.tradeList = new LinkedList<>();
		addITradeLists(tradesLists);
	}

	public void addITradeLists(Int2ObjectMap<AlienTrade.ITrade[]> tradesLists)
	{
		for (int i = 1; i < tradesLists.size() + 1; i++)
		{
			AlienTrade.ITrade[] levelList = tradesLists.get(i);
			AlienTradeList trades = this.tradeList.size() > i ? this.tradeList.get(i) : new AlienTradeList(this);
			trades.addITradeList(levelList);
			this.tradeList.add(trades);
		}
	}

	public AlienTradeList getAlienTrade(int level)
	{
		if (tradeList.size() > level)
		{
			return tradeList.get(level);
		}
		else
		{
			return new AlienTradeList(null);
		}
	}

	public List<ItemStack> getInputs()
	{
		List<ItemStack> list = new LinkedList<>();
		for (List<AlienTradeList.Trade> trades : this.tradeList)
		{
			for (AlienTradeList.Trade trade : trades)
			{
				list.add(trade.getMinCostA());
				if (!trade.getMinCostB().isEmpty())
					list.add(trade.getMinCostB());
			}
		}
		return list;
	}

	public List<ItemStack> getOutputs()
	{
		List<ItemStack> list = new LinkedList<>();
		for (List<AlienTradeList.Trade> trades : this.tradeList)
			list.addAll(trades.stream().map(AlienTradeList.Trade::getMinResult).collect(Collectors.toList()));
		return list;
	}

	public int getMaxLevel()
	{
		return tradeList.size();
	}

	public String getName()
	{
		return this.profession.toString();
	}

	public String getDisplayName()
	{
		return "entity." + BossToolsAddon.PMODID + ".alien." + this.profession.toString();
	}

	public VillagerProfession getProfession()
	{
		return this.profession;
	}

	public List<Integer> getPossibleLevels(IFocus<ItemStack> focus)
	{
		List<Integer> levels = new ArrayList<>();
		for (int i = 0; i < tradeList.size(); i++)
			if (tradeList.get(i) != null && tradeList.get(i).getFocusedList(focus).size() > 0)
				levels.add(i);
		return levels;
	}

	public AlienEntity getAlienEntity()
	{
		AlienEntity alienEntity = (AlienEntity) ModInnet.ALIEN.get().create(CompatBase.getWorld());
		alienEntity.setVillagerData(alienEntity.getVillagerData().setProfession(this.getProfession()));
		return alienEntity;
	}

}
