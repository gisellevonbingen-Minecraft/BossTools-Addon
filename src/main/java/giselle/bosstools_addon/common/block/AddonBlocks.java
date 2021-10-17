package giselle.bosstools_addon.common.block;

import giselle.bosstools_addon.BossToolsAddon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AddonBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Block.class, BossToolsAddon.MODID);
	public static final RegistryObject<OxygenAccepterBlock> OXYGEN_ACCEPTER = BLOCKS.register("oxygen_accepter", () -> new OxygenAccepterBlock(Block.Properties.of(Material.STONE)));

	private AddonBlocks()
	{

	}

}
