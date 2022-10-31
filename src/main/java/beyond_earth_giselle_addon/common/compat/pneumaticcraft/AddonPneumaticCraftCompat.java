package beyond_earth_giselle_addon.common.compat.pneumaticcraft;

import beyond_earth_giselle_addon.client.compat.pneumaticcraft.AddonPneumaticCraftCompatClient;
import beyond_earth_giselle_addon.common.compat.AddonCompatibleMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonPneumaticCraftCompat extends AddonCompatibleMod
{
	public static final String MODID = "pneumaticcraft";

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

	@Override
	public String getModID()
	{
		return MODID;
	}

	@Override
	protected void onLoad()
	{
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		AddonPneumaticCraftUpgrades.UPGRADES.register(fml_bus);
		AddonCommonUpgradeHandlers.register();
		fml_bus.addListener(this::commonSetup);

		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(AddonPneumaticCraftEventHandler.class);

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> AddonPneumaticCraftCompatClient::new);
	}

	private void commonSetup(FMLCommonSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			AddonPneumaticCraftUpgrades.setupDB();
		});

	}

}
