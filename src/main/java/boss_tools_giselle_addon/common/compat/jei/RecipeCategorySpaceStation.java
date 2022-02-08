package boss_tools_giselle_addon.common.compat.jei;

import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.item.crafting.IngredientStack;
import boss_tools_giselle_addon.common.item.crafting.SpaceStationRecipe;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;

public class RecipeCategorySpaceStation extends RecipeCategoryRecipeType<BossToolsRecipeType<? extends SpaceStationRecipe>, SpaceStationRecipe>
{
	public static final ResourceLocation BACKGROUND_LOCATION = BossToolsAddon.rl("textures/jei/space_station_background.png");
	public static final int BACKGROUND_WIDTH = 144;
	public static final int BACKGROUND_HEIGHT = 51;

	public static final int SLOTS_X_CENTER = 72;
	public static final int SLOTS_Y_TOP = 6;
	public static final int SLOTS_X_OFFSET = 18;
	public static final int SLOTS_Y_OFFSET = 18;

	private IDrawable background;
	private IDrawable icon;
	private IDrawable slot;
	private ITextComponent[] tooltips;

	public RecipeCategorySpaceStation(Class<? extends SpaceStationRecipe> recipeClass, BossToolsRecipeType<? extends SpaceStationRecipe> recipeType)
	{
		super(recipeClass, recipeType);
	}

	@Override
	public void createGui(IGuiHelper guiHelper)
	{
		super.createGui(guiHelper);
		this.background = this.createBackground(guiHelper);
		this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModInnet.BLUE_IRON_PLATING_BLOCK_ITEM.get()));
		this.slot = guiHelper.getSlotDrawable();

		String tl = BossToolsAddon.tl(AddonJeiPlugin.JEI_INFO, this.getKey());
		this.tooltips = Arrays.stream(new TranslationTextComponent(tl).getString().split("\n")).map(StringTextComponent::new).toArray(ITextComponent[]::new);
	}

	protected IDrawable createBackground(IGuiHelper guiHelper)
	{
		return guiHelper.createDrawable(BACKGROUND_LOCATION, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
	}

	@Override
	public void setIngredients(SpaceStationRecipe recipe, IIngredients ingredients)
	{
		ingredients.setInputIngredients(recipe.getIngredients());
	}

	public int[] getSpaceStationItemPosition(int index, int count)
	{
		int xIndex = index % count;
		int yIndex = index / count;
		int slots_width = count * SLOTS_X_OFFSET;
		int xPosition = SLOTS_X_CENTER + (xIndex * SLOTS_X_OFFSET) - (slots_width / 2);
		int yPosition = SLOTS_Y_TOP + (yIndex * SLOTS_Y_OFFSET);
		return new int[]{xPosition, yPosition};
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SpaceStationRecipe recipe, IIngredients ingredients)
	{
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		NonNullList<IngredientStack> ingredientStacks = recipe.getIngredientStacks();
		int count = ingredientStacks.size();

		for (int i = 0; i < count; i++)
		{
			int[] pos = this.getSpaceStationItemPosition(i, count);
			IngredientStack ingredientStack = ingredientStacks.get(i);
			itemStacks.init(i, true, pos[0], pos[1]);
			itemStacks.set(i, NonNullList.of(ItemStack.EMPTY, ingredientStack.getItems()));
		}

	}

	@Override
	public void draw(SpaceStationRecipe recipe, MatrixStack stack, double mouseX, double mouseY)
	{
		super.draw(recipe, stack, mouseX, mouseY);

		NonNullList<IngredientStack> ingredientStacks = recipe.getIngredientStacks();
		int count = ingredientStacks.size();

		for (int i = 0; i < count; i++)
		{
			int[] pos = this.getSpaceStationItemPosition(i, count);
			this.slot.draw(stack, pos[0], pos[1]);
		}

		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer font = minecraft.font;
		int tooltipYOffset = this.getSpaceStationItemPosition(ingredientStacks.size() - 1, count)[1] + SLOTS_Y_OFFSET + 4;
		ITextComponent[] tooltips = this.getTooltips();

		for (int i = 0; i < tooltips.length; i++)
		{
			ITextComponent tooltip = tooltips[i];
			int tooltipWidth = font.width(tooltip);
			font.draw(stack, tooltip, SLOTS_X_CENTER - (tooltipWidth / 2), tooltipYOffset + font.lineHeight * i, 0xFF404040);
		}

	}

	@Override
	public List<ItemStack> getRecipeCatalystItemStacks()
	{
		List<ItemStack> list = super.getRecipeCatalystItemStacks();
		list.add(new ItemStack(ModInnet.TIER_1_ROCKET_ITEM.get()));
		list.add(new ItemStack(ModInnet.TIER_2_ROCKET_ITEM.get()));
		list.add(new ItemStack(ModInnet.TIER_3_ROCKET_ITEM.get()));

		return list;
	}

	@Override
	public IDrawable getBackground()
	{
		return this.background;
	}

	@Override
	public IDrawable getIcon()
	{
		return this.icon;
	}

	public ITextComponent[] getTooltips()
	{
		return this.tooltips;
	}

}
