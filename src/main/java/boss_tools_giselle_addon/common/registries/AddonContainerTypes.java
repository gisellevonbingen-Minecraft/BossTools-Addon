package boss_tools_giselle_addon.common.registries;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.inventory.container.AdvancedCompressorContainer;
import boss_tools_giselle_addon.common.inventory.container.ElectricBlastFurnaceContainer;
import boss_tools_giselle_addon.common.inventory.container.FuelLoaderContainer;
import boss_tools_giselle_addon.common.inventory.container.GravityNormalizerContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

public class AddonContainerTypes
{
	public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(BossToolsAddon.MODID);
	public static final RegistryObject<ContainerType<FuelLoaderContainer>> FUEL_LOADER = CONTAINER_TYPES.register("fuel_loader", FuelLoaderContainer::new);
	public static final RegistryObject<ContainerType<ElectricBlastFurnaceContainer>> ELECTRIC_BLAST_FURNACE = CONTAINER_TYPES.register("electric_blast_furnace", ElectricBlastFurnaceContainer::new);
	public static final RegistryObject<ContainerType<AdvancedCompressorContainer>> ADVANCED_COMPRESSOR = CONTAINER_TYPES.register("advanced_compressor", AdvancedCompressorContainer::new);
	public static final RegistryObject<ContainerType<GravityNormalizerContainer>> GRAVITY_NORMALIZER = CONTAINER_TYPES.register("gravity_normalizer", GravityNormalizerContainer::new);

	private AddonContainerTypes()
	{

	}

}
