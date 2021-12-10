package boss_tools_giselle_addon.common.compat.theoneprobe;

import boss_tools_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class TOPCompat extends CompatibleMod
{
	public static final String MODID = "theoneprobe";

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
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::imcQueue);
	}

	private void imcQueue(InterModEnqueueEvent event)
	{
		InterModComms.sendTo(MODID, "getTheOneProbe", ProbeInfoProvider::new);
	}

}
