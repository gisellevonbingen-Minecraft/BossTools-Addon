package boss_tools_giselle_addon.common.advancements.criterion;

import com.google.gson.JsonObject;

import boss_tools_giselle_addon.common.BossToolsAddon;
import mekanism.api.providers.IItemProvider;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class ThrowOnOrbitTrigger extends AbstractCriterionTrigger<ThrowOnOrbitTrigger.Instance>
{
	private static final ResourceLocation ID = BossToolsAddon.rl("throw_on_orbit");

	public ResourceLocation getId()
	{
		return ID;
	}

	@Override
	protected ThrowOnOrbitTrigger.Instance createInstance(JsonObject json, EntityPredicate.AndPredicate player, ConditionArrayParser parser)
	{
		ItemPredicate item = ItemPredicate.fromJson(json.get("item"));
		return new ThrowOnOrbitTrigger.Instance(player, item);
	}

	public void trigger(ServerPlayerEntity player, ItemStack item)
	{
		this.trigger(player, trigger ->
		{
			return trigger.matches(item);
		});
	}

	public static class Instance extends CriterionInstance
	{
		private final ItemPredicate item;

		public Instance(EntityPredicate.AndPredicate player, ItemPredicate item)
		{
			super(ThrowOnOrbitTrigger.ID, player);
			this.item = item;
		}

		public static ThrowOnOrbitTrigger.Instance throwItemOnOrbit(IItemProvider item)
		{
			return new ThrowOnOrbitTrigger.Instance(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(item).build());
		}

		public boolean matches(ItemStack item)
		{
			return this.item.matches(item);
		}

		@Override
		public JsonObject serializeToJson(ConditionArraySerializer serializer)
		{
			JsonObject json = super.serializeToJson(serializer);
			json.add("item", this.item.serializeToJson());
			return json;
		}

	}

}
