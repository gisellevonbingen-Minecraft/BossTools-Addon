package astrocraft_giselle_addon.client.util;

import javax.annotation.Nullable;

import astrocraft_giselle_addon.client.gui.FlagEditScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.mrscauthd.astrocraft.flag.FlagTileEntity;

public class FlagEditClientUtils
{
	@Nullable
	public static FlagTileEntity getFlagTileEntity(BlockPos blockPos)
	{
		Minecraft minecraft = Minecraft.getInstance();
		BlockEntity blockEntity = minecraft.level.getBlockEntity(blockPos);

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
