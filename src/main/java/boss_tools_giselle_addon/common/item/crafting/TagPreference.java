package boss_tools_giselle_addon.common.item.crafting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import boss_tools_giselle_addon.common.config.AddonConfigs;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class TagPreference<T extends IForgeRegistryEntry<T>>
{
	private static final RegistryComparator COMPARATOR = new RegistryComparator();

	private static final List<TagPreference<?>> PREFERNCES = new ArrayList<>();

	private static final Map<ResourceLocation, Optional<?>> PREFERENCE_CACHE = new HashMap<>();

	public static final TagPreference<Item> ITEMS = new TagPreference<>(() -> TagCollectionManager.getInstance().getItems());

	public static final TagPreference<Fluid> FLUIDS = new TagPreference<>(() -> TagCollectionManager.getInstance().getFluids());

	public static void register(IEventBus bus)
	{
		bus.addListener((TagsUpdatedEvent.VanillaTagTypes e) -> TagPreference.onTagsUpdated(e));
	}

	private static void onTagsUpdated(TagsUpdatedEvent.VanillaTagTypes event)
	{
		PREFERENCE_CACHE.clear();
	}

	private final Map<ResourceLocation, Optional<?>> caches = new HashMap<>();
	private final Supplier<ITagCollection<T>> tagCollectionSupplier;

	private TagPreference(Supplier<ITagCollection<T>> tagCollectionSupplier)
	{
		PREFERNCES.add(this);
		this.tagCollectionSupplier = tagCollectionSupplier;
	}

	private Optional<T> getUncachedPreference(ITag<T> tag)
	{
		ResourceLocation id = this.tagCollectionSupplier.get().getId(tag);

		if (id == null)
		{
			return Optional.empty();
		}
		else
		{
			return tag.getValues().stream().min(COMPARATOR);
		}

	}

	@SuppressWarnings("unchecked")
	public Optional<T> getPreference(ITag<T> tag)
	{
		ResourceLocation id = this.tagCollectionSupplier.get().getId(tag);
		return (Optional<T>) this.caches.computeIfAbsent(id, name -> getUncachedPreference(tag));
	}

	private static class RegistryComparator implements Comparator<IForgeRegistryEntry<?>>
	{
		public int defaultIf(int check, int undesired, int fallback)
		{
			return check == undesired ? fallback : check;
		}

		@Override
		public int compare(IForgeRegistryEntry<?> a, IForgeRegistryEntry<?> b)
		{
			ResourceLocation idA = a.getRegistryName();
			ResourceLocation idB = b.getRegistryName();
			List<? extends String> entries = AddonConfigs.Common.recipes.tagPreferences.get();
			int size = entries.size();
			int indexA = this.defaultIf(entries.indexOf(idA.getNamespace()), -1, size);
			int indexB = this.defaultIf(entries.indexOf(idB.getNamespace()), -1, size);

			if (indexA != indexB)
			{
				return Integer.compare(indexA, indexB);
			}
			else
			{
				return idA.compareNamespaced(idB);
			}

		}

	}

}
