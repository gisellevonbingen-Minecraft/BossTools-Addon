package beyond_earth_giselle_addon.common.item;

import java.util.List;

import beyond_earth_giselle_addon.common.capability.ChargeMode;
import beyond_earth_giselle_addon.common.capability.IChargeMode;
import beyond_earth_giselle_addon.common.capability.IOxygenCharger;
import beyond_earth_giselle_addon.common.capability.OxygenCanCapabilityProvider;
import beyond_earth_giselle_addon.common.capability.OxygenChargerUtils;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.config.ItemsConfig;
import beyond_earth_giselle_addon.common.util.TranslationUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.mrscauthd.beyond_earth.capabilities.oxygen.IOxygenStorage;
import net.mrscauthd.beyond_earth.capabilities.oxygen.OxygenUtil;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;

public class OxygenCanItem extends Item
{
	public OxygenCanItem(Properties properties)
	{
		super(properties.stacksTo(1));
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> list)
	{
		super.fillItemCategory(group, list);

		if (this.allowdedIn(group) == true)
		{
			ItemStack full = new ItemStack(this);
			IOxygenCharger oxygenCharger = this.initCapabilities(full, null).getOxygenCharger();

			if (oxygenCharger != null)
			{
				oxygenCharger.setChargeMode(ChargeMode.ALL);

				IOxygenStorage oxygenStorage = oxygenCharger.getOxygenStorage();
				oxygenStorage.setOxygenStored(oxygenStorage.getMaxOxygenStored());
				list.add(full);
			}

		}

	}

	@Override
	public Rarity getRarity(ItemStack stack)
	{
		return this.isFoil(stack) ? Rarity.EPIC : super.getRarity(stack);
	}

	@Override
	public boolean isFoil(ItemStack stack)
	{
		IOxygenCharger oxygenCharger = OxygenChargerUtils.getOxygenCharger(stack);

		if (oxygenCharger != null && oxygenCharger.getOxygenStorage().extractOxygen(1, true) > 0 && oxygenCharger.getChargeMode() != ChargeMode.NONE)
		{
			return true;
		}

		return super.isFoil(stack);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		ItemStack stack = player.getItemInHand(hand);

		if (level.isClientSide() == true)
		{
			return InteractionResultHolder.pass(stack);
		}

		IOxygenCharger oxygenCharger = OxygenChargerUtils.getOxygenCharger(stack);

		if (oxygenCharger != null && player.isShiftKeyDown() == false)
		{
			IChargeMode chargeMode = oxygenCharger.getChargeMode();
			List<IChargeMode> modes = oxygenCharger.getAvailableChargeModes();
			int nextIndex = (modes.indexOf(chargeMode) + 1) % modes.size();
			IChargeMode nextMode = modes.get(nextIndex);
			oxygenCharger.setChargeMode(nextMode);

			player.sendMessage(TranslationUtils.descriptionChargeMode(nextMode), player.getUUID());
		}

		return InteractionResultHolder.pass(stack);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected)
	{
		super.inventoryTick(stack, level, entity, slot, selected);

		if (level.isClientSide() == false)
		{
			transferToItems(entity, stack);
		}

	}

	public static void transferToItems(Entity entity, ItemStack stack)
	{
		if (entity == null || stack == null)
		{
			return;
		}

		IOxygenCharger oxygenCharger = OxygenChargerUtils.getOxygenCharger(stack);

		if (oxygenCharger == null)
		{
			return;
		}

		IOxygenStorage oxygenStorage = oxygenCharger.getOxygenStorage();
		Iterable<ItemStack> itemStacks = oxygenCharger.getChargeMode().getItemStacks(entity);
		int transfer = AddonConfigs.Common.items.oxygenCan_OxygenTransfer.get();

		if (transfer <= 0)
		{
			return;
		}

		for (ItemStack itemStack : itemStacks)
		{
			if (OxygenChargerUtils.getOxygenCharger(itemStack) != null)
			{
				continue;
			}

			IOxygenStorage targetOxygenStorage = OxygenUtil.getItemStackOxygenStorage(itemStack);

			if (targetOxygenStorage != null)
			{
				int extracting = oxygenStorage.extractOxygen(transfer, true);
				int receiving = targetOxygenStorage.receiveOxygen(extracting, true);

				if (receiving > 0)
				{
					oxygenStorage.extractOxygen(receiving, false);
					targetOxygenStorage.receiveOxygen(receiving, false);
					transfer -= receiving;

					if (transfer <= 0)
					{
						break;
					}

				}

			}

		}

	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag)
	{
		super.appendHoverText(stack, level, tooltip, flag);

		IOxygenCharger oxygenCharger = OxygenChargerUtils.getOxygenCharger(stack);

		if (oxygenCharger != null)
		{
			tooltip.add(TranslationUtils.descriptionChargeMode(oxygenCharger.getChargeMode()));
			tooltip.add(GaugeTextHelper.buildSpacesuitOxygenTooltip(oxygenCharger.getOxygenStorage()));
		}

	}

	@Override
	public boolean isBarVisible(ItemStack stack)
	{
		return OxygenChargerUtils.getOxygenCharger(stack) != null;
	}

	@Override
	public int getBarWidth(ItemStack stack)
	{
		IOxygenCharger oxygenCharger = OxygenChargerUtils.getOxygenCharger(stack);
		double ratio = oxygenCharger != null ? oxygenCharger.getOxygenStorage().getOxygenStoredRatio() : 0.0D;
		return (int) (ratio * 13);
	}

	@Override
	public int getBarColor(ItemStack stack)
	{
		IOxygenCharger oxygenCharger = OxygenChargerUtils.getOxygenCharger(stack);
		double ratio = oxygenCharger != null ? oxygenCharger.getOxygenStorage().getOxygenStoredRatio() : 0.0D;
		return Mth.hsvToRgb((float) (ratio / 3.0F), 1.0F, 1.0F);
	}

	@Override
	public OxygenCanCapabilityProvider initCapabilities(ItemStack stack, CompoundTag tag)
	{
		ItemsConfig config = AddonConfigs.Common.items;
		int capacity = config.oxygenCan_OxygenCapacity.get();
		int transfer = config.oxygenCan_OxygenTransfer.get();
		return new OxygenCanCapabilityProvider(stack, capacity, transfer);
	}

}
