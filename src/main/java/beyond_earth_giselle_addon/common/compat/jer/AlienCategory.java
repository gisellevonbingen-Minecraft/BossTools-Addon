package beyond_earth_giselle_addon.common.compat.jer;

import javax.annotation.Nonnull;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.compat.jei.AddonJeiPlugin;
import jeresources.jei.BlankJEIRecipeCategory;
import jeresources.jei.JEIConfig;
import jeresources.reference.Resources;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocus;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AlienCategory extends BlankJEIRecipeCategory<AlienWrapper>
{
	public static final ResourceLocation KEY = BeyondEarthAddon.rl("alien_trading");
	public static final ResourceLocation Uid = AddonJeiPlugin.createUid(KEY);

	protected static final int X_FIRST_ITEM = 95;
	protected static final int X_ITEM_DISTANCE = 18;
	protected static final int X_ITEM_RESULT = 150;
	protected static final int Y_ITEM_DISTANCE = 22;

	public AlienCategory()
	{
        super(JEIConfig.getJeiHelpers().getGuiHelper().createDrawable(Resources.Gui.Jei.TABS, 0, 0, 16, 16));
	}

	@Nonnull
	@Override
	public ResourceLocation getUid()
	{
		return Uid;
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
	public Class<? extends AlienWrapper> getRecipeClass()
	{
		return AlienWrapper.class;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull AlienWrapper recipeWrapper, @Nonnull IIngredients ingredients)
	{
		IFocus<ItemStack> focus = recipeLayout.getFocus(VanillaTypes.ITEM);
		recipeWrapper.setFocus(focus);

		int y = Y_ITEM_DISTANCE * (6 - recipeWrapper.getPossibleLevels(focus).size()) / 2;
		for (int i = 0; i < recipeWrapper.getPossibleLevels(focus).size(); i++)
		{
			recipeLayout.getItemStacks().init(3 * i + 1, true, X_FIRST_ITEM, y + i * Y_ITEM_DISTANCE);
			recipeLayout.getItemStacks().init(3 * i + 2, true, X_FIRST_ITEM + X_ITEM_DISTANCE, y + i * Y_ITEM_DISTANCE);
			recipeLayout.getItemStacks().init(3 * i + 3, false, X_ITEM_RESULT, y + i * Y_ITEM_DISTANCE);
		}

		int i = 0;
		for (int level : recipeWrapper.getPossibleLevels(focus))
		{
			AlienTradeList tradeList = recipeWrapper.getTrades(level).getFocusedList(focus);
			recipeLayout.getItemStacks().set(3 * i + 1, tradeList.getCostAs());
			recipeLayout.getItemStacks().set(3 * i + 2, tradeList.getCostBs());
			recipeLayout.getItemStacks().set(3 * i + 3, tradeList.getResults());
			i++;
		}
	}
}
