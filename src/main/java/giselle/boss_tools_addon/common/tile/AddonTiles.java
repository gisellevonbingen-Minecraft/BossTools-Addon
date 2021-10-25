package giselle.boss_tools_addon.common.tile;

import giselle.boss_tools_addon.BossToolsAddon;
import giselle.boss_tools_addon.common.block.AddonBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AddonTiles
{
	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BossToolsAddon.MODID);
	public static final RegistryObject<TileEntityType<OxygenAccepterTileEntity>> OXYGEN_ACCEPTER = TILES.register("oxygen_accepter", () -> TileEntityType.Builder.of(OxygenAccepterTileEntity::new, AddonBlocks.OXYGEN_ACCEPTER.get()).build(null));
	public static final RegistryObject<TileEntityType<FuelLoaderTileEntity>> FUEL_LOADER = TILES.register("fuel_loader", () -> TileEntityType.Builder.of(FuelLoaderTileEntity::new, AddonBlocks.FUEL_LOADER.get()).build(null));

	private AddonTiles()
	{

	}

}
