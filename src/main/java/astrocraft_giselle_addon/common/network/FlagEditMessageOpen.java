package astrocraft_giselle_addon.common.network;

import javax.annotation.Nullable;

import astrocraft_giselle_addon.client.util.FlagEditClientUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.mrscauthd.astrocraft.flag.FlagTileEntity;

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

		FlagTileEntity tileEntity = FlagEditClientUtils.getFlagTileEntity(blockPos);
		FlagEditClientUtils.showScreen(tileEntity);
	}

}
