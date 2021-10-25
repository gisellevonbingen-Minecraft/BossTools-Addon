package boss_tools_giselle_addon.compat.mekanism;

import boss_tools_giselle_addon.compat.CompatibleMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class MekanismCompat extends CompatibleMod
{
	public static final String MODID = "mekanism";

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
		CommonEventListener eventListener = new CommonEventListener();
		
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		AddonMekanismItems.ITEMS.register(fml_bus);
		fml_bus.register(eventListener);

		MinecraftForge.EVENT_BUS.register(eventListener);
	}

}
