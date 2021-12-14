package boss_tools_giselle_addon.common.compat.jei;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class RecipeCategory<R> implements IRecipeCategory<R>
{
	private final Class<? extends R> recipeClass;
	private final ResourceLocation uid;

	private ITextComponent titleTextComponent;

	public RecipeCategory(Class<? extends R> recipeClass)
	{
		this.recipeClass = recipeClass;
		this.uid = AddonJeiPlugin.createUid(this.getKey());

		this.titleTextComponent = this.createTitle();
	}

	public abstract void registerRecipes(IRecipeRegistration registration);

	public abstract ResourceLocation getKey();

	protected ITextComponent createTitle()
	{
		return AddonJeiPlugin.getCategoryTitle(this.getKey());
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

	public void createGui(IGuiHelper guiHelper)
	{

	}

}
