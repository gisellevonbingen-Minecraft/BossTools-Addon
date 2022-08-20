package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.data.StringData;

import boss_tools_giselle_addon.common.compat.crafttweaker.AddonCTConstants;
import boss_tools_giselle_addon.common.compat.crafttweaker.CTUtils;
import boss_tools_giselle_addon.common.item.crafting.IngredientStack;
import boss_tools_giselle_addon.common.item.crafting.SpaceStationRecipe;
import boss_tools_giselle_addon.common.registries.AddonRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.NonNullList;

@ZenRegister
@ZenCodeType.Name(AddonCTConstants.RECIPE_MANAGER_SPACE_STATION)
@IRecipeHandler.For(SpaceStationRecipe.class)
public class AddonCTSpaceStationRecipeManager extends CTRecipeManager<SpaceStationRecipe>
{
	public static final AddonCTSpaceStationRecipeManager INSTANCE = new AddonCTSpaceStationRecipeManager();

	@Override
	public IRecipeType<SpaceStationRecipe> getRecipeType()
	{
		return AddonRecipes.SPACE_STATION;
	}

	@ZenCodeType.Method
	public void setJSONRecipe(IData data)
	{
		MapData mapData = (MapData) data;
		mapData.put("type", new StringData(AddonRecipes.RECIPE_SERIALIZER_SPACE_STATION.getId().toString()));
		IRecipe<?> recipe = CTRecipeUtils.parseRecipe(SpaceStationRecipe.KEY, mapData);

		this.removeAll();
		CTRecipeUtils.addRecipe(recipe);
	}

	@ZenCodeType.Method
	public void setRecipe(CommandStringDisplayable... ingredients)
	{
		this.removeAll();

		NonNullList<IngredientStack> ingredientStacks = NonNullList.withSize(ingredients.length, IngredientStack.EMPTY);

		for (int i = 0; i < ingredients.length; i++)
		{
			ingredientStacks.set(i, CTUtils.toItemIngredientStack(ingredients[i]));
		}

		this.addRecipe(new SpaceStationRecipe(SpaceStationRecipe.KEY, ingredientStacks));
	}

	@Override
	public String dumpToCommandString(IRecipeManager manager, SpaceStationRecipe recipe)
	{
		return null;
	}

}
