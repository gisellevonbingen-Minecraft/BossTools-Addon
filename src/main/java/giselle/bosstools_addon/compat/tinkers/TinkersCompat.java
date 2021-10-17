package giselle.bosstools_addon.compat.tinkers;

import giselle.bosstools_addon.compat.CompatibleMod;
import giselle.bosstools_addon.compat.mekanism.gear.AddonMekanismModules;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class TinkersCompat extends CompatibleMod
{
	public static final String MODID = "tconstruct";

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

	@Override
	public String getModID()
	{
		return MODID;
	}

	@Override
	protected void onLoad()
	{
		AddonMekanismModules.registerAll();

		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		AddonTinkersFluids.FLUIDS.register(fml_bus);
	}

}
