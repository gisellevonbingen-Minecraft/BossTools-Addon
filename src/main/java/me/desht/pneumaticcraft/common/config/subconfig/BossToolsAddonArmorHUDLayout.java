package me.desht.pneumaticcraft.common.config.subconfig;

import com.google.gson.JsonObject;

import boss_tools_giselle_addon.common.BossToolsAddon;
import me.desht.pneumaticcraft.common.config.subconfig.ArmorHUDLayout.LayoutItem;

public class BossToolsAddonArmorHUDLayout
{
	private static final String SPACE_BREATHING_KEY = BossToolsAddon.rl("space_breathing").toString();

	private static final LayoutItem SPACE_BREATHING_DEF = new LayoutItem(0.5F, 0.005F, false);

	public static final BossToolsAddonArmorHUDLayout INSTANCE = new BossToolsAddonArmorHUDLayout();

	public LayoutItem spaceBreathingStat = SPACE_BREATHING_DEF;

	public void readFromJson(JsonObject json)
	{
		if (json.has("stats") == true)
		{
			JsonObject sub = json.getAsJsonObject("stats");
			this.spaceBreathingStat = readLayout(sub, SPACE_BREATHING_KEY, SPACE_BREATHING_DEF);
		}

	}

	public void writeToJson(JsonObject json)
	{
		JsonObject sub = json.getAsJsonObject("stats");
		sub.add(SPACE_BREATHING_KEY, this.spaceBreathingStat.toJson());
	}

	private LayoutItem readLayout(JsonObject json, String name, LayoutItem def)
	{
		return json.has(name) ? LayoutItem.fromJson(json.get(name).getAsJsonObject()) : def;
	}

	public static class LayoutType
	{
		public static ArmorHUDLayout.LayoutType SPACE_BREATHING;
	}

}
