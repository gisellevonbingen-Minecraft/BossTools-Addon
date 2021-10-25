package giselle.boss_tools_addon.client;

import giselle.boss_tools_addon.client.gui.FuelLoaderScreen;
import giselle.boss_tools_addon.common.inventory.container.AddonContainers;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonClientProxy
{
	public AddonClientProxy()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addGenericListener(ContainerType.class, this::registerContainer);
	}

	public void registerContainer(RegistryEvent.Register<ContainerType<?>> event)
	{
		ScreenManager.register(AddonContainers.FUEL_LOADER.get(), FuelLoaderScreen::new);
	}

}
