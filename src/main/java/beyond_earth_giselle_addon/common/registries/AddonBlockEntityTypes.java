package beyond_earth_giselle_addon.common.registries;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity;
import beyond_earth_giselle_addon.common.block.entity.ElectricBlastFurnaceBlockEntity;
import beyond_earth_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class AddonBlockEntityTypes
{
	public static final BlockEntityTypeDeferredRegister BLOCK_ENTITY_TYPES = new BlockEntityTypeDeferredRegister(BeyondEarthAddon.MODID);
	public static final RegistryObject<BlockEntityType<FuelLoaderBlockEntity>> FUEL_LOADER = BLOCK_ENTITY_TYPES.register("fuel_loader", AddonBlocks.FUEL_LOADER, FuelLoaderBlockEntity::new);
	public static final RegistryObject<BlockEntityType<ElectricBlastFurnaceBlockEntity>> ELECTRIC_BLAST_FURNACE = BLOCK_ENTITY_TYPES.register("electric_blast_furnace", AddonBlocks.ELECTRIC_BLAST_FURNACE, ElectricBlastFurnaceBlockEntity::new);
	public static final RegistryObject<BlockEntityType<AdvancedCompressorBlockEntity>> ADVANCED_COMPRESSOR = BLOCK_ENTITY_TYPES.register("advanced_compressor", AddonBlocks.ADVANCED_COMPRESSOR, AdvancedCompressorBlockEntity::new);

	private AddonBlockEntityTypes()
	{

	}

}
