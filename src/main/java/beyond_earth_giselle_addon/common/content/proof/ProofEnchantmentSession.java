package beyond_earth_giselle_addon.common.content.proof;

import com.mojang.datafixers.util.Pair;

import beyond_earth_giselle_addon.common.enchantment.EnchantmentEnergyStorageOrDamageable;
import beyond_earth_giselle_addon.common.enchantment.EnchantmentHelper2;
import beyond_earth_giselle_addon.common.util.ItemStackUtils;
import beyond_earth_giselle_addon.common.util.ItemUsableResource;
import beyond_earth_giselle_addon.common.util.ItemUsableResourceResult;
import beyond_earth_giselle_addon.common.util.LivingEntityHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class ProofEnchantmentSession extends ProofSession
{
	private EnchantmentEnergyStorageOrDamageable enchantment;
	private ItemStack enchantedItem;
	private int enchantLevel;

	private ItemUsableResource testedUsableResource;

	public ProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyStorageOrDamageable enchantment)
	{
		super(entity);
		this.enchantment = enchantment;

		Pair<ItemStack, Integer> pair = EnchantmentHelper2.getEnchantmentItemAndLevel(enchantment, entity);
		this.enchantedItem = pair.getFirst();
		this.enchantLevel = pair.getSecond();

		this.testedUsableResource = null;
	}

	@Override
	public boolean canProvide()
	{
		if (super.canProvide() == false)
		{
			return false;
		}

		ItemStack enchantedItem = this.getEnchantedItem();
		int enchantLevel = this.getEnchantLevel();

		if (enchantedItem.isEmpty() == true || enchantLevel == 0)
		{
			return false;
		}

		if (LivingEntityHelper.isPlayingMode(this.getEntity()) == true)
		{
			if (ItemStackUtils.hasUseableResources(enchantedItem) == true)
			{
				int energyUsing = this.getResourceUsingAmount(ItemUsableResource.Energy);
				int durabilityUsing = this.getResourceUsingAmount(ItemUsableResource.Durability);
				ItemUsableResourceResult result = ItemStackUtils.useResources(enchantedItem, energyUsing, durabilityUsing, true);
				this.testedUsableResource = result.getResource();

				if (result.getResult() == false)
				{
					return false;
				}

			}

		}
		else
		{
			this.testedUsableResource = ItemUsableResource.Durability;
		}

		return true;
	}

	@Override
	public void onProvide()
	{
		super.onProvide();

		LivingEntity entity = this.getEntity();

		if (LivingEntityHelper.isPlayingMode(entity) == true)
		{
			if (entity.level.isClientSide() == false)
			{
				ItemStack enchantedItem = this.getEnchantedItem();
				int energyUsing = this.getResourceUsingAmount(ItemUsableResource.Energy);
				int durabilityUsing = this.getResourceUsingAmount(ItemUsableResource.Durability);
				ItemStackUtils.useResources(enchantedItem, energyUsing, durabilityUsing, false);
			}

		}

	}

	public abstract int getResourceUsingAmount(ItemUsableResource resource);

	public abstract int getProofDuration(ItemUsableResource resource);

	@Override
	public int getProofDuration()
	{
		ItemUsableResource resource = this.getTestedUsableResource();
		return this.getProofDuration(resource);
	}

	public EnchantmentEnergyStorageOrDamageable getEnchantment()
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

	public ItemUsableResource getTestedUsableResource()
	{
		return this.testedUsableResource;
	}

}
