package boss_tools_giselle_addon.common.mixin.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import boss_tools_giselle_addon.common.compat.pneumaticcraft.AddonEnumUpgrade;
import boss_tools_giselle_addon.common.compat.pneumaticcraft.AddonEnumUpgrade.LazyEnumUpgrade;
import boss_tools_giselle_addon.common.util.EnumFactory;
import me.desht.pneumaticcraft.api.item.EnumUpgrade;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

@Mixin(value = EnumUpgrade.class, remap = false)
@Unique
public abstract class EnumUpgradeMixin
{
	@Shadow
	@Final
	@Mutable
	private static EnumUpgrade[] $VALUES;

	@Inject(at = @At("TAIL"), method = "<clinit>")
	private static void clinit(CallbackInfo ci)
	{
		for (LazyEnumUpgrade lazy : AddonEnumUpgrade.LAZYS)
		{
			EnumUpgrade upgrade = EnumUpgradeMixin.add(i -> init(lazy.internalName, i, lazy.name));
			lazy.set(upgrade);
		}

	}

	@Invoker("<init>")
	private static EnumUpgrade init(String internalName, int internalId, String name)
	{
		throw new AssertionError();
	}

	@Invoker("<init>")
	private static EnumUpgrade init(String internalName, int internalId, String name, int maxTier, String... depModIds)
	{
		throw new AssertionError();
	}

	private static EnumUpgrade add(EnumFactory<EnumUpgrade> factory)
	{
		List<EnumUpgrade> variants = new ArrayList<>(Arrays.asList($VALUES));
		EnumUpgrade upgrade = factory.make(variants.get(variants.size() - 1).ordinal() + 1);
		variants.add(upgrade);
		$VALUES = variants.toArray(EnumUpgrade[]::new);
		return upgrade;
	}

	@Inject(method = "getItemName", at = @At("HEAD"), cancellable = true)
	private void getItemName(int tier, CallbackInfoReturnable<String> callbackInfo)
	{
		EnumUpgrade self = (EnumUpgrade) (Object) this;

		if (AddonEnumUpgrade.ADDEDS.contains(self) == true)
		{
			callbackInfo.setReturnValue(AddonEnumUpgrade.getItemName(self, tier).toString());
		}

	}

	@Inject(method = "getItem", at = @At("HEAD"), cancellable = true)
	private void getItem(int tier, CallbackInfoReturnable<Item> callbackInfo)
	{
		EnumUpgrade self = (EnumUpgrade) (Object) this;

		if (AddonEnumUpgrade.ADDEDS.contains(self) == true)
		{
			ResourceLocation name = AddonEnumUpgrade.getItemName(self, tier);
			callbackInfo.setReturnValue(ForgeRegistries.ITEMS.getValue(name));
		}

	}

}
