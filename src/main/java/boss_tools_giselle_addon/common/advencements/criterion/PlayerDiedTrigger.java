package boss_tools_giselle_addon.common.advencements.criterion;

import com.google.gson.JsonObject;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class PlayerDiedTrigger extends AbstractCriterionTrigger<PlayerDiedTrigger.Instance>
{
	private static final ResourceLocation ID = BossToolsAddon.rl("player_died");

	public ResourceLocation getId()
	{
		return ID;
	}

	public PlayerDiedTrigger.Instance createInstance(JsonObject json, EntityPredicate.AndPredicate and, ConditionArrayParser parser)
	{
		LocationPredicate location = json.has("location") ? LocationPredicate.fromJson(json.get("location")) : null;
		DamageSourceTypePredicate damageType = json.has("damageType") ? DamageSourceTypePredicate.fromJson(json.get("damageType")) : null;
		return new PlayerDiedTrigger.Instance(and, location, damageType);
	}

	public void trigger(ServerPlayerEntity player, String damageType)
	{
		this.trigger(player, trigger ->
		{
			return trigger.matches(player.getLevel(), player.position(), damageType);
		});
	}

	public static class Instance extends CriterionInstance
	{
		private final LocationPredicate location;
		private final DamageSourceTypePredicate damageType;

		public Instance(EntityPredicate.AndPredicate player, LocationPredicate location, DamageSourceTypePredicate damageType)
		{
			super(PlayerDiedTrigger.ID, player);
			this.location = location;
			this.damageType = damageType;
		}

		public static PlayerDiedTrigger.Instance pickupFromOrbit(LocationPredicate location, DamageSourceTypePredicate damageType)
		{
			return new PlayerDiedTrigger.Instance(EntityPredicate.AndPredicate.ANY, location, damageType);
		}

		public boolean matches(ServerWorld level, Vector3d position, String damageType)
		{
			return (this.location == null || this.location.matches(level, position.x, position.y, position.z)) && (this.damageType == null || this.damageType.matches(damageType));
		}

		@Override
		public JsonObject serializeToJson(ConditionArraySerializer serializer)
		{
			JsonObject json = super.serializeToJson(serializer);

			if (this.location != null)
			{
				json.add("location", this.location.serializeToJson());
			}

			if (this.damageType != null)
			{
				json.add("damageType", this.damageType.serializeToJson());
			}

			return json;
		}

	}

}
