package boss_tools_giselle_addon.common.content.proof;

import com.mojang.datafixers.util.Pair;

import boss_tools_giselle_addon.common.enchantment.EnchantmentEnergyOrDurability;
import boss_tools_giselle_addon.common.enchantment.EnchantmentHelper2;
import boss_tools_giselle_addon.common.util.ItemEnergyDurabilityUtils;
import boss_tools_giselle_addon.common.util.LivingEntityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

public abstract class ProofEnchantmentSession extends ProofSession
{
	private EnchantmentEnergyOrDurability enchantment;
	private ItemStack enchantedItem;
	private int enchantLevel;

	public ProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyOrDurability enchantment)
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

		LivingEntity entity = this.getEntity();
		int energyUsing = this.getEnergyUsing();
		int durabilityUsing = this.getDurabilityUsing();

		if (LivingEntityHelper.isPlayingMode(entity) == true)
		{
			if (ItemEnergyDurabilityUtils.extractEnergyOrDurability(enchantedItem, energyUsing, durabilityUsing, true, null, null) == false)
			{
				return false;
			}

		}

		return super.canProvide();
	}

	@Override
	public void onProvide()
	{
		super.onProvide();

		LivingEntity entity = this.getEntity();

		if (LivingEntityHelper.isPlayingMode(entity) == true)
		{
			ServerPlayerEntity player = entity instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity : null;
			ItemStack enchantedItem = this.getEnchantedItem();
			int energyUsing = this.getEnergyUsing();
			int durabilityUsing = this.getDurabilityUsing();
			ItemEnergyDurabilityUtils.extractEnergyOrDurability(enchantedItem, energyUsing, durabilityUsing, false, entity.getRandom(), player);
		}

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
