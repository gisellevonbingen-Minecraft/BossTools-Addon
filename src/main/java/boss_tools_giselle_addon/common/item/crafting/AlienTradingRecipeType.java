package boss_tools_giselle_addon.common.item.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;

public class AlienTradingRecipeType<T extends AlienTradingRecipe> extends BossToolsRecipeType<T>
{
	private static final List<AlienTradingRecipeType<?>> TYPES = new ArrayList<>();

	public static List<AlienTradingRecipeType<?>> getTypes()
	{
		return Collections.unmodifiableList(TYPES);
	}

	public AlienTradingRecipeType(String name)
	{
		super(name);
		TYPES.add(this);
	}

}
