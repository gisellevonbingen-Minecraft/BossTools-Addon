package boss_tools_giselle_addon.common.mixin.compat.pneumaticcraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonObject;

import me.desht.pneumaticcraft.common.config.subconfig.ArmorHUDLayout;
import me.desht.pneumaticcraft.common.config.subconfig.BossToolsAddonArmorHUDLayout;

@Mixin(value = ArmorHUDLayout.class, remap = false)
public abstract class ArmorHUDLayoutMixin
{
	@Inject(method = "writeToJson", at = @At("TAIL"), cancellable = true)
	private void writeToJson(JsonObject json, CallbackInfo callbackInfo)
	{
		BossToolsAddonArmorHUDLayout.INSTANCE.writeToJson(json);
	}

	@Inject(method = "readFromJson", at = @At("TAIL"), cancellable = true)
	private void readFromJson(JsonObject json, CallbackInfo callbackInfo)
	{
		BossToolsAddonArmorHUDLayout.INSTANCE.readFromJson(json);
	}

}
