package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;

import boss_tools_giselle_addon.common.compat.crafttweaker.CTConstants;
import boss_tools_giselle_addon.common.compat.crafttweaker.bracket.CTBracketHandlers;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipe;

@ZenRegister
@ZenCodeType.Name(CTConstants.RECIPE_MANAGER_NASA_WORKBENCHING)
@IRecipeHandler.For(WorkbenchingRecipe.class)
public class CTNASAWorkbenchingRecipeManager extends CTRecipeManager<WorkbenchingRecipe>
{
	public static final CTNASAWorkbenchingRecipeManager INSTANCE = new CTNASAWorkbenchingRecipeManager();

	@ZenCodeType.Method
	public void addRecipe(String name, IItemStack output, Map<RocketPart, IIngredient[]> map)
	{
		Map<RocketPart, List<Ingredient>> parts = new HashMap<>();

		for (Entry<RocketPart, IIngredient[]> entry : map.entrySet())
		{
			RocketPart rocketPart = entry.getKey();
			List<Ingredient> ingredients = Arrays.stream(entry.getValue()).map(IIngredient::asVanillaIngredient).toList();
			parts.put(rocketPart, ingredients);
		}

		this.addRecipe(new WorkbenchingRecipe(this.getId(name), parts, output.getImmutableInternal()));
	}

	@Override
	public IRecipeType<WorkbenchingRecipe> getRecipeType()
	{
		return BossToolsRecipeTypes.WORKBENCHING;
	}

	@Override
	public String dumpToCommandString(IRecipeManager manager, WorkbenchingRecipe recipe)
	{
		List<String> values = new ArrayList<>();
		values.add(this.toStringValue(recipe.getOutput()));
		values.add(this.toStringValues(recipe.getParts().entrySet().stream().toList(), "{", "}", this::toStringEntry));

		return this.buildCommandString(manager, recipe, values);
	}

	private String toStringEntry(Entry<RocketPart, List<Ingredient>> entry)
	{
		return new StringBuilder().append(CTBracketHandlers.toString(entry.getKey())).append(": ").append(this.toStringValue(entry.getValue())).toString();
	}

}
