package boss_tools_giselle_addon.compat.jei;

import java.util.List;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class RecipeCategory<T extends IRecipeType<? extends R>, R extends IRecipe<IInventory>> implements IRecipeCategory<R>
{
	private final ResourceLocation uid;
	private final T recipeType;
	private final Class<? extends R> recipeClass;

	private ITextComponent titleTextComponent;

	public RecipeCategory(T recipeType, Class<? extends R> recipeClass)
	{
		this.uid = JeiPlugin.createUid(recipeType);
		this.recipeType = recipeType;
		this.recipeClass = recipeClass;

		this.titleTextComponent = this.createTitle();
	}

	protected ITextComponent createTitle()
	{
		return JeiPlugin.getCategoryTitle(this.getRecipeType());
	}

	public T getRecipeType()
	{
		return this.recipeType;
	}

	@Override
	public ResourceLocation getUid()
	{
		return this.uid;
	}

	@Override
	public Class<? extends R> getRecipeClass()
	{
		return this.recipeClass;
	}

	@Override
	public ITextComponent getTitleAsTextComponent()
	{
		return this.titleTextComponent;
	}

	@Override
	public String getTitle()
	{
		return this.getTitleAsTextComponent().getString();
	}

	@Override
	public IDrawable getBackground()
	{
		return null;
	}

	@Override
	public IDrawable getIcon()
	{
		return null;
	}

	public void registerRecipes(IRecipeRegistration registration)
	{
		Minecraft minecraft = Minecraft.getInstance();
		ClientWorld level = minecraft.level;
		RecipeManager recipeManager = level.getRecipeManager();

		IRecipeType<? extends IRecipe<IInventory>> recipeType = this.getRecipeType();
		List<?> recipes = recipeManager.getAllRecipesFor(recipeType);
		registration.addRecipes(recipes, this.getUid());
	}

	public void createGui(IGuiHelper guiHelper)
	{

	}

}
