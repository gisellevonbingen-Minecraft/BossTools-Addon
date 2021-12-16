package astrocraft_giselle_addon.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

public class ItemStackRenderer
{
	public static void render(ItemStack itemStack, int x, int y)
	{
		Minecraft minecraft = Minecraft.getInstance();
		ItemRenderer itemRenderer = minecraft.getItemRenderer();
		itemRenderer.renderAndDecorateItem(itemStack, x, y);
		itemRenderer.renderGuiItemDecorations(minecraft.font, itemStack, x, y, null);
	}

	private ItemStackRenderer()
	{

	}

}
