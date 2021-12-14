package boss_tools_giselle_addon.common.compat.theoneprobe;

import java.util.function.Function;

import mcjty.theoneprobe.api.ITheOneProbe;

public class AddonTOPPlugin implements Function<ITheOneProbe, Void>
{
	@Override
	public Void apply(ITheOneProbe top)
	{
		top.registerProvider(AddonProbeInfoBlockProvider.INSTANCE);
		top.registerEntityProvider(AddonProbeInfoEntityProvider.INSTANCE);
		top.registerProbeConfigProvider(AddonProbeConfigProvider.INSTANCE);

		return null;
	}

}
