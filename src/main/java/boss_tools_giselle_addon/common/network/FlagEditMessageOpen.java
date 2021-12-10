package boss_tools_giselle_addon.common.network;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.client.util.FlagEditClientUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;

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
	public void onHandle(BlockPos blockPos, @Nullable ServerPlayerEntity sender)
	{
		super.onHandle(blockPos, sender);

		FlagTileEntity tileEntity = FlagEditClientUtils.getFlagTileEntity(blockPos);
		FlagEditClientUtils.showScreen(tileEntity);
	}

}
