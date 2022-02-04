package boss_tools_giselle_addon.common.compat.mekanism;

import boss_tools_giselle_addon.client.compat.mekanism.AddonMekanismCompatClient;
import boss_tools_giselle_addon.common.compat.AddonCompatibleMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonMekanismCompat extends AddonCompatibleMod
{
	public static final String MODID = "mekanism";

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
		AddonMekanismModules.MODULES.register(fml_bus);
		AddonMekanismItems.ITEMS.register(fml_bus);
		fml_bus.register(AddonMekanismFMLEventListener.class);

		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(AddonMekanismCommonEventListener.class);

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> AddonMekanismCompatClient::new);
	}

}
