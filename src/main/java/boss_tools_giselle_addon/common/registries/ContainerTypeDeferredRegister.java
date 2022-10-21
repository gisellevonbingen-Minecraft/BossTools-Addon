package boss_tools_giselle_addon.common.registries;

import boss_tools_giselle_addon.common.util.TriFunction;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeDeferredRegister extends DeferredRegisterWrapper<ContainerType<?>>
{
	public ContainerTypeDeferredRegister(String modid)
	{
		super(modid, ForgeRegistries.CONTAINERS);
	}

	public <C extends Container> RegistryObject<ContainerType<C>> register(String name, IContainerFactory<C> factory)
	{
		return this.register(name, () -> new ContainerType<>(factory));
	}

	public <T extends TileEntity, C extends Container> RegistryObject<ContainerType<C>> register(String name, TriFunction<Integer, PlayerInventory, T, C> function)
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
	public <T extends TileEntity, C extends Container> C packetParser(int windowId, PlayerInventory inv, PacketBuffer data, TriFunction<Integer, PlayerInventory, T, C> function)
	{
		TileEntity blockEntity = inv.player.level.getBlockEntity(data.readBlockPos());
		return function.apply(windowId, inv, (T) blockEntity);
	}

}
