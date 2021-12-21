package beyond_earth_giselle_addon.common.resources;

import beyond_earth_giselle_addon.common.item.crafting.IS2ISRecipeCache;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class ReloadListener implements ResourceManagerReloadListener
{
	public static final ReloadListener INSTANCE = new ReloadListener();

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager)
	{
		IS2ISRecipeCache.clearCaches();
	}

}
