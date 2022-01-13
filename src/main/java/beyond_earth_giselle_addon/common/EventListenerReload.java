package beyond_earth_giselle_addon.common;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListenerReload
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onAddReloadListener(AddReloadListenerEvent event)
	{
		RecipeManager recipeManager = event.getDataPackRegistries().getRecipeManager();

		event.addListener(new ResourceManagerReloadListener()
		{
			@Override
			public void onResourceManagerReload(ResourceManager resourceManager)
			{
				BeyondEarthAddon.resetRecipeCaches(recipeManager);
			}

		});

	}

}
