package beyond_earth_giselle_addon.common.inventory;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.util.TriFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MenuType.MenuSupplier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AddonMenuTypes
{
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, BeyondEarthAddon.MODID);
	public static final RegistryObject<MenuType<FuelLoaderContainerMenu>> FUEL_LOADER = register("fuel_loader", FuelLoaderContainerMenu::new);
	public static final RegistryObject<MenuType<ElectricBlastFurnaceContainerMenu>> ELECTRIC_BLAST_FURNACE = register("electric_blast_furnace", ElectricBlastFurnaceContainerMenu::new);
	public static final RegistryObject<MenuType<AdvancedCompressorContainerMenu>> ADVANCED_COMPRESSOR = register("advanced_compressor", AdvancedCompressorContainerMenu::new);
	public static final RegistryObject<MenuType<GravityNormalizerContainerMenu>> GRAVITY_NORMALIZER = register("gravity_normalizer", GravityNormalizerContainerMenu::new);

	public static <C extends AbstractContainerMenu> RegistryObject<MenuType<C>> register(String name, MenuSupplier<C> factory)
	{
		return MENU_TYPES.register(name, () -> new MenuType<C>(factory));
	}

	public static <T extends BlockEntity, C extends AbstractContainerMenu> RegistryObject<MenuType<C>> register(String name, TriFunction<Integer, Inventory, T, C> function)
	{
		return register(name, new IContainerFactory<C>()
		{
			@Override
			public C create(int windowId, Inventory inv, FriendlyByteBuf data)
			{
				return packetParser(windowId, inv, data, function);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static <T extends BlockEntity, C extends AbstractContainerMenu> C packetParser(int windowId, Inventory inv, FriendlyByteBuf data, TriFunction<Integer, Inventory, T, C> function)
	{
		BlockEntity blockEntity = inv.player.level.getBlockEntity(data.readBlockPos());
		return function.apply(windowId, inv, (T) blockEntity);
	}

	private AddonMenuTypes()
	{

	}

}
