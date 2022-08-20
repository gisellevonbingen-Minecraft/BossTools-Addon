package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;

import boss_tools_giselle_addon.common.compat.crafttweaker.CTConstants;
import net.minecraft.item.crafting.IRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.OxygenBubbleDistributorRecipe;

@ZenRegister
@ZenCodeType.Name(CTConstants.RECIPE_MANAGER_OXYGEN_BUBBLE_DISTRIBUTING)
@IRecipeHandler.For(OxygenBubbleDistributorRecipe.class)
public class CTOxygenBubbleDistributingRecipeManager extends CTOxygenMakingRecipeManager<OxygenBubbleDistributorRecipe>
{
	public static final CTOxygenBubbleDistributingRecipeManager INSTANCE = new CTOxygenBubbleDistributingRecipeManager();

	public CTOxygenBubbleDistributingRecipeManager()
	{
		super(OxygenBubbleDistributorRecipe::new);
	}

	@ZenCodeType.Method
	public void addRecipePair(String name, int loadingOxygen, CommandStringDisplayable fluidInput)
	{
		this.addRecipePair(name, loadingOxygen, loadingOxygen * 4, fluidInput);
	}

	@ZenCodeType.Method
	public void addRecipePair(String name, int loadingOxygen, int bubbleOxygen, CommandStringDisplayable fluidInput)
	{
		CTOxygenLoadingRecipeManager.INSTANCE.addRecipe(name + "/loading", loadingOxygen, fluidInput);
		CTOxygenBubbleDistributingRecipeManager.INSTANCE.addRecipe(name + "/bubble_distributing", bubbleOxygen, fluidInput);
	}

	@Override
	public IRecipeType<OxygenBubbleDistributorRecipe> getRecipeType()
	{
		return BossToolsRecipeTypes.OXYGENBUBBLEDISTRIBUTOR;
	}

}
