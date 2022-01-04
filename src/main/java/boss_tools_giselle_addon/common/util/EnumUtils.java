package boss_tools_giselle_addon.common.util;

public class EnumUtils
{
	public static <T extends Enum<?>> T valueOfIgnoreCase(Class<T> clazz, String name)
	{
		for (T each : clazz.getEnumConstants())
		{
			if (each.name().compareToIgnoreCase(name) == 0)
			{
				return each;
			}

		}

		return null;
	}

	private EnumUtils()
	{

	}

}
