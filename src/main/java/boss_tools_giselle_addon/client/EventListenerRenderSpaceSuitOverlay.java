package boss_tools_giselle_addon.client;

import boss_tools_giselle_addon.client.event.RenderSpaceSuitOverlayEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.ModInnet;

public class EventListenerRenderSpaceSuitOverlay
{
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent parent)
	{
		if (parent.isCanceled() == false && parent.getType() == ElementType.HELMET)
		{
			Minecraft minecraft = Minecraft.getInstance();
			PlayerEntity player = minecraft.player;
			ItemStack chest = player.getItemBySlot(EquipmentSlotType.CHEST);
			Item chestItem = chest.getItem();

			if (chestItem == ModInnet.SPACE_SUIT.get() || chestItem == ModInnet.NETHERITE_SPACE_SUIT.get())
			{
				RenderSpaceSuitOverlayEvent event = new RenderSpaceSuitOverlayEvent(parent);
				MinecraftForge.EVENT_BUS.post(event);

				FontRenderer font = minecraft.font;
				int lineOffset = 0;
				int width = 62;
				int x = 5;
				int y = 60 + font.lineHeight + lineOffset;

				for (ITextComponent component : event.getComponents())
				{
					int textWidth = font.width(component);
					font.drawShadow(parent.getMatrixStack(), component, Math.max((x + (width - textWidth) / 2), 1), y, 0xFFFFFF);
					y += font.lineHeight + lineOffset;
				}

			}

		}

	}

}
