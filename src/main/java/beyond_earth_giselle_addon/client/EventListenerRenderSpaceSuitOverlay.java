package beyond_earth_giselle_addon.client;

import beyond_earth_giselle_addon.client.event.RenderSpaceSuitOverlayEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.ModInnet;

public class EventListenerRenderSpaceSuitOverlay
{
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.PostLayer parent)
	{
		if (parent.isCanceled() == false && parent.getOverlay() == ForgeIngameGui.HELMET_ELEMENT)
		{
			Minecraft minecraft = Minecraft.getInstance();
			Player player = minecraft.player;
			ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
			Item chestItem = chest.getItem();

			if (chestItem == ModInnet.SPACE_SUIT.get() || chestItem == ModInnet.NETHERITE_SPACE_SUIT.get())
			{
				RenderSpaceSuitOverlayEvent event = new RenderSpaceSuitOverlayEvent(parent);
				MinecraftForge.EVENT_BUS.post(event);

				Font font = minecraft.font;
				int lineOffset = 0;
				int width = 62;
				int x = 5;
				int y = 60 + font.lineHeight + lineOffset;

				for (Component component : event.getComponents())
				{
					int textWidth = font.width(component);
					font.drawShadow(parent.getMatrixStack(), component, Math.max((x + (width - textWidth) / 2), 1), y, 0xFFFFFF);
					y += font.lineHeight + lineOffset;
				}

			}

		}

	}

}
