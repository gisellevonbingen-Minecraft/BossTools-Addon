package net.mrscauthd.boss_tools.compat.tinkers;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mrscauthd.boss_tools.compat.CompatibleMod;

public class TinkersCompat extends CompatibleMod {

	public static final String MODID = "tconstruct";

	public static ResourceLocation rl(String path) {
		return new ResourceLocation(MODID, path);
	}

	@Override
	public String getModID() {
		return MODID;
	}

	@Override
	protected void onLoad() {
//		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
//		TinkersBossToolsFluids.FLUIDS.register(bus);
	}

}
