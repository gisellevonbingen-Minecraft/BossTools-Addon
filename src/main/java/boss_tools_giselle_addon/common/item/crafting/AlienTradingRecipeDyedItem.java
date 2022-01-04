package boss_tools_giselle_addon.common.item.crafting;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import net.minecraft.entity.Entity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class AlienTradingRecipeDyedItem extends AlienTradingRecipeItemStack
{
	private float chance1 = 0.7F;
	private float chance2 = 0.8F;
	
	public AlienTradingRecipeDyedItem(ResourceLocation id, JsonObject json)
	{
		super(id, json);
		
		this.chance1 = JSONUtils.getAsFloat(json, "chance1", this.chance1);
		this.chance2 = JSONUtils.getAsFloat(json, "chance2", this.chance2);
	}

	public AlienTradingRecipeDyedItem(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);
		
		this.chance1 = buffer.readFloat();
		this.chance2 = buffer.readFloat();
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		super.write(buffer);
		
		buffer.writeFloat(this.chance1);
		buffer.writeFloat(this.chance2);
	}

	@Override
	public ItemStack getResult(Entity trader, Random rand)
	{
		ItemStack result = super.getResult(trader, rand);

		if (result.getItem() instanceof DyeableArmorItem)
		{
			List<DyeItem> list = Lists.newArrayList();
			list.add(getRandomDyeItem(rand));

			if (rand.nextFloat() > this.getChance1())
			{
				list.add(getRandomDyeItem(rand));
			}

			if (rand.nextFloat() > this.getChance2())
			{
				list.add(getRandomDyeItem(rand));
			}

			return IDyeableArmorItem.dyeArmor(result, list);
		}
		else
		{
			return result;
		}

	}

	public DyeItem getRandomDyeItem(Random random)
	{
		return DyeItem.byColor(DyeColor.byId(random.nextInt(16)));
	}
	
	public float getChance1()
	{
		return this.chance1;
	}
	
	public float getChance2()
	{
		return this.chance2;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_ALIEN_TRADING_DYEDITEM.get();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return AddonRecipes.ALIEN_TRADING_DYEDITEM;
	}

	public static class Serializer extends BossToolsRecipeSerializer<AlienTradingRecipeDyedItem>
	{
		@Override
		public AlienTradingRecipeDyedItem fromJson(ResourceLocation id, JsonObject json)
		{
			return new AlienTradingRecipeDyedItem(id, json);
		}

		@Override
		public AlienTradingRecipeDyedItem fromNetwork(ResourceLocation id, PacketBuffer buffer)
		{
			return new AlienTradingRecipeDyedItem(id, buffer);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, AlienTradingRecipeDyedItem recipe)
		{
			recipe.write(buffer);
		}

	}

}
