package boss_tools_giselle_addon.common.compat.immersiveengineering;

import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.ArcRecyclingChecker;
import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.compat.AddonCompatibleMod;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonIECompat extends AddonCompatibleMod
{
	public static final String MODID = "immersiveengineering";

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
		AddonIEItems.ITEMS.register(fml_bus);

		IEApi.prefixToIngotMap.put("compresseds", new Integer[]{1, 1});
		ArcRecyclingChecker.allowItemTagForRecycling(ItemTags.bind(BossToolsAddon.prl("compresseds").toString()));
	}

}