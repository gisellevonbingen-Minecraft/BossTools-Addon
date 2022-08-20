package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.managers.IRecipeManager.RecipeFilter;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipe;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import boss_tools_giselle_addon.common.compat.crafttweaker.AddonCTConstants;
import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipe;
import boss_tools_giselle_addon.common.item.crafting.AlienTradingRecipeType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.BossToolsMod;

@ZenRegister
@ZenCodeType.Name(AddonCTConstants.RECIPE_MANAGER_ALIEN_TRADING)
public class AddonCTAlienTradingRecipeManager
{
	Gson JSON_RECIPE_GSON = new GsonBuilder().create();

	@ZenCodeGlobals.Global(BossToolsMod.ModId + "_alien_trading")
	public static final AddonCTAlienTradingRecipeManager INSTANCE = new AddonCTAlienTradingRecipeManager();

	@ZenCodeType.Method
	public void addJsonRecipe(String name, IData data)
	{
		MapData mapData = (MapData) data;
		JsonObject recipeObject = JSON_RECIPE_GSON.fromJson(mapData.toJsonString(), JsonObject.class);
		IRecipe<?> iRecipe = RecipeManager.fromJson(new ResourceLocation(CraftTweaker.MODID, name), recipeObject);
		IRecipeType<?> recipeType = iRecipe.getType();
		IRecipeManager recipeManager = RecipeTypeBracketHandler.getOrDefault(recipeType);
		CraftTweakerAPI.apply(new ActionAddRecipe(recipeManager, iRecipe, ""));
	}

	@ZenCodeType.Method
	public void removeAll()
	{
		this.foreach(IRecipeManager::removeAll);
	}

	@ZenCodeType.Method
	public void removeByJob(VillagerProfession job)
	{
		this.foreach(recipeManager -> CraftTweakerAPI.apply(new ActionRemoveRecipe(recipeManager, _r -> this.test(_r, r -> r.getJob() == job))));
	}

	@ZenCodeType.Method
	public void removeByJob(String job)
	{
		this.removeByJob(ForgeRegistries.PROFESSIONS.getValue(new ResourceLocation(job)));
	}

	@ZenCodeType.Method
	public void removeByJobWithLevel(VillagerProfession job, int level)
	{
		this.foreach(recipeManager -> CraftTweakerAPI.apply(new ActionRemoveRecipe(recipeManager, _r -> this.test(_r, r -> r.getJob() == job && r.getLevel() == level))));
	}

	@ZenCodeType.Method
	public void removeByJobWithLevel(String job, int level)
	{
		this.removeByJobWithLevel(ForgeRegistries.PROFESSIONS.getValue(new ResourceLocation(job)), level);
	}

	@ZenCodeType.Method
	public void removeRecipe(IIngredient output)
	{
		this.foreach(recipeManager -> recipeManager.removeRecipe(output));
	}

	@ZenCodeType.Method
	public void removeRecipe(IItemStack output)
	{
		this.foreach(recipeManager -> recipeManager.removeRecipe(output));
	}

	@ZenCodeType.Method
	public void removeRecipeByInput(IItemStack input)
	{
		this.foreach(recipeManager -> recipeManager.removeRecipeByInput(input));
	}

	@ZenCodeType.Method
	public void removeByModid(String modid)
	{
		this.foreach(recipeManager -> recipeManager.removeByModid(modid));
	}

	@ZenCodeType.Method
	public void removeByModid(String modid, RecipeFilter exclude)
	{
		this.foreach(recipeManager -> recipeManager.removeByModid(modid, exclude));
	}

	@ZenCodeType.Method
	public void removeByName(String name)
	{
		this.foreach(recipeManager -> recipeManager.removeByName(name));
	}

	@ZenCodeType.Method
	public void removeByRegex(String regex)
	{
		this.foreach(recipeManager -> recipeManager.removeByRegex(regex));
	}

	@ZenCodeType.Method
	public void removeByRegex(String regex, RecipeFilter exclude)
	{
		this.foreach(recipeManager -> recipeManager.removeByRegex(regex, exclude));
	}

	public void foreach(Consumer<IRecipeManager> consumer)
	{
		for (AlienTradingRecipeType<?> recipeType : AlienTradingRecipeType.getTypes())
		{
			IRecipeManager recipeManager = RecipeTypeBracketHandler.getOrDefault(recipeType);
			consumer.accept(recipeManager);
		}

	}

	public boolean test(IRecipe<?> rawRecipe, Predicate<AlienTradingRecipe> predicate)
	{
		if (rawRecipe instanceof AlienTradingRecipe)
		{
			return predicate.test((AlienTradingRecipe) rawRecipe);
		}
		else
		{
			return false;
		}

	}

}
