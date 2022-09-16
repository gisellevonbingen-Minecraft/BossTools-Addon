package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RecyclingVanillaRecipeSerializer<P extends AbstractCookingRecipe, R extends AbstractCookingRecipe & IRecyclingVanillaRecipe<P, IInventory>> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<R>
{
	private final CookingRecipeSerializer<P> parent;
	private final CookieBaker<P, R> factory;

	public RecyclingVanillaRecipeSerializer(CookingRecipeSerializer<P> parent, RecyclingVanillaRecipeSerializer.CookieBaker<P, R> factory)
	{
		this.parent = parent;
		this.factory = factory;
	}

	@Override
	public R fromJson(ResourceLocation id, JsonObject json)
	{
		JsonElement resultJson = json.get("result");

		try
		{
			json.addProperty("result", "minecraft:air");
			ItemOutput output = ItemOutput.fromJson(resultJson);
			P parentRecipe = this.parent.fromJson(id, json);
			return this.factory.create(parentRecipe, output);
		}
		finally
		{
			json.add("result", resultJson);
		}

	}

	@Override
	public R fromNetwork(ResourceLocation id, PacketBuffer buffer)
	{
		P parentRecipe = this.parent.fromNetwork(id, buffer);
		ItemOutput output = ItemOutput.fromNetwork(buffer);
		return this.factory.create(parentRecipe, output);
	}

	@Override
	public void toNetwork(PacketBuffer buffer, R recipe)
	{
		this.parent.toNetwork(buffer, recipe.getParent());
		recipe.getOutput().toNetwork(buffer);
	}

	public static interface CookieBaker<P extends AbstractCookingRecipe, R extends AbstractCookingRecipe & IRecyclingVanillaRecipe<P, IInventory>>
	{
		R create(P parent, ItemOutput output);
	}

}
