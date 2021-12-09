package boss_tools_giselle_addon.common.network;

import javax.annotation.Nullable;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class BlockPosMessage extends AbstractMessage
{
	private BlockPos blockPos;

	public BlockPosMessage()
	{
		this.setBlockPos(null);
	}

	public BlockPosMessage(BlockPos pos)
	{
		this.setBlockPos(pos);
	}

	@Override
	public void decode(PacketBuffer buffer)
	{
		if (buffer.readBoolean() == true)
		{
			this.setBlockPos(buffer.readBlockPos());
		}

	}

	@Override
	public void encode(PacketBuffer buffer)
	{
		BlockPos blockPos = this.getBlockPos();
		boolean notNull = blockPos != null;
		buffer.writeBoolean(notNull);

		if (notNull == true)
		{
			buffer.writeBlockPos(blockPos);
		}

	}

	@Override
	public void onHandle(Context context)
	{
		BlockPos blockPos = this.getBlockPos();

		if (blockPos != null)
		{
			this.onHandle(blockPos, context.getSender());
		}

	}

	public void onHandle(BlockPos blockPos, @Nullable ServerPlayerEntity sender)
	{

	}

	public BlockPos getBlockPos()
	{
		return this.blockPos;
	}

	public void setBlockPos(BlockPos pos)
	{
		this.blockPos = pos;
	}

}
