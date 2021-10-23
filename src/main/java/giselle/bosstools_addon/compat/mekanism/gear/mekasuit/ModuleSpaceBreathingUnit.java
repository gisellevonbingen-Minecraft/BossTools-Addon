package giselle.bosstools_addon.compat.mekanism.gear.mekasuit;

import java.util.List;

import javax.annotation.Nullable;

import giselle.bosstools_addon.config.AddonConfigs;
import mekanism.api.Action;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.math.FloatingLong;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.content.gear.HUDElement;
import mekanism.common.content.gear.Modules.ModuleData;
import mekanism.common.content.gear.mekasuit.ModuleMekaSuit;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismGases;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.StorageUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class ModuleSpaceBreathingUnit extends ModuleMekaSuit
{
	public static final ResourceLocation ICON = MekanismUtils.getResource(MekanismUtils.ResourceType.GUI_HUD, "space_breathing_unit.png");
	public static final String OXYGEN_NBT_KEY = "Oxygen_Bullet_Generator";
	public static final String TIMER_NBT_KEY = "timer_oxygen";

	private long maxProduceRate = 0;
	private long usingOxygen = 0;
	private FloatingLong usingEnergy = null;

	@Override
	public void init(ModuleData<?> data, ItemStack container)
	{
		super.init(data, container);

		this.maxProduceRate = AddonConfigs.Common.mekanism.moduleSpaceBreathing_maxProduceRate.get();
		this.usingOxygen = AddonConfigs.Common.mekanism.moduleSpaceBreathing_usingOxygen.get();
		this.usingEnergy = FloatingLong.create(AddonConfigs.Common.mekanism.moduleSpaceBreathing_usingEnergy.get());
	}

	@Override
	protected void tickServer(PlayerEntity player)
	{
		super.tickServer(player);

		this.produceOxygen(player);
		this.useOxygen(player);
	}

	private void useOxygen(PlayerEntity player)
	{
		CompoundNBT compound = player.getPersistentData();

		if (compound.getBoolean(OXYGEN_NBT_KEY) == false)
		{
			if (this.useResources(player) == true)
			{
				compound.putBoolean(OXYGEN_NBT_KEY, true);
				compound.putDouble(TIMER_NBT_KEY, 0);
			}

		}
	}

	private void produceOxygen(PlayerEntity player)
	{
		long productionRate = getProduceRate(player);

		if (productionRate > 0)
		{
			ItemStack chestStack = this.getContainer();
			IGasHandler chestCapability = chestStack.getCapability(Capabilities.GAS_HANDLER_CAPABILITY).orElse(null);

			if (chestCapability != null)
			{
				chestCapability.insertChemical(MekanismGases.OXYGEN.getStack(productionRate), Action.EXECUTE);
			}

		}

	}

	public long getProduceRate(PlayerEntity player)
	{
		double maskHeight = player.getEyeHeight() - 0.15D;
		BlockPos headPos = new BlockPos(player.getBbWidth(), maskHeight, player.getBbHeight());
		FluidState fluidstate = player.level.getFluidState(headPos);

		if (fluidstate.is(FluidTags.WATER) && maskHeight <= (headPos.getY() + fluidstate.getHeight(player.level, headPos)))
		{
			return this.getMaxProduceRate();
		}
		else if (player.isInWaterOrRain())
		{
			return this.getMaxProduceRate() / 2;
		}

		return 0L;
	}

	public boolean useResources(LivingEntity entity)
	{
		ItemStack container = this.getContainer();
		IGasHandler gasHandlerItem = container.getCapability(Capabilities.GAS_HANDLER_CAPABILITY).orElse(null);
		GasStack usingOxygen = new GasStack(this.getGas(), this.getUsingOxygen());

		if (gasHandlerItem != null && gasHandlerItem.extractChemical(usingOxygen, Action.SIMULATE).getAmount() >= usingOxygen.getAmount())
		{
			FloatingLong usingEnergy = this.getUsingEnergy();

			if (this.canUseEnergy(entity, usingEnergy) == true)
			{
				gasHandlerItem.extractChemical(usingOxygen, Action.EXECUTE);
				this.useEnergy(entity, usingEnergy);
				return true;
			}

		}

		return false;
	}

	@Override
	public void addHUDElements(List<HUDElement> list)
	{
		super.addHUDElements(list);

		if (this.isEnabled() == false)
		{
			return;
		}

		ItemStack container = this.getContainer();
		Gas gas = this.getGas();
		GasStack stored = ((ItemMekaSuitArmor) container.getItem()).getContainedGas(container, gas);
		long capacity = this.getGasCapacity(container, gas);
		double ratio = StorageUtils.getRatio(stored.getAmount(), capacity);
		list.add(HUDElement.percent(ICON, ratio));
	}

	public Gas getGas()
	{
		return MekanismGases.OXYGEN.get();
	}

	@Nullable
	private long getGasCapacity(ItemStack container, Gas gas)
	{
		IGasHandler gasHandlerItem = container.getCapability(Capabilities.GAS_HANDLER_CAPABILITY).orElse(null);

		if (gasHandlerItem != null)
		{
			for (int i = 0; i < gasHandlerItem.getTanks(); ++i)
			{
				if (gasHandlerItem.isValid(i, new GasStack(gas, 1)))
				{
					return gasHandlerItem.getTankCapacity(i);
				}

			}

		}

		return 0;
	}

	public long getMaxProduceRate()
	{
		return this.maxProduceRate;
	}

	public long getUsingOxygen()
	{
		return this.usingOxygen;
	}

	public FloatingLong getUsingEnergy()
	{
		return this.usingEnergy;
	}

}
