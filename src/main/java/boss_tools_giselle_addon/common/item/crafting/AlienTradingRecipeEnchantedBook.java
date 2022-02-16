package boss_tools_giselle_addon.common.item.crafting;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonObject;

import boss_tools_giselle_addon.common.registries.AddonRecipes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class AlienTradingRecipeEnchantedBook extends AlienTradingRecipeItemStackBase
{
	private int costBaseLevelMultiplier = 3;
	private int costRangeBase = 5;
	private int costRangeLevelMultiplier = 10;
	private int costTreasureOnlyMultiplier = 2;

	public AlienTradingRecipeEnchantedBook(ResourceLocation id, JsonObject json)
	{
		super(id, json);

		this.costBaseLevelMultiplier = JSONUtils.getAsInt(json, "costBaseLevelMultiplier", this.costBaseLevelMultiplier);
		this.costRangeBase = JSONUtils.getAsInt(json, "costRangeBase", this.costRangeBase);
		this.costRangeLevelMultiplier = JSONUtils.getAsInt(json, "costRangeLevelMultiplier", this.costRangeLevelMultiplier);
		this.costTreasureOnlyMultiplier = JSONUtils.getAsInt(json, "costTreasureOnlyMultiplier", this.costTreasureOnlyMultiplier);
	}

	public AlienTradingRecipeEnchantedBook(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);

		this.costBaseLevelMultiplier = buffer.readInt();
		this.costRangeBase = buffer.readInt();
		this.costRangeLevelMultiplier = buffer.readInt();
		this.costTreasureOnlyMultiplier = buffer.readInt();
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		super.write(buffer);

		buffer.writeInt(this.costBaseLevelMultiplier);
		buffer.writeInt(this.costRangeBase);
		buffer.writeInt(this.costRangeLevelMultiplier);
		buffer.writeInt(this.costTreasureOnlyMultiplier);
	}

	@Override
	public Triple<ItemStack, ItemStack, ItemStack> getTrade(Entity trader, Random rand)
	{
		List<Enchantment> list = ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(Enchantment::isTradeable).collect(Collectors.toList());
		Enchantment enchantment = list.get(rand.nextInt(list.size()));
		int level = MathHelper.nextInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());

		ItemStack costA = this.getCostA();
		ItemStack costB = this.getCostB();
		int cost = costA.getCount() + (level * this.getCostBaseLevelMultiplier());
		int bound = this.getCostRangeBase() + (level * this.getCostRangeLevelMultiplier());

		if (bound > 0)
		{
			cost += rand.nextInt(bound);
		}

		if (enchantment.isTreasureOnly() == true)
		{
			cost *= this.getCostTreasureOnlyMultiplier();
		}

		costA.setCount(Math.min(cost, costA.getMaxStackSize()));
		ItemStack itemstack = EnchantedBookItem.createForEnchantment(new EnchantmentData(enchantment, level));
		return Triple.of(costA, costB, itemstack);
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

	public int getCostTreasureOnlyMultiplier()
	{
		return this.costTreasureOnlyMultiplier;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_ALIEN_TRADING_ENCHANTEDBOOK.get();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return AddonRecipes.ALIEN_TRADING_ENCHANTEDBOOK;
	}

	public static class Serializer extends BossToolsRecipeSerializer<AlienTradingRecipeEnchantedBook>
	{
		@Override
		public AlienTradingRecipeEnchantedBook fromJson(ResourceLocation id, JsonObject json)
		{
			return new AlienTradingRecipeEnchantedBook(id, json);
		}

		@Override
		public AlienTradingRecipeEnchantedBook fromNetwork(ResourceLocation id, PacketBuffer buffer)
		{
			return new AlienTradingRecipeEnchantedBook(id, buffer);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, AlienTradingRecipeEnchantedBook recipe)
		{
			recipe.write(buffer);
		}

	}

}
