package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;

import boss_tools_giselle_addon.common.compat.crafttweaker.CTConstants;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;

@ZenRegister
@ZenCodeType.Name(CTConstants.RECIPE_MANAGER_IS2IS)
public abstract class CTItemStackToItemStackRecipeManager<T extends ItemStackToItemStackRecipe> extends CTRecipeManager<T>
{
	private final ItemStackToItemStackRecipeConstructor<T> constructor;

	public CTItemStackToItemStackRecipeManager(ItemStackToItemStackRecipeConstructor<T> constructor)
	{
		this.constructor = constructor;
	}

	public int getDefaultCookTime()
	{
		return 200;
	}

	@ZenCodeType.Method
	public void addRecipe(String name, IItemStack output, IIngredient input)
	{
		this.addRecipe(name, output, input, this.getDefaultCookTime());
	}

	@ZenCodeType.Method
	public void addRecipe(String name, IItemStack output, IIngredient input, int cookTime)
	{
		ResourceLocation id = this.getId(name);
		T recipe = this.getConstructor().construct(id, input.asVanillaIngredient(), output.getImmutableInternal(), cookTime);
		this.addRecipe(recipe);
	}

	@Override
	public String dumpToCommandString(IRecipeManager manager, T recipe)
	{
		return this.buildCommandString(manager, recipe, recipe.getIngredient(), recipe.getOutput(), recipe.getCookTime());
	}

	public ItemStackToItemStackRecipeConstructor<T> getConstructor()
	{
		return this.constructor;
	}

	public interface ItemStackToItemStackRecipeConstructor<T>
	{
		public T construct(ResourceLocation id, Ingredient ingredient, ItemStack output, int cookTime);
	}

}
