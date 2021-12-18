package astrocraft_giselle_addon.client.event;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.Event;

public class RenderSpaceSuitOverlayEvent extends Event
{
	private final RenderGameOverlayEvent.PostLayer parent;
	private final List<Component> components;

	public RenderSpaceSuitOverlayEvent(RenderGameOverlayEvent.PostLayer parent)
	{
		this.parent = parent;
		this.components = new ArrayList<>();
	}

	@Override
	public boolean isCancelable()
	{
		return false;
	}

	public RenderGameOverlayEvent.PostLayer getParent()
	{
		return this.parent;
	}

	public PoseStack getMatrixStack()
	{
		return this.getParent().getMatrixStack();
	}

	public float getPartialTicks()
	{
		return this.getParent().getPartialTicks();
	}

	public Window getWindow()
	{
		return this.getParent().getWindow();
	}

	public List<Component> getComponents()
	{
		return this.components;
	}

}
