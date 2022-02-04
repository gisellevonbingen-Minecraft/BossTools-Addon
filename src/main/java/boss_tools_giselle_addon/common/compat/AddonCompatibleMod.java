package boss_tools_giselle_addon.common.compat;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;

public abstract class AddonCompatibleMod
{
	private boolean isLoaded;

	public abstract String getModID();

	protected abstract void onLoad();

	public void tryLoad()
	{
		if (ModList.get().isLoaded(this.getModID()) == true)
		{
			this.isLoaded = true;

			try
			{
				this.onLoad();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

	}

	public boolean isLoaded()
	{
		return this.isLoaded;
	}

	public ResourceLocation getLocation(String path)
	{
		return new ResourceLocation(this.getModID(), path);
	}

}
