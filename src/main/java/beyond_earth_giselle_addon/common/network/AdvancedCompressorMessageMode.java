package beyond_earth_giselle_addon.common.network;

import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity;
import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity.ICompressorMode;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class AdvancedCompressorMessageMode extends BlockEntityMessage<AdvancedCompressorBlockEntity>
{
	private ResourceLocation modeKey;

	public AdvancedCompressorMessageMode()
	{

	}

	public AdvancedCompressorMessageMode(AdvancedCompressorBlockEntity blockEntity, ResourceLocation modeKey)
	{
		super(blockEntity);
		this.setModeKey(modeKey);
	}

	@Override
	public void decode(FriendlyByteBuf buffer)
	{
		super.decode(buffer);
		this.setModeKey(buffer.readResourceLocation());
	}

	@Override
	public void encode(FriendlyByteBuf buffer)
	{
		super.encode(buffer);
		buffer.writeResourceLocation(this.getModeKey());
	}

	@Override
	public void onHandle(AdvancedCompressorBlockEntity blockEntity, ServerPlayer sender)
	{
		ICompressorMode mode = blockEntity.findMode(this.getModeKey());
		blockEntity.setMode(mode);
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
