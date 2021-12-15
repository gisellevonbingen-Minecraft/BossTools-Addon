package boss_tools_giselle_addon.client.util;

import java.util.Map;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;

public class SkullRenderUtils
{
	public static void renderSkinModel(PoseStack stack, GameProfile gameProfile, float xRot, float yRot)
	{
		Minecraft minecraft = Minecraft.getInstance();
		BufferSource bufferSource = minecraft.renderBuffers().crumblingBufferSource();
		ResourceLocation texture = registerSkinTexture(stack, bufferSource, gameProfile);

		stack.pushPose();
		stack.translate(0.25D, 0.5D, 0.0D);
		stack.scale(1.0F, -1.0F, 1.0F);
		Lighting.setupForFlatItems();
		RenderType renderType = RenderType.entityTranslucent(texture);
		renderSkinModel(stack, bufferSource, renderType, xRot, yRot);
		stack.popPose();
		bufferSource.endBatch(renderType);
	}

	public static ResourceLocation registerSkinTexture(PoseStack stack, BufferSource bufferSource, GameProfile gameProfile)
	{
		Minecraft minecraft = Minecraft.getInstance();
		SkinManager skinManager = minecraft.getSkinManager();
		Map<Type, MinecraftProfileTexture> map = gameProfile != null ? skinManager.getInsecureSkinInformation(gameProfile) : null;
		MinecraftProfileTexture texture = map != null ? map.get(Type.SKIN) : null;
		return texture != null ? skinManager.registerTexture(texture, Type.SKIN) : DefaultPlayerSkin.getDefaultSkin();
	}

	public static void renderSkinModel(PoseStack stack, BufferSource bufferSource, RenderType renderType, float xRot, float yRot)
	{
		VertexConsumer vertexBuilder = bufferSource.getBuffer(renderType);
		SkullModel model = new SkullModel(SkullModel.createHumanoidHeadLayer().bakeRoot());
		model.setupAnim(0.0F, yRot, xRot);
		model.renderToBuffer(stack, vertexBuilder, 0xF0, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}

	private SkullRenderUtils()
	{

	}

}
