package boss_tools_giselle_addon.common.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.GravityNormalizingCommonHandler;
import boss_tools_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.SpaceBreathingCommonHandler;
import boss_tools_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.SpaceFireProofCommonHandler;
import boss_tools_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.VenusAcidProofCommonHandler;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.common.pneumatic_armor.BossToolsAddonArmorUpgradeRegistry;
import net.minecraft.util.ResourceLocation;

public class AddonCommonUpgradeHandlers
{
	private static final List<IArmorUpgradeHandler<?>> HANDLERS = new ArrayList<>();
	private static final List<IArmorUpgradeHandler<?>> READONLY_LIST = Collections.unmodifiableList(HANDLERS);

	public static final SpaceBreathingCommonHandler SPACE_BREATHING = register("space_breathing", SpaceBreathingCommonHandler::new);
	public static final GravityNormalizingCommonHandler GRAVITY_NORMALIZING = register("gravity_normalizing", GravityNormalizingCommonHandler::new);
	public static final SpaceFireProofCommonHandler SPACE_FIRE_PROOF = register("space_fire_proof", SpaceFireProofCommonHandler::new);
	public static final VenusAcidProofCommonHandler VENUS_ACID_PROOF = register("venus_acid_proof", VenusAcidProofCommonHandler::new);

	private static <T extends IArmorUpgradeHandler<?>> T register(String name, Function<ResourceLocation, T> func)
	{
		ResourceLocation id = BossToolsAddon.rl(name);
		T handler = func.apply(id);
		HANDLERS.add(handler);
		return handler;
	}

	public static void register()
	{
		BossToolsAddonArmorUpgradeRegistry.registerUpgradeHandler(HANDLERS);
	}

	public static List<IArmorUpgradeHandler<?>> getHandlers()
	{
		return READONLY_LIST;
	}

}
