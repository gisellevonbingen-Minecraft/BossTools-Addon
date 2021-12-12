package boss_tools_giselle_addon.common.compat.jei;

import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.common.BossToolsAddon;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;

public class RecipeCategoryItemStackToItemStack<T extends ItemStackToItemStackRecipeType<? extends R>, R extends ItemStackToItemStackRecipe> extends RecipeCategory<T, R>
{
	public static final ResourceLocation BACKGROUND_LOCATION = BossToolsAddon.rl("textures/jei/itemstack_to_itemstack.png");
	public static final int BACKGROUND_HEIGHT = 84;
	public static final int BACKGROUND_WIDTH = 144;
	public static final int ARROW_LEFT = 55;
	public static final int ARROW_TOP = 29;
	public static final int INPUT_X = 33;
	public static final int INPUT_Y = 29;
	public static final int OUTPUT_X = 88;
	public static final int OUTPUT_Y = 29;

	private LoadingCache<Integer, IDrawableAnimated> cachedArrows;
	private IDrawable background;

	public RecipeCategoryItemStackToItemStack(T recipeType, Class<? extends R> recipeClass)
	{
		super(recipeType, recipeClass);
	}

	@Override
	public void createGui(IGuiHelper guiHelper)
	{
		super.createGui(guiHelper);
		this.cachedArrows = net.mrscauthd.boss_tools.jei.JeiPlugin.createArrows(guiHelper);
		this.background = createBackground(guiHelper);
	}

	protected IDrawableStatic createBackground(IGuiHelper guiHelper)
	{
		return guiHelper.createDrawable(BACKGROUND_LOCATION, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
	}

	public void draw(R recipe, MatrixStack stack, double mouseX, double mouseY)
	{
		super.draw(recipe, stack, mouseX, mouseY);

		this.drawArrow(recipe, stack);
		this.drawText(recipe, stack);
	}

	public void drawArrow(R recipe, MatrixStack stack)
	{
		int cookTime = recipe.getCookTime();
		int arrowLeft = this.getArrowLeft();
		int arrowTop = this.getArrowTop();
		this.cachedArrows.getUnchecked(Integer.valueOf(cookTime)).draw(stack, arrowLeft, arrowTop);
	}

	public void drawText(R recipe, MatrixStack stack)
	{
		int cookTime = recipe.getCookTime();
		net.mrscauthd.boss_tools.jei.JeiPlugin.drawTextTime(stack, this.getBackground(), cookTime);
	}

	@Override
	public void setIngredients(R recipe, IIngredients ingredients)
	{
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, R recipe, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

		int inputSlot = this.getInputSlot();
		stacks.init(inputSlot, true, this.getInputX(), this.getInputY());
		stacks.set(inputSlot, ingredients.getInputs(VanillaTypes.ITEM).get(0));

		int outputSlot = this.getOutputSlot();
		stacks.init(outputSlot, false, this.getOutputX(), this.getOutputY());
		stacks.set(outputSlot, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

	public int getArrowLeft()
	{
		return ARROW_LEFT;
	}

	public int getArrowTop()
	{
		return ARROW_TOP;
	}

	public int getInputSlot()
	{
		return 0;
	}

	public int getInputX()
	{
		return INPUT_X;
	}

	public int getInputY()
	{
		return INPUT_Y;
	}

	public int getOutputSlot()
	{
		return 1;
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
