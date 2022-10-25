package beyond_earth_giselle_addon.common.registries;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.inventory.AdvancedCompressorContainerMenu;
import beyond_earth_giselle_addon.common.inventory.ElectricBlastFurnaceContainerMenu;
import beyond_earth_giselle_addon.common.inventory.FuelLoaderContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegistryObject;

public class AddonMenuTypes
{
	public static final MenuTypeDeferredRegister MENU_TYPES = new MenuTypeDeferredRegister(BeyondEarthAddon.MODID);
	public static final RegistryObject<MenuType<FuelLoaderContainerMenu>> FUEL_LOADER = MENU_TYPES.register("fuel_loader", FuelLoaderContainerMenu::new);
	public static final RegistryObject<MenuType<ElectricBlastFurnaceContainerMenu>> ELECTRIC_BLAST_FURNACE = MENU_TYPES.register("electric_blast_furnace", ElectricBlastFurnaceContainerMenu::new);
	public static final RegistryObject<MenuType<AdvancedCompressorContainerMenu>> ADVANCED_COMPRESSOR = MENU_TYPES.register("advanced_compressor", AdvancedCompressorContainerMenu::new);

	private AddonMenuTypes()
	{

	}

}
