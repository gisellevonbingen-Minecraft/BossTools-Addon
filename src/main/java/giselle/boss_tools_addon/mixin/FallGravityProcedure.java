package giselle.boss_tools_addon.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import giselle.boss_tools_addon.FallGravityProcedureEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;

@Mixin(net.mrscauthd.boss_tools.procedures.FallGravityProcedure.class)
public abstract class FallGravityProcedure
{
	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/procedures/FallGravityProcedure;executeProcedure(Ljava/util/Map;)V", cancellable = true, remap = false)
	private static void executeProcedure(Map<String, Object> dependencies, CallbackInfo info)
	{
		Entity entity = (Entity) dependencies.get("entity");
		FallGravityProcedureEvent e = new FallGravityProcedureEvent(entity);
		MinecraftForge.EVENT_BUS.post(e);

		if (e.isCanceled() == true)
		{
			info.cancel();
		}

	}

}
