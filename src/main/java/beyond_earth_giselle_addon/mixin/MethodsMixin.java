package beyond_earth_giselle_addon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import beyond_earth_giselle_addon.common.event.PlayerCreateSpaceStationEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.beyond_earth.events.Methods;

@Mixin(Methods.class)
public abstract class MethodsMixin
{
	@Inject(at = @At(value = "HEAD"), method = "createSpaceStation")
	private static void createSpaceStation(Player player, ServerLevel level, CallbackInfo info)
	{
		MinecraftForge.EVENT_BUS.post(new PlayerCreateSpaceStationEvent(player));
	}

}
