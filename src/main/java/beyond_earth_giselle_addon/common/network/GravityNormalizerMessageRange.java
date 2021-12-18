package beyond_earth_giselle_addon.common.network;

import beyond_earth_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class GravityNormalizerMessageRange extends BlockEntityMessage<GravityNormalizerBlockEntity>
{
	private int range;

	public GravityNormalizerMessageRange()
	{
		super();
		this.setRange(0);
	}

	public GravityNormalizerMessageRange(GravityNormalizerBlockEntity blockEntity, int range)
	{
		super(blockEntity);
		this.setRange(range);
	}

	@Override
	public void decode(FriendlyByteBuf buffer)
	{
		super.decode(buffer);
		this.setRange(buffer.readInt());
	}

	@Override
	public void encode(FriendlyByteBuf buffer)
	{
		super.encode(buffer);
		buffer.writeInt(this.getRange());
	}

	@Override
	public void onHandle(GravityNormalizerBlockEntity blockEntity, ServerPlayer sender)
	{
		blockEntity.setRange(this.getRange());
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
