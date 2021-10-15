package giselle.bosstools_addon.compat.mekanism;

import giselle.bosstools_addon.BossToolsAddon;
import mekanism.api.text.ILangEntry;
import net.minecraft.util.Util;

public class StringLangEntry implements ILangEntry
{
	private final String key;

	public StringLangEntry(String key)
	{
		this.key = key;
	}

	public StringLangEntry(String type, String path)
	{
		this(Util.makeDescriptionId(type, BossToolsAddon.rl(path)));
	}

	@Override
	public String getTranslationKey()
	{
		return this.key;
	}

}
