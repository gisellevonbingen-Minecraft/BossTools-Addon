package boss_tools_giselle_addon.common.compat.mekanism.gear;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.compat.mekanism.AddonModuleHelper;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import mekanism.api.Action;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.math.FloatingLong;
import mekanism.common.capabilities.Capabilities;
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

public class ModuleSpaceBreathingUnit implements ICustomModule<ModuleSpaceBreathingUnit>
{
	private static final String OXYGEN_LIFE_KEY = "oxygenLife";

	public static final ResourceLocation ICON = BossToolsAddon.rl(MekanismUtils.ResourceType.GUI_HUD.getPrefix() + "space_breathing_unit.png");

	private long maxProduceRate = 0;
	private long oxygenUsing = 0;
	private long oxygenDuration = 0;
	private FloatingLong energyUsingProvide;
	private FloatingLong energyUsingProduce;

	public ModuleSpaceBreathingUnit()
	{

	}

	@Override
	public void init(IModule<ModuleSpaceBreathingUnit> module, ModuleConfigItemCreator configItemCreator)
	{
		ICustomModule.super.init(module, configItemCreator);

		this.maxProduceRate = AddonConfigs.Common.mekanism.moduleSpaceBreathing_maxProduceRate.get();
		this.oxygenUsing = AddonConfigs.Common.mekanism.moduleSpaceBreathing_oxygenUsing.get();
		this.oxygenDuration = AddonConfigs.Common.mekanism.moduleSpaceBreathing_oxygenDuration.get();
		this.energyUsingProvide = FloatingLong.create(AddonConfigs.Common.mekanism.moduleSpaceBreathing_energyUsingProvide.get());
		this.energyUsingProduce = FloatingLong.create(AddonConfigs.Common.mekanism.moduleSpaceBreathing_energyUsingProduce.get());
	}

	/**
	 * 
	 * @param entity
	 * @return whether provided oxygen to entity
	 */
	public boolean provideOxygen(IModule<ModuleSpaceBreathingUnit> module, LivingEntity entity)
	{
		if (this.getOxygenLife(module) > 0)
		{
			return true;
		}
		else if (this.useResources(module, entity) == true)
		{
			this.setOxygenLife(module, this.getOxygenDuration());
			return true;
		}
		else
		{
			return false;
		}

	}

	@Override
	public void tickServer(IModule<ModuleSpaceBreathingUnit> module, PlayerEntity player)
	{
		ICustomModule.super.tickServer(module, player);

		this.produceOxygen(module, player);
		this.reduceOxygenLife(module);
	}

	private void reduceOxygenLife(IModule<ModuleSpaceBreathingUnit> module)
	{
		long oxygenLife = this.getOxygenLife(module);

		if (oxygenLife > 0)
		{
			this.setOxygenLife(module, oxygenLife - 1);
		}

	}

	private void produceOxygen(IModule<ModuleSpaceBreathingUnit> module, PlayerEntity player)
	{
		long productionRate = getProduceRate(player);

		if (productionRate == 0)
		{
			return;
		}
		
		long productionRateFirst = productionRate;
		FloatingLong energyUsing = this.getEnergyUsingProvide();
		
		if (module.canUseEnergy(player, energyUsing) == true)
		{
			int airSupply = player.getAirSupply();
			int airFill = (int) Math.min(productionRate, player.getMaxAirSupply() - airSupply);

			if (airFill > 0)
			{
				player.setAirSupply(airSupply + airFill);
				productionRate -= airFill;
			}

			if (productionRate > 0)
			{
				ItemStack chestStack = module.getContainer();
				IGasHandler chestCapability = chestStack.getCapability(Capabilities.GAS_HANDLER_CAPABILITY).orElse(null);

				if (chestCapability != null)
				{
					GasStack remain = chestCapability.insertChemical(MekanismGases.OXYGEN.getStack(productionRate), Action.EXECUTE);
					productionRate = remain.getAmount();
				}

			}

			long oxygenUsed = productionRateFirst - productionRate;
			long oxygenUsedRatio = oxygenUsed / productionRateFirst;
			FloatingLong multiply = energyUsing.multiply(oxygenUsedRatio);
			module.useEnergy(player, multiply);
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

	public boolean useResources(IModule<ModuleSpaceBreathingUnit> module, LivingEntity entity)
	{
		ItemStack container = module.getContainer();
		IGasHandler gasHandlerItem = container.getCapability(Capabilities.GAS_HANDLER_CAPABILITY).orElse(null);
		GasStack usingOxygen = new GasStack(this.getGas(), this.getOxygenUsing());

		if (gasHandlerItem != null && gasHandlerItem.extractChemical(usingOxygen, Action.SIMULATE).getAmount() >= usingOxygen.getAmount())
		{
			FloatingLong energyUsing = this.getEnergyUsingProvide();

			if (module.canUseEnergy(entity, energyUsing) == true)
			{
				gasHandlerItem.extractChemical(usingOxygen, Action.EXECUTE);
				module.useEnergy(entity, energyUsing);
				return true;
			}

		}

		return false;
	}

	@Override
	public void addHUDElements(IModule<ModuleSpaceBreathingUnit> module, PlayerEntity player, Consumer<IHUDElement> hudElementAdder)
	{
		ICustomModule.super.addHUDElements(module, player, hudElementAdder);

		if (module.isEnabled() == false)
		{
			return;
		}

		ItemStack container = module.getContainer();
		Gas gas = this.getGas();
		GasStack stored = ((ItemMekaSuitArmor) container.getItem()).getContainedGas(container, gas);
		long capacity = this.getGasCapacity(container, gas);
		double ratio = StorageUtils.getRatio(stored.getAmount(), capacity);
		hudElementAdder.accept(MekanismAPI.getModuleHelper().hudElementPercent(ICON, ratio));
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

	public long getOxygenUsing()
	{
		return this.oxygenUsing;
	}

	public long getOxygenDuration()
	{
		return this.oxygenDuration;
	}

	public FloatingLong getEnergyUsingProvide()
	{
		return this.energyUsingProvide;
	}

	public FloatingLong getEnergyUsingProduce()
	{
		return energyUsingProduce;
	}

	public long getOxygenLife(IModule<ModuleSpaceBreathingUnit> module)
	{
		CompoundNBT compound = AddonModuleHelper.getCustomTag(module, module.getContainer());
		return compound != null ? compound.getLong(OXYGEN_LIFE_KEY) : 0L;
	}

	public void setOxygenLife(IModule<ModuleSpaceBreathingUnit> module, long oxygenLife)
	{
		CompoundNBT compound = AddonModuleHelper.getOrCreateCustomTag(module, module.getContainer());
		compound.putLong(OXYGEN_LIFE_KEY, Math.max(oxygenLife, 0L));
	}

}
