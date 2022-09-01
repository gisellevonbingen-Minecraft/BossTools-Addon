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

public class LanderExplodeTrigger extends SimpleCriterionTrigger<LanderExplodeTrigger.Instance>
{
	private static final ResourceLocation ID = BeyondEarthAddon.rl("lander_explode");

	public ResourceLocation getId()
	{
		return ID;
	}

	@Override
	protected LanderExplodeTrigger.Instance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext context)
	{
		LocationPredicate location = json.has("location") ? LocationPredicate.fromJson(json.get("location")) : null;
		return new LanderExplodeTrigger.Instance(player, location);
	}

	public void trigger(ServerPlayer player, Vec3 position)
	{
		this.trigger(player, trigger ->
		{
			return trigger.matches(player.getLevel(), position);
		});
	}

	public static class Instance extends AbstractCriterionTriggerInstance
	{
		private final LocationPredicate location;

		public Instance(EntityPredicate.Composite player, LocationPredicate location)
		{
			super(LanderExplodeTrigger.ID, player);
			this.location = location;
		}

		public static LanderExplodeTrigger.Instance pickupFromOrbit(LocationPredicate location)
		{
			return new LanderExplodeTrigger.Instance(EntityPredicate.Composite.ANY, location);
		}

		public boolean matches(ServerLevel level, Vec3 position)
		{
			return (this.location == null || this.location.matches(level, position.x, position.y, position.z));
		}

		@Override
		public JsonObject serializeToJson(SerializationContext context)
		{
			JsonObject json = super.serializeToJson(context);

			if (this.location != null)
			{
				json.add("location", this.location.serializeToJson());
			}

			return json;
		}

	}

}
