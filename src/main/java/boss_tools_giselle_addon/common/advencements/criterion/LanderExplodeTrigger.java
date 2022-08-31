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

public class LanderExplodeTrigger extends AbstractCriterionTrigger<LanderExplodeTrigger.Instance>
{
	private static final ResourceLocation ID = BossToolsAddon.rl("lander_explode");

	public ResourceLocation getId()
	{
		return ID;
	}

	public LanderExplodeTrigger.Instance createInstance(JsonObject json, EntityPredicate.AndPredicate and, ConditionArrayParser parser)
	{
		LocationPredicate location = json.has("location") ? LocationPredicate.fromJson(json.get("location")) : null;
		return new LanderExplodeTrigger.Instance(and, location);
	}

	public void trigger(ServerPlayerEntity player, Vector3d position)
	{
		this.trigger(player, trigger ->
		{
			return trigger.matches(player.getLevel(), position);
		});
	}

	public static class Instance extends CriterionInstance
	{
		private final LocationPredicate location;

		public Instance(EntityPredicate.AndPredicate player, LocationPredicate location)
		{
			super(LanderExplodeTrigger.ID, player);
			this.location = location;
		}

		public static LanderExplodeTrigger.Instance pickupFromOrbit(LocationPredicate location)
		{
			return new LanderExplodeTrigger.Instance(EntityPredicate.AndPredicate.ANY, location);
		}

		public boolean matches(ServerWorld level, Vector3d position)
		{
			return (this.location == null || this.location.matches(level, position.x, position.y, position.z));
		}

		@Override
		public JsonObject serializeToJson(ConditionArraySerializer serializer)
		{
			JsonObject json = super.serializeToJson(serializer);

			if (this.location != null)
			{
				json.add("location", this.location.serializeToJson());
			}

			return json;
		}

	}

}
