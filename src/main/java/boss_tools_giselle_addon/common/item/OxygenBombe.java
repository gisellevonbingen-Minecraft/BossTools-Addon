package boss_tools_giselle_addon.common.item;

import java.util.List;

import boss_tools_giselle_addon.common.capability.OxygenCapacitorCapabilityProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;

public class OxygenBombe extends Item
{
	public OxygenBombe(Properties properties)
	{
		super(properties.stacksTo(1));
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, tooltip, flag);

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
	public int getRGBDurabilityForDisplay(ItemStack stack)
	{
		return super.getRGBDurabilityForDisplay(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return stack.getCapability(CapabilityOxygen.OXYGEN).isPresent();
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt)
	{
		return new OxygenCapacitorCapabilityProvider(stack, 48000, 256);
	}

}
