package boss_tools_giselle_addon.client.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.common.fluid.FluidUtil2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class GuiHelper
{
	public static final ResourceLocation FLUID_TANK_PATH = BossToolsAddon.rl("textures/gui/container/fluid_tank_fore.png");
	public static final int FLUID_TANK_WIDTH = 18;
	public static final int FLUID_TANK_HEIGHT = 60;

	public static void drawFluidTank(MatrixStack matrixStack, int left, int top, IFluidTank tank)
	{
		drawFluidTank(matrixStack, left, top, tank.getFluid(), tank.getCapacity());
	}

	public static void drawFluidTank(MatrixStack matrixStack, int left, int top, FluidStack stack, int capacity)
	{
		if (stack != null && !stack.isEmpty() && capacity > 0)
		{
			int maxHeight = FLUID_TANK_HEIGHT;
			int scaledHeight = (int) Math.ceil(maxHeight * ((double) stack.getAmount() / (double) capacity));
			int offset = maxHeight - scaledHeight;
			drawFluid(matrixStack, left, top + offset, FLUID_TANK_WIDTH, scaledHeight, stack);
		}

		drawFluidTankOverlay(matrixStack, left, top);
	}

	public static void drawFluidTankOverlay(MatrixStack matrixStack, int left, int top)
	{
		drawVertical(matrixStack, left, top, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT, FLUID_TANK_PATH, 1.0D);
	}

	public static void drawFluid(MatrixStack matrixStack, int left, int top, int width, int height, FluidStack stack)
	{
		Fluid fluid = FluidUtil2.getFluid(stack);

		if (fluid == null)
		{
			return;
		}

		TextureAtlasSprite fluidStillSprite = getStillFluidSprite(stack);
		FluidAttributes attributes = fluid.getAttributes();
		setGLColorFromInt(attributes.getColor(stack));
		drawTiledSprite(matrixStack, left, top, width, height, fluidStillSprite, 16, 16);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void drawRocketFluidTank(MatrixStack matrixStack, int left, int top, FluidStack stack, int capacity, int amount)
	{
		if (stack != null && !stack.isEmpty() && capacity > 0)
		{
			int maxHeight = 46;
			int scaledHeight = (int) Math.ceil(maxHeight * ((double) amount / (double) capacity));
			int offset = maxHeight - scaledHeight;
			GuiHelper.drawFluid(matrixStack, left, top + offset, 46, scaledHeight, stack);
		}

	}

	public static void drawFluidHorizontal(MatrixStack matrixStack, int left, int top, int width, int height, FluidStack stack, int capacity)
	{
		Fluid fluid = FluidUtil2.getFluid(stack);

		if (fluid == null)
		{
			return;
		}

		double ratio = (double) stack.getAmount() / (double) capacity;
		drawFluid(matrixStack, left, top, (int) Math.ceil(width * ratio), height, stack);
	}

	public static void drawFluidVertical(MatrixStack matrixStack, int left, int top, int width, int height, FluidStack stack, int capacity)
	{
		Fluid fluid = FluidUtil2.getFluid(stack);

		if (fluid == null)
		{
			return;
		}

		double ratio = (double) stack.getAmount() / (double) capacity;
		int scaledHeight = (int) Math.ceil(height * ratio);
		int offset = height - scaledHeight;
		drawFluid(matrixStack, left, top + offset, scaledHeight, height, stack);
	}

	public static void drawTiledSprite(MatrixStack matrixStack, int left, int top, int width, int height, TextureAtlasSprite sprite, int tileWidth, int tileHeight)
	{
		float uMin = sprite.getU0();
		float uMax = sprite.getU1();
		float vMin = sprite.getV0();
		float vMax = sprite.getV1();
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bind(PlayerContainer.BLOCK_ATLAS);
		drawTiledSprite(matrixStack, left, top, width, height, tileWidth, tileHeight, uMin, uMax, vMin, vMax);
	}

	public static void drawTiledSprite(MatrixStack matrixStack, int left, int top, int width, int height, int tileWidth, int tileHeight, float uMin, float uMax, float vMin, float vMax)
	{
		Matrix4f matrix = matrixStack.last().pose();

		int xTileCount = width / tileWidth;
		int xRemainder = width - (xTileCount * tileWidth);
		int yTileCount = height / tileWidth;
		int yRemainder = height - (yTileCount * tileWidth);

		int yStart = top + height;

		for (int xTile = 0; xTile <= xTileCount; xTile++)
		{
			for (int yTile = 0; yTile <= yTileCount; yTile++)
			{
				int tiledWidth = (xTile == xTileCount) ? xRemainder : tileWidth;
				int tiledHeight = (yTile == yTileCount) ? yRemainder : tileWidth;
				int x = left + (xTile * tileWidth);
				int y = yStart - ((yTile + 1) * tileHeight);

				if (tiledWidth > 0 && tiledHeight > 0)
				{
					int maskRight = tileWidth - tiledWidth;
					int maskTop = tileHeight - tiledHeight;

					drawTextureWithMasking(matrix, x, y, tileWidth, tileHeight, maskTop, maskRight, 0, uMin, uMax, vMin, vMax);
				}
			}
		}
	}

	public static void drawTextureWithMasking(Matrix4f matrix, float left, float top, float tileWidth, float tileHeight, int maskTop, int maskRight, float zLevel, float uMin, float uMax, float vMin, float vMax)
	{
		uMax = uMax - (maskRight / tileWidth * (uMax - uMin));
		vMax = vMax - (maskTop / tileHeight * (vMax - vMin));

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		bufferBuilder.vertex(matrix, left, top + tileHeight, zLevel).uv(uMin, vMax).endVertex();
		bufferBuilder.vertex(matrix, left + tileWidth - maskRight, top + tileHeight, zLevel).uv(uMax, vMax).endVertex();
		bufferBuilder.vertex(matrix, left + tileWidth - maskRight, top + maskTop, zLevel).uv(uMax, vMin).endVertex();
		bufferBuilder.vertex(matrix, left, top + maskTop, zLevel).uv(uMin, vMin).endVertex();
		tessellator.end();
	}

	public static void setGLColorFromInt(int color)
	{
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		float alpha = ((color >> 24) & 0xFF) / 255F;

		RenderSystem.color4f(red, green, blue, alpha);
	}

	public static TextureAtlasSprite getStillFluidSprite(FluidStack stack)
	{
		Fluid fluid = stack.getFluid();
		FluidAttributes attributes = fluid.getAttributes();
		ResourceLocation fluidStill = attributes.getStillTexture(stack);
		return Minecraft.getInstance().getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(fluidStill);
	}

	public static Rectangle2d getFluidTankBounds(int left, int top)
	{
		return new Rectangle2d(left, top, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT);
	}

	public static void drawVertical(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio)
	{
		int ratioHeight = (int) Math.ceil(height * ratio);
		int remainHeight = height - ratioHeight;
		Minecraft.getInstance().getTextureManager().bind(resource);
		AbstractGui.blit(matrixStack, left, top + remainHeight, 0, remainHeight, width, ratioHeight, width, height);
	}

	public static void drawVerticalReverse(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio)
	{
		int ratioHeight = (int) Math.ceil(height * ratio);
		int remainHeight = height - ratioHeight;
		Minecraft.getInstance().getTextureManager().bind(resource);
		AbstractGui.blit(matrixStack, left, top, 0, 0, width, remainHeight, width, height);
	}

	public static void drawHorizontal(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio)
	{
		int ratioWidth = (int) Math.ceil(width * ratio);
		Minecraft.getInstance().getTextureManager().bind(resource);
		AbstractGui.blit(matrixStack, left, top, 0, 0, ratioWidth, height, width, height);
	}

	public static void drawHorizontalReverse(MatrixStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio)
	{
		int ratioWidth = (int) Math.ceil(width * ratio);
		int remainWidth = width - ratioWidth;
		Minecraft.getInstance().getTextureManager().bind(resource);
		AbstractGui.blit(matrixStack, left + ratioWidth, top, ratioWidth, 0, remainWidth, height, width, height);
	}

	private GuiHelper()
	{

	}

}
