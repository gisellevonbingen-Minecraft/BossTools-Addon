package boss_tools_giselle_addon.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import boss_tools_giselle_addon.common.event.LanderExplodeEvent;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.entity.LanderEntity;

@Mixin(LanderEntity.class)
public abstract class LanderEntityMixin
{
	@Inject(at = @At(value = "HEAD"), method = "hurt")
	private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info)
	{
		LanderEntity lander = (LanderEntity) ((Object) this);

		if (source == DamageSource.FALL)
		{
			MinecraftForge.EVENT_BUS.post(new LanderExplodeEvent(lander));
		}

	}

}
