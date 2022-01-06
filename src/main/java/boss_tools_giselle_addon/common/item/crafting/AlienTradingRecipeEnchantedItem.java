package boss_tools_giselle_addon.common.item.crafting;

import java.util.Random;

import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonObject;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class AlienTradingRecipeEnchantedItem extends AlienTradingRecipeItemStack
{
	private int levelBase = 5;
	private int levelRange = 15;

	private int costBaseLevelMultiplier = 1;
	private int costRangeBase = 0;
	private int costRangeLevelMultiplier = 0;

	public AlienTradingRecipeEnchantedItem(ResourceLocation id, JsonObject json)
	{
		super(id, json);

		this.levelBase = JSONUtils.getAsInt(json, "levelBase", this.levelBase);
		this.levelRange = JSONUtils.getAsInt(json, "levelRange", this.levelRange);

		this.costBaseLevelMultiplier = JSONUtils.getAsInt(json, "costBaseLevelMultiplier", this.costBaseLevelMultiplier);
		this.costRangeBase = JSONUtils.getAsInt(json, "costRangeBase", this.costRangeBase);
		this.costRangeLevelMultiplier = JSONUtils.getAsInt(json, "costRangeLevelMultiplier", this.costRangeLevelMultiplier);
	}

	public AlienTradingRecipeEnchantedItem(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);

		this.levelBase = buffer.readInt();
		this.levelRange = buffer.readInt();

		this.costBaseLevelMultiplier = buffer.readInt();
		this.costRangeBase = buffer.readInt();
		this.costRangeLevelMultiplier = buffer.readInt();
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		super.write(buffer);

		buffer.writeInt(this.levelBase);
		buffer.writeInt(this.levelRange);

		buffer.writeInt(this.costBaseLevelMultiplier);
		buffer.writeInt(this.costRangeBase);
		buffer.writeInt(this.costRangeLevelMultiplier);
	}

	@Override
	public Triple<ItemStack, ItemStack, ItemStack> getTrade(Entity trader, Random rand)
	{
		int level = this.getLevelBase();
		int bound1 = this.getLevelRange();
		ItemStack costA = this.getCostA();
		ItemStack costB = this.getCostB();

		if (bound1 > 0)
		{
			level += rand.nextInt(bound1);
		}

		int cost = costA.getCount() + level * this.getCostBaseLevelMultiplier();
		int bound2 = this.getCostRangeBase() + level * this.getCostRangeLevelMultiplier();

		if (bound2 > 0)
		{
			cost += rand.nextInt(bound2);
		}

		costA.setCount(Math.min(cost, costA.getMaxStackSize()));
		ItemStack result = EnchantmentHelper.enchantItem(rand, this.getResult(trader, rand), level, false);
		return Triple.of(costA, costB, result);
	}

	public int getLevelBase()
	{
		return this.levelBase;
	}

	public int getLevelRange()
	{
		return this.levelRange;
	}

	public int getCostBaseLevelMultiplier()
	{
		return this.costBaseLevelMultiplier;
	}

	public int getCostRangeBase()
	{
		return this.costRangeBase;
	}

	public int getCostRangeLevelMultiplier()
	{
		return this.costRangeLevelMultiplier;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_ALIEN_TRADING_ENCHANTEDITEM.get();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return AddonRecipes.ALIEN_TRADING_ENCHANTEDITEM;
	}

	public static class Serializer extends BossToolsRecipeSerializer<AlienTradingRecipeEnchantedItem>
	{
		@Override
		public AlienTradingRecipeEnchantedItem fromJson(ResourceLocation id, JsonObject json)
		{
			return new AlienTradingRecipeEnchantedItem(id, json);
		}

		@Override
		public AlienTradingRecipeEnchantedItem fromNetwork(ResourceLocation id, PacketBuffer buffer)
		{
			return new AlienTradingRecipeEnchantedItem(id, buffer);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, AlienTradingRecipeEnchantedItem recipe)
		{
			recipe.write(buffer);
		}

	}

}
