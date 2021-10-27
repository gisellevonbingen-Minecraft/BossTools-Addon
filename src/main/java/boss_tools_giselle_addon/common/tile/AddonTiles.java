package boss_tools_giselle_addon.common.tile;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.block.AddonBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AddonTiles
{
	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BossToolsAddon.MODID);
	public static final RegistryObject<TileEntityType<OxygenAccepterTileEntity>> OXYGEN_ACCEPTER = TILES.register("oxygen_accepter", () -> TileEntityType.Builder.of(OxygenAccepterTileEntity::new, AddonBlocks.OXYGEN_ACCEPTER.get()).build(null));
	public static final RegistryObject<TileEntityType<FuelLoaderTileEntity>> FUEL_LOADER = TILES.register("fuel_loader", () -> TileEntityType.Builder.of(FuelLoaderTileEntity::new, AddonBlocks.FUEL_LOADER.get()).build(null));
	public static final RegistryObject<TileEntityType<ElectricBlastFurnaceTileEntity>> ELECTRIC_BLAST_FURNACE = TILES.register("electric_blast_furnace", () -> TileEntityType.Builder.of(ElectricBlastFurnaceTileEntity::new, AddonBlocks.ELECTRIC_BLAST_FURNACE.get()).build(null));

	private AddonTiles()
	{

	}

}
