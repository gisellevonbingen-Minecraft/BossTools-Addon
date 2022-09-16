package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;

public abstract class ItemOutput
{
	public static ItemOutput fromJson(JsonElement element)
	{
		if (element.isJsonPrimitive() == true)
		{
			return of(JSONUtils.convertToItem(element, "item"));
		}

		JsonObject object = JSONUtils.convertToJsonObject(element, "element");
		JsonElement tagElement = object.get("tag");

		if (tagElement != null)
		{
			ITag<Item> tag = TagCollectionManager.getInstance().getItems().getTag(new ResourceLocation(JSONUtils.convertToString(tagElement, "tag")));
			int count = JSONUtils.getAsInt(object, "count", 1);
			return of(tag, count);
		}
		else
		{
			return of(CraftingHelper.getItemStack(object, true));
		}

	}

	public static ItemOutput fromNetwork(PacketBuffer buffer)
	{
		ItemStack item = buffer.readItem();
		return new ItemOutput.ItemStackValue(item);
	}

	public static ItemOutput of(Item item)
	{
		return new ItemOutput.ItemStackValue(new ItemStack(item));
	}

	public static ItemOutput of(ItemStack itemStack)
	{
		return new ItemOutput.ItemStackValue(itemStack.copy());
	}

	public static ItemOutput of(ITag<Item> tag)
	{
		return new ItemOutput.TagValue(tag, 1);
	}

	public static ItemOutput of(ITag<Item> tag, int count)
	{
		return new ItemOutput.TagValue(tag, count);
	}

	public void toNetwork(PacketBuffer buffer)
	{
		buffer.writeItem(this.getItemStack());
	}

	public abstract ItemStack getItemStack();

	public static class ItemStackValue extends ItemOutput
	{
		private final ItemStack itemStack;

		public ItemStackValue(ItemStack itemStack)
		{
			this.itemStack = itemStack;
		}

		@Override
		public ItemStack getItemStack()
		{
			return this.itemStack;
		}

	}

	public static class TagValue extends ItemOutput
	{
		private final ITag<Item> tag;
		private final int count;

		private ItemStack cached;

		public TagValue(ITag<Item> tag, int count)
		{
			this.tag = tag;
			this.count = count;

			this.cached = null;
		}

		@Override
		public ItemStack getItemStack()
		{
			if (this.cached == null)
			{
				this.cached = TagPreference.ITEMS.getPreference(this.tag).map(item -> new ItemStack(item, this.count)).orElse(ItemStack.EMPTY);
			}

			return this.cached;
		}

	}

}
