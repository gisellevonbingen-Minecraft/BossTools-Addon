package boss_tools_giselle_addon.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock.Types;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3i;
import net.mrscauthd.boss_tools.flag.FlagBlock;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;

public class FlagRenderer extends TileEntityRenderer<FlagTileEntity>
{
	public FlagRenderer(TileEntityRendererDispatcher dispatcher)
	{
		super(dispatcher);
	}

	public void render(FlagTileEntity flag, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
	{
		BlockState blockState = flag.getBlockState();

		if (blockState.getValue(FlagBlock.HALF) == DoubleBlockHalf.UPPER)
		{
			float renderScale = 1.0F;
			float renderOffsetX = 0.050F;
			float renderOffsetY = 0.18F;
			float skullLength = 0.01F;

			Direction direction = blockState.getValue(FlagBlock.FACING);
			Vector3i normal = direction.getNormal();
			float angle = direction.get2DDataValue() * 90.0F;
			int x = normal.getX();
			int z = normal.getZ();
			boolean isXDirection = x != 0;
			boolean isZDirection = z != 0;
			float skullModelOffset = 0.25F;
			float skullDirectionalX = isXDirection ? skullModelOffset : 0.00F;
			float skullDirectionalZ = isZDirection ? skullModelOffset : 0.00F;
			float skullScaleX = isXDirection ? skullLength : renderScale;
			float skullScaleY = renderScale;
			float skullScaleZ = isZDirection ? skullLength : renderScale;
			float skullOffsetX = (0.50F - (skullScaleX / 2.0F)) - (renderOffsetX * z);
			float skullOffsetY = (0.75F - (skullScaleY / 2.0F)) + renderOffsetY;
			float skullOffsetZ = (0.50F - (skullScaleZ / 2.0F)) + (renderOffsetX * x);

			stack.pushPose();
			stack.translate(skullOffsetX, skullOffsetY, skullOffsetZ);
			stack.scale(skullScaleX, skullScaleY, skullScaleZ);

			// Front
			stack.pushPose();
			stack.translate(-skullDirectionalX * x, 0, -skullDirectionalZ * z);
			SkullTileEntityRenderer.renderSkull(null, angle - 000.0F, Types.PLAYER, flag.getPlayerProfile(), 0, stack, buffer, combinedLight);
			stack.popPose();

			// Back
			stack.pushPose();
			stack.translate(+skullDirectionalX * x, 0, +skullDirectionalZ * z);
			SkullTileEntityRenderer.renderSkull(null, angle - 180.0F, Types.PLAYER, flag.getPlayerProfile(), 0, stack, buffer, combinedLight);
			stack.popPose();

			stack.popPose();
		}

	}

}
