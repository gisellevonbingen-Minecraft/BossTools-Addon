package boss_tools_giselle_addon.common;

import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListenerReload
{
	@SuppressWarnings("deprecation")
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onAddReloadListener(AddReloadListenerEvent event)
	{
		RecipeManager recipeManager = event.getDataPackRegistries().getRecipeManager();

		event.addListener(new net.minecraft.resources.IResourceManagerReloadListener()
		{
			@Override
			public void onResourceManagerReload(IResourceManager resourceManager)
			{
				BossToolsAddon.resetRecipeCaches(recipeManager);
			}

		});

	}

}
