package boss_tools_giselle_addon.common.network;

import boss_tools_giselle_addon.common.tile.GravityNormalizerTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class GravityNormalizerMessageWorkingAreaVisible extends TileEntityMessage<GravityNormalizerTileEntity>
{
	private boolean visible;

	public GravityNormalizerMessageWorkingAreaVisible()
	{
		super();
		this.setVisible(false);
	}

	public GravityNormalizerMessageWorkingAreaVisible(GravityNormalizerTileEntity tileEntity, boolean visible)
	{
		super(tileEntity);
		this.setVisible(visible);
	}

	@Override
	public void decode(PacketBuffer buffer)
	{
		super.decode(buffer);
		this.setVisible(buffer.readBoolean());
	}

	@Override
	public void encode(PacketBuffer buffer)
	{
		super.encode(buffer);
		buffer.writeBoolean(this.isVisible());
	}

	@Override
	public void onHandle(GravityNormalizerTileEntity tileEntity, ServerPlayerEntity sender)
	{
		tileEntity.setWorkingAreaVisible(this.isVisible());
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
