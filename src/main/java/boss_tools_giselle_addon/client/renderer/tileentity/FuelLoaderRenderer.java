package boss_tools_giselle_addon.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.client.util.RenderHelper;
import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class FuelLoaderRenderer extends TileEntityRenderer<FuelLoaderTileEntity>
{
	public FuelLoaderRenderer(TileEntityRendererDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public boolean shouldRenderOffScreen(FuelLoaderTileEntity p_188185_1_)
	{
		return true;
	}

	@Override
	public void render(FuelLoaderTileEntity tileEntity, float partialTick, MatrixStack stack, IRenderTypeBuffer renderer, int light, int overlayLight)
	{
		if (tileEntity.isWorkingAreaVisible() == true)
		{
			int rl = 78;
			int gl = 197;
			int bl = 231;

			int r = 41;
			int g = 148;
			int b = 204;
			int range = tileEntity.getWorkingRange();

			AxisAlignedBB workingArea = tileEntity.getWorkingArea(BlockPos.ZERO, range);
			RenderHelper.renderBox(stack, renderer, workingArea, rl, gl, bl, r, g, b);
		}

	}

}
