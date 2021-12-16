package astrocraft_giselle_addon.common.block;

import astrocraft_giselle_addon.common.AstroCraftAddon;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AddonBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Block.class, AstroCraftAddon.MODID);
	public static final RegistryObject<FuelLoaderBlock> FUEL_LOADER = BLOCKS.register("fuel_loader", () -> new FuelLoaderBlock(createDefaultBlockProperties()));
	public static final RegistryObject<ElectricBlastFurnaceBlock> ELECTRIC_BLAST_FURNACE = BLOCKS.register("electric_blast_furnace", () -> new ElectricBlastFurnaceBlock(createDefaultBlockProperties()));
	public static final RegistryObject<AdvancedCompressorBlock> ADVANCED_COMPRESSOR = BLOCKS.register("advanced_compressor", () -> new AdvancedCompressorBlock(createDefaultBlockProperties()));
	public static final RegistryObject<GravityNormalizerBlock> GRAVITY_NORMALIZER = BLOCKS.register("gravity_normalizer", () -> new GravityNormalizerBlock(createDefaultBlockProperties()));

	public static BlockBehaviour.Properties createDefaultBlockProperties()
	{
		return BlockBehaviour.Properties.of(Material.METAL).strength(3.0F);
	}

	private AddonBlocks()
	{

	}

}
