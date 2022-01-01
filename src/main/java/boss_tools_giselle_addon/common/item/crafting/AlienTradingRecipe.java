package boss_tools_giselle_addon.common.item.crafting;

import java.util.Random;

import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonObject;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipe;

public abstract class AlienTradingRecipe extends BossToolsRecipe
{
	private VillagerProfession job;
	private int level;
	private int xp;
	private int maxUses;
	private float priceMultiplier;

	public AlienTradingRecipe(ResourceLocation id, JsonObject json)
	{
		super(id, json);

		this.job = ForgeRegistries.PROFESSIONS.getValue(new ResourceLocation(JSONUtils.getAsString(json, "job")));
		this.level = JSONUtils.getAsInt(json, "level");
		this.xp = JSONUtils.getAsInt(json, "xp");
		this.maxUses = JSONUtils.getAsInt(json, "maxUses", 9999999);
		this.priceMultiplier = JSONUtils.getAsFloat(json, "priceMultiplier", 0.05F);
	}

	public AlienTradingRecipe(ResourceLocation id, PacketBuffer buffer)
	{
		super(id, buffer);

		this.job = buffer.readRegistryId();
		this.level = buffer.readInt();
		this.xp = buffer.readInt();
		this.maxUses = buffer.readInt();
		this.priceMultiplier = buffer.readFloat();
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
	}

	public abstract Triple<ItemStack, ItemStack, ItemStack> getTrade(Entity trader, Random rand);

	@Override
	public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_)
	{
		return true;
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

}
