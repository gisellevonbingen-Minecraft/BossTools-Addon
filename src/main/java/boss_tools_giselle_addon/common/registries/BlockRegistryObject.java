package boss_tools_giselle_addon.common.registries;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.RegistryObject;

public class BlockRegistryObject<B extends Block, I extends BlockItem> extends DoubleRegistryObject<B, I> implements Supplier<B>, IItemProvider
{
	public BlockRegistryObject(RegistryObject<? extends B> b, RegistryObject<? extends I> i)
	{
		super(b, i);
	}

	public B asBlock()
	{
		return this.getPrimary();
	}

	@Override
	public B get()
	{
		return this.asBlock();
	}

	@Override
	public I asItem()
	{
		return this.getSecondary();
	}

}
