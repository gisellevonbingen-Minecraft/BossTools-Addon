package beyond_earth_giselle_addon.common.network;

import javax.annotation.Nullable;

import beyond_earth_giselle_addon.client.util.FlagEditClientUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.mrscauthd.beyond_earth.common.blocks.entities.FlagBlockEntity;

public class FlagEditMessageOpen extends BlockPosMessage
{
	public FlagEditMessageOpen()
	{
		super();
	}

	public FlagEditMessageOpen(BlockPos pos)
	{
		super(pos);
	}

	@Override
	public void onHandle(BlockPos blockPos, @Nullable ServerPlayer sender)
	{
		super.onHandle(blockPos, sender);

		FlagBlockEntity blockEntity = FlagEditClientUtils.getFlagBlockEntity(blockPos);
		FlagEditClientUtils.showScreen(blockEntity);
	}

}
