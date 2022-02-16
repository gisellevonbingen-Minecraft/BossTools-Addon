package boss_tools_giselle_addon.common.registries;

import java.util.function.Supplier;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeDeferredRegister extends DeferredRegisterWrapper<TileEntityType<?>>
{
	public TileEntityTypeDeferredRegister(String modid)
	{
		super(modid, ForgeRegistries.TILE_ENTITIES);
	}

	public <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, BlockRegistryObject<?, ?> block, Supplier<T> tileEntitySupplier)
	{
		return this.register(name, () -> TileEntityType.Builder.of(tileEntitySupplier, block.get()).build(null));
	}

}
