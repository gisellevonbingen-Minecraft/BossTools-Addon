package astrocraft_giselle_addon.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import astrocraft_giselle_addon.client.util.ItemStackRenderer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;

public class ItemIconButton extends Button
{
	private ItemStack itemStack;

	public ItemIconButton(int p_i232256_1_, int p_i232256_2_, int p_i232256_3_, int p_i232256_4_, ItemStack p_i232256_5_, Button.OnPress p_i232256_6_, Button.OnTooltip p_i232256_7_)
	{
		super(p_i232256_1_, p_i232256_2_, p_i232256_3_, p_i232256_4_, new TextComponent(""), p_i232256_6_, p_i232256_7_);
		this.setItemStack(p_i232256_5_);
	}

	public ItemIconButton(int p_i232255_1_, int p_i232255_2_, int p_i232255_3_, int p_i232255_4_, ItemStack p_i232255_5_, Button.OnPress p_i232255_6_)
	{
		super(p_i232255_1_, p_i232255_2_, p_i232255_3_, p_i232255_4_, new TextComponent(""), p_i232255_6_);
		this.setItemStack(p_i232255_5_);
	}

	@Override
	public void renderButton(PoseStack stack, int mouseX, int mouseY, float p_230431_4_)
	{
		super.renderButton(stack, mouseX, mouseY, p_230431_4_);
		ItemStackRenderer.render(this.getItemStack(), this.x + (this.getWidth() - 16) / 2, this.y + (this.getHeight() - 16) / 2);
	}

	public ItemStack getItemStack()
	{
		return this.itemStack;
	}

	public void setItemStack(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}

}
