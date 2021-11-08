package boss_tools_giselle_addon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import boss_tools_giselle_addon.GravityNormalizeUtils;
import net.minecraft.entity.item.ItemEntity;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity
{
	@Inject(at = @At(value = "TAIL"), method = "Lnet/minecraft/entity/item/ItemEntity;tick()V")
	private void tick(CallbackInfo info)
	{
		ItemEntity entity = (ItemEntity) ((Object) this);
		GravityNormalizeUtils.setNormalizing(entity, false);
	}

}
