package beyond_earth_giselle_addon.common.advencements.criterion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;

public class DamageSourceTypePredicate
{
	public static final DamageSourceTypePredicate ANY = DamageSourceTypePredicate.Builder.damageType().build();

	private final List<String> types = new ArrayList<>();

	public DamageSourceTypePredicate(Collection<String> types)
	{
		this.types.addAll(types);
	}

	public boolean matches(String damageType)
	{
		return this.types.size() == 0 || this.types.contains(damageType);
	}

	public static DamageSourceTypePredicate fromJson(@Nullable JsonElement json)
	{
		if (json == null || json.isJsonNull() == true)
		{
			return ANY;
		}
		else
		{
			List<String> types = new ArrayList<>();
			JsonArray array = GsonHelper.convertToJsonArray(json, "damageTypes");

			for (JsonElement element : array)
			{
				types.add(GsonHelper.convertToString(element, "element"));
			}

			return new DamageSourceTypePredicate(types);
		}

	}

	public JsonElement serializeToJson()
	{
		if (this == ANY)
		{
			return JsonNull.INSTANCE;
		}
		else
		{
			JsonArray json = new JsonArray();

			for (String type : this.types)
			{
				json.add(type);
			}

			return json;
		}

	}

	public static class Builder
	{
		private final List<String> types = new ArrayList<>();

		public static DamageSourceTypePredicate.Builder damageType()
		{
			return new DamageSourceTypePredicate.Builder();
		}

		public DamageSourceTypePredicate.Builder add(String type)
		{
			this.types.add(type);
			return this;
		}

		public DamageSourceTypePredicate.Builder add(DamageSource source)
		{
			this.types.add(source.getMsgId());
			return this;
		}

		public DamageSourceTypePredicate build()
		{
			return new DamageSourceTypePredicate(this.types);
		}

	}

}
