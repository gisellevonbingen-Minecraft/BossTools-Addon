package boss_tools_giselle_addon.common.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;

public class ItemStackToItemStackRecipeWrapper extends ItemStackToItemStackRecipe
{
	private final IRecipe<IInventory> parent;
	private final float experience;

	public ItemStackToItemStackRecipeWrapper(IRecipe<IInventory> parent, Ingredient ingredient, ItemStack result, int cookTime)
	{
		this(parent, ingredient, result, cookTime, 0.0F);
	}

	public ItemStackToItemStackRecipeWrapper(IRecipe<IInventory> parent, Ingredient ingredient, ItemStack result, int cookTime, float experience)
	{
		super(parent.getId(), ingredient, result, cookTime);
		this.parent = parent;
		this.experience = experience;
	}

	public final IRecipe<IInventory> getParent()
	{
		return this.parent;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return this.getParent().getSerializer();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return this.getParent().getType();
	}

	public float getExperience()
	{
		return this.experience;
	}

}
