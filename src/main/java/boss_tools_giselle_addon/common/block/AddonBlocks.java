package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AddonBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Block.class, BossToolsAddon.MODID);
	public static final RegistryObject<FuelLoaderBlock> FUEL_LOADER = BLOCKS.register("fuel_loader", () -> new FuelLoaderBlock(createDefaultBlockProperties()));
	public static final RegistryObject<ElectricBlastFurnaceBlock> ELECTRIC_BLAST_FURNACE = BLOCKS.register("electric_blast_furnace", () -> new ElectricBlastFurnaceBlock(createDefaultBlockProperties()));
	public static final RegistryObject<AdvancedCompressorBlock> ADVANCED_COMPRESSOR = BLOCKS.register("advanced_compressor", () -> new AdvancedCompressorBlock(createDefaultBlockProperties()));
	public static final RegistryObject<GravityNormalizerBlock> GRAVITY_NORMALIZER = BLOCKS.register("gravity_normalizer", () -> new GravityNormalizerBlock(createDefaultBlockProperties()));

	public static Block.Properties createDefaultBlockProperties()
	{
		return Block.Properties.of(Material.METAL).strength(3.0F).harvestTool(ToolType.PICKAXE);
	}

	private AddonBlocks()
	{

	}

}
