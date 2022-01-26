package boss_tools_giselle_addon.common.content.spacebreathing;

import com.google.common.collect.Iterables;
import com.mojang.datafixers.util.Pair;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.capability.CapabilityOxygenCharger;
import boss_tools_giselle_addon.common.capability.IOxygenCharger;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.config.EnchantmentsConfig;
import boss_tools_giselle_addon.common.enchantment.AddonEnchantments;
import boss_tools_giselle_addon.common.enchantment.EnchantmentHelper2;
import boss_tools_giselle_addon.common.event.LivingProvideProofDurationEvent;
import boss_tools_giselle_addon.common.inventory.InventoryHelper;
import boss_tools_giselle_addon.common.util.ItemEnergyDurabilityUtils;
import boss_tools_giselle_addon.common.util.NBTUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;

public class SpaceBreathingUtils
{
	public static final String NBT_KEY = BossToolsAddon.rl("spacebreathing").toString();
	public static final String NBT_PROOFDURATION_KEY = "proofduration";

	public static void reduceProofDuration(LivingEntity entity)
	{
		int proofDuration = getProofDuration(entity);

		if (proofDuration > 0)
		{
			setProofDuration(entity, proofDuration - 1);
		}

	}

	public static int getProofDuration(LivingEntity entity)
	{
		CompoundNBT compound = NBTUtils.getTag(entity, NBT_KEY);
		return compound != null ? compound.getInt(NBT_PROOFDURATION_KEY) : 0;
	}

	public static void setProofDuration(LivingEntity entity, int proofDuration)
	{
		CompoundNBT compound = NBTUtils.getOrCreateTag(entity, NBT_KEY);
		compound.putLong(NBT_PROOFDURATION_KEY, Math.max(proofDuration, 0));
	}

	public static boolean tryProvideOxygen(LivingEntity entity)
	{
		if (getProofDuration(entity) > 0)
		{
			return true;
		}
		else if (provideOxygenEnchantment(entity) == true)
		{
			return true;
		}
		else if (provideOxygenEvent(entity) == true)
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	public static boolean provideOxygenEvent(LivingEntity entity)
	{
		int proofDuration = LivingProvideProofDurationEvent.postUntilDuration(new LivingProvideProofDurationEvent(entity)).getProofDuration();

		if (proofDuration > 0)
		{
			setProofDuration(entity, proofDuration);
			return true;
		}
		else
		{
			return false;
		}

	}

	public static boolean provideOxygenEnchantment(LivingEntity entity)
	{
		Pair<ItemStack, Integer> pair = EnchantmentHelper2.getEnchantmentItemAndLevel(AddonEnchantments.SPACE_BREATHING.get(), entity);
		ItemStack equipment = pair.getFirst();
		int level = pair.getSecond();

		if (equipment.isEmpty() || level == 0)
		{
			return false;
		}

		InventoryHelper.getInventoryStacks(entity);
		ItemStack oxygenCan = InventoryHelper.getInventoryStacks(entity).stream().filter(is ->
		{
			IOxygenCharger oxygenCharger = is.getCapability(CapabilityOxygenCharger.OXYGEN_CHARGER).orElse(null);
			return oxygenCharger != null && Iterables.contains(oxygenCharger.getChargeMode().getItemStacks(entity), equipment);
		}).findFirst().orElse(ItemStack.EMPTY);
		IOxygenStorage oxygenStorage = oxygenCan.getCapability(CapabilityOxygen.OXYGEN).orElse(null);
		int oxygenUsing = 1;

		if (oxygenStorage == null || oxygenStorage.extractOxygen(oxygenUsing, true) < oxygenUsing)
		{
			return false;
		}

		EnchantmentsConfig config = AddonConfigs.Common.enchantments;
		int energyUsing = config.space_breathing_energyUsing.get();
		int durabilityUsing = config.space_breathing_durabilityUsing.get();

		ServerPlayerEntity player = (entity instanceof ServerPlayerEntity) ? (ServerPlayerEntity) entity : null;

		if (ItemEnergyDurabilityUtils.extractEnergyOrDurability(equipment, energyUsing, durabilityUsing, true, null, null) == false)
		{
			return false;
		}

		oxygenStorage.extractOxygen(oxygenUsing, false);
		ItemEnergyDurabilityUtils.extractEnergyOrDurability(equipment, level, oxygenUsing, false, entity.getRandom(), player);
		setProofDuration(entity, config.space_breathing_proofDuration.get());
		return true;
	}

	private SpaceBreathingUtils()
	{

	}

}
