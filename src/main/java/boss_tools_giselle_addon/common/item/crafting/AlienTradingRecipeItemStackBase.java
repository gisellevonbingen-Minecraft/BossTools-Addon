package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

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

	public ItemStack getCostA()
	{
		return this.costA.copy();
	}

	public ItemStack getCostB()
	{
		return this.costB.copy();
	}

}
