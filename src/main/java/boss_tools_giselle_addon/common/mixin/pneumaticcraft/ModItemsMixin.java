package boss_tools_giselle_addon.common.mixin.pneumaticcraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import boss_tools_giselle_addon.common.compat.pneumaticcraft.AddonEnumUpgrade;
import me.desht.pneumaticcraft.api.item.EnumUpgrade;
import me.desht.pneumaticcraft.common.core.ModItems;

@Mixin(value = ModItems.class, remap = false)
public abstract class ModItemsMixin
{
	@Inject(method = "register(Lme/desht/pneumaticcraft/api/item/EnumUpgrade;)V", at = @At("HEAD"), cancellable = true)
	private static void register(EnumUpgrade upgrade, CallbackInfo callbackInfo)
	{
		if (AddonEnumUpgrade.ADDEDS.contains(upgrade) == true)
		{
			callbackInfo.cancel();
		}

	}

}
