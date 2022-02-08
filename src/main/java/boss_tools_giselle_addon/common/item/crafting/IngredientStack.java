package boss_tools_giselle_addon.common.item.crafting;

import java.util.Arrays;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;

public final class IngredientStack
{
	public static final IngredientStack EMPTY = new IngredientStack(Ingredient.EMPTY);

	private final Ingredient ingredient;
	private int count = 1;

	public IngredientStack(JsonElement json)
	{
		if (json instanceof JsonObject)
		{
			JsonObject jsonObject = (JsonObject) json;
			this.ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(jsonObject, "ingredient"));
			this.count = JSONUtils.getAsInt(jsonObject, "count", this.count);
		}
		else if (json instanceof JsonArray)
		{
			this.ingredient = Ingredient.fromJson(json);
		}
		else
		{
			this.ingredient = Ingredient.EMPTY;
		}
	}

	public IngredientStack(PacketBuffer buffer)
	{
		this.ingredient = Ingredient.fromNetwork(buffer);
		this.count = buffer.readInt();
	}

	public IngredientStack(Ingredient ingredient)
	{
		this.ingredient = ingredient;
	}

	public IngredientStack(Ingredient ingredient, int count)
	{
		this.ingredient = ingredient;
		this.count = count;
	}

	public ItemStack[] getItems()
	{
		int count = this.getCount();
		return Arrays.stream(this.getIngredient().getItems()).map(is ->
		{
			is.setCount(count);
			return is;
		}).toArray(ItemStack[]::new);
	}

	public boolean testWithoutCount(ItemStack itemStack)
	{
		return this.getIngredient().test(itemStack);
	}

	public boolean testWithCount(ItemStack itemStack)
	{
		return this.testWithoutCount(itemStack) && itemStack.getCount() >= this.getCount();
	}

	public void toNetwork(PacketBuffer buffer)
	{
		this.ingredient.toNetwork(buffer);
		buffer.writeInt(this.count);
	}

	public JsonObject toJson()
	{
		JsonObject json = new JsonObject();
		json.add("ingredient", this.ingredient.toJson());
		json.addProperty("count", this.count);
		return json;
	}

	public Ingredient getIngredient()
	{
		return ingredient;
	}

	public int getCount()
	{
		return this.count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public IngredientStack copy()
	{
		return new IngredientStack(this.getIngredient(), this.getCount());
	}

}
