package boss_tools_giselle_addon.common.network;

import boss_tools_giselle_addon.common.tile.GravityNormalizerTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class GravityNormalizerMessageRange extends TileEntityMessage<GravityNormalizerTileEntity>
{
	private int range;

	public GravityNormalizerMessageRange()
	{
		super();
		this.setRange(0);
	}

	public GravityNormalizerMessageRange(GravityNormalizerTileEntity tileEntity, int range)
	{
		super(tileEntity);
		this.setRange(range);
	}

	@Override
	public void decode(PacketBuffer buffer)
	{
		super.decode(buffer);
		this.setRange(buffer.readInt());
	}

	@Override
	public void encode(PacketBuffer buffer)
	{
		super.encode(buffer);
		buffer.writeInt(this.getRange());
	}

	@Override
	public void onHandle(GravityNormalizerTileEntity tileEntity, ServerPlayerEntity sender)
	{
		tileEntity.setRange(this.getRange());
	}

	public int getRange()
	{
		return this.range;
	}

	public void setRange(int range)
	{
		this.range = range;
	}

}
