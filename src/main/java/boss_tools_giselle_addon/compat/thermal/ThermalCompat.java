package boss_tools_giselle_addon.compat.thermal;

import boss_tools_giselle_addon.compat.CompatibleMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ThermalCompat extends CompatibleMod
{
	public static final String MODID = "thermal_expansion";

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
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		AddonThermalItems.ITEMS.register(fml_bus);
	}

}
