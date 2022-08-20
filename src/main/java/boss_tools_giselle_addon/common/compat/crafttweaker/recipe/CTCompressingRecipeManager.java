package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;

import boss_tools_giselle_addon.common.compat.crafttweaker.CTConstants;
import net.minecraft.item.crafting.IRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.CompressingRecipe;

@ZenRegister
@ZenCodeType.Name(CTConstants.RECIPE_MANAGER_COMPRESSING)
@IRecipeHandler.For(CompressingRecipe.class)
public class CTCompressingRecipeManager extends CTItemStackToItemStackRecipeManager<CompressingRecipe>
{
	public static final CTCompressingRecipeManager INSTANCE = new CTCompressingRecipeManager();

	private CTCompressingRecipeManager()
	{
		super(CompressingRecipe::new);
	}

	@Override
	public IRecipeType<CompressingRecipe> getRecipeType()
	{
		return BossToolsRecipeTypes.COMPRESSING;
	}

}
