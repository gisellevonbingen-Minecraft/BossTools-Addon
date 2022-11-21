package boss_tools_giselle_addon.common.mixin.boss_tools;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import boss_tools_giselle_addon.common.item.crafting.IngredientStack;
import boss_tools_giselle_addon.common.item.crafting.SpaceStationRecipe;
import boss_tools_giselle_addon.common.util.LivingEntityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.mrscauthd.boss_tools.gui.screens.planetselection.PlanetSelectionGui;

@Mixin(PlanetSelectionGui.class)
public abstract class PlanetSelectionGuiMixin
{
	@Inject(at = @At(value = "HEAD"), method = "deleteItems", cancellable = true, remap = false)
	private static void deleteItems(PlayerEntity player, CallbackInfo info)
	{
		info.cancel();

		if (LivingEntityHelper.isPlayingMode(player) == false)
		{
			return;
		}

		PlayerInventory inv = player.inventory;
		SpaceStationRecipe recipe = (SpaceStationRecipe) player.level.getRecipeManager().byKey(SpaceStationRecipe.KEY).orElse(null);

		for (IngredientStack ingredientStack : recipe.getIngredientStacks())
		{
			inv.clearOrCountMatchingItems(ingredientStack::testWithoutCount, ingredientStack.getCount(), inv);
		}

	}

}
