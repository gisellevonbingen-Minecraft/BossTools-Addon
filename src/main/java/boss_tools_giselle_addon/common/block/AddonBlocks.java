package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.BossToolsAddon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AddonBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Block.class, BossToolsAddon.MODID);
	public static final RegistryObject<OxygenAccepterBlock> OXYGEN_ACCEPTER = BLOCKS.register("oxygen_accepter", () -> new OxygenAccepterBlock(Block.Properties.of(Material.METAL)));
	public static final RegistryObject<FuelLoaderBlock> FUEL_LOADER = BLOCKS.register("fuel_loader", () -> new FuelLoaderBlock(Block.Properties.of(Material.METAL)));
	public static final RegistryObject<ElectricBlastFurnaceBlock> ELECTRIC_BLAST_FURNACE = BLOCKS.register("electric_blast_furnace", () -> new ElectricBlastFurnaceBlock(Block.Properties.of(Material.METAL)));

	private AddonBlocks()
	{

	}

}
