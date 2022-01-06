package boss_tools_giselle_addon.common.item.crafting;

import java.util.Random;

import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonObject;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class AlienTradingRecipeItemStack extends AlienTradingRecipeItemStackBase
{
	private ItemStack result;

	public AlienTradingRecipeItemStack(ResourceLocation id, JsonObject json)
	{
		super(id, json);

		this.result = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "result"), true);
	}

	public AlienTradingRecipeItemStack(ResourceLocation id, PacketBuffer buffer)
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
	public Triple<ItemStack, ItemStack, ItemStack> getTrade(Entity trader, Random rand)
	{
		return Triple.of(this.getCostA(), this.getCostB(), this.getResult(trader, rand));
	}

	public ItemStack getResult(Entity trader, Random rand)
	{
		return this.result.copy();
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_ALIEN_TRADING_ITEMSTACK.get();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return AddonRecipes.ALIEN_TRADING_ITEMSTACK;
	}

	public static class Serializer extends BossToolsRecipeSerializer<AlienTradingRecipeItemStack>
	{
		@Override
		public AlienTradingRecipeItemStack fromJson(ResourceLocation id, JsonObject json)
		{
			return new AlienTradingRecipeItemStack(id, json);
		}

		@Override
		public AlienTradingRecipeItemStack fromNetwork(ResourceLocation id, PacketBuffer buffer)
		{
			return new AlienTradingRecipeItemStack(id, buffer);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, AlienTradingRecipeItemStack recipe)
		{
			recipe.write(buffer);
		}

	}

}
