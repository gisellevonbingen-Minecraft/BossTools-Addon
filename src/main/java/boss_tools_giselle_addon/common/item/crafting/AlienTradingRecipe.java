package boss_tools_giselle_addon.common.item.crafting;

import com.google.gson.JsonObject;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipe;

public class AlienTradingRecipe extends BossToolsRecipe
{
	private VillagerProfession job;
	private int level;
	private int xp;
	private int maxUses;
	private float priceMultiplier;
	private ItemStack costA;
	private ItemStack costB;
	private ItemStack result;

	public AlienTradingRecipe(ResourceLocation id, JsonObject json)
	{
		super(id, json);

		this.job = ForgeRegistries.PROFESSIONS.getValue(new ResourceLocation(JSONUtils.getAsString(json, "job")));
		this.level = JSONUtils.getAsInt(json, "level");
		this.xp = JSONUtils.getAsInt(json, "xp");
		this.maxUses = JSONUtils.getAsInt(json, "maxUses");
		this.priceMultiplier = JSONUtils.getAsFloat(json, "priceMultiplier");
		this.costA = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "costA"), true);
		this.costB =  json.has("costB") ? CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "costB"), true) : ItemStack.EMPTY;
		this.result = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "result"), true);
	}

	public AlienTradingRecipe(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);

		this.job = buffer.readRegistryId();
		this.level = buffer.readInt();
		this.xp = buffer.readInt();
		this.maxUses = buffer.readInt();
		this.priceMultiplier = buffer.readFloat();
		this.costA = buffer.readItem();
		this.costB = buffer.readItem();
		this.result = buffer.readItem();
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		super.write(buffer);

		buffer.writeRegistryId(this.job);
		buffer.writeInt(this.level);
		buffer.writeInt(this.xp);
		buffer.writeInt(this.maxUses);
		buffer.writeFloat(this.priceMultiplier);
		buffer.writeItem(this.costA);
		buffer.writeItem(this.costB);
		buffer.writeItem(this.result);
	}

	@Override
	public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_)
	{
		return true;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return AddonRecipes.RECIPE_SERIALIZER_ALIEN_TRADING.get();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return AddonRecipes.ALIEN_TRADING;
	}

	public VillagerProfession getJob()
	{
		return this.job;
	}

	public int getLevel()
	{
		return this.level;
	}

	public int getXP()
	{
		return this.xp;
	}

	public int getMaxUses()
	{
		return this.maxUses;
	}

	public float getPriceMultiplier()
	{
		return this.priceMultiplier;
	}

	public ItemStack getCostA()
	{
		return this.costA.copy();
	}

	public ItemStack getCostB()
	{
		return this.costB.copy();
	}

	public ItemStack getResult()
	{
		return this.result.copy();
	}

}
