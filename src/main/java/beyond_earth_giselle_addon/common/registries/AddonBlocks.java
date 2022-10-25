package beyond_earth_giselle_addon.common.registries;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.block.AdvancedCompressorBlock;
import beyond_earth_giselle_addon.common.block.ElectricBlastFurnaceBlock;
import beyond_earth_giselle_addon.common.block.FuelLoaderBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class AddonBlocks
{
	public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(BeyondEarthAddon.MODID);
	public static final BlockRegistryObject<FuelLoaderBlock, BlockItem> FUEL_LOADER = BLOCKS.register("fuel_loader", () -> new FuelLoaderBlock(createDefaultBlockProperties()), AddonBlocks::createBlockItem);
	public static final BlockRegistryObject<ElectricBlastFurnaceBlock, BlockItem> ELECTRIC_BLAST_FURNACE = BLOCKS.register("electric_blast_furnace", () -> new ElectricBlastFurnaceBlock(createDefaultBlockProperties()), AddonBlocks::createBlockItem);
	public static final BlockRegistryObject<AdvancedCompressorBlock, BlockItem> ADVANCED_COMPRESSOR = BLOCKS.register("advanced_compressor", () -> new AdvancedCompressorBlock(createDefaultBlockProperties()), AddonBlocks::createBlockItem);

	public static BlockBehaviour.Properties createDefaultBlockProperties()
	{
		return BlockBehaviour.Properties.of(Material.METAL).strength(3.0F);
	}

	public static BlockItem createBlockItem(Block block)
	{
		return new BlockItem(block, AddonItems.getMainItemProperties());
	}

	private AddonBlocks()
	{

	}

}
