package beyond_earth_giselle_addon.common.compat.theoneprobe;

import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeConfigProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class AddonProbeConfigProvider implements IProbeConfigProvider
{
	public static final AddonProbeConfigProvider INSTANCE = new AddonProbeConfigProvider();

	@Override
	public void getProbeConfig(IProbeConfig var1, Player var2, Level var3, net.minecraft.world.entity.Entity var4, IProbeHitEntityData var5)
	{

	}

	@Override
	public void getProbeConfig(IProbeConfig var1, Player var2, Level var3, net.minecraft.world.level.block.state.BlockState var4, IProbeHitData var5)
	{

	}

}
