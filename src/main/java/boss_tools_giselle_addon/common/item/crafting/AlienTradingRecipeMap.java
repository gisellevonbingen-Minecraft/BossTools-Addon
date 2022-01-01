package boss_tools_giselle_addon.common.item.crafting;

import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang3.EnumUtils;

import com.google.gson.JsonObject;

import net.minecraft.entity.Entity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeSerializer;

public class AlienTradingRecipeMap extends AlienTradingRecipeItemStackBase
{
	private ResourceLocation structureName;
	private MapDecoration.Type mapDecorationType;

	public AlienTradingRecipeMap(ResourceLocation id, JsonObject json)
	{
		super(id, json);

		JsonObject result = JSONUtils.getAsJsonObject(json, "result");
		this.structureName = new ResourceLocation(JSONUtils.getAsString(result, "structureName"));
		this.mapDecorationType = EnumUtils.getEnumIgnoreCase(MapDecoration.Type.class, JSONUtils.getAsString(result, "mapDecorationType"));
	}

	public AlienTradingRecipeMap(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);

		this.structureName = buffer.readResourceLocation();
		this.mapDecorationType = buffer.readEnum(MapDecoration.Type.class);
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		super.write(buffer);

		buffer.writeResourceLocation(this.structureName);
		buffer.writeEnum(this.mapDecorationType);
	}

	@Override
	public ItemStack getResult(Entity trader, Random rand)
	{
		World level = trader.level;

		if (level instanceof ServerWorld)
		{
			ServerWorld serverWorld = (ServerWorld) level;
			Structure<?> structure = ForgeRegistries.STRUCTURE_FEATURES.getValue(this.getStructureName());
			BlockPos blockpos = serverWorld.findNearestMapFeature(structure, trader.blockPosition(), 100, true);

			if (blockpos != null)
			{
				ItemStack itemstack = FilledMapItem.create(level, blockpos.getX(), blockpos.getZ(), (byte) 2, true, true);
				itemstack.setHoverName((ITextComponent) new TranslationTextComponent("filled_map." + structure.getFeatureName().toLowerCase(Locale.ROOT)));
				FilledMapItem.renderBiomePreviewMap(serverWorld, itemstack);
				MapData.addTargetDecoration(itemstack, blockpos, "+", this.getMapDecorationType());

				return itemstack;
			}
			else
			{
				return new ItemStack(Items.MAP);
			}

		}
		else
		{
			return new ItemStack(Items.MAP);
		}

	}

	public ResourceLocation getStructureName()
	{
		return this.structureName;
	}

	public MapDecoration.Type getMapDecorationType()
	{
		return this.mapDecorationType;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_ALIEN_TRADING_MAP.get();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return AddonRecipes.ALIEN_TRADING_MAP;
	}

	public static class Serializer extends BossToolsRecipeSerializer<AlienTradingRecipeMap>
	{
		@Override
		public AlienTradingRecipeMap fromJson(ResourceLocation id, JsonObject json)
		{
			return new AlienTradingRecipeMap(id, json);
		}

		@Override
		public AlienTradingRecipeMap fromNetwork(ResourceLocation id, PacketBuffer buffer)
		{
			return new AlienTradingRecipeMap(id, buffer);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, AlienTradingRecipeMap recipe)
		{
			recipe.write(buffer);
		}

	}

}
