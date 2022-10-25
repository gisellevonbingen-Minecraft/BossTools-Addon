package beyond_earth_giselle_addon.client.util;

import javax.annotation.Nullable;

import beyond_earth_giselle_addon.client.gui.FlagEditScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.mrscauthd.beyond_earth.common.blocks.entities.FlagBlockEntity;

public class FlagEditClientUtils
{
	@Nullable
	public static FlagBlockEntity getFlagBlockEntity(BlockPos blockPos)
	{
		Minecraft minecraft = Minecraft.getInstance();
		BlockEntity blockEntity = minecraft.level.getBlockEntity(blockPos);
		return blockEntity instanceof FlagBlockEntity flag ? flag : null;
	}

	public static void showScreen(FlagBlockEntity blockEntity)
	{
		if (blockEntity == null)
		{
			return;
		}

		Minecraft minecraft = Minecraft.getInstance();
		minecraft.setScreen(new FlagEditScreen(blockEntity));
	}

}
