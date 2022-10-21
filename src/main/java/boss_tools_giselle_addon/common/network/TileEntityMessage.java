package boss_tools_giselle_addon.common.network;

import javax.annotation.Nullable;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityMessage<T extends TileEntity> extends BlockPosMessage
{
	public TileEntityMessage()
	{
		super();
	}

	public TileEntityMessage(T tileEntity)
	{
		super(tileEntity != null ? tileEntity.getBlockPos() : null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onHandle(BlockPos blockPos, @Nullable ServerPlayerEntity sender)
	{
		super.onHandle(blockPos, sender);

		if (sender == null)
		{
			return;
		}

		T tileEntity = null;

		try
		{
			TileEntity original = sender.getLevel().getBlockEntity(blockPos);
			tileEntity = (T) original;
		}
		catch (Exception e)
		{

		}

		if (tileEntity != null)
		{
			this.onHandle(tileEntity, sender);
		}

	}

	public abstract void onHandle(T tileEntity, ServerPlayerEntity sender);

}
