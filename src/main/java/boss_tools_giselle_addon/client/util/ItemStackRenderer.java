package boss_tools_giselle_addon.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

public class ItemStackRenderer
{
	public static void render(ItemStack itemStack, int x, int y)
	{
		Minecraft minecraft = Minecraft.getInstance();
		ItemRenderer itemRenderer = minecraft.getItemRenderer();
		itemRenderer.renderAndDecorateItem(null, itemStack, x, y);
		itemRenderer.renderGuiItemDecorations(minecraft.font, itemStack, x, y, null);
	}

	private ItemStackRenderer()
	{

	}

}
