package beyond_earth_giselle_addon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import beyond_earth_giselle_addon.common.event.LanderExplodeEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.beyond_earth.entities.LanderEntity;

@Mixin(LanderEntity.class)
public abstract class LanderEntityMixin
{
	@Inject(at = @At(value = "HEAD"), method = "causeFallDamage")
	private void causeFallDamage(float fallDistance, float modifier, DamageSource source, CallbackInfoReturnable<Boolean> info)
	{
		LanderEntity lander = (LanderEntity) ((Object) this);

		if (fallDistance >= 3.0F)
		{
			MinecraftForge.EVENT_BUS.post(new LanderExplodeEvent(lander));
		}

	}

}
