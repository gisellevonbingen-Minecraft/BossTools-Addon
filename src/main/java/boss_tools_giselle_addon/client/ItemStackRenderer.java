package boss_tools_giselle_addon.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class ItemStackRenderer
{
	public static void render(ItemStack itemStack, MatrixStack matrix, int x, int y)
	{
		RenderSystem.pushMatrix();
		RenderSystem.multMatrix(matrix.last().pose());
		RenderSystem.enableDepthTest();
		RenderHelper.turnBackOn();

		Minecraft minecraft = Minecraft.getInstance();
		ItemRenderer itemRenderer = minecraft.getItemRenderer();
		itemRenderer.renderAndDecorateItem(null, itemStack, x, y);
		itemRenderer.renderGuiItemDecorations(minecraft.font, itemStack, x, y, null);

		RenderSystem.disableBlend();
		RenderHelper.turnOff();
		RenderSystem.popMatrix();
	}

	private ItemStackRenderer()
	{

	}

}
