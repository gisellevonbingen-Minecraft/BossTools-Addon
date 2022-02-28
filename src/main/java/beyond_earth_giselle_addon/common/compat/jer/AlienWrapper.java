package beyond_earth_giselle_addon.common.compat.jer;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import jeresources.reference.Resources;
import jeresources.util.Font;
import jeresources.util.RenderHelper;
import jeresources.util.TranslationHelper;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryExtension;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.beyond_earth.entity.alien.AlienEntity;

public class AlienWrapper implements IRecipeCategoryExtension
{
	private final AlienEntry entry;
	private IFocus<ItemStack> focus;

	public AlienWrapper(AlienEntry entry)
	{
		this.entry = entry;
	}

	public AlienTradeList getTrades(int level)
	{
		return entry.getAlienTrade(level);
	}

	public int getMaxLevel()
	{
		return entry.getMaxLevel();
	}

	public List<Integer> getPossibleLevels(IFocus<ItemStack> focus)
	{
		return entry.getPossibleLevels(focus);
	}

	public void setFocus(IFocus<ItemStack> focus)
	{
		this.focus = focus;
	}

	public List<ItemStack> getPois()
	{
		return entry.getPois();
	}

	public boolean hasPois()
	{
		return entry.hasPois();
	}

	@Override
	public void drawInfo(int recipeWidth, int recipeHeight, PoseStack poseStack, double mouseX, double mouseY)
	{
		RenderHelper.scissor(poseStack, 7, 43, 59, 79);
		AlienEntity alien = entry.getAlienEntity();
		RenderHelper.renderEntity(poseStack, 37, 118, 36.0F, 38 - mouseX, 80 - mouseY, alien);
		RenderHelper.stopScissor();

		int y = AlienCategory.Y_ITEM_DISTANCE * (6 - getPossibleLevels(focus).size()) / 2;
		int i;

		for (i = 0; i < getPossibleLevels(focus).size(); i++)
		{
			RenderHelper.drawTexture(poseStack, 130, y + i * AlienCategory.Y_ITEM_DISTANCE, 0, 120, 20, 20, Resources.Gui.Jei.VILLAGER.getResource());
			RenderHelper.drawTexture(poseStack, AlienCategory.X_FIRST_ITEM, y + i * AlienCategory.Y_ITEM_DISTANCE, 22, 120, 18, 18, Resources.Gui.Jei.VILLAGER.getResource());
			RenderHelper.drawTexture(poseStack, AlienCategory.X_FIRST_ITEM + AlienCategory.X_ITEM_DISTANCE, y + i * AlienCategory.Y_ITEM_DISTANCE, 22, 120, 18, 18, Resources.Gui.Jei.VILLAGER.getResource());
			RenderHelper.drawTexture(poseStack, AlienCategory.X_ITEM_RESULT, y + i * AlienCategory.Y_ITEM_DISTANCE, 22, 120, 18, 18, Resources.Gui.Jei.VILLAGER.getResource());
		}

		i = 0;

		for (int level : getPossibleLevels(focus))
		{
			Font.normal.print(poseStack, "lv. " + (level + 1), 72, y + i++ * AlienCategory.Y_ITEM_DISTANCE + 6);
		}

		Font.normal.print(poseStack, TranslationHelper.translateAndFormat(entry.getDisplayName()), 5, 5);

		if (entry.hasPois() == true)
		{
			Font.normal.splitPrint(poseStack, TranslationHelper.translateAndFormat("jer.villager.poi"), 5, 18, 45);
			RenderHelper.drawTexture(poseStack, 49, 18, 22, 120, 18, 18, Resources.Gui.Jei.VILLAGER.getResource());
		}

	}

}
