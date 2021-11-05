package boss_tools_giselle_addon.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import boss_tools_giselle_addon.common.tile.GravityNormalizerTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class GravityNormalizerRenderer extends TileEntityRenderer<GravityNormalizerTileEntity>
{
	public GravityNormalizerRenderer(TileEntityRendererDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public void render(GravityNormalizerTileEntity tileEntity, float partialTick, MatrixStack matrix, IRenderTypeBuffer renderer, int light, int overlayLight)
	{
		if (tileEntity.isWorkingAreaVisible() == true)
		{
			int range = tileEntity.getRange();
			AxisAlignedBB workingArea = tileEntity.getWorkingArea(BlockPos.ZERO, range);
			IVertexBuilder builder = renderer.getBuffer(RenderType.lines());
			WorldRenderer.renderLineBox(matrix, builder, workingArea, 1.0F, 1.0F, 1.0F, 1.0F);
			
//			IVertexBuilder builder2 = renderer.getBuffer(RenderType.solid());
//			VoxelShape shape = VoxelShapes.create(workingArea);
//			WorldRenderer.renderVoxelShape(matrix, builder2, shape, 0.0D, 0.0D, 0.0D, 1.0F, 1.0F, 1.0F, 1.0F);
		}

	}

}
