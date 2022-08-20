package boss_tools_giselle_addon.common.mixin;

import java.util.List;
import java.util.Map.Entry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipe;
import net.mrscauthd.boss_tools.inventory.RocketPartsItemHandler;
import net.mrscauthd.boss_tools.machines.NASAWorkbenchBlock;

@Mixin(NASAWorkbenchBlock.CustomTileEntity.class)
public abstract class WorkbenchTileEntityMixin
{
	@Inject(at = @At(value = "HEAD"), method = "consumeIngredient()Z", cancellable = true, remap = false)
	public void consumeIngredient(CallbackInfoReturnable<Boolean> info)
	{
		info.cancel();

		NASAWorkbenchBlock.CustomTileEntity tileEntity = (NASAWorkbenchBlock.CustomTileEntity) ((Object) this);
		WorkbenchingRecipe recipe = tileEntity.cacheRecipes();

		if (recipe == null)
		{
			info.setReturnValue(false);
			return;
		}

		RocketPartsItemHandler partsItemHandler = tileEntity.getPartsItemHandler();

		for (Entry<RocketPart, List<Ingredient>> entry : recipe.getParts().entrySet())
		{
			IItemHandlerModifiable subHandler = partsItemHandler.getSubHandlers().get(entry.getKey());

			int ingredientKinds = entry.getValue().size();

			for (int i = 0; i < ingredientKinds; i++)
			{
				subHandler.extractItem(i, 1, false);
			}

		}

		World level = tileEntity.getLevel();

		if (level instanceof ServerWorld)
		{
			ServerWorld serverLevel = (ServerWorld) level;
			BlockPos pos = tileEntity.getBlockPos();

			serverLevel.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
			serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 100, 0.1D, 0.1D, 0.1D, 0.7D);
		}

		info.setReturnValue(true);
	}

}
