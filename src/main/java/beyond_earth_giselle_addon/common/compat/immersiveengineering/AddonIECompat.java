package beyond_earth_giselle_addon.common.compat.immersiveengineering;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.compat.AddonCompatibleMod;
import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.ArcRecyclingChecker;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
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
		ArcRecyclingChecker.allowItemTagForRecycling(ItemTags.bind(BeyondEarthAddon.prl("compresseds").toString()));
	}

}
