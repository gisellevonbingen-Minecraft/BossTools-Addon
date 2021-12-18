package boss_tools_giselle_addon.client.event;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.MainWindow;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.Event;

public class RenderSpaceSuitOverlayEvent extends Event
{
	private final RenderGameOverlayEvent parent;
	private final List<ITextComponent> components;

	public RenderSpaceSuitOverlayEvent(RenderGameOverlayEvent parent)
	{
		this.parent = parent;
		this.components = new ArrayList<>();
	}

	@Override
	public boolean isCancelable()
	{
		return false;
	}

	public RenderGameOverlayEvent getParent()
	{
		return this.parent;
	}

	public MatrixStack getMatrixStack()
	{
		return this.getParent().getMatrixStack();
	}

	public float getPartialTicks()
	{
		return this.getParent().getPartialTicks();
	}

	public MainWindow getWindow()
	{
		return this.getParent().getWindow();
	}

	public List<ITextComponent> getComponents()
	{
		return this.components;
	}

}
