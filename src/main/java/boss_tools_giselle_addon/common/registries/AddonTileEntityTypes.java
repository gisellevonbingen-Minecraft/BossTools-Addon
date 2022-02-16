package boss_tools_giselle_addon.common.registries;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity;
import boss_tools_giselle_addon.common.tile.ElectricBlastFurnaceTileEntity;
import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import boss_tools_giselle_addon.common.tile.GravityNormalizerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class AddonTileEntityTypes
{
	public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(BossToolsAddon.MODID);
	public static final RegistryObject<TileEntityType<FuelLoaderTileEntity>> FUEL_LOADER = TILE_ENTITY_TYPES.register("fuel_loader", AddonBlocks.FUEL_LOADER, FuelLoaderTileEntity::new);
	public static final RegistryObject<TileEntityType<ElectricBlastFurnaceTileEntity>> ELECTRIC_BLAST_FURNACE = TILE_ENTITY_TYPES.register("electric_blast_furnace", AddonBlocks.ELECTRIC_BLAST_FURNACE, ElectricBlastFurnaceTileEntity::new);
	public static final RegistryObject<TileEntityType<AdvancedCompressorTileEntity>> ADVANCED_COMPRESSOR = TILE_ENTITY_TYPES.register("advanced_compressor", AddonBlocks.ADVANCED_COMPRESSOR, AdvancedCompressorTileEntity::new);
	public static final RegistryObject<TileEntityType<GravityNormalizerTileEntity>> GRAVITY_NORMALIZER = TILE_ENTITY_TYPES.register("gravity_normalizer", AddonBlocks.GRAVITY_NORMALIZER, GravityNormalizerTileEntity::new);

	private AddonTileEntityTypes()
	{

	}

}
