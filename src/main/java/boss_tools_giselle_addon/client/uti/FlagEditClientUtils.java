package boss_tools_giselle_addon.client.uti;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.client.gui.FlagEditScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;

public class FlagEditClientUtils
{
	@Nullable
	public static FlagTileEntity getFlagTileEntity(BlockPos blockPos)
	{
		Minecraft minecraft = Minecraft.getInstance();
		TileEntity blockEntity = minecraft.level.getBlockEntity(blockPos);

		if (blockEntity instanceof FlagTileEntity)
		{
			return (FlagTileEntity) blockEntity;
		}
		else
		{
			return null;
		}

	}

	public static void showScreen(FlagTileEntity tileEntity)
	{
		if (tileEntity == null)
		{
			return;
		}

		Minecraft minecraft = Minecraft.getInstance();
		minecraft.setScreen(new FlagEditScreen(tileEntity));
	}

}
