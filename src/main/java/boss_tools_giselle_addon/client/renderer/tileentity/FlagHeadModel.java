package boss_tools_giselle_addon.client.renderer.tileentity;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class FlagHeadModel extends Model
{
	protected final ModelRenderer head;

	public FlagHeadModel()
	{
		this(0, 35, 64, 64);
	}

	public FlagHeadModel(int xTexOffs, int yTexOffs, int texWidth, int texHeight)
	{
		super(RenderType::entityTranslucent);
		this.texWidth = texWidth;
		this.texHeight = texHeight;

		this.head = new ModelRenderer(this, xTexOffs, yTexOffs);
		this.head.texOffs(8, 8).addBox(-3.0f, -11.0f, 3.98f, 8.0f, 8.0f, 0.02f, 0.0f, false);
		this.head.texOffs(0, 8).addBox(-3.0f, -11.0f, 4.002f, 8.0f, 8.0f, 0.01f, 0.0f, false);
		this.head.texOffs(40, 8).addBox(-3.0F, -11.0F, 3.970F, 8.0F, 8.0F, 0.020F);
		this.head.texOffs(32, 8).addBox(-3.0F, -11.0F, 4.020F, 8.0F, 8.0F, 0.020F);
	}

	public void setupAnim(float p_225603_1_, float yRot, float xRot)
	{
		this.head.yRot = yRot * ((float) Math.PI / 180F);
		this.head.xRot = xRot * ((float) Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(@Nonnull MatrixStack matrix, @Nonnull IVertexBuilder builder, int light, int overlayLight, float red, float green, float blue, float alpha)
	{
		this.head.render(matrix, builder, light, overlayLight, red, green, blue, alpha);
	}

}
