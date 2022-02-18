package beyond_earth_giselle_addon.common.compat.jei;

import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipe;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.beyond_earth.jei.JeiPlugin;

public class RecipeCategoryItemStackToItemStack<T extends ItemStackToItemStackRecipeType<? extends R>, R extends ItemStackToItemStackRecipe> extends RecipeCategoryRecipeType<T, R>
{
	public static final ResourceLocation BACKGROUND_LOCATION = BeyondEarthAddon.rl("textures/jei/itemstack_to_itemstack.png");
	public static final int BACKGROUND_WIDTH = 144;
	public static final int BACKGROUND_HEIGHT = 84;
	public static final int ARROW_LEFT = 55;
	public static final int ARROW_TOP = 29;
	public static final int INPUT_X = 34;
	public static final int INPUT_Y = 30;
	public static final int OUTPUT_X = 89;
	public static final int OUTPUT_Y = 30;

	private LoadingCache<Integer, IDrawableAnimated> cachedArrows;
	private IDrawable background;

	public RecipeCategoryItemStackToItemStack(Class<? extends R> recipeClass, T recipeType)
	{
		super(recipeClass, recipeType);
	}

	@Override
	public void createGui(IGuiHelper guiHelper)
	{
		super.createGui(guiHelper);
		this.cachedArrows = JeiPlugin.createArrows(guiHelper);
		this.background = createBackground(guiHelper);
	}

	protected IDrawable createBackground(IGuiHelper guiHelper)
	{
		return guiHelper.createDrawable(BACKGROUND_LOCATION, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
	{
		super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);

		this.drawArrow(recipe, stack);
		this.drawText(recipe, stack);
	}

	public void drawArrow(R recipe, PoseStack stack)
	{
		int cookTime = recipe.getCookTime();
		int arrowLeft = this.getArrowLeft();
		int arrowTop = this.getArrowTop();
		this.cachedArrows.getUnchecked(Integer.valueOf(cookTime)).draw(stack, arrowLeft, arrowTop);
	}

	public void drawText(R recipe, PoseStack stack)
	{
		int cookTime = recipe.getCookTime();
		JeiPlugin.drawTextTime(stack, this.getBackground(), cookTime);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses)
	{
		super.setRecipe(builder, recipe, focuses);

		builder.addSlot(RecipeIngredientRole.INPUT, this.getInputX(), this.getInputY()) //
				.addIngredients(recipe.getInput());

		builder.addSlot(RecipeIngredientRole.OUTPUT, this.getOutputX(), this.getOutputY()) //
				.addItemStack(recipe.getOutput());
	}

	public int getArrowLeft()
	{
		return ARROW_LEFT;
	}

	public int getArrowTop()
	{
		return ARROW_TOP;
	}

	public int getInputX()
	{
		return INPUT_X;
	}

	public int getInputY()
	{
		return INPUT_Y;
	}

	public int getOutputX()
	{
		return OUTPUT_X;
	}

	public int getOutputY()
	{
		return OUTPUT_Y;
	}

	@Override
	public IDrawable getBackground()
	{
		return this.background;
	}

}
