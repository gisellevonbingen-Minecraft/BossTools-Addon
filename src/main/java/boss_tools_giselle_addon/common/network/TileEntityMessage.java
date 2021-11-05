package boss_tools_giselle_addon.common.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public abstract class TileEntityMessage<T extends TileEntity> extends AbstractMessage
{
	private BlockPos blockPos;

	public TileEntityMessage()
	{
		this.blockPos = BlockPos.ZERO;
	}

	public TileEntityMessage(T tileEntity)
	{
		this.setBlockPos(tileEntity.getBlockPos());
	}

	@Override
	public void decode(PacketBuffer buffer)
	{
		this.setBlockPos(buffer.readBlockPos());
	}

	@Override
	public void encode(PacketBuffer buffer)
	{
		buffer.writeBlockPos(this.getBlockPos());
	}

	@Override
	public void onHandle(Context context)
	{
		ServerPlayerEntity sender = context.getSender();
		@SuppressWarnings("unchecked")
		T tileEntity = (T) sender.getLevel().getBlockEntity(this.getBlockPos());
		this.onHandle(tileEntity, sender);
	}

	public abstract void onHandle(T tileEntity, ServerPlayerEntity sender);

	public void setBlockPos(BlockPos blockPos)
	{
		this.blockPos = blockPos;
	}

	public BlockPos getBlockPos()
	{
		return this.blockPos;
	}

}
