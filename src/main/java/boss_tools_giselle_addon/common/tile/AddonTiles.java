package boss_tools_giselle_addon.common.tile;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.block.AddonBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AddonTiles
{
	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BossToolsAddon.MODID);
	public static final RegistryObject<TileEntityType<FuelLoaderTileEntity>> FUEL_LOADER = TILES.register("fuel_loader", () -> TileEntityType.Builder.of(FuelLoaderTileEntity::new, AddonBlocks.FUEL_LOADER.get()).build(null));
	public static final RegistryObject<TileEntityType<ElectricBlastFurnaceTileEntity>> ELECTRIC_BLAST_FURNACE = TILES.register("electric_blast_furnace", () -> TileEntityType.Builder.of(ElectricBlastFurnaceTileEntity::new, AddonBlocks.ELECTRIC_BLAST_FURNACE.get()).build(null));
	public static final RegistryObject<TileEntityType<AdvancedCompressorTileEntity>> ADVANCED_COMPRESSOR = TILES.register("advanced_compressor", () -> TileEntityType.Builder.of(AdvancedCompressorTileEntity::new, AddonBlocks.ADVANCED_COMPRESSOR.get()).build(null));
	public static final RegistryObject<TileEntityType<GravityNormalizerTileEntity>> GRAVITY_NORMALIZER = TILES.register("gravity_normalizer", () -> TileEntityType.Builder.of(GravityNormalizerTileEntity::new, AddonBlocks.GRAVITY_NORMALIZER.get()).build(null));

	private AddonTiles()
	{

	}

}
