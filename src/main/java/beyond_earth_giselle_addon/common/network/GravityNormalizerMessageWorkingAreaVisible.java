package beyond_earth_giselle_addon.common.network;

import beyond_earth_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class GravityNormalizerMessageWorkingAreaVisible extends BlockEntityMessage<GravityNormalizerBlockEntity>
{
	private boolean visible;

	public GravityNormalizerMessageWorkingAreaVisible()
	{
		super();
		this.setVisible(false);
	}

	public GravityNormalizerMessageWorkingAreaVisible(GravityNormalizerBlockEntity blockEntity, boolean visible)
	{
		super(blockEntity);
		this.setVisible(visible);
	}

	@Override
	public void decode(FriendlyByteBuf buffer)
	{
		super.decode(buffer);
		this.setVisible(buffer.readBoolean());
	}

	@Override
	public void encode(FriendlyByteBuf buffer)
	{
		super.encode(buffer);
		buffer.writeBoolean(this.isVisible());
	}

	@Override
	public void onHandle(GravityNormalizerBlockEntity blockEntity, ServerPlayer sender)
	{
		blockEntity.setWorkingAreaVisible(this.isVisible());
	}

	public boolean isVisible()
	{
		return this.visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

}
