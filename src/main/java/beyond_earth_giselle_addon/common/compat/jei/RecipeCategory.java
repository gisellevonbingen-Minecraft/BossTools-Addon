package beyond_earth_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class RecipeCategory<R> implements IRecipeCategory<R>
{
	private final RecipeType<R> recipeType;

	public RecipeCategory(RecipeType<R> recipeType)
	{
		this.recipeType = recipeType;
	}

	@Override
	public abstract IDrawable getBackground();

	public RecipeType<R> getRecipeType()
	{
		return this.recipeType;
	}
	
	@Override
	public Class<? extends R> getRecipeClass()
	{
		return this.getRecipeType().getRecipeClass();
	}

	@Override
	public ResourceLocation getUid()
	{
		return this.getRecipeType().getUid();
	}

	@Override
	public Component getTitle()
	{
		return AddonJeiPlugin.getCategoryTitle(this.getUid());
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
