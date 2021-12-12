package boss_tools_giselle_addon.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.matrix.MatrixStack.Entry;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;

public class RenderHelper
{
	public static final ResourceLocation TILE_SURFACE = BossToolsAddon.rl("block/tile_surface");
	private static TextureAtlasSprite atlass = null;

	public static void renderBox(MatrixStack stack, IRenderTypeBuffer buffer, AxisAlignedBB bounds, int lineR, int lineG, int lineB, int surfaceR, int surfaceG, int surfaceB)
	{
		Entry last = stack.last();
		Matrix3f normal = last.normal();
		Matrix4f matrix4f = last.pose();

		float startX = (float) bounds.minX + 0.005F;
		float topY = (float) bounds.minY + 0.005F;
		float startZ = (float) bounds.minZ + 0.005F;

		float endX = (float) bounds.maxX - 0.005F;
		float botY = (float) bounds.maxY - 0.005F;
		float endZ = (float) bounds.maxZ - 0.005F;
		IVertexBuilder builder = buffer.getBuffer(RenderType.lines());

		// Bottom frame
		drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ, endX, botY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, botY, endZ, endX, botY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ, startX, botY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, botY, startZ, endX, botY, endZ, lineR, lineG, lineB);

		// Top frame
		drawShapeOutline(builder, matrix4f, normal, startX, topY, startZ, endX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, topY, endZ, endX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, topY, startZ, startX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, topY, startZ, endX, topY, endZ, lineR, lineG, lineB);

		// Vertical lines
		drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ, startX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, botY, endZ, startX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, botY, startZ, endX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, botY, endZ, endX, topY, endZ, lineR, lineG, lineB);

		drawSurfaces(buffer, matrix4f, normal, startX, startZ, endX, endZ, botY, topY, surfaceR, surfaceG, surfaceB);
	}

	public static void drawShapeOutline(IVertexBuilder builder, Matrix4f matrix, Matrix3f normal, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b)
	{
		float nX = (float) (x2 - x1);
		float nY = (float) (y2 - y1);
		float nZ = (float) (z2 - z1);
		float sqrt = (float) Math.sqrt(nX * nX + nY * nY + nZ * nZ);
		nX = nX / sqrt;
		nY = nY / sqrt;
		nZ = nZ / sqrt;

		int alpha = 0xFF;
		builder.vertex(matrix, x1, y1, z1).color(r, g, b, alpha).normal(normal, nX, nY, nZ).endVertex();
		builder.vertex(matrix, x2, y2, z2).color(r, g, b, alpha).normal(normal, nX, nY, nZ).endVertex();
	}

	public static void drawSurfaces(IRenderTypeBuffer buffer, Matrix4f matrix, Matrix3f normal, float startX, float startZ, float endX, float endZ, float botY, float topY, int r, int g, int b)
	{
		Minecraft minecraft = Minecraft.getInstance();

		GraphicsFanciness graphicsFanciness = minecraft.options.graphicsMode;
		IVertexBuilder builder;

		if (graphicsFanciness == GraphicsFanciness.FABULOUS)
		{
			builder = buffer.getBuffer(RenderType.translucentMovingBlock());
		}
		else
		{
			builder = buffer.getBuffer(RenderType.translucentNoCrumbling());
		}

		if (atlass == null)
		{
			atlass = minecraft.getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(TILE_SURFACE);
		}

		float minU = atlass.getU0();
		float maxU = atlass.getU1();
		float minV = atlass.getV0();
		float maxV = atlass.getV1();
		int alpha = 0xAA;

		// Down
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();

		// Top
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();

		// North
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();

		// South
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();

		// West
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();

		// East
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();

		// Inside

		// Down
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();

		// Top
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();

		// North
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();

		// South
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();

		// West
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();

		// East
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
	}

	private RenderHelper()
	{

	}
}
