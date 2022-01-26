package boss_tools_giselle_addon.common.content.proof;

import com.mojang.datafixers.util.Pair;

import boss_tools_giselle_addon.common.enchantment.EnchantmentEnergyOrDurability;
import boss_tools_giselle_addon.common.enchantment.EnchantmentHelper2;
import boss_tools_giselle_addon.common.util.ItemEnergyDurabilityUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

public abstract class SpaceProofEnchantmentSession extends SpaceProofSession
{
	private EnchantmentEnergyOrDurability enchantment;
	private ItemStack enchantedItem;
	private int enchantLevel;

	public SpaceProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyOrDurability enchantment)
	{
		super(entity);
		this.enchantment = enchantment;

		Pair<ItemStack, Integer> pair = EnchantmentHelper2.getEnchantmentItemAndLevel(enchantment, entity);
		this.enchantedItem = pair.getFirst();
		this.enchantLevel = pair.getSecond();
	}

	@Override
	public boolean canProvide()
	{
		ItemStack enchantedItem = this.getEnchantedItem();
		int enchantLevel = this.getEnchantLevel();

		if (enchantedItem.isEmpty() == true || enchantLevel == 0)
		{
			return false;
		}

		int energyUsing = this.getEnergyUsing();
		int durabilityUsing = this.getDurabilityUsing();

		if (ItemEnergyDurabilityUtils.extractEnergyOrDurability(enchantedItem, energyUsing, durabilityUsing, true, null, null) == false)
		{
			return false;
		}
		else
		{
			return super.canProvide();
		}

	}

	@Override
	public void onProvide()
	{
		super.onProvide();

		ItemStack enchantedItem = this.getEnchantedItem();
		int energyUsing = this.getEnergyUsing();
		int durabilityUsing = this.getDurabilityUsing();
		LivingEntity entity = this.getEntity();
		ServerPlayerEntity player = (entity instanceof ServerPlayerEntity) ? (ServerPlayerEntity) entity : null;
		ItemEnergyDurabilityUtils.extractEnergyOrDurability(enchantedItem, energyUsing, durabilityUsing, false, entity.getRandom(), player);
	}

	public EnchantmentEnergyOrDurability getEnchantment()
	{
		return this.enchantment;
	}

	public ItemStack getEnchantedItem()
	{
		return this.enchantedItem;
	}

	public int getEnchantLevel()
	{
		return this.enchantLevel;
	}

	public abstract int getEnergyUsing();

	public abstract int getDurabilityUsing();

}
