package beyond_earth_giselle_addon.common.advencements.criterion;

import com.google.gson.JsonObject;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class PlayerDiedTrigger extends SimpleCriterionTrigger<PlayerDiedTrigger.Instance>
{
	private static final ResourceLocation ID = BeyondEarthAddon.rl("player_died");

	public ResourceLocation getId()
	{
		return ID;
	}

	@Override
	protected PlayerDiedTrigger.Instance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext context)
	{
		LocationPredicate location = json.has("location") ? LocationPredicate.fromJson(json.get("location")) : null;
		DamageSourceTypePredicate damageType = json.has("damageType") ? DamageSourceTypePredicate.fromJson(json.get("damageType")) : null;
		return new PlayerDiedTrigger.Instance(player, location, damageType);
	}

	public void trigger(ServerPlayer player, String damageType)
	{
		this.trigger(player, trigger ->
		{
			return trigger.matches(player.getLevel(), player.position(), damageType);
		});
	}

	public static class Instance extends AbstractCriterionTriggerInstance
	{
		private final LocationPredicate location;
		private final DamageSourceTypePredicate damageType;

		public Instance(EntityPredicate.Composite player, LocationPredicate location, DamageSourceTypePredicate damageType)
		{
			super(PlayerDiedTrigger.ID, player);
			this.location = location;
			this.damageType = damageType;
		}

		public static PlayerDiedTrigger.Instance pickupFromOrbit(LocationPredicate location, DamageSourceTypePredicate damageType)
		{
			return new PlayerDiedTrigger.Instance(EntityPredicate.Composite.ANY, location, damageType);
		}

		public boolean matches(ServerLevel level, Vec3 position, String damageType)
		{
			return (this.location == null || this.location.matches(level, position.x, position.y, position.z)) && (this.damageType == null || this.damageType.matches(damageType));
		}

		@Override
		public JsonObject serializeToJson(SerializationContext context)
		{
			JsonObject json = super.serializeToJson(context);

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
