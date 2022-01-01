package boss_tools_giselle_addon.common.item.crafting;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class AlienTradingRecipePotionedItem extends AlienTradingRecipeItemStackBase
{
	private ItemStack result;

	public AlienTradingRecipePotionedItem(ResourceLocation id, JsonObject json)
	{
		super(id, json);

		this.result = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "result"), true);
	}

	public AlienTradingRecipePotionedItem(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);

		this.result = buffer.readItem();
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		super.write(buffer);

		buffer.writeItem(this.result);
	}

	@Override
	public ItemStack getResult(Entity trader, Random rand)
	{
		List<Potion> potions = ForgeRegistries.POTION_TYPES.getValues().stream().filter(this::testPotion).collect(Collectors.toList());
		Potion potion = potions.get(rand.nextInt(potions.size()));
		return PotionUtils.setPotion(this.getResultBase().copy(), potion);
	}

	public boolean testPotion(Potion potion)
	{
		return !potion.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(potion);
	}

	public ItemStack getResultBase()
	{
		return result.copy();
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_ALIEN_TRADING_POTIONEDITEM.get();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return AddonRecipes.ALIEN_TRADING_POTIONEDITEM;
	}

	public static class Serializer extends BossToolsRecipeSerializer<AlienTradingRecipePotionedItem>
	{
		@Override
		public AlienTradingRecipePotionedItem fromJson(ResourceLocation id, JsonObject json)
		{
			return new AlienTradingRecipePotionedItem(id, json);
		}

		@Override
		public AlienTradingRecipePotionedItem fromNetwork(ResourceLocation id, PacketBuffer buffer)
		{
			return new AlienTradingRecipePotionedItem(id, buffer);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, AlienTradingRecipePotionedItem recipe)
		{
			recipe.write(buffer);
		}

	}

}
