package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;

import boss_tools_giselle_addon.common.compat.crafttweaker.CTConstants;
import net.minecraft.item.crafting.IRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.OxygenLoaderRecipe;

@ZenRegister
@ZenCodeType.Name(CTConstants.RECIPE_MANAGER_OXYGEN_LOADING)
@IRecipeHandler.For(OxygenLoaderRecipe.class)
public class CTOxygenLoadingRecipeManager extends CTOxygenMakingRecipeManager<OxygenLoaderRecipe>
{
	public static final CTOxygenLoadingRecipeManager INSTANCE = new CTOxygenLoadingRecipeManager();

	public CTOxygenLoadingRecipeManager()
	{
		super(OxygenLoaderRecipe::new);
	}

	@ZenCodeType.Method
	public void addRecipePair(String name, int loadingOxygen, CommandStringDisplayable fluidInput)
	{
		CTOxygenBubbleDistributingRecipeManager.INSTANCE.addRecipePair(name, loadingOxygen, fluidInput);
	}

	@ZenCodeType.Method
	public void addRecipePair(String name, int loadingOxygen, int bubbleOxygen, CommandStringDisplayable fluidInput)
	{
		CTOxygenBubbleDistributingRecipeManager.INSTANCE.addRecipePair(name, loadingOxygen, bubbleOxygen, fluidInput);
	}

	@Override
	public IRecipeType<OxygenLoaderRecipe> getRecipeType()
	{
		return BossToolsRecipeTypes.OXYGENLOADER;
	}

}
