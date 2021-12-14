package boss_tools_giselle_addon.common.compat.theoneprobe;

import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeConfigProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class AddonProbeConfigProvider implements IProbeConfigProvider
{
	public static final AddonProbeConfigProvider INSTANCE = new AddonProbeConfigProvider();

	@Override
	public void getProbeConfig(IProbeConfig arg0, PlayerEntity arg1, World arg2, Entity arg3, IProbeHitEntityData arg4)
	{

	}

	@Override
	public void getProbeConfig(IProbeConfig arg0, PlayerEntity arg1, World arg2, BlockState arg3, IProbeHitData arg4)
	{

	}

}
