package boss_tools_giselle_addon.common.item.crafting;

import java.util.Random;

import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonObject;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;

public abstract class AlienTradingRecipeItemStackBase extends AlienTradingRecipe
{
	private ItemStack costA;
	private ItemStack costB;

	public AlienTradingRecipeItemStackBase(ResourceLocation id, JsonObject json)
	{
		super(id, json);

		this.costA = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "costA"), true);
		this.costB = json.has("costB") ? CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "costB"), true) : ItemStack.EMPTY;
	}

	public AlienTradingRecipeItemStackBase(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);

		this.costA = buffer.readItem();
		this.costB = buffer.readItem();
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		super.write(buffer);

		buffer.writeItem(this.costA);
		buffer.writeItem(this.costB);
	}

	@Override
	public Triple<ItemStack, ItemStack, ItemStack> getTrade(Entity trader, Random rand)
	{
		return Triple.of(this.getCostA(), this.getCostB(), this.getResult(trader, rand));
	}

	public abstract ItemStack getResult(Entity trader, Random rand);

	public ItemStack getCostA()
	{
		return this.costA.copy();
	}

	public ItemStack getCostB()
	{
		return this.costB.copy();
	}

}
