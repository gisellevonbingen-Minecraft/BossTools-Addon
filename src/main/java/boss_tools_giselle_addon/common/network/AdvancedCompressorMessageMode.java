package boss_tools_giselle_addon.common.network;

import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity;
import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity.ICompressorMode;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class AdvancedCompressorMessageMode extends TileEntityMessage<AdvancedCompressorTileEntity>
{
	private ResourceLocation modeKey;
	
	public AdvancedCompressorMessageMode()
	{

	}

	public AdvancedCompressorMessageMode(AdvancedCompressorTileEntity tileEntity, ResourceLocation modeKey)
	{
		super(tileEntity);
		this.setModeKey(modeKey);
	}

	@Override
	public void decode(PacketBuffer buffer)
	{
		super.decode(buffer);
		this.setModeKey(buffer.readResourceLocation());
	}

	@Override
	public void encode(PacketBuffer buffer)
	{
		super.encode(buffer);
		buffer.writeResourceLocation(this.getModeKey());
	}

	@Override
	public void onHandle(AdvancedCompressorTileEntity tileEntity, ServerPlayerEntity sender)
	{
		ICompressorMode mode = tileEntity.findMode(this.getModeKey());
		tileEntity.setMode(mode);
	}

	public ResourceLocation getModeKey()
	{
		return this.modeKey;
	}

	public void setModeKey(ResourceLocation modeKey)
	{
		this.modeKey = modeKey;
	}

}
