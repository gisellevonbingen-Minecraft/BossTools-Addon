package boss_tools_giselle_addon.common.item;

import java.util.List;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.capability.CapabilityChargeModeHandler;
import boss_tools_giselle_addon.common.capability.IChargeMode;
import boss_tools_giselle_addon.common.capability.IChargeModeHandler;
import boss_tools_giselle_addon.common.capability.OxygenCanCapabilityProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;

public class OxygenCan extends Item
{
	public OxygenCan(Properties properties)
	{
		super(properties.stacksTo(1));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);

		if (world.isClientSide() == true)
		{
			return ActionResult.pass(stack);
		}

		if (player.isShiftKeyDown() == false)
		{
			IChargeModeHandler chargeModeHandler = stack.getCapability(CapabilityChargeModeHandler.CHARGE_MODE_HANDLER).orElse(null);

			if (chargeModeHandler != null)
			{
				IChargeMode chargeMode = chargeModeHandler.getChargeMode();
				List<IChargeMode> modes = chargeModeHandler.getAvailableChargeModes();
				int nextIndex = (modes.indexOf(chargeMode) + 1) % modes.size();
				IChargeMode nextMode = modes.get(nextIndex);
				chargeModeHandler.setChargeMode(nextMode);

				player.sendMessage(new TranslationTextComponent(BossToolsAddon.tl("description", this.getRegistryName(), "charge_mode"), nextMode.getDisplayName()), null);
			}

		}

		return ActionResult.pass(stack);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);

		if (world.isClientSide() == true)
		{
			return;
		}

		IOxygenStorage oxygenStorage = stack.getCapability(CapabilityOxygen.OXYGEN).orElse(null);
		IChargeModeHandler chargeModeHandler = stack.getCapability(CapabilityChargeModeHandler.CHARGE_MODE_HANDLER).orElse(null);

		if (oxygenStorage != null && chargeModeHandler != null)
		{
			int transfer = 256;
			Iterable<ItemStack> itemStacks = chargeModeHandler.getChargeMode().getItemStacks(entity);

			for (ItemStack itemStack : itemStacks)
			{
				IOxygenStorage targetOxygenStorage = itemStack.getCapability(CapabilityOxygen.OXYGEN).orElse(null);

				if (targetOxygenStorage != null)
				{
					int extracting = oxygenStorage.extractOxygen(transfer, true);
					int receiving = targetOxygenStorage.receiveOxygen(extracting, true);

					if (receiving > 0)
					{
						oxygenStorage.extractOxygen(receiving, false);
						targetOxygenStorage.receiveOxygen(receiving, false);
					}

				}

			}

		}

	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, tooltip, flag);

		IChargeModeHandler chargeModeHandler = stack.getCapability(CapabilityChargeModeHandler.CHARGE_MODE_HANDLER).orElse(null);

		if (chargeModeHandler != null)
		{
			ITextComponent modeDisplayName = chargeModeHandler.getChargeMode().getDisplayName();
			tooltip.add(new TranslationTextComponent(BossToolsAddon.tl("description", this.getRegistryName(), "charge_mode"), modeDisplayName).withStyle(TextFormatting.GRAY));
		}

		IOxygenStorage oxygenStorage = stack.getCapability(CapabilityOxygen.OXYGEN).orElse(null);

		if (oxygenStorage != null)
		{
			tooltip.add(GaugeTextHelper.buildSpacesuitOxygenTooltip(oxygenStorage));
		}

	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return 1.0D - stack.getCapability(CapabilityOxygen.OXYGEN).map(IOxygenStorage::getOxygenStoredRatio).orElse(0.0D);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return stack.getCapability(CapabilityOxygen.OXYGEN).isPresent();
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt)
	{
		return new OxygenCanCapabilityProvider(stack, 48000, 256);
	}

}
