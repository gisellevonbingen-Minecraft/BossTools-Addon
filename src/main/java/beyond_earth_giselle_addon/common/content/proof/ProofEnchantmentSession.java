package beyond_earth_giselle_addon.common.content.proof;

import com.mojang.datafixers.util.Pair;

import beyond_earth_giselle_addon.common.enchantment.EnchantmentEnergyStorage;
import beyond_earth_giselle_addon.common.enchantment.EnchantmentHelper2;
import beyond_earth_giselle_addon.common.util.LivingEntityHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class ProofEnchantmentSession extends ProofSession
{
	private EnchantmentEnergyStorage enchantment;
	private ItemStack enchantedItem;
	private IEnergyStorage energyStorage;
	private int enchantLevel;

	public ProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyStorage enchantment)
	{
		super(entity);
		this.enchantment = enchantment;

		Pair<ItemStack, Integer> pair = EnchantmentHelper2.getEnchantmentItemAndLevel(enchantment, entity);
		this.enchantedItem = pair.getFirst();
		this.energyStorage = this.enchantedItem.getCapability(CapabilityEnergy.ENERGY).orElse(null);
		this.enchantLevel = pair.getSecond();
	}

	@Override
	public boolean canProvide()
	{
		ItemStack enchantedItem = this.getEnchantedItem();
		IEnergyStorage energyStorage = this.getEnergyStorage();
		int enchantLevel = this.getEnchantLevel();

		if (enchantedItem.isEmpty() == true || enchantLevel == 0 || energyStorage == null)
		{
			return false;
		}

		LivingEntity entity = this.getEntity();

		if (LivingEntityHelper.isPlayingMode(entity) == true)
		{
			int energyUsing = this.getEnergyUsing();

			if (energyStorage.extractEnergy(energyUsing, true) < energyUsing)
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
			if (entity.getLevel().isClientSide() == false)
			{
				int energyUsing = this.getEnergyUsing();
				this.getEnergyStorage().extractEnergy(energyUsing, false);
			}

		}

	}

	public EnchantmentEnergyStorage getEnchantment()
	{
		return this.enchantment;
	}

	public ItemStack getEnchantedItem()
	{
		return this.enchantedItem;
	}

	public IEnergyStorage getEnergyStorage()
	{
		return this.energyStorage;
	}

	public int getEnchantLevel()
	{
		return this.enchantLevel;
	}

	public abstract int getEnergyUsing();

}
