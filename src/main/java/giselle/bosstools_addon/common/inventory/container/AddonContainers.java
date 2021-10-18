package giselle.bosstools_addon.common.inventory.container;

import giselle.bosstools_addon.BossToolsAddon;
import giselle.bosstools_addon.util.TriFunction;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;

public class AddonContainers
{
	@SuppressWarnings("unchecked")
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ContainerType.class, BossToolsAddon.MODID);
	public static final RegistryObject<ContainerType<FuelLoaderContainer>> FUEL_LOADER = register("fuel_loader", FuelLoaderContainer::new);

	public static <C extends Container> RegistryObject<ContainerType<C>> register(String name, IContainerFactory<C> factory)
	{
		return CONTAINERS.register(name, () -> new ContainerType<C>(factory));
	}

	public static <T extends TileEntity, C extends Container> RegistryObject<ContainerType<C>> register(String name, TriFunction<Integer, PlayerInventory, T, C> function)
	{
		return register(name, new IContainerFactory<C>()
		{
			@Override
			public C create(int windowId, PlayerInventory inv, PacketBuffer data)
			{
				return packetParser(windowId, inv, data, function);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static <T extends TileEntity, C extends Container> C packetParser(int windowId, PlayerInventory inv, PacketBuffer data, TriFunction<Integer, PlayerInventory, T, C> function)
	{
		TileEntity blockEntity = inv.player.level.getBlockEntity(data.readBlockPos());
		return function.apply(windowId, inv, (T) blockEntity);
	}

	private AddonContainers()
	{

	}

}
