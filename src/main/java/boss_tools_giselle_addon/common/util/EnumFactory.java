package boss_tools_giselle_addon.common.util;

public interface EnumFactory<T>
{
	T make(int internalId);
}
