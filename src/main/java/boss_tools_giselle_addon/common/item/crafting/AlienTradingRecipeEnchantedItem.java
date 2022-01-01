package boss_tools_giselle_addon.common.item.crafting;

import java.util.Random;

import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonObject;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class AlienTradingRecipeEnchantedItem extends AlienTradingRecipe
{
	private int levelBase = 5;
	private int levelRange = 15;

	private int costBase = 5;
	private int costBaseLevelMultiplier = 0;
	private int costRangeBase = 15;
	private int costRangeLevelMultiplier = 0;

	private ItemStack costA;
	private ItemStack costB;
	private ItemStack result;

	public AlienTradingRecipeEnchantedItem(ResourceLocation id, JsonObject json)
	{
		super(id, json);

		this.levelBase = JSONUtils.getAsInt(json, "levelBase", this.levelBase);
		this.levelRange = JSONUtils.getAsInt(json, "levelRange", this.levelRange);

		this.costBase = JSONUtils.getAsInt(json, "costBase", this.costBase);
		this.costBaseLevelMultiplier = JSONUtils.getAsInt(json, "costBaseLevelMultiplier", this.costBaseLevelMultiplier);
		this.costRangeBase = JSONUtils.getAsInt(json, "costRangeBase", this.costRangeBase);
		this.costRangeLevelMultiplier = JSONUtils.getAsInt(json, "costRangeLevelMultiplier", this.costRangeLevelMultiplier);

		this.costA = new ItemStack(Items.EMERALD);
		this.costB = new ItemStack(Items.AIR);
		this.costA = json.has("costA") ? CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "costA"), true) : this.costA;
		this.costB = json.has("costB") ? CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "costB"), true) : this.costB;
		this.result = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "result"), true);
	}

	public AlienTradingRecipeEnchantedItem(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);

		this.levelBase = buffer.readInt();
		this.levelRange = buffer.readInt();

		this.costBase = buffer.readInt();
		this.costBaseLevelMultiplier = buffer.readInt();
		this.costRangeBase = buffer.readInt();
		this.costRangeLevelMultiplier = buffer.readInt();

		this.costA = buffer.readItem();
		this.costB = buffer.readItem();
		this.result = buffer.readItem();
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		super.write(buffer);

		buffer.writeInt(this.levelBase);
		buffer.writeInt(this.levelRange);

		buffer.writeInt(this.costBase);
		buffer.writeInt(this.costBaseLevelMultiplier);
		buffer.writeInt(this.costRangeBase);
		buffer.writeInt(this.costRangeLevelMultiplier);

		buffer.writeItem(this.costA);
		buffer.writeItem(this.costB);
		buffer.writeItem(this.result);
	}

	@Override
	public Triple<ItemStack, ItemStack, ItemStack> getTrade(Entity trader, Random rand)
	{
		ItemStack costABase = this.getCostA();
		ItemStack costB = this.getCostB();

		int level = this.getLevelBase();
		int bound1 = this.getLevelRange();

		if (bound1 > 0)
		{
			level += rand.nextInt(bound1);
		}

		int cost = Math.min(this.getCostBase() + level * this.getCostBaseLevelMultiplier(), costABase.getMaxStackSize());
		int bound2 = this.getCostRangeBase() + level * this.getCostRangeLevelMultiplier();

		if (bound2 > 0)
		{
			cost += rand.nextInt(bound2);
		}

		ItemStack costA = new ItemStack(Items.EMERALD, cost);

		ItemStack result = EnchantmentHelper.enchantItem(rand, this.getResultBase(), level, false);
		return Triple.of(costA, costB, result);
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_ALIEN_TRADING_ENCHANTEDITEM.get();
	}

	public int getLevelBase()
	{
		return this.levelBase;
	}

	public int getLevelRange()
	{
		return this.levelRange;
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

	public ItemStack getCostA()
	{
		return this.costA.copy();
	}

	public ItemStack getCostB()
	{
		return this.costB.copy();
	}

	public ItemStack getResultBase()
	{
		return result.copy();
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
