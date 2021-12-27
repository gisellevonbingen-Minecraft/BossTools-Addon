package boss_tools_giselle_addon.common.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import boss_tools_giselle_addon.common.item.crafting.IS2ISRecipeCache;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Unit;

public class ReloadListener implements IFutureReloadListener
{
	public static final ReloadListener INSTANCE = new ReloadListener();

	@Override
	public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler p_215226_3_, IProfiler profiler, Executor p_215226_5_, Executor executor)
	{
		return stage.wait(Unit.INSTANCE).thenRunAsync(() ->
		{
			profiler.startTick();
			profiler.push("listener");
			this.onResourceManagerReload(resourceManager);
			profiler.pop();
			profiler.endTick();
		}, executor);

	}

	public void onResourceManagerReload(IResourceManager resourceManager)
	{
		IS2ISRecipeCache.clearCaches();
	}

}
