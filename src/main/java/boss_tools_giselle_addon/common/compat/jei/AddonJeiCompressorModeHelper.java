package boss_tools_giselle_addon.common.compat.jei;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity.ICompressorMode;
import net.minecraft.util.ResourceLocation;

public class AddonJeiCompressorModeHelper
{
	public static final AddonJeiCompressorModeHelper INSTANCE = new AddonJeiCompressorModeHelper();

	private final Map<ICompressorMode, ResourceLocation> mode2uids;
	private final Map<ResourceLocation, ICompressorMode> uid2modes;

	private AddonJeiCompressorModeHelper()
	{
		this.mode2uids = new HashMap<>();
		this.uid2modes = new HashMap<>();
	}

	public Map<ICompressorMode, ResourceLocation> getModeToCategoryUids()
	{
		return Collections.unmodifiableMap(this.mode2uids);
	}

	public Map<ICompressorMode, ResourceLocation> getCategoryUidToModes()
	{
		return Collections.unmodifiableMap(this.mode2uids);
	}

	public ICompressorMode modeByCategoryUid(ResourceLocation categoryUid)
	{
		return this.uid2modes.get(categoryUid);
	}

	public ResourceLocation categoryUidByMode(ICompressorMode mode)
	{
		return this.mode2uids.get(mode);
	}

	public void register(ICompressorMode mode, ResourceLocation categoryUid)
	{
		this.mode2uids.put(mode, categoryUid);
		this.uid2modes.put(categoryUid, mode);
	}

}
