package boss_tools_giselle_addon.common.advencements.criterion;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PlayerCreateSpaceStationTrigger extends AbstractCriterionTrigger<PlayerCreateSpaceStationTrigger.Instance>
{
	private static final ResourceLocation ID = BossToolsAddon.rl("player_create_space_station");

	public ResourceLocation getId()
	{
		return ID;
	}

	public PlayerCreateSpaceStationTrigger.Instance createInstance(JsonObject json, EntityPredicate.AndPredicate and, ConditionArrayParser parser)
	{
		RegistryKey<World> at = json.has("at") ? RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(JSONUtils.getAsString(json, "from"))) : null;
		return new PlayerCreateSpaceStationTrigger.Instance(and, at);
	}

	public void trigger(ServerPlayerEntity player)
	{
		this.trigger(player, trigger ->
		{
			return trigger.matches(player.getLevel());
		});
	}

	public static class Instance extends CriterionInstance
	{
		@Nullable
		private final RegistryKey<World> at;

		public Instance(EntityPredicate.AndPredicate player, @Nullable RegistryKey<World> at)
		{
			super(PlayerCreateSpaceStationTrigger.ID, player);
			this.at = at;
		}

		public static PlayerCreateSpaceStationTrigger.Instance playerCreateSpaceStation(@Nullable RegistryKey<World> at)
		{
			return new PlayerCreateSpaceStationTrigger.Instance(EntityPredicate.AndPredicate.ANY, at);
		}

		public boolean matches(ServerWorld level)
		{
			return this.at == null || this.at == level.dimension();
		}

		@Override
		public JsonObject serializeToJson(ConditionArraySerializer serializer)
		{
			JsonObject json = super.serializeToJson(serializer);

			if (this.at != null)
			{
				json.addProperty("at", this.at.location().toString());
			}

			return json;
		}

	}

}
