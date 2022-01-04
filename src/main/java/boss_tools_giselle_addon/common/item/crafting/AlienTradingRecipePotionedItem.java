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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class AlienTradingRecipePotionedItem extends AlienTradingRecipeItemStack
{
	public AlienTradingRecipePotionedItem(ResourceLocation id, JsonObject json)
	{
		super(id, json);
	}

	public AlienTradingRecipePotionedItem(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		super.write(buffer);
	}

	@Override
	public ItemStack getResult(Entity trader, Random rand)
	{
		ItemStack result = super.getResult(trader, rand);
		List<Potion> potions = ForgeRegistries.POTION_TYPES.getValues().stream().filter(this::testPotion).collect(Collectors.toList());
		Potion potion = potions.get(rand.nextInt(potions.size()));
		return PotionUtils.setPotion(result, potion);
	}

	public boolean testPotion(Potion potion)
	{
		return !potion.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(potion);
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
