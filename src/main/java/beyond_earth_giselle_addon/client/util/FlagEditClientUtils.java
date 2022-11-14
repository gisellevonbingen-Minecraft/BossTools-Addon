package beyond_earth_giselle_addon.client.util;

import javax.annotation.Nullable;

import beyond_earth_giselle_addon.client.gui.FlagEditScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.mrscauthd.beyond_earth.flag.FlagTileEntity;

public class FlagEditClientUtils
{
	@Nullable
	public static FlagTileEntity getFlagTileEntity(BlockPos blockPos)
	{
		Minecraft minecraft = Minecraft.getInstance();
		BlockEntity blockEntity = minecraft.level.getBlockEntity(blockPos);
		return blockEntity instanceof FlagTileEntity flagBlockEntity ? flagBlockEntity : null;

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
