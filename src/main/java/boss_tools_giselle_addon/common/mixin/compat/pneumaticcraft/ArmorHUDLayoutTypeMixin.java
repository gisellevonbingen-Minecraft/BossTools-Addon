package boss_tools_giselle_addon.common.mixin.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import boss_tools_giselle_addon.common.util.EnumFactory;
import me.desht.pneumaticcraft.common.config.subconfig.ArmorHUDLayout;
import me.desht.pneumaticcraft.common.config.subconfig.ArmorHUDLayout.LayoutItem;
import me.desht.pneumaticcraft.common.config.subconfig.ArmorHUDLayout.LayoutType;
import me.desht.pneumaticcraft.common.config.subconfig.BossToolsAddonArmorHUDLayout;

@Mixin(value = LayoutType.class, remap = false)
@Unique
public abstract class ArmorHUDLayoutTypeMixin
{
	@Shadow
	@Final
	@Mutable
	private static LayoutType[] $VALUES;

	@Inject(at = @At("TAIL"), method = "<clinit>")
	private static void clinit(CallbackInfo ci)
	{
		BossToolsAddonArmorHUDLayout.LayoutType.SPACE_BREATHING = add("SPACE_BREATHING", (layout, item) -> BossToolsAddonArmorHUDLayout.INSTANCE.spaceBreathingStat = item);
	}

	@Invoker("<init>")
	private static LayoutType init(String internalName, int internalId, BiConsumer<ArmorHUDLayout, LayoutItem> consumer)
	{
		throw new AssertionError();
	}

	private static LayoutType add(String internalName, BiConsumer<ArmorHUDLayout, LayoutItem> consumer)
	{
		return add(i -> init(internalName, i, consumer));
	}

	private static LayoutType add(EnumFactory<LayoutType> factory)
	{
		List<LayoutType> variants = new ArrayList<>(Arrays.asList($VALUES));
		LayoutType layoutType = factory.make(variants.get(variants.size() - 1).ordinal() + 1);
		variants.add(layoutType);
		$VALUES = variants.toArray(LayoutType[]::new);
		return layoutType;
	}

}
