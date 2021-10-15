package giselle.bosstools_addon.common.world;

import giselle.bosstools_addon.BossToolsAddon;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BossToolsWorlds
{
	public static final RegistryKey<World> OVERWORLD_ORBIT = register(BossToolsAddon.prl("overworld_orbit"));
	public static final RegistryKey<World> MOON = register(BossToolsAddon.prl("moon"));
	public static final RegistryKey<World> MOON_ORBIT = register(BossToolsAddon.prl("moon_orbit"));
	public static final RegistryKey<World> MARS = register(BossToolsAddon.prl("mars"));
	public static final RegistryKey<World> MARS_ORBIT = register(BossToolsAddon.prl("mars_orbit"));
	public static final RegistryKey<World> MERCURY = register(BossToolsAddon.prl("mercury"));
	public static final RegistryKey<World> MERCURY_ORBIT = register(BossToolsAddon.prl("mercury_orbit"));
	public static final RegistryKey<World> VENUS = register(BossToolsAddon.prl("venus"));
	public static final RegistryKey<World> VENUS_ORBIT = register(BossToolsAddon.prl("venus_orbit"));

	public static RegistryKey<World> register(ResourceLocation location)
	{
		return RegistryKey.create(Registry.DIMENSION_REGISTRY, location);
	}

	private BossToolsWorlds()
	{

	}

}
