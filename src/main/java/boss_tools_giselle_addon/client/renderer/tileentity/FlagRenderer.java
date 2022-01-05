package boss_tools_giselle_addon.client.renderer.tileentity;

import java.util.Map;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.entity.renderer.flagtileentity.TileEntityHeadRenderer;
import net.mrscauthd.boss_tools.flag.FlagBlock;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;

public class FlagRenderer extends TileEntityHeadRenderer
{
	protected final FlagHeadModel model;

	public FlagRenderer(TileEntityRendererDispatcher dispatcher)
	{
		super(dispatcher);

		this.model = new FlagHeadModel();
	}

	@Override
	public void render(FlagTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
	{
		super.render(tileEntity, partialTicks, matrixStack, buffer, combinedLight, combinedOverlay);

		if (tileEntity.getBlockState().getValue(FlagBlock.HALF) == DoubleBlockHalf.UPPER)
		{
			BlockState blockstate = tileEntity.getBlockState();
			boolean flag = blockstate.getBlock() instanceof FlagBlock;
			Direction direction = flag ? (blockstate.getValue(FlagBlock.FACING)) : null;
			this.render(direction, direction.toYRot(), tileEntity.getPlayerProfile(), 0.0f, matrixStack, buffer, combinedLight);
		}

	}

	public void render(@Nullable Direction directionIn, float p_228879_1_, @Nullable GameProfile gameProfileIn, float animationProgress, MatrixStack matrixStackIn, IRenderTypeBuffer buffer, int combinedLight)
	{
		matrixStackIn.pushPose();

		if (directionIn == null)
		{
			matrixStackIn.translate(0.5, 0.0, 0.5);
		}
		else
		{
			matrixStackIn.translate((double) (0.5f - directionIn.getStepX() * 0.25f), 0.25, (double) (0.5f - directionIn.getStepZ() * 0.25f));
		}
		
		matrixStackIn.scale(-1.0f, -1.0f, 1.0f);
		
		IVertexBuilder ivertexbuilder = buffer.getBuffer(getRenderType(gameProfileIn));
		this.model.setupAnim(animationProgress, p_228879_1_, 0.0f);
		this.model.renderToBuffer(matrixStackIn, ivertexbuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
		matrixStackIn.popPose();
	}

	public RenderType getRenderType(@Nullable GameProfile gameProfileIn)
	{
		Minecraft minecraft = Minecraft.getInstance();
		Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = (Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>) minecraft.getSkinManager().getInsecureSkinInformation(gameProfileIn);
		
		if (map.containsKey(MinecraftProfileTexture.Type.SKIN) == true)
		{
			MinecraftProfileTexture profileTexture = map.get(MinecraftProfileTexture.Type.SKIN);
			ResourceLocation textureRL = minecraft.getSkinManager().registerTexture(profileTexture, MinecraftProfileTexture.Type.SKIN);
			return RenderType.entityTranslucent(textureRL);
		}
		else
		{
			return RenderType.entityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(PlayerEntity.createPlayerUUID(gameProfileIn)));
		}

	}

}
