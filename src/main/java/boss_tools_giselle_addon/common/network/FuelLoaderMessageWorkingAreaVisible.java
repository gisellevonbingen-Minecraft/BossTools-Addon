package boss_tools_giselle_addon.common.network;

import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class FuelLoaderMessageWorkingAreaVisible extends TileEntityMessage<FuelLoaderTileEntity>
{
	private boolean visible;

	public FuelLoaderMessageWorkingAreaVisible()
	{
		super();
		this.setVisible(false);
	}

	public FuelLoaderMessageWorkingAreaVisible(FuelLoaderTileEntity tileEntity, boolean visible)
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
	public void onHandle(FuelLoaderTileEntity tileEntity, ServerPlayerEntity sender)
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
