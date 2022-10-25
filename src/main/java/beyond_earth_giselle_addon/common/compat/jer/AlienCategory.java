package beyond_earth_giselle_addon.common.compat.jer;

import java.util.List;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.compat.jei.AddonJeiPlugin;
import jeresources.jei.BlankJEIRecipeCategory;
import jeresources.reference.Resources;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AlienCategory extends BlankJEIRecipeCategory<AlienWrapper>
{
	public static final ResourceLocation KEY = BeyondEarthAddon.rl("alien_trading");
	public static final ResourceLocation UID = AddonJeiPlugin.createUid(KEY);
	public static final RecipeType<AlienWrapper> TYPE = new RecipeType<>(UID, AlienWrapper.class);

	protected static final int X_FIRST_ITEM = 95;
	protected static final int X_ITEM_DISTANCE = 18;
	protected static final int X_ITEM_RESULT = 150;
	protected static final int Y_ITEM_DISTANCE = 22;

	public AlienCategory(IGuiHelper guiHelper)
	{
		super(guiHelper.createDrawable(Resources.Gui.Jei.TABS, 0, 0, 16, 16));
	}

	public static RecipeType<AlienWrapper> getType()
	{
		return TYPE;
	}

	@Override
	public RecipeType<AlienWrapper> getRecipeType()
	{
		return TYPE;
	}

	@Nonnull
	@Override
	public Component getTitle()
	{
		return AddonJeiPlugin.getCategoryTitle(KEY);
	}

	@Nonnull
	@Override
	public IDrawable getBackground()
	{
		return Resources.Gui.Jei.VILLAGER;
	}

	@Override
	public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull AlienWrapper recipeWrapper, @NotNull IFocusGroup focuses)
	{
		if (recipeWrapper.hasPois() == true)
		{
			builder.addSlot(RecipeIngredientRole.INPUT, 50, 19).addItemStacks(recipeWrapper.getPois());
		}

		IFocus<ItemStack> focus = focuses.getFocuses(VanillaTypes.ITEM_STACK).findFirst().orElse(null);
		List<Integer> possibleLevels = recipeWrapper.getPossibleLevels(focus);
		int y = 1 + Y_ITEM_DISTANCE * (6 - possibleLevels.size()) / 2;

		for (int i = 0; i < possibleLevels.size(); i++)
		{
			AlienTradeList tradeList = recipeWrapper.getTrades(possibleLevels.get(i)).getFocusedList(focus);
			builder.addSlot(RecipeIngredientRole.INPUT, 1 + X_FIRST_ITEM, y + i * Y_ITEM_DISTANCE).addItemStacks(tradeList.getCostAs());
			builder.addSlot(RecipeIngredientRole.INPUT, 1 + X_FIRST_ITEM + X_ITEM_DISTANCE, y + i * Y_ITEM_DISTANCE).addItemStacks(tradeList.getCostBs());
			builder.addSlot(RecipeIngredientRole.OUTPUT, 1 + X_ITEM_RESULT, y + i * Y_ITEM_DISTANCE).addItemStacks(tradeList.getResults());
		}

	}

}
