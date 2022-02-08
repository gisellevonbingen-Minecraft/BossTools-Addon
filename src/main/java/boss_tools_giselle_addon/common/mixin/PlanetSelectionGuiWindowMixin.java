package boss_tools_giselle_addon.common.mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.common.item.crafting.IngredientStack;
import boss_tools_giselle_addon.common.item.crafting.SpaceStationRecipe;
import boss_tools_giselle_addon.common.util.LivingEntityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.gui.helper.ImageButtonPlacer;
import net.mrscauthd.boss_tools.gui.screens.planetselection.PlanetSelectionGuiWindow;

@Mixin(PlanetSelectionGuiWindow.class)
public abstract class PlanetSelectionGuiWindowMixin
{
	@Inject(at = @At(value = "HEAD"), method = "spaceStationCreatorButtonManager", cancellable = true)
	public void spaceStationCreatorButtonManager(ResourceLocation brb, ResourceLocation brb2, ResourceLocation bbb, ResourceLocation bbb2, int mouseX, int mouseY, int left, int top, int width, int height, MatrixStack ms, ImageButtonPlacer button, String orbitType, String gravity, String oxygen, String temperature, CallbackInfo info)
	{
		PlanetSelectionGuiWindow screen = (PlanetSelectionGuiWindow) ((Object) this);
		boolean spaceStationItemList = screen.getSpaceStationItemList();

		if (spaceStationItemList == true)
		{
			button.setTexture(bbb);
		}
		else
		{
			button.setTexture(brb);
		}

		if (GuiHelper.isHover(screen.getBounds(left, top, width, height), mouseX, mouseY) == true)
		{
			List<ITextComponent> list = new ArrayList<ITextComponent>();
			list.add(ITextComponent.nullToEmpty("\u00A79Item Requirement:"));

			Minecraft minecraft = screen.getMinecraft();
			ClientPlayerEntity player = minecraft.player;
			SpaceStationRecipe recipe = SpaceStationRecipe.getRecipe(player.level.getRecipeManager());

			if (recipe != null)
			{
				boolean playingMode = LivingEntityHelper.isPlayingMode(player);
				
				for (IngredientStack ingredientStack : recipe.getIngredientStacks())
				{
					int count = this.getItemCount(player, ingredientStack.getIngredient());
					boolean check = playingMode == false || count >= ingredientStack.getCount();
					ITextComponent component = Arrays.stream(ingredientStack.getIngredient().getItems()).findFirst().map(ItemStack::getHoverName).orElse(StringTextComponent.EMPTY);
					list.add(new StringTextComponent("\u00A78[\u00A76" + count + "/" + ingredientStack.getCount() + "\u00A78]" + (check ? "\u00A7a" : "\u00A7c") + " " + component.getString() + (ingredientStack.getCount() > 1 ? "'s" : "")));
				}

			}

			list.add(ITextComponent.nullToEmpty("\u00A7c----------------"));
			list.add(ITextComponent.nullToEmpty("\u00A79Type: " + "\u00A73" + orbitType));
			list.add(ITextComponent.nullToEmpty("\u00A79Gravity: " + "\u00A73" + gravity));
			list.add(ITextComponent.nullToEmpty("\u00A79Oxygen: " + "\u00A7" + oxygen));
			list.add(ITextComponent.nullToEmpty("\u00A79Temperature: \u00A7" + temperature));
			screen.renderComponentTooltip(ms, list, mouseX, mouseY);

			if (spaceStationItemList == true)
			{
				button.setTexture(bbb2);
			}
			else
			{
				button.setTexture(brb2);
			}

		}

		info.cancel();
	}

	public int getItemCount(PlayerEntity player, Ingredient ingredient)
	{
		PlayerInventory inventory = player.inventory;
		int count = 0;

		for (int i = 0; i < inventory.getContainerSize(); ++i)
		{
			ItemStack itemStack = inventory.getItem(i);

			if (ingredient.test(itemStack) == true)
			{
				count += itemStack.getCount();
			}

		}

		return count;
	}

	@Inject(at = @At(value = "HEAD"), method = "getSpaceStationItemList", cancellable = true)
	private void getSpaceStationItemList(CallbackInfoReturnable<Boolean> info)
	{
		PlanetSelectionGuiWindow screen = (PlanetSelectionGuiWindow) ((Object) this);
		Minecraft minecraft = screen.getMinecraft();
		ClientPlayerEntity player = minecraft.player;
		boolean result = true;

		if (LivingEntityHelper.isPlayingMode(player) == true)
		{
			SpaceStationRecipe recipe = SpaceStationRecipe.getRecipe(player.level.getRecipeManager());

			if (recipe != null)
			{
				result = recipe.getIngredientStacks().stream().allMatch(i -> this.getItemCount(player, i.getIngredient()) >= i.getCount());
			}

		}

		info.setReturnValue(result);
	}

}
