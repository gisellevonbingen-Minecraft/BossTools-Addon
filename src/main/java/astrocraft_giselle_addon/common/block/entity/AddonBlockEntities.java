package astrocraft_giselle_addon.common.block.entity;

import astrocraft_giselle_addon.common.BossToolsAddon;
import astrocraft_giselle_addon.common.block.AddonBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AddonBlockEntities
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, BossToolsAddon.MODID);
	public static final RegistryObject<BlockEntityType<FuelLoaderBlockEntity>> FUEL_LOADER = BLOCK_ENTITIES.register("fuel_loader", () -> BlockEntityType.Builder.of(FuelLoaderBlockEntity::new, AddonBlocks.FUEL_LOADER.get()).build(null));
	public static final RegistryObject<BlockEntityType<ElectricBlastFurnaceBlockEntity>> ELECTRIC_BLAST_FURNACE = BLOCK_ENTITIES.register("electric_blast_furnace", () -> BlockEntityType.Builder.of(ElectricBlastFurnaceBlockEntity::new, AddonBlocks.ELECTRIC_BLAST_FURNACE.get()).build(null));
	public static final RegistryObject<BlockEntityType<AdvancedCompressorBlockEntity>> ADVANCED_COMPRESSOR = BLOCK_ENTITIES.register("advanced_compressor", () -> BlockEntityType.Builder.of(AdvancedCompressorBlockEntity::new, AddonBlocks.ADVANCED_COMPRESSOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<GravityNormalizerBlockEntity>> GRAVITY_NORMALIZER = BLOCK_ENTITIES.register("gravity_normalizer", () -> BlockEntityType.Builder.of(GravityNormalizerBlockEntity::new, AddonBlocks.GRAVITY_NORMALIZER.get()).build(null));

	private AddonBlockEntities()
	{

	}

}
