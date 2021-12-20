package boss_tools_giselle_addon.common.item;

import java.util.List;

import boss_tools_giselle_addon.common.capability.CapabilityOxygenCharger;
import boss_tools_giselle_addon.common.capability.ChargeMode;
import boss_tools_giselle_addon.common.capability.IChargeMode;
import boss_tools_giselle_addon.common.capability.IOxygenCharger;
import boss_tools_giselle_addon.common.capability.OxygenCanCapabilityProvider;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.util.TranslationUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.OxygenUtil;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;

public class OxygenCanItem extends Item
{
	public OxygenCanItem(Properties properties)
	{
		super(properties.stacksTo(1));
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> list)
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
		IOxygenCharger oxygenCharger = stack.getCapability(CapabilityOxygenCharger.OXYGEN_CHARGER).orElse(null);

		if (oxygenCharger != null && oxygenCharger.getOxygenStorage().extractOxygen(1, true) > 0 && oxygenCharger.getChargeMode() != ChargeMode.NONE)
		{
			return true;
		}

		return super.isFoil(stack);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);

		if (world.isClientSide() == true)
		{
			return ActionResult.pass(stack);
		}

		IOxygenCharger oxygenCharger = stack.getCapability(CapabilityOxygenCharger.OXYGEN_CHARGER).orElse(null);

		if (oxygenCharger != null && player.isShiftKeyDown() == false)
		{
			IChargeMode chargeMode = oxygenCharger.getChargeMode();
			List<IChargeMode> modes = oxygenCharger.getAvailableChargeModes();
			int nextIndex = (modes.indexOf(chargeMode) + 1) % modes.size();
			IChargeMode nextMode = modes.get(nextIndex);
			oxygenCharger.setChargeMode(nextMode);

			player.sendMessage(TranslationUtils.descriptionChargeMode(nextMode), player.getUUID());
		}

		return ActionResult.pass(stack);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		super.inventoryTick(stack, world, entity, slot, selected);

		if (world.isClientSide() == false)
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

		IOxygenCharger oxygenCharger = stack.getCapability(CapabilityOxygenCharger.OXYGEN_CHARGER).orElse(null);

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
			if (itemStack.getCapability(CapabilityOxygenCharger.OXYGEN_CHARGER).isPresent() == true)
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
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, tooltip, flag);

		IOxygenCharger oxygenCharger = stack.getCapability(CapabilityOxygenCharger.OXYGEN_CHARGER).orElse(null);

		if (oxygenCharger != null)
		{
			tooltip.add(TranslationUtils.descriptionChargeMode(oxygenCharger.getChargeMode()));
			tooltip.add(GaugeTextHelper.buildSpacesuitOxygenTooltip(oxygenCharger.getOxygenStorage()));
		}

	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return 1.0D - stack.getCapability(CapabilityOxygenCharger.OXYGEN_CHARGER).lazyMap(IOxygenCharger::getOxygenStorage).map(IOxygenStorage::getOxygenStoredRatio).orElse(0.0D);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return stack.getCapability(CapabilityOxygenCharger.OXYGEN_CHARGER).isPresent();
	}

	@Override
	public OxygenCanCapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt)
	{
		return new OxygenCanCapabilityProvider(stack, 24000, 256);
	}

}
