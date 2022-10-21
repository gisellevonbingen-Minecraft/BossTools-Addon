package boss_tools_giselle_addon.common.registries;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.block.AdvancedCompressorBlock;
import boss_tools_giselle_addon.common.block.ElectricBlastFurnaceBlock;
import boss_tools_giselle_addon.common.block.FuelLoaderBlock;
import boss_tools_giselle_addon.common.block.GravityNormalizerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraftforge.common.ToolType;

public class AddonBlocks
{
	public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(BossToolsAddon.MODID);
	public static final BlockRegistryObject<FuelLoaderBlock, BlockItem> FUEL_LOADER = BLOCKS.register("fuel_loader", () -> new FuelLoaderBlock(createDefaultBlockProperties()), AddonBlocks::createBlockItem);
	public static final BlockRegistryObject<ElectricBlastFurnaceBlock, BlockItem> ELECTRIC_BLAST_FURNACE = BLOCKS.register("electric_blast_furnace", () -> new ElectricBlastFurnaceBlock(createDefaultBlockProperties()), AddonBlocks::createBlockItem);
	public static final BlockRegistryObject<AdvancedCompressorBlock, BlockItem> ADVANCED_COMPRESSOR = BLOCKS.register("advanced_compressor", () -> new AdvancedCompressorBlock(createDefaultBlockProperties()), AddonBlocks::createBlockItem);
	public static final BlockRegistryObject<GravityNormalizerBlock, BlockItem> GRAVITY_NORMALIZER = BLOCKS.register("gravity_normalizer", () -> new GravityNormalizerBlock(createDefaultBlockProperties()), AddonBlocks::createBlockItem);

	public static Block.Properties createDefaultBlockProperties()
	{
		return Block.Properties.of(Material.METAL).strength(3.0F).harvestTool(ToolType.PICKAXE);
	}

	public static BlockItem createBlockItem(Block block)
	{
		return new BlockItem(block, AddonItems.getMainItemProperties());
	}

	private AddonBlocks()
	{

	}

}
