package boss_tools_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class RecipeCategory<R> implements IRecipeCategory<R>
{
	private final Class<? extends R> recipeClass;

	public RecipeCategory(Class<? extends R> recipeClass)
	{
		this.recipeClass = recipeClass;
	}

	@Override
	public abstract IDrawable getBackground();

	public abstract ResourceLocation getKey();

	@Override
	public Class<? extends R> getRecipeClass()
	{
		return this.recipeClass;
	}

	@Override
	public ResourceLocation getUid()
	{
		return AddonJeiPlugin.createUid(this.getKey());
	}

	@Override
	public String getTitle()
	{
		return null;
	}

	@Override
	public ITextComponent getTitleAsTextComponent()
	{
		return AddonJeiPlugin.getCategoryTitle(this.getKey());
	}

	@Override
	public IDrawable getIcon()
	{
		return null;
	}

	public void createGui(IGuiHelper guiHelper)
	{

	}

	public void registerRecipes(IRecipeRegistration registration)
	{

	}

	public void addRecipeTransferHandler(IRecipeTransferRegistration registration)
	{

	}

	public List<ItemStack> getRecipeCatalystItemStacks()
	{
		return new ArrayList<>();
	}

	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		ResourceLocation uid = this.getUid();

		for (ItemStack itemStack : this.getRecipeCatalystItemStacks())
		{
			registration.addRecipeCatalyst(itemStack, uid);
		}

	}

	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{

	}

}
