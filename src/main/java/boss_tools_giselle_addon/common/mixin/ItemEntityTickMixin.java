package boss_tools_giselle_addon.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import boss_tools_giselle_addon.common.event.ItemTickEvent;
import net.minecraft.entity.item.ItemEntity;
import net.minecraftforge.common.MinecraftForge;

@Mixin(ItemEntity.class)
public abstract class ItemEntityTickMixin
{
	@Inject(at = @At(value = "RETURN"), method = "tick")
	private void tick(CallbackInfo info)
	{
		ItemEntity item = (ItemEntity) ((Object) this);
		MinecraftForge.EVENT_BUS.post(new ItemTickEvent(item));
	}

}
