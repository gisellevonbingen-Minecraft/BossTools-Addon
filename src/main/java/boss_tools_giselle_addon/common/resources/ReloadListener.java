package boss_tools_giselle_addon.common.resources;

import java.util.function.Predicate;

import boss_tools_giselle_addon.common.item.crafting.IS2ISRecipeCache;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

public class ReloadListener implements ISelectiveResourceReloadListener
{
	public static final ReloadListener INSTANCE = new ReloadListener();

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate)
	{
		IS2ISRecipeCache.clearCaches();
	}

}
