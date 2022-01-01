package boss_tools_giselle_addon.common.item.crafting;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonObject;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class AlienTradingRecipeEnchantedBook extends AlienTradingRecipe
{
	private int costBase = 2;
	private int costBaseLevelMultiplier = 10;
	private int costRangeBase = 5;
	private int costRangeLevelMultiplier = 3;
	private int costTreasureOnlyMultiplier = 2;

	private ItemStack costA;
	private ItemStack costB;

	public AlienTradingRecipeEnchantedBook(ResourceLocation id, JsonObject json)
	{
		super(id, json);

		this.costBase = JSONUtils.getAsInt(json, "costBase", this.costBase);
		this.costBaseLevelMultiplier = JSONUtils.getAsInt(json, "costBaseLevelMultiplier", this.costBaseLevelMultiplier);
		this.costRangeBase = JSONUtils.getAsInt(json, "costRangeBase", this.costRangeBase);
		this.costRangeLevelMultiplier = JSONUtils.getAsInt(json, "costRangeLevelMultiplier", this.costRangeLevelMultiplier);
		this.costTreasureOnlyMultiplier = JSONUtils.getAsInt(json, "costTreasureOnlyMultiplier", this.costTreasureOnlyMultiplier);

		this.costA = new ItemStack(Items.EMERALD);
		this.costB = new ItemStack(Items.BOOK);
		this.costA = json.has("costA") ? CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "costA"), true) : this.costA;
		this.costB = json.has("costB") ? CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "costB"), true) : this.costB;
	}

	public AlienTradingRecipeEnchantedBook(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		super.write(buffer);
	}

	@Override
	public Triple<ItemStack, ItemStack, ItemStack> getTrade(Entity trader, Random rand)
	{
		List<Enchantment> list = ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(Enchantment::isTradeable).collect(Collectors.toList());
		Enchantment enchantment = list.get(rand.nextInt(list.size()));
		int level = MathHelper.nextInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
		
		int cost = this.getCostBase() + (level * this.getCostRangeLevelMultiplier());
		cost += rand.nextInt(this.getCostRangeBase() + (level * this.getCostBaseLevelMultiplier()));

		if (enchantment.isTreasureOnly() == true)
		{
			cost *= this.getCostTreasureOnlyMultiplier();
		}

		if (cost > 64)
		{
			cost = 64;
		}

		ItemStack costA = this.getCostA().copy();
		costA.setCount(cost);
		ItemStack itemstack = EnchantedBookItem.createForEnchantment(new EnchantmentData(enchantment, level));
		return Triple.of(costA, this.getCostB(), itemstack);
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_ALIEN_TRADING_ENCHANTEDBOOK.get();
	}

	public int getCostBase()
	{
		return this.costBase;
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

	public ItemStack getCostA()
	{
		return this.costA;
	}

	public ItemStack getCostB()
	{
		return this.costB;
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
