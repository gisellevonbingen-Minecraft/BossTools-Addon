package beyond_earth_giselle_addon.client.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.common.capability.OxygenChargerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.gauge.IGaugeValue;
import net.mrscauthd.beyond_earth.common.util.Methods;

public class OxygenCanOverlay implements IGuiOverlay
{
	@Override
	public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight)
	{
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;

		if (Methods.isLivingInAnySpaceSuits(player) == true)
		{
			IGaugeValue value = OxygenChargerUtils.getInventoryOxygenChargerStorage(player);

			if (value.getCapacity() > 0)
			{
				Font font = minecraft.font;
				Component text = GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getPercentText(value));
				int x = 5 + (62 - font.width(text)) / 2;
				font.drawShadow(poseStack, text, Math.max(x, 0), 68, 0xFFFFFF);
			}

		}

	}

}
