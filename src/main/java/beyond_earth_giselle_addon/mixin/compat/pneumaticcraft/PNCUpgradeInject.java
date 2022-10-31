package beyond_earth_giselle_addon.mixin.compat.pneumaticcraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import beyond_earth_giselle_addon.common.compat.pneumaticcraft.AddonPNCUpgrade;
import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

@Mixin(value = PNCUpgrade.class, remap = false)
public abstract class PNCUpgradeInject
{
	@Inject(method = "getItem", at = @At("HEAD"), cancellable = true)
	private void getItem(int tier, CallbackInfoReturnable<Item> callbackInfo)
	{
		PNCUpgrade self = (PNCUpgrade) (Object) this;

		if (self instanceof AddonPNCUpgrade upgrade)
		{
			Item item = ForgeRegistries.ITEMS.getValue(AddonPNCUpgrade.getItemName(upgrade, tier));
			callbackInfo.setReturnValue(item);
		}

	}

}
