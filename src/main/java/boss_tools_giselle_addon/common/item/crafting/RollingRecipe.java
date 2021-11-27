package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;

public class RollingRecipe extends ItemStackToItemStackRecipe
{

	public RollingRecipe(ResourceLocation id, JsonObject json)
	{
		super(id, json);
	}

	public RollingRecipe(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);
	}

	public RollingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack output, int cookTime)
	{
		super(id, ingredient, output, cookTime);
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_ROLLING.get();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return AddonRecipes.ROLLING;
	}

}
