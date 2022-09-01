package boss_tools_giselle_addon.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import boss_tools_giselle_addon.common.event.PlayerCreateSpaceStationEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.events.Methodes;

@Mixin(Methodes.class)
public abstract class MethodesMixin
{
	@Inject(at = @At(value = "HEAD"), method = "createSpaceStation", remap = false)
	private static void createSpaceStation(PlayerEntity player, ServerWorld level, CallbackInfo info)
	{
		MinecraftForge.EVENT_BUS.post(new PlayerCreateSpaceStationEvent(player));
	}

}
